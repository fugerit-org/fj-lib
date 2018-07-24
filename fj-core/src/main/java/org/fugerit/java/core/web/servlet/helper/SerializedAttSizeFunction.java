package org.fugerit.java.core.web.servlet.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;

import org.fugerit.java.core.log.BasicLogObject;
import org.fugerit.java.core.util.result.BasicResult;
import org.fugerit.java.core.util.result.Result;

public class SerializedAttSizeFunction extends BasicLogObject implements AttributeFunction {

	public SerializedAttSizeFunction() {
		this.totalSize = 0;
	}
	
	private int totalSize;
	
	public static int calcSerializedSize( Object value ) throws NotSerializableException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream( baos );
		os.writeObject( value );
		os.flush();
		os.close();
		return baos.toByteArray().length;
	}
	
	@Override
	public Result apply(String key, Object value) {
		Result res = BasicResult.DEFAULT_OK_RESULT;
		try {
			int currentObjectSize = calcSerializedSize( value );
			this.totalSize += currentObjectSize;
			this.getLogger().debug( "current object size : (attribute name:"+key+") "+currentObjectSize );
		} catch (NotSerializableException nse) {
			this.getLogger().warn( "not serializable object : (attribute name:"+key+")" );
			res = BasicResult.DEFAULT_KO_RESULT;
		} catch (IOException ioe) {
			throw new RuntimeException( "Function failed "+ioe, ioe );
		}
		return res;
	}

	public int getTotalSize() {
		return totalSize;
	}

}
