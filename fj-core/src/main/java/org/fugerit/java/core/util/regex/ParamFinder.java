package org.fugerit.java.core.util.regex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fugerit.java.core.log.BasicLogObject;

public class ParamFinder extends BasicLogObject {

	public static void main( String[] args ) {
		try {
			
			String test = "Prova {idRisorsa} aa";
			
			ParamFinder pf = ParamFinder.newFinderAlt1();
			
			Properties params = new Properties();
			params.setProperty( "idRisorsa" , "1" );
			
			System.out.println( "TEST >>> "+pf.substitute( test , params ) );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static final String DEFAULT_PRE = "\\$\\{";
	public static final String DEFAULT_POST = "\\}";
	
	public static final String ALT1_PRE = "\\{";
	public static final String ALT1_POST = "\\}";

	/*
	 * Creates a ParamFinder with default substitution ${param}
	 * 
	 * @return
	 */
	public static ParamFinder newFinder() {
		return new ParamFinder( DEFAULT_PRE, DEFAULT_POST, 2, 1 );
	}
	
	/*
	 * Creates a ParamFinder with alternative substitution {param}
	 * 
	 * @return
	 */
	public static ParamFinder newFinderAlt1() {
		return new ParamFinder( ALT1_PRE, ALT1_POST, 1, 1 );
	}	
	
	private Pattern p;
	
	private String pre;
	
	private String post;
	
	private int preL;
	
	private int postL;
	
	protected ParamFinder( String pre, String post, int preL, int postL ) {
		String pattern = pre+"(.*?)"+post;
		this.p = Pattern.compile( pattern );
		this.pre = pre;
		this.post = post;
		this.preL = preL;
		this.postL = postL;
	}
	
	public Set getParamSet( CharSequence text ) {
		List paramList = this.getParamList( text );
		Set paramSet = new HashSet( paramList );
		return paramSet;
	}
	
	public List getParamList( CharSequence text ) {
		List paramList = new ArrayList();
		Matcher m = p.matcher( text );
		while ( m.find() ) {
			String found = m.group();
			this.getLogger().debug( "FOUND : "+found+" : "+this.pre.length()+" : "+this.post.length() );
			String paramName = found.substring( this.preL, found.length()-this.postL );
			paramList.add( paramName );
		}
		//this.getLog().debug( "PARAM_LIST "+paramList );
		return paramList;
	}

	public int count( CharSequence text, String param ) {
		int count = 0;
		List list = this.getParamList( text );
		while ( list.remove( param ) ) {
			count++;
		}
		return count;
	}
	
	public String substitute( CharSequence text, String param, String value ) {
		String result = text.toString(); 
		return result.replaceAll( this.pre+param+this.post , value );
	}
	
	public String substitute( CharSequence text, Properties params ) {
		String result = text.toString();
		List list = this.getParamList( result );
		Iterator it = list.iterator();
		while ( it.hasNext() ) {
			String current = it.next().toString();
			String sub = params.getProperty( current );
			if ( sub != null ) {
				result = result.replaceAll( this.pre+current+this.post , sub );	
			}
		}
		return result;
	}
	
}
