package org.fugerit.java.core.util.collection;

import java.util.Properties;

public class SubPropsUtils {

	private SubPropsUtils() {}
	
	/**
	 * Return a new <code>java.util.Properties</code> object containing only
	 * the properties with given prefix.
	 * 
	 * @param props			input java.util.Properties
	 * @param prefix		prefix for the keys to search
	 * @param removePrefix	<code>true</code> in case you want to remove the prefix from keys in the resulting Properties	
	 * @return				resulting java.util.Properties
	 */
	public static Properties subProps( Properties props, String prefix, boolean removePrefix ) {
		Properties subProps = new Properties();
		subProps(props, prefix, removePrefix, subProps);
		return subProps;
	}
	
	/**
	 * Return a new <code>java.util.Properties</code> object containing only
	 * the properties with given prefix.
	 * 
	 * The result will be a instance of {@link SortedProperties}
	 * 
	 * @param props			input java.util.Properties
	 * @param prefix		prefix for the keys to search
	 * @param removePrefix	<code>true</code> in case you want to remove the prefix from keys in the resulting Properties	
	 * @return				resulting java.util.Properties
	 */
	public static Properties subSortedProps( Properties props, String prefix, boolean removePrefix ) {
		Properties subProps = new SortedProperties();
		subProps(props, prefix, removePrefix, subProps);
		return subProps;
	}
	
	/**
	 * Return a new <code>java.util.Properties</code> object containing only
	 * the properties with given prefix.
	 * 
	 * @param props			input java.util.Properties
	 * @param prefix		prefix for the keys to search
	 * @param removePrefix	<code>true</code> in case you want to remove the prefix from keys in the resulting Properties	
	 * @param subProps		output java.util.Properties
	 */
	public static void subProps( Properties props, String prefix, boolean removePrefix, Properties subProps ) {
		for ( Object k : props.keySet() ) {
			String key = String.valueOf( k );
			if ( key.startsWith( prefix ) ) {
				String newKey = key;
				if ( removePrefix ) {
					newKey = key.substring( prefix.length() );
				}
				String value = props.getProperty( key );
				subProps.setProperty( newKey , value );
			}
		}
	}
	
}
