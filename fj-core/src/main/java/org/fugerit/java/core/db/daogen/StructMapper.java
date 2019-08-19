package org.fugerit.java.core.db.daogen;

import java.sql.SQLException;
import java.util.Map;

public interface StructMapper {

	public Map<String, Class<?>> newTypeMapper() throws SQLException;
	
}
