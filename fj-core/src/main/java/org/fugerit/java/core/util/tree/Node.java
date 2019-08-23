package org.fugerit.java.core.util.tree;

import java.util.Collection;

public class Node<T extends Node<T, L>, L extends Collection<T>> {

	private Node<T, L> parent;
	
	public Node() {
		super();
		this.parent = null;
	}

	private L children;

	public L accessChildren() {
		return children;
	}

	public void initChildren(L children) {
		this.children = children;
	}

	public Node<T, L> accessParent() {
		return parent;
	}

	public void addParent(Node<T, L> parent) {
		this.parent = parent;
	}
	
	public static <T extends Node<T, L>, L extends Collection<T>> boolean isRoot( Node<T, L> node ) {
		return node != null && node.accessParent() == null;
	}
	
	public static <T extends Node<T, L>, L extends Collection<T>> boolean isLeaf( Node<T, L> node ) {
		return node != null && ( node.accessChildren() == null || node.accessChildren().isEmpty() );
	}

}
