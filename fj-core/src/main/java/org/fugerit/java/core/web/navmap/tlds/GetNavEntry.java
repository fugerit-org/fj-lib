package org.fugerit.java.core.web.navmap.tlds;

import javax.servlet.jsp.JspException;

import org.fugerit.java.core.web.navmap.model.NavEntryI;

public class GetNavEntry extends NavTagHelper {

	/*
	 * 
	 */
	private static final long serialVersionUID = 2433943453865119114L;

	@Override
	public int doStartTag() throws JspException {
		NavEntryI entry = this.resolveEntry();
		this.overrideCurrentEntry( entry );
		this.setAttribute( this.getId(), entry, this.getToScope() );
		return SKIP_BODY;
	}

}
