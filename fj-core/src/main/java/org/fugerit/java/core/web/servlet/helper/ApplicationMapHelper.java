package org.fugerit.java.core.web.servlet.helper;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

public class ApplicationMapHelper {

	public static int getTotalContextMapSize( ServletContext context ) {
		SerializedAttSizeFunction fun = new SerializedAttSizeFunction();
		applyFunctionOnContextAttributes( context , fun );
		return fun.getTotalSize();
	}
	
	public static int getTotalRequestMapSize( ServletRequest request ) {
		SerializedAttSizeFunction fun = new SerializedAttSizeFunction();
		applyFunctionOnRequestAttributes( request , fun );
		return fun.getTotalSize();
	}
	
	public static int getTotalSessionMapSize( HttpSession session ) {
		SerializedAttSizeFunction fun = new SerializedAttSizeFunction();
		applyFunctionOnSessionAttributes( session , fun );
		return fun.getTotalSize();
	}
	
	public static int applyFunctionOnContextAttributes( ServletContext context, AttributeFunction fun ) {
		int res = 0;
		Enumeration<String> keys = context.getAttributeNames();
		while ( keys.hasMoreElements() ) {
			String key = keys.nextElement();
			Object value = context.getAttribute( key );
			if ( fun.apply( key, value ).isResultOk() ) {
				res ++;
			}
		}
		return res;
	}
	
	public static int applyFunctionOnRequestAttributes( ServletRequest request, AttributeFunction fun ) {
		int res = 0;
		Enumeration<String> keys = request.getAttributeNames();
		while ( keys.hasMoreElements() ) {
			String key = keys.nextElement();
			Object value = request.getAttribute( key );
			if ( fun.apply( key, value ).isResultOk() ) {
				res ++;
			}
		}
		return res;
	}
	
	public static int applyFunctionOnSessionAttributes( HttpSession session, AttributeFunction fun ) {
		int res = 0;
		Enumeration<String> keys = session.getAttributeNames();
		while ( keys.hasMoreElements() ) {
			String key = keys.nextElement();
			Object value = session.getAttribute( key );
			if ( fun.apply( key, value ).isResultOk() ) {
				res ++;
			}
		}
		return res;
	}
	
}
