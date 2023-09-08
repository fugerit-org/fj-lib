package test.org.fugerit.java.core.jvfs.fun;

import java.io.File;
import java.io.IOException;

import org.fugerit.java.core.jvfs.JFileFun;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.fun.JFileFunResult;
import org.fugerit.java.core.jvfs.fun.JFileFunSafe;
import org.fugerit.java.core.util.result.ResultExHandler;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.core.jvfs.TestJMountHelper;
import test.org.fugerit.java.core.jvfs.TestJVFSHelper;

@Slf4j
public class TestJFileFun extends TestJVFSHelper {

	@Test
	public void testFun() throws IOException {
		File dest = new File( "target/test_fun_01" );
		log.info( "create dest dir : {} -> {}", dest, dest.mkdirs() );
		JVFS jvfs =  TestJMountHelper.getRealJVFSDefault();
		JFileFun fun = f -> log.info( "test {}", f );
		JFileFunResult result = new JFileFunResult( fun );
		result.handle( jvfs.getRoot() );
		result.reset();
		log.info( "result {}", result.getResult() );
		JFileFun fail = f -> { throw new IOException( "fail : "+f ); };
		Assert.assertThrows( IOException.class , () -> fail.handle( jvfs.getRoot() ) );
		JFileFunSafe safe = new JFileFunSafe( fail , new ResultExHandler() );
		safe.handle( jvfs.getRoot() );
	}
	
}
