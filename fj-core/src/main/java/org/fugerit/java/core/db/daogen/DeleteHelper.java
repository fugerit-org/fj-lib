package org.fugerit.java.core.db.daogen;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.db.dao.FieldList;

public class DeleteHelper extends QueryHelper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4923486564445809747L;

	private boolean firstWhereParam;

	public DeleteHelper(String table, FieldList fl) {
		super(table, fl);
		this.firstWhereParam = true;
		this.getQuery().append("DELETE FROM ");
		this.getQuery().append( this.getTable() );
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
			throw new ConfigRuntimeException( "At least one condition must be set! "+content );
		}
		return content;
	}

}
