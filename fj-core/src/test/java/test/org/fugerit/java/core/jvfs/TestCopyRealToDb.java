package test.org.fugerit.java.core.jvfs;

import java.io.File;

import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.db.JMountDaogenDB;
import org.fugerit.java.core.jvfs.file.RealJMount;
import org.fugerit.java.test.db.helper.MemDBHelper;
import org.junit.Test;

public class TestCopyRealToDb extends TestJVFSHelper {

	@Test
	public void testCopy02() {
		ConnectionFactory cf = MemDBHelper.newCF();
		try  {
			JVFS from =  RealJMount.createJVFS( new File( "src/test/resources/core" ) );
			JVFS to =  JMountDaogenDB.createJVFS( cf );
			this.testWriteRead(from, to);
			this.tryDumpTestDb();
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
}
