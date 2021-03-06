package indi.uhyils.pojo.model;

import indi.uhyils.pojo.model.base.BaseVoEntity;

/**
 * 页面推送
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月29日 08时10分
 */
public class SendPageEntity extends BaseVoEntity {

    /**
     * 用户
     */
    private String userId;
    /**
     * 是否观看
     */
    private Boolean view;

    /**
     * 内容
     */
    private String content;

    /**
     * 标题
     */
    private String title;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getView() {
        return view;
    }

    public void setView(Boolean view) {
        this.view = view;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
