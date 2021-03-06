package indi.uhyils.pojo.model;

import indi.uhyils.pojo.model.base.BaseVoEntity;

/**
 * (DeviceCallback)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年08月29日 10时47分48秒
 */
public class DeviceCallbackEntity extends BaseVoEntity {

    private static final long serialVersionUID = -26635302287022954L;

    /**
     * 有回调的设备id
     */
    private String deviceId;

    /**
     * 回调url
     */
    private String url;

    /**
     * 意义
     */
    private String meaning;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

}
