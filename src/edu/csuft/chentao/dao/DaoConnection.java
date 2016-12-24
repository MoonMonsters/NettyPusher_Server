/**
 * 
 */
package edu.csuft.chentao.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import edu.csuft.chentao.util.Logger;

/**
 * @author csuft.chentao
 *
 *         2016年12月8日 下午9:40:34
 */
public class DaoConnection {

	private static Connection connection = null;
	/** 用户名 */
	private static final String USERNAME = "root";
	/** 密码 */
	private static final String PASSWORD = "root";

	private DaoConnection() {
	}

	/** 获得数据库的连接对象 */
	public static Connection getConnection() {

		if (connection == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				// 后面代码，解决数据库数据乱码问题
				String url = "jdbc:mysql://localhost:3306/nettypusher?useUnicode=true&characterEncoding=utf8";
				connection = DriverManager.getConnection(url, USERNAME,
						PASSWORD);
			} catch (Exception e) {
				e.printStackTrace();
				Logger.log("数据库连接错误...");
			}
		}

		return connection;
	}

}
