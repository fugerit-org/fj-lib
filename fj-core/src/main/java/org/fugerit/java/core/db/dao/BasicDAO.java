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

import org.fugerit.java.core.cfg.CloseHelper;
import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.log.LogObject;
import org.slf4j.Logger;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * Base class for persistance object.
 * </p>
 *
 * @author Fugerit
 */
@Slf4j
public class BasicDAO<T> implements LogObject {

	@Override
	public Logger getLogger() {
		return log;
	}

	protected void extractAll(ResultSet rs, List<T> list, RSExtractor<T> rse) throws DAOException {
		DAOException.apply( () -> {
			while (rs.next()) {
				list.add(rse.extractNext(rs));
			}
		} );
	}

	public FieldList newFieldList(Field field) {
		return new FieldList(this.getFieldFactory(), field);
	}

	public FieldList newFieldList() {
		return new FieldList(this.getFieldFactory());
	}

	public List<T> newList() {
		return new ArrayList<T>();
	}

	private int update(OpDAO<T> op, Connection conn) throws SQLException {
		int result = 0;
		String query = this.queryFormat(op.getSql(), "update(opdao)");
		PreparedStatement pstm = conn.prepareStatement(query);
		this.setAll(pstm, op.getFieldList());
		result = pstm.executeUpdate();
		return result;
	}

	public boolean updateBatch(List<OpDAO<T>> opList) throws DAOException {
		boolean result = true;
		Connection conn = this.getConnection();
		try {
			conn.setAutoCommit(false);
			int tmp = 0;
			PreparedStatement pstm = null;
			try {
				for (int k = 0; k < opList.size(); k++) {
					OpDAO<T> currentOp = (OpDAO<T>) opList.get(k);
					this.getLogger().debug("updateBatch : {} , params : {}", currentOp.getSql(),
							currentOp.getFieldList().size());
					if (pstm == null) {
						String query = this.queryFormat(currentOp.getSql(), "update(opdao)");
						pstm = conn.prepareStatement(query);
					}
					this.setAll(pstm, currentOp.getFieldList());
					pstm.addBatch();
				}
				this.getLogger().debug("updateBatch result : {} / {}", tmp, opList.size());
				conn.commit();
			} finally {
				this.closeSafe(pstm);
			}
		} catch (SQLException e) {
			DAOException.apply(conn::rollback);
		} finally {
			DAOException.apply(() -> {
				try {
					conn.setAutoCommit(true);
				} finally {
					this.close(conn);
				}
			});
		}
		return result;
	}

	public boolean updateTransaction(List<OpDAO<T>> opList) throws DAOException {
		boolean result = true;
		Connection conn = this.getConnection();
		try {
			conn.setAutoCommit(false);
			int tmp = 0;
			for (int k = 0; k < opList.size(); k++) {
				OpDAO<T> currentOp = opList.get(k);
				this.getLogger().debug("updateTransaction : {} , params : {}", currentOp.getSql(),
						currentOp.getFieldList().size());
				if (currentOp.getType() == OpDAO.TYPE_UPDATE) {
					tmp += this.update(currentOp, conn); // old, non caching mode
				}
			}
			this.getLogger().debug("updateTransaction result : {} / {}", tmp, opList.size());
			conn.commit();
		} catch (SQLException e) {
			DAOException.apply(conn::rollback);
		} finally {
			DAOException.apply(() -> {
				try {
					conn.setAutoCommit(true);
				} finally {
					this.close(conn);
				}
			});
		}
		return result;
	}

	public void init(DAOFactory daoFactory) {
		// do nothing implementation, subclasses may override it if needed
	}

	public static final FieldList NO_FIELDS = new FieldList(null);

	private DAOFactory daoFactory;

	private QueryWrapper queryWrapper;

	public QueryWrapper getQueryWrapper() {
		return queryWrapper;
	}

	protected QueryWrapper getQueryWrapperFor( String productName ) {
		QueryWrapper qw = null;
		productName = productName.toLowerCase();
		if (productName.indexOf("mysql") != -1 || productName.indexOf("maria") != -1) {
			qw = MysqlQueryWrapper.INSTANCE;
		} else if (productName.indexOf("postgres") != -1) {
			qw = PostgresQueryWrapper.INSTANCE;
		} else if (productName.indexOf("oracle") != -1) {
			qw = OracleQueryWrapper.INSTANCE;
		} else {
			qw = null;
		}
		return qw;
	}
	
	protected BasicDAO(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
		SafeFunction.apply(() -> {
			try (Connection conn = this.getConnection()) {
				String productName = conn.getMetaData().getDatabaseProductName();
				this.queryWrapper = this.getQueryWrapperFor(productName);
				this.getLogger().debug("product name : {} query wrapper init : {}", productName, this.queryWrapper);
			}
		});
	}

	protected BasicDAO(ConnectionFactory connectionFactory) {
		this(new BasicDAOFactory(connectionFactory));
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

	protected void setAll(PreparedStatement ps, FieldList fields) throws SQLException {
		DAOHelper.setAll(ps, fields, this);
	}

	protected boolean execute(String query, FieldList fields) throws DAOException {
		return DAOException.get(() -> {
			String newQuery = this.queryFormat(query, "execute");
			try (Connection conn = this.getConnection(); PreparedStatement ps = conn.prepareStatement(newQuery)) {
				this.setAll(ps, fields);
				return ps.execute();
			}
		});
	}

	protected boolean execute(String query) throws DAOException {
		return execute(query, NO_FIELDS);
	}

	protected int update(OpDAO<T> op) throws DAOException {
		return this.update(op.getSql(), op.getFieldList());
	}

	protected int update(String query, Field field) throws DAOException {
		return this.update(query, this.newFieldList(field));
	}

	protected int update(String query) throws DAOException {
		return this.update(query, this.newFieldList());
	}

	protected int update(String query, FieldList fields) throws DAOException {
		return DAOException.get(() -> {
			String newQuery = this.queryFormat(query, "update");
			try (Connection conn = this.getConnection(); PreparedStatement ps = conn.prepareStatement(newQuery)) {
				this.setAll(ps, fields);
				int res = ps.executeUpdate();
				if (!conn.getAutoCommit()) {
					conn.commit();
				}
				return res;
			}
		});
	}

	protected int delete(String query, FieldList fields) throws DAOException {
		return DAOException.get(() -> {
			String newQuery = this.queryFormat(query, "delete");
			try (Connection conn = this.getConnection(); PreparedStatement ps = conn.prepareStatement(newQuery)) {
				this.setAll(ps, fields);
				int res = ps.executeUpdate();
				if (!conn.getAutoCommit()) {
					conn.commit();
				}
				return res;
			}
		});
	}

	protected T loadOne(OpDAO<T> op) throws DAOException {
		return this.loadOne(op.getSql(), op.getFieldList(), op.getRsExtractor());
	}

	protected void loadAll(List<T> list, OpDAO<T> op) throws DAOException {
		this.loadAll(list, op.getSql(), op.getFieldList(), op.getRsExtractor());
	}

	protected void loadAll(List<T> l, String query, Field f, RSExtractor<T> re) throws DAOException {
		this.loadAll(l, query, this.newFieldList(f), re);
	}

	protected List<T> loadAll(String query, Field f, RSExtractor<T> re) throws DAOException {
		return this.loadAll(query, this.newFieldList(f), re);
	}

	protected T loadOne(String query, Field f, RSExtractor<T> re) throws DAOException {
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
		return DAOHelper.loadOne(query, fields, re, this.getDaoFactory(), this);
	}

	protected List<T> loadAll(String query, FieldList fields, RSExtractor<T> re) throws DAOException {
		List<T> l = this.newList();
		this.loadAll(l, query, fields, re);
		return l;
	}

	protected LoadResult<T> loadAllResult(String query, FieldList fields, RSExtractor<T> re) throws DAOException {
		return LoadResult.initResult(this, query, fields, re);

	}

	protected void loadAll(List<T> l, String query, FieldList fields, RSExtractor<T> re) throws DAOException {
		DAOHelper.loadAll(l, query, fields, re, this.getDaoFactory(), this);
	}

	protected String queryFormat(String sql, String method) {
		return DAOHelper.queryFormat(sql, method, this, this.getDaoFactory());
	}

	private void closeSafe(AutoCloseable c) {
		this.getLogger().debug("closed? : {}", CloseHelper.closeSilent(c));
	}

}

class OracleQueryWrapper extends QueryWrapper {

	public static final QueryWrapper INSTANCE = new OracleQueryWrapper();

	@Override
	public String createPagedQuery(String sql, PageInfoDB info) {
		StringBuilder result = new StringBuilder();
		int start = ((info.getNumber() - 1) * info.getSize());
		int end = start + info.getSize();
		result.append("SELECT paged2.* FROM( ");
		result.append("SELECT paged1.*, ROWNUM AS RN FROM ( ");
		result.append(sql);
		result.append(") paged1 ");
		result.append(") paged2 WHERE paged2.rn > " + start + " AND paged2.rn <= " + end);
		return result.toString();
	}

}

class PostgresQueryWrapper extends QueryWrapper {

	public static final QueryWrapper INSTANCE = new PostgresQueryWrapper();

	@Override
	public String createPagedQuery(String sql, PageInfoDB info) {
		StringBuilder result = new StringBuilder();
		result.append("SELECT paged.* FROM ( ");
		result.append(sql);
		result.append(") paged LIMIT " + info.getSize() + " OFFSET " + ((info.getNumber() - 1) * info.getSize()));
		return result.toString();
	}

}

class MysqlQueryWrapper extends QueryWrapper {

	public static final QueryWrapper INSTANCE = new MysqlQueryWrapper();

	@Override
	public String createPagedQuery(String sql, PageInfoDB info) {
		StringBuilder result = new StringBuilder();
		result.append("SELECT paged.* FROM ( ");
		result.append(sql);
		result.append(") paged LIMIT " + ((info.getNumber() - 1) * info.getSize()) + ", " + info.getSize());
		return result.toString();
	}

}
