package org.fugerit.java.core.web.navmap.tlds;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.fugerit.java.core.web.auth.handler.AuthHandler;
import org.fugerit.java.core.web.navmap.model.NavEntry;
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
public class AuthCheck extends TagSupportHelper {

	/*
	 * 
	 */
	private static final long serialVersionUID = 2433943997865119114L;
 
	private String url;
	
	private String resource;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	@Override
	public int doStartTag() throws JspException {
		String resource = this.getResource();
		if ( resource == null ) {
			String currentUrl = this.getUrl();
			NavEntry entry = null;
			NavMap map = (NavMap) this.pageContext.getServletContext().getAttribute( NavMap.CONTEXT_ATT_NAME );
			if ( currentUrl == null ) {
				 entry = (NavEntry)(this.pageContext.getSession().getAttribute( NavEntry.SESSION_ATT_NAME ));
			} else {
				entry = map.getEntryByUrl( currentUrl );
			}
			if ( entry == null ) {
				throw new JspException( "Cannot determine resource to check" );	
			} else {
				resource = entry.getAuth();
			}
		}
		AuthHandler handler = (AuthHandler) this.pageContext.getServletContext().getAttribute( AuthHandler.ATT_NAME );
		if ( handler == null ) {
			throw new JspException( "No auth handler defined" );
		} else {
			HttpServletRequest req = (HttpServletRequest)this.pageContext.getRequest();
			this.pageContext.setAttribute( this.getId() , handler.checkAuth( req , resource ) , PageContext.REQUEST_SCOPE );
		}
		return SKIP_BODY;
	}
	
	
	
}
