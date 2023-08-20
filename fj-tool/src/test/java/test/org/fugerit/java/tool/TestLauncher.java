package test.org.fugerit.java.tool;

import static org.junit.Assert.assertTrue;

import org.fugerit.java.core.cli.ArgUtils;
import org.fugerit.java.tool.Launcher;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestLauncher {

	@Test
	public void test() {
		boolean ok = false;
		try {
			String[] params = {
					ArgUtils.getArgString( Launcher.ARG_HELP ),
					"1"
				};
			Launcher.main( params );
			ok = true;
		} catch (Exception e) {
			String message = "Error : "+e;
			log.error( message, e );
		}
		assertTrue( ok );
	}

}
