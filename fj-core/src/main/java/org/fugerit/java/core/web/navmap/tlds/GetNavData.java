package org.fugerit.java.core.web.navmap.tlds;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.fugerit.java.core.web.navmap.model.NavEntry;
import org.fugerit.java.core.web.navmap.model.NavEntryI;
import org.fugerit.java.core.web.navmap.model.NavMap;
import org.fugerit.java.core.web.navmap.servlet.NavData;
import org.fugerit.java.core.web.tld.helpers.TagSupportHelper;

/**
 * Custom tag to get NavData into request.
 * 
 * 'id' is mandatory (request attribute name where the NavData are stored)
 * 'url' is optional (if not defined current url in RequestUrl is looked for)
 * 
 * Version 1.1 (2018-01-15)
 * 
 * @author Fugerit
 * 
 * @see org.fugerit.java.core.web.navmap.model.NavMap
 * @see org.fugerit.java.core.web.navmap.servlet.NavData
 *
 */
public class GetNavData extends TagSupportHelper {

	/*
	 * 
	 */
	private static final long serialVersionUID = 2433943997865119114L;
 
	private String url;
	
	private String id;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int doStartTag() throws JspException {
		String currentUrl = this.getUrl();
		NavEntryI entry = null;
		NavMap map = (NavMap) this.pageContext.getServletContext().getAttribute( NavMap.CONTEXT_ATT_NAME );
		if ( currentUrl == null ) {
			 entry = (NavEntryI)(this.pageContext.getSession().getAttribute( NavEntry.SESSION_ATT_NAME ));
		} else {
			entry = map.getEntryByUrl( currentUrl );
		}
		if ( entry == null ) {
			throw new JspException( "Cannot find NavEntry id:"+this.getId()+", url:"+this.getUrl() );
		} else {
			NavData navData = new NavData( entry, map );
			this.pageContext.setAttribute( this.getId() , navData , PageContext.REQUEST_SCOPE );
		}
		return SKIP_BODY;
	}
	
	
	
}
