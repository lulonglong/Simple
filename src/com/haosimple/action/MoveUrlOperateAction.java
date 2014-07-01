package com.haosimple.action;

import java.sql.SQLException;
import java.sql.Timestamp;

import com.haosimple.common.action.BaseAction;
import com.haosimple.dao.UrlOperateDaoImpl;

public class MoveUrlOperateAction extends BaseAction {
	private UrlOperateDaoImpl urlOperateDaoImpl = new UrlOperateDaoImpl();

	/**
	 * @param urlString
	 * @throws SQLException
	 */
	public void move(Timestamp date, int singleMaxCount) throws SQLException {
		urlOperateDaoImpl.moveData(date, singleMaxCount);
	}
}
