package test.org.fugerit.java.core.db.dao.daogen;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.daogen.ByteArrayDataHandler;
import org.fugerit.java.core.db.daogen.CharArrayDataHandler;
import org.fugerit.java.core.io.ArchiveIO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

@Slf4j
class TestByteArrayDataHandler {

    private static final String TEST_STRING = "test";

    @Test
    void testByteArrayDataHandler() throws DAOException, IOException {
        ByteArrayDataHandler ch = ByteArrayDataHandler.newHandlerByte( TEST_STRING.getBytes() );
        Assertions.assertEquals( TEST_STRING, ch.toString() );
        Assertions.assertEquals( TEST_STRING, String.valueOf( ch.toChars() ) );
        try (ZipOutputStream zos = new ZipOutputStream( new ByteArrayOutputStream())) {
            ArchiveIO.addEntry( "test.txt", zos, ch );
        }
    }

}
