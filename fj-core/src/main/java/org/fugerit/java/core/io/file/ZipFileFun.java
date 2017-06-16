package org.fugerit.java.core.io.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.fugerit.java.core.io.StreamIO;

/*
 * 
 * 
 * @author Fugerit
 *
 */
public class ZipFileFun extends AbstractFileFun {

    private int base;
    
    private ZipOutputStream stream;
    
    public ZipFileFun(ZipOutputStream stream, File baseDir) {
        super();
        this.stream = stream;
        this.base = baseDir.getAbsolutePath().length()+1;
    }

    /* (non-Javadoc)
     * @see org.fugerit.java.core.io.FileFun#handleFile(java.io.File)
     */
    public void handleFile(File file) throws IOException {
        String path = file.getAbsolutePath();
        String entryPath = path.substring(this.base);
        if (file.isDirectory()) {
            entryPath+= File.separator;
        }
        ZipEntry entry = new ZipEntry(entryPath);
        this.stream.putNextEntry(entry);
        if (!file.isDirectory()) {
            StreamIO.pipeStream(new FileInputStream(file), this.stream, StreamIO.MODE_CLOSE_IN_ONLY);            
        }        
        this.stream.closeEntry();
    }

}
