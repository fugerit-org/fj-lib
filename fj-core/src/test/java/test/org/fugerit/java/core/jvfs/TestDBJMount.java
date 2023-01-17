package test.org.fugerit.java.core.jvfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.db.JMountDaogenDB;
import org.fugerit.java.test.db.helper.MemDBHelper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDBJMount extends TestJVFSHelper {

	private final static Logger logger = LoggerFactory.getLogger( TestDBJMount.class );
	
	private void createFile( File source, JFile dest ) throws IOException {
		logger.info( "source -> {} , dest -> {}", source, dest );
		if ( source.isDirectory() ) {
			dest.mkdirs();
		} else {
			JFile parent = dest.getParent();
			if ( parent != null && !parent.exists() ) {
				parent.mkdirs();
			}
			StreamIO.pipeStream( new FileInputStream( source ) , dest.getOutputStream() , StreamIO.MODE_CLOSE_BOTH );
		}
	}
	
	@Test
	public void testInitMemDB() {
		try  {
			JVFS jvfs =  JMountDaogenDB.createJVFS( MemDBHelper.newCF() );
			JFile dbFile1 = jvfs.getJFile( "/test-mount/core/cfg/xml/property-catalog-test.xml" );
			this.createFile( new File( "src/test/resources/core/cfg/xml/property-catalog-test.xml" ) , dbFile1 );
			this.createFile( new File( "src/test/resources/core/jvfs/db/daogen/daogen-config-jvfs.xml" ) , jvfs.getJFile( "/test-mount/core/jvfs/db/daogen/daogen-config-jvfs.xml" ) );
			boolean delete = dbFile1.delete();
			logger.info( "delete {} -> {}", dbFile1, delete );
			this.tryDumpTestDb();
		} catch (Exception e) {
			failEx(e);
		}
	}
	
}
