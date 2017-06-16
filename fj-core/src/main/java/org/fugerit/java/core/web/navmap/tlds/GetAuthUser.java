package org.fugerit.java.core.web.navmap.tlds;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.fugerit.java.core.web.auth.model.AuthUser;
import org.fugerit.java.core.web.auth.model.AuthUserUtil;
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
public class GetAuthUser extends TagSupportHelper {

	/*
	 * 
	 */
	private static final long serialVersionUID = 2433943997865119114L;
 
	private String info;

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public int doStartTag() throws JspException {
		AuthUser user = AuthUserUtil.lookForUser( this.pageContext.getSession() );
		Object res = null;
		if ( this.getInfo() != null ) {
			res = user.getUserInfo().get( this.getInfo() );
		} else {
			res = user;
		}
		this.pageContext.setAttribute( this.getId(), res, PageContext.REQUEST_SCOPE );
		return SKIP_BODY;
	}
	
	
	
}
