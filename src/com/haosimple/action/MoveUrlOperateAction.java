package com.haosimple.action;

import java.sql.SQLException;
import java.sql.Timestamp;

import com.haosimple.common.action.BaseAction;
import com.haosimple.dao.UrlOperateDaoImpl;

public class MoveUrlOperateAction extends BaseAction {

	/**
	 * @param urlString
	 * @throws SQLException 
	 */
	public void move(Timestamp date, int singleMaxCount ) throws SQLException{
			new UrlOperateDaoImpl().moveData( date, singleMaxCount );
	}
}
