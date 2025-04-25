package test.org.fugerit.java.core.db.helpers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;

import org.fugerit.java.core.db.helpers.SQLScriptFacade;
import org.fugerit.java.core.db.helpers.SQLScriptReader;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.io.StreamIO;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import test.org.fugerit.java.core.db.TestBasicDBHelper;

@Slf4j
public class TestSQLScriptFacade extends TestBasicDBHelper {

	@Test
	void testReadScripts() {
		SafeFunction.apply( () -> {
			try ( InputStream is = ClassHelper.loadFromDefaultClassLoader( "test/memdb/base_db_init.sql" ) ) {
				String script = StreamIO.readString( is );
				log.info( "script -> {}", script );
				String[] res = SQLScriptFacade.parseScript(script);
				for ( int k=0; k<res.length; k++ ) {
					log.info( "current {} -> {}", k, res[k] );
				}
				Assertions.assertNotEquals( 0 , res.length );
				// test sql script
				try ( SQLScriptReader reader = new SQLScriptReader( new ByteArrayInputStream( "SELECT * FROM fugerit.user;".getBytes() ) );
						Connection conn = newConnection() ) {
					SQLScriptFacade.executeAll( reader , conn );
				}
				
			}
		} );
	}
	
}
