package org.fugerit.java.core.web.navmap.tlds;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.fugerit.java.core.web.auth.handler.AuthHandler;
import org.fugerit.java.core.web.navmap.model.NavEntry;
import org.fugerit.java.core.web.navmap.model.NavEntryI;
import org.fugerit.java.core.web.navmap.model.NavMap;
import org.fugerit.java.core.web.tld.helpers.TagSupportHelper;

/*
 * Use auth handler to check a authorization for a resource
 * 
 * 'id' is mandatory (request attribute name where the NavData are stored)
 * 'resource' is optiona (the resource to check)
 * 'url' is optional (if not defined current url in RequestUrl is looked for)
 * 
 * Version 1.0 (2016-12-02)
 * 
 * @author Fugerit
 * 
 * @see org.fugerit.java.core.web.navmap.model.NavMap
 * @see org.fugerit.java.core.web.navmap.servlet.NavData
 *
 */
public class RenderUrl extends TagSupportHelper {

	/*
	 * 
	 */
	private static final long serialVersionUID = 2433943997865119114L;
 
	private String url;
	
	private String paramMap;
	
	private String paramId;
	
	private String paramValue;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParamMap() {
		return paramMap;
	}

	public void setParamMap(String paramMap) {
		this.paramMap = paramMap;
	}

	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	@Override
	public int doStartTag() throws JspException {
		NavEntryI entry = null;
		NavMap map = (NavMap) this.pageContext.getServletContext().getAttribute( NavMap.CONTEXT_ATT_NAME );
		if ( this.getUrl() != null ) {
			entry = map.getEntryByUrl( this.getUrl() );
		} else {
			entry = (NavEntryI)(this.pageContext.getSession().getAttribute( NavEntry.SESSION_ATT_NAME ));
		}
		if ( entry == null ) {
			throw new JspException( "No entry found" );
		}
		StringBuffer render = new StringBuffer();
		render.append( entry.getUrl() );
		if ( this.getParamId() != null && this.getParamValue() != null ) {
			render.append( "?" );
			render.append( this.getParamId() );
			render.append( "=" );
			render.append( this.getParamValue() );
		}
		if ( this.getId() != null ) {
			this.pageContext.setAttribute( this.getId() , render.toString() , PageContext.REQUEST_SCOPE );
		} else {
			super.print( render.toString() );
		}
		return SKIP_BODY;
	}
	
	
	
}
