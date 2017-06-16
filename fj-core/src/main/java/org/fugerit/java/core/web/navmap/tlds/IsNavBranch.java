package org.fugerit.java.core.web.navmap.tlds;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.fugerit.java.core.web.navmap.model.NavEntry;
import org.fugerit.java.core.web.navmap.model.NavMap;
import org.fugerit.java.core.web.tld.helpers.TagSupportHelper;

/*
 * Custom tag to get NavData into request.
 * 
 * 'id' is mandatory (request attribute name where the NavData are stored)
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
public class IsNavBranch extends TagSupportHelper {

	/*
	 * 
	 */
	private static final long serialVersionUID = 2433943997865119114L;
 
	private String checkUrl;
	
	private String branchUrl;

	public String getCheckUrl() {
		return checkUrl;
	}

	public void setCheckUrl(String checkUrl) {
		this.checkUrl = checkUrl;
	}

	public String getBranchUrl() {
		return branchUrl;
	}

	public void setBranchUrl(String branchUrl) {
		this.branchUrl = branchUrl;
	}

	@Override
	public int doStartTag() throws JspException {
		String currentUrl = this.getCheckUrl();
		NavEntry entry = null;
		NavMap map = (NavMap) this.pageContext.getServletContext().getAttribute( NavMap.CONTEXT_ATT_NAME );
		if ( currentUrl == null ) {
			 entry = (NavEntry)(this.pageContext.getSession().getAttribute( NavEntry.SESSION_ATT_NAME ));
		} else {
			entry = map.getEntryByUrl( currentUrl );
		}
		String branchUrl = this.getBranchUrl();
		NavEntry branchEntry = map.getEntryByUrl( branchUrl );
		boolean check = branchEntry.isCurrentBranch( entry );
		String res = String.valueOf( check );
		this.pageContext.setAttribute( this.getId(), res, PageContext.REQUEST_SCOPE );
		return SKIP_BODY;
	}
	
	
	
}
