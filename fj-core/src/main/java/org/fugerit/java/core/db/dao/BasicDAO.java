package org.fugerit.java.core.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.helpers.DAOID;
import org.fugerit.java.core.log.BasicLogObject;


/*
 * <p>Classe base per la gestione di oggetti di accesso al DB.</p>
 *
 * Fugerit
 */
public class BasicDAO extends BasicLogObject {

	public BufferedDAO newBufferedDAO( int commitOn ) {
		return new BufferedDAO( this, commitOn );
	}
	
	public BufferedDAO newBufferedDAO() {
		return new BufferedDAO( this );
	}
	
	protected void extractAll( ResultSet rs, List list, RSExtractor rse ) throws DAOException {
		try {
			while ( rs.next() ) {
				list.add( rse.extractNext( rs ) );
			}
		} catch (SQLException e) {
			throw ( new DAOException( e ) );
		}
	}

	public FieldList newFieldList( Field field ) {
		return new FieldList( this.getFieldFactory(), field );
	}
	
	public FieldList newFieldList() {
		return new FieldList( this.getFieldFactory() );
	}
	
	public List<Object> newList() {
		return new ArrayList<Object>();
	}	
	
	private int update( OpDAO op, Connection conn ) throws SQLException {
		int result = 0;
		String query = this.queryFormat( op.getSql(), "update(opdao)" );
		PreparedStatement pstm = conn.prepareStatement( query );
		this.setAll( pstm, op.getFieldList() );
		result = pstm.executeUpdate();
		return result;
	}		

	public boolean updateBatch( List<OpDAO> opList ) throws DAOException {
		boolean result = true;
		Connection conn = this.getConnection();
		try {
			conn.setAutoCommit( false );
			int tmp = 0;
			PreparedStatement pstm = null;
			for (int k=0; k<opList.size(); k++) {
				OpDAO currentOp = (OpDAO)opList.get( k );
				this.getLogger().debug( "updateBatch : "+currentOp.getSql()+" , params : "+currentOp.getFieldList().size() );
				if ( pstm == null ) {
					String query = this.queryFormat( currentOp.getSql(), "update(opdao)" );
					pstm = conn.prepareStatement( query );
				}
				this.setAll( pstm, currentOp.getFieldList() );
				pstm.addBatch();
			}
			this.getLogger().debug( "updateBatch result : "+tmp+" / "+opList.size() );
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				this.getLogger().error( "Errore durante il rollback : ", e );
			}
			throw (new DAOException(e));
		} finally {
			try {
				conn.setAutoCommit( true );
			} catch (SQLException e) {
				this.getLogger().error( "Errore durante deallocazione connessione [conn.setAutoCommit( true )]", e );
			} finally {
				this.close( conn );
			}
		}
		return result;
	}

	
	public boolean updateTransaction( List opList ) throws DAOException {
		boolean result = true;
		Connection conn = this.getConnection();
		try {
			conn.setAutoCommit( false );
			int tmp = 0;
//			String query = null;
//			PreparedStatement pstm = null;
			for (int k=0; k<opList.size(); k++) {
				OpDAO currentOp = (OpDAO)opList.get( k );
				this.getLogger().debug( "updateTransaction : "+currentOp.getSql()+" , params : "+currentOp.getFieldList().size() );
				if (currentOp.getType()==OpDAO.TYPE_UPDATE) {
//					tmp+= this.update( currentOp, conn );
//					String tmpQuery = this.queryFormat( currentOp.getSql(), "update(opdao)" );
//					if ( !tmpQuery.equals( query ) ) {
//						pstm = conn.prepareStatement( tmpQuery );
//					}
//					this.setAll( pstm, currentOp.getFieldList() );
//					tmp+= pstm.executeUpdate();
					tmp+= this.update( currentOp, conn ); // vecchio codice non caching
				}
			}
			this.getLogger().debug( "updateTransaction result : "+tmp+" / "+opList.size() );
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				this.getLogger().error( "Errore durante il rollback : ", e );
			}
			throw (new DAOException(e));
		} finally {
			try {
				conn.setAutoCommit( true );
			} catch (SQLException e) {
				this.getLogger().error( "Errore durante deallocazione connessione [conn.setAutoCommit( true )]", e );
			} finally {
				this.close( conn );
			}
		}
		return result;
	}

    public void init(BasicDAOFactory daoFactory ) {
        
    }
    
	public RSExtractor RSE_LONG = new RSExtractor() {
		/* (non-Javadoc) 
		 * @see it.finanze.secin.shared.dao.RSExtractor#extractNext(java.sql.ResultSet)
		 */
		public Object extractNext(ResultSet rs) throws SQLException {
			return new Long( rs.getLong( 1 ) );
		}
    
	};    
	
	public RSExtractor RSE_DAOID = new RSExtractor() {
		/* (non-Javadoc) 
		 * @see it.finanze.secin.shared.dao.RSExtractor#extractNext(java.sql.ResultSet)
		 */
		public Object extractNext(ResultSet rs) throws SQLException {
			return new DAOID( rs.getLong( 1 ) );
		}
    
	};   	
    
	public RSExtractor RSE_INT = new RSExtractor() {
		/* (non-Javadoc) 
		 * @see it.finanze.secin.shared.dao.RSExtractor#extractNext(java.sql.ResultSet)
		 */
		public Object extractNext(ResultSet rs) throws SQLException {
			return new Integer( rs.getInt( 1 ) );
		}
    
	};
        
    
    public RSExtractor RSE_STRING = new RSExtractor() {
        /* (non-Javadoc) 
         * @see it.finanze.secin.shared.dao.RSExtractor#extractNext(java.sql.ResultSet)
         */
        public Object extractNext(ResultSet rs) throws SQLException {
            return rs.getString(1);
        }
    
    };
    
    private static final FieldList NO_FIELDS = new FieldList( null );
    
    private BasicDAOFactory daoFactory;
    
    private QueryWrapper queryWrapper;
    
    public QueryWrapper getQueryWrapper() {
		return queryWrapper;
	}

	protected BasicDAO(BasicDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
        try {
        	Connection conn = this.getConnection();
        	try {
        		String productName = conn.getMetaData().getDatabaseProductName().toLowerCase();
        		if ( productName.indexOf( "mysql" ) != -1 || productName.indexOf( "maria" ) != -1 ) {
        			this.queryWrapper = MysqlQueryWrapper.INSTANCE;
        		} else if ( productName.indexOf( "postgres" ) != -1 ) {
        			this.queryWrapper = PostgresQueryWrapper.INSTANCE;
        		} else if ( productName.indexOf( "oracle" ) != -1 ) {
        			this.queryWrapper = OracleQueryWrapper.INSTANCE;
        		} else {
        			this.queryWrapper = null;
        		}
        		this.getLogger().debug( "product name : "+productName+" query wrapper init : "+this.queryWrapper );
        	} catch (Exception e1) {
        		e1.printStackTrace();
        	} finally {
        		conn.close();
        	}
        } catch (Exception e) {
        	this.getLogger().warn( "Error on query wrapper setup : "+e, e );
        }
    }
    
    protected BasicDAO(ConnectionFactory connectionFactory) {
        this( new BasicDAOFactory( connectionFactory ) );
    }    
    
    /*
     * <p>Restituisce il valore di daoFactory.</p>
     *
     * @return il valore di daoFactory.
     */
    protected BasicDAOFactory getDaoFactory() {
        return daoFactory;
    }
    
    protected FieldFactory getFieldFactory() {
        return this.getDaoFactory().getFieldFactory();
    }
    
    protected Connection getConnection() throws DAOException {
        return this.getDaoFactory().getConnection();
    }
    
    protected void close(Connection conn) throws DAOException {
        try {
            conn.close();
        } catch (SQLException e) {
            throw (new DAOException(e));
        }
    }
    
    protected void setAll(PreparedStatement ps, FieldList fields) throws SQLException {
    	this.getLogger().debug( "Total Param Number : "+fields.size() );
    	int np = 0;
    	int k = 0;
		while ( k<fields.size() ) {
			np++;
			int param = (k+1);
			Field f = fields.getField(k);
			this.getLogger().debug( "Setting param n. "+param+", value: "+f.toString()+"(fl.size:"+fields.size()+")" );
			f.setField(ps, param);
			k++;
			this.getLogger().debug( "test : "+(k<fields.size())+" k:"+k+" fields.size:"+fields.size() );
		}    		
    	this.getLogger().debug( "Total param set : "+np );
    }

    protected boolean execute(String query, FieldList fields) throws DAOException {
		query = this.queryFormat( query, "execute" );	
        boolean result = false;
        Connection conn = this.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement( query );
            this.setAll(ps, fields);
            result = ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw (new DAOException(e));
        } finally {
            this.close( conn );
        }
        return result;
    }
    
	protected boolean execute(String query) throws DAOException {
		query = this.queryFormat( query, "execute" );	
		   boolean result = false;
		   Connection conn = this.getConnection();
		   try {
			   PreparedStatement ps = conn.prepareStatement( query );
			   result = ps.execute();
			   ps.close();
		   } catch (SQLException e) {
			   throw (new DAOException(e));
		   } finally {
			   this.close( conn );
		   }
		   return result;
	   }
    
	protected int update(OpDAO op) throws DAOException {
		return this.update( op.getSql(), op.getFieldList() );
	}
    
    protected int update(String query, Field field) throws DAOException {
        return this.update( query, this.newFieldList( field ) );
    }
    
    protected int update(String query) throws DAOException {
        return this.update( query, this.newFieldList() );
    }
    
    protected int update(String query, FieldList fields) throws DAOException {
		query = this.queryFormat( query, "update" );
        int result = 0;
        Connection conn = this.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement( query );
            this.setAll(ps, fields);
            result = ps.executeUpdate();
            ps.close();
            if ( !conn.getAutoCommit() ) {
            	conn.commit();
            }
        } catch (SQLException e) {
            throw (new DAOException(e));
        } finally {
            this.close( conn );
        }
        this.getLogger().debug( "update: result '"+result+"'" );
        return result;
    }



	protected int delete(String query, FieldList fields) throws DAOException {
		query = this.queryFormat( query, "delete" );
		int result = 0;
		Connection conn = this.getConnection();
		try {
			PreparedStatement ps = conn.prepareStatement( query );
			this.setAll(ps, fields);
			result = ps.executeUpdate();
			ps.close();
            if ( !conn.getAutoCommit() ) {
            	conn.commit();
            }
		} catch (SQLException e) {
			throw (new DAOException(e));
		} finally {
			this.close( conn );
		}
		return result;
	}

	protected Object loadOne( OpDAO op ) throws DAOException {
		return this.loadOne( op.getSql(), op.getFieldList(), op.getRsExtractor() );
	}
	
	protected void loadAll( List list, OpDAO op ) throws DAOException {
		this.loadAll( list, op.getSql(), op.getFieldList(), op.getRsExtractor() );
	}
	
	// TODO : review all paged result itnerface
//	
//	protected PagedResult alternatePagedResult( String query, FieldList fl, RSExtractor re, PageInfoDB info ) throws DAOException {
//		List list = this.newList();
//		OpDAO op = OpDAO.newQueryOp( query, fl , re );
//		this.loadAll(list, op);
//        PagedResult result = null;
//        if ( list.size() > 0 ) {
//        	result = PagedResult.newPagedResults( list, info.getSize() )[info.getNumber()-1];
//        }
//        return result;
//	}
//	
//	protected PagedResult loadAllPaged( String query, FieldList fl, RSExtractor re, PageInfoDB info ) throws DAOException {
//		PagedResult pr = null;
//		if ( this.queryWrapper == null ) {
//			pr = this.alternatePagedResult(query, fl, re, info);
//		} else {
//			OpDAO countOp = OpDAO.newQueryOp( "SELECT count(*) AS countsize FROM ( "+query+" ) countview" , fl, LongRSE.DEFAULT );
//			int count = ((Long)this.loadOne( countOp )).intValue();
//			StringBuffer sql = new StringBuffer();
//			sql.append( query );
//			if ( info.getOrder() != null ) {
//				sql.append( " ORDER BY "+info.getOrder() );
//			}
//			OpDAO queryOp = OpDAO.newQueryOp( this.queryWrapper.createPagedQuery( sql.toString(), info) , fl, re );
//			List page = this.newList();
//			this.loadAll( page, queryOp );
//			pr = PagedResult.newPagedResult( info.getSize() , count, info.getNumber(), page );
//		}
//		return pr;
//	}

    protected void loadAll(List l, String query, Field f, RSExtractor re) throws DAOException {
        this.loadAll(l, query, this.newFieldList(f), re);
    }    
    
    protected List loadAll(String query, Field f, RSExtractor re) throws DAOException {
        return this.loadAll(query, this.newFieldList(f), re);
    }    
    
    protected Object loadOne(String query, Field f,  RSExtractor re) throws DAOException {
        return this.loadOne(query, this.newFieldList(f), re);
    }

    protected void loadAll(List l, String query, RSExtractor re) throws DAOException {
        this.loadAll(l, query, NO_FIELDS, re);
    }        
    
    
	protected void loadAll(List l, String query, RSExtractor re, boolean emptyFirst) throws DAOException {
		   this.loadAll(l, query, NO_FIELDS, re, emptyFirst);
	   }        
    
    
    
    protected List loadAll(String query, RSExtractor re) throws DAOException {
        return this.loadAll(query, NO_FIELDS, re);
    }    
    
    protected Object loadOne(String query, RSExtractor re) throws DAOException {
        return this.loadOne(query, NO_FIELDS, re);
    }
    
    protected Object loadOne(String query, FieldList fields, RSExtractor re) throws DAOException {
	    this.getLogger().debug("loadOne START ");
		query = this.queryFormat( query, "loadOne" );
        this.getLogger().debug("loadOne fields        : '"+fields.size()+"'");
        this.getLogger().debug("loadOne RSExtractor   : '"+re+"'");    	
        Object result = null;
        Connection conn = this.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement( query );
            this.setAll(ps, fields);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result =  re.extractNext( rs );
            } 
            rs.close();
            ps.close();
        } catch (SQLException e) {
        	throw (new DAOException( e.getMessage()+"[query:"+query+"]", e ));
        } finally {
            this.close( conn );
        }
        this.getLogger().debug("loadOne END : "+result );
        return result;
    }
    
    protected List loadAll(String query, FieldList fields, RSExtractor re) throws DAOException {
        List l = new Vector();
        this.loadAll(l, query, fields, re);
        return l;
    }
    
    protected LoadResult loadAllResult( String query, FieldList fields, RSExtractor re) throws DAOException {
	   return LoadResult.initResult( this , query, fields, re );

    }    
    
    protected void loadAll(List l, String query, FieldList fields, RSExtractor re) throws DAOException {
	    this.getLogger().debug("loadAll START list : '"+l.size()+"'");
		query = this.queryFormat( query, "loadAll" );
        this.getLogger().debug("loadAll fields        : '"+fields.size()+"'");
        this.getLogger().debug("loadAll RSExtractor   : '"+re+"'");
        Connection conn = this.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement( query );
            this.setAll(ps, fields);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                l.add( re.extractNext( rs ) );
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
        	throw (new DAOException( e.getMessage()+"[query:"+query+"]", e ));
        } finally {
            this.close( conn );
        }
        this.getLogger().debug("loadAll END list : '"+l.size()+"'");

    }


	protected void loadAll(List l, String query, FieldList fields, RSExtractor re, boolean emptyFirst) throws DAOException {
			this.getLogger().debug("loadAll START list : '"+l.size()+"'");
			query = this.queryFormat( query, "loadAll" );
			this.getLogger().debug("loadAll fields        : '"+fields.size()+"'");
			this.getLogger().debug("loadAll RSExtractor   : '"+re+"'");
			Connection conn = this.getConnection();
			try {
				PreparedStatement ps = conn.prepareStatement( query );
				this.setAll(ps, fields);
				ResultSet rs = ps.executeQuery();
				int i=0;
				while (rs.next()) {
					l.add( re.extractNext( rs ) );
					i++;
				}
				rs.close();
				ps.close();
			} catch (SQLException e) {
				throw (new DAOException( e.getMessage()+"[query:"+query+"]", e ));
			} finally {
				this.close( conn );
			}
			this.getLogger().debug("loadAll END list : '"+l.size()+"'");
		}


	protected String queryFormat( String sql, String method ) {
		this.getLogger().debug( "input  query : "+sql );
		this.getLogger().debug( "params :       "+this.getDaoFactory().getSqlArgs() );
		MessageFormat f = new MessageFormat( sql );
		sql = f.format( this.getDaoFactory().getSqlArgs() );
		this.getLogger().debug( "output query : "+sql );
		return sql;
	}

    protected String loadString(String query, Field field) throws DAOException {
        return (String)this.loadOne( query, field, RSE_STRING );
    }
    
    protected String loadString(String query, FieldList fields) throws DAOException {
        return (String)this.loadOne( query, fields, RSE_STRING );
    }

}

abstract class QueryWrapper {
	
	public abstract String createPagedQuery( String sql, PageInfoDB info );
	
}

class OracleQueryWrapper extends QueryWrapper {

	public static final QueryWrapper INSTANCE = new OracleQueryWrapper();
	
	public String createPagedQuery(String sql, PageInfoDB info) {
		StringBuffer result = new StringBuffer();
		int start = ((info.getNumber()-1)*info.getSize());
		int end = start+info.getSize();
		result.append( "SELECT paged2.* FROM( " );
		result.append( "SELECT paged1.*, ROWNUM AS RN FROM ( " );
		result.append( sql );
		result.append( ") paged1 " );
		result.append( ") paged2 WHERE paged2.rn > "+start+" AND paged2.rn <= "+end );
		return result.toString();
	}
	
}

class PostgresQueryWrapper extends QueryWrapper {

	public static final QueryWrapper INSTANCE = new PostgresQueryWrapper();
	
	public String createPagedQuery(String sql, PageInfoDB info) {
		StringBuffer result = new StringBuffer();
		result.append( "SELECT paged.* FROM ( " );
		result.append( sql );
		result.append( ") paged LIMIT "+info.getSize()+" OFFSET "+( (info.getNumber()-1)*info.getSize() ) );
		return result.toString();
	}
	
}

class MysqlQueryWrapper extends QueryWrapper {

	public static final QueryWrapper INSTANCE = new MysqlQueryWrapper();
	
	public String createPagedQuery(String sql, PageInfoDB info) {
		StringBuffer result = new StringBuffer();
		result.append( "SELECT paged.* FROM ( " );
		result.append( sql );
		result.append( ") paged LIMIT "+( (info.getNumber()-1)*info.getSize() )+", "+info.getSize() );
		return result.toString();
	}
	
}