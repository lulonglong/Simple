package com.haosimple.action;

import java.sql.SQLException;

import com.haosimple.common.action.BaseAction;
import com.haosimple.dao.UrlOperateDaoImpl;

public class DeleteUrlAction extends BaseAction {
	private UrlOperateDaoImpl urlOperateDaoImpl = new UrlOperateDaoImpl();

	/**
	 * @param urlString
	 * @param header
	 * @param remoteAddr
	 * @throws SQLException
	 */
	public void deleteUrl(String urlString, String userAgent, String userIp)
			throws SQLException {
		urlOperateDaoImpl.deleteUrlOperate(urlString, userAgent, userIp);
	}

}
