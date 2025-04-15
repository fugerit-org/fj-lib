package test.org.fugerit.java.core.function;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.function.UnsafeVoid;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.function.Consumer;

/*
 * Abstract : this test class shows why and how to use the method in utility class:
 *
 * org.fugerit.java.core.function.SafeFunction
 *
 * the goal is to compare SafeFunction usage with classical "try { ... } catch ( ... ) {} approach.
 *
 * When using try catch, many times two simple patterns are applied
 *
 * error logging :
   try {
       [...]
   } catch (Exception e) {
        log.error( .... );
   }
 *
 * or :
 *
 * error rethrow :
   try {
       [...]
   } catch (Exception e) {
        throw new CustomException( e ); // often a runtime exception
   }
 *
 * SafeFunction utility uses lambdas to help handle this situation in a standard way.
 *
 * Main benefits are :
 * - standardized exception handling
 * - try section and catch section can be tested separately
 *   (for instance in unit test, where sometimes it's hard to reproduce the error condition)
 *
 * Some drawbacks could be :
 * - the code and stack traces maybe more difficult to read as coders are not used to it
 * - if not customized, default behaviour is to always use a wrapping exception :
 *   org.fugerit.java.core.cfg.ConfigRuntimeException
 *
 * NOTE: if a custom exception handling is needed, it is possible to either :
 *  - provide a custom exception handling to SafeFunction
 *  - revert to standard try / catch approach
 *
 * here is the javadoc :
 * https://javadoc.io/doc/org.fugerit.java/fj-core/latest/org/fugerit/java/core/function/SafeFunction.html
 *
 * and here the source code :
 * https://javadoc.io/doc/org.fugerit.java/fj-core/latest/org/fugerit/java/core/function/SafeFunction.html
 *
 * going further :
 * a few other options are provided rethrowing custom non-runtime exceptions, for instance :
 * - org.fugerit.java.core.io.helper.HelperIOException
 * - org.fugerit.java.core.db.dao.DAOException
 * - org.fugerit.java.core.xml.XMLException
 *
 */
@Slf4j
public class TestPOCSafeFunction {

    /*
     * simple function to use as the body of this class's scenarios
     *
     * it always throws a java.io.IOException
     */
    private static final UnsafeVoid<Exception> SCENARIO = () -> {
        if ( Boolean.TRUE ) {
            throw new IOException( "My scenario exception" );
        }
    };

    /*
     * simple custom exception handler.
     *
     * if the exception is a java.io.IOException it will only log the message
     * otherwise it will log both message and stack trace
     *
     * log level will be 'warning' anyway.
     *
     */
    private static final Consumer<Exception> CUSTOM_EXCEPTION_HANDLER = e -> {
        if ( e instanceof IOException ) {
            SafeFunction.EX_CONSUMER_LOG_WARN.accept( e );
        } else {
            SafeFunction.EX_CONSUMER_TRACE_WARN.accept( e );
        }
    };

    public void applyTryCatch() {
        try {
            SCENARIO.apply();
        } catch (Exception e) {
            throw new ConfigRuntimeException( e );
        }
    }

    public void applySafeFunction() {
        SafeFunction.apply( SCENARIO::apply );
    }

    /*
     * In this POC, a piece of code (SCENARIO:apply) is called both in :
     * - try catch mode
     * - SafeFunction.apply() mode.
     * And a org.fugerit.java.core.cfg.ConfigRuntimeException is checked to be thrown.
     */
    @Test
    public void testSafeFunctionApply() {
        // classical try catch
        Assert.assertThrows( ConfigRuntimeException.class, this::applyTryCatch );
        // safe function
        Assert.assertThrows( ConfigRuntimeException.class, this::applySafeFunction );
    }

    public boolean getTryCatch() {
        try {
            SCENARIO.apply();
            return Boolean.TRUE;
        } catch (Exception e) {
            throw new ConfigRuntimeException( e );
        }
    }

    public boolean getSafeFunction() {
        SafeFunction.apply( SCENARIO::apply );
        return Boolean.TRUE;
    }

    /*
     * In this POC, a piece of code (SCENARIO:apply) is called both in :
     * - try catch mode
     * - SafeFunction.get() mode.
     * And a org.fugerit.java.core.cfg.ConfigRuntimeException is checked to be thrown.
     */
    @Test
    public void testSafeFunctionGet() {
        // classical try catch
        Assert.assertThrows( ConfigRuntimeException.class, this::getTryCatch );
        // safe function
        Assert.assertThrows( ConfigRuntimeException.class, this::getSafeFunction );
    }

    /*
     * In this POC, a piece of code (SCENARIO:apply) is called both in :
     * - try catch mode
     * - SafeFunction.apply() mode.
     * And the thrown IO Exception is just logged with no further handling
     */
    @Test
    public void testSafeFunctionApplySilent() {
        // classical try catch
        try {
            SCENARIO.apply();
        } catch (Exception e) {
            log.warn( "Exception suppressed : {}", e.toString() );
        }
        // safe function
        SafeFunction.applySilent( SCENARIO::apply );
    }

    /*
     * In this POC, a piece of code (SCENARIO:apply) is called both in :
     * - try catch mode
     * - SafeFunction.apply() mode.
     * And a custom exception handling is applied.
     */
    @Test
    public void testSafeFunctionApplyCustomHandling() {
        // classical try catch
        try {
            SCENARIO.apply();
        } catch (Exception e) {
            CUSTOM_EXCEPTION_HANDLER.accept( e );
        }
        // safe function
        SafeFunction.apply( SCENARIO::apply, CUSTOM_EXCEPTION_HANDLER );
    }

}
