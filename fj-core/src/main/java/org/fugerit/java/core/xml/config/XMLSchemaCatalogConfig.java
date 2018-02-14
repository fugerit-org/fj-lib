package org.fugerit.java.core.xml.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.xml.DataListCatalogConfig;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;

public class XMLSchemaCatalogConfig extends DataListCatalogConfig {

	public static XMLSchemaCatalogConfig loadConfigSchema( InputStream is ) throws Exception {
		return (XMLSchemaCatalogConfig)loadConfig( is, new XMLSchemaCatalogConfig() );
	}
	
	public XMLSchemaCatalogConfig() {
		super( ATT_TAG_SCHEMA_LIST, ATT_TAG_SCHEMA );
	}
	
	public static final String ATT_TAG_SCHEMA_LIST = "schema-list";
	
	public static final String ATT_TAG_SCHEMA = "schema";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6067071761917625426L;

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
		logger.info( "configure() "+CONFIG_PATH_MODE+"="+this.pathMode );
		logger.info( "configure() "+CONFIG_PATH_BASE+"="+this.pathBase );
	}

	public Source[] getXsds( String setId ) {
		return getXsds( this.pathMode, this.pathBase, this.getDataList( setId ) );
	}
	
	public static Source[] getXsds( String pathMode, String pathBase, Collection<String> schemaList )  {
		Source[] xsds = new Source[ schemaList.size() ];
		Iterator<String> it = schemaList.iterator();
		int k = 0;
		while ( it.hasNext() ) {
			try {
				String current = it.next();
				InputStream is = null;
				if ( CONFIG_PATH_MODE_FILE.equalsIgnoreCase( pathMode ) ) {
					File currentFile = new File( pathBase, current );
					is = new FileInputStream( currentFile );
				} else {
					String fullPath = pathBase+"/"+current;
					is =  ClassHelper.getDefaultClassLoader().getResourceAsStream( fullPath );
				}
				xsds[k] = new StreamSource( is );
				k++;
			} catch (Exception e) {
				throw new RuntimeException( e );	
			}
		}
		return xsds;
	}
	
	public void validate( ErrorHandler er, SAXSource source, String schemaListId ) throws Exception {
		validateWorker( er, source, getXsds( schemaListId ));
	}
	
	public static void validateWorker( ErrorHandler er, SAXSource source, Source[] xsds ) throws Exception {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema( xsds );
        Validator validator = schema.newValidator();
        validator.setErrorHandler( er );
        validator.validate(source);
	}

}
