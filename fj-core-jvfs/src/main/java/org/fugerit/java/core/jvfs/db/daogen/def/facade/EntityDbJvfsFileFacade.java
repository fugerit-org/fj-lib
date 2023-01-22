package org.fugerit.java.core.jvfs.db.daogen.def.facade;

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.daogen.DAOContext;
import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.db.DaogenJFileDB;

/**
 * EntityDbJvfsFileFacade, version : 1.0.0
 *
 * author: fugerit
 */
public interface EntityDbJvfsFileFacade extends EntityDbJvfsFileFacadeHelper {

	int rename( DAOContext context, DaogenJFileDB file, JFile newFile ) throws DAOException;
	
	// [HELPER/IMPL MODEL] this class is a stub and can be modified as you see fit (it will not been overwritten)
}
