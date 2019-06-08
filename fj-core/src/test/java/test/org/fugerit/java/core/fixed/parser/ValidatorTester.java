package test.org.fugerit.java.core.fixed.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Properties;

import org.fugerit.java.core.cli.ArgUtils;
import org.fugerit.java.core.fixed.parser.FixedFieldFileConfig;
import org.fugerit.java.core.fixed.parser.FixedFieldFileDescriptor;
import org.fugerit.java.core.fixed.parser.FixedFieldFileReader;
import org.fugerit.java.core.fixed.parser.FixedFileFieldMap;
import org.fugerit.java.core.fixed.parser.FixedFileFieldValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidatorTester {

	private static final Logger logger = LoggerFactory.getLogger(ValidatorTester.class);
	
	public static void printErrorsToLog( Iterator<FixedFileFieldValidationResult> errors ) {
		while ( errors.hasNext() ) {
			logger.info( errors.next().getMessage() );
		}
	}
	
	public static void main( String[] args ) {
		try {
			Properties params = ArgUtils.getArgs( args );
			File input = new File( params.getProperty( "input" ) );
			File definition = new File( params.getProperty( "definition" ) );
			FileInputStream configIS = new FileInputStream( definition );
			FixedFieldFileConfig config = FixedFieldFileConfig.parseConfig( configIS );
			configIS.close();
			FixedFieldFileDescriptor descriptor = config.getFileDescriptor( "tracciato_test" );
			FileInputStream fis = new FileInputStream( input );
			FixedFieldFileReader reader = FixedFieldFileReader.newInstance( descriptor, fis );
			while ( reader.hasNext() ) {
				FixedFileFieldMap map = reader.nextRawMap();
				if ( !map.getValidationErrors().isEmpty() ) {
					printErrorsToLog( map.getValidationErrors().iterator() );
				}
			}
			printErrorsToLog( reader.getGenericValidationErrors().iterator() );
			logger.info( "ERROR COUNT : "+reader.getErrorCount() );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
