package org.fugerit.java.core.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.io.file.FileFunSecure;
import org.fugerit.java.core.io.file.ZipFileFun;
import org.fugerit.java.core.lang.helpers.ExHandler;
import org.fugerit.java.core.lang.helpers.Result;
import org.fugerit.java.core.util.result.ResultExHandler;

/*
 * <p>Class helper for I/O operations on files.</p>
 *
 *  @author Fugerit
 *
 */
@Slf4j
public class FileIO {

    public static File newFile( String baseDir, String fileName ) {
        return baseDir == null ?  new File( fileName ) :new File( baseDir, fileName );
    }

    public static File newFile( String baseDir, String fileName, boolean mustAlreadyExists ) throws IOException {
        File file =  newFile( baseDir, fileName );
        if ( mustAlreadyExists && !file.exists() ) {
            throw new IOException( String.format( "File [%s] does not exist", file.getCanonicalPath() ) );
        }
        return file;
    }

    public static boolean isInTmpFolder( File tempFile ) throws IOException {
        return isInTmpFolder( tempFile.getCanonicalPath() );
    }
    public static boolean isInTmpFolder( String tempFilePath ) throws IOException {
        File tempDir = new File( System.getProperty( "java.io.tmpdir" ) );
        log.info( "file -> {} (tmpdir : {})", tempFilePath, tempDir );
        return tempFilePath.startsWith(tempDir.getCanonicalPath() );
    }

	public static boolean createFullFile( File file ) throws IOException {
		boolean created = Boolean.TRUE;
		if ( file.exists() ) {
			return Boolean.FALSE;
		} else {
			if ( file.isDirectory() ) {
				return file.mkdirs();
			} else {
				file.getParentFile().mkdirs();
				return file.createNewFile();
			}
		}
	}
	
    /*
     * <p> 
     *	
     * 		Creates a zip archive of all files in a given directory.
     * 		The recursion is done in a secure mode, no exception is thrown.
     * 		
     * </p> 
     *
     * @param file				The zip archive to create.   
     * @param dir				The directory to recurse.
     * @return					The result of the recursion.
     * @throws IOException		If something goes wrong during elaboration.
     */    	
    public static Result zipFileSecure(File file, File dir) throws IOException {
        ZipOutputStream stream = new ZipOutputStream(new FileOutputStream(file));
        FileFun fun = new ZipFileFun(stream, dir);
        Result result = null;
        try {
             result = recurseDirSecure(dir, fun);
        } catch (IOException ioe) {
            throw (ioe);
        } finally {
            stream.close();            
        }
        return result;
    }
    
    /*
     * <p> 
     *	
     * 		Creates a zip archive of all files in a given directory.
     * 		The recursion is done in a secure mode, no exception is thrown.
     * 		
     * </p> 
     *
     * @param file				The zip archive to create.   
     * @param dir				The directory to recurse.
     * @throws IOException		If something goes wrong during elaboration.
     */        
    public static void zipFileSecure(File file, File dir, ExHandler handler) throws IOException {
        ZipOutputStream stream = new ZipOutputStream(new FileOutputStream(file));
        FileFun fun = new ZipFileFun(stream, dir);
        try {
            recurseDirSecure(dir, fun, handler);
        } catch (IOException ioe) {
            throw (ioe);
        } finally {
            stream.close();            
        }
    }
    
    /*
     * <p> 
     *	
     * 		Creates a zip archive of all files in a given directory.
     * 		
     * </p> 
     *
     * @param file				The zip archive to create.   
     * @param dir				The directory to recurse.
     * @throws IOException		If something goes wrong during elaboration.
     */    
    public static void zipFile(File file, File dir) throws IOException {
        ZipOutputStream stream = new ZipOutputStream(new FileOutputStream(file));
        FileFun fun = new ZipFileFun(stream, dir);
        try {
            recurseDir(dir, fun);
        } catch (IOException ioe) {
            throw (ioe);
        } finally {
            stream.close();            
        }
    }
    
    /*
     * <p> 
     *	
     * 		Apply a <code>FileFun</code> to every file in a given directory.
     * 		The recursion is done in a secure mode, no exception is thrown.
     * 		
     * </p> 
     * 
     * @param dir				The directory to recurse.
     * @param fun				The function to apply.
     * @return					The result of the recursion.
     * @throws IOException		If something goes wrong during elaboration.
     */    
    public static Result recurseDirSecure(File dir, FileFun fun) throws IOException {
        Result result = new Result();
        recurseDirSecure(dir, fun, new ResultExHandler(result));
        return result;
    }

    /*
     * <p> 
     *	
     * 		Apply a <code>FileFun</code> to every file in a given directory.
     * 		The recursion is done in a secure mode, no exception is thrown.
     * 		
     * </p> 
     * 
     * @param dir				The directory to recurse.
     * @param fun				The function to apply.
     * @param handler			The exception handler.*
     * @throws IOException		If something goes wrong during elaboration.
     */
    public static void recurseDirSecure(File dir, FileFun fun, ExHandler handler) throws IOException {
        recurseDir(dir, new FileFunSecure(fun, handler));
    }
    
    /*
     * <p> 
     *	Apply a <code>FileFun</code> to every file in a given directory.
     * </p> 
     * 
     * @param dir				The directory to recurse.
     * @param fun				The function to apply.
     * @throws IOException		If something goes wrong during elaboration.
     */     
    public static void recurseDir(File dir, FileFun fun) throws IOException {
        if (dir.isDirectory()) {
            File[] list = dir.listFiles();
            for (int k=0; k<list.length; k++) {
                recurseDir(list[k], fun);
                fun.handleFile(list[k]);
            }
        }
    }
    
    public static void recurseDir(File dir, FileFun fun, boolean includeBaseDir) throws IOException {
        recurseDir(dir, fun);
        if ( includeBaseDir ) {
        	fun.handleFile( dir );
        }
    }

    /*
     * <p> 
     *	Apply a <code>FileFun</code> to every file in a given directory.
     * </p> 
     * 
     * @param dir				The directory to recurse.
     * @param fun				The function to apply.
     * @throws IOException		If something goes wrong during elaboration.
     */     
    public static void recurseDirClose(File dir, FileFun fun) throws IOException {
       recurseDir(dir, fun);
       fun.close();
    }
    
    /*
     * <p> 
     *	Read the content of a file.
     * </p> 
     * 
     * @param file				Data to write to the files.
     * @return					The content of the file as a <code>java.lang.StringBuffer</code> array.
     * @throws IOException		If something goes wrong during elaboration.
     */ 
    public static StringBuilder readStringBuffer(String file) throws IOException {
        return readStringBuffer(new File(file));
    }    
    
    /*
     * <p> 
     *	Read the content of a file.
     * </p> 
     * 
     * @param file				Data to write to the files.
     * @return					The content of the file as a <code>java.lang.String</code> array.
     * @throws IOException		If something goes wrong during elaboration.
     */ 
    public static String readString(String file) throws IOException {
        return readString(new File(file));
    }
    
    /*
     * <p> 
     *	Read the content of a file.
     * </p> 
     * 
     * @param file				Data to write to the files.
     * @return					The content of the file as a <code>byte[]</code> array.
     * @throws IOException		If something goes wrong during elaboration.
     */ 
    public static byte[] readBytes(String file) throws IOException {
        return readBytes(new File(file));
    }    
    
    /*
     * <p> 
     *	Read the content of a file.
     * </p> 
     * 
     * @param file				Data to write to the files.
     * @return					The content of the file as a <code>char[]</code> array.
     * @throws IOException		If something goes wrong during elaboration.
     */     
    public static char[] readChars(String file) throws IOException {
        return readChars(new File(file));
    }    
    
    /*
     * <p> 
     *	Read the content of a file.
     * </p> 
     * 
     * @param file				Data to write to the files.
     * @return					The content of the file as a <code>java.lang.String</code> array.
     * @throws IOException		If something goes wrong during elaboration.
     */
    public static StringBuilder readStringBuffer(File file) throws IOException {
        return (new StringBuilder(readString(file)));
    }    
    
    /*
     * <p> 
     *	Read the content of a file.
     * </p> 
     * 
     * @param file				Data to write to the files.
     * @return					The content of the file as a <code>java.lang.String</code> array.
     * @throws IOException		If something goes wrong during elaboration.
     */ 
    public static String readString(File file) throws IOException {
        return (new String(readBytes(file)));
    }
    
    /*
     * <p> 
     *	Read the content of a file.
     * </p> 
     * 
     * @param file				Data to write to the files.
     * @return					The content of the file as a <code>byte[]</code> array.
     * @throws IOException		If something goes wrong during elaboration.
     */ 
    public static byte[] readBytes(File file) throws IOException {
        ByteArrayOutputStream dst = new ByteArrayOutputStream();
        StreamIO.pipeStreamCloseBoth(new FileInputStream(file), dst);
        return dst.toByteArray();
    }    
    
    /*
     * <p> 
     *	Read the content of a file.
     * </p> 
     * 
     * @param file				Data to write to the files.
     * @return					The content of the file as a <code>char[]</code> array.
     * @throws IOException		If something goes wrong during elaboration.
     */ 
    public static char[] readChars(File file) throws IOException {
        CharArrayWriter dst = new CharArrayWriter();
        StreamIO.pipeCharCloseBoth(new FileReader(file), dst);
        return dst.toCharArray();
    }
    
    /*
     * <p> 
     *	Write to a file.
     * </p> 
     * 
     * @param data				Data to write to the files.
     * @param file				Data to write to the files.
     * @return					The number of char effectively written.
     * @throws IOException		If something goes wrong during elaboration.
     */
    public static long writeStringBuffer(CharSequence data, String file ) throws IOException {
        return writeStringBuffer(data, new File(file));
    }    
    
    /*
     * <p> 
     *	Write to a file.
     * </p> 
     * 
     * @param data				Data to write to the files.
     * @param file				Data to write to the files.
     * @return					The number of char effectively written.
     * @throws IOException		If something goes wrong during elaboration.
     */
    public static long writeString(String data, String file ) throws IOException {
        return writeString(data, new File(file));
    }
    
    /*
     * <p> 
     *	Write to a file.
     * </p> 
     * 
     * @param data				Data to write to the files.
     * @param file				Data to write to the files.
     * @return					The number of bytes effectively written.
     * @throws IOException		If something goes wrong during elaboration.
     */   
    public static long writeBytes(byte[] data, String file ) throws IOException {
        return writeBytes(data, new File(file));
    }    
    
    /*
     * <p> 
     *	Write to a file.
     * </p> 
     * 
     * @param data				Data to write to the files.
     * @param file				Data to write to the files.
     * @return					The number of char effectively written.
     * @throws IOException		If something goes wrong during elaboration.
     */
    public static long writeChars(char[] data, String file ) throws IOException {
        return writeChars(data, new File(file));
    }
    
    /*
     * <p> 
     *	Write to a file.
     * </p> 
     * 
     * @param data				Data to write to the files.
     * @param file				Data to write to the files.
     * @return					The number of char effectively written.
     * @throws IOException		If something goes wrong during elaboration.
     */   
    public static long writeStringBuffer(CharSequence data, File file ) throws IOException {
        return writeString(data.toString(), file);
    }    
    
    /*
     * <p> 
     *	Write to a file.
     * </p> 
     * 
     * @param data				Data to write to the files.
     * @param file				Data to write to the files.
     * @return					The number of char effectively written.
     * @throws IOException		If something goes wrong during elaboration.
     */  
    public static long writeString(String data, File file ) throws IOException {
        return writeChars(data.toCharArray(), file);
    }    
    
    /*
     * <p> 
     *	Write to a file.
     * </p> 
     * 
     * @param data				Data to write to the files.
     * @param file				Data to write to the files.
     * @return					The number of bytes effectively written.
     * @throws IOException		If something goes wrong during elaboration.
     */ 
    public static long writeBytes(byte[] data, File file ) throws IOException {
        return StreamIO.pipeStream(new ByteArrayInputStream(data), new FileOutputStream(file), StreamIO.MODE_CLOSE_BOTH );
    }    
    
    /*
     * <p> 
     *	Write to a file.
     * </p> 
     * 
     * @param data				Data to write to the files.
     * @param file				Data to write to the files.
     * @return					The number of char effectively written.
     * @throws IOException		If something goes wrong during elaboration.
     */
    public static long writeChars(char[] data, File file ) throws IOException {
        return StreamIO.pipeChar(new CharArrayReader(data), new FileWriter(file), StreamIO.MODE_CLOSE_BOTH );
    }
    
    private FileIO() {
        super();
    }

}
