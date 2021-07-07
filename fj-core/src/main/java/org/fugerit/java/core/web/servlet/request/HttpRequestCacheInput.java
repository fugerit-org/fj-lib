package org.fugerit.java.core.web.servlet.request;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.fugerit.java.core.io.StreamIO;

public class HttpRequestCacheInput extends HttpServletRequestWrapper {

	private byte[] buffer;
	
	private HttpServletRequest wrapped;
	
	public HttpRequestCacheInput(HttpServletRequest request) throws IOException {
		super(request);
		this.wrapped = request;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		StreamIO.pipeStream( request.getInputStream() , baos, StreamIO.MODE_CLOSE_BOTH );
		buffer = baos.toByteArray();
	}

	public byte[] getBuffer() {
		return buffer;
	}

	public HttpServletRequest getWrapped() {
		return wrapped;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return new ServletInputStream() {
			private final InputStream is = new ByteArrayInputStream( buffer );
			@Override
			public int read() throws IOException {
				return is.read();
			}
			@Override
			public int read(byte[] b, int off, int len) throws IOException {
				return is.read(b, off, len);
			}
		};
	}
	
}
