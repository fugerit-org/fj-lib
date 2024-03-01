package test.org.fugerit.java.core.db.dao.daogen;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.daogen.ByteArrayDataHandler;
import org.fugerit.java.core.db.daogen.CharArrayDataHandler;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class TestByteArrayDataHandler {

    @Test
    public void testByteArrayDataHandler() throws DAOException {
        ByteArrayDataHandler ch = ByteArrayDataHandler.newHandlerByte( "test".getBytes() );
        Assert.assertEquals( "test", ch.toString() );
    }

}
