/*
 *
		Fugerit Java Library is distributed under the terms of :

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


	Full license :
		http://www.apache.org/licenses/LICENSE-2.0
		
	Project site: 
		https://www.fugerit.org/
	
	SCM site :
		https://github.com/fugerit79/fj-lib
	
 *
 */
package org.fugerit.java.core.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.log.BasicLogObject;
import org.fugerit.java.core.log.LogFacade;


/**
 * <p>Base class for persistance object.</p>
 *
 * @author Fugerit
 */
public class BasicDAO<T> extends BasicLogObject {
	
	protected void extractAll( ResultSet rs, List<T> list, RSExtractor<T> rse ) throws DAOException {
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
	
	public List<T> newList() {
		return new ArrayList<T>();
	}	
	
	private int update( OpDAO<T> op, Connection conn ) throws SQLException {
		int result = 0;
		String query = this.queryFormat( op.getSql(), "update(opdao)" );
		PreparedStatement pstm = conn.prepareStatement( query );
		this.setAll( pstm, op.getFieldList() );
		result = pstm.executeUpdate();
		return result;
	}		

	public boolean updateBatch( List<OpDAO<T>> opList ) throws DAOException {
		boolean result = true;
		Connection conn = this.getConnection();
		try {
			conn.setAutoCommit( false );
			int tmp = 0;
			PreparedStatement pstm = null;
			try {
				for (int k=0; k<opList.size(); k++) {
					OpDAO<T> currentOp = (OpDAO<T>)opList.get( k );
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
			} finally {
				this.closeSafe( pstm );
			}
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

	
	public boolean updateTransaction( List<OpDAO<T>> opList ) throws DAOException {
		boolean result = true;
		Connection conn = this.getConnection();
		try {
			conn.setAutoCommit( false );
			int tmp = 0;
//			String query = null;
//			PreparedStatement pstm = null;
			for (int k=0; k<opList.size(); k++) {
				OpDAO<T> currentOp = opList.get( k );
				this.getLogger().debug( "updateTransaction : "+currentOp.getSql()+" , params : "+currentOp.getFieldList().size() );
				if (currentOp.getType()==OpDAO.TYPE_UPDATE) {
//					tmp+= this.update( currentOp, conn );
//					String tmpQuery = this.queryFormat( currentOp.getSql(), "update(opdao)" );
//					if ( !tmpQuery.equals( query ) ) {
//						pstm = conn.prepareStatement( tmpQuery );
//					}
//					this.setAll( pstm, currentOp.getFieldList() );
//					tmp+= pstm.executeUpdate();
					tmp+= this.update( currentOp, conn ); // old, non caching mode
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

    public void init(DAOFactory daoFactory ) {
        
    }
    
    public static final FieldList NO_FIELDS = new FieldList( null );
    
    private DAOFactory daoFactory;
    
    private QueryWrapper queryWrapper;
    
    public QueryWrapper getQueryWrapper() {
		return queryWrapper;
	}

	protected BasicDAO(DAOFactory daoFactory) {
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
    
    protected DAOFactory getDaoFactory() {
        return daoFactory;
    }
    
    protected FieldFactory getFieldFactory() {
        return this.getDaoFactory().getFieldFactory();
    }
    
    protected Connection getConnection() throws DAOException {
        return this.getDaoFactory().getConnection();
    }
    
    protected void close(Connection conn) throws DAOException {
        DAOHelper.close(conn);
    }
    
    protected void setAll( PreparedStatement ps, FieldList fields ) throws SQLException {
    	DAOHelper.setAll( ps , fields, this );
    }
    
    protected boolean execute(String query, FieldList fields) throws DAOException {
		query = this.queryFormat( query, "execute" );	
        boolean result = false;
        try ( Connection conn = this.getConnection();
        		PreparedStatement ps = conn.prepareStatement( query ) ) {
            this.setAll(ps, fields);
            result = ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw (new DAOException(e));
        }
        return result;
    }
    
	protected boolean execute(String query) throws DAOException {
		query = this.queryFormat( query, "execute" );	
		   boolean result = false;
		   try (Connection conn = this.getConnection();
				   PreparedStatement ps = conn.prepareStatement( query )) {
			   result = ps.execute();
		   } catch (SQLException e) {
			   throw (new DAOException(e));
		   }
		   return result;
	   }
    
	protected int update(OpDAO<T> op) throws DAOException {
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

	protected T loadOne( OpDAO<T> op ) throws DAOException {
		return this.loadOne( op.getSql(), op.getFieldList(), op.getRsExtractor() );
	}
	
	protected void loadAll( List<T> list, OpDAO<T> op ) throws DAOException {
		this.loadAll( list, op.getSql(), op.getFieldList(), op.getRsExtractor() );
	}
	
    protected void loadAll(List<T> l, String query, Field f, RSExtractor<T> re) throws DAOException {
        this.loadAll(l, query, this.newFieldList(f), re);
    }    
    
    protected List<T> loadAll(String query, Field f, RSExtractor<T> re) throws DAOException {
        return this.loadAll(query, this.newFieldList(f), re);
    }    
    
    protected T loadOne(String query, Field f,  RSExtractor<T> re) throws DAOException {
        return this.loadOne(query, this.newFieldList(f), re);
    }

    protected void loadAll(List<T> l, String query, RSExtractor<T> re) throws DAOException {
        this.loadAll(l, query, NO_FIELDS, re);
    }        
    
    protected List<T> loadAll(String query, RSExtractor<T> re) throws DAOException {
        return this.loadAll(query, NO_FIELDS, re);
    }    
    
    protected T loadOne(String query, RSExtractor<T> re) throws DAOException {
        return this.loadOne(query, NO_FIELDS, re);
    }
    
    protected T loadOne(String query, FieldList fields, RSExtractor<T> re) throws DAOException {
	    return DAOHelper.loadOne(query, fields, re, this.getDaoFactory(), this );
    }
    
    
    protected List<T> loadAll(String query, FieldList fields, RSExtractor<T> re) throws DAOException {
        List<T> l = this.newList();
        this.loadAll(l, query, fields, re);
        return l;
    }
    
    protected LoadResult<T> loadAllResult( String query, FieldList fields, RSExtractor<T> re) throws DAOException {
	   return LoadResult.initResult( this , query, fields, re );

    }    
  

	protected void loadAll(List<T> l, String query, FieldList fields, RSExtractor<T> re ) throws DAOException {
		DAOHelper.loadAll( l , query, fields, re, this.getDaoFactory() , this );
	}

	protected String queryFormat( String sql, String method ) {
		return DAOHelper.queryFormat(sql, method, this, this.getDaoFactory() );
	}

	private void closeSafe( AutoCloseable c ) {
		if ( c != null ) {
			try {
				c.close();
			} catch (Exception e) {
				LogFacade.getLog().warn( "Failed to close : "+c, e );
			}
		}
	}
	
}

abstract class QueryWrapper {
	
	public abstract String createPagedQuery( String sql, PageInfoDB info );
	
}

class OracleQueryWrapper extends QueryWrapper {

	public static final QueryWrapper INSTANCE = new OracleQueryWrapper();
	
	@Override
	public String createPagedQuery(String sql, PageInfoDB info) {
		StringBuilder result = new StringBuilder();
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
	
	@Override
	public String createPagedQuery(String sql, PageInfoDB info) {
		StringBuilder result = new StringBuilder();
		result.append( "SELECT paged.* FROM ( " );
		result.append( sql );
		result.append( ") paged LIMIT "+info.getSize()+" OFFSET "+( (info.getNumber()-1)*info.getSize() ) );
		return result.toString();
	}
	
}

class MysqlQueryWrapper extends QueryWrapper {

	public static final QueryWrapper INSTANCE = new MysqlQueryWrapper();
	
	@Override
	public String createPagedQuery(String sql, PageInfoDB info) {
		StringBuilder result = new StringBuilder();
		result.append( "SELECT paged.* FROM ( " );
		result.append( sql );
		result.append( ") paged LIMIT "+( (info.getNumber()-1)*info.getSize() )+", "+info.getSize() );
		return result.toString();
	}
	
}
