package com.haosimple.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haosimple.action.GetBreakAction;
import com.haosimple.common.servlet.BaseServlet;
import com.haosimple.common.util.StringUtil;
import com.haosimple.vo.BreakResultVO;

/**
 * Servlet implementation class GetBreakServlet
 */

public class GetBreakServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	public String execute( HttpServletRequest req, HttpServletResponse res ) throws IOException {
		BreakResultVO vo=new BreakResultVO();
		
		try {
			vo.setUrl(new GetBreakAction().getUrl());
		}
		catch ( SQLException e ) {
			logger.error( StringUtil.getExceptionStack( e ) );
			vo.setErrorCode( "010002" );
		}
		catch (Exception e) {
			logger.error( StringUtil.getExceptionStack( e ) );
			vo.setErrorCode( "010003" );
		}
		
		logger.info( vo.toJsonString() );
		return vo.toJsonString();
	}

}
