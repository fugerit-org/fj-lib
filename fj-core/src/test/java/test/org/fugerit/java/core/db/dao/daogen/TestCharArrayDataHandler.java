package test.org.fugerit.java.core.db.dao.daogen;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.daogen.CharArrayDataHandler;
import org.fugerit.java.core.io.ArchiveIO;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

@Slf4j
public class TestCharArrayDataHandler {

    private static final String TEST_STRING = "test";

    @Test
    public void testCharArrayDataHandler() throws DAOException, IOException {
        CharArrayDataHandler ch = CharArrayDataHandler.newHandlerByte( TEST_STRING.toCharArray() );
        Assert.assertEquals( TEST_STRING, ch.toString() );
        Assert.assertEquals( TEST_STRING, new String( ch.toBytes() ) );
        try (ZipOutputStream zos = new ZipOutputStream( new ByteArrayOutputStream())) {
            ArchiveIO.addEntry( "test.txt", zos, ch );
        }
    }

}
