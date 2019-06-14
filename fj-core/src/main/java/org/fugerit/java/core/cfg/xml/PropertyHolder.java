package org.fugerit.java.core.cfg.xml;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.fugerit.java.core.util.PropsIO;

public class PropertyHolder extends BasicIdConfigType {

	public final static String MODE_CLASS_LOADER = "classloader";
	
	public final static String MODE_FILE = "file";
	
	public final static String MODE_MULTI = "multi";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4759769106837549175L;

	private String description;
	
	private String path;
	
	private String mode;
	
	private Properties props;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
		
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public static Properties load( String mode, String path ) throws IOException {
		Properties props = null;
		if ( MODE_FILE.equalsIgnoreCase( mode ) ) {
			props = PropsIO.loadFromFile( path );
		} else if ( MODE_CLASS_LOADER.equalsIgnoreCase( mode ) ) {
			props = PropsIO.loadFromClassLoader( path );
		}
		return props;
	}
	
	public void init() throws IOException {
		this.props = load( this.getMode() , this.getPath() ); 
	}

	public boolean isEmpty() {
		return props.isEmpty();
	}

	public boolean containsValue(Object value) {
		return props.containsValue(value);
	}

	public boolean containsKey(Object key) {
		return props.containsKey(key);
	}

	public Set<Object> keySet() {
		return props.keySet();
	}

	public Set<Entry<Object, Object>> entrySet() {
		return props.entrySet();
	}

	public String getProperty(String key) {
		return props.getProperty(key);
	}

	public String getProperty(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}

	public Enumeration<Object> keys() {
		return props.keys();
	}
	
	public Properties getInnerProps() {
		return props;
	}
	
	public String toString() {
		return this.getClass().getSimpleName()+"[id:"+this.getId()+",description:"+this.getDescription()+"]";
	}

}
