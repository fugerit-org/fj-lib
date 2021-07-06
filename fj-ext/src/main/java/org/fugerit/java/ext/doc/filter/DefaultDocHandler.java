package org.fugerit.java.ext.doc.filter;

import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.log.BasicLogObject;
import org.fugerit.java.core.xml.dom.DOMUtils;
import org.w3c.dom.Element;

/*
 * 
 * 
 * @author Matteo a.k.a. Fugerit
 *
 */
public class DefaultDocHandler extends BasicLogObject implements DocHandler {

	private String forward;
	
	
	private String mode;
	
	@Override
	public String getMode() {
		return mode;
	}

	@Override
	public void setMode(String mode) {
		this.mode = mode;
	}

	@Override
	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	private String encoding;
	
	/* (non-Javadoc)
	 * @see net.jsomnium.jlib.mod.doc.filter.DocHandler#handleDoc(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.ServletContext)
	 */
	@Override
	public void handleDoc(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws Exception {
		this.getLogger().info( "handleDoc" );
	}

	@Override
	public void handleDocPost(HttpServletRequest request,HttpServletResponse response, ServletContext context) throws Exception {
		this.getLogger().info( "handleDocPost" );
	}

	@Override
	public String getEncoding() {
		return encoding;
	}

	@Override
	public void init(Element config) throws ConfigException {
		Properties props = DOMUtils.attributesToProperties( config );
		this.encoding = props.getProperty( "encoding", "ISO-8859-15" );
		this.getLogger().info( "init() encofing : "+this.encoding );
		this.setForward( props.getProperty( "forward" ) );
	}
	
	@Override
	public boolean isUseJsp() {
		return useJsp;
	}

	@Override
	public void setUseJsp(boolean useJsp) {
		this.useJsp = useJsp;
	}

	@Override
	public String getJsp() {
		return jsp;
	}

	@Override
	public void setJsp(String jsp) {
		this.jsp = jsp;
	}

	private boolean useJsp;
	
	private String jsp;
	
}
