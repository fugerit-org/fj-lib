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

import java.util.ArrayList;
import java.util.List;

import org.fugerit.java.core.lang.helpers.CollectionUtils;
import org.fugerit.java.core.lang.helpers.ConcatHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.util.collection.KeyObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Identifier for a table in a database.
 *
 * @author Fugerit
 *
 */
@AllArgsConstructor
@NoArgsConstructor
public class TableId implements KeyObject<String> {

	public static String generateKey( TableId tableId ) {
		return ConcatHelper.concatWithDefaultSeparator( 
				tableId.getTableCatalog(), tableId.getTableSchema(), tableId.getTableName() );
	}
	
	@Override
	public String getKey() {
		return generateKey(this);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+"[tableName:"+this.getTableName()+",tableSchema:"+this.getTableSchema()+"]";
	}
	
	@Getter @Setter private String tableName;
	
	@Getter @Setter private String tableSchema;
	
	@Getter @Setter private String tableCatalog;

	private static boolean equals( Object o1, Object o2 ) {
		return ( o1 == null && o2 == null ) || ( o1 != null && o1.equals( o2 ) );
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean eq = false;
		if ( obj instanceof TableId ) {
			TableId tableId = (TableId)obj;
			eq = equals( this.getTableCatalog(), tableId.getTableCatalog() )
				&& equals( this.getTableSchema(), tableId.getTableSchema() )
				&& equals( this.getTableName(), tableId.getTableName() );
		}
		return eq;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int h = Integer.MIN_VALUE;
		if ( this.getTableCatalog()!=null ) {
			h+= tableCatalog.hashCode();
		}
		if ( this.getTableName()!=null ) {
			h+= getTableName().hashCode();
		}
		if ( this.getTableSchema()!=null ) {
			h+= this.getTableSchema().hashCode();
		}
		return h;
	}
	
	public String toIdString() {
		List<String> list = new ArrayList<String>();
		CollectionUtils.addIfNotNull( list , this.getTableCatalog() );
		CollectionUtils.addIfNotNull( list , this.getTableSchema() );
		CollectionUtils.addIfNotNull( list , this.getTableName() );
		return StringUtils.concat(  ".", list );
	}
	
}
