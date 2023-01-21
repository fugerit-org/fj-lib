package org.fugerit.java.core.jvfs.db.daogen.res;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.fugerit.java.core.db.daogen.BasicRSExtractor;
import org.fugerit.java.core.jvfs.db.daogen.helper.HelperDbJvfsFile;
import org.fugerit.java.core.jvfs.db.daogen.model.ModelDbJvfsFile;

// custom import start ( code above here will be overwritten )
// custom import end ( code below here will be overwritten )

/**
 * DbJvfsFileRSE, version : 1.0.0
 *
 * author: fugerit
 *
 * warning!: auto generated object, insert custom code only between comments :
 * // custom code start ( code above here will be overwritten )
 * // custom code end ( code below here will be overwritten )
 */
public class DbJvfsFileRSE extends BasicRSExtractor<ModelDbJvfsFile> {

	// custom code start ( code above here will be overwritten )
	// custom code end ( code below here will be overwritten )

	private static final long serialVersionUID = 634313347220L;

	public static final DbJvfsFileRSE DEFAULT = new DbJvfsFileRSE();

	@Override
	public ModelDbJvfsFile extractNext( ResultSet rs ) throws SQLException { 
		HelperDbJvfsFile current = new HelperDbJvfsFile();
		current.setFileName( rs.getString( "FILE_NAME" )  );
		current.setParentPath( rs.getString( "PARENT_PATH" )  );
		current.setFileProps( rs.getString( "FILE_PROPS" )  );
		current.setCreationTime( rs.getTimestamp( "CREATION_TIME" )  );
		current.setUpdateTime( rs.getTimestamp( "UPDATE_TIME" )  );
		current.setFileSize( rs.getBigDecimal( "FILE_SIZE" )  );
		try { 
			current.setFileContent( org.fugerit.java.core.db.daogen.ByteArrayDataHandler.newHandlerPreload( rs.getBlob( "FILE_CONTENT" ) )  );
		} catch (Exception e) {
			throw new SQLException( "Errore estrazione campo : FILE_CONTENT", e );
		}
		return current;
	} 
}
