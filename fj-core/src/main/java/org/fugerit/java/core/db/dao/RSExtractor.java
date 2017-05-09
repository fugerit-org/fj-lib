package org.fugerit.java.core.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * <p>.</p>
 *
 * Fugerit
 */
public interface RSExtractor<K> {

    public K extractNext(ResultSet rs) throws SQLException;
    
}
