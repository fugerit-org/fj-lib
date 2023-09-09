package test.org.fugerit.java.core.cfg;

import java.io.Closeable;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CloseableFactory {

	public static final Closeable DO_NOTHING_CLOSEABLE = new Closeable() {
		@Override
		public void close() throws IOException {
			log.info( "closing!" );
		}
	};
	
	public static final Closeable ERROR_CLOSEABLE = new Closeable() {
		@Override
		public void close() throws IOException {
			throw new IOException( "error closing scenario" );
		}
	};
	
	public static AutoCloseable wrap( Closeable c ) {
		return new AutoCloseable() {
			@Override
			public void close() throws Exception {
				c.close();
			}
		};
	}
	
}
