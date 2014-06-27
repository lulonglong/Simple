package com.haosimple.common.servlet;

import java.io.DataOutputStream;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import com.haosimple.common.util.StringUtil;

public abstract class BaseServlet extends HttpServlet {
	/** */
	private static final long serialVersionUID = 4660836483106903265L;

	public static final String CONTENT_TYPE_JSON_UTF8 = "text/plain; charset=UTF-8";

	protected Logger logger;

	public BaseServlet() {
		logger = Logger.getLogger( this.getClass() );
	}

	@Override
	public void doGet( HttpServletRequest req, HttpServletResponse res ) throws IOException {
		res.sendError( HttpServletResponse.SC_FORBIDDEN );
		//doPost( req, res );
	}

	@Override
	public void doPost( HttpServletRequest req, HttpServletResponse res ) throws IOException {
		String result = null;

		try {
			req.setCharacterEncoding( "utf-8" );
			result = execute( req, res );
		}
		catch ( Exception e ) {
			logger.error( StringUtil.getExceptionStack( e ) );
		}
		finally {
			if ( !StringUtil.isNotNull( result ) ) {
				result = "";
			}
			response( res, result.getBytes( "UTF-8" ) );
		}
	}

	public abstract String execute( HttpServletRequest req, HttpServletResponse res ) throws IOException;

	private void response( HttpServletResponse res, byte[] body ) throws IOException {

		res.addHeader( "Pragma", "no-cache" );
		res.addHeader( "Cache-Control", "no-cache" );
		res.setContentType( CONTENT_TYPE_JSON_UTF8 );
		res.setCharacterEncoding( "UTF-8" );
		DataOutputStream out = new DataOutputStream( res.getOutputStream() );
		out.write( body );
		out.close();
	}

}
