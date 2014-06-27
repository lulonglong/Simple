package com.haosimple.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.haosimple.common.dao.BaseDao;
import com.haosimple.common.util.Configuration;
import com.sina.sae.util.SaeUserInfo;

public class BreakDaoImpl extends BaseDao {

	private final static String INSERT_BREAK_URL_SQL = "insert tb_break_url(url) values(?)";
	private final static String SELECT_BREAK_URL_SQL = "select url from tb_break_url ORDER BY createTime desc limit 1";

	// 添加文章url
	public void insertUrl( String url ) throws SQLException {
		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement( INSERT_BREAK_URL_SQL );
		preparedStatement.setString( 1, url );
		preparedStatement.execute();
		closeConnection( connection );
	}
	
	// 获取最新文章的url
	public String getUrl() throws SQLException{
		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement( SELECT_BREAK_URL_SQL );
		ResultSet resultSet=preparedStatement.executeQuery();
		resultSet.next();
		String urlString=resultSet.getString( 1 );
		closeConnection( connection );
		logger.info( urlString );
		return urlString;
	}

	private Connection getConnection() throws SQLException {
		String url = Configuration.getValueByKey( "dbconnection" );// "jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_haosimple";
		String username = Configuration.getValueByKey( "dbusername" ) == null ? SaeUserInfo.getAccessKey()
				: Configuration.getValueByKey( "dbusername" );// SaeUserInfo.getAccessKey();
		String password = Configuration.getValueByKey( "dbpassword" ) == null ? SaeUserInfo.getSecretKey()
				: Configuration.getValueByKey( "dbpassword" );// SaeUserInfo.getSecretKey();
		logger.info( "url: "+url+" username: "+username+" pass "+password );
		Connection conn = DriverManager.getConnection( url, username, password );
		return conn;
	}

	private void closeConnection( Connection conn ) {
		try {
			conn.close();
		}
		catch ( SQLException e ) {
			logger.error( "con close failed" );
		}
	}
}
