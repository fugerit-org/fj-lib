package test.org.fugerit.java.core.jvfs;

import java.io.IOException;

import org.fugerit.java.core.db.daogen.BasicDaoResult;
import org.fugerit.java.core.db.daogen.CloseableDAOContext;
import org.fugerit.java.core.db.daogen.CloseableDAOContextSC;
import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.db.daogen.EntityDbJvfsFileFacade;
import org.fugerit.java.core.jvfs.db.daogen.ModelDbJvfsFile;
import org.fugerit.java.core.jvfs.db.daogen.impl.DataEntityDbJvfsFileFacade;
import org.fugerit.java.test.db.helper.MemDBHelper;

import test.org.fugerit.java.BasicTest;

public class TestJVFSHelper extends BasicTest {

	protected void testJVFSWorker( JVFS jvfs ) {
		try {
			JFile root =jvfs.getRoot();
			if ( root != null ) {
				logger.info( "root {} -> {}", root.getName() , root );
				JFile[] list = root.listFiles();
				for ( int k=0; k<list.length; k++ ) {
					JFile file = list[k];
					logger.info( "file {}", file );	
				}
			}
		} catch ( Exception e ) {
			this.failEx(e);
		}
	}
	
	private void testCopyRecurse( JVFS to, JFile source ) throws IOException {
		JFile dest = to.getJFile( source.getPath() );
		logger.info( "testCopyRecurse() source:{} -> dest:{}", source, dest );
		if ( source.isDirectory() ) {
			dest.mkdir();
			JFile[] list = source.listFiles();
			for ( int k=0; k<list.length; k++ ) {
				testCopyRecurse( to , list[k] );
			}
		} else {
			StreamIO.pipeStream( source.getInputStream() , dest.getOutputStream(), StreamIO.MODE_CLOSE_BOTH );
		}
	}
	
	private void testReadRecurse( JFile file1, JFile file2 ) throws IOException {
		logger.info( "testReadRecurse() file1:{} -> file2:{}", file1, file2 );
		if ( file1.isDirectory() ) {
			JFile[] list = file1.listFiles();
			for ( int k=0; k<list.length; k++ ) {
				this.testReadRecurse( list[k] , file2.getJVFS().getJFile( list[k].getPath() ) );
			}
		}
	}
	
	protected void testWriteRead( JVFS from, JVFS to ) throws IOException {
		this.testCopyRecurse( to, from.getRoot() );
		this.testReadRecurse( to.getRoot(), from.getRoot() );
	}
	
	protected void tryDumpTestDb() {
		logger.info( "Try to dumb jvfs db : " );
		try ( CloseableDAOContext context = new CloseableDAOContextSC( MemDBHelper.newConnection() ) )  {
			EntityDbJvfsFileFacade fileFacade = new DataEntityDbJvfsFileFacade();
			BasicDaoResult<ModelDbJvfsFile> res = fileFacade.loadAll( context );
			logger.info( "dump result:{}, size:{}", res.getResultCode(), res.getList().size() );
			if ( res.isResultOk() ) {
				res.getList().forEach( f -> logger.info( "parentPath:{},fileName:{}", f.getParentPath(), f.getFileName() ) );
			}
		} catch (Exception e1) {
			logger.warn( "dump failed : "+e1, e1 );
		}
	}
	
}
