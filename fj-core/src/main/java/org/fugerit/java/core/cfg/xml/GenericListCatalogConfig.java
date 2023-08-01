package org.fugerit.java.core.cfg.xml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.helpers.XMLConfigurableObject;
import org.fugerit.java.core.cfg.provider.ConfigProviderFacade;
import org.fugerit.java.core.io.helper.StreamHelper;
import org.fugerit.java.core.lang.helpers.BooleanUtils;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.util.collection.KeyObject;
import org.fugerit.java.core.util.collection.ListMapStringKey;
import org.fugerit.java.core.xml.config.XMLSchemaCatalogConfig;
import org.fugerit.java.core.xml.dom.DOMIO;
import org.fugerit.java.core.xml.dom.DOMUtils;
import org.fugerit.java.core.xml.sax.SAXErrorHandlerStore;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import lombok.Getter;
import lombok.Setter;

/**
 * Class for loading an xml configuration in the form of : 
 * 
 * 
	<pre>
 	{@code
 		<!-- 
 			key/value attributes in this list
 			are accessed through the method getGeneralProps()
 		-->
		<data-catalog-config key1="value1" key2="value">
			
			<!--
				Some additional configurations could be loaded through configuration files.
				All modules will be loaded before the main configuration file.
			-->
			<module-list>
				<module id="module-01" mode="cl" src="class/load/path/to/module"/>
			</module-list>

	 		<!-- 
	 			data list ids are accessed
	 			through the method getIdSet() [ex. 1, 2, 3]
	 			while specific entry list 
	 			through the method getDataList() [ex. 1A, 1B]
	 		-->
			
			<data-list id="1">
				<data id="1A"/>
				<data id="1B"/>
			</data-list>
			
			<data-list id="2">
				<data id="2A"/>
			</data-list>
			
			<data-list id="3">
				<data id="3A"/>
				<data id="3B"/>
				<data id="3C"/>
			</data-list>
			
		</data-catalog-config>
	}
	</pre>
	
	It is possibile to provided custom data-list and data element name.

 * @author fugerit
 *
 */
public class GenericListCatalogConfig<T> extends XMLConfigurableObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 60670717619176336L;
	
	private Map<String, Collection<T>> dataMap;
	
	private Set<String> orderedId;
	
	@Getter private Properties generalProps;
	
	@Getter @Setter private XMLSchemaCatalogConfig definition;
	
	@Getter @Setter private String schemaId;
	
	/**
	 * Creates a new DataListCatalogConfig wth default configuration.
	 */
	public GenericListCatalogConfig() {
		this( ATT_TAG_DATA_LIST, ATT_TAG_DATA );
	}
	
	/**
	 * Creates a new DataListCatalogConfig
	 * 
	 * @param attTagDataList	attribute name to use for data list
	 * @param attTagData		attribute name to use for data entry
	 */
	public GenericListCatalogConfig( String attTagDataList, String attTagData ) {
		this.dataMap = new HashMap<String, Collection<T>>();
		this.attTagDataList = attTagDataList;
		this.attTagData = attTagData;
		this.generalProps = new Properties();
		this.orderedId = new ConcurrentSkipListSet<String>();
		this.entryIdCheck = new HashSet<String>();
	}

	/**
	 * General configurazion property for checking duplicate catalog id 
	 */
	public static final String CONFIG_CHECK_DUPLICATE_ID = "check-duplicate-id";
	
	/**
	 * General configurazion property for checking duplicate entry id
	 */
	public static final String CONFIG_CHECK_ENTRY_DUPLICATE_ID = "check-duplicate-entry-id";
	
	/**
	 * If 'check-duplicate-id' is se to fail, the duplicate will cause the configuration to fail
	 */
	public static final String CONFIG_CHECK_DUPLICATE_ID_FAIL = "fail";

	/**
	 * If 'check-duplicate-entry-id' is se to fail, the duplicate will cause the configuration to fail
	 */
	public static final String CONFIG_CHECK_DUPLICATE_ID_FAIL_ON_SET = "fail-on-set";
	
	/**
	 * If 'check-duplicate-id' is se to warn, the duplicate will only be logged
	 */
	public static final String CONFIG_CHECK_DUPLICATE_ID_WARN = "warn";

	/**
	 * Default vaule for 'check-duplicate-id' property.
	 * ('warn' for compatibility reason, strongly recommended setting to 'fail' ) 
	 */
	public static final String CONFIG_CHECK_DUPLICATE_ID_DEFAULT = CONFIG_CHECK_DUPLICATE_ID_WARN;
	
	/**
	 * Default configuration element for a a data catalog config element
	 */
	public static final String ATT_DATA_CATALOG_CONFIG = "data-catalog-config";
	
	/**
	 * True if the catalog should try xsd validation (will be used only if a schema catalog is set) 
	 */
	public static final String ATT_TRY_XSD_VALIDATION = "try-xsd-validation";
	public static final String ATT_TRY_XSD_VALIDATION_TRUE = "1";
	public static final String ATT_TRY_XSD_VALIDATION_FALSE = "0";
	public static final String ATT_TRY_XSD_VALIDATION_DEFAULT = ATT_TRY_XSD_VALIDATION_FALSE;
	
	/**
	 * Default configuration element for a data list
	 */
	public static final String ATT_TAG_DATA_LIST = "data-list";
	
	/**
	 * Default configuration entry for a data entry
	 */
	public static final String ATT_TAG_DATA = "data";
	
	/**
	 * Type to use for the for the elements (data)
	 */
	public static final String ATT_TYPE = "type";

	/**
	 * Bean population mode
	 */
	public static final String ATT_BEAN_MODE = "bean-mode";

	/**
	 * Name of the config provider to use
	 */
	public static final String ATT_CONFIG_PROVIDER_NAME = "config-provider-name";
	
	/**
	 * Default, bean population mode (by default read only attributes)
	 */	
	public static final String ATT_BEAN_MODE_DEFAULT = XmlBeanHelper.MODE_XML_ATTRIBUTES;
	
	/**
	 * Type to use for the config containers (data-list)
	 */
	public static final String ATT_LIST_TYPE = "list-type";

	public static final String ATT_TAG_TYPE_STRING = "java.lang.String";
	
	public static final String ATT_TAG_MODULE_LIST = "module-list";
	
	public static final String ATT_TAG_MODULE = "module";

	/**
	 * Configuration attribute for module 'id' 
	 */
	public static final String ATT_TAG_MODULE_CONF_ID = "id";
	
	/**
	 * Configuration attribute for module load 'mode'
	 */
	public static final String ATT_TAG_MODULE_CONF_MODE = "mode";
	
	
	/**
	 * Value for module load mode by class loader
	 */
	public static final String ATT_TAG_MODULE_CONF_MODE_CL = StreamHelper.MODE_CLASSLOADER;

	
	/**
	 * Value for module load mode by file
	 */
	public static final String ATT_TAG_MODULE_CONF_MODE_FILE = StreamHelper.MODE_FILE;
	
	/**
	 * Configuration attribute for module src path 
	 */
	public static final String ATT_TAG_MODULE_CONF_PATH = "path";
	
	/**
	 * Configuration attribute for module unsafe module 
	 * (if unsafe='true' load exception would be ignored and main configuration will proceed ) 
	 */
	public static final String ATT_TAG_MODULE_CONF_UNSAFE = "unsafe";
	
	protected String attTagDataList;
	protected String attTagData;

	private Set<String> entryIdCheck;
	
	public static <T> GenericListCatalogConfig<T> load( InputStream is, GenericListCatalogConfig<T> config ) throws Exception {
		Document doc = DOMIO.loadDOMDoc( is, true );
		Element root = doc.getDocumentElement();
		config.configure( root );
		return config;
	}

	protected Set<String> getEntryIdCheck() {
		return entryIdCheck;
	}

	@SuppressWarnings("unchecked")
	protected Collection<T> newCollection( Object typeSample, String listType, Element tag, Element current ) throws ConfigException {
		Collection<T> c = null;
		if ( listType != null ) {
			try {
				c = (Collection<T>)ClassHelper.newInstance( listType );
				if ( c instanceof IdConfigType ) {
					XmlBeanHelper.setFromElementSafe( c , current );	
				}
			} catch (Throwable e) {
				e.printStackTrace();
				throw new ConfigException( e );
			}
		} else  if ( typeSample instanceof KeyObject<?> ) {
			c = new ListMapConfig<T>();
		} else {
			c = new ArrayList<T>();
		}
		if ( c instanceof ListMapConfig ) {
			((ListMapConfig<T>)c).initFromElementAttributes( current );
		}
		return c;
	}
	
	protected T customEntryHandling( String dataListId, T current, Element element ) throws ConfigException {
		T result = this.customEntryHandling(current, element);
		return result;
	}
	
	protected T customEntryHandling( T current, Element element ) throws ConfigException {
		return current;
	}
	
	@Override
	public void configure(Element tag) throws ConfigException {
		
		DOMUtils.attributesToProperties( tag, this.getGeneralProps() );
		logger.info( "general props : "+this.getGeneralProps() );
		
		String confgiProviderName = this.getGeneralProps().getProperty( ATT_CONFIG_PROVIDER_NAME );
		this.setConfigProvider( ConfigProviderFacade.getInstance().getProviderWithDefault( confgiProviderName , this ) );
		
		String tryXsdValidation = this.getGeneralProps().getProperty( ATT_TRY_XSD_VALIDATION, ATT_TRY_XSD_VALIDATION_DEFAULT );
		if ( BooleanUtils.isTrue( tryXsdValidation ) ) {
			if ( this.hasDefinition() ) {
				logger.info( "ATT {} is false, skip xsd validation", ATT_TRY_XSD_VALIDATION );
				try {
					SAXErrorHandlerStore eh = this.validate( new DOMSource( tag ) );
					for ( SAXException se : eh.getFatals() ) {
						logger.error( "xsd validation fatal : {}", se );
					}
					for ( SAXException se : eh.getErrors() ) {
						logger.error( "xsd validation error : {}", se );
					}
					for ( SAXException se : eh.getWarnings() ) {
						logger.warn( "xsd validation warning : {}", se );
					}
				} catch (Exception e) {
					throw new ConfigException( "Xsd Validation Error "+e, e );
				}
			} else {
				logger.info( "No xsd definition set, skip xsd validation" );
			}
		} else {
			logger.info( "ATT {} is false, skip xsd validation", ATT_TRY_XSD_VALIDATION );
		}
		
		String checkDuplicateId = this.getGeneralProps().getProperty( CONFIG_CHECK_DUPLICATE_ID , CONFIG_CHECK_DUPLICATE_ID_DEFAULT );
		String checkDuplicateUniversalId = this.getGeneralProps().getProperty( CONFIG_CHECK_ENTRY_DUPLICATE_ID , CONFIG_CHECK_DUPLICATE_ID_DEFAULT );
		
		String listType = this.getGeneralProps().getProperty( ATT_LIST_TYPE );
		
		String beanMode = this.getGeneralProps().getProperty( ATT_BEAN_MODE, ATT_BEAN_MODE_DEFAULT );
		
		String type = this.getGeneralProps().getProperty( ATT_TYPE );
		if ( StringUtils.isEmpty( type ) ) {
			throw new ConfigException( "No type defined" );
		}
		
		Object typeSample = null;
		try {
			typeSample = ClassHelper.newInstance( type );
		} catch (Exception ce ) {
			throw new ConfigException( ce );
		}
		
		String dataListElementName = this.attTagDataList;
		String dataElementName = this.attTagData;
		NodeList dataCatalogConfig = tag.getElementsByTagName( ATT_DATA_CATALOG_CONFIG );
		if ( dataCatalogConfig.getLength() > 0 ) {
			Element dataCatalogConfigTag = (Element)dataCatalogConfig.item( 0 );
			dataListElementName = StringUtils.valueWithDefault( dataCatalogConfigTag.getAttribute( "list-tag" ) , this.attTagDataList );
			dataElementName = StringUtils.valueWithDefault( dataCatalogConfigTag.getAttribute( "data-tag" ) , this.attTagData );
		}
		NodeList schemaListTag = tag.getElementsByTagName( dataListElementName );
		for ( int k=0; k<schemaListTag.getLength(); k++ ) {
			Element currentListTag = (Element) schemaListTag.item( k );
			Collection<T> listCurrent = this.newCollection( typeSample, listType, tag, currentListTag );
			NodeList schemaIt = currentListTag.getElementsByTagName( dataElementName );
			String idList = currentListTag.getAttribute( "id" );
			if ( this.getIdSet().contains( idList ) ) {
				String message = "Duplicate id found : "+idList;
				if ( CONFIG_CHECK_DUPLICATE_ID_FAIL.equalsIgnoreCase( checkDuplicateId ) ) {
					throw new ConfigException( message );
				} else {
					logger.warn( "["+this.getClass().getSimpleName()+"]"+message );
				}
			}
			logger.info( "add "+idList+" -> "+listCurrent );
			this.dataMap.put( idList , listCurrent );
			this.orderedId.add( idList );
			String extendsAtt = currentListTag.getAttribute( "extends" );
			if ( StringUtils.isNotEmpty( extendsAtt ) ) {
				String[] extendsAttList = extendsAtt.split( "," );
				for ( int j=0; j<extendsAttList.length; j++ ) {
					String currentExtend = extendsAttList[j].trim();
					Collection<T> parent = this.getDataList( currentExtend );
					if ( parent == null ) {
						throw new ConfigException( "Parent schema list not found "+currentExtend );
					} else {
						listCurrent.addAll( parent );
					}	
				}
			}
			for ( int i=0; i<schemaIt.getLength(); i++ ) {
				Element currentSchemaTag = (Element) schemaIt.item( i );
				String idSchema = currentSchemaTag.getAttribute( "id" );
				if ( StringUtils.isNotEmpty( idSchema ) && CONFIG_CHECK_DUPLICATE_ID_FAIL.equalsIgnoreCase( checkDuplicateUniversalId ) ) {
					if ( !this.getEntryIdCheck().add( idSchema ) ) {
						throw new ConfigException( "Duplicate entry id found : "+idSchema );
					}
				} else if ( StringUtils.isNotEmpty( idSchema ) && CONFIG_CHECK_DUPLICATE_ID_FAIL_ON_SET.equalsIgnoreCase( checkDuplicateUniversalId ) ) {
					if ( listCurrent instanceof ListMapStringKey ) {
						ListMapStringKey<?> listCheck = ((ListMapStringKey<?>)listCurrent);
						if ( listCheck.getMap().containsKey( idSchema ) ) {
							throw new ConfigException( "Duplicate entry id on set found : "+idSchema );
						}	
					}
					
				}
				if ( ATT_TAG_TYPE_STRING.equals( type ) ) {
					if ( StringUtils.isEmpty( idSchema ) ) {
						throw new ConfigException( "No schema id definied" );
					} else {
						@SuppressWarnings("unchecked")
						T id = ((T)idSchema);
						id = this.customEntryHandling( idList, id, currentSchemaTag );
						listCurrent.add( id );	
					}	
				} else {
					try {
						T t = XmlBeanHelper.setFromElement( type, currentSchemaTag, beanMode );
						t = this.customEntryHandling( idList, t, currentSchemaTag );
						listCurrent.add( t );
					} catch (Exception e) {
						throw new ConfigException( "Error configuring type : "+e, e );
					}
				}
			}
		}
		
		NodeList moduleListTag = tag.getElementsByTagName( ATT_TAG_MODULE_LIST );
		for ( int l=0; l<moduleListTag.getLength(); l++ ) {
			Element currentModuleList = (Element)moduleListTag.item( l );
			NodeList moduleTag = currentModuleList.getElementsByTagName( ATT_TAG_MODULE );
			for ( int m=0; m<moduleTag.getLength(); m++ ) {
				Element currentModule = (Element)moduleTag.item( m );
				String id = currentModule.getAttribute( ATT_TAG_MODULE_CONF_ID );
				String mode = currentModule.getAttribute( ATT_TAG_MODULE_CONF_MODE );
				String path = currentModule.getAttribute( ATT_TAG_MODULE_CONF_PATH );
				String unsafe = currentModule.getAttribute( ATT_TAG_MODULE_CONF_UNSAFE );
				logger.info( "Loading module id="+id+" mode="+mode+" path="+path );
				try ( InputStream is = this.getConfigProvider().readConfiguration( mode, path) )   {
					Document currentModuleDoc = DOMIO.loadDOMDoc( is );
					Element rootTag = currentModuleDoc.getDocumentElement();
					this.configure( rootTag );
				} catch (Exception e) {
					if ( "true".equalsIgnoreCase( unsafe ) ) {
						logger.warn( "Module "+id+" load failed, exception suppressed as it's marked 'unsafe'" );
					} else {
						throw new ConfigException( "Error loading module : "+id, e );
					}
				}
			}
		}
		
	}

	/**
	 * Returns the set of data list id contained in this configuration
	 * 
	 * @return a set of id
	 */
	public Set<String> getIdSet() {
		return this.orderedId;
	}
	
	/**
	 * Returns a collection of data entry contained in a specifed data list
	 * 
	 * @param id	the id of the data list
	 * @return		the data entry in the data list
	 */
	public Collection<T> getDataList( String id ) {
		return this.dataMap.get( id );
	}

	/**
	 * Check if the catalog config has a schema definition
	 * 
	 * @return	<code>true</code> if the schemaId exists and has the catalog defined
	 */
	public boolean hasDefinition() {
		return StringUtils.isNotEmpty( this.getSchemaId() ) && this.getDefinition() != null 
				&& this.getDefinition().getDataList( this.getSchemaId() ) != null;
	}
	
	public SAXErrorHandlerStore validate( Source source ) throws Exception {
		SAXErrorHandlerStore eh = new SAXErrorHandlerStore();
		this.getDefinition().validate( eh , source, this.getSchemaId() );
		return eh;
	}
	
}
