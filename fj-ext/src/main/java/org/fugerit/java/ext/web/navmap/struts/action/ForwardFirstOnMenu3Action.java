package org.fugerit.java.ext.web.navmap.struts.action;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.fugerit.java.core.web.navmap.helper.MenuResolverHelper;
import org.fugerit.java.core.web.navmap.model.NavEntryI;

public class ForwardFirstOnMenu3Action extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		NavEntryI entry = MenuResolverHelper.getFirstAuthOnMenu3( request );
		if ( entry != null && entry.getUrl() != null ) {
			RequestDispatcher rd = request.getRequestDispatcher( entry.getUrl() );
			rd.forward( request, response );
		}
		return null;
	}

}
