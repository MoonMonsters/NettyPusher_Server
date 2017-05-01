/**
 * 
 */
package edu.csuft.chentao.dao;

import edu.csuft.chentao.pojo.req.FileZip;

/**
 * @author csuft.chentao
 *
 * 2017年5月1日 上午9:52:31
 */
public class GroupFileZipTable {
	
	public static final String TABLE_NAME = "group_file_zip";
	public static final String INSERT_ALL_FIELD = "group_file_zip(filename,serial_number,group_id,user_id,nickname,time)";
	public static final String QUERY_ALL_FIELD = "filename,serial_number,group_id,user_id,nickname,time";
	public static final String FILENAME = "filename";
	public static final String NICKNAME = "nickname";
	public static final String SERIAL_NUMBER = "serial_number";
	public static final String USER_ID = "user_id";
	public static final String GROUP_ID = "group_id";
	public static final String TIME = "time";
	
	/**
     * 文件名
     */
    private String fileName;
    /**
     * 文件的序列号,可以用来充当下载序列，根据序列来下载对应的文件，而不是根据文件名
     */
    private String serialNumber;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 文件所在的群id
     */
    private int groupId;
    /**
     * 文件上传时间
     */
    private String time;
	public GroupFileZipTable(String fileName, String serialNumber,
			String nickname, int userId, int groupId, String time) {
		super();
		this.fileName = fileName;
		this.serialNumber = serialNumber;
		this.nickname = nickname;
		this.userId = userId;
		this.groupId = groupId;
		this.time = time;
	}
    
    public GroupFileZipTable(){}
    
    
    
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

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	/**
     * 将FileZip转换成GroupFileZipTable对象，用来处理数据库对象
     * @param fileZip 传输的文件对象
     * @return GroupFileZipTable对象，用来处理数据表
     */
    public static GroupFileZipTable copyToGroupFileZipTable(FileZip fileZip){
    	GroupFileZipTable table = new GroupFileZipTable();
    	
    	table.setFileName(fileZip.getFileName());
    	table.setGroupId(fileZip.getGroupId());
    	table.setNickname(fileZip.getNickname());
    	table.setSerialNumber(fileZip.getSerialNumber());
    	table.setTime(fileZip.getTime());
    	table.setUserId(fileZip.getUserId());
    	
    	return table;
    }
    
    /**
     * 将Table对象和数据结合转换成FileZip对象，用来发送到客户端
     * @param table 数据表对象
     * @return 可发送对象
     */
    public static FileZip copyToFileZip(GroupFileZipTable table,byte[] content, boolean attachContent){
    	FileZip fileZip = new FileZip();
    	
    	fileZip.setFileName(table.getFileName());
    	fileZip.setGroupId(table.getGroupId());
    	fileZip.setNickname(table.getNickname());
    	fileZip.setSerialNumber(table.getSerialNumber());
    	fileZip.setTime(table.getTime());
    	fileZip.setUserId(table.getUserId());
    	if(attachContent){	//如果需要添加文件数据，则加入
    		fileZip.setZip(content);
    	}
    	
    	return fileZip;
    }
}
