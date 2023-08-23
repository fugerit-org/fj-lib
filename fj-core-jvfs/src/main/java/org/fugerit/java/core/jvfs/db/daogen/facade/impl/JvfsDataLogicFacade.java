package org.fugerit.java.core.jvfs.db.daogen.facade.impl;

import org.fugerit.java.core.jvfs.db.daogen.def.facade.EntityDbJvfsFileFacade;
import org.fugerit.java.core.jvfs.db.daogen.facade.JvfsLogicFacade;
import org.fugerit.java.core.jvfs.db.impl.facade.data.DataEntityDbJvfsFileFacade;

/**
 * JvfsDataLogicFacade, version : 1.0.0
 *
 * author: fugerit
 */
public class JvfsDataLogicFacade extends JvfsDataLogicFacadeHelper implements org.fugerit.java.core.jvfs.db.daogen.facade.JvfsLogicFacade {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2818436404285930891L;
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
