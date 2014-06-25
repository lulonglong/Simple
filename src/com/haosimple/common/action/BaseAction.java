package com.haosimple.common.action;

import org.apache.log4j.Logger;

public class BaseAction {

	protected Logger logger;

	public BaseAction() {
		logger = Logger.getLogger( this.getClass() );
	}
}
