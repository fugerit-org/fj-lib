package org.fugerit.java.core.util.tree;

import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.helpers.XMLConfigurableObject;
import org.fugerit.java.core.cfg.xml.GenericListCatalogConfig;
import org.fugerit.java.core.cfg.xml.XmlBeanHelper;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.xml.dom.DOMUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TreeConfigXML<T extends Node<T, L>, L extends Collection<T>> extends XMLConfigurableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 543412342345323L;

	public static final String ATT_TYPE = GenericListCatalogConfig.ATT_TYPE;

	public static final String ATT_LIST_TYPE = GenericListCatalogConfig.ATT_LIST_TYPE;

	public static final String TAG_TREE = "tree";

	public static final String TAG_NODE = "node";
	
	private Properties generalProps;
	
	private Node<T, L> tree;
	
	protected Collection<TreeDecorator<T>> decorators;
	
	public Node<T, L> getTree() {
		return tree;
	}
	
	public TreeConfigXML() {
		this.generalProps = new Properties();
		this.decorators = new HashSet<>();
		this.tree = null;
	}
	
	@SuppressWarnings("unchecked")
	protected void addKids( NodeList childs, T parent ) throws Exception {
		for ( int k=0; k<childs.getLength(); k++ ) {
			if ( childs.item( k ).getNodeType() == Element.ELEMENT_NODE ) {
				Element current = (Element)childs.item( k );
				if ( TAG_NODE.equals( current.getTagName() ) ) {
					if ( parent.accessChildren() == null ) {
						String type = this.getGeneralProps().getProperty( ATT_LIST_TYPE );
						L kids = (L) ClassHelper.newInstance( type );
						parent.initChildren( kids );
					}
					T node = this.setupData( current );
					this.setupData(node, parent, current );
					parent.accessChildren().add( node );
					addKids( current.getChildNodes() , node );
				}
			}
		}
	}
	
	protected void setupData( T current, T parent, Element tag ) throws Exception {
		for ( TreeDecorator<T> decorator : this.decorators ) {
			decorator.setupData(current, parent, tag);
		}
	}
	
	protected T setupData( Element tag ) throws Exception {
		String type = this.getGeneralProps().getProperty( ATT_TYPE );
		T obj = XmlBeanHelper.setFromElement( type , tag );
		return obj;
	}
	
	@Override
	public void configure(Element tag) throws ConfigException {
		DOMUtils.attributesToProperties( tag , this.getGeneralProps() );
		for ( TreeDecorator<T> dec : this.decorators ) {
			dec.init( this.generalProps , tag );
		}
		NodeList childs = tag.getChildNodes();
		T root = null;
		for ( int k=0; k<childs.getLength(); k++ ) {
			if ( childs.item( k ).getNodeType() == Element.ELEMENT_NODE ) {
				Element current = (Element)childs.item( k );
				if ( TAG_NODE.equals( current.getTagName() ) ) {
					if ( root == null ) {
						NodeList currentKids = current.getChildNodes();
						try {
							root = this.setupData( current );
							this.setupData( root, null, current );
							this.addKids( currentKids, root );
							this.tree = root;
						} catch (Exception e) {
							throw new ConfigException( e );
						}
						
					} else {
						throw new ConfigException( "Multiple root noode not allowed" );
					}
				}
			}
		}
	}

	public Properties getGeneralProps() {
		return generalProps;
	}

}
