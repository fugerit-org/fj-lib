package org.fugerit.java.core.jvfs.db.daogen.impl;

import org.fugerit.java.core.jvfs.db.daogen.EntityDbJvfsFileFacade;
import org.fugerit.java.core.jvfs.db.daogen.JvfsLogicFacade;

/**
 * JvfsDataLogicFacade, version : 1.0.0
 *
 * author: fugerit
 */
public class JvfsDataLogicFacade extends JvfsDataLogicFacadeHelper implements org.fugerit.java.core.jvfs.db.daogen.JvfsLogicFacade, java.io.Serializable {

	private static final long serialVersionUID = 532493378641L;
	
	private static final JvfsDataLogicFacade INSTANCE = new JvfsDataLogicFacade(); 
	
	public static JvfsLogicFacade getInstance() {
		return INSTANCE;
	}
	
	@Override
	public EntityDbJvfsFileFacade newInstanceWithTableName(String tableName) {
		return DataEntityDbJvfsFileFacade.newInstanceWithTable(tableName);
	}

	@Override
	public EntityDbJvfsFileFacade newInstanceWithTablePrefix(String prefix) {
		return DataEntityDbJvfsFileFacade.newInstanceWithPrefix(prefix);
	}

	// [HELPER/IMPL MODEL] this class is a stub and can be modified as you see fit (it will not been overwritten)
}
