package com.haosimple.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

import com.haosimple.common.util.Define;

/**
 * Descriptions
 *
 * @version 2014-6-26
 * @author Suntec
 * @since JDK1.6
 *
 */
public class ContextInitListener implements ServletContextListener {

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed( ServletContextEvent arg0 ) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized( ServletContextEvent arg0 ) {
		String prefix = arg0.getServletContext().getRealPath("/");
		System.out.println(prefix);
		PropertyConfigurator.configure( prefix+Define.LOG4J_PATH );
	}

}
