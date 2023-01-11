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
 * @(#)JFile.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.jvfs
 * @creation	: 9-dic-2004 14.01.30
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.jvfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import org.fugerit.java.core.util.path.PathResolver;

/*
 * <p>
 *	<jdl:section>
 * 		<jdl:text lang='it'>
 * 		Interfaccia rappresentante un file del file system virtuale.
 * 		</jdl:text>
 * 		<jdl:text lang='en'>
 * 		Iterface for a file in the virtual file system.
 * 		</jdl:text>  
 *	</jdl:section>
 * </p>
 *
 * @author Morozko
 *
 */
public interface JFile {

	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Il JVFS cui il file appartiene.</jdl:text>
	 * 		<jdl:text lang='en'>The JVFS who owns the file.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'>Il JVFS cui il file appartiene.</jdl:text>
	 * 				<jdl:text lang='en'>The JVFS who owns the file.</jdl:text>  
	 *			</jdl:section>
	 */
    public JVFS getJVFS();

	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Il path del JFile.</jdl:text>
	 * 		<jdl:text lang='en'>The JFile path.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'>Il path del JFile.</jdl:text>
	 * 				<jdl:text lang='en'>The JFile path.</jdl:text>  
	 *			</jdl:section>
	 */
    public String getPath();
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Il nome del JFile.</jdl:text>
	 * 		<jdl:text lang='en'>The JFile name.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'>Il nome del JFile.</jdl:text>
	 * 				<jdl:text lang='en'>The JFile name.</jdl:text>  
	 *			</jdl:section>
	 */
    public String getName();
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Se questo JFile � una directory restituisce un suo figlio di nome dato.</jdl:text>
	 * 		<jdl:text lang='en'>If this JFile is a directory returns a child with a given name.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 *
	 * @param	name	<jdl:section>
	 * 						<jdl:text lang='it'>Il nome del JFile.</jdl:text>
	 * 						<jdl:text lang='en'>The JFile name.</jdl:text>  
	 *					</jdl:section>
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'>Il figlio.</jdl:text>
	 * 				<jdl:text lang='en'>The child.</jdl:text>  
	 *			</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>	 
	 */
    public JFile getChild(String name) throws IOException;
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Se questo JFile � una directory restituisce l'elenco dei nomi dei suoi figli.</jdl:text>
	 * 		<jdl:text lang='en'>If this JFile is a directory returns the listing of its children names.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'>L'elenco dei nomi dei figli del JFile.</jdl:text>
	 * 				<jdl:text lang='en'>The listing of JFile's children names.</jdl:text>  
	 *			</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>	 
	 */
    public String[] list() throws IOException;
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Se questo JFile � una directory restituisce l'elenco dei suoi figli.</jdl:text>
	 * 		<jdl:text lang='en'>If this JFile is a directory returns the listing of its children.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'>L'elenco dei figli del JFile.</jdl:text>
	 * 				<jdl:text lang='en'>The listing of JFile's children.</jdl:text>  
	 *			</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */    
    public JFile[] listFiles() throws IOException;
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Cancella il file rappresentato da questo persorse astratto se esiste.</jdl:text>
	 * 		<jdl:text lang='en'>Delete the file rapresented by this abstract path name if it exists.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'><code>true</code> se la cancellazione avviene, <code>false</code> altrimenti.</jdl:text>
	 * 				<jdl:text lang='en'><code>true</code> il deletion happens, <code>false</code> otherwise.</jdl:text>  
	 *			</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */   
    public boolean delete() throws IOException;
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Crea un file rappresentato da questo persorse astratto, se il file non esiste.</jdl:text>
	 * 		<jdl:text lang='en'>Creates a new file rapresented by this abstract path name, if file does not exist.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'><code>true</code> se la creazione avviene, <code>false</code> altrimenti.</jdl:text>
	 * 				<jdl:text lang='en'><code>true</code> if creation happens, <code>false</code> otherwise.</jdl:text>  
	 *			</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */       
    public boolean create() throws IOException;
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Verifica l'esistenza del file rappresentato da questo persorso astratto.</jdl:text>
	 * 		<jdl:text lang='en'>Verify existence of the file rapresented by this abstract path name.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'><code>true</code> se il file esiste, <code>false</code> altrimenti.</jdl:text>
	 * 				<jdl:text lang='en'><code>true</code> if the file exists, <code>false</code> otherwise.</jdl:text>  
	 *			</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */        
    public boolean exists() throws IOException;

	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Crea una directory rappresentato da questo persorse astratto, se il file non esiste.</jdl:text>
	 * 		<jdl:text lang='en'>Creates a new directory rapresented by this abstract path name, if file does not exist.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'><code>true</code> se la creazione avviene, <code>false</code> altrimenti.</jdl:text>
	 * 				<jdl:text lang='en'><code>true</code> if creation happens, <code>false</code> otherwise.</jdl:text>  
	 *			</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */         
    public boolean mkdir() throws IOException;
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Crea una directory, e ogni eventuale genitore non ancora esistente, rappresentato da questo persorse astratto, se il file non esiste.</jdl:text>
	 * 		<jdl:text lang='en'>Creates a new directory rapresented by this abstract path name, and any parent directory, if path does not exist.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'><code>true</code> se la creazione avviene, <code>false</code> altrimenti.</jdl:text>
	 * 				<jdl:text lang='en'><code>true</code> if creation happens, <code>false</code> otherwise.</jdl:text>  
	 *			</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */         
    public boolean mkdirs() throws IOException;
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Verifica se un persorse corrisponde ad una directory.</jdl:text>
	 * 		<jdl:text lang='en'>Verify if the path rapresents a directory.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'><code>true</code> se il path � una directory, <code>false</code> altrimenti.</jdl:text>
	 * 				<jdl:text lang='en'><code>true</code> if the path is a directory, <code>false</code> otherwise.</jdl:text>  
	 *			</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */       
    public boolean isDirectory() throws IOException;
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Verifica se un persorse corrisponde ad un file.</jdl:text>
	 * 		<jdl:text lang='en'>Verify if the path rapresents a file.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'><code>true</code> se il path � un file, <code>false</code> altrimenti.</jdl:text>
	 * 				<jdl:text lang='en'><code>true</code> if the path is a file, <code>false</code> otherwise.</jdl:text>  
	 *			</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */           
    public boolean isFile() throws IOException;
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Restituisce un input stream sul file.</jdl:text>
	 * 		<jdl:text lang='en'>Return an input stream on the file.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'>Un input stream per leggere dal file.</jdl:text>
	 * 				<jdl:text lang='en'>An input stream to read from the file.</jdl:text>  
	 *			</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */      
    public InputStream getInputStream() throws IOException;
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Restituisce un output stream sul file.</jdl:text>
	 * 		<jdl:text lang='en'>Return an output stream on the file.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'>Un output stream per scrivere sul file.</jdl:text>
	 * 				<jdl:text lang='en'>An output stream to write on the file.</jdl:text>  
	 *			</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */     
    public OutputStream getOutputStream() throws IOException;
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Restituisce un Reader sul file.</jdl:text>
	 * 		<jdl:text lang='en'>Return a Reader on the file.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'>Un Reader per leggere dal file.</jdl:text>
	 * 				<jdl:text lang='en'>A Reader to read from the file.</jdl:text>  
	 *			</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */       
    public Reader getReader() throws IOException;
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Restituisce un Writer sul file.</jdl:text>
	 * 		<jdl:text lang='en'>Return a Writer on the file.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'>Un Writer per scrivere sul file.</jdl:text>
	 * 				<jdl:text lang='en'>A Writer to write on the file.</jdl:text>  
	 *			</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */         
    public Writer getWriter() throws IOException;
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Restituisce il path della radice del file system virtuale. Metodo delegato per <code>getPathResolver().getRoot()</code>.</jdl:text>
     * 		<jdl:text lang='en'>Return the root path of the virtual file system. Delegate method for <code>getPathResolver().getRoot()</code>.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @return	<jdl:section>
     * 				<jdl:text lang='it'>il path della radice del file system virtuale.</jdl:text>
     * 				<jdl:text lang='en'>the root path of the virtual file system.</jdl:text>  
     *			</jdl:section>
     */
    public String getRootPath();
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Restituisce il path della radice del file system virtuale. Metodo delegato per <code>getPathResolver().getSeparator()</code>.</jdl:text>
     * 		<jdl:text lang='en'>Return the root path of the virtual file system. Delegate method for <code>getPathResolver().getSeparator()</code>.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @return	<jdl:section>
     * 				<jdl:text lang='it'>il path della radice del file system virtuale.</jdl:text>
     * 				<jdl:text lang='en'>the root path of the virtual file system.</jdl:text>  
     *			</jdl:section>
     */   
    public String getPathSeparator();

    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Restituisce il risolutore di path del file system virtuale.</jdl:text>
     * 		<jdl:text lang='en'>Return the path resolver of the virtual file system.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @return	<jdl:section>
     * 				<jdl:text lang='it'>il risolutore di path della radice del file system virtuale.</jdl:text>
     * 				<jdl:text lang='en'>the path resolver of the virtual file system.</jdl:text>  
     *			</jdl:section>
     * 
     */     
    public PathResolver getPathResolver();
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Verifica se questo file supporta una certa caratteristicha.</jdl:text>
	 * 		<jdl:text lang='en'>Verify if the file support a certain feature.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @param	featureName	<jdl:section>
	 * 							<jdl:text lang='it'>Il nome della caratteristica.</jdl:text>
	 * 							<jdl:text lang='en'>The name of the feature.</jdl:text>  
	 *						</jdl:section> 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'><code>true</code> se la caratteristica � supportata, <code>false</code> altrimenti.</jdl:text>
	 * 				<jdl:text lang='en'><code>true</code> if the feature is supported, <code>false</code> otherwise.</jdl:text>  
	 *			</jdl:section>
	 */       
    public boolean getFeature(String featureName);
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Restituisce una data propriet� del file.</jdl:text>
	 * 		<jdl:text lang='en'>Return a given property of the file.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @param	propertyName	<jdl:section>
	 * 								<jdl:text lang='it'>Il nome della properiet�.</jdl:text>
	 * 								<jdl:text lang='en'>The name of the property.</jdl:text>  
	 *							</jdl:section> 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'><code>true</code> se la caratteristica � supportata, <code>false</code> altrimenti.</jdl:text>
	 * 				<jdl:text lang='en'><code>true</code> if the feature is supported, <code>false</code> otherwise.</jdl:text>  
	 *			</jdl:section>
	 */     
    public String getProperty(String propertyName);
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Verifica se si pu� leggere dal file.</jdl:text>
	 * 		<jdl:text lang='en'>Verify if you can read from file.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'><code>true</code> se il file pu� essere letto, <code>false</code> altrimenti.</jdl:text>
	 * 				<jdl:text lang='en'><code>true</code> if the file can be read from, <code>false</code> otherwise.</jdl:text>  
	 *			</jdl:section>
	 */     
    public boolean canRead() throws IOException;
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Verifica se si pu� scrivere sul file.</jdl:text>
	 * 		<jdl:text lang='en'>Verify if you can write on the file.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'><code>true</code> se il file pu� essere scritto, <code>false</code> altrimenti.</jdl:text>
	 * 				<jdl:text lang='en'><code>true</code> if the file can be written to, <code>false</code> otherwise.</jdl:text>  
	 *			</jdl:section>
	 */       
    public boolean canWrite() throws IOException;
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Imposta il file come readonly.</jdl:text>
     * 		<jdl:text lang='en'>Sets the file as readonly.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
     */
    public void setReadOnly() throws IOException;
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Restituisce le dimensioni del file.</jdl:text>
	 * 		<jdl:text lang='en'>Returns the size of the file.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'>un <code>int</code> rappresentante le dimensioni in byte del file.</jdl:text>
	 * 				<jdl:text lang='en'>an <code>int</code> representing the size in byte of the file.</jdl:text>  
	 *			</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */      
    public int supposedSize() throws IOException;
    
	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Restituisce la data di ultima modifica del file.</jdl:text>
	 * 		<jdl:text lang='en'>Returns file's last modification time.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @return	<jdl:section>
	 * 				<jdl:text lang='it'>un <code>long</code> con la data di ultima modifica del file.</jdl:text>
	 * 				<jdl:text lang='en'>an <code>long</code> with the file's last modification time.</jdl:text>  
	 *			</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */  
    public long getLastModified() throws IOException;

	/*
	 * <p> 
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Imposta la data di ultima modifica del file.</jdl:text>
	 * 		<jdl:text lang='en'>Sets file's last modification time.</jdl:text>  
	 *	</jdl:section>
	 * </p> 
	 * 
	 * @param	time	<jdl:section>
	 * 						<jdl:text lang='it'>un <code>long</code> con la data di ultima modifica del file.</jdl:text>
	 * 						<jdl:text lang='en'>an <code>long</code> with the file's last modification time.</jdl:text>  
	 *					</jdl:section>
     * @throws IOException		<jdl:section>
	 * 								<jdl:text lang='it'>Se qualcosa va male durante l'elaborazione.</jdl:text>
	 * 								<jdl:text lang='en'>If something goes wrong during elaboration.</jdl:text>  
	 *							</jdl:section>
	 */      
    public void setLastModified(long time) throws IOException;
    
}
