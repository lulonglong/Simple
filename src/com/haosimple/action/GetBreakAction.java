package com.haosimple.action;

import java.sql.SQLException;

import com.haosimple.common.action.BaseAction;
import com.haosimple.dao.BreakDaoImpl;

public class GetBreakAction extends BaseAction {
	private BreakDaoImpl breakDaoImpl = new BreakDaoImpl();

	public String getUrl() throws SQLException {
		return breakDaoImpl.getUrl();
	}

}
