package org.fugerit.java.core.util.tree;

import org.fugerit.java.core.util.collection.KeyString;
import org.fugerit.java.core.util.collection.ListMapStringKey;

public abstract class NodeKeyString<T extends Node<T, L>, L extends ListMapStringKey<T>> extends Node<T, L> implements KeyString {

	@Override
	public String toString() {
		return this.getClass().getName()+"[key:"+this.getKey()+"]"; 
	}
	
	
}
