package test.org.fugerit.java.core.testhelpers;

import java.io.IOException;
import java.io.InputStream;

import org.fugerit.java.core.lang.helpers.BooleanUtils;

public class FailInputStream extends InputStream {

	@Override
	public int read() throws IOException {
		if ( BooleanUtils.isTrue( BooleanUtils.BOOLEAN_TRUE ) ) {
			throw new IOException( "fail read" );
		}
		return 0;
	}

}
