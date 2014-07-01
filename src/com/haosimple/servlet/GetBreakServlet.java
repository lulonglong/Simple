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

	/**
	 * 
	 */
	private static final long serialVersionUID = -8941071840145155189L;
	private GetBreakAction getBreakAction = new GetBreakAction();

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		BreakResultVO vo = new BreakResultVO();

		try {
			vo.setUrl(getBreakAction.getUrl());
		} catch (SQLException e) {
			logger.error(StringUtil.getExceptionStack(e));
			vo.setErrorCode("050001");
		} catch (Exception e) {
			logger.error(StringUtil.getExceptionStack(e));
			vo.setErrorCode("050002");
		}

		return vo.toJsonString();
	}

}
