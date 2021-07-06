package org.fugerit.java.ext.doc.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.fugerit.java.core.log.LogFacade;

/*
 * 
 * 
 * @author Matteo a.k.a. Fugerit
 *
 */
public class HttpServletResponsePush extends HttpServletResponseWrapper  {

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponseWrapper#getOutputStream()
	 */
	public ServletOutputStream getOriginalOutputStream() throws IOException {
		return super.getOutputStream();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponseWrapper#getWriter()
	 */
	public PrintWriter getOriginalWriter() throws IOException {
		return super.getWriter();
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponseWrapper#getOutputStream()
	 */
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return new DataServletOutputStream( new ByteArrayOutputStream() );
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponseWrapper#getWriter()
	 */
	@Override
	public PrintWriter getWriter() throws IOException {
		return new PrintWriter( this.getOutputStream() );
	}

	public HttpServletResponsePush(HttpServletResponse response) {
		super(response);
	}
	
	public void flush() throws IOException {
	}

	@Override
	public void setContentType(String type) {
		LogFacade.getLog().debug( "HttpServletResponseByteData.setContentType() do nothing : operation not allowed here" );
	}
		
}
