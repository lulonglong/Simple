/**
 *  Copyright(C) 2012 Suntec Software(Shanghai) Co., Ltd.
 *  All Right Reserved.
 */
package com.haosimple.common.util;

public class StringUtil {
	/**
	 * 判断参数是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotNull( String str ) {
		boolean result = false;
		if ( str != null && str.length() > 0 ) {
			result = true;
		}
		return result;
	}

	/**
	 * 是否为空或者空白
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullOrWhiteSpace( String str ) {
		if ( str == null || str.trim().length() == 0 ) {
			return true;
		}
		return false;
	}


	/**
	 * 获取堆栈信息
	 * 
	 * @param e
	 * @return
	 */
	public static String getExceptionStack( Exception e ) {
		StackTraceElement[] stackTraceElements = e.getStackTrace();
		StringBuilder result = new StringBuilder( e.toString().replace( '\r', ' ' ).replace( '\n', ' ' ) + "@@@" );

		for ( int index = 0; index < stackTraceElements.length; index++ ) {
			result.append( "at " ).append( stackTraceElements[index].getClassName() ).append( "." );
			result.append( stackTraceElements[index].getMethodName() + "(" );
			result.append( stackTraceElements[index].getFileName() + ":" );
			result.append( stackTraceElements[index].getLineNumber() + ")@@@" );
		}

		return result.toString();
	}
}
