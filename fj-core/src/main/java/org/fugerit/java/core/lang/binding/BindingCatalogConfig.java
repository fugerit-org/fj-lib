package org.fugerit.java.core.lang.binding;

import java.io.InputStream;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.helpers.XMLConfigurableObject;
import org.fugerit.java.core.cfg.xml.CustomListCatalogConfig;
import org.fugerit.java.core.cfg.xml.XmlBeanHelper;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.util.collection.ListMapStringKey;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Simple binding A.P.I.
 * 
 * An example can be found in the junit : 
 * fj-core/src/test/java/test/org/fugerit/java/core/lang/binding/TestBindingCatalog.java
 * And its config file : 
 * fj-core/src/test/resources/core/lang/binding/binding-catalog.xml
 *
 * Some binding helper are always available :
 * default-helper @see {@link BindingHelperDefault}
 * string-value @see {@link BindingHelperStringValue}
 * 
 * @author Matteo a.k.a. Fugerit
 *
 */
public class BindingCatalogConfig extends CustomListCatalogConfig<BindingFieldConfig, BindingConfig> {

	public static final String ID_DEFAULT_HELPER = "default-helper";
	public static final String ID_STRING_VALUE_HELPER = "string-value";
	
	public static final String ATT_BINDING_HELPER = "binding-helper";
	
	public BindingCatalogConfig() {
		super( "binding", "field" );
		this.getGeneralProps().setProperty( ATT_TYPE , BindingFieldConfig.class.getName() );
		this.getGeneralProps().setProperty( ATT_LIST_TYPE , BindingConfig.class.getName() );
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5246222821349199544L;

	public static BindingCatalogConfig loadConfig( InputStream is ) throws Exception {
		BindingCatalogConfig config = new BindingCatalogConfig();
		load( is , config );
		return config;
	}
	
	@Override
	public void configure(Element tag) throws ConfigException {
		super.configure(tag);
		this.helperCatalog = new ListMapStringKey<>();
		this.helperCatalog.add( BindingHelperDefault.DEFAULT );
		this.helperCatalog.add( BindingHelperStringValue.DEFAULT );
		this.helperCatalog.add( BindingHelperDateToXML.DEFAULT );
		this.helperCatalog.add( BindingHelperXMLToDate.DEFAULT );
		this.helperCatalog.add( BindingHelperCollectionToObject.DEFAULT );
		NodeList helperTags = tag.getElementsByTagName( ATT_BINDING_HELPER );
		for ( int k=0; k<helperTags.getLength(); k++ ) {
			Element helperTag = (Element) helperTags.item( k );
			String id = helperTag.getAttribute( "id" );
			String type = helperTag.getAttribute( "type" );
			try {
				BindingHelper helper = (BindingHelper)ClassHelper.newInstance( type );
				helper.setId( id );
				XmlBeanHelper.setFromElementAtts( helper , helperTag );
				if ( helper instanceof XMLConfigurableObject ) {
					((XMLConfigurableObject)helper).configure( helperTag );
				}
				this.helperCatalog.add( helper );
			} catch (Exception e) {
				throw new ConfigException( "Error configuring helper : "+id, e );
			}
		}
		for ( String bindingId : this.getIdSet() ) {
			this.getListMap( bindingId ).setCatalog( this );
		}
	}

	
	
	private ListMapStringKey<BindingHelper> helperCatalog;
	
	public void bind( String bindingId, Object from, Object to ) throws BindingException {
		BindingConfig binding = this.getListMap( bindingId );
		for ( BindingFieldConfig field : binding ) {
			BindingHelper helper = BindingHelperDefault.DEFAULT;
			String helperId = field.getHelper();
			if ( StringUtils.isNotEmpty( helperId ) ) {
				helper = this.helperCatalog.get( helperId );
			}
			if ( helper == null ) {
				throw new BindingException( "No helper found for id "+helperId );
			} else {
				helper.bind(binding, field, from, to);
			}
		}
	}
	
}
