package test.org.fugerit.java.core.jvfs;

import java.io.File;

import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.db.JMountDaogenDB;
import org.fugerit.java.core.jvfs.db.daogen.EntityDbJvfsFileFacade;
import org.fugerit.java.core.jvfs.db.daogen.impl.DataEntityDbJvfsFileFacade;
import org.fugerit.java.core.jvfs.file.RealJMount;
import org.fugerit.java.test.db.helper.MemDBHelper;
import org.junit.Test;

public class TestCopyRealToDb extends TestJVFSHelper {

	@Test
	public void testCopy02() {
		try  {
			String prefix = "FUGERIT.";
			DataEntityDbJvfsFileFacade facade = DataEntityDbJvfsFileFacade.newInstanceWithPrefix(prefix);
			JVFS from =  RealJMount.createJVFS( new File( "src/test/resources/core" ) );
			JVFS to =  JMountDaogenDB.createJVFS( MemDBHelper.newCF(), facade );
			this.testWriteRead(from, to);
			this.tryDumpTestDb( facade );
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
}
