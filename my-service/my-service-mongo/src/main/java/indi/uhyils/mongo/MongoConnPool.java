package indi.uhyils.mongo;

import org.apache.commons.collections.list.SynchronizedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * mongo连接池
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年07月01日 14时56分
 */
@Component
public class MongoConnPool {

    /**
     * 连接安全的集合
     */
    private volatile List<MongoConn> list = SynchronizedList.decorate(new ArrayList<>());

    /**
     * 守护线程 用来销毁到期线程
     */
    private Thread guardianThread;

    @Autowired
    private MongoDbFactory mongoDbFactory;

    /**
     * 核心连接数量
     */
    @Value("${mongo.pool.coreSize}")
    private Integer coreConnSize;
    /**
     * 最大连接数量
     */
    @Value("${mongo.pool.maxSize}")
    private Integer maxConnSize;
    /**
     * 核心之外的连接存活时间
     */
    @Value("${mongo.pool.keepAliveTime}")
    private Long keepAliveTime;

    public MongoConnPool() {
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        // ?? nothing to do
    }

    public List<MongoConn> getList() {
        return list;
    }

    public void setList(List<MongoConn> list) {
        this.list = list;
    }

    public Integer getCoreConnSize() {
        return coreConnSize;
    }

    public void setCoreConnSize(Integer coreConnSize) {
        this.coreConnSize = coreConnSize;
    }

    public Integer getMaxConnSize() {
        return maxConnSize;
    }

    public void setMaxConnSize(Integer maxConnSize) {
        this.maxConnSize = maxConnSize;
    }

    public Long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public MongoConn getConn() throws Exception {
        for (MongoConn mongoConn : list) {
            // 如果没有人用 (类似双重检测锁)
            if (!mongoConn.getHaveUse()) {
                synchronized (mongoConn) {
                    if (!mongoConn.getHaveUse()) {
                        mongoConn.setHaveUse(true);
                        mongoConn.setCreateTime(System.currentTimeMillis());
                        return mongoConn;
                    }
                }
            }
        }
        if (list.size() >= maxConnSize) {
            throw new Exception("连接池已满,fuck?!!");
        }
        synchronized (list) {
            MongoConn conn = mongoDbFactory.getConn(this);
            list.add(conn);
            return conn;
        }
    }

    public void returnConn(MongoConn conn) {
        conn.setHaveUse(false);
    }
}