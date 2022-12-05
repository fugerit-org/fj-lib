package org.fugerit.java.core.io.helper;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * This PrintWriter allow to setup custom line separator
 * 
 * @author fugerit
 *
 */
public class CustomPrintWriter extends PrintWriter {
	
	public final static String WINDOWS_LINE_SEPARATOR = "\r\n";
	public final static String DEFAULT_LINE_SEPARATOR = WINDOWS_LINE_SEPARATOR;
	
	private String lineSeparator;
	
	public String getLineSeparator() {
		return lineSeparator;
	}

	public void setLineSeparator(String lineSeparator) {
		this.lineSeparator = lineSeparator;
	}

	protected boolean autoFlush = false;

	public CustomPrintWriter(final Writer out, String lineSeparator) {
		super(out);
		this.lineSeparator = lineSeparator;
	}

	public CustomPrintWriter(final Writer out, final boolean autoFlush, String lineSeparator) {
		super(out, autoFlush);
		this.autoFlush = autoFlush;
		this.lineSeparator = lineSeparator;
	}

	public CustomPrintWriter(final OutputStream out, String lineSeparator) {
		super(out);
		this.lineSeparator = lineSeparator;
	}

	public CustomPrintWriter(final OutputStream out, final boolean autoFlush, String lineSeparator) {
		super(out, autoFlush);
		this.autoFlush = autoFlush;
		this.lineSeparator = lineSeparator;
	}
	
	public CustomPrintWriter(final Writer out) {
		this( out, DEFAULT_LINE_SEPARATOR );
	}

	public CustomPrintWriter(final Writer out, final boolean autoFlush) {
		this( out, autoFlush, DEFAULT_LINE_SEPARATOR );
	}

	public CustomPrintWriter(final OutputStream out) {
		this( out, DEFAULT_LINE_SEPARATOR );
	}

	public CustomPrintWriter(final OutputStream out, final boolean autoFlush) {
		this( out, autoFlush, DEFAULT_LINE_SEPARATOR );
	}

	protected void ensureOpen() throws IOException {
		if (out == null)
			throw new IOException("Stream closed");
	}

	@Override
	public void println() {
		try {
			synchronized (lock) {
				ensureOpen();
				out.write( DEFAULT_LINE_SEPARATOR );
				if (autoFlush) {
					out.flush();
				}
			}
		} catch (InterruptedIOException e) {
			Thread.currentThread().interrupt();
		} catch (IOException e) {
			setError();
		}
	}

}
