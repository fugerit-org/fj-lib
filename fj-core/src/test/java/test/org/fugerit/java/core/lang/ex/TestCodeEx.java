package test.org.fugerit.java.core.lang.ex;

import org.fugerit.java.core.lang.ex.CodeEx;
import org.fugerit.java.core.util.result.Result;
import org.junit.Assert;
import org.junit.Test;

public class TestCodeEx implements CodeEx {

    private int code = Result.RESULT_CODE_OK;

    @Override
    public int getCode() {
        return  this.code;
    }

    @Test
    public void testCodeEx() {
        TestCodeEx codeEx = new TestCodeEx();
        Assert.assertNull( codeEx.getMessage() );
        Assert.assertNull( codeEx.getCause() );
        Assert.assertEquals( Result.RESULT_CODE_OK, codeEx.getCode() );
    }

}
