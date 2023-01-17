package org.fugerit.java.core.jvfs.db.daogen;

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.daogen.BasicDaoResult;
import org.fugerit.java.core.db.daogen.DAOContext;
import org.fugerit.java.core.jvfs.db.daogen.ModelDbJvfsFile;

// custom import start ( code above here will be overwritten )
// custom import end ( code below here will be overwritten )

/**
 * EntityDbJvfsFileFacadeHelper, version : 1.0.0
 *
 * author: fugerit
 *
 * warning!: auto generated object, insert custom code only between comments :
 * // custom code start ( code above here will be overwritten )
 * // custom code end ( code below here will be overwritten )
 */
public interface EntityDbJvfsFileFacadeHelper {

	// custom code start ( code above here will be overwritten )
	// custom code end ( code below here will be overwritten )

	/*
	 * NOTE: It is advised to use a finder for incapsulating search params, except searches for primary key.
	 */

	/**
	 * Method to load all the items for entity : ModelDbJvfsFile
	 *
	 * @param context	DAOContext
	 *
	 * @return search result
	 * @throws DAOException			in case of errors
	 */
	BasicDaoResult<ModelDbJvfsFile> loadAll( DAOContext context ) throws DAOException;

	/**
	 * Method to load all the items for entity : ModelDbJvfsFile
	 *
	 * @param context	DAOContext
	 * @param finder	the finder incapsulating search params
	 *
	 * @return search result
	 * @throws DAOException			in caso di errori
	 */
	BasicDaoResult<ModelDbJvfsFile> loadAllByFinder( DAOContext context, DbJvfsFileFinder finder ) throws DAOException;

	/**
	 * Load method by primary key for entity : ModelDbJvfsFile
	 *
	 * @param context	DAO Context
	 * @param fileName The file name
	 * @param parentPath The parent path, empty for root

	 *
	 * @return The found object or <code>null</code>
	 * @throws DAOException			in case of errors
	 */
	ModelDbJvfsFile loadById( DAOContext context, java.lang.String fileName, java.lang.String parentPath ) throws DAOException;

	/**
	 * Method to create an new entity of type : ModelDbJvfsFile
	 *
	 * A new ID should be assigned by this method.
	 *
	 * @param context	DAO context
	 * @param model		Entity to create
	 *
	 * @return 			The created entity
	 * @throws DAOException		In case of any error.
	 */
	BasicDaoResult<ModelDbJvfsFile> create( DAOContext context, ModelDbJvfsFile model ) throws DAOException;

	/**
	 * Delete method by primary key for entity : ModelDbJvfsFile
	 *
	 * @param context	DAO Context
	 * @param fileName The file name
	 * @param parentPath The parent path, empty for root

	 *
	 * @return Delete result (resultCode=0, delete ok)
	 * @throws DAOException			in case of errors
	 */
	BasicDaoResult<ModelDbJvfsFile> deleteById( DAOContext context, java.lang.String fileName, java.lang.String parentPath ) throws DAOException;

	/**
	 * Delete method by primary key for entity : ModelDbJvfsFile
	 *
	 * @param context	DAO Context
	 * @param 	model	entity to update
	 *
	 * @return Update result (resultCode=0, update ok)
	 * @throws DAOException			in case of errors
	 */
	BasicDaoResult<ModelDbJvfsFile> updateById( DAOContext context, ModelDbJvfsFile model ) throws DAOException;

}
