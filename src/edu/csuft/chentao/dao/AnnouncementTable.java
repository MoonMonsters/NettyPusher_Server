/**
 * 
 */
package edu.csuft.chentao.dao;

/**
 * @author csuft.chentao
 *
 * 2017年4月16日 下午3:52:53
 */
/**
 * Announcement数据库，用来存储所有的公告数据
 */
public class AnnouncementTable {

	public static final String TABLENAME = "announcement";
	public static final String TABLE_ALL_FIELD = "announcement(serialnumber,title,content,username,time,userid,groupid)";
	public static final String ID = "id";
	public static final String SERIALNUMBER = "serialnumber";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String USERNAME = "username";
	public static final String TIME = "time";
	public static final String USERID = "userid";
	public static final String GROUPID = "groupid";

	/**
	 * 插入数据库的编号
	 */
	private int id;
	/**
	 * 编号，预留，如果以后做删除操作用的着
	 */
	private String serialnumber;

	/**
	 * 公告标题
	 */
	private String title;

	/**
	 * 公告内容
	 */
	private String content;

	/**
	 * 发布者名字，用作预备名字，如果根据用户id在用户数据库中没有搜索到，则使用该名字
	 */
	private String username;

	/**
	 * 发布者用户id
	 */
	private int userid;

	/**
	 * 群id
	 */
	private int groupid;
	/**
     * 发布时间
     */
    private String time;
    
	public AnnouncementTable() {
		super();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the serialnumber
	 */
	public String getSerialnumber() {
		return serialnumber;
	}

	/**
	 * @param serialnumber
	 *            the serialnumber to set
	 */
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the userid
	 */
	public int getUserid() {
		return userid;
	}

	/**
	 * @param userid
	 *            the userid to set
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}

	/**
	 * @return the groupid
	 */
	public int getGroupid() {
		return groupid;
	}

	/**
	 * @param groupid
	 *            the groupid to set
	 */
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	/**
	 * @param id
	 * @param serialnumber
	 * @param title
	 * @param content
	 * @param username
	 * @param userid
	 * @param groupid
	 * @param time
	 */
	public AnnouncementTable(int id, String serialnumber, String title,
			String content, String username, int userid, int groupid,
			String time) {
		super();
		this.id = id;
		this.serialnumber = serialnumber;
		this.title = title;
		this.content = content;
		this.username = username;
		this.userid = userid;
		this.groupid = groupid;
		this.time = time;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

}
