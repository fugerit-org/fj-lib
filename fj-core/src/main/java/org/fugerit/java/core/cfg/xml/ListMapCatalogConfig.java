package org.fugerit.java.core.cfg.xml;

import org.fugerit.java.core.util.collection.KeyObject;
import org.fugerit.java.core.util.collection.ListMapStringKey;

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
public class ListMapCatalogConfig<T extends KeyObject<String>> extends GenericListCatalogConfig<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -844996956024455810L;

	/**
	 * Returns one list map of this catalog by ID
	 * 
	 * @param id	id of the catalog
	 * @return		the list map
	 */
	public ListMapStringKey<T> getListMap(String id) {
		return ( ListMapStringKey<T>)super.getDataList(id);
	}

	public ListMapCatalogConfig() {
		super();
	}

	public ListMapCatalogConfig(String attTagDataList, String attTagData) {
		super(attTagDataList, attTagData);
	}

}
