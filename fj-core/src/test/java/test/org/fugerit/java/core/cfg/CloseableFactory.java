package test.org.fugerit.java.core.cfg;

import java.io.Closeable;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CloseableFactory {

	public static final Closeable DO_NOTHING_CLOSEABLE = () -> log.info( "closing!" );
	
	public static final Closeable ERROR_CLOSEABLE = () -> { throw new IOException( "error closing scenario" ); };
	
	public static final AutoCloseable ERROR_AUTO_CLOSEABLE = wrap( ERROR_CLOSEABLE );

	public static AutoCloseable wrap( Closeable c ) {
		return c::close;
	} 
	
}
