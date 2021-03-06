package indi.uhyils.pojo.request;

import indi.uhyils.pojo.request.base.DefaultRequest;

/**
 * 根据字典code获取字典项
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月25日 15时01分
 */
public class GetByCodeRequest extends DefaultRequest {

    /**
     * 字典code
     */
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
