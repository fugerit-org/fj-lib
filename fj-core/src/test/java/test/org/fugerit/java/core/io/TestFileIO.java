package test.org.fugerit.java.core.io;

import org.fugerit.java.core.io.FileIO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class TestFileIO {

    @Test
    public void testIsInTmpFolderOk() throws IOException  {
        Assertions.assertTrue(FileIO.isInTmpFolder( new File( System.getProperty( "java.io.tmpdir" ) ) ));
    }


    @Test
    public void testIsInTmpFolderKo() throws IOException  {
        Assertions.assertFalse(FileIO.isInTmpFolder( new File( "/" ) ));
    }

    @Test
    public void testNewFile() throws IOException {
        String baseDir = "target/";
        String fileName = "not-exists.txt";
        String fileNameExists = "classes";
        File file = FileIO.newFile( baseDir, fileName );
        Assertions.assertTrue( file.getPath().endsWith( fileName ) );
        Assertions.assertTrue( FileIO.newFile( null, fileName ).getPath().endsWith( fileName ) );
        Assertions.assertThrows( IOException.class, () -> FileIO.newFile( baseDir, fileName, Boolean.TRUE ) );
        Assertions.assertTrue( FileIO.newFile( baseDir, fileName, Boolean.FALSE ).getPath().endsWith( fileName ) );
        Assertions.assertTrue( FileIO.newFile( baseDir, fileNameExists, Boolean.TRUE ).getPath().endsWith( fileNameExists ) );
    }


    @Test
    public void testCreateFullFile() throws IOException {
        String baseDir0 = "target/";
        String baseDir1 = "target/path/";
        String fileName = "not-exists-alt.txt";
        String fileNameExists = "classes";
        File file0 = FileIO.newFile( baseDir0, fileName );
        File file1 = FileIO.newFile( baseDir1, fileName );
        File file2 = FileIO.newFile( baseDir0, fileNameExists );
        file0.delete();
        file1.delete();
        file1.getParentFile().delete();
        file2.delete();
        Assertions.assertTrue( FileIO.createFullFile( file0 ) );
        Assertions.assertTrue( FileIO.createFullFile( file1 ) );
        Assertions.assertFalse( FileIO.createFullFile( file0 ) );
        Assertions.assertFalse( FileIO.createFullFile( file2 ) );
        Assertions.assertFalse( FileIO.createFullFile( new File( baseDir1 ) ) );
    }

}
