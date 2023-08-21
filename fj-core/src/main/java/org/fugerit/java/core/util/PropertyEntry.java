package org.fugerit.java.core.util;

import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.util.collection.KeyString;

public class PropertyEntry extends MapEntry<String, String> implements KeyString {

	public PropertyEntry(String key, String value) {
		super( key, value );
	}

	public PropertyEntry() {
		this( null, null );
	}
	
	public String setProperty( Properties props ) {
		return (String)props.setProperty( this.getKey(), this.getValue() );
	}
	
	public static PropertyEntry newEntry( String key, String value ) throws ConfigException {
		PropertyEntry entry = null;
		if ( StringUtils.isNotEmpty( key ) && value != null ) {
			entry = new PropertyEntry( key, value );
		} else {
			throw new ConfigException( "'key' must be not empty and 'value' not null" );
		}
		return entry;
	}
	
	public static void printOne( StringBuilder builder, PropertyEntry entry ) {
		builder.append( entry.getKey() );
		builder.append( "=" );
		builder.append( entry.getValue() );
	}
	
	public static void printAll( StringBuilder builder, PropertyEntry ...entries ) {
		builder.append( "[" );
		if ( entries != null ) {
			if ( entries.length > 0 ) {
				printOne( builder, entries[0] );
				for ( int k=1; k<entries.length; k++ ) {
					builder.append( "," );
					printOne( builder, entries[k] );
				}
			}
		}
		builder.append( "]" );
	}
	
	public String toSimpleString() {
		StringBuilder builder = new StringBuilder();
		printAll( builder, this );
		return builder.toString();
	}
		
}
