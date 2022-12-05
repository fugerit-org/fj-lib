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
package org.fugerit.java.core.db.dao;

/**
 * <p>Class for handling SQL operations.</p>
 *
 * @author Fugerit
 */
public class OpDAO<T> {


	public static <T> OpDAO<T> newQueryOp( String sql, RSExtractor<T> rse ) {
		return newQueryOp(sql, new FieldList( new FieldFactory() ), rse );
	}

	public static <T> OpDAO<T> newUpdateOp( String sql ) {
		return newUpdateOp(sql, new FieldList( new FieldFactory() ) );
	}
	
	public static <T> OpDAO<T> newExecuteOp( String sql ) {
		return newExecuteOp(sql, new FieldList( new FieldFactory() ) );
	}
	
	public static <T> OpDAO<T> newExecuteOp( String sql, FieldList fl ) {
		OpDAO<T> op = new OpDAO<T>();
		op.setType( TYPE_EXECUTE );
		op.setFieldList( fl );
		op.setSql( sql );
		return op;
	}
	
	public static <T> OpDAO<T> newQueryOp( String sql, FieldList fl, RSExtractor<T> rse ) {
		OpDAO<T> op = new OpDAO<T>();
		op.setType( TYPE_QUERY );
		op.setFieldList( fl );
		op.setSql( sql );
		op.setRsExtractor( rse );
		return op;
	}
	
	public static <T> OpDAO<T> newUpdateOp( String sql, FieldList fl ) {
		OpDAO<T> op = new OpDAO<T>();
		op.setType( TYPE_UPDATE );
		op.setFieldList( fl );
		op.setSql( sql );
		return op;
	}

	public final static int TYPE_UPDATE = 0;
	public final static int TYPE_QUERY = 1;
	public final static int TYPE_EXECUTE = 2;

	private String sql;
	
	private FieldList fieldList;
	
	private RSExtractor<T> rsExtractor;
	
	private int type;

	public FieldList getFieldList() {
		return fieldList;
	}

	public RSExtractor<T> getRsExtractor() {
		return rsExtractor;
	}
	
	public String getSql() {
		return sql;
	}
	
	public int getType() {
		return type;
	}

	public void setFieldList(FieldList list) {
		fieldList = list;
	}

	public void setRsExtractor(RSExtractor<T> extractor) {
		rsExtractor = extractor;
	}

	public void setSql(String string) {
		sql = string;
	}

	public void setType(int i) {
		type = i;
	}

}
