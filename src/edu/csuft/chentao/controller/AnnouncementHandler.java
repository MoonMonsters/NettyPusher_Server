/**
 * 
 */
package edu.csuft.chentao.controller;

import java.util.List;
import java.util.Set;

import edu.csuft.chentao.dao.AnnouncementReader;
import edu.csuft.chentao.dao.AnnouncementReaderOperate;
import edu.csuft.chentao.dao.AnnouncementTableOperate;
import edu.csuft.chentao.dao.GroupUserTableOperate;
import edu.csuft.chentao.netty.NettyCollections;
import edu.csuft.chentao.pojo.req.Announcement;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author csuft.chentao
 *
 * 2017年4月16日 下午3:38:52
 */
public class AnnouncementHandler implements Handler{

	public void handle(ChannelHandlerContext chc, Object object) {
		//1.获得Announcement对象
		Announcement announcement = (Announcement) object;
		//2.保存到数据库中
		AnnouncementTableOperate.insert(announcement);
		//2.根据群id，得到群中所有用户id
		int groupId = announcement.getGroupid();
		List<Integer> userIdList = GroupUserTableOperate.getAllUserIdWithGroupId(groupId);
		//3.根据用户id，将Announcement对象转发给在线用户
		NettyCollections.traverse(groupId, announcement);
		//4.如果用户不在线，则将所有数据存储到数据库中
		Set<Integer> userIdSet = NettyCollections.getConnectionUerIdList();
		userIdSet.add(announcement.getUserid());
		for(int userId : userIdList){
			if(!userIdSet.contains(userId)){
				AnnouncementReader reader = new AnnouncementReader();
				reader.setUserid(userId);
				reader.setSerialnumber(announcement.getSerialnumber());
				AnnouncementReaderOperate.insert(reader);
			}
		}
	}
}
