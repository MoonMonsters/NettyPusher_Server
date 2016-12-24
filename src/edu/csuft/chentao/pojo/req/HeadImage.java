package edu.csuft.chentao.pojo.req;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 头像
 * @author cusft.chentao
 *
 */
public class HeadImage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 文件名 */
	String filename;// (在服务端根据用户id转成md5码存储)
	/** 文件长度 */
	int length;
	/** 内容 */
	byte[] buf;

	public HeadImage(String filename, int length, byte[] buf) {
		super();
		this.filename = filename;
		this.length = length;
		this.buf = buf;
	}

	public HeadImage() {
		super();
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public byte[] getBuf() {
		return buf;
	}

	public void setBuf(byte[] buf) {
		this.buf = buf;
	}

	@Override
	public String toString() {
		return "HeadImageReq [filename=" + filename + ", length=" + length
				+ ", buf=" + Arrays.toString(buf) + "]";
	}
}
