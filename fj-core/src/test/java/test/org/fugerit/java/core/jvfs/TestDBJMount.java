package test.org.fugerit.java.core.jvfs;

import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.db.JMountDaogenDB;
import org.fugerit.java.core.jvfs.db.daogen.impl.DataEntityDbJvfsFileFacade;
import org.fugerit.java.test.db.helper.MemDBHelper;
import org.junit.Test;

public class TestDBJMount extends TestJVFSHelper {

	@Test
	public void testInitMemDB() {
		try  {
			String tableName = "FUGERIT.ALT2_DB_JVFS_FILE";
			DataEntityDbJvfsFileFacade facade = DataEntityDbJvfsFileFacade.newInstanceWithTable(tableName);
			JVFS jvfs =  JMountDaogenDB.createJVFSWithTableName( MemDBHelper.newCF(), tableName );
			this.testCreateDelete( jvfs );
			this.tryDumpTestDb( facade );
		} catch (Exception e) {
			failEx(e);
		}
	}
	
}
