/**
 * 
 */
package edu.csuft.chentao.controller;

import edu.csuft.chentao.dao.GroupFileZipTable;
import edu.csuft.chentao.dao.GroupFileZipTableOperation;
import edu.csuft.chentao.pojo.req.FileZip;
import edu.csuft.chentao.util.OperationUtil;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 * 
 *         2017年4月23日 下午12:03:13
 */
public class FileZipHandler implements Handler {

	public void handle(ChannelHandlerContext chc, Object object) {
		FileZip fz = (FileZip) object;

		OperationUtil.saveGroupFile(fz.getGroupId(), fz.getSerialNumber(),
				fz.getZip());

		// 插入数据表中
		GroupFileZipTable table = GroupFileZipTable.copyToGroupFileZipTable(fz);
		GroupFileZipTableOperation.insert(table);

		/*
		 * 上传完毕后，将该文件信息发送到客户端去
		 */
		// 文件大小
		fz.setFileSize(String.valueOf(fz.getZip().length));
		// 不包含文件数据
		fz.setZip(null);

		// 发送
		chc.writeAndFlush(fz);
	}
}
