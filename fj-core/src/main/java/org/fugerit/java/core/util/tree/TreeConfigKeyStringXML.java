package org.fugerit.java.core.util.tree;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.fugerit.java.core.util.collection.ListMapStringKey;
import org.w3c.dom.Element;

public class TreeConfigKeyStringXML<T extends NodeKeyString<T, L>, L extends ListMapStringKey<T>> extends TreeConfigXML<T, L> {

	// code added to setup a basic conditional serialization - START
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		// this class is conditionally serializable, depending on contained object
		// you are encouraged to handle special situation using this method
		out.defaultWriteObject();
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		// this class is conditionally serializable, depending on contained object
		// you are encouraged to handle special situation using this method
		in.defaultReadObject();
	}
	
	// code added to setup a basic conditional serialization - END
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3723689013500668456L;

	private HashMap<String, T> map;
	
	public TreeConfigKeyStringXML() {
		this.map = new HashMap<>();
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
