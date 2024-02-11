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

}
