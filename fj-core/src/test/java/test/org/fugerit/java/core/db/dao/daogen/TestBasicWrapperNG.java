package test.org.fugerit.java.core.db.dao.daogen;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.db.daogen.BasicHelper;
import org.fugerit.java.core.db.daogen.BasicWrapperNG;
import org.fugerit.java.core.function.SafeFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import test.org.fugerit.java.BasicTest;
import test.org.fugerit.java.core.db.dao.ModelUser;

import java.io.NotSerializableException;

@Slf4j
class TestBasicWrapperNG {

    private static final BasicTest HELPER = new BasicTest();

    @Test
    public void testBasicWrapperNG() {
        SafeFunction.apply( () -> {
            ModelUser userTest = new ModelUser();
            // wrapper ng
            BasicWrapperNG<ModelUser> userWrapperNG = new BasicWrapperNG<>( userTest );
            log.info( "test 1 : {}", userWrapperNG.unwrap() );
            log.info( "test 2 : {}", userWrapperNG.unwrapModel() );
            log.info( "test 3 : {}", userWrapperNG );
            userWrapperNG.wrapModel( userTest );
            Assertions.assertThrows(NotSerializableException.class, () -> HELPER.fullSerializationTest( userWrapperNG ));
            BasicHelper.throwUnsupported( "test 0", () -> false ); // will not throw unsupported operation
            Assertions.assertThrows( UnsupportedOperationException.class, () -> BasicHelper.throwUnsupported( "test 1" ) );
        } );
    }

}
