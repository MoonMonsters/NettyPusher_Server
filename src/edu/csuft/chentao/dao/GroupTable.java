/**
 * group表相关数据
 */
package edu.csuft.chentao.dao;

/**
 * @author csuft.chentao
 *
 *         2016年12月8日 下午9:19:35
 */
public class GroupTable {

	/** group表名 */
	public static final String GROUPTABLE = "chatgroup";
	public static final String GROUPTABLE_ALL_FIELD = "chatgroup(groupid,groupname,tag,number,groupimage)";
	public static final String ID = "id";
	public static final String GROUPID = "groupid";
	public static final String GROUPNAME = "groupname";
	public static final String TAG = "tag";
	/** 人数 */
	public static final String NUMBER = "number";
	public static final String GROUPIMAGE = "groupimage";

	/** id */
	private int id;
	/** 群id */
	private int groupid;
	/** 群名称 */
	private String groupname;
	/** 群标签 */
	private String tag;
	/** 人数 */
	private int number;
	/** 群头像 */
	private String groupimage;

	public GroupTable(int id, int groupid, String groupname, String tag,
			int number, String groupimage) {
		super();
		this.id = id;
		this.groupid = groupid;
		this.groupname = groupname;
		this.tag = tag;
		this.number = number;
		this.groupimage = groupimage;
	}

	public GroupTable() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getGroupimage() {
		return groupimage;
	}

	public void setGroupimage(String groupimage) {
		this.groupimage = groupimage;
	}

}
