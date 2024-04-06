package test.org.fugerit.java.core.lang.ex;

import org.fugerit.java.core.lang.ex.ExUtils;
import org.junit.Assert;
import org.junit.Test;

public class TestExUtils {

    @Test
    public void testExUtils() {
        Assert.assertThrows( UnsupportedOperationException.class, () -> ExUtils.THROW_UNSUPPORTED.accept( "test1()" ) );
        ExUtils.WARN_UNSUPPORTED.accept( "test2()" );
    }

}
