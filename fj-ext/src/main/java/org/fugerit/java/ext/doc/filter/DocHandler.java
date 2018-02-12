package org.fugerit.java.ext.doc.filter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fugerit.java.core.cfg.ConfigException;
import org.w3c.dom.Element;


/*
 * 
 * 
 * @author Matteo a.k.a. Fugerit
 *
 */
public interface DocHandler {

	public final static String MODE_DIRECT = "direct";
	public final static String MODE_JSP = "jsp";
	
	public final static String ATT_NAME_DOC = "org.fugerit.java.ext.doc.DocBase.ATT_NAME";
	
	public void handleDoc( HttpServletRequest request, HttpServletResponse response, ServletContext context ) throws Exception;
	
	public void handleDocPost( HttpServletRequest request, HttpServletResponse response, ServletContext context ) throws Exception;
	
	public void init( Element config ) throws ConfigException;
	
	public String getEncoding();

	public String getForward();
	
	public boolean isUseJsp();

	public void setUseJsp(boolean useJsp);

	public String getJsp();

	public void setJsp(String jsp);
	
	public String getMode();
	
	public void setMode(String mode);
}
