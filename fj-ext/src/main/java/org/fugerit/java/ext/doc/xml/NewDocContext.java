package org.fugerit.java.ext.doc.xml;

import java.util.LinkedList;

import org.fugerit.java.ext.doc.DocBase;
import org.fugerit.java.ext.doc.DocContainer;
import org.fugerit.java.ext.doc.DocElement;
import org.fugerit.java.ext.doc.DocHelper;

public class NewDocContext {

	private DocBase docBase;
	
	private DocElement currentElement;
	
	private DocContainer currentContainer;
	
	private LinkedList parents;
	
	private DocHelper docHelper;

	public DocBase getDocBase() {
		return docBase;
	}

	public void setDocBase(DocBase docBase) {
		this.docBase = docBase;
	}

	public DocElement getCurrentElement() {
		return currentElement;
	}

	public void setCurrentElement(DocElement currentElement) {
		this.currentElement = currentElement;
	}

	public DocContainer getCurrentContainer() {
		return currentContainer;
	}

	public void setCurrentContainer(DocContainer currentContainer) {
		this.currentContainer = currentContainer;
	}

	public LinkedList getParents() {
		return parents;
	}

	public void setParents(LinkedList parents) {
		this.parents = parents;
	}

	public DocHelper getDocHelper() {
		return docHelper;
	}

	public void setDocHelper(DocHelper docHelper) {
		this.docHelper = docHelper;
	}
	
}
