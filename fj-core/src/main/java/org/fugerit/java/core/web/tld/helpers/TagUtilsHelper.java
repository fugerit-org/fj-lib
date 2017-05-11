package org.fugerit.java.core.web.tld.helpers;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang3.StringUtils;


/*

 * @author Fugerit
 */
public class TagUtilsHelper {

	public final static String SCOPE_CONTEXT = "context";

	public final static String SCOPE_SESSION = "session";

	public final static String SCOPE_REQUEST = "request";

	public final static String SCOPE_PAGE = "page";

	
	public static void setAttibute( PageContext pageContext, String scope, String name, Object value ) throws JspException {
		scope = StringUtils.defaultString( scope, SCOPE_PAGE );
		if ( scope.equalsIgnoreCase( SCOPE_PAGE ) ) {
			pageContext.setAttribute( name, value );
		} else if ( scope.equalsIgnoreCase( SCOPE_REQUEST ) ) {
			pageContext.getRequest().setAttribute( name, value );
		} else if ( scope.equalsIgnoreCase( SCOPE_SESSION ) ) {
			((HttpServletRequest)pageContext.getRequest()).setAttribute( name, value );
		} else if ( scope.equalsIgnoreCase( SCOPE_CONTEXT ) ) {
			pageContext.getServletContext().setAttribute( name, value );
		}		
	}	


	public static Object findAttibute( PageContext pageContext, String scope, String name, String property ) throws JspException {		
		Object result = null;
		if ( scope == null ) {
			result = pageContext.findAttribute( name );	
		} else if ( scope.equalsIgnoreCase( SCOPE_PAGE ) ) {
			result = pageContext.getAttribute( name );
		} else if ( scope.equalsIgnoreCase( SCOPE_REQUEST ) ) {
			result = pageContext.getRequest().getAttribute( name );
		} else if ( scope.equalsIgnoreCase( SCOPE_SESSION ) ) {
			result = ((HttpServletRequest)pageContext.getRequest()).getSession().getAttribute( name );
		} else if ( scope.equalsIgnoreCase( SCOPE_CONTEXT ) ) {
			result = pageContext.getServletContext().getAttribute( name );
		}
		if ( property != null ) {
			try {
				Class[] c = new Class[0];
				Object[] o = new Object[0];
				Method method = result.getClass().getMethod( "get"+property.substring( 0, 1 ).toUpperCase()+property.substring( 1 ), c );
				result = method.invoke( result, o );
			} catch (Exception e) {
				throw ( new JspException( e ) );
			}
		}
		return result;
	}

}
