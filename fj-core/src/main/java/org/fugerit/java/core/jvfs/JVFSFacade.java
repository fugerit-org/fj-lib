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
 * @(#)JVFSFacade.java
 *
 * @project    : org.morozko.java.core
 * @package    : org.morozko.java.core.jvfs
 * @creation   : 24/ago/06
 * @license	   : META-INF/LICENSE.TXT
 */
package org.fugerit.java.core.jvfs;

import java.util.HashMap;
import java.util.Map;

import org.fugerit.java.core.jvfs.file.RealJMount;

/*
 * <p>
 *	<jdl:section>
 * 		<jdl:text lang='it'>
 * 		Classe che astrae la gestione dei file system virtuali permettendo
 * 		eventualmente, anche la loro registrazione.
 * 		</jdl:text>
 * 		<jdl:text lang='en'>
 * 		Class for handling virtual file system, you can also register JVFS.
 * 		</jdl:text>  
 *	</jdl:section>
 * </p>
 *
 * @author Morozko
 *
 */
public class JVFSFacade {

	/*
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>La chiave del file system virtuale predefinito.</jdl:text>
	 * 		<jdl:text lang='en'>The default virtual file system key.</jdl:text>  
	 *	</jdl:section>
	 */
	public static final String DEFAULT_JVFS = JVFSFacade.class.getName()+"#DEFAULT_JVFS";
	
	private static Map<String, JVFS> jvfsMap;			// the virtual file system map

	private static JVFS defaultJvfs;	// default virtual file system
	
	// initialize necessary resouces
	static {
		jvfsMap = new HashMap<>();
		setDefaultJvfs( RealJMount.createDefaultJVFS() );
	}

	/*
	 * <p>
	 *  <jdl:section>
	 * 		<jdl:text lang='it'>Restituisce il valore del campo defaultJvfs.</jdl:text>
	 * 		<jdl:text lang='en'>Returns the value of defaultJvfs.</jdl:text>  
	 *  </jdl:section>
	 * </p>
	 *
	 * @return <jdl:section>
	 *         		<jdl:text lang='it'>il valore del campo defaultJvfs.</jdl:text>
	 *         		<jdl:text lang='en'>the value of defaultJvfs.</jdl:text> 
	 * 		   </jdl:section>
	 */
	public static JVFS getDefaultJvfs() {
		return defaultJvfs;
	}
	
	/*
	 * <p>
	 *  <jdl:section>
	 * 		<jdl:text lang='it'>Imposta il campo defaultJvfs.</jdl:text>
	 * 		<jdl:text lang='en'>Sets defaultJvfs.</jdl:text>  
	 *	</jdl:section>
	 * </p>
	 *
	 * @param 	<jdl:section>
	 * 				<jdl:text lang='it'>defaultJvfs il valore di defaultJvfs da impostare.</jdl:text>
	 * 				<jdl:text lang='en'>defaultJvfs the defaultJvfs to set.</jdl:text>
	 * 			</jdl:section>
	 */
	public static void setDefaultJvfs(JVFS defaultJvfs) {
		JVFSFacade.defaultJvfs = defaultJvfs;
		registerJVFS( DEFAULT_JVFS, defaultJvfs );
	}

	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Restituisce il file system virutale registrato con la chiave data.</jdl:text>
	 * 		<jdl:text lang='en'>Returns the virtual file system registered with the given key.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @param jvfsName	<jdl:section>
	 * 						<jdl:text lang='it'>La chiave del virtual file system.</jdl:text>
	 * 						<jdl:text lang='en'>The virtual file system key.</jdl:text>  
	 *					</jdl:section>
	 * @return		<jdl:section>
	 * 					<jdl:text lang='it'>Il file system virtuale.</jdl:text>
	 * 					<jdl:text lang='en'>The virtual file system.</jdl:text>  
	 *				</jdl:section>
	 */
	public static JVFS getJVFS( String jvfsName ) {
		return (JVFS)jvfsMap.get( jvfsName );
	}	
	
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Registra un file system virtuale.</jdl:text>
	 * 		<jdl:text lang='en'>Register a virtual file system.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @param name	<jdl:section>
	 * 					<jdl:text lang='it'>La chiave del virtual file system.</jdl:text>
	 * 					<jdl:text lang='en'>The virtual file system key.</jdl:text>  
	 *				</jdl:section>
	 * @param jvfs	<jdl:section>
	 * 					<jdl:text lang='it'>Il virtual file system.</jdl:text>
	 * 					<jdl:text lang='en'>The virtual file system.</jdl:text>  
	 *				</jdl:section>
	 */
	public static void registerJVFS( String name, JVFS jvfs ) {
		jvfsMap.put( name, jvfs );
	}
	
}
