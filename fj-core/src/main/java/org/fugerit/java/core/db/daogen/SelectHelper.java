package org.fugerit.java.core.db.daogen;

import org.fugerit.java.core.db.dao.FieldList;

public class SelectHelper extends QueryHelper {

	public static final String MODE_AND = " AND ";
	
	public static final String MODE_OR = " OR ";
	
	public static final String COMPARE_EQUAL = " = ";
	
	public static final String COMPARE_DIFFERENT = " <> ";
	
	public static final String COMPARE_LT = " < ";
	
	public static final String COMPARE_GT = " > ";
	
	public static final String COMPARE_LIKE = " LIKE ";
	
	public static final String COMPARE_LT_EQUAL = " <= ";
	
	public static final String COMPARE_GT_EQUAL = " >= ";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4923486564445809747L;
	
	private boolean firstParam;
	
	public SelectHelper( String table, FieldList fl ) {
		super( table, fl );
		this.firstParam = true;
	}

	public void initSelectEntity() {
		this.getQuery().append( "SELECT * FROM " );
		this.getQuery().append( this.getTable() );
	}
	
	public boolean addParam( String columnName, Object value, String mode, String compare ) {
		boolean added = (value != null);
		if ( added ) {
			if ( this.firstParam ) {
				this.firstParam = false;
				this.getQuery().append( " WHERE " );
			} else {
				this.getQuery().append( mode );
			}
			this.getQuery().append( columnName );
			this.getQuery().append( compare );
			this.getQuery().append( " ? " );
			this.getFields().addField( value );
		}
		return added;
	}
	
	public boolean andEqualParam( String columnName, Object value ) {
		return this.addParam( columnName , value, MODE_AND, COMPARE_EQUAL );
	}
	
	public boolean andLikeParam( String columnName, Object value ) {
		return this.addParam( columnName , value, MODE_AND, COMPARE_LIKE );
	}
	
}