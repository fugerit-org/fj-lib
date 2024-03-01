package test.org.fugerit.java.core.db.dao.daogen;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.daogen.CharArrayDataHandler;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class TestCharArrayDataHandler {

    @Test
    public void testCharArrayDataHandler() throws DAOException {
        CharArrayDataHandler ch = CharArrayDataHandler.newHandlerByte( "test".toCharArray() );
        Assert.assertEquals( "test", ch.toString() );
    }

}
