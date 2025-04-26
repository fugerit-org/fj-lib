package test.org.fugerit.java.core.fixed.parser;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.fixed.parser.FixedFieldFileConfig;
import org.fugerit.java.core.fixed.parser.FixedFieldFileDescriptor;
import org.fugerit.java.core.fixed.parser.helper.ReaderFixedFieldFileReader;
import org.fugerit.java.core.fixed.parser.helper.StreamFixedFieldFileReader;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import test.org.fugerit.java.BasicTest;

class TestFixedFieldFileConfig extends BasicTest {

	private static FixedFieldFileConfig config = null;
	
	@BeforeAll
	 static void readConfig() {
		boolean ok = false;
		try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( "core/fixed/fixed-file-config.xml" ) ) {
			config = FixedFieldFileConfig.parseConfig( is );
			logger.info( "config -> {}", config );
			if ( config != null ) {
				logger.info( "ids -> {}", config.getIds() );
			}
			ok = config != null;
		} catch (Exception e) {
			throw new ConfigRuntimeException( e );
		}
		Assertions.assertTrue( ok );
	}
	
	@Test
	void checkDescriptor() {
		boolean ok = false;
		try {
			FixedFieldFileDescriptor descriptor = config.getFileDescriptor( "testFixedValidator" );
			logger.info( "descriptor -> {}", descriptor );
			ok = descriptor != null;
			// stream
			try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( "core/fixed/input_test.txt" ) ) {
				StreamFixedFieldFileReader reader = new StreamFixedFieldFileReader(descriptor, is);
				if ( reader.hasNext() ) {
					logger.info( "line : {}", reader.nextRawMap() );
				}
			}
			// reader
			try ( InputStreamReader is = new InputStreamReader( ClassHelper.loadFromDefaultClassLoader( "core/fixed/input_test.txt" ) ) ) {
				ReaderFixedFieldFileReader reader = new ReaderFixedFieldFileReader(descriptor, is);
				if ( reader.hasNext() ) {
					logger.info( "line : {}", reader.nextRawMap() );
				}
			}
		} catch (Exception e) {
			this.failEx(e);
		}
		Assertions.assertTrue( ok );
	}
	
}
