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
 * @(#)JFileIO.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.jvfs
 * @creation	: 16-gen-2005 15.32.21
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.jvfs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipOutputStream;

import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.jvfs.fun.JFileFunResult;
import org.fugerit.java.core.jvfs.fun.JFileFunSafe;
import org.fugerit.java.core.jvfs.fun.ZipJFileFun;
import org.fugerit.java.core.lang.helpers.ExHandler;
import org.fugerit.java.core.lang.helpers.Result;

/*
 * <p>
 *	<jdl:section>
 * 		<jdl:text lang='it'>
 * 		Libreria di funzioni per la manipolazione di JFile.
 * 		</jdl:text>
 * 		<jdl:text lang='en'>
 * 		Library of functions for handling JFile.
 * 		</jdl:text>  
 *	</jdl:section>
 * </p>
 *
 * @author Morozko
 *
 */
public class JFileIO {

	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Crea un file jar di tutti i file contenuti in una directory.</jdl:text>
	 * 		<jdl:text lang='en'>Creates a jar file of all files in a directory.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @param dir			<jdl:section>
	 * 							<jdl:text lang='it'>La directory da cui partire per la creazione dell' archivio.</jdl:text>
	 * 							<jdl:text lang='en'>The directory to start from for jar creation.</jdl:text>  
	 *						</jdl:section>
	 * @param zipFile		<jdl:section>
	 * 							<jdl:text lang='it'>Il persorso del file jar da creare.</jdl:text>
	 * 							<jdl:text lang='en'>The path of the jar file to create.</jdl:text>  
	 *						</jdl:section>
	 * @return				<jdl:section>
	 * 							<jdl:text lang='it'>Il risultato della creazione dell' archivio.</jdl:text>
	 * 							<jdl:text lang='en'>Archive creation result.</jdl:text>  
	 *						</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */
    public static Result jarDirResult(JFile dir, JFile zipFile) throws IOException {
        Result result = null;
        ZipOutputStream zos = new JarOutputStream(zipFile.getOutputStream());
        result = recurseResult(dir, new ZipJFileFun(zos, dir.getPath()));
        zos.close();
        return result;
    }

	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Crea un file jar di tutti i file contenuti in una directory.</jdl:text>
	 * 		<jdl:text lang='en'>Creates a jar file of all files in a directory.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @param dir			<jdl:section>
	 * 							<jdl:text lang='it'>La directory da cui partire per la creazione dell' archivio.</jdl:text>
	 * 							<jdl:text lang='en'>The directory to start from for jar creation.</jdl:text>  
	 *						</jdl:section>
	 * @param zipFile		<jdl:section>
	 * 							<jdl:text lang='it'>Il persorso del file jar da creare.</jdl:text>
	 * 							<jdl:text lang='en'>The path of the jar file to create.</jdl:text>  
	 *						</jdl:section>
	 * @param handler		<jdl:section>
	 * 							<jdl:text lang='it'>Gestore degli errori.</jdl:text>
	 * 							<jdl:text lang='en'>For handling errors.</jdl:text>  
	 *						</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */	
    public static void jarDirSafe(JFile dir, JFile zipFile, ExHandler handler) throws IOException {
        ZipOutputStream zos = new JarOutputStream(zipFile.getOutputStream());
        recurseSafe(dir, new ZipJFileFun(zos, dir.getPath()), handler);
        zos.close();
    }    
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Crea un file jar di tutti i file contenuti in una directory.</jdl:text>
	 * 		<jdl:text lang='en'>Creates a jar file of all files in a directory.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @param dir			<jdl:section>
	 * 							<jdl:text lang='it'>La directory da cui partire per la creazione dell' archivio.</jdl:text>
	 * 							<jdl:text lang='en'>The directory to start from for jar creation.</jdl:text>  
	 *						</jdl:section>
	 * @param zipFile		<jdl:section>
	 * 							<jdl:text lang='it'>Il persorso del file jar da creare.</jdl:text>
	 * 							<jdl:text lang='en'>The path of the jar file to create.</jdl:text>  
	 *						</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */	
    public static void jarDir(JFile dir, JFile zipFile) throws IOException {
        ZipOutputStream zos = new JarOutputStream(zipFile.getOutputStream());
        recurse(dir, new ZipJFileFun(zos, dir.getPath()));
        zos.close();
    }    
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Crea un file zip di tutti i file contenuti in una directory.</jdl:text>
	 * 		<jdl:text lang='en'>Creates a zip file of all files in a directory.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @param dir			<jdl:section>
	 * 							<jdl:text lang='it'>La directory da cui partire per la creazione dell' archivio.</jdl:text>
	 * 							<jdl:text lang='en'>The directory to start from for zip creation.</jdl:text>  
	 *						</jdl:section>
	 * @param zipFile		<jdl:section>
	 * 							<jdl:text lang='it'>Il persorso del file zip da creare.</jdl:text>
	 * 							<jdl:text lang='en'>The path of the zip file to create.</jdl:text>  
	 *						</jdl:section>
	 * @return				<jdl:section>
	 * 							<jdl:text lang='it'>Il risultato della creazione dell' archivio.</jdl:text>
	 * 							<jdl:text lang='en'>Archive creation result.</jdl:text>  
	 *						</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */	    
    public static Result zipDirResult(JFile dir, JFile zipFile) throws IOException {
        Result result = null;
        ZipOutputStream zos = new ZipOutputStream(zipFile.getOutputStream());
        result = recurseResult(dir, new ZipJFileFun(zos, dir.getPath()));
        zos.close();
        return result;
    }
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Crea un file zip di tutti i file contenuti in una directory.</jdl:text>
	 * 		<jdl:text lang='en'>Creates a zip file of all files in a directory.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @param dir			<jdl:section>
	 * 							<jdl:text lang='it'>La directory da cui partire per la creazione dell' archivio.</jdl:text>
	 * 							<jdl:text lang='en'>The directory to start from for zip creation.</jdl:text>  
	 *						</jdl:section>
	 * @param zipFile		<jdl:section>
	 * 							<jdl:text lang='it'>Il persorso del file zip da creare.</jdl:text>
	 * 							<jdl:text lang='en'>The path of the zip file to create.</jdl:text>  
	 *						</jdl:section>
	 * @param handler		<jdl:section>
	 * 							<jdl:text lang='it'>Gestore degli errori.</jdl:text>
	 * 							<jdl:text lang='en'>For handling errors.</jdl:text>  
	 *						</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */	    
    public static void zipDirSafe(JFile dir, JFile zipFile, ExHandler handler) throws IOException {
        ZipOutputStream zos = new ZipOutputStream(zipFile.getOutputStream());
        recurseSafe(dir, new ZipJFileFun(zos, dir.getPath()), handler);
        zos.close();
    }
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Crea un file zip di tutti i file contenuti in una directory.</jdl:text>
	 * 		<jdl:text lang='en'>Creates a zip file of all files in a directory.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @param dir			<jdl:section>
	 * 							<jdl:text lang='it'>La directory da cui partire per la creazione dell' archivio.</jdl:text>
	 * 							<jdl:text lang='en'>The directory to start from for zip creation.</jdl:text>  
	 *						</jdl:section>
	 * @param zipFile		<jdl:section>
	 * 							<jdl:text lang='it'>Il persorso del file zip da creare.</jdl:text>
	 * 							<jdl:text lang='en'>The path of the zip file to create.</jdl:text>  
	 *						</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */	        
    public static void zipDir(JFile dir, JFile zipFile) throws IOException {
        ZipOutputStream zos = new ZipOutputStream(zipFile.getOutputStream());
        recurse(dir, new ZipJFileFun(zos, dir.getPath()));
        zos.close();
    }
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Scorre una directory e applica una funzione a tutti i file che vi trova.</jdl:text>
     * 		<jdl:text lang='en'>Recurse a directory and apply a function to every file in it.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param file		<jdl:section>
	 * 						<jdl:text lang='it'>La directory da scandire.</jdl:text>
	 * 						<jdl:text lang='en'>The directory to recurse.</jdl:text>  
	 *					</jdl:section>
     * @param fun		<jdl:section>
	 * 						<jdl:text lang='it'>La funzione da applicare.</jdl:text>
	 * 						<jdl:text lang='en'>The function to apply.</jdl:text>  
	 *					</jdl:section>
     * @return			<jdl:section>
	 * 						<jdl:text lang='it'>Il risultato della scansione.</jdl:text>
	 * 						<jdl:text lang='en'>The recurse result.</jdl:text>  
	 *					</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
     */
   public static Result recurseResult(JFile file, JFileFun fun) throws IOException {
       JFileFunResult resultFun = new JFileFunResult(fun);
       recurse(file, resultFun);
       return resultFun.getResult();
   }
    
   /*
    * <p> 
    *	<jdl:section>
    * 		<jdl:text lang='it'>Scorre una directory e applica una funzione a tutti i file che vi trova.</jdl:text>
    * 		<jdl:text lang='en'>Recurse a directory and apply a function to every file in it.</jdl:text>  
    *	</jdl:section>
    * </p> 
    * 
    * @param file		<jdl:section>
	 * 						<jdl:text lang='it'>La directory da scandire.</jdl:text>
	 * 						<jdl:text lang='en'>The directory to recurse.</jdl:text>  
	 *					</jdl:section>
    * @param fun		<jdl:section>
	 * 						<jdl:text lang='it'>La funzione da applicare.</jdl:text>
	 * 						<jdl:text lang='en'>The function to apply.</jdl:text>  
	 *					</jdl:section>
	 * @param handler		<jdl:section>
	 * 							<jdl:text lang='it'>Gestore degli errori.</jdl:text>
	 * 							<jdl:text lang='en'>For handling errors.</jdl:text>  
	 *						</jdl:section>
    * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
    */
  public static void recurseSafe(JFile file, JFileFun fun, ExHandler handler) throws IOException {
      recurse(file, new JFileFunSafe(fun, handler));
  }
   
  /*
   * <p> 
   *	<jdl:section>
   * 		<jdl:text lang='it'>Scorre una directory e applica una funzione a tutti i file che vi trova.</jdl:text>
   * 		<jdl:text lang='en'>Recurse a directory and apply a function to every file in it.</jdl:text>  
   *	</jdl:section>
   * </p> 
   * 
   * @param file		<jdl:section>
   * 						<jdl:text lang='it'>La directory da scandire.</jdl:text>
   * 						<jdl:text lang='en'>The directory to recurse.</jdl:text>  
   *					</jdl:section>
   * @param fun		<jdl:section>
   * 						<jdl:text lang='it'>La funzione da applicare.</jdl:text>
   * 						<jdl:text lang='en'>The function to apply.</jdl:text>  
   *					</jdl:section>
   * @throws IOException		<jdl:section>
   * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
   * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
   *							</jdl:section>
   */
   public static void recurse(JFile file, JFileFun fun) throws IOException {
       fun.handle(file);
       if (file.isDirectory()) {
           JFile[] list = file.listFiles();
           for (int k=0; k<list.length; k++) {
               recurse(list[k], fun);
           }
       }
   }

   /*
    * <p> 
    *	<jdl:section>
    * 		<jdl:text lang='it'>Legge il contenuto di un file file.</jdl:text>
    * 		<jdl:text lang='en'>Read the content of a file.</jdl:text>  
    *	</jdl:section>
    * </p> 
    * 
    * @param file				<jdl:section>
    * 								<jdl:text lang='it'>Il file su cui scrivere.</jdl:text>
    * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
    *							</jdl:section>
    * @return					<jdl:section>
    * 								<jdl:text lang='it'>Il contenuto del file come un array di <code>java.lang.StringBuffer</code>.</jdl:text>
    * 								<jdl:text lang='en'>The content of the file as a <code>java.lang.StringBuffer</code> array.</jdl:text>  
    *							</jdl:section>
    * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
    */   	
    public StringBuffer readStringBuffer(String file) throws IOException {
        return readStringBuffer(this.jvfs.getJFile(file));
    }    
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Legge il contenuto di un file file.</jdl:text>
     * 		<jdl:text lang='en'>Read the content of a file.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param file				<jdl:section>
     * 								<jdl:text lang='it'>Il file su cui scrivere.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @return					<jdl:section>
     * 								<jdl:text lang='it'>Il contenuto del file come un array di <code>java.lang.String</code>.</jdl:text>
     * 								<jdl:text lang='en'>The content of the file as a <code>java.lang.String</code> array.</jdl:text>  
     *							</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
     */     
    public String readString(String file) throws IOException {
        return readString(this.jvfs.getJFile(file));
    }
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Legge il contenuto di un file file.</jdl:text>
     * 		<jdl:text lang='en'>Read the content of a file.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param file				<jdl:section>
     * 								<jdl:text lang='it'>Il file su cui scrivere.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @return					<jdl:section>
     * 								<jdl:text lang='it'>Il contenuto del file come un array di <code>byte[]</code>.</jdl:text>
     * 								<jdl:text lang='en'>The content of the file as a <code>byte[]</code> array.</jdl:text>  
     *							</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
     */    
    public byte[] readBytes(String file) throws IOException {
        return readBytes(this.jvfs.getJFile(file));
    }    
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Legge il contenuto di un file file.</jdl:text>
     * 		<jdl:text lang='en'>Read the content of a file.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param file				<jdl:section>
     * 								<jdl:text lang='it'>Il file su cui scrivere.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @return					<jdl:section>
     * 								<jdl:text lang='it'>Il contenuto del file come un array di <code>char[]</code>.</jdl:text>
     * 								<jdl:text lang='en'>The content of the file as a <code>char[]</code> array.</jdl:text>  
     *							</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
     */        
    public char[] readChars(String file) throws IOException {
        return readChars(this.jvfs.getJFile(file));
    }    
    
    /*
     * <p>Legge da un file.</p>
     * 
     * @param file  il file da leggere
     * @return      il contenuto di un file
     * @throws IOException  se si verificano problemi durante la lettura
     */
    public StringBuffer readStringBuffer(JFile file) throws IOException {
        return (new StringBuffer(readString(file)));
    }    
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Legge il contenuto di un file file.</jdl:text>
     * 		<jdl:text lang='en'>Read the content of a file.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param file				<jdl:section>
     * 								<jdl:text lang='it'>Il file su cui scrivere.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @return					<jdl:section>
     * 								<jdl:text lang='it'>Il contenuto del file come un array di <code>java.lang.String</code>.</jdl:text>
     * 								<jdl:text lang='en'>The content of the file as a <code>java.lang.String</code> array.</jdl:text>  
     *							</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
     */
    public static String readString(JFile file) throws IOException {
        return (new String(readBytes(file)));
    }
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Legge il contenuto di un file file.</jdl:text>
     * 		<jdl:text lang='en'>Read the content of a file.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param file				<jdl:section>
     * 								<jdl:text lang='it'>Il file su cui scrivere.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @return					<jdl:section>
     * 								<jdl:text lang='it'>Il contenuto del file come un array di <code>byte[]</code>.</jdl:text>
     * 								<jdl:text lang='en'>The content of the file as a <code>byte[]</code> array.</jdl:text>  
     *							</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
     */
    public static byte[] readBytes(JFile file) throws IOException {
        ByteArrayOutputStream dst = new ByteArrayOutputStream();
        StreamIO.pipeStream(file.getInputStream(), dst, StreamIO.MODE_CLOSE_BOTH);
        return dst.toByteArray();
    }    
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Legge il contenuto di un file file.</jdl:text>
     * 		<jdl:text lang='en'>Read the content of a file.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param file				<jdl:section>
     * 								<jdl:text lang='it'>Il file su cui scrivere.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @return					<jdl:section>
     * 								<jdl:text lang='it'>Il contenuto del file come un array di <code>char[]</code>.</jdl:text>
     * 								<jdl:text lang='en'>The content of the file as a <code>char[]</code> array.</jdl:text>  
     *							</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
     */ 
    public static char[] readChars(JFile file) throws IOException {
        CharArrayWriter dst = new CharArrayWriter();
        StreamIO.pipeChar(file.getReader(), dst, StreamIO.MODE_CLOSE_BOTH);
        return dst.toCharArray();
    }
    
    
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Scrive in un file.</jdl:text>
     * 		<jdl:text lang='en'>Write to a file.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param data				<jdl:section>
     * 								<jdl:text lang='it'>Dati da scrivere nel file.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @param file				<jdl:section>
     * 								<jdl:text lang='it'>Il file su cui scrivere.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @return					<jdl:section>
     * 								<jdl:text lang='it'>Il numero di charatteri effettivamente scritti.</jdl:text>
     * 								<jdl:text lang='en'>The number of char effectively written.</jdl:text>  
     *							</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
     */
    public long writeStringBuffer(StringBuffer data, String file ) throws IOException {
        return writeStringBuffer(data, this.jvfs.getJFile(file));
    }    
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Scrive in un file.</jdl:text>
     * 		<jdl:text lang='en'>Write to a file.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param data				<jdl:section>
     * 								<jdl:text lang='it'>Dati da scrivere nel file.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @param file				<jdl:section>
     * 								<jdl:text lang='it'>Il file su cui scrivere.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @return					<jdl:section>
     * 								<jdl:text lang='it'>Il numero di charatteri effettivamente scritti.</jdl:text>
     * 								<jdl:text lang='en'>The number of char effectively written.</jdl:text>  
     *							</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
     */
    public long writeString(String data, String file ) throws IOException {
        return writeString(data, this.jvfs.getJFile(file));
    }
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Scrive in un file.</jdl:text>
     * 		<jdl:text lang='en'>Write to a file.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param data				<jdl:section>
     * 								<jdl:text lang='it'>Dati da scrivere nel file.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @param file				<jdl:section>
     * 								<jdl:text lang='it'>Il file su cui scrivere.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @return					<jdl:section>
     * 								<jdl:text lang='it'>Il numero di byte effettivamente scritti.</jdl:text>
     * 								<jdl:text lang='en'>The number of bytes effectively written.</jdl:text>  
     *							</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
     */    
    public long writeBytes(byte[] data, String file ) throws IOException {
        return writeBytes(data, this.jvfs.getJFile(file));
    }    
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Scrive in un file.</jdl:text>
     * 		<jdl:text lang='en'>Write to a file.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param data				<jdl:section>
     * 								<jdl:text lang='it'>Dati da scrivere nel file.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @param file				<jdl:section>
     * 								<jdl:text lang='it'>Il file su cui scrivere.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @return					<jdl:section>
     * 								<jdl:text lang='it'>Il numero di charatteri effettivamente scritti.</jdl:text>
     * 								<jdl:text lang='en'>The number of char effectively written.</jdl:text>  
     *							</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
     */
    public long writeChars(char[] data, String file ) throws IOException {
        return writeChars(data, this.jvfs.getJFile(file));
    }
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Scrive in un file.</jdl:text>
     * 		<jdl:text lang='en'>Write to a file.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param data				<jdl:section>
     * 								<jdl:text lang='it'>Dati da scrivere nel file.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @param file				<jdl:section>
     * 								<jdl:text lang='it'>Il file su cui scrivere.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @return					<jdl:section>
     * 								<jdl:text lang='it'>Il numero di charatteri effettivamente scritti.</jdl:text>
     * 								<jdl:text lang='en'>The number of char effectively written.</jdl:text>  
     *							</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
     */    
    public static long writeStringBuffer(StringBuffer data, JFile file ) throws IOException {
        return writeString(data.toString(), file);
    }    
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Scrive in un file.</jdl:text>
     * 		<jdl:text lang='en'>Write to a file.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param data				<jdl:section>
     * 								<jdl:text lang='it'>Dati da scrivere nel file.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @param file				<jdl:section>
     * 								<jdl:text lang='it'>Il file su cui scrivere.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @return					<jdl:section>
     * 								<jdl:text lang='it'>Il numero di charatteri effettivamente scritti.</jdl:text>
     * 								<jdl:text lang='en'>The number of char effectively written.</jdl:text>  
     *							</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
     */
    public static long writeString(String data, JFile file ) throws IOException {
        return writeChars(data.toCharArray(), file);
    }    
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Scrive in un file.</jdl:text>
     * 		<jdl:text lang='en'>Write to a file.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param data				<jdl:section>
     * 								<jdl:text lang='it'>Dati da scrivere nel file.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @param file				<jdl:section>
     * 								<jdl:text lang='it'>Il file su cui scrivere.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @return					<jdl:section>
     * 								<jdl:text lang='it'>Il numero di byte effettivamente scritti.</jdl:text>
     * 								<jdl:text lang='en'>The number of bytes effectively written.</jdl:text>  
     *							</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
     */ 
    public static long writeBytes(byte[] data, JFile file ) throws IOException {
        return StreamIO.pipeStream(new ByteArrayInputStream(data), file.getOutputStream(), StreamIO.MODE_CLOSE_BOTH);
    }    
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Scrive in un file.</jdl:text>
     * 		<jdl:text lang='en'>Write to a file.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param data				<jdl:section>
     * 								<jdl:text lang='it'>Dati da scrivere nel file.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @param file				<jdl:section>
     * 								<jdl:text lang='it'>Il file su cui scrivere.</jdl:text>
     * 								<jdl:text lang='en'>Data to write to the files.</jdl:text>  
     *							</jdl:section>
     * @return					<jdl:section>
     * 								<jdl:text lang='it'>Il numero di charatteri effettivamente scritti.</jdl:text>
     * 								<jdl:text lang='en'>The number of char effectively written.</jdl:text>  
     *							</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
     */
    public static long writeChars(char[] data, JFile file ) throws IOException {
        return StreamIO.pipeChar(new CharArrayReader(data), file.getWriter(), StreamIO.MODE_CLOSE_BOTH);
    }

    private JVFS jvfs;
    
    /*
     * 
     * <jdl:section>
     * 		<jdl:text lang='it'><p>Crea una nuova istanza di JFileIO.</p></jdl:text>
     * 		<jdl:text lang='en'><p>Creates a new instance of JFileIO.</p></jdl:text>
     * </jdl:section>
     *
     * @param jvfs				<jdl:section>
     * 								<jdl:text lang='it'>Il file system virtuale da utilizzare.</jdl:text>
     * 								<jdl:text lang='en'>The virtual file system to use.</jdl:text>  
     *							</jdl:section>
     */
    public JFileIO(JVFS jvfs) {
        super();
        this.jvfs = jvfs;
    }

}
