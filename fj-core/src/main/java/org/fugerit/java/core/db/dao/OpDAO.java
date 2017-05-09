package org.fugerit.java.core.db.dao;
/*
 * <p>.</p>
 *
 * @author Fugerit
 */
public class OpDAO {


	public static OpDAO newQueryOp( String sql, RSExtractor rse ) {
		return newQueryOp(sql, new FieldList( new FieldFactory() ), rse );
	}

	public static OpDAO newUpdateOp( String sql ) {
		return newUpdateOp(sql, new FieldList( new FieldFactory() ) );
	}
	
	public static OpDAO newExecuteOp( String sql ) {
		return newExecuteOp(sql, new FieldList( new FieldFactory() ) );
	}
	
	public static OpDAO newExecuteOp( String sql, FieldList fl ) {
		OpDAO op = new OpDAO();
		op.setType( TYPE_EXECUTE );
		op.setFieldList( fl );
		op.setSql( sql );
		return op;
	}
	
	public static OpDAO newQueryOp( String sql, FieldList fl, RSExtractor rse ) {
		OpDAO op = new OpDAO();
		op.setType( TYPE_QUERY );
		op.setFieldList( fl );
		op.setSql( sql );
		op.setRsExtractor( rse );
		return op;
	}
	
	public static OpDAO newUpdateOp( String sql, FieldList fl ) {
		OpDAO op = new OpDAO();
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
	
	private RSExtractor rsExtractor;
	
	private int type;

	public FieldList getFieldList() {
		return fieldList;
	}

	public RSExtractor getRsExtractor() {
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

	public void setRsExtractor(RSExtractor extractor) {
		rsExtractor = extractor;
	}

	public void setSql(String string) {
		sql = string;
	}

	public void setType(int i) {
		type = i;
	}

	protected void finalize() throws Throwable {
		super.finalize();
		this.fieldList = null;
		this.sql = null;
	}

}
