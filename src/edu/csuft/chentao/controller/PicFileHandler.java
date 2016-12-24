/**
 * 处理图片对象
 */
package edu.csuft.chentao.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import edu.csuft.chentao.pojo.req.PicFile;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 *
 *         2016年12月9日 下午9:23:51
 */
public class PicFileHandler implements Handler {

	private static final String PATH = "./picfile";

	@Override
	public void handle(ChannelHandlerContext chc, Object object) {
		PicFile picFile = (PicFile) object;
		FileOutputStream fos = null;

		try {
			//如果图片已经存在，那么更新（删除，重新存入）
			File file = new File(PATH, picFile.getFilename());
			if (file.exists()) {
				file.delete();
			}
			fos = new FileOutputStream(file);
			fos.write(picFile.getBuf(), 0, picFile.getLength());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
