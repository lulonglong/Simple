package com.haosimple.servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.haosimple.action.DeleteUrlAction;
import com.haosimple.action.InsertUrlAction;
import com.haosimple.common.entity.vo.CommonResultVO;
import com.haosimple.common.servlet.BaseServlet;
import com.haosimple.common.util.StringUtil;

/**
 * Servlet implementation class AddUrlServlet
 */

public class DeleteUrlServlet extends BaseServlet {

	/** */
	private static final long serialVersionUID = 1515204880936343477L;
	private InsertUrlAction insertUrlAction = new InsertUrlAction();
	private DeleteUrlAction deleteUrlAction = new DeleteUrlAction();

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		CommonResultVO vo = new CommonResultVO();
		String urlString = req.getParameter("url");

		if (StringUtil.isNullOrWhiteSpace(urlString)) {
			vo.setErrorCode("020001");
			return vo.toJsonString();
		}

		try {
			insertUrlAction.insertUrl(urlString);
			deleteUrlAction.deleteUrl(urlString, req.getHeader("user-agent"),
					req.getRemoteAddr());
		} catch (SQLException e) {
			logger.error(StringUtil.getExceptionStack(e));
			vo.setErrorCode("020002");
		} catch (Exception e) {
			logger.error(StringUtil.getExceptionStack(e));
			vo.setErrorCode("020003");
		}

		return vo.toJsonString();
	}

}
