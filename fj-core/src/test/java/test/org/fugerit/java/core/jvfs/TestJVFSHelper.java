package test.org.fugerit.java.core.jvfs;

import static org.junit.Assert.fail;

import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JVFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJVFSHelper {

	private static final Logger logger = LoggerFactory.getLogger( TestJVFSHelper.class );
	
	protected void failEx( Exception e ) {
		String message = "Error : "+e;
		logger.error( "Error "+e, e );
		fail( message );
	}
	
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
	
}
