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
 * @(#)LineReader.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.io.line
 * @creation	: 23-dic-2004 12.25.18
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.io.line;

import java.io.IOException;

/*
 * <p>
 *	<jdl:section>
 * 		<jdl:text lang='it'>
 * 		Interfaccia per la lettura di input in linee.
 * 		</jdl:text>
 * 		<jdl:text lang='en'>
 * 		Interface for reading lines of input.
 * 		</jdl:text>  
 *	</jdl:section>
 * </p>
 *
 * @author Morozko
 *
 */
public interface LineReader {

    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Legge una linea dall' input.</jdl:text>
     * 		<jdl:text lang='en'>Read one line from input.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
	 * @throws IOException	<jdl:section>
	 * 							<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 							<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *						</jdl:section>
     */
    public String readLine() throws IOException;
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Chiude il LineReader.</jdl:text>
     * 		<jdl:text lang='en'>Close the LineReader.</jdl:text>  
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
