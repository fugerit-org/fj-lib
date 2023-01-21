package org.fugerit.java.core.jvfs.db.daogen;

/**
 * JvfsLogicFacade, version : 1.0.0
 *
 * author: fugerit
 */
public interface JvfsLogicFacade extends JvfsLogicFacadeHelper {

	EntityDbJvfsFileFacade newInstanceWithTableName( String tableName );
	
	EntityDbJvfsFileFacade newInstanceWithTablePrefix( String prefix );
	
	// [HELPER/IMPL MODEL] this class is a stub and can be modified as you see fit (it will not been overwritten)
}
