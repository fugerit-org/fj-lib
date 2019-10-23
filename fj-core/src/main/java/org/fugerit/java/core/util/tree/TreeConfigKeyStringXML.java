package org.fugerit.java.core.util.tree;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.fugerit.java.core.util.collection.ListMapStringKey;
import org.w3c.dom.Element;

public class TreeConfigKeyStringXML<T extends NodeKeyString<T, L>, L extends ListMapStringKey<T>> extends TreeConfigXML<T, L> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3723689013500668456L;

	private Map<String, T> map;
	
	public TreeConfigKeyStringXML() {
		this.map = new HashMap<String, T>();
	}

	@Override
	protected T setupData(Element tag) throws Exception {
		T node = super.setupData(tag);
		if ( node != null ) {
			this.map.put( node.getKey() , node );
		}
		return node;
	}

	public T findNodeByKey( String key ) {
		return this.map.get( key );
	}
	
	public Set<String> keySet() {
		return this.map.keySet();
	}
	
	public Collection<T> values() {
		return this.map.values();
	}
	
}
