package test.org.fugerit.java.tool;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Properties;

import org.fugerit.java.tool.Launcher;
import org.fugerit.java.tool.ToolHandlerHelper;
import org.fugerit.java.tool.fixed.ExtractFixedConfigHandler;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class TestExtractFixedConfigHandler {

	@Test
	void test() {
		boolean ok = false;
		try {
			Properties params = new Properties();
			params.setProperty( Launcher.ARG_TOOL , "fixed-config-extract");
			params.setProperty( ExtractFixedConfigHandler.PARAM_INPUT_FILE , "src/test/resources/tool/fixed/sample_input.xlsx");
			params.setProperty( ExtractFixedConfigHandler.PARAM_OUTPUT_XML , "target/sample_fixed_output.xml");
			ExtractFixedConfigHandler handler = new ExtractFixedConfigHandler();
			int res = handler.handle(params);
			ok = (res == ToolHandlerHelper.EXIT_OK);
		} catch (Exception e) {
			String message = "Error : "+e;
			log.error( message, e );
			fail( message );
		}
		assertTrue( ok );
	}
	
	@Test
	void testKo1() {
		boolean ok = false;
		try {
			Properties params = new Properties();
			params.setProperty( Launcher.ARG_TOOL , "fixed-config-extract");
			params.setProperty( ExtractFixedConfigHandler.PARAM_INPUT_FILE , "src/test/resources/tool/fixed/sample_input_fail.xlsx");
			params.setProperty( ExtractFixedConfigHandler.PARAM_OUTPUT_XML , "target/sample_fixed_output_fail_1.xml");
			ExtractFixedConfigHandler handler = new ExtractFixedConfigHandler();
			int res = handler.handle(params);
			ok = (res != ToolHandlerHelper.EXIT_OK);
		} catch (Exception e) {
			String message = "Error : "+e;
			log.error( message, e );
			fail( message );
		}
		assertTrue( ok );
	}
	
}
