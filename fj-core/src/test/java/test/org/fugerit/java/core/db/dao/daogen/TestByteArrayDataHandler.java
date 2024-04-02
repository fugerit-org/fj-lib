package test.org.fugerit.java.core.db.dao.daogen;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.daogen.ByteArrayDataHandler;
import org.fugerit.java.core.db.daogen.CharArrayDataHandler;
import org.fugerit.java.core.io.ArchiveIO;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

@Slf4j
public class TestByteArrayDataHandler {

    private static final String TEST_STRING = "test";

    @Test
    public void testByteArrayDataHandler() throws DAOException, IOException {
        ByteArrayDataHandler ch = ByteArrayDataHandler.newHandlerByte( TEST_STRING.getBytes() );
        Assert.assertEquals( TEST_STRING, ch.toString() );
        Assert.assertEquals( TEST_STRING, String.valueOf( ch.toChars() ) );
        try (ZipOutputStream zos = new ZipOutputStream( new ByteArrayOutputStream())) {
            ArchiveIO.addEntry( "test.txt", zos, ch );
        }
    }

}
