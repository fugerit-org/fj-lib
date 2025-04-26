package test.org.fugerit.java.core.lang.ex;

import org.fugerit.java.core.lang.ex.ExUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestExUtils {

    @Test
    void testExUtils() {
        Assertions.assertThrows( UnsupportedOperationException.class, () -> ExUtils.THROW_UNSUPPORTED.accept( "test1()" ) );
        ExUtils.WARN_UNSUPPORTED.accept( "test2()" );
    }

}
