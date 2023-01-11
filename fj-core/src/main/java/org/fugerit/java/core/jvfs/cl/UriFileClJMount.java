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
 * @(#)ClJMount.java
 *
 * @project    : org.morozko.java.core
 * @package    : org.morozko.java.core.jvfs.cl
 * @creation   : 23/ago/06
 * @license	   : META-INF/LICENSE.TXT
 */
package org.fugerit.java.core.jvfs.cl;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.cfg.helpers.DefaultConfigurableObject;
import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JMount;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.file.RealJMount;

/*
 * <p>
 *	<jdl:section>
 * 		<jdl:text lang='it'>
 * 		Implementazione di JMount che risolve i file all' interno di un <code>ClassLoader</code>.
 * 		</jdl:text>
 * 		<jdl:text lang='en'>
 * 		JMount implementation which resolves file in a  <code>ClassLoader</code>.
 * 		</jdl:text>  
 *	</jdl:section>
 * </p>
 *
 * @author Morozko
 *
 */
public class UriFileClJMount extends DefaultConfigurableObject implements JMount {
	
	/*
	 * 
	 */
	private static final long serialVersionUID = 6365971783948169969L;
	
	/*
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Propriet� di configurazione che indica il package name.</jdl:text>
	 * 		<jdl:text lang='en'>Property to configure the package to use.</jdl:text>  
	 *	</jdl:section>
	 */	
	public static final String PROP_PACK_NAME = "cl-package-name";
	
	/* (non-Javadoc)
	 * @see org.morozko.java.core.cfg.helpers.DefaultConfigurableObject#configure(java.util.Properties)
	 */
	public void configure(Properties props) throws ConfigException {
		this.packageName = props.getProperty( PROP_PACK_NAME );
		try {
			this.mount = new RealJMount( toFile( this.packageName ) );
		} catch (IOException e) {
			throw new ConfigException( e );
		}
	}

	private String packageName;		// the base package name
	
	private JMount mount;
	
	public static File toFile( String packageName ) throws IOException {
		File res = null;
		try {
        	URI uri = Thread.currentThread().getContextClassLoader().getResource( packageName ).toURI();
			res = new File( uri );
		} catch (Exception e) {
			throw new IOException( e );
		}
		return res;
	}
	
	public UriFileClJMount() {}
	
	
    public static JVFS createJVFS( String packageName ) throws IOException {
    	JVFS res = null;
        try {
        	URI uri = Thread.currentThread().getContextClassLoader().getResource( packageName ).toURI();
			File root = new File( uri );
			res = RealJMount.createJVFS( root );
		} catch (Exception e) {
			throw new IOException( e );
		}
        return res;
    }

	@Override
	public JFile getJFile(JVFS jvfs, String point, String relPath) {
		return this.mount.getJFile(jvfs, point, relPath);
	}
    
}
