package com.haosimple.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haosimple.action.AddBreakAction;
import com.haosimple.common.entity.vo.CommonResultVO;
import com.haosimple.common.servlet.BaseServlet;
import com.haosimple.common.util.Configuration;
import com.haosimple.common.util.StringUtil;

/**
 * Servlet implementation class AddBreakServlet
 */

public class AddBreakServlet extends BaseServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6441215785839794515L;
	private AddBreakAction addBreakAction = new AddBreakAction();

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res)
			throws IOException {

		CommonResultVO vo = new CommonResultVO();
		String url = req.getParameter("url");
		logger.debug("url: " + url);

		String password = req.getParameter("password");
		if (StringUtil.isNullOrWhiteSpace(url)
				|| StringUtil.isNullOrWhiteSpace(password)
				|| !password.equals(Configuration
						.getValueByKey("breakpassword"))) {
			vo.setErrorCode("040001");
			return vo.toJsonString();
		}

		try {
			addBreakAction.addUrl(url);
		} catch (SQLException e) {
			logger.error(StringUtil.getExceptionStack(e));
			vo.setErrorCode("040002");
		} catch (Exception e) {
			logger.error(StringUtil.getExceptionStack(e));
			vo.setErrorCode("040003");
		}

		return vo.toJsonString();
	}

}
