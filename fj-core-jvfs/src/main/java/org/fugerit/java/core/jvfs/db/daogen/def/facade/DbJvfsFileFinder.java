package org.fugerit.java.core.jvfs.db.daogen.def.facade;

import org.fugerit.java.core.db.daogen.BaseIdFinder;
import org.fugerit.java.core.jvfs.db.daogen.model.ModelDbJvfsFile;

// custom import start ( code above here will be overwritten )
// custom import end ( code below here will be overwritten )

/**
 * DbJvfsFileFinder, version : 1.0.0
 *
 * author: fugerit
 *
 * warning!: auto generated object, insert custom code only between comments :
 * // custom code start ( code above here will be overwritten )
 * // custom code end ( code below here will be overwritten )
 */
public class DbJvfsFileFinder extends BaseIdFinder {

	// custom code start ( code above here will be overwritten )
	// custom code end ( code below here will be overwritten )

	private ModelDbJvfsFile model;

	public void setModel( ModelDbJvfsFile model ) {
		this.model = model;
	}

	public ModelDbJvfsFile getModel() {
		return this.model;
	}

	/**
	 * Factory method to create a new finder 
	 *
	 * @param model		the model to wrap in the finder
	 *
	 * @return	the finder
	 */
	public static DbJvfsFileFinder newInstance( ModelDbJvfsFile model ) { 
		DbJvfsFileFinder finder = new DbJvfsFileFinder();
		// default id not available for this entity
		finder.setModel( model );
		return finder;
	}

}
