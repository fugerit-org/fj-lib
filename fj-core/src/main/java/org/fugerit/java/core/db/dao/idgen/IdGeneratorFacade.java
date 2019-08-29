package org.fugerit.java.core.db.dao.idgen;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.connect.SingleConnectionFactory;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.daogen.DAOContext;
import org.fugerit.java.core.db.helpers.DbUtils;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.util.PropsIO;

public class IdGeneratorFacade {

	private final static Properties SEQ_TYPES = PropsIO.loadFromClassLoaderSafe( "core/dao/idgen/seq_generator.properties" );
	
	public static BasicSeqIdGenerator sequenceGenerator( DAOContext context, String sequenceName ) throws DAOException {
		return sequenceGenerator( context.getConnection(), sequenceName );
	}
	
	public static BasicSeqIdGenerator sequenceGenerator( Connection conn, String sequenceName ) throws DAOException {
		ConnectionFactory cf = new SingleConnectionFactory( conn );
		return sequenceGenerator(cf, sequenceName);
	}
	
	public static BasicSeqIdGenerator sequenceGenerator( ConnectionFactory cf, String sequenceName ) throws DAOException {
		int dbType = DbUtils.DB_UNKNOWN;;
		try {
			dbType = DbUtils.indentifyDB( cf.getConnection() );
		} catch (SQLException e) {
			throw new DAOException( e );
		}
		return sequenceGenerator( cf, sequenceName, dbType );
	}
	
	public static BasicSeqIdGenerator sequenceGenerator( ConnectionFactory cf, String sequenceName, int dbType ) throws DAOException {
		BasicSeqIdGenerator gen = null;
		try {
			
			String type = SEQ_TYPES.getProperty( String.valueOf( dbType ) );
			if ( type != null ) {
				gen = (BasicSeqIdGenerator)ClassHelper.newInstance( type );
				gen.setSequenceName( sequenceName );
				gen.setConnectionFactory( cf );
			}
		} catch (Exception e) {
			throw new DAOException( e );
		}
		return gen;
	}
	
}
