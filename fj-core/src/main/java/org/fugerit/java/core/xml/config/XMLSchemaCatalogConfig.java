package org.fugerit.java.core.xml.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.cfg.xml.DataListCatalogConfig;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.io.helper.StreamHelper;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

public class XMLSchemaCatalogConfig extends DataListCatalogConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6067071761917625426L;
	
	// code added to setup a basic conditional serialization - START
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		// this class is conditionally serializable, depending on contained object
		// special situation can be handleded using this method in future
		out.defaultWriteObject();
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		// this class is conditionally serializable, depending on contained object
		// special situation can be handleded using this method in future
		in.defaultReadObject();
	}
	
	// code added to setup a basic conditional serialization - END	
	
	/**
	 * <p>Creates and configure an instance of XMLSchemaCatalogConfig</p>
	 * 
	 * <p>NOTE: starting from version 8.4.X java.lang.Exception removed in favor of org.fugerit.java.core.cfg.ConfigRuntimeException.</p>
	 * 
	 * @see <a href="https://fuzzymemory.fugerit.org/src/docs/sonar_cloud/java-S112.html">Define and throw a dedicated exception instead of using a generic one.</a>
	 * 
	 * @param is		the input stream to load from
	 * @return			the configured instance
	 * @throws 			ConfigRuntimeException in case of issues during loading
	 */
	public static XMLSchemaCatalogConfig loadConfigSchema( InputStream is ) {
		return (XMLSchemaCatalogConfig)loadConfig( is, new XMLSchemaCatalogConfig() );
	}
	
	public XMLSchemaCatalogConfig() {
		super( ATT_TAG_SCHEMA_LIST, ATT_TAG_SCHEMA );
	}
	
	public static final String ATT_TAG_SCHEMA_LIST = "schema-list";
	
	public static final String ATT_TAG_SCHEMA = "schema";
	
	private Map<String, Schema> schemaMapCache = Collections.synchronizedMap( new HashMap<String, Schema>() );

	public static final String CONFIG_PATH_MODE = "path-mode";
	public static final String CONFIG_PATH_MODE_FILE = "file";
	public static final String CONFIG_PATH_MODE_CLASSLOADER = "class-loader";
	public static final String CONFIG_PATH_MODE_DEFAULT = CONFIG_PATH_MODE_CLASSLOADER;
	
	public static final String CONFIG_PATH_BASE = "path-base";
	
	private String pathMode;
	private String pathBase;
	
	@Override
	public void configure(Element tag) throws ConfigException {
		super.configure(tag);
		this.pathMode = StringUtils.valueWithDefault( tag.getAttribute( CONFIG_PATH_MODE ), CONFIG_PATH_MODE_DEFAULT );
		this.pathBase = tag.getAttribute( CONFIG_PATH_BASE );
		this.getLogger().info( "configure() {}={}", CONFIG_PATH_MODE, this.pathMode );
		this.getLogger().info( "configure() {}={}", CONFIG_PATH_BASE, this.pathBase );
	}

	public Source[] getXsds( String setId ) {
		return getXsds( this.pathMode, this.pathBase, this.getDataList( setId ) );
	}
	
	public static Source[] getXsds( String pathMode, String pathBase, Collection<String> schemaList )  {
		Source[] xsds = new Source[ schemaList.size() ];
		SafeFunction.apply( () -> {
			Iterator<String> it = schemaList.iterator();
			int k = 0;
			while ( it.hasNext() ) {
				String current = it.next();
				InputStream is = null;
				if ( CONFIG_PATH_MODE_FILE.equalsIgnoreCase( pathMode ) ) {
					File currentFile = new File( pathBase, current );
					is = new FileInputStream( currentFile );
				} else {
					String fullPath = pathBase+StreamHelper.SLASH+current;
					is =  ClassHelper.getDefaultClassLoader().getResourceAsStream( fullPath );
				}
				xsds[k] = new StreamSource( is );
				k++;
			}	
		});
		return xsds;
	}
	
	public void validateCacheSchema( ErrorHandler er, SAXSource source, String schemaListId ) throws Exception {
		validateCacheSchema(er, (Source)source, schemaListId);
	}

	public void validate( ErrorHandler er, SAXSource source, String schemaListId ) throws Exception {
		validate(er, (Source)source, schemaListId);
	}
	
	public void validateCacheSchema( ErrorHandler er, Source source, String schemaListId ) throws Exception {
		Schema schema = this.schemaMapCache.get( schemaListId );
		if ( schema == null ) {
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Source[] xsds = getXsds( schemaListId );
	        schema = sf.newSchema( xsds );
	        schemaMapCache.put( schemaListId , schema );
		}
		validateWorker( er, source, schema );
	}

	public void validate( ErrorHandler er, Source source, String schemaListId ) throws Exception {
		Source[] xsds = getXsds( schemaListId );
		validateWorker( er, source, xsds );
	}
	
	/**
	 * <p>Validate an XML source.</p>
	 * 
	 * <p>NOTE: starting from version 8.4.X java.lang.Exception removed in favor of org.fugerit.java.core.cfg.ConfigRuntimeException.</p>
	 * 
	 * @see <a href="https://fuzzymemory.fugerit.org/src/docs/sonar_cloud/java-S112.html">Define and throw a dedicated exception instead of using a generic one.</a>
	 * 
	 * @param er		the error handler
	 * @param source	the XML source
	 * @param xsds		the reference XSD
	 */
	public static void validateWorker( ErrorHandler er, SAXSource source, Source[] xsds ) {
        validateWorker(er, (Source)source, xsds);
	}
	
	/**
	 * <p>Validate an XML source.</p>
	 * 
	 * <p>NOTE: starting from version 8.4.X java.lang.Exception removed in favor of org.fugerit.java.core.cfg.ConfigRuntimeException.</p>
	 * 
	 * @see <a href="https://fuzzymemory.fugerit.org/src/docs/sonar_cloud/java-S112.html">Define and throw a dedicated exception instead of using a generic one.</a>
	 * 
	 * @param er		the error handler
	 * @param source	the XML source
	 * @param xsds		the reference XSD
	 */
	public static void validateWorker( ErrorHandler er, Source source, Source[] xsds ) {
		SafeFunction.apply( () -> {
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	        Schema schema = sf.newSchema( xsds );
	        validateWorker(er, source, schema);
		} );
	}
	
	private static void validateWorker( ErrorHandler er, Source source, Schema schema ) throws SAXException, IOException {
        Validator validator = schema.newValidator();
        validator.setErrorHandler( er );
        validator.validate(source);
	}

}
