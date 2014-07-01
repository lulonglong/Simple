package com.haosimple.servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.haosimple.action.AddUrlAction;
import com.haosimple.action.InsertUrlAction;
import com.haosimple.common.entity.vo.CommonResultVO;
import com.haosimple.common.servlet.BaseServlet;
import com.haosimple.common.util.StringUtil;

/**
 * Servlet implementation class AddUrlServlet
 */

public class AddUrlServlet extends BaseServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8744589605023060759L;
	private InsertUrlAction insertUrlAction = new InsertUrlAction();
	private AddUrlAction addUrlAction = new AddUrlAction();

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		
		CommonResultVO vo = new CommonResultVO();
		String urlString = req.getParameter("url");

		if (StringUtil.isNullOrWhiteSpace(urlString)) {
			vo.setErrorCode("010001");
			return vo.toJsonString();
		}

		try {
			insertUrlAction.insertUrl(urlString);
			addUrlAction.addUrl(urlString, req.getHeader("user-agent"),
					req.getRemoteAddr());
		} catch (SQLException e) {
			logger.error(StringUtil.getExceptionStack(e));
			vo.setErrorCode("010002");
		} catch (Exception e) {
			logger.error(StringUtil.getExceptionStack(e));
			vo.setErrorCode("010003");
		}

		return vo.toJsonString();
	}

}
