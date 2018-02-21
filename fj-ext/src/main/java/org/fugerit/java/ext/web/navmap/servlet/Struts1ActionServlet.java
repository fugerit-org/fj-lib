package org.fugerit.java.ext.web.navmap.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionServlet;
import org.fugerit.java.core.web.navmap.model.NavMap;
import org.fugerit.java.core.web.navmap.servlet.NavFacade;
import org.fugerit.java.core.web.servlet.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Struts1ActionServlet extends ActionServlet {

	private static Logger logger = LoggerFactory.getLogger( Struts1ActionServlet.class );
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 571684758099138154L;

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String reqId = String.valueOf(  request.getSession( true ).getId()+"/"+System.currentTimeMillis() );
		NavMap navMap = (NavMap) this.getServletContext().getAttribute( NavMap.CONTEXT_ATT_NAME );
		logger.info( "process() "+request.getRequestURL() );
		if ( navMap != null ) {
			try {
				RequestContext requestContext = RequestContext.getRequestContext( this.getServletContext() , request, response);
				NavFacade.nav( requestContext, navMap, reqId);
			} catch (Exception e) {
				logger.error( "Error in struts process "+request.getRequestURL(), e );
				throw new ServletException( e );
			}
		}
		super.process(request, response);		
	}
	
}
