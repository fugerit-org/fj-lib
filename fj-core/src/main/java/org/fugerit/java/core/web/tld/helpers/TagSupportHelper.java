package org.fugerit.java.core.web.tld.helpers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class TagSupportHelper extends TagSupport {

	protected void renderValue( String v ) throws JspException {
		if ( this.getId() == null ) {
			this.print( v );	
		} else {
			TagUtilsHelper.setAttibute(this.pageContext, this.getScope(), this.getId(), v );
		}
	}
	
	protected Object findDefaultObject( String value ) throws JspException {
		return this.findObject( this.getName() , this.getProperty(), value );
	}
	
	public void print( Object message ) throws JspException {
		try {
			this.pageContext.getOut().print( message );
		} catch (IOException e) {
			throw ( new JspException( e ) );
		}
	}
	
	private String styleClass;
	
	private String styleId;
	
	private String scope;
	
	private String toScope;
	
	private String name;
	
	private String property;
	
	protected String getStyleData() {
		String sc = "";
		if (this.getStyleClass()!=null) {
			sc = " class='"+this.getStyleClass()+"' ";
		}
		String si = "";
		if (this.getStyleId()!=null) {
			sc = " id='"+this.getStyleId()+"' ";
		}
		return sc+si;
	}	
	
	protected Object findObject( String name, String property, String value ) throws JspException {
		return this.findObject( name, property, value, null );
	}
	
	protected Object findObject( String name, String property, String value, String parameter ) throws JspException {
		Object obj = null;
		if ( name != null ) {
			obj = TagUtilsHelper.findAttibute( this.pageContext, this.getScope(), name, property );
		} else if ( value != null ) {
			obj = value;
		} else if ( parameter != null ) {
			obj= ((HttpServletRequest)this.pageContext.getRequest()).getParameter( parameter );
		}
		return obj;
	}	
	

	private static final long serialVersionUID = 565826836474874032L;


	public String getScope() {
		return scope;
	}


	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getStyleId() {
		return styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	public String getToScope() {
		return toScope;
	}

	public void setToScope(String toScope) {
		this.toScope = toScope;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProperty() {
		return property;
	}
	
	public void setProperty(String property) {
		this.property = property;
	}
	
	protected static String renderAttribute( String name, String value ) {
		String result = " ";
		if ( value!= null ) {
			result+= name+"='"+value+"'";
		}
		return result;
	}
	
	protected static boolean isTrue( String value ) {
		return "true".equalsIgnoreCase( value ) || "1".equalsIgnoreCase( value );
	}
	
}
