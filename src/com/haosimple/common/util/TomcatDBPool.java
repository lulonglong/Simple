package com.haosimple.common.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

public class TomcatDBPool {

	private static Logger logger = Logger.getLogger(TomcatDBPool.class);

	private static DataSource pool;

	static {
		try {
			Context env = (Context) new InitialContext()
					.lookup("java:comp/env");
			pool = (DataSource) env.lookup("jdbc/SimpleDB");

			if (pool == null)
				logger.error("SimpleDB is an unknown DataSource");

		} catch (NamingException e) {
			logger.error("init tomcatpool occurs error： "
					+ StringUtil.getExceptionStack(e));
		}
	}

	public static DataSource getPool() {
		return pool;
	}

}
