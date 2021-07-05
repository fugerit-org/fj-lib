package org.fugerit.java.core.web.servlet.config;

import java.io.File;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.fugerit.java.core.io.helper.StreamHelper;

public class ConfigContext {

	public File resolvePath( String path ) throws Exception {
		return ConfigFacade.resolvePath( path , this.context );
	}
	
	public ConfigContext( ServletContext context ) {
		this.context = context;
	}
	
	private ServletContext context;

	public Object getAttribute(String name) {
		return context.getAttribute(name);
	}

	public void removeAttribute(String name) {
		context.removeAttribute(name);
	}

	public void setAttribute(String name, Object value) {
		context.setAttribute(name, value);
	}

	public String getBasePath() {
		return this.context.getRealPath( "/" );
	}
	
	public InputStream resolveStream( String path ) throws Exception {
		return StreamHelper.resolveStream( path, this.getBasePath() );
	}

	public ServletContext getContext() {
		return context;
	}
	
	@Override
	public String toString() {
		return "ConfigContext["+this.getContext()+"]";
	}
	
}
