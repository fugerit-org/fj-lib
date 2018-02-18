package org.fugerit.java.core.web.navmap.tlds;

import javax.servlet.jsp.JspException;

import org.fugerit.java.core.web.navmap.model.NavEntryI;
import org.fugerit.java.core.web.navmap.model.NavMap;
import org.fugerit.java.core.web.navmap.servlet.NavData;

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
public class GetNavData extends NavTagHelper {

	/*
	 * 
	 */
	private static final long serialVersionUID = 2433943997865119114L;

	@Override
	public int doStartTag() throws JspException {
		NavEntryI entry = this.resolveEntry();
		NavMap map = this.resolveNavMap();
		if ( entry == null ) {
			throw new JspException( "Cannot find NavEntry id:"+this.getId()+", url:"+this.getUrl() );
		} else {
			NavData navData = new NavData( entry, map );
			this.overrideCurrentEntry( entry );
			this.setAttribute( this.getId() , navData, this.getToScope() );
		}
		return SKIP_BODY;
	}
	
	
	
}
