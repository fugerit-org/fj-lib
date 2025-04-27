package test.org.fugerit.java.core.fixed.parser;

import org.fugerit.java.core.fixed.parser.FixedFileFieldValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class ValidatorTester {

	private static final Logger logger = LoggerFactory.getLogger(ValidatorTester.class);
	
	public static void printErrorsToLog( Iterator<FixedFileFieldValidationResult> errors ) {
		while ( errors.hasNext() ) {
			logger.info( errors.next().getMessage() );
		}
	}
	
}
