package indi.uhyils.pojo.model;

import indi.uhyils.pojo.model.base.BaseVoEntity;

/**
 * (Response)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年08月29日 10时47分51秒
 */
public class ResponseEntity extends BaseVoEntity {

    private static final long serialVersionUID = -32346207764606945L;

    /**
     * 触发类型
     */
    private Integer triggerType;

    /**
     * 触发场景id
     */
    private String tiggerSceneId;

    /**
     * 指令id
     */
    private String instructionsId;

    /**
     * 1->人工设置
     * 2->网上批量导入
     * 3->机器自动学习
     */
    private Integer manual;


    public Integer getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(Integer triggerType) {
        this.triggerType = triggerType;
    }

    public String getTiggerSceneId() {
        return tiggerSceneId;
    }

    public void setTiggerSceneId(String tiggerSceneId) {
        this.tiggerSceneId = tiggerSceneId;
    }

    public String getInstructionsId() {
        return instructionsId;
    }

    public void setInstructionsId(String instructionsId) {
        this.instructionsId = instructionsId;
    }

    public Integer getManual() {
        return manual;
    }

    public void setManual(Integer manual) {
        this.manual = manual;
    }

}
