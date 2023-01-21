package test.org.fugerit.java.core.jvfs;

import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.db.JMountDaogenDB;
import org.fugerit.java.core.jvfs.db.daogen.impl.DataEntityDbJvfsFileFacade;
import org.junit.Test;

import test.org.fugerit.java.core.jvfs.db.helper.MemJvfsDBHelper;

public class TestDBJMount extends TestJVFSHelper {

	@Test
	public void testInitMemDB() {
		try  {
			String tableName = "FUGERIT.ALT2_DB_JVFS_FILE";
			DataEntityDbJvfsFileFacade facade = DataEntityDbJvfsFileFacade.newInstanceWithTable(tableName);
			JVFS jvfs =  JMountDaogenDB.createJVFSWithTableName( MemJvfsDBHelper.newCF(), tableName );
			this.testCreateDelete( jvfs );
			this.tryDumpTestDb( facade );
		} catch (Exception e) {
			failEx(e);
		}
	}
	
}
