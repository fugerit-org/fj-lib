package test.org.fugerit.java.core.jvfs;

import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.db.JMountDaogenDB;
import org.junit.Test;

import test.org.fugerit.java.core.jvfs.db.helper.MemJvfsDBHelper;

public class TestCopyRealToDb extends TestJVFSHelper {

	@Test
	public void testCopy02() {
		try  {
			JVFS from = TestJMountHelper.getRealJVFSDefault();
			JVFS to =  JMountDaogenDB.createJVFS( MemJvfsDBHelper.newCF(), this.facade );
			this.testWriteRead(from, to);
			this.tryDumpTestDb();
		} catch (Exception e) {
			this.failEx(e);
		}
	}
	
}
