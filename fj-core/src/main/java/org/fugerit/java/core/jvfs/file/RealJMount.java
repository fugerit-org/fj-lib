/****************************************************************
<copyright>
	Morozko Java Library org.morozko.java.core 

	Copyright (c) 2006 Morozko

	All rights reserved. This program and the accompanying materials
	are made available under the terms of the Apache License v2.0
	which accompanies this distribution, and is available at
	http://www.apache.org/licenses/
	(txt version : http://www.apache.org/licenses/LICENSE-2.0.txt
	html version : http://www.apache.org/licenses/LICENSE-2.0.html)

   This product includes software developed at
   The Apache Software Foundation (http://www.apache.org/).
</copyright>
*****************************************************************/
/*
 * @(#)RealJMount.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.jvfs.file
 * @creation	: 9-dic-2004 14.50.54
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.jvfs.file;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.helpers.DefaultConfigurableObject;
import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JMount;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.JVFSImpl;

/*
 * <p>
 *	<jdl:section>
 *	<jdl:text lang='it'>
 *	Estensione di JVFS per risolvere i file a partire dal <code>java.io.File</code>.
 *	Si tratta in pratica di una semplice mappatura di un percorso tra JVFS e una
 *	cartella locale.
 *	</jdl:text>
 *	<jdl:text lang='en'>
 *	JVFS Extension to resolve the files starting from a <code>java.io.File</code>.
 * 	It is a simple mapping between a JVFS path and a directory on the local file system.
 *	</jdl:text>
 *	</jdl:section>
 * </p>
 *
 * @author Morozko
 *
 */
public class RealJMount extends DefaultConfigurableObject implements JMount {

	/*
	 * 
	 */
	private static final long serialVersionUID = 2946128203628261102L;
	
	public static final String PROP_BASE_PATH = "real-base-path";
	
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Crea un nuovo JVFS con il risolutore di path di default e con la radice reale.</jdl:text>
	 * 		<jdl:text lang='en'>Creates a new JVFS with default path resolver and starting at the root file.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return					<jdl:section>
	 * 								<jdl:text lang='it'><p>Il JVFS.</p></jdl:text>
	 * 								<jdl:text lang='en'><p>The JVFS.</p></jdl:text>
	 * 							</jdl:section>	
	 */
	public static JVFS createDefaultJVFS() {
		JVFS jvfs = null;
		try {
			jvfs = createJVFS( new File( "/" ) );
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jvfs;
	}
	
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Crea un nuovo JVFS con il risolutore di path di default e con la radice indicata.</jdl:text>
	 * 		<jdl:text lang='en'>Creates a new JVFS with default path resolver and starting at the  given root file.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @param root				<jdl:section>
	 * 								<jdl:text lang='it'><p>La cartella base per il JMount.</p></jdl:text>
	 * 								<jdl:text lang='en'><p>The base directory for the JMount.</p></jdl:text>
	 * 							</jdl:section>	
	 * @return					<jdl:section>
	 * 								<jdl:text lang='it'><p>Il JVFS.</p></jdl:text>
	 * 								<jdl:text lang='en'><p>The JVFS.</p></jdl:text>
	 * 							</jdl:section>	
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>	
	 */
    public static JVFS createJVFS(File root) throws IOException {
        return JVFSImpl.getInstance(new RealJMount(root));
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return this.getClass().getName()+"[base:"+this.base+"]";
    }
    
    private File base;	// the base path for the JMount
    
    /*
     * 
     * <jdl:section>
     * 		<jdl:text lang='it'><p>Crea una nuova istanza di RealJMount.</p></jdl:text>
     * 		<jdl:text lang='en'><p>Creates a new instance of RealJMount.</p></jdl:text>
     * </jdl:section>
     *
     */
    public RealJMount() {
    	
    }
    
    /*
     * 
     * <jdl:section>
     * 		<jdl:text lang='it'><p>Crea una nuova istanza di RealJMount.</p></jdl:text>
     * 		<jdl:text lang='en'><p>Creates a new instance of RealJMount.</p></jdl:text>
     * </jdl:section>
     *
     * @param base				<jdl:section>
	 * 								<jdl:text lang='it'><p>La cartella base per il JMount.</p></jdl:text>
	 * 								<jdl:text lang='en'><p>The base directory for the JMount.</p></jdl:text>
	 * 							</jdl:section>	
     */
    public RealJMount(File base) {
        this.base = base;
    }
    
    /* (non-Javadoc)
	 * @see org.morozko.java.core.cfg.helpers.DefaultConfigurableObject#configure(java.util.Properties)
	 */
	public void configure(Properties props) throws ConfigException {
		this.base = new File( props.getProperty( PROP_BASE_PATH ) );
		if ( !this.base.exists() ) {
			throw ( new ConfigException( "Base path must exists : "+this.base ) );
		}
	}

	/* (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JMount#getJFile(java.lang.String, java.lang.String)
     */
    public JFile getJFile(JVFS jvfs, String point, String relPath) {
        return (new RealJFile(jvfs.getPathResolver().getChild(point, relPath), jvfs, new File(base, relPath)));
    }
    
}
