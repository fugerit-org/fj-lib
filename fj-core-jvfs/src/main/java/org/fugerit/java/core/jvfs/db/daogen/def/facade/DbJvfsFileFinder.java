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

	private static final long serialVersionUID = 4317877389001250786L;

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
