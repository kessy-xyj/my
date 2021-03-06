package indi.uhyils.pojo.model;

import indi.uhyils.pojo.model.base.BaseIdEntity;

/**
 * JVM实时信息
 * 记录了每一个微服务的JVM信息,值得注意的是,并
 * 不是实时的,会有5-30分钟的间隔,通过各个微服务
 * 的定时任务来进行此表的插入操作{@ps 中间通过了
 * MQ来转移消息, 保证尽量不延迟}
 * 此数据库与{@link MonitorDO} 是多对一的关系
 * {@db sys_MonitorJvmStatusDetailDO}
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月19日 14时18分
 */
public class MonitorJvmStatusDetailDO extends BaseIdEntity {

    private String fid;

    /**
     * 已使用总内存
     */
    private Double useMem;

    /**
     * 堆区已使用内存
     */
    private Double heapUseMem;

    /**
     * 栈已使用内存
     */
    private Double noHeapUseMem;

    /**
     * status生成时间
     */
    private Long time;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public Double getUseMem() {
        return useMem;
    }

    public void setUseMem(Double useMem) {
        this.useMem = useMem;
    }

    public Double getHeapUseMem() {
        return heapUseMem;
    }

    public void setHeapUseMem(Double heapUseMem) {
        this.heapUseMem = heapUseMem;
    }

    public Double getNoHeapUseMem() {
        return noHeapUseMem;
    }

    public void setNoHeapUseMem(Double noHeapUseMem) {
        this.noHeapUseMem = noHeapUseMem;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("            \"fid\":\"")
                .append(fid).append('\"');
        if (useMem != null) {
            sb.append(",            \"useMem\":")
                    .append(useMem);
        }
        if (heapUseMem != null) {
            sb.append(",            \"heapUseMem\":")
                    .append(heapUseMem);
        }
        if (noHeapUseMem != null) {
            sb.append(",            \"noHeapUseMem\":")
                    .append(noHeapUseMem);
        }
        if (time != null) {
            sb.append(",            \"time\":")
                    .append(time);
        }
        sb.append('}');
        return sb.toString();
    }
}
