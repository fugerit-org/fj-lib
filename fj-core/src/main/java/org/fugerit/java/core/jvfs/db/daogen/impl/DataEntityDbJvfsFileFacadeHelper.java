package org.fugerit.java.core.jvfs.db.daogen.impl;

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.daogen.BasicDAOHelper;
import org.fugerit.java.core.db.daogen.BasicDaoResult;
import org.fugerit.java.core.db.daogen.BasicDataFacade;
import org.fugerit.java.core.db.daogen.DAOContext;
import org.fugerit.java.core.db.daogen.DeleteHelper;
import org.fugerit.java.core.db.daogen.InsertHelper;
import org.fugerit.java.core.db.daogen.SelectHelper;
import org.fugerit.java.core.db.daogen.UpdateHelper;
import org.fugerit.java.core.jvfs.db.daogen.DbJvfsFileFinder;
import org.fugerit.java.core.jvfs.db.daogen.EntityDbJvfsFileFacadeHelper;
import org.fugerit.java.core.jvfs.db.daogen.ModelDbJvfsFile;
import org.fugerit.java.core.jvfs.db.daogen.impl.DbJvfsFileRSE;

// custom import start ( code above here will be overwritten )
// custom import end ( code below here will be overwritten )

/**
 * DataEntityDbJvfsFileFacadeHelper, version : 1.0.0
 *
 * author: fugerit
 *
 * warning!: auto generated object, insert custom code only between comments :
 * // custom code start ( code above here will be overwritten )
 * // custom code end ( code below here will be overwritten )
 */
public class DataEntityDbJvfsFileFacadeHelper extends BasicDataFacade<ModelDbJvfsFile> implements EntityDbJvfsFileFacadeHelper {

	// custom code start ( code above here will be overwritten )
	// custom code end ( code below here will be overwritten )

	private static final long serialVersionUID = 334818698796L;

	private final static String TABLE_NAME = "PUBLIC.FUGERIT.DB_JVFS_FILE";

	public DataEntityDbJvfsFileFacadeHelper() {
		super( TABLE_NAME, DbJvfsFileRSE.DEFAULT, null );
	}

 	public final static String COL_FILE_NAME = "FILE_NAME";
 	public final static String COL_PARENT_PATH = "PARENT_PATH";
 	public final static String COL_FILE_PROPS = "FILE_PROPS";
 	public final static String COL_CREATION_TIME = "CREATION_TIME";
 	public final static String COL_UPDATE_TIME = "UPDATE_TIME";
 	public final static String COL_FILE_SIZE = "FILE_SIZE";
 	public final static String COL_FILE_CONTENT = "FILE_CONTENT";

	/* loadAll( context ) is inherited from BasicDataFacade */

	@Override
	public BasicDaoResult<ModelDbJvfsFile> loadAllByFinder( DAOContext context, DbJvfsFileFinder finder ) throws DAOException {
		BasicDaoResult<ModelDbJvfsFile> result = new BasicDaoResult<>();
		BasicDAOHelper<ModelDbJvfsFile> daoHelper = new BasicDAOHelper<>( context );
		SelectHelper query = daoHelper.newSelectHelper( this.getQueryView(), this.getTableName() );
		if ( finder.getModel() != null ) {
			ModelDbJvfsFile model = finder.getModel();
			query.andEqualParam( COL_FILE_NAME, model.getFileName() );
			query.andEqualParam( COL_PARENT_PATH, model.getParentPath() );
			query.andEqualParam( COL_FILE_PROPS, model.getFileProps() );
			query.andEqualParam( COL_CREATION_TIME, model.getCreationTime() );
			query.andEqualParam( COL_UPDATE_TIME, model.getUpdateTime() );
			query.andEqualParam( COL_FILE_SIZE, model.getFileSize() );
			query.andEqualParam( COL_FILE_CONTENT, model.getFileContent() );
		}
		daoHelper.loadAllHelper( result.getList(), query, this.getRse() ); 
		result.evaluateResultFromList(); 
		return result;
	}

	@Override
	public BasicDaoResult<ModelDbJvfsFile> create( DAOContext context, ModelDbJvfsFile model ) throws DAOException {
		BasicDaoResult<ModelDbJvfsFile> result = new BasicDaoResult<>();
		BasicDAOHelper<ModelDbJvfsFile> daoHelper = new BasicDAOHelper<>( context );
		java.sql.Timestamp currentTime = new java.sql.Timestamp( System.currentTimeMillis() );
		//  default-column-time-insert : true - i will set insert time
		model.setCreationTime( currentTime ); 
		//  default-column-time-update : true - i will set update time
		model.setUpdateTime( currentTime ); 
		InsertHelper query = daoHelper.newInsertHelper( this.getTableName() );
		query.addParam( COL_FILE_NAME, model.getFileName() );
		query.addParam( COL_PARENT_PATH, model.getParentPath() );
		query.addParam( COL_FILE_PROPS, model.getFileProps() );
		query.addParam( COL_CREATION_TIME, model.getCreationTime() );
		query.addParam( COL_UPDATE_TIME, model.getUpdateTime() );
		query.addParam( COL_FILE_SIZE, model.getFileSize() );
		query.addParam( COL_FILE_CONTENT, model.getFileContent() );
		int res = daoHelper.update( query );
		this.evaluteSqlUpdateResult(res, model, result);
		return result;
	}

	@Override
	public ModelDbJvfsFile loadById( DAOContext context, java.lang.String fileName, java.lang.String parentPath ) throws DAOException {
		ModelDbJvfsFile result = null;
		BasicDAOHelper<ModelDbJvfsFile> daoHelper = new BasicDAOHelper<>( context );
		SelectHelper query = daoHelper.newSelectHelper( this.getQueryView(), this.getTableName() );
		if ( fileName == null  || parentPath == null  ) { 
			 throw new DAOException( "Null parameter in key java.lang.String fileName, java.lang.String parentPath" );
		} else { 
			query.andEqualParam( COL_FILE_NAME, fileName );
			query.andEqualParam( COL_PARENT_PATH, parentPath );
		}
		result = daoHelper.loadOneHelper( query, this.getRse() );
		return result;
	}

	@Override
	public BasicDaoResult<ModelDbJvfsFile> deleteById( DAOContext context, java.lang.String fileName, java.lang.String parentPath ) throws DAOException {
		BasicDaoResult<ModelDbJvfsFile> result = new BasicDaoResult<>();
		BasicDAOHelper<ModelDbJvfsFile> daoHelper = new BasicDAOHelper<>( context );
		DeleteHelper query = daoHelper.newDeleteHelper( this.getTableName() );
		query.andWhereParam( COL_FILE_NAME, fileName );
		query.andWhereParam( COL_PARENT_PATH, parentPath );
		int res = daoHelper.update( query );
		this.evaluteSqlUpdateResult(res, null, result);
		return result;
	}

	@Override
	public BasicDaoResult<ModelDbJvfsFile> updateById( DAOContext context, ModelDbJvfsFile model ) throws DAOException {
		BasicDaoResult<ModelDbJvfsFile> result = new BasicDaoResult<>();
		BasicDAOHelper<ModelDbJvfsFile> daoHelper = new BasicDAOHelper<>( context );
		//  default-column-time-update : true - i will set update time
		model.setUpdateTime( new java.sql.Timestamp( System.currentTimeMillis() ) ); 
		UpdateHelper query = daoHelper.newUpdateHelper( this.getTableName() );
		query.addSetParam( COL_FILE_PROPS, model.getFileProps() );
		query.addSetParam( COL_CREATION_TIME, model.getCreationTime() );
		query.addSetParam( COL_UPDATE_TIME, model.getUpdateTime() );
		query.addSetParam( COL_FILE_SIZE, model.getFileSize() );
		query.addSetParam( COL_FILE_CONTENT, model.getFileContent() );
		query.andWhereParam( COL_FILE_NAME, model.getFileName() );
		query.andWhereParam( COL_PARENT_PATH, model.getParentPath() );
		int res = daoHelper.update( query );
		this.evaluteSqlUpdateResult(res, model, result);
		return result;
	}

}
