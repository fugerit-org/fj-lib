package org.fugerit.java.core.web.navmap.tlds;

import javax.servlet.jsp.JspException;

import org.fugerit.java.core.lang.helpers.BooleanUtils;
import org.fugerit.java.core.web.navmap.model.NavEntry;
import org.fugerit.java.core.web.navmap.model.NavEntryI;
import org.fugerit.java.core.web.navmap.model.NavMap;
import org.fugerit.java.core.web.tld.helpers.TagSupportHelper;

public class NavTagHelper extends TagSupportHelper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1107531413908981833L;
	
	private String url;
	
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	protected NavMap resolveNavMap() {
		return (NavMap) this.pageContext.getServletContext().getAttribute( NavMap.CONTEXT_ATT_NAME );
	}
	
	protected NavEntryI resolveEntry() throws JspException {
		NavEntryI entry = null;
		NavMap map = this.resolveNavMap();
		if ( this.getUrl() != null ) {
			entry = map.getEntryByUrl( url );
		} else if ( this.getName() != null ) {
			entry = (NavEntryI) this.findDefaultObject( null );
		} else {
			entry = (NavEntryI) this.pageContext.findAttribute( NavEntry.SESSION_ATT_NAME );
		}
		return entry;
	}
	
	protected void overrideCurrentEntry( NavEntryI entry ) {
		if ( entry != null && BooleanUtils.isTrue( this.getSetCurrentEntry() ) ) {
			this.setAttribute( NavEntryI.SESSION_ATT_NAME , entry, SCOPE_SESSION );
		}
	}
	
	private String setCurrentEntry;

	public String getSetCurrentEntry() {
		return setCurrentEntry;
	}

	public void setSetCurrentEntry(String setCurrentEntry) {
		this.setCurrentEntry = setCurrentEntry;
	}
	
}
