/**
 *  Copyright(C) 2012 Suntec Software(Shanghai) Co., Ltd.
 *  All Right Reserved.
 */
package com.haosimple.common.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.TreeMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class PrintRequestInfoFilter implements Filter {

	private static Logger logger = Logger.getLogger( PrintRequestInfoFilter.class );

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		final String FUNCTION_NAME = "doFilter() ";
		logger.info(FUNCTION_NAME + "----- 【Request信息开始】 -----");
		logger.info("------【req编码】-------"+request.getCharacterEncoding());
		if (request instanceof HttpServletRequest) {

			HttpServletRequest req = (HttpServletRequest) request;

			logger.info(req.getRequestURI());

			/************* Request Head信息 *************/
			Enumeration<String> headerKeys = req.getHeaderNames();

			TreeMap<String, String> headMap = new TreeMap<String, String>();
			while (headerKeys.hasMoreElements()) {

				String key = headerKeys.nextElement();
				headMap.put(key, req.getHeader(key));
			}

			logger.info(FUNCTION_NAME + headMap);
		}

		logger.info(FUNCTION_NAME + "----- 【Request信息结束】 -----");
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig fc) throws ServletException {

	}

}