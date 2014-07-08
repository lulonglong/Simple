package com.haosimple.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.haosimple.common.dao.BaseDao;
import com.haosimple.common.util.TomcatDBPool;

public class BreakDaoImpl extends BaseDao {

	private final static String INSERT_BREAK_URL_SQL = "insert tb_break_url(url) values(?)";
	private final static String SELECT_BREAK_URL_SQL = "select url from tb_break_url ORDER BY createTime desc limit 1";

	// 添加文章url
	public void insertUrl(String url) throws SQLException {
		Connection connection = null;

		try {
			connection = TomcatDBPool.getPool().getConnection();
			PreparedStatement preparedStatement = connection
					.prepareStatement(INSERT_BREAK_URL_SQL);
			preparedStatement.setString(1, url);
			preparedStatement.execute();

		} finally {
			closeConnection(connection);
		}
	}

	// 获取最新文章的url
	public String getUrl() throws SQLException {
		Connection connection = null;
		String urlString = null;

		try {
			connection = TomcatDBPool.getPool().getConnection();
			PreparedStatement preparedStatement = connection
					.prepareStatement(SELECT_BREAK_URL_SQL);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			urlString = resultSet.getString(1);

		} finally {
			closeConnection(connection);
		}

		logger.info(urlString);
		return urlString;
	}

	private void closeConnection(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			logger.error("con close failed");
		}
	}
}
