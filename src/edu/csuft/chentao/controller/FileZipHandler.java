/**
 * 
 */
package edu.csuft.chentao.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
		
		File file = new File(fz.getFileName());
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(fz.getZip());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
