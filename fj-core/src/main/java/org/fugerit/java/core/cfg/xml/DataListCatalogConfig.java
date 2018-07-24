package org.fugerit.java.core.cfg.xml;

import java.io.InputStream;

import org.fugerit.java.core.xml.dom.DOMIO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
public class DataListCatalogConfig extends GenericListCatalogConfig<String> {
	
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
		super(attTagDataList, attTagData);
		this.getGeneralProps().setProperty( ATT_TYPE , ATT_TAG_TYPE_STRING );
	}


}
