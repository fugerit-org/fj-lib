package org.fugerit.java.core.web.servlet.config;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class SessionContext implements Serializable {

	/*
	 * 
	 */
	private static final long serialVersionUID = -4543097476221921635L;

	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append( this.getClass().getName() );
		b.append( "[ID:"+this.getId()+"," );
		Iterator attNames = this.attributeNames();
		while ( attNames.hasNext() ) {
			String name = (String)attNames.next();
			Object value = this.getAttribute( name );
			String val = "null";
			if ( value != null ) {
				val = value.getClass().getName();
			}
			b.append( name+"="+val+";" );
		}
		b.append( "]" );
		return b.toString();
	}
	
	public String toStringLines() {
		StringBuffer b = new StringBuffer();
		b.append( this.getClass().getName() );
		b.append( "[" );
		Iterator attNames = this.attributeNames();
		while ( attNames.hasNext() ) {
			String name = (String)attNames.next();
			Object value = this.getAttribute( name );
			String val = "null";
			if ( value != null ) {
				val = value.getClass().getName();
			}
			b.append( "\n"+name+"="+val );
		}
		b.append( "]" );
		return b.toString();
	}
	
	public static SessionContext getHttpSessionContext( HttpServletRequest request ) {
		HttpSession session = request.getSession();
		SessionContext context = (SessionContext)session.getAttribute( HttpSessionContext.ATT_NAME );
		if ( context == null ) {
			context = new HttpSessionContext( session );
			//session.setAttribute( HttpSessionContext.ATT_NAME , context );
		}
		return context;
	}
	
	public SessionContext() {
	}
	
	public abstract String getId();
	
	public abstract void removeAttribute( String name );
	
	public abstract Object getAttribute( String name );
	
	public Object getAttribute( String name, Object value ) {
		Object result = this.getAttribute( name );
		if ( result == null ) {
			result = value;
			this.setAttribute( name , value );
		}
		return result;
	}
	
	public abstract void setAttribute( String name, Object value );
	
	public abstract Iterator attributeNames();
	
}


class HttpSessionContext extends SessionContext {

	/*
	 * 
	 */
	private static final long serialVersionUID = -6927502334822765826L;

	private HttpSession session;
	
	public Iterator attributeNames() {
		return Collections.list( this.session.getAttributeNames() ).iterator();
	}

	public Object getAttribute(String name) {
		return this.session.getAttribute( name );
	}

	public void removeAttribute(String name) {
		this.session.removeAttribute( name );
	}

	public void setAttribute(String name, Object value) {
		this.session.setAttribute( name , value );
	}

	public static final String ATT_NAME = "org.fugerit.java.core.web.servlet.config.HttpSessionContext.ATT_NAME";
	
	public HttpSessionContext( HttpSession session ) {
		super();
		this.session = session;
	}

	public String getId() {
		return this.session.getId();
	}

}
