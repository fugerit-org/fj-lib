/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		https://www.fugerit.org/
	
	SCM site :
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.db.metadata;

import org.fugerit.java.core.util.collection.KeyObject;

/**
 * <p>Wrapper for a database table column.</p>
 * 
 * <p>The info are similar to the ones returned by a ResultSetMetadata object.</p>
 * 
 * <p>This class implements KeyObject interface, the getKey() method returns a lowercase() versione of getName().</p>
 *
 * @author Fugerit
 *
 */
public class ColumnModel implements KeyObject<String> {

	public static final int NULLABLE_FALSE = 0;
	public static final int NULLABLE_TRUE = 1;
	public static final int NULLABLE_UNKNOWN = -1;
	
	public String toString() {
		return this.getClass().getSimpleName()+"[name:"+this.getName()+";typeSql:"+this.getTypeSql()+";typeName:"+this.typeName+"]";
	}
	
	private String name;
	
	private int typeSql;
	
	private int nullable;
	
	private int size;
	
	private String typeName;
	
	private String comment;
	
	private String extra;
	
	private String javaType;
	

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getTypeSql() {
		return typeSql;
	}

	public void setTypeSql(int typeSql) {
		this.typeSql = typeSql;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getNullable() {
		return nullable;
	}

	public void setNullable(int nullable) {
		this.nullable = nullable;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	@Override
	public String getKey() {
		return this.getName().toLowerCase();
	}
	
}
