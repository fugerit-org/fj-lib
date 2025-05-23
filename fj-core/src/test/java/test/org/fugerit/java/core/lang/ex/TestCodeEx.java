package test.org.fugerit.java.core.lang.ex;

import org.fugerit.java.core.lang.ex.CodeEx;
import org.fugerit.java.core.util.result.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestCodeEx implements CodeEx {

    private int code = Result.RESULT_CODE_OK;

    @Override
    public int getCode() {
        return  this.code;
    }

    @Test
    void testCodeEx() {
        TestCodeEx codeEx = new TestCodeEx();
        Assertions.assertNull( codeEx.getMessage() );
        Assertions.assertNull( codeEx.getCause() );
        Assertions.assertEquals( Result.RESULT_CODE_OK, codeEx.getCode() );
    }

}
