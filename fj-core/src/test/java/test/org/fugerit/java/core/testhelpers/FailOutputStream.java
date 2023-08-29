package test.org.fugerit.java.core.testhelpers;

import java.io.IOException;
import java.io.OutputStream;

import org.fugerit.java.core.lang.helpers.BooleanUtils;

public class FailOutputStream extends OutputStream {

	@Override
	public void write(int b) throws IOException {
		if ( BooleanUtils.isTrue( BooleanUtils.BOOLEAN_TRUE ) ) {
			throw new IOException( "fail write" );
		}
	}

}
