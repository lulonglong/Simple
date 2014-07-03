package com.haosimple.common.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sina.sae.util.SaeUserInfo;

public class DBUtil {
	private static ComboPooledDataSource ds;
	/**
	 * 初始化连接池代码块
	 */
	static {
		try {
			initDBSource();
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 初始化c3p0连接池
	 * 
	 * @throws PropertyVetoException
	 */
	private static final void initDBSource() throws PropertyVetoException {
		ds = new ComboPooledDataSource();
		String jdbcURL = Configuration.getValueByKey("dbconnection");// "jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_haosimple";
		String username = Configuration.getValueByKey("dbusername") == null ? SaeUserInfo
				.getAccessKey() : Configuration.getValueByKey("dbusername");// SaeUserInfo.getAccessKey();
		String password = Configuration.getValueByKey("dbpassword") == null ? SaeUserInfo
				.getSecretKey() : Configuration.getValueByKey("dbpassword");// SaeUserInfo.getSecretKey();

		ds.setDriverClass("com.mysql.jdbc.Driver");
		ds.setJdbcUrl(jdbcURL);
		ds.setUser(username);
		ds.setPassword(password);
		ds.setMinPoolSize(2);
		ds.setMaxPoolSize(60);
		ds.setMaxStatementsPerConnection(6);
		ds.setMaxStatements(100);
		//ds.setAutomaticTestTable("tb_c3p0_test");
		//ds.setIdleConnectionTestPeriod(8);
		ds.setMaxIdleTime(60*5);
	}

	/**
	 * 获取数据库连接对象
	 * 
	 * @return 数据连接对象
	 * @throws SQLException
	 */
	public static synchronized Connection getConnection() throws SQLException {
		final Connection conn = ds.getConnection();
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		return conn;
	}
}
