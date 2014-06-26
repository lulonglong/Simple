package com.haosimple.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.haosimple.common.dao.BaseDao;
import com.sina.sae.util.SaeUserInfo;

public class UrlOperateDaoImpl extends BaseDao{
	static{
		try {
			Class.forName( "com.mysql.jdbc.Driver" );
		}
		catch ( ClassNotFoundException e ) {
			logger.error( "com.mysql.jdbc.Driver not existed" );
		}
	}
	
	private final static String INSERT_URL_SQL="insert tb_url(url) values(?)";
	
	public void insertUrl(String url){
		
	}
	
	public void addUrlOperate(String url,String userAgent,String userIp){
		
	}
	
	public void deleteUrlOperate(String url,String userAgent,String userIp){
		
	}
	
	public void transferUrlOperateTable(){
		
	}
	
	private Connection getConnection() throws SQLException{
		String url="jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_haosimple";
		String username=SaeUserInfo.getAccessKey();
		String password=SaeUserInfo.getSecretKey();
		Connection con=DriverManager.getConnection(url,username,password);
		return con;
	}
	
	private void closeConnection(Connection con){
		try {
			con.close();
		}
		catch ( SQLException e ) {
			logger.error( "con close failed" );
		}
	}
}
