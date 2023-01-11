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
 * @(#)LineWriter.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.io
 * @creation	: 23-dic-2004 12.24.27
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.io.line;

import java.io.IOException;

/*
 * <p>
 *	<jdl:section>
 * 		<jdl:text lang='it'>
 * 		Interfaccia per scrivere linee.
 * 		</jdl:text>
 * 		<jdl:text lang='en'>
 * 		Interface for writing in line.
 * 		</jdl:text>  
 *	</jdl:section>
 * </p>
 *
 * @author Morozko
 *
 */
public interface LineWriter {

	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Va a capo.</jdl:text>
	 * 		<jdl:text lang='en'>Carriage return.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 *
	 */
    public void println();
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Stampa una pagina senza andare a capo.</jdl:text>
     * 		<jdl:text lang='en'>Write a line without carriage return.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param line	<jdl:section>
     * 					<jdl:text lang='it'>La linea di testo da stampare.</jdl:text>
     * 					<jdl:text lang='en'>The line of text to print.</jdl:text>  
     *				</jdl:section>
     */
    public void print(String line);
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Stampa una pagina e va a capo.</jdl:text>
     * 		<jdl:text lang='en'>Write a line with carriage return.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param line	<jdl:section>
     * 					<jdl:text lang='it'>La linea di testo da stampare.</jdl:text>
     * 					<jdl:text lang='en'>The line of text to print.</jdl:text>  
     *				</jdl:section>
     */
    public void println(String line);
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Chiude il LineWriter.</jdl:text>
     * 		<jdl:text lang='en'>Close the LineWriter.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
	 * @throws IOException	<jdl:section>
	 * 							<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 							<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *						</jdl:section>
     */
    public void close() throws IOException;
    
}
