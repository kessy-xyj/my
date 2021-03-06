package indi.uhyils.dao;

import indi.uhyils.dao.base.DefaultDao;
import indi.uhyils.pojo.model.DictItemEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;


/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年04月25日 13时03分
 */
@Mapper
public interface DictItemDao extends DefaultDao<DictItemEntity> {

    /**
     * 删除字典表指定字段对应的字典项
     *
     * @param id 字典id
     * @return 删除的个数
     */
    Integer deleteByDictId(String id);

    /**
     * 获取某个字典的所有字典项
     *
     * @param id 字典id
     * @return 某个字典的所有字典项
     */
    ArrayList<DictItemEntity> getByDictId(String id);

    /**
     * 根据字典code获取字典项
     *
     * @param code 字典code
     * @return 字典项
     */
    ArrayList<DictItemEntity> getByCode(String code);
}
