package org.fugerit.java.core.db.daogen;

import org.fugerit.java.core.db.dao.FieldList;

public class UpdateHelper extends QueryHelper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4923486564445809747L;

	private boolean firstSetParam;

	private boolean firstWhereParam;

	public UpdateHelper(String table, FieldList fl) {
		super(table, fl);
		this.firstSetParam = true;
		this.firstWhereParam = true;
		this.getQuery().append("UPDATE ");
		this.getQuery().append(this.getTable());
	}

	public void addSetParam(String columnName, Object value) {
		if (this.firstSetParam) {
			this.firstSetParam = false;
			this.getQuery().append(" SET ");
		} else {
			this.getQuery().append(" , ");
		}
		this.getQuery().append(columnName);
		if (value == null) {
			this.getQuery().append(" = NULL ");
		} else {
			this.getQuery().append(" = ? ");
			this.getFields().addField(value);
		}
	}

	public void andWhereParam(String columnName, Object value) {
		if (this.firstWhereParam) {
			this.firstWhereParam = false;
			this.getQuery().append(" WHERE ");
		} else {
			this.getQuery().append(" AND ");
		}
		this.getQuery().append(columnName);
		this.getQuery().append(" = ? ");
		this.getFields().addField(value);
	}
	
	@Override
	public String getQueryContent() {
		String content = super.getQueryContent();
		if ( !content.toUpperCase().contains( "WHERE" ) ) {
			throw new RuntimeException( "At least one condition must be set! "+content );
		}
		return content;
	}

}