package test.org.fugerit.java.core.db.dao.daogen;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.fugerit.java.core.db.dao.RSExtractor;

import test.org.fugerit.java.core.db.helpers.MemDBTestBase;

public class TestAddressSelectHelper extends MemDBTestBase {

	public static final String COL_INFO = "INFO";
	
	public static RSExtractor<String> RSE_ADDRESS_COL_INFO = new RSExtractor<String>() {
		@Override
		public String extractNext(ResultSet rs) throws SQLException {
			return rs.getString( COL_INFO );
		}
	};

	
}
