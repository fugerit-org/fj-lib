package test.org.fugerit.java.core.io;

import org.fugerit.java.core.io.FileIO;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestFileIO {

    @Test
    public void testIsInTmpFolderOk() throws IOException  {
        Assert.assertTrue(FileIO.isInTmpFolder( new File( System.getProperty( "java.io.tmpdir" ) ) ));
    }


    @Test
    public void testIsInTmpFolderKo() throws IOException  {
        Assert.assertFalse(FileIO.isInTmpFolder( new File( "/" ) ));
    }

    @Test
    public void testNewFile() throws IOException {
        String baseDir = "target/";
        String fileName = "not-exists.txt";
        String fileNameExists = "classes";
        File file = FileIO.newFile( baseDir, fileName );
        Assert.assertTrue( file.getPath().endsWith( fileName ) );
        Assert.assertThrows( IOException.class, () -> FileIO.newFile( baseDir, fileName, Boolean.TRUE ) );
        Assert.assertTrue( FileIO.newFile( baseDir, fileName, Boolean.FALSE ).getPath().endsWith( fileName ) );
        Assert.assertTrue( FileIO.newFile( baseDir, fileNameExists, Boolean.TRUE ).getPath().endsWith( fileNameExists ) );
    }

}
