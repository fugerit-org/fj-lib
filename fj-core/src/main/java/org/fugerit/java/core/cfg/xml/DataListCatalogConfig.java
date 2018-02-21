package org.fugerit.java.core.cfg.xml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.helpers.XMLConfigurableObject;
import org.fugerit.java.core.lang.helpers.StringUtils;
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
public class DataListCatalogConfig extends XMLConfigurableObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 60670717619176336L;
	
	/**
	 * Worker method for loading an xml from an input stream
	 * 
	 * @param is			input source
	 * @param config		config object
	 * @return				the object configured
	 * @throws Exception	in case of issues
	 */
	protected static DataListCatalogConfig loadConfig( InputStream is, DataListCatalogConfig config ) throws Exception {
		Document doc = DOMIO.loadDOMDoc( is );
		Element root = doc.getDocumentElement();
		config.configure( root );
		return config;
	}
	
	/**
	 * Worker method for loading an xml from an input stream
	 * 
	 * @param is			input source
	 * @return				the object configured
	 * @throws Exception	in case of issues
	 */
	public static DataListCatalogConfig loadConfig( InputStream is ) throws Exception {
		return loadConfig( is, new DataListCatalogConfig() );
	}
	
	private Map<String, Collection<String>> dataMap;
	
	private Properties generalProps;
	
	/**
	 * Creates a new DataListCatalogConfig wth default configuration.
	 */
	public DataListCatalogConfig() {
		this( ATT_TAG_DATA_LIST, ATT_TAG_DATA );
	}
	
	/**
	 * Creates a new DataListCatalogConfig
	 * 
	 * @param attTagDataList	attribute name to use for data list
	 * @param attTagData		attribute name to use for data entry
	 */
	public DataListCatalogConfig( String attTagDataList, String attTagData ) {
		this.dataMap = new HashMap<String, Collection<String>>();
		this.attTagDataList = attTagDataList;
		this.attTagData = attTagData;
		this.generalProps = new Properties();
	}
	
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
	
	protected String attTagDataList;
	protected String attTagData;

	@Override
	public void configure(Element tag) throws ConfigException {
		
		NamedNodeMap nnm = tag.getAttributes();
		for ( int k=0; k<nnm.getLength(); k++ ) {
			Attr att = (Attr)nnm.item( k );
			this.getGeneralProps().setProperty( att.getName() , att.getValue() );
		}
		logger.info( "general props : "+this.getGeneralProps() );
		
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
			Collection<String> listCurrent = new ArrayList<String>();
			NodeList schemaIt = currentListTag.getElementsByTagName( dataElementName );
			String idList = currentListTag.getAttribute( "id" );
			String extendsAtt = currentListTag.getAttribute( "extends" );
			if ( StringUtils.isNotEmpty( extendsAtt ) ) {
				String[] extendsAttList = extendsAtt.split( "," );
				for ( int j=0; j<extendsAttList.length; j++ ) {
					String currentExtend = extendsAttList[j].trim();
					Collection<String> parent = this.getDataList( currentExtend );
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
				if ( StringUtils.isEmpty( idSchema ) ) {
					throw new ConfigException( "No schema id definied" );
				} else {
					listCurrent.add( idSchema );	
				}
			}
			logger.info( "add schema list "+idList+" -> "+listCurrent );
			this.dataMap.put( idList , listCurrent );
		}
	}

	/**
	 * Returns the set of data list id contained in this configuration
	 * 
	 * @return a set of id
	 */
	public Set<String> getIdSet() {
		return this.dataMap.keySet();
	}
	
	/**
	 * Returns a collection of data entry contained in a specifed data list
	 * 
	 * @param id	the id of the data list
	 * @return		the data entry in the data list
	 */
	public Collection<String> getDataList( String id ) {
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
