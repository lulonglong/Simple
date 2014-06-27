package com.haosimple.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.haosimple.common.util.Define;
import com.haosimple.common.util.StringUtil;

/**
 * Descriptions
 *
 * @version 2014-6-26
 * @author long
 * @since JDK1.6
 *
 */
public class ContextInitListener implements ServletContextListener {

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed( ServletContextEvent arg0 ) {
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized( ServletContextEvent arg0 ) {
		String prefix = arg0.getServletContext().getRealPath("/");
		StringUtil.setWebRootPath(prefix);
		PropertyConfigurator.configure( prefix+Define.LOG4J_PATH );
		
		//
		Logger logger=Logger.getLogger( this.getClass() );
		try {
			Class.forName( "com.mysql.jdbc.Driver" );
			logger.info( "init com.mysql.jdbc.Driver successfully" );
		}
		catch ( ClassNotFoundException e ) {
			logger.error( "com.mysql.jdbc.Driver not existed" );
		}
	}

}
