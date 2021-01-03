package test.org.fugerit.java.core.validator;

import static org.junit.Assert.fail;

import java.util.Locale;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.util.PropertyEntry;
import org.fugerit.java.core.validator.ValidatorCatalog;
import org.fugerit.java.core.validator.ValidatorDate;
import org.fugerit.java.core.validator.ValidatorResult;
import org.junit.Test;

import test.org.fugerit.java.BasicTest;

public class TestValidatorCatalog extends BasicTest {

	private final static String CONFIG_PATH = "cl://core/validator/validator-catalog-test.xml";

	private static ValidatorCatalog catalog = null;
	static {
		try {
			catalog = ValidatorCatalog.init( CONFIG_PATH );
		} catch (ConfigException e) {
			logger.info( "Errore init : "+e, e );
		}
	}
	
	private void validatorWorker( String validatorId, ValidatorResult result, Locale l, String value, String label, boolean expected, Properties params ) {
		try {
			boolean valid = catalog.validate( validatorId , result, l, label, value, label, params );
			logger.info( "valid? -> {} ", valid );
			if ( expected != valid ) {
				fail( "Expected:"+expected+" != Result:"+valid );
			}
		} catch (Exception e) {
			String message = "Error : "+e;
			logger.error( message, e );
			fail( message );
		}
	}
	
	private void printResult( ValidatorResult result ) {
		for ( PropertyEntry entry : result.getErrors() ) {
			logger.info( "Error : {}", entry.getValue() );
		}
	}
	
	public void testRegexValidator001( Locale l ) {
		ValidatorResult result = new ValidatorResult();
		Properties params = new Properties();
		this.validatorWorker( "testRegexValidator" , result, l, "test value one", "test label 1 (valid)", true, params);
		this.validatorWorker( "testRegexValidatorExt" , result, l, "test value one", "test label 1 (not valid)", false, params);
		this.validatorWorker( "testRegexValidatorExt" , result, l, "t", "t (not valid)", false, params);
		this.validatorWorker( "testRegexValidator" , result, l, "test value 2 <>", "test label 2 (not valid)", false, params);
		this.validatorWorker( "testDateValidator" , result, l, "01/03/2020", "Valid date", true, params);
		this.validatorWorker( "testDateValidator" , result, l, "01/03-202A", "Invalid date 1", false, params);
		this.validatorWorker( "testDateValidator" , result, l, "01/03/2021", "Invalid date 2", false, params);
		this.validatorWorker( "testDateValidator" , result, l, "01/03/2019", "Invalid date 3", false, params);
		params.setProperty( ValidatorDate.KEY_MINDATE , "05/05/2020" );
		this.validatorWorker( "testDateValidator" , result, l, "01/03/2020", "Invalid date Params 1", false, params );
		//this.validatorWorker( "testNumberValidator1" , result, l, "100", "Valid Number 1", true, params );
		this.printResult(result);
	}
	
	@Test
	public void testRegexValidator001_EN() {
		this.testRegexValidator001( Locale.UK );
	}
	
	@Test
	public void testRegexValidator001_IT() {
		this.testRegexValidator001( Locale.ITALY );
	}
	
	@Test
	public void testNumberValidator001_EN() {
		Locale l = Locale.UK;
		ValidatorResult result = new ValidatorResult();
		Properties params = new Properties();
		this.validatorWorker( "testNumberValidator1" , result, l, "100", "Valid Number 1", true, params );
		this.validatorWorker( "testNumberValidator1" , result, l, "1,000.123", "Valid Number 2", true, params );
		this.validatorWorker( "testNumberValidator1" , result, l, "1,000.1234", "Invalid number 1", false, params );
		this.validatorWorker( "testNumberValidator1" , result, l, "1,000,134,234,243.123", "Invalid number 2", false, params );
		this.printResult(result);
	}
	
	@Test
	public void testNumberValidator001_IT() {
		Locale l = Locale.ITALY;
		ValidatorResult result = new ValidatorResult();
		Properties params = new Properties();
		this.validatorWorker( "testNumberValidator1" , result, l, "100", "Valid Number 1", true, params );
		this.validatorWorker( "testNumberValidator1" , result, l, "1.000,123", "Valid Number 2", true, params );
		this.validatorWorker( "testNumberValidator1" , result, l, "1.000,1234", "Invalid number 1", false, params );
		this.validatorWorker( "testNumberValidator1" , result, l, "1.000.134.234.243,123", "Invalid number 2", false, params );
		this.printResult(result);
	}
		
}
