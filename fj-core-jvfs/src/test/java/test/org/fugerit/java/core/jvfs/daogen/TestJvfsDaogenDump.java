package test.org.fugerit.java.core.jvfs.daogen;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.daogen.base.config.DaogenConfigDump;
import org.junit.Test;

import test.org.fugerit.java.core.jvfs.TestJVFSHelper;
import test.org.fugerit.java.core.jvfs.db.helper.MemJvfsDBHelper;

public class TestJvfsDaogenDump extends TestJVFSHelper {
	
	public static final String OUTPUT_DUMP = "target/daogen-dump-jvfs.xml";
	
	@Test
	public void testDumpJvfsDB() {
		try ( FileWriter writer = new FileWriter( new File( OUTPUT_DUMP ) ) ) {
			ConnectionFactory cf = MemJvfsDBHelper.newCF();
			Properties params = new Properties();
			List<String> tableList = new ArrayList<>();
			tableList.add( "DB_JVFS_FILE" );
			DaogenConfigDump.dumpConfig(  cf, params, writer, tableList);
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
	
}
