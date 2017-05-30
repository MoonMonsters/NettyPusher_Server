package edu.csuft.chentao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import edu.csuft.chentao.pojo.req.Message;
import edu.csuft.chentao.util.Constant;
import edu.csuft.chentao.util.OperationUtil;

/**
 * @author csuft.chentao
 *
 *         2017年5月29日 上午12:03:40
 */
public class MessageTableOperate {

	/**
	 * 插入数据
	 * 
	 * @param table
	 *            要插入的数据
	 */
	public static void insert(MessageTable table) {
		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			String sql = "insert into " + MessageTable.TABLE_ALL_FIELD
					+ " values(?,?,?,?,?,?,?)";

			ps = connection.prepareStatement(sql);
			// 插入数据
			// message(user_id,group_id,type_msg,time,content,image_serial_number)
			ps.setInt(1, table.getUserId());
			ps.setInt(2, table.getGroupId());
			ps.setInt(3, table.getTypeMsg());
			ps.setString(4, table.getTime());
			ps.setString(5, table.getContent());
			ps.setString(6, table.getImageSerialNumber());
			ps.setInt(7, table.getMessageSerialNumber());

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}
	}

	/**
	 * 分页查询所有聊天信息
	 * 
	 * @param groupId
	 *            群id
	 * @param from
	 *            开始位置
	 * @param to
	 *            结束位置
	 * @return 聊天信息集合
	 */
	public static List<Message> queryAllByGroupId(int groupId, int from, int to) {
		List<Message> messageList = new ArrayList<Message>();

		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// user_id,group_id,type_msg,time,content,image_serial_number,message_serial_number
			String sql = "select user_id,group_id,type_msg,time,content,image_serial_number,message_serial_number"
					+ " from "
					+ MessageTable.TABLE_NAME
					+ " where "
					+ MessageTable.GROUP_ID
					+ " = ? order by "
					+ MessageTable.ID + " asc limit ?,?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, groupId);
			ps.setInt(2, from);
			ps.setInt(3, to);
			rs = ps.executeQuery();
			while (rs.next()) {
				int userId = rs.getInt(1);
				int groupId2 = rs.getInt(2);
				int typeMsg = rs.getInt(3);
				String time = rs.getString(4);
				String content = rs.getString(5);
				String imageSerialNumber = rs.getString(6);
				int messageSerialNumber = rs.getInt(7);
				MessageTable table = new MessageTable(userId, groupId2, typeMsg,
						time, content, imageSerialNumber, messageSerialNumber);
				Message message = MessageTableOperate.tableToMessage(table);
				messageList.add(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return messageList;
	}

	public static List<Message> queryMessageListByTime(int groupId, String lastTime, int from, int to){
		List<Message> messageList = new ArrayList<Message>();

		Connection connection = DaoConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// user_id,group_id,type_msg,time,content,image_serial_number,message_serial_number
			String sql = "select user_id,group_id,type_msg,time,content,image_serial_number,message_serial_number"
					+ " from "
					+ MessageTable.TABLE_NAME
					+ " where "
					+ MessageTable.GROUP_ID
					+ " = ? and "+MessageTable.TIME+" > ? order by "
					+ MessageTable.ID + " asc limit ?,?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, groupId);
			ps.setString(2, lastTime);
			ps.setInt(3, from);
			ps.setInt(4, to);
			rs = ps.executeQuery();
			while (rs.next()) {
				int userId = rs.getInt(1);
				int groupId2 = rs.getInt(2);
				int typeMsg = rs.getInt(3);
				String time = rs.getString(4);
				String content = rs.getString(5);
				String imageSerialNumber = rs.getString(6);
				int messageSerialNumber = rs.getInt(7);
				MessageTable table = new MessageTable(userId, groupId2, typeMsg,
						time, content, imageSerialNumber, messageSerialNumber);
				Message message = MessageTableOperate.tableToMessage(table);
				messageList.add(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			OperationUtil.closeDataConnection(ps, rs);
		}

		return messageList;
	}
	
	/**
	 * 将Message对象转换成对应的Table对象
	 */
	public static MessageTable messageToTable(Message message) {

		MessageTable table = new MessageTable();

		if (message.getTypeMsg() == Constant.TYPE_MSG_PIC) { // 如果是图片消息类型
			String serialNumber = OperationUtil.getSerialNumber();
			OperationUtil.saveMessageImage(message.getGroupid(), serialNumber,
					message.getPicFile());

			table.setImageSerialNumber(serialNumber);
		} else if (message.getTypeMsg() == Constant.TYPE_MSG_TEXT) {
			table.setContent(message.getMessage());
		}

		table.setGroupId(message.getGroupid());
		table.setTime(message.getTime());
		table.setTypeMsg(message.getTypeMsg());
		table.setUserId(message.getUserid());
		table.setMessageSerialNumber(message.getSerial_number());

		return table;

	}

	/**
	 * 将Table对象转换成Message对象
	 */
	public static Message tableToMessage(MessageTable table) {
		Message message = new Message();

		// 图片消息类型
		if (table.getTypeMsg() == Constant.TYPE_MSG_PIC) {

			byte[] buf = OperationUtil.getMessageImage(table.getGroupId(),
					table.getImageSerialNumber());

			message.setPicFile(buf);
			// 文字消息类型
		} else if (table.getTypeMsg() == Constant.TYPE_MSG_TEXT) {
			message.setMessage(table.getContent());
		}

		message.setGroupid(table.getGroupId());
		message.setType(Constant.TYPE_MSG_SYNC);
		message.setTime(table.getTime());
		message.setTypeMsg(table.getTypeMsg());
		message.setUserid(table.getUserId());
		message.setSerial_number(table.getMessageSerialNumber());

		return message;
	}

}
