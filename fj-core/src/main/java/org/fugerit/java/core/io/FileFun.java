package org.fugerit.java.core.io;

import java.io.File;
import java.io.IOException;

/*
 * <p>Function interface for manipulating files.</p>
 *
 * @author Fugerit
 *
 */
public interface FileFun {

	/*
	 * <p>Does operations starting from a file.</p>  
	 * 
	 * @param parent		The parent of the file.
	 * @param name			The name of the file.
	 * @throws IOException	If something goes wrong during elaboration
	 */
    public void handleFile(String parent, String name) throws IOException;
    
	/*
	 * <p>Does operations starting from a file.</p>  
	 * 
	 * @param parent		The parent of the file.
	 * @param name			The name of the file.
	 * @throws IOException	If something goes wrong during elaboration
	 */   
    public void handleFile(File parent, String name) throws IOException;
    
	/*
	 * <p>Does operations starting from a file.</p>  
	 * 
	 * @param file			The file.
	 * @throws IOException	If something goes wrong during elaboration
	 */
    public void handleFile(File file) throws IOException;
    
	/*
	 * <p>Does operations starting from a file.</p>  
	 * 
	 * @param file			path to file.
	 * @throws IOException	If something goes wrong during elaboration
	 */     
    public void handleFile(String path) throws IOException;
    
}
