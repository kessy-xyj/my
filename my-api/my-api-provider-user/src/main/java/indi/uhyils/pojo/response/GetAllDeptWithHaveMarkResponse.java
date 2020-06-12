package indi.uhyils.pojo.response;

import java.io.Serializable;

/**
 * 获取根据角色id所有的所有权限集, 包含此角色包不包含此权限
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月07日 09时58分
 */
public class GetAllDeptWithHaveMarkResponse implements Serializable {

    /**
     * 权限集id
     */
    private String deptId;

    /**
     * 权限集名称
     */
    private String deptName;

    /**
     * 角色是否包含此权限集
     */
    private Boolean mark;


    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Boolean getMark() {
        return mark;
    }

    public void setMark(Boolean mark) {
        this.mark = mark;
    }
}
