package test.org.fugerit.java.core.jvfs;

import java.io.IOException;
import java.util.Comparator;

import org.fugerit.java.core.db.daogen.BasicDaoResult;
import org.fugerit.java.core.db.daogen.CloseableDAOContext;
import org.fugerit.java.core.db.daogen.CloseableDAOContextSC;
import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JFileCopyFacade;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.db.daogen.EntityDbJvfsFileFacade;
import org.fugerit.java.core.jvfs.db.daogen.ModelDbJvfsFile;
import org.fugerit.java.core.jvfs.db.daogen.impl.DataEntityDbJvfsFileFacade;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.test.db.helper.MemDBHelper;

import test.org.fugerit.java.BasicTest;

public class TestJVFSHelper extends BasicTest {

	protected  EntityDbJvfsFileFacade facade = DataEntityDbJvfsFileFacade.newInstanceWithPrefix( "FUGERIT." );
	
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
		JFileCopyFacade.copyFile( source , dest , true, true, true );
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
	
	private static final Comparator<ModelDbJvfsFile> SORTER = new Comparator<ModelDbJvfsFile>() {
		@Override
		public int compare(ModelDbJvfsFile o1, ModelDbJvfsFile o2) {
			String path1 = StringUtils.valueWithDefault( o1.getParentPath(), "" )+JFile.SEPARATOR+o1.getFileName();
			String path2 = StringUtils.valueWithDefault( o2.getParentPath(), "" )+JFile.SEPARATOR+o2.getFileName();
			return path1.compareTo( path2 );
		}
	};
	
	protected void tryDumpTestDb() {
		this.tryDumpTestDb( this.facade );
	}
	
	protected void tryDumpTestDb( EntityDbJvfsFileFacade fileFacade ) {
		logger.info( "Try to dumb jvfs db : " );
		try ( CloseableDAOContext context = new CloseableDAOContextSC( MemDBHelper.newConnection() ) )  {
			BasicDaoResult<ModelDbJvfsFile> res = fileFacade.loadAll( context );
			logger.info( "dump result:{}, size:{}", res.getResultCode(), res.getList().size() );
			if ( res.isResultOk() ) {
				res.getList().sort( SORTER );
				res.getList().forEach( f -> logger.info( "parentPath:{},fileName:{}", f.getParentPath(), f.getFileName() ) );
			}
		} catch (Exception e1) {
			logger.warn( "dump failed : "+e1, e1 );
		}
	}
	
}
