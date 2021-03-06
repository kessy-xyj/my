package indi.uhyils.pojo.model;

import indi.uhyils.pojo.model.base.BaseIdEntity;

/**
 * 接口调用监控信息 和{@link MonitorDO}是多对一的关系,记录了每一次的接口调用的信息 拟将此数据库代替log数据库,但是没有解决无法获取用户ip的痛点
 * {@db sys_monitorInterfaceDetailDO}
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月19日 14时22分
 */
public class MonitorInterfaceDetailDO extends BaseIdEntity {

    /**
     * 见{@link MonitorDO}
     */
    private String fid;

    /**
     * 用户调用的接口名称
     */
    private String interfaceName;

    /**
     * 用户调用的接口方法
     */
    private String methodName;

    /**
     * 此条操作是否达到了用户想要的效果 即只有ServiceCode{@ps 无法link} 为success时此字段才为true
     */
    private Boolean success;

    /**
     * 操作发生时间 时间戳类型
     */
    private Long time;

    /**
     * 执行时间跨度 时间戳类型
     */
    private Long runTime;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getRunTime() {
        return runTime;
    }

    public void setRunTime(Long runTime) {
        this.runTime = runTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("            \"fid\":\"")
                .append(fid).append('\"');
        if (interfaceName != null) {
            sb.append(",            \"interfaceName\":\"")
                    .append(interfaceName).append('\"');
        }
        if (methodName != null) {
            sb.append(",            \"methodName\":\"")
                    .append(methodName).append('\"');
        }
        if (success != null) {
            sb.append(",            \"success\":")
                    .append(success);
        }
        if (time != null) {
            sb.append(",            \"time\":")
                    .append(time);
        }
        if (runTime != null) {
            sb.append(",            \"runTime\":")
                    .append(runTime);
        }
        sb.append('}');
        return sb.toString();
    }
}
