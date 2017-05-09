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
package org.fugerit.java.core.db.dao.rse;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.fugerit.java.core.db.dao.RSExtractor;
import org.fugerit.java.core.util.collection.OptionItem;

/**
 * Result Set Extractor for OptionItem
 *
 * @author Fugerit
 *
 */
public class OptionItemRSE implements RSExtractor<OptionItem> {

	public static OptionItemRSE getInstance( String valueAndLabelFIeld ) {
		return new OptionItemRSE( valueAndLabelFIeld, valueAndLabelFIeld );
	}
	
	public static OptionItemRSE getInstance( String valueField, String labelField ) {
		return new OptionItemRSE( valueField, labelField );
	}
	
	private String valueField;
	
	private String labelField;
	
	public OptionItemRSE( String valueField, String labelField ) {
		this.valueField = valueField;
		this.labelField = labelField;
	}
	
	/* (non-Javadoc)
	 * @see org.fugerit.java.core.db.dao.RSExtractor#extractNext(java.sql.ResultSet)
	 */
	public OptionItem extractNext(ResultSet rs) throws SQLException {
		String value = rs.getString( this.valueField );
		String label = rs.getString( this.labelField );
		OptionItem optionItem = new OptionItem( value, label );
		return optionItem;
	}

	public String getLabelField() {
		return labelField;
	}

	public String getValueField() {
		return valueField;
	}

}
