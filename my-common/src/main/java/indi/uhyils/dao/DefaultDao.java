package indi.uhyils.dao;

import java.io.Serializable;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年04月23日 15时22分
 */
public interface DefaultDao<T extends Serializable> {

    T getById(String id);

    int insert(T t);

    int update(T t);

    int delete(String id);

}