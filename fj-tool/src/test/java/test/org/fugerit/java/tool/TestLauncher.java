package test.org.fugerit.java.tool;

import org.fugerit.java.core.cli.ArgUtils;
import org.fugerit.java.tool.Launcher;
import org.junit.Test;

public class TestLauncher {

	@Test
	public void test() {
		String[] params = {
			ArgUtils.getArgString( Launcher.ARG_HELP ),
			"1"
		};
		Launcher.main( params );
	}

}
