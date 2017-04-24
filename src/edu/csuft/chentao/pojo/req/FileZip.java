package edu.csuft.chentao.pojo.req;

/**
 * @author csuft.chentao
 */

import java.io.Serializable;

/**
 * 文件上传下载类
 */
public class FileZip implements Serializable {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件的序列号
     */
    private String serialNumber;
    /**
     * 用户名
     */
    private String nickname;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 文件内容
     */
    private byte[] zip;

    public FileZip(String fileName, String serialNumber, String nickname, int userId, byte[] zip) {
        this.fileName = fileName;
        this.serialNumber = serialNumber;
        this.nickname = nickname;
        this.userId = userId;
        this.zip = zip;
    }

    public FileZip() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public byte[] getZip() {
        return zip;
    }

    public void setZip(byte[] zip) {
        this.zip = zip;
    }
}