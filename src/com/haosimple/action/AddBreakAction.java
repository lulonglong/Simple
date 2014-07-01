package com.haosimple.action;

import java.sql.SQLException;

import com.haosimple.common.action.BaseAction;
import com.haosimple.dao.BreakDaoImpl;

public class AddBreakAction extends BaseAction {
	private BreakDaoImpl breakDaoImpl = new BreakDaoImpl();

	public void addUrl(String url) throws SQLException {
		breakDaoImpl.insertUrl(url);
	}

}
