package com.haosimple.common.dao;

import org.apache.log4j.Logger;

public class BaseDao {

	protected Logger logger;

	public BaseDao() {
		logger = Logger.getLogger( this.getClass() );
	}
}
