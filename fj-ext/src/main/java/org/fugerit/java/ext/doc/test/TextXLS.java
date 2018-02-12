package org.fugerit.java.ext.doc.test;

import java.io.File;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class TextXLS {

	public static void main( String[] args ) {
		try {
			WritableWorkbook ww = Workbook.createWorkbook( new File( "dist/test-excel.xls" ) );

			WritableSheet ws = ww.createSheet( "Test" , 0 );
			
			WritableCellFormat cf = new WritableCellFormat();
			cf.setBackground( Colour.GRAY_25 );
			
			ws.addCell( new Label( 0, 0, "Col 1", cf ) );
			ws.addCell( new Label( 1, 0, "Col 2", cf ) );
			
			for ( int k=0; k<10; k++ ) {
				ws.addCell( new Label( 0, k+1, "A "+k, cf ) );
				ws.addCell( new Number( 1, k+1, Math.random(), cf ) );
			}
			
			ww.write();
			ww.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
