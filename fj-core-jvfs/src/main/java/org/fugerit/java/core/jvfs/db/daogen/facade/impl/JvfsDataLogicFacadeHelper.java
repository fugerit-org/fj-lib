package org.fugerit.java.core.jvfs.db.daogen.facade.impl;

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.jvfs.db.daogen.def.facade.EntityDbJvfsFileFacade;

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
public class JvfsDataLogicFacadeHelper implements org.fugerit.java.core.jvfs.db.daogen.facade.JvfsLogicFacadeHelper, java.io.Serializable {

	// custom code start ( code above here will be overwritten )
	
	// code added to setup a basic conditional serialization - START
	
	private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
		// this class is conditionally serializable, depending on contained object
		// you are encouraged to handle special situation using this method
		out.defaultWriteObject();
	}

	private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
		// this class is conditionally serializable, depending on contained object
		// you are encouraged to handle special situation using this method
		in.defaultReadObject();
	}
	
	// code added to setup a basic conditional serialization - END
	
	// custom code end ( code below here will be overwritten )

	private static final long serialVersionUID = 591065272400L;


	public JvfsDataLogicFacadeHelper() {
		this.entitydbjvfsfilefacade = new org.fugerit.java.core.jvfs.db.impl.facade.data.DataEntityDbJvfsFileFacade();
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
