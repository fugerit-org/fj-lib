package test.org.fugerit.java.core.jvfs;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.fugerit.java.core.db.dao.DAOUtilsNG;
import org.fugerit.java.core.db.dao.rse.StringRSE;
import org.fugerit.java.test.db.helper.MemDBHelper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDBJMount extends TestJVFSHelper {

	private final static Logger logger = LoggerFactory.getLogger( TestDBJMount.class );
	
	@Test
	public void testInitMemDB() {
		try {
			MemDBHelper.init();
			try ( Connection conn = MemDBHelper.newConnection() ) {
				List<String> listFile = new ArrayList<>();
				String testSql = "SELECT file_name FROM fugerit.db_jvfs_file";
				DAOUtilsNG.extraAll(conn, listFile, testSql, StringRSE.DEFAULT);
				logger.info( "listFile {}", listFile );
			}
		} catch (Exception e) {
			failEx(e);
		}
	}
	
}
