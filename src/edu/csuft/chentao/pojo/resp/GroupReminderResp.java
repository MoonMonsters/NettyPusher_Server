/**
 * 对群操作的描述
 */
package edu.csuft.chentao.pojo.resp;

import java.io.Serializable;

/**
 * @author csuft.chentao
 *
 *         2017年2月6日 下午9:55:41
 */
/**
 * int type;
 * int groupId;
 * int description;
 * int readerId;
 * int userId;
 * @author csuft.chentao
 *
 * 2017年2月6日 下午11:27:27
 */
public class GroupReminderResp implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 返回消息类型
     */
    private int type;
    /**
     * 群头像
     */
    private byte[] image;
    /**
     * 群id
     */
    private int groupId;
    /**
     * 待处理的用户id
     */
    private int userId;
    /**
     * 对操作的描述
     */
    private String description;
    /**
     * 群名称
     */
    private String groupName;

    public GroupReminderResp(int type, byte[] image, int groupId,
                             int userId, String description, String groupName) {
        this.type = type;
        this.image = image;
        this.groupId = groupId;
        this.userId = userId;
        this.description = description;
        this.groupName = groupName;
    }

    public GroupReminderResp() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "GroupReminderResp{" +
                "type=" + type +
                ", groupId=" + groupId +
                ", userId=" + userId +
                ", description='" + description + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }

}
