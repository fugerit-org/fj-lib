package org.fugerit.java.core.db.daogen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.fugerit.java.core.db.dao.FieldList;
import org.fugerit.java.core.lang.helpers.StringUtils;

public class SelectHelper extends QueryHelper {
	
	public static final String MODE_NONE = "";
	
	public static final String MODE_AND = " AND ";
	
	public static final String MODE_OR = " OR ";
	
	public static final String COMPARE_EQUAL = " = ";
	
	public static final String COMPARE_DIFFERENT = " <> ";
	
	public static final String COMPARE_LT = " < ";
	
	public static final String COMPARE_GT = " > ";
	
	public static final String COMPARE_LIKE = " LIKE ";
	
	public static final String COMPARE_LT_EQUAL = " <= ";
	
	public static final String COMPARE_GT_EQUAL = " >= ";
	
	public static final String ORDER_ASC = "ASC";
	
	public static final String ORDER_DESC = "DESC";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4923486564445809747L;
	
	private boolean firstParam;
	
	private List<OrderByHandler> orderByList;
	
	/**
	 * Creates a new SelectHelper
	 * 
	 * NOTE : initSelectEntity() should not be invoked.
	 * 
	 * @param table			the entity this select helper is meant for.
	 * @param fl			the list of fields
	 * @param firstParam	<code>true</code> if WHERE must be set, <code>false</code> othewise.
	 * @param queryView		the query to use instead of default query build on TABLE name.
	 * @return				a new SelectHelper
	 */
	public static SelectHelper newCustomSelectHelper( String table, FieldList fl, boolean firstParam, String queryView ) {
		SelectHelper helper = new SelectHelper(table, fl);
		helper.firstParam = firstParam;
		helper.appendToQuery( queryView );
		return helper;
	}
	
	public SelectHelper( String table, FieldList fl ) {
		super( table, fl );
		this.firstParam = true;
		this.orderByList = new ArrayList<>();
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
			this.getQuery().append( QUESTION_MARK );
			this.getFields().addField( value );
		}
		return added;
	}

	public boolean orEqualParam( String columnName, Object value ) {
		return this.addParam( columnName , value, MODE_OR, COMPARE_EQUAL );
	}
	
	public boolean orLikeParam( String columnName, Object value ) {
		return this.addParam( columnName , value, MODE_OR, COMPARE_LIKE );
	}
	
	public boolean andEqualParam( String columnName, Object value ) {
		return this.addParam( columnName , value, MODE_AND, COMPARE_EQUAL );
	}
	
	public boolean andLikeParam( String columnName, Object value ) {
		return this.addParam( columnName , value, MODE_AND, COMPARE_LIKE );
	}
	
	public void addOrderBy( String columnName, String orderByMode ) {
		this.orderByList.add( new OrderByHandler(columnName, orderByMode) );
	}

	public void addOrderBy( String orderBy ) {
		if ( StringUtils.isNotEmpty( orderBy ) ) {
			this.appendToQuery( " ORDER BY " );
			this.appendToQuery( orderBy );
			this.appendToQuery( " " );
		}
	}
	
	@Override
	public String getQueryContent() {
		StringBuilder query = new StringBuilder();
		query.append( super.getQueryContent() );
		if ( !this.orderByList.isEmpty() ) {
			query.append( " ORDER BY " );
			boolean firstOderBy = true;
			Iterator<OrderByHandler> itOrderBy = this.orderByList.iterator();
			while ( itOrderBy.hasNext() ) {
				OrderByHandler current = itOrderBy.next();
				query.append( current.getColumnName() );
				query.append( " " );
				query.append( current.getOrderByMode() );
				query.append( " " );
				if ( firstOderBy ) {
					firstOderBy = false;
				} else {
					query.append( ", " );
				}
			}			
		}
		return query.toString();
	}
	
}

class OrderByHandler implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6753430035123845585L;

	private String columnName;
	
	private String orderByMode;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getOrderByMode() {
		return orderByMode;
	}

	public void setOrderByMode(String orderByMode) {
		this.orderByMode = orderByMode;
	}

	public OrderByHandler(String columnName, String orderByMode) {
		super();
		this.columnName = columnName;
		this.orderByMode = orderByMode;
	}
	
}
