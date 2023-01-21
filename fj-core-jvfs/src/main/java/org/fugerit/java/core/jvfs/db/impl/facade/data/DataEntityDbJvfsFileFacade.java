package org.fugerit.java.core.jvfs.db.impl.facade.data;

import org.fugerit.java.core.jvfs.db.daogen.def.facade.EntityDbJvfsFileFacade;
import org.fugerit.java.core.lang.helpers.StringUtils;
import org.fugerit.java.core.log.LogFacade;

/**
 * DataEntityDbJvfsFileFacade, version : 1.0.0
 *
 * author: fugerit
 */
public class DataEntityDbJvfsFileFacade extends DataEntityDbJvfsFileFacadeHelper implements EntityDbJvfsFileFacade {

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
