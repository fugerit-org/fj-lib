package org.fugerit.java.core.db.daogen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.fugerit.java.core.db.dao.FieldList;
import org.fugerit.java.core.lang.helpers.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * Query helper for select operations.
 * 
 * {@link QueryHelper} for basic informations.
 * 
 * @author Matteo Franci a.k.a. Fugerit
 *
 */
public class SelectHelper extends QueryHelper {
	
	/**
	 * Constant for empty string
	 */
	public static final String MODE_NONE = "";
	
	/**
	 * Constant for 'AND'
	 */
	public static final String MODE_AND = " AND ";
	
	/**
	 * Constant for 'OR'
	 */
	public static final String MODE_OR = " OR ";
	
	/**
	 * Constant for '='
	 */
	public static final String COMPARE_EQUAL = " = ";
	
	/**
	 * Constant for '&lt;&gt;'
	 */
	public static final String COMPARE_DIFFERENT = " <> ";
	

	/**
	 * Constant for '&lt;'
	 */
	public static final String COMPARE_LT = " < ";
	
	/**
	 * Constant for '&gt;'
	 */
	public static final String COMPARE_GT = " > ";
	
	/**
	 * Constant for 'LIKE'
	 */
	public static final String COMPARE_LIKE = " LIKE ";
	
	/**
	 * Constant for '&lt;='
	 */
	public static final String COMPARE_LT_EQUAL = " <= ";

	/**
	 * Constant for '&gt;='
	 */
	public static final String COMPARE_GT_EQUAL = " >= ";

	/**
	 * Constant for 'ASC'
	 */
	public static final String ORDER_ASC = "ASC";
	
	/**
	 * Constant for 'DESC'
	 */
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
	
	/**
	 * Constructor for a SelectHelper
	 * 
	 * @param table	the table to be wrapped
	 * @param fl		the field list
	 */
	public SelectHelper( String table, FieldList fl ) {
		super( table, fl );
		this.firstParam = true;
		this.orderByList = new ArrayList<>();
	}

	/**
	 * Initialize this helper.
	 * 
	 */
	public void initSelectEntity() {
		this.getQuery().append( "SELECT * FROM " );
		this.getQuery().append( this.getTable() );
	}
	
	/**
	 * If no field has been set, WHERE will be added, otherwise 'mode' will be added. 
	 * 
	 * @param mode	the mode to be added ( AND, OR )
	 * @return	<code>true</code> if WHERE has been added, <code>false</code> otherwise. 
	 * 
	 * @since 8.0.1
	 */
	public boolean addWhereOrMode( String mode ) {
		boolean res = this.firstParam;
		if ( this.firstParam ) {
			this.firstParam = false;
			this.getQuery().append( " WHERE " );
		} else {
			this.getQuery().append( mode );
		}
		return res;
	}
	
	/**
	 * Add a parameter to the query.
	 * The parameter is added if and only if the value is not null.
	 * If you want to add a null comparison use addNullComparison() method()
	 * 
	 * @param columnName	the name of the column
	 * @param value			the value
	 * @param mode			the mode (AND, OR)
	 * @param compare		the comparison type ( = , &lt;&gt;, &gt;, &lt;, &lt;=, &gt;=, LIKE )
	 * @param parameter		the parameter (for example section '?' or 'UPPER(?)')
	 * @return <code>true</code> if the parameter has been added, <code>false</code> otherwise.
	 * 
	 * @since 8.0.1
	 */
	public boolean addParam( String columnName, Object value, String mode, String compare, String parameter ) {
		boolean added = (value != null);
		if ( added ) {
			this.addWhereOrMode(mode);
			this.getQuery().append( columnName );
			this.getQuery().append( compare );
			this.getQuery().append( parameter );
			this.getFields().addField( value );
		}
		return added;
	}
	
	/**
	 * Add a NULL check to the query.
	 * 
	 * @param columnName	the name of the column
	 * @param isNull <code>true</code> to add 'IS NULL' check, <code>false</code> to add 'IS NOT NULL' check.
	 * @param mode			the mode (AND, OR)
	 * @return  <code>true</code> if the parameter has been added, <code>false</code> otherwise.
	 * 
	 * @since 8.0.1
	 */
	public boolean addNullComparison( String columnName, String mode, boolean isNull ) {
		boolean res = true;
		this.addWhereOrMode(mode); 
		this.getQuery().append( columnName );
		if ( isNull ) {
			this.getQuery().append( " IS NULL " );
		} else {
			this.getQuery().append( " IS NOT NULL " );
		}
		return res;
	}
	

	/**
	 * Add a parameter to the query.
	 * The parameter is added if and only if the value is not null.
	 * If you want to add a null comparison use addNullComparison() method()
	 * The parameter will be added as a parameter (?) for prepared statement
	 * 
	 * @param columnName	the name of the column
	 * @param value			the value
	 * @param mode			the mode (AND, OR)
	 * @param compare		the comparison type ( = , &lt;&gt;, &gt;, &lt;, &lt;=, &gt;=, LIKE )
	 * @return <code>true</code> if the parameter has been added, <code>false</code> otherwise.
	 * 
	 */
	public boolean addParam( String columnName, Object value, String mode, String compare ) {
		return this.addParam(columnName, value, mode, compare, QUESTION_MARK);
	}

	/**
	 * Add a parameter to the query.
	 * The parameter is added if and only if the value is not null.
	 * If you want to add a null comparison use addNullComparison() method().
	 * The parameter will be added as a parameter (?) for prepared statement.
	 * The mode will be OR and the comparison =
	 * 
	 * @param columnName	the name of the column
	 * @param value			the value
	 * @return <code>true</code> if the parameter has been added, <code>false</code> otherwise.
	 */
	public boolean orEqualParam( String columnName, Object value ) {
		return this.addParam( columnName , value, MODE_OR, COMPARE_EQUAL );
	}

	/**
	 * Add a parameter to the query.
	 * The parameter is added if and only if the value is not null.
	 * If you want to add a null comparison use addNullComparison() method().
	 * The parameter will be added as a parameter (?) for prepared statement.
	 * The mode will be OR and the comparison LIKE
	 * 
	 * @param columnName	the name of the column
	 * @param value			the value
	 * @return <code>true</code> if the parameter has been added, <code>false</code> otherwise.
	 */
	public boolean orLikeParam( String columnName, Object value ) {
		return this.addParam( columnName , value, MODE_OR, COMPARE_LIKE );
	}
	
	/**
	 * Add a parameter to the query.
	 * The parameter is added if and only if the value is not null.
	 * If you want to add a null comparison use addNullComparison() method().
	 * The parameter will be added as a parameter (?) for prepared statement.
	 * The mode will be AND and the comparison =
	 * 
	 * @param columnName	the name of the column
	 * @param value			the value
	 * @return <code>true</code> if the parameter has been added, <code>false</code> otherwise.
	 */
	public boolean andEqualParam( String columnName, Object value ) {
		return this.addParam( columnName , value, MODE_AND, COMPARE_EQUAL );
	}
	
	/**
	 * Add a parameter to the query.
	 * The parameter is added if and only if the value is not null.
	 * If you want to add a null comparison use addNullComparison() method().
	 * The parameter will be added as a parameter (?) for prepared statement.
	 * The mode will be AND and the comparison LIKE
	 * 
	 * @param columnName	the name of the column
	 * @param value			the value
	 * @return <code>true</code> if the parameter has been added, <code>false</code> otherwise.
	 */
	public boolean andLikeParam( String columnName, Object value ) {
		return this.addParam( columnName , value, MODE_AND, COMPARE_LIKE );
	}
	
	/**
	 * Add ORDER BY to the query.
	 * 
	 * @param columnName	the column name
	 * @param orderByMode	the order mode (ASC, DESC)
	 */
	public void addOrderBy( String columnName, String orderByMode ) {
		this.orderByList.add( new OrderByHandler(columnName, orderByMode) );
	}

	/**
	 * Directly add ORDER BY to the query.
	 * 
	 * @param orderBy string "ORDER BY ${orderBy}" will be added to the query buffer.
	 */
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

@AllArgsConstructor
class OrderByHandler implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6753430035123845585L;

	@Getter private String columnName;
	
	@Getter private String orderByMode;
	
}
