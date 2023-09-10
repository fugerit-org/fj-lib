package test.org.fugerit.java.core.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.connect.ConnectionFactoryCloseable;
import org.fugerit.java.core.db.connect.ConnectionFactoryImpl;
import org.fugerit.java.core.db.dao.BasicDAO;
import org.fugerit.java.core.db.dao.BasicDAOFactory;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.dao.DAOFactory;
import org.fugerit.java.core.db.dao.Field;
import org.fugerit.java.core.db.dao.FieldFactory;
import org.fugerit.java.core.db.dao.FieldList;
import org.fugerit.java.core.db.dao.LoadResult;
import org.fugerit.java.core.db.dao.OpDAO;
import org.fugerit.java.core.db.dao.PageInfoDB;
import org.fugerit.java.core.db.dao.QueryWrapper;
import org.fugerit.java.core.db.dao.RSExtractor;
import org.fugerit.java.core.db.dao.rse.StringRSE;
import org.fugerit.java.core.function.SafeFunction;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;

import test.org.fugerit.java.core.db.BasicDBHelper;

public class TestBasicDAO extends BasicDBHelper {

	private ConnectionFactoryCloseable connFactory() {
		return ConnectionFactoryImpl.wrap( new ConnectionFactoryImpl() {	
			@Override
			public Connection getConnection() throws DAOException {
				return newConnection();
			}
		});
	}
	
	private static final String USERNAME_TEST = "user1";
	
	@Test
	public void testBasicDAO() {
		SafeFunction.apply( () -> {
			try ( ConnectionFactoryCloseable cf = this.connFactory() ) {
				BasicDAOFactory bdf = new BasicDAOFactory( cf );
				BasicDAOCheck dao = new BasicDAOCheck( bdf );
				dao.init( bdf );
				dao.getLogger().info( "test : {}", 1 );
				// parameters : 
				FieldList flUsernameTest = dao.newFieldList( FieldFactory.DEFAULT.newField( USERNAME_TEST ) );
				// select all
				SafeFunction.apply( () -> {
					List<String> result = dao.newList();
					dao.loadAll( result , OpDAO.newQueryOp( "SELECT username FROM fugerit.user WHERE username = ?" , flUsernameTest, StringRSE.DEFAULT ) );
					Assert.assertEquals( 1 , result.size() );
				} );
				// select all
				SafeFunction.apply( () -> {
					List<String> result = dao.newList();
					dao.loadAll( result , "SELECT username FROM fugerit.user", StringRSE.DEFAULT );
					Assert.assertFalse( result.isEmpty() );
				} );
				// select all
				SafeFunction.apply( () -> {
					List<String> result = dao.newList();
					try ( Connection conn = dao.getConnection();
							Statement stm = conn.createStatement();
							ResultSet rs = stm.executeQuery( "SELECT username FROM fugerit.user" )) {
						dao.extractAll( rs , result, StringRSE.DEFAULT );
						Assert.assertFalse( result.isEmpty() );	
					}
				} );
				// select all
				SafeFunction.apply( () -> {
					List<String> result = dao.newList();
					dao.loadAll( result , "SELECT username FROM fugerit.user WHERE username = ?", flUsernameTest.getField( 0 ), StringRSE.DEFAULT );
					Assert.assertFalse( result.isEmpty() );
				} );
				// select all
				SafeFunction.apply( () -> {
					List<String> result = dao.loadAll( "SELECT username FROM fugerit.user WHERE username = ?", flUsernameTest.getField( 0 ), StringRSE.DEFAULT );
					Assert.assertFalse( result.isEmpty() );
				} );
				// select all
				SafeFunction.apply( () -> {
					List<String> result = dao.newList();
					dao.loadAll( result ,"SELECT username FROM fugerit.user", StringRSE.DEFAULT );
					Assert.assertFalse( result.isEmpty() );
				} );
				// select all
				SafeFunction.apply( () -> {
					List<String> result = dao.loadAll( "SELECT username FROM fugerit.user", StringRSE.DEFAULT );
					Assert.assertFalse( result.isEmpty() );
				} );
				// select result
				SafeFunction.apply( () -> {
					LoadResult<String> lr = dao.loadAllResult( "SELECT username FROM fugerit.user WHERE username = ?", flUsernameTest, StringRSE.DEFAULT );
					try {
						lr.start();
						Assert.assertEquals( 1 , lr.startCount() );
						int count = 0;
						while ( lr.hasNext() ) {
							lr.getNext();
							count++;
						}
						Assert.assertEquals( 1 , count );
					} finally {
						lr.end();
					}
				} );
				// select one
				SafeFunction.apply( () -> {
					String username =  dao.loadOne( "SELECT username FROM fugerit.user WHERE username = ?" , flUsernameTest.getField( 0 ), StringRSE.DEFAULT );
					Assert.assertEquals( USERNAME_TEST , username );
				} );
				// select one
				SafeFunction.apply( () -> {
					String username =  dao.loadOne( "SELECT username FROM fugerit.user WHERE username IS NULL" , StringRSE.DEFAULT );
					Assert.assertNull( username );
				} );
				// select one
				SafeFunction.apply( () -> {
					String username =  dao.loadOne(  OpDAO.newQueryOp( "SELECT username FROM fugerit.user WHERE username = ?" , flUsernameTest, StringRSE.DEFAULT ) );
					Assert.assertEquals( USERNAME_TEST , username );
				} );
				// create 
				SafeFunction.apply( () -> {
					boolean res = dao.execute( "CREATE TABLE fugerit.basic_dao_test ( id VARCHAR(32) )" );
					Assert.assertFalse(res);
				} );
				// insert one 
				SafeFunction.apply( () -> {
					int res = dao.update(  OpDAO.newUpdateOp( "INSERT INTO fugerit.basic_dao_test VALUES ( ? )" , flUsernameTest) );
					Assert.assertEquals( 1 , res ) ;
				} );
				// delete one 
				SafeFunction.apply( () -> {
					int res = dao.update(  OpDAO.newUpdateOp( "DELETE FROM fugerit.basic_dao_test WHERE ID = ?" , flUsernameTest) );
					Assert.assertEquals( 1 , res ) ;
				} );
				// insert one 
				SafeFunction.apply( () -> {
					int res = dao.update(  "INSERT INTO fugerit.basic_dao_test VALUES ( ? )" , flUsernameTest.getField( 0 ) );
					Assert.assertEquals( 1 , res ) ;
				} );
				// update zero 
				SafeFunction.apply( () -> {
					int res = dao.update(  "UPDATE fugerit.basic_dao_test SET id = id WHERE 1=0"  );
					Assert.assertEquals( 0 , res ) ;
				} );
				// delete one 
				SafeFunction.apply( () -> {
					int res = dao.delete(  "DELETE FROM fugerit.basic_dao_test WHERE ID = ?" , flUsernameTest );
					Assert.assertEquals( 1 , res ) ;
				} );
				// insert batch
				SafeFunction.apply( () -> {
					boolean res = dao.updateBatch( this.createInsertOps(dao, 100, 50 ) ); 
					Assert.assertTrue(res);
				} );
				// insert batch
				SafeFunction.apply( () -> {
					boolean res = dao.updateTransaction( this.createInsertOps(dao, 100, 50 ) ); 
					Assert.assertTrue(res);
				} );
				// test query wrapper
				String[] products = { "maria", "mysql", "postgres", "oracle" };
				for ( int k=0; k<products.length; k++ ) {
					QueryWrapper wrapper = dao.getQueryWrapperFor( products[k] );
					PageInfoDB page = new PageInfoDB( 10 , 5 );
					String pagedSql = wrapper.createPagedQuery( "SELECT * FROM fugerit.users", page );
					dao.getLogger().info( "pagedSql : {}", pagedSql );
				}
				// other tests
				Assert.assertNull( dao.getQueryWrapper() );
				Assert.assertNotNull( new BasicDAOCheck( cf ) );
			}
		});
	}
	
	private List<OpDAO<String>> createInsertOps( BasicDAOCheck dao, int start, int size ) {
		List<OpDAO<String>> list = new ArrayList<>();
		for ( int k=0; k<size; k++ ) {
			FieldList fl = dao.newFieldList();
			fl.addField( FieldFactory.DEFAULT.newField( "user_"+(start+k) ) );
			list.add( OpDAO.newUpdateOp( "INSERT INTO fugerit.basic_dao_test VALUES ( ? )" , fl ) );
		}
		return list;
	}
	
}

class BasicDAOCheck extends BasicDAO<String> {

	protected BasicDAOCheck(DAOFactory daoFactory) {
		super(daoFactory);
	}

	public BasicDAOCheck(ConnectionFactory connectionFactory) {
		super(connectionFactory);
	}
	
	@Override
	public QueryWrapper getQueryWrapper() {
		return super.getQueryWrapper();
	}

	@Override
	protected QueryWrapper getQueryWrapperFor(String productName) {
		return super.getQueryWrapperFor(productName);
	}

	@Override
	public void loadAll(List<String> list, OpDAO<String> op) throws DAOException {
		super.loadAll(list, op);
	}

	@Override
	public Logger getLogger() {
		
		return super.getLogger();
	}

	@Override
	protected void extractAll(ResultSet rs, List<String> list, RSExtractor<String> rse) throws DAOException {
		
		super.extractAll(rs, list, rse);
	}

	@Override
	public FieldList newFieldList(Field field) {
		
		return super.newFieldList(field);
	}

	@Override
	public FieldList newFieldList() {
		
		return super.newFieldList();
	}

	@Override
	public List<String> newList() {
		
		return super.newList();
	}

	@Override
	public boolean updateBatch(List<OpDAO<String>> opList) throws DAOException {
		
		return super.updateBatch(opList);
	}

	@Override
	public boolean updateTransaction(List<OpDAO<String>> opList) throws DAOException {
		
		return super.updateTransaction(opList);
	}

	@Override
	public void init(DAOFactory daoFactory) {
		
		super.init(daoFactory);
	}

//	@Override
//	public QueryWrapper getQueryWrapper() {
//		
//		return super.getQueryWrapper();
//	}

	@Override
	protected DAOFactory getDaoFactory() {
		
		return super.getDaoFactory();
	}

	@Override
	protected FieldFactory getFieldFactory() {
		
		return super.getFieldFactory();
	}

	@Override
	protected Connection getConnection() throws DAOException {
		
		return super.getConnection();
	}

	@Override
	protected void close(Connection conn) throws DAOException {
		
		super.close(conn);
	}

	@Override
	protected void setAll(PreparedStatement ps, FieldList fields) throws SQLException {
		
		super.setAll(ps, fields);
	}

	@Override
	protected boolean execute(String query, FieldList fields) throws DAOException {
		
		return super.execute(query, fields);
	}

	@Override
	protected boolean execute(String query) throws DAOException {
		
		return super.execute(query);
	}

	@Override
	protected int update(OpDAO<String> op) throws DAOException {
		
		return super.update(op);
	}

	@Override
	protected int update(String query, Field field) throws DAOException {
		
		return super.update(query, field);
	}

	@Override
	protected int update(String query) throws DAOException {
		
		return super.update(query);
	}

	@Override
	protected int update(String query, FieldList fields) throws DAOException {
		
		return super.update(query, fields);
	}

	@Override
	protected int delete(String query, FieldList fields) throws DAOException {
		
		return super.delete(query, fields);
	}

	@Override
	protected String loadOne(OpDAO<String> op) throws DAOException {
		
		return super.loadOne(op);
	}

	@Override
	protected void loadAll(List<String> l, String query, Field f, RSExtractor<String> re) throws DAOException {
		
		super.loadAll(l, query, f, re);
	}

	@Override
	protected List<String> loadAll(String query, Field f, RSExtractor<String> re) throws DAOException {
		
		return super.loadAll(query, f, re);
	}

	@Override
	protected String loadOne(String query, Field f, RSExtractor<String> re) throws DAOException {
		
		return super.loadOne(query, f, re);
	}

	@Override
	protected void loadAll(List<String> l, String query, RSExtractor<String> re) throws DAOException {
		
		super.loadAll(l, query, re);
	}

	@Override
	protected List<String> loadAll(String query, RSExtractor<String> re) throws DAOException {
		
		return super.loadAll(query, re);
	}

	@Override
	protected String loadOne(String query, RSExtractor<String> re) throws DAOException {
		
		return super.loadOne(query, re);
	}

	@Override
	protected String loadOne(String query, FieldList fields, RSExtractor<String> re) throws DAOException {
		
		return super.loadOne(query, fields, re);
	}

	@Override
	protected List<String> loadAll(String query, FieldList fields, RSExtractor<String> re) throws DAOException {
		
		return super.loadAll(query, fields, re);
	}

	@Override
	protected LoadResult<String> loadAllResult(String query, FieldList fields, RSExtractor<String> re)
			throws DAOException {
		
		return super.loadAllResult(query, fields, re);
	}

	@Override
	protected void loadAll(List<String> l, String query, FieldList fields, RSExtractor<String> re) throws DAOException {
		
		super.loadAll(l, query, fields, re);
	}

	@Override
	protected String queryFormat(String sql, String method) {
		
		return super.queryFormat(sql, method);
	}
	
	
	
}