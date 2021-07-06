package org.fugerit.java.core.web.servlet.response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.fugerit.java.core.log.LogFacade;

public class HttpServletResponseByteData extends HttpServletResponseWrapper  {

	private PrintWriter writer = null;
	
	private ServletOutputStream stream = null;
	
	private ByteArrayOutputStream baos;

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
		this.stream = new DataServletOutputStream( baos );
		return this.stream;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletResponseWrapper#getWriter()
	 */
	@Override
	public PrintWriter getWriter() throws IOException {
		this.writer = new PrintWriter( new OutputStreamWriter( this.getOutputStream() ), true );
		return this.writer;
	}

	public HttpServletResponseByteData(HttpServletResponse response) {
		super(response);
		this.baos = new ByteArrayOutputStream();
	}

	public ByteArrayOutputStream getBaos() {
		return baos;
	}
	
	public void flush() throws IOException {
		if ( this.writer != null ) {
			this.writer.flush();
		}
		if ( this.stream != null ) {
			this.stream.flush();
		}
	}

	@Override
	public void setContentType(String type) {
		LogFacade.getLog().info( "HttpServletResponseByteData.setContentType() do nothing : operation not allowed here" );
	}
		
}
