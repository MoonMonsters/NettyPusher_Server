package edu.csuft.chentao.pojo.req;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 聊天图片
 * 
 * @author cusft.chentao
 *
 */
public class PicFile implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 文件名 */
	String filename;// (随机构造16位字符串当图片名称)
	/** 文件长度 */
	int length;
	/** 内容 */
	byte[] buf;

	public PicFile(String filename, int length, byte[] buf) {
		super();
		this.filename = filename;
		this.length = length;
		this.buf = buf;
	}

	public PicFile() {
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
		return "PicFileReq [filename=" + filename + ", length=" + length
				+ ", buf=" + Arrays.toString(buf) + "]";
	}

}
