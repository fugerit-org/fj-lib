package org.fugerit.java.core.jvfs.db.daogen.impl;

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.jvfs.db.daogen.EntityDbJvfsFileFacade;

// custom import start ( code above here will be overwritten )
// custom import end ( code below here will be overwritten )

/**
 * JvfsDataLogicFacadeHelper, version : 1.0.0
 *
 * author: fugerit
 *
 * warning!: auto generated object, insert custom code only between comments :
 * // custom code start ( code above here will be overwritten )
 * // custom code end ( code below here will be overwritten )
 */
public class JvfsDataLogicFacadeHelper implements org.fugerit.java.core.jvfs.db.daogen.JvfsLogicFacadeHelper, java.io.Serializable {

	// custom code start ( code above here will be overwritten )
	// custom code end ( code below here will be overwritten )

	private static final long serialVersionUID = 813564779935L;


	public JvfsDataLogicFacadeHelper() {
		this.entitydbjvfsfilefacade = new org.fugerit.java.core.jvfs.db.daogen.impl.DataEntityDbJvfsFileFacade();
	}

	private EntityDbJvfsFileFacade entitydbjvfsfilefacade;

	@Override
	public EntityDbJvfsFileFacade getEntityDbJvfsFileFacade() throws DAOException {
		return this.entitydbjvfsfilefacade;
	}

	protected void setEntityDbJvfsFileFacade( EntityDbJvfsFileFacade facade ) throws DAOException {
		this.entitydbjvfsfilefacade = facade;
	}

}
