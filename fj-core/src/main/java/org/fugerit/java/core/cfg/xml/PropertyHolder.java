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
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.util.collection.ListMapStringKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyHolder extends BasicIdConfigType {

	private final static Logger logger = LoggerFactory.getLogger( PropertyHolder.class );
	
	public static final String UNSAFE_TRUE = "true";
	public static final String UNSAFE_FALSE = "false";
	public static final String UNSAFE_WARN = "warn";
	
	public final static String MODE_CLASS_LOADER = "classloader";
	public final static String MODE_CL = "cl";
	
	public final static String MODE_FILE = "file";
	
	/**
	 * When this mode is used, you must define in PATH reference to other holders in the same catalog, semicolon separated.
	 * For instace if props-01 and props-02 are two holder in the same catalog : 
	 * path="props01;props-02"
	 */
	public final static String MODE_MULTI = "multi";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4759769106837549175L;

	private String description;
	
	private String path;
	
	private String mode;
	
	private String xml;
	
	private String unsafe;
	
	private String unsafeMessage;
	
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
	
	public static Properties load( String mode, String path, String xml) throws IOException {
		return load(mode, path, xml, UNSAFE_FALSE, null );
	}
	
	public static Properties load( String mode, String path, String xml, String unsafe, String usafeMessage ) throws IOException {
		Properties props = new Properties();
		try {
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
		} catch ( Exception e ) {
			if ( UNSAFE_TRUE.equalsIgnoreCase( unsafe ) || UNSAFE_WARN.equalsIgnoreCase( unsafe ) ) {
				String unsafeMessage = " ";
				if ( StringUtils.isNotEmpty( unsafeMessage ) ) {
					unsafeMessage+= unsafeMessage+" ";
				}
				if ( UNSAFE_WARN.equalsIgnoreCase( unsafe ) ) {
					logger.warn( "Error loading unsafe property holder : "+path+unsafe+e );	
				} else {
					logger.warn( "WARNING! Error loading unsafe property holder : "+path+unsafe+e, e );
				}
			} else {
				throw new IOException( "Property holder load error : "+path, e );
			}
		}
		return props;
	}
	
	public void init( PropertyCatalog catalog, String catalogId ) throws IOException {
		if ( MODE_MULTI.equalsIgnoreCase( this.getMode() ) ) {
			Properties multi = new Properties();
			String[] data = this.getPath().split( ";" );
			for ( String propsId : data ) {
				ListMapStringKey<PropertyHolder> parent = catalog.getListMap( catalogId );
				PropertyHolder current = parent.get( propsId );
				Properties currentProps = current.getInnerProps();
				for ( Object key : currentProps.keySet() ) {
					String k = key.toString();
					String oldValue = multi.getProperty( k );
					String newValue = currentProps.getProperty( k );
					if ( oldValue != null ) {
						logger.info( "Override property '{}' from '{}' to '{}'", k, oldValue, newValue );
					}
					multi.setProperty( k , newValue );
				}
			}
			this.props = multi;
		} else {
			this.init();	
		}
	}
	
	public void init() throws IOException {
		this.props = load( this.getMode() , this.getPath(), this.getXml(), this.getUnsafe(), this.getUnsafeMessage() ); 
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

	public String getUnsafe() {
		return unsafe;
	}

	public void setUnsafe(String unsafe) {
		this.unsafe = unsafe;
	}

	public String getUnsafeMessage() {
		return unsafeMessage;
	}

	public void setUnsafeMessage(String unsafeMessage) {
		this.unsafeMessage = unsafeMessage;
	}

}
