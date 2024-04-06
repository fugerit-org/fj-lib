package org.fugerit.java.core.lang.ex;

import lombok.extern.slf4j.Slf4j;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Slf4j
public class ExUtils {

    private ExUtils() {}

    private static String newUnsupportedMessage( String function ) {
        return String.format( "Function %s is not supported", function );
    }

    private static final BiConsumer<String, Boolean> DEFAULT_UNSUPPORTED_HANDLER = ( function, condition ) -> {
        if ( condition.booleanValue() ) {
            throw newUnsupported( function );
        } else {
            log.warn( newUnsupportedMessage( function ) );
        }
    };

    public static final Consumer<String> THROW_UNSUPPORTED = function -> DEFAULT_UNSUPPORTED_HANDLER.accept( function, Boolean.TRUE );

    public static final Consumer<String> WARN_UNSUPPORTED = function -> DEFAULT_UNSUPPORTED_HANDLER.accept( function, Boolean.FALSE );

    public static UnsupportedOperationException newUnsupported( String function ) {
        return new UnsupportedOperationException( newUnsupportedMessage( function ) );
    }

}
