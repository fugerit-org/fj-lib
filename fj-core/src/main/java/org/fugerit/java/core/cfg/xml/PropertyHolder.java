package org.fugerit.java.core.cfg.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.fugerit.java.core.lang.helpers.BooleanUtils;
import org.fugerit.java.core.lang.helpers.ClassHelper;

public class PropertyHolder extends BasicIdConfigType {

	public final static String MODE_CLASS_LOADER = "classloader";
	public final static String MODE_CL = "cl";
	
	public final static String MODE_FILE = "file";
	
	public final static String MODE_MULTI = "multi";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4759769106837549175L;

	private String description;
	
	private String path;
	
	private String mode;
	
	private String xml;
	
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

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	private static void loadWorker( InputStream is, Properties props, boolean xml ) throws IOException {
		if ( xml ) {
			props.loadFromXML( is );
		} else {
			props.load( is );
		}
	}
	
	public static Properties load( String mode, String path, String xml ) throws IOException {
		Properties props = new Properties();
		if ( MODE_CLASS_LOADER.equalsIgnoreCase( mode ) || MODE_CL.equalsIgnoreCase( mode ) ) {
			try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( path ) ) {
				loadWorker( is , props, BooleanUtils.isTrue( xml ) );
			} catch (Exception e) {
				throw new IOException( e );
			}
		} else {
			try ( InputStream is = new FileInputStream( new File( path ) ) ) {
				loadWorker( is , props, BooleanUtils.isTrue( xml ) );
			}
		}
		return props;
	}
	
	public void init() throws IOException {
		this.props = load( this.getMode() , this.getPath(), this.getXml() ); 
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
