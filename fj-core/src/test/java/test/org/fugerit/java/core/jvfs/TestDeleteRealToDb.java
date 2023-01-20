package test.org.fugerit.java.core.jvfs;

import java.io.File;
import java.io.IOException;

import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.db.JMountDaogenDB;
import org.fugerit.java.core.jvfs.db.daogen.impl.DataEntityDbJvfsFileFacade;
import org.fugerit.java.core.jvfs.file.RealJMount;
import org.fugerit.java.test.db.helper.MemDBHelper;
import org.junit.Test;

public class TestDeleteRealToDb extends TestJVFSHelper {

	@Test
	public void testDelete01() {
		try {
			File dest = new File( "target/test_delete_01" );
			logger.info( "create dest dir : {} -> {}", dest, dest.mkdirs() );
			JVFS from =  RealJMount.createJVFS( new File( "src/test/resources/core" ) );
			String tableName = "FUGERIT.ALT_DB_JVFS_FILE";
			DataEntityDbJvfsFileFacade facade = DataEntityDbJvfsFileFacade.newInstanceWithTable(tableName);
			JVFS to =  JMountDaogenDB.createJVFSWithTableName( MemDBHelper.newCF(), tableName );
			this.testWriteDelete(from, to);
			this.tryDumpTestDb( facade );
		} catch (IOException e) {
			this.failEx(e);
		}
	}
	
}
