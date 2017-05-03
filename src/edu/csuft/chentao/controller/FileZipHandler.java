/**
 * 
 */
package edu.csuft.chentao.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import edu.csuft.chentao.dao.GroupFileZipTable;
import edu.csuft.chentao.dao.GroupFileZipTableOperation;
import edu.csuft.chentao.pojo.req.FileZip;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 * 
 *         2017年4月23日 下午12:03:13
 */
public class FileZipHandler implements Handler {

	public void handle(ChannelHandlerContext chc, Object object) {
		FileZip fz = (FileZip) object;

		System.out.println("fz.getFileName = " + fz.getFileName());
		System.out.println("fz.fileLength = " + fz.getZip().length);
		System.out.println("fz.serailNumber = " + fz.getSerialNumber());
		// 创建文件
		File file = new File(fz.getSerialNumber());
		// 如果不存在，则新建
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream fos = null;
		try {
			// 写入
			fos = new FileOutputStream(file);
			fos.write(fz.getZip());

			//插入数据表中
			GroupFileZipTable table = GroupFileZipTable.copyToGroupFileZipTable(fz);
			GroupFileZipTableOperation.insert(table);
			
			/*
			 * 上传完毕后，将该文件信息发送到客户端去
			 */
			//文件大小
			fz.setFileSize(String.valueOf(fz.getZip().length));
			//不包含文件数据
			fz.setZip(null);
			
			//发送
			chc.writeAndFlush(fz);
			
		} catch (FileNotFoundException e) {
			System.out.println("文件未找到");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO流错误");
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
