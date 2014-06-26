package com.haosimple.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Configuration {

	static Properties prop = new Properties();
	static Logger logger = Logger.getLogger( Configuration.class );

	/**
	 * get value
	 * 
	 * @param key
	 * @return
	 */
	public static String getValueByKey( String key ) {
		String result = prop.getProperty( key );
		if ( result == null )
			logger.warn( "configuration " + key + " is null" );
		else {
			logger.debug( "configuration " + key + " value is  " + result );
		}
		return result;
	}

	/*** 初始化配置信息 ***/
	static {
		
		InputStream inputStream = null;
		
		try {
			inputStream = new FileInputStream( new File( StringUtil.getWebRootPath() + Define.PROPERTY_PATH ) );
			prop.load( inputStream );
		}
		catch ( FileNotFoundException e ) {
			logger.error( "file is not found --" + StringUtil.getExceptionStack( e ) );
		}
		catch ( IOException e ) {
			logger.error( "load file failed ,check file format --" + StringUtil.getExceptionStack( e ) );
		}
		finally {
			try {
				if ( inputStream != null )
					inputStream.close();
			}
			catch ( IOException e ) {
				logger.error("close inputStream occurred error--"+ StringUtil.getExceptionStack( e ));
			}
		}
	}

}
