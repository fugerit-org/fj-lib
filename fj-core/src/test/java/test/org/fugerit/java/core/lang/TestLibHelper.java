package test.org.fugerit.java.core.lang;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.lang.LibHelper;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestLibHelper {

    private static final String LLP = "java.library.path";

    @Test
    public void testCount0() throws IOException {
        File libraryPath = new File( "src/test/resources/core/lang/lib_helper" );
        String llPath = System.getProperty( LLP );
        System.setProperty( LLP, llPath+File.pathSeparator+libraryPath.getCanonicalPath() );
        int count1 = LibHelper.ll( "test_ll_fug1" );
        Assert.assertEquals( 1, count1 );
        Assert.assertThrows( ConfigRuntimeException.class, () -> LibHelper.ll( "test_ll_fug" ) );
        Assert.assertEquals( 0, LibHelper.ll( "test_ll_not_found" ) );
    }

}
