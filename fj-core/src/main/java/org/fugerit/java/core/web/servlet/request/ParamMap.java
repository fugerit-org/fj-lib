package org.fugerit.java.core.web.servlet.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class ParamMap implements Serializable {


	public static final String PARAM_MAP_GENERATE = "ParamMapGenerate";
	
	/*
	 * 
	 */
	private static final long serialVersionUID = -5053311423985375268L;
	
	private HashMap<String, String[]> map;
	
	private ParamMap() {
		this.map = new HashMap<String, String[]>();
	}
	
	public String getQueryString( String[] excludeParams ) {
		List<String> listExclude = Arrays.asList( excludeParams );
		StringBuilder qs = new StringBuilder();
		Iterator<String> ki = this.getParamNames();
		qs.append( "?"+PARAM_MAP_GENERATE+"=1" );
		while ( ki.hasNext() ) {
			String name = (String) ki.next();
			if ( !PARAM_MAP_GENERATE.equals( name ) ) {
				String[] values = this.getParams( name );
				for ( int k=0; k<values.length; k++ ) {
					if ( !listExclude.contains( name ) ) {
						qs.append( "&"+name+"="+values[k] );	
					}
				}				
			}
		}
		return qs.toString();
	}
	
	public String getQueryString() {
		StringBuilder qs = new StringBuilder();
		Iterator<String> ki = this.getParamNames();
		qs.append( "?"+PARAM_MAP_GENERATE+"=1" );
		while ( ki.hasNext() ) {
			String name = (String) ki.next();
			if ( !PARAM_MAP_GENERATE.equals( name ) ) {
				String[] values = this.getParams( name );
				for ( int k=0; k<values.length; k++ ) {
					qs.append( "&"+name+"="+values[k] );
				}				
			}
		}
		return qs.toString();
	}
	
	public static ParamMap newEmptyMap() {
		return new ParamMap();
	}
	
	public static ParamMap getParamMap( HttpServletRequest request ) {
		ParamMap map = new ParamMap();
		Enumeration<String> ae = request.getParameterNames();
		while ( ae.hasMoreElements() ) {
			String att = (String) ae.nextElement();
			map.setParams( att, request.getParameterValues( att ) );
		}
		return map;
	}
	
	public Iterator<String> getParamNames() {
		return this.map.keySet().iterator();
	}
	
	public void setParams( String name, String[] list ) {
		this.map.put( name , list );
	}
	
	public void addParam( String name, String value ) {
		String[] list = this.getParams( name );
		if ( name == null ) {
			list = new String[1];
			list[0] = value;
		} else {
			if ( list == null ) {
				list = new String[0];
			}
			String[] tmp = new String[ list.length+1 ];
			for ( int k=0; k<list.length; k++ ) {
				tmp[k] = list[k];
			}
			tmp[list.length] = value;
			list = tmp;
		}
		this.map.put( name , list );
	}
	
	public boolean containsParam( String name ) {
		return this.map.containsKey( name );
	}
	
	public String getParam( String name, String def ) {
		String value = this.getParam( name );
		if ( value == null ) {
			value = def;
		}
		return value;
	}
	
	public String getParam( String name ) {
		String result = null;
		String[] params = this.getParams( name );
		if ( params != null && params.length == 1 ) {
			result = params[0];
		} else if ( params != null && params.length > 1 ) {
			throw ( new RuntimeException( "More than one parameter with given name:"+name ) );
		}
		return result;
	}
	
	public String[] getParams( String name ) {
		return (String[])this.map.get( name ); 
	}
	
	public String getSortedQueryString() {
		StringBuilder qs = new StringBuilder();
		ArrayList<String> l = new ArrayList<String>(  this.map.keySet() );
		Collections.sort( l );
		qs.append( "?"+PARAM_MAP_GENERATE+"=2" );
		Iterator<String> ki = l.iterator();
		while ( ki.hasNext() ) {
			String name = (String) ki.next();
			if ( !PARAM_MAP_GENERATE.equals( name ) ) {
				String[] values = this.getParams( name );
				Arrays.sort( values );
				for ( int k=0; k<values.length; k++ ) {
					qs.append( "&"+name+"="+values[k] );
				}				
			}
		}
		return qs.toString();
	}
	
	@Override
	public int hashCode() {
		return this.getSortedQueryString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		boolean ok = false;
		if ( obj instanceof ParamMap ) {
			ok = this.getSortedQueryString().equals( ((ParamMap)obj).getSortedQueryString() );
		}
		return ok;
	}

	@Override
	public String toString() {
		StringBuilder qs = new StringBuilder();
		qs.append( this.getClass().getName() );
		qs.append( "[" );
		ArrayList<String> l = new ArrayList<String>(  this.map.keySet() );
		Collections.sort( l );
		Iterator<String> ki = l.iterator();
		while ( ki.hasNext() ) {
			String name = (String) ki.next();
			if ( !PARAM_MAP_GENERATE.equals( name ) ) {
				String[] values = this.getParams( name );
				Arrays.sort( values );
				for ( int k=0; k<values.length; k++ ) {
					qs.append( name+"="+values[k]+"; " );
				}				
			}
		}
		qs.append( "]" );
		return qs.toString();
	}
	
}
