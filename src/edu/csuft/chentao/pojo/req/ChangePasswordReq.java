package edu.csuft.chentao.pojo.req;

import java.io.Serializable;

/**
 * Created by Chalmers on 2017-05-06 21:26.
 * email:qxinhai@yeah.net
 */

public class ChangePasswordReq implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
     * 用户id
     */
    private int userId;

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 重复新密码
     */
    private String newPassword2;

    public ChangePasswordReq(int userId, String oldPassword, String newPassword, String newPassword2) {
        this.userId = userId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newPassword2 = newPassword2;
    }

    public ChangePasswordReq() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }

    @Override
    public String toString() {
        return "ChangePasswordReq{" +
                "userId=" + userId +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", newPassword2='" + newPassword2 + '\'' +
                '}';
    }
}
