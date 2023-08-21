package org.fugerit.java.core.jvfs.db.impl.facade.data;

import java.sql.Connection;

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.dao.DAOUtilsNG;
import org.fugerit.java.core.db.dao.LoadResultNG;
import org.fugerit.java.core.db.daogen.DAOContext;
import org.fugerit.java.core.db.helpers.TimeHelper;
import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.db.DaogenJFileDB;
import org.fugerit.java.core.jvfs.db.daogen.def.facade.EntityDbJvfsFileFacade;
import org.fugerit.java.core.jvfs.db.daogen.model.ModelDbJvfsFile;
import org.fugerit.java.core.jvfs.db.daogen.res.DbJvfsFileRSE;
import org.fugerit.java.core.jvfs.helpers.JFileUtils;
import org.fugerit.java.core.jvfs.helpers.PathDescriptor;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.log.LogFacade;

/**
 * DataEntityDbJvfsFileFacade, version : 1.0.0
 *
 * author: fugerit
 */
public class DataEntityDbJvfsFileFacade extends DataEntityDbJvfsFileFacadeHelper implements EntityDbJvfsFileFacade {

	@Override
	public int rename( DAOContext context, DaogenJFileDB file, JFile newFile ) throws DAOException {
		int res = 0;
		try {
			if ( file.isRoot() ) {
				throw new DAOException( "Cannot rename root : "+file );
			} else {
				String renameFileSql = StringUtils.concat( " " , "UPDATE ",
						this.getTableName(), "SET", 
						COL_FILE_NAME, "=?,",
						COL_PARENT_PATH,"=?,",
						COL_UPDATE_TIME, "=?",
						"WHERE",
						COL_FILE_NAME, "=? AND",
						COL_PARENT_PATH,"=?" );
				PathDescriptor descriptor1 = JFileUtils.pathDescriptor( file.getPath() );
				PathDescriptor descriptor2 = JFileUtils.pathDescriptor( newFile.getPath() );
				if ( file.isDirectory() ) {
					Connection conn = context.getConnection();
					boolean resetAutocommit = false;
					if ( conn.getAutoCommit() ) {
						conn.setAutoCommit( false );
						resetAutocommit = true;
					}
					try {
						res = DAOUtilsNG.update( context.getConnection() , renameFileSql , 
								descriptor2.getName(), descriptor2.getParentPath(), TimeHelper.nowTimestamp(), descriptor1.getName(), descriptor1.getParentPath() );
						if ( res > 0 ) {
							String searchDirSql = StringUtils.concat( " " , 
									"SELECT * FROM", this.getTableName(), "WHERE", COL_PARENT_PATH, "LIKE ?");
							int resDir = 0;
							try ( LoadResultNG<ModelDbJvfsFile> loader = DAOUtilsNG.extraAllFields( conn , searchDirSql, DbJvfsFileRSE.DEFAULT, file.getPath()+"%") ) {
								while ( loader.hasNext() ) {
									ModelDbJvfsFile currentKid = loader.next();
									String newParentPath = currentKid.getParentPath().replaceFirst( file.getPath(), newFile.getPath() );
									resDir+= DAOUtilsNG.update( context.getConnection() , renameFileSql , 
											currentKid.getFileName(), newParentPath, TimeHelper.nowTimestamp(), currentKid.getFileName(), currentKid.getParentPath() );
								}
							}
							logger.debug( "result file {}, dir {}, found {}", res, resDir );
							res+= resDir;
						}
						conn.commit();
					} catch (Exception e) {
						conn.rollback();
						throw e;
					} finally {
						if ( resetAutocommit ) {
							conn.setAutoCommit( true );
						}
					}
				} else {
					res = DAOUtilsNG.update( context.getConnection() , renameFileSql , 
							descriptor2.getName(), descriptor2.getParentPath(), TimeHelper.nowTimestamp(), descriptor1.getName(), descriptor1.getParentPath() );
				}	
			}
		} catch (Exception e) {
			throw new DAOException( e ); 
		}
		return res;
	}

	private static final long serialVersionUID = 504022259276L;

	public static final String DEFAULT_TABLE_NAME = "DB_JVFS_FILE";
	
	public DataEntityDbJvfsFileFacade() {
		super();
	}

	public DataEntityDbJvfsFileFacade(String tableName, String queryView) {
		super(tableName, queryView);
	}
	
	public static DataEntityDbJvfsFileFacade newInstanceWithPrefix( String prefix ) {
		String tableName = StringUtils.valueWithDefault( prefix, "")+DEFAULT_TABLE_NAME;
		LogFacade.getLog().info( "newInstanceWithPrefix():{}, prefix:{}", DataEntityDbJvfsFileFacade.class.getSimpleName(), prefix );
		return new DataEntityDbJvfsFileFacade( tableName, null );
	}
	
	public static DataEntityDbJvfsFileFacade newInstanceWithTable( String tableName ) {
		LogFacade.getLog().info( "newInstanceWithTable():{}, tableName:{}", DataEntityDbJvfsFileFacade.class.getSimpleName(), tableName );
		return new DataEntityDbJvfsFileFacade( tableName, null );
	}

	
	// [HELPER/IMPL MODEL] this class is a stub and can be modified as you see fit (it will not been overwritten)
}
