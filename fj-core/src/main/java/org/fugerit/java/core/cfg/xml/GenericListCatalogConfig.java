package org.fugerit.java.core.cfg.xml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.helpers.XMLConfigurableObject;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.lang.helpers.reflect.MethodHelper;
import org.fugerit.java.core.util.collection.KeyObject;
import org.fugerit.java.core.util.collection.ListMapStringKey;
import org.fugerit.java.core.xml.dom.DOMIO;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

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
	
	private Properties generalProps;
	
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
	}

	/**
	 * General configurazion property for checking duplicate id 
	 */
	public static final String CONFIG_CHECK_DUPLICATE_ID = "check-duplicate-id";
	
	/**
	 * If 'check-duplicate-id' is se to fail, the duplicate will cause the configuration to fail
	 */
	public static final String CONFIG_CHECK_DUPLICATE_ID_FAIL = "fail";
	
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
	 * Default configuration element for a data list
	 */
	public static final String ATT_TAG_DATA_LIST = "data-list";
	
	/**
	 * Default configuration entry for a data entry
	 */
	public static final String ATT_TAG_DATA = "data";
	
	public static final String ATT_TYPE = "type";

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
	public static final String ATT_TAG_MODULE_CONF_MODE_CL = "cl";
	
	
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

	public static <T> GenericListCatalogConfig<T> load( InputStream is, GenericListCatalogConfig<T> config ) throws Exception {
		Document doc = DOMIO.loadDOMDoc( is );
		Element root = doc.getDocumentElement();
		config.configure( root );
		return config;
	}
	
	protected Collection<T> newCollection( Object typeSample ) {
		Collection<T> c = null;
		if ( typeSample instanceof KeyObject<?> ) {
			c = new ListMapStringKey<T>();
		} else {
			c = new ArrayList<T>();
		}
		return c;
	}
	
	@Override
	public void configure(Element tag) throws ConfigException {
		
		NamedNodeMap nnm = tag.getAttributes();
		for ( int k=0; k<nnm.getLength(); k++ ) {
			Attr att = (Attr)nnm.item( k );
			this.getGeneralProps().setProperty( att.getName() , att.getValue() );
		}
		logger.info( "general props : "+this.getGeneralProps() );
		
		String checkDuplicateId = this.getGeneralProps().getProperty( CONFIG_CHECK_DUPLICATE_ID , CONFIG_CHECK_DUPLICATE_ID_DEFAULT );
		
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
			Collection<T> listCurrent = this.newCollection( typeSample );
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
				if ( ATT_TAG_TYPE_STRING.equals( type ) ) {
					String idSchema = currentSchemaTag.getAttribute( "id" );
					if ( StringUtils.isEmpty( idSchema ) ) {
						throw new ConfigException( "No schema id definied" );
					} else {
						@SuppressWarnings("unchecked")
						T id = ((T)idSchema);
						listCurrent.add( id );	
					}	
				} else {
					try {
						@SuppressWarnings("unchecked")
						T t = (T) ClassHelper.newInstance( type );
						NamedNodeMap atts = currentSchemaTag.getAttributes();
						for ( int ak=0; ak<atts.getLength(); ak++ ) {
							Attr att = (Attr)atts.item( ak );
							String key = att.getName();
							String value = att.getValue();
							MethodHelper.invokeSetter( t , key, String.class, value );
						}
						if ( t instanceof TextValueType ) {
							((TextValueType) t ).setTextValue( currentSchemaTag.getTextContent() );
						}
						listCurrent.add( t );
					} catch (Exception e) {
						throw new ConfigException( "Error configuring type : "+e, e );
					}
				}
			}
			logger.info( "add "+idList+" -> "+listCurrent );
			this.dataMap.put( idList , listCurrent );
			this.orderedId.add( idList );
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
				if ( ATT_TAG_MODULE_CONF_MODE_CL.equalsIgnoreCase( mode ) ) {
					logger.info( "Loading module id="+id+" mode="+mode+" path="+path );
					try {
						InputStream is = ClassHelper.loadFromDefaultClassLoader( path );
						Document currentModuleDoc = DOMIO.loadDOMDoc( is );
						Element rootTag = currentModuleDoc.getDocumentElement();
						this.configure( rootTag );
					} catch (Exception e) {
						if ( "true".equalsIgnoreCase( unsafe ) ) {
							logger.warn( "Module "+id+" load failed, exception suppressed as it's marked 'unsafe'", e );
						} else {
							throw new ConfigException( "Error loadind module : "+id );
						}
					}
				} else {
					throw new ConfigException( "Usupported module load mode : "+mode );
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
	 * Returns the general configuration properties of this configuration
	 * 
	 * @return general configuration properties
	 */
	public Properties getGeneralProps() {
		return generalProps;
	}

}
