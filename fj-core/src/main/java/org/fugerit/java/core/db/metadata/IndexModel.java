/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		http://www.fugerit.org/java/
	
	SCM site :
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.db.metadata;

import org.fugerit.java.core.util.collection.KeyObject;

/**
 * 
 *
 * @author Fugerit
 *
 */
public class IndexModel extends ColumnContainer implements KeyObject<String> {
	
	private String name;
	
	/*
	 * @return Restituisce il valore di name.
	 */
	public String getName() {
		return name;
	}

	/*
	 * @param name il valore di name da impostare.
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getKey() {
		return this.getName();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+"[name:"+this.getName()+"]";
	}
		
}
