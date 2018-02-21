package org.fugerit.java.ext.doc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * 
 * 
 * @author Matteo a.k.a. Fugerit
 *
 */
public class DocContainer extends DocElement {

	private List<DocElement> elementList;
	
	public DocContainer() {
		this.elementList = new ArrayList<DocElement>();
	}
	
	public Iterator<DocElement> docElements() {
		return this.elementList.iterator();
	}
	
	public int containerSize() {
		return this.elementList.size();
	}
	
	public void addElement( DocElement docElement ) {
		this.elementList.add( docElement );
	}
	
}
