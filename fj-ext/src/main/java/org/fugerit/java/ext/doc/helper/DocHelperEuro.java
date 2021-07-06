package org.fugerit.java.ext.doc.helper;

import org.fugerit.java.ext.doc.DocHelper;

public class DocHelperEuro extends DocHelper {

	@Override
	public String filterText(String temp) {
		StringBuilder text = new StringBuilder();
		for ( int k=0; k<temp.length(); k++ ) {
			char c = temp.charAt( k );
			int i = (int)c;
			if ( i == 164 ) {
				text.append( "à" );
			} else {
				text.append( c );
			}
		}
		return text.toString();
	}

	
	
}
