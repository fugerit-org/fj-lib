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
 * @(#)JFileFacade.java
 *
 * @project    : org.morozko.java.core
 * @package    : org.morozko.java.core.jvfs
 * @creation   : 25/ago/06
 * @license	   : META-INF/LICENSE.TXT
 */
package org.fugerit.java.core.jvfs;

import java.io.IOException;

import org.fugerit.java.core.io.StreamIO;

/*
 * <p>
 *	<jdl:section>
 * 		<jdl:text lang='it'>
 * 		Funzioni per la lettura da JFile.
 * 		</jdl:text>
 * 		<jdl:text lang='en'>
 * 		Funtions for reading from JFile.
 * 		</jdl:text>  
 *	</jdl:section>
 * </p>
 *
 * @author Morozko
 *
 */
public class JFileFacade {

	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Legge il contenuto di un file.</jdl:text>
	 * 		<jdl:text lang='en'>Reads the content of a file.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @param	file	<jdl:section>
	 * 						<jdl:text lang='it'>Il file da cui leggere.</jdl:text>
	 * 						<jdl:text lang='en'>The file to read from.</jdl:text>  
	 *					</jdl:section>
	 * @return			<jdl:section>
	 * 						<jdl:text lang='it'>Un array di byte contentente i dati del file.</jdl:text>
	 * 						<jdl:text lang='en'>A byte array containing the file data.</jdl:text>  
	 *					</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>	
	 */
	public static byte[] readBytes( JFile file ) throws IOException {
		return StreamIO.readBytes( file.getInputStream() );
	}	
	
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Legge il contenuto di un file.</jdl:text>
	 * 		<jdl:text lang='en'>Reads the content of a file.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @param	file	<jdl:section>
	 * 						<jdl:text lang='it'>Il file da cui leggere.</jdl:text>
	 * 						<jdl:text lang='en'>The file to read from.</jdl:text>  
	 *					</jdl:section>
	 * @return			<jdl:section>
	 * 						<jdl:text lang='it'>Una stringa contentente i dati del file.</jdl:text>
	 * 						<jdl:text lang='en'>A string containing the file data.</jdl:text>  
	 *					</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>	
	 */	
	public static String readString( JFile file ) throws IOException {
		return StreamIO.readString( file.getReader() );
	}
	
}
