package com.haosimple.action;

import java.sql.SQLException;

import com.haosimple.common.action.BaseAction;
import com.haosimple.dao.BreakDaoImpl;

public class AddBreakAction extends BaseAction {

	public void addUrl( String url) throws SQLException {
		new BreakDaoImpl().insertUrl( url );
	}
	
}
