package edu.csuft.chentao.pojo.req;

import java.io.Serializable;

/**
 * 在手机端和服务端传输的公告信息
 */
public class Announcement implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号，预留，如果以后做删除操作用的着
     */
    private String serialnumber;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 发布者名字，用作预备名字，如果根据用户id在用户数据库中没有搜索到，则使用该名字
     */
    private String username;

    /**
     * 发布者用户id
     */
    private int userid;

    /**
     * 群id
     */
    private int groupid;

    /**
     * 发布时间
     */
    private String time;

    public Announcement() {
    }

    public Announcement(String serialnumber, String title, String content, String username, int userid, String time) {
        this.serialnumber = serialnumber;
        this.title = title;
        this.content = content;
        this.username = username;
        this.userid = userid;
        this.time = time;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }
}
