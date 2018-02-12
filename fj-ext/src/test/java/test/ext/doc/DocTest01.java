package test.ext.doc;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.ext.doc.DocBase;
import org.fugerit.java.ext.doc.DocFacade;
import org.junit.Test;

public class DocTest01 {

	@Test
	public void test() {
		try {
			String testName = "test_01.xml";
			InputStream input = ClassHelper.getDefaultClassLoader().getResourceAsStream( "doc/"+testName );
			DocBase docBase = DocFacade.parse( input );
			FileOutputStream fos = new FileOutputStream( new File( "target/"+testName+".pdf" ) );
			DocFacade.createPDF( docBase , fos );
			fos.close();
		} catch (Exception e) {
			fail( "Exception : "+e );
			e.printStackTrace();
		}
		
	}

}
