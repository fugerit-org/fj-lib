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

public class DataListCatalogConfig extends XMLConfigurableObject {
	
	protected static DataListCatalogConfig loadConfig( InputStream is, DataListCatalogConfig config ) throws Exception {
		Document doc = DOMIO.loadDOMDoc( is );
		Element root = doc.getDocumentElement();
		config.configure( root );
		return config;
	}
	
	public static DataListCatalogConfig loadConfig( InputStream is ) throws Exception {
		return loadConfig( is, new DataListCatalogConfig() );
	}
	
	private Map<String, Collection<String>> dataMap;
	
	private Properties generalProps;
	
	public DataListCatalogConfig() {
		this( ATT_TAG_DATA_LIST, ATT_TAG_DATA );
	}
	
	public DataListCatalogConfig( String attTagDataList, String attTagData ) {
		this.dataMap = new HashMap<String, Collection<String>>();
		this.attTagDataList = attTagDataList;
		this.attTagData = attTagData;
		this.generalProps = new Properties();
	}
	
	public static final String ATT_DATA_CATALOG_CONFIG = "data-catalog-config";
	public static final String ATT_TAG_DATA_LIST = "data-list";
	public static final String ATT_TAG_DATA = "data";
	
	protected String attTagDataList;
	protected String attTagData;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6067071761917625426L;

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

	public Set<String> getIdSet() {
		return this.dataMap.keySet();
	}
	
	public Collection<String> getDataList( String id ) {
		return this.dataMap.get( id );
	}

	public Properties getGeneralProps() {
		return generalProps;
	}

}
