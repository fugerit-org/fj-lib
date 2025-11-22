package org.fugerit.java.core.db.daogen;

import org.fugerit.java.core.db.dao.DAORuntimeException;
import org.fugerit.java.core.function.SafeFunction;
import org.fugerit.java.core.function.SimpleValue;
import org.fugerit.java.core.util.ObjectUtils;

import java.sql.PreparedStatement;
import java.util.function.BiFunction;

public class StatementHelperLibrary {

    private StatementHelperLibrary() {}

    private static final String ATT_DAO_CONTEXT_PATTERN = "DaoContext.StatementHelper.%s.ATT_NAME";

    public static final String ATT_DAO_CONTEXT_QUERY_TIMEOUT = String.format( ATT_DAO_CONTEXT_PATTERN, "queryTimeout" );

    public static final String ATT_DAO_CONTEXT_FETCH_SIZE = String.format( ATT_DAO_CONTEXT_PATTERN, "fetchSize" );

    public static final BiFunction<PreparedStatement, DAOContext, PreparedStatement>
            DO_NOTHING_STATEMENT_HELPER = ( p, c ) -> p;

    public static final BiFunction<PreparedStatement, DAOContext, PreparedStatement>
            QUERY_TIMEOUT_FETCH_SIZE_STATEMENT_HELPER = newHelperPipeline( newHelperWithQueryTimeout( null ), newHelperWithFetchSize( null ) );

    public static final BiFunction<PreparedStatement, DAOContext, PreparedStatement>
            DEFAULT_STATEMENT_HELPER = hewHelperSafeSilent( QUERY_TIMEOUT_FETCH_SIZE_STATEMENT_HELPER );

    private static <T> T resolve( DAOContext daoContext, String key, T defaultValue ) {
        return ObjectUtils.objectWithDefault( (T)daoContext.getAttribute( key ), defaultValue );
    }

    public static DAOContext withQueryTimeout( DAOContext daoContext, Integer queryTimeout ) {
        if  ( queryTimeout != null ) {
            daoContext.setAttribute( ATT_DAO_CONTEXT_QUERY_TIMEOUT, queryTimeout );
        }
        return daoContext;
    }

    public static DAOContext withFetchSize( DAOContext daoContext, Integer fetchSize ) {
        if ( fetchSize != null ) {
            daoContext.setAttribute( ATT_DAO_CONTEXT_FETCH_SIZE, fetchSize );
        }
        return daoContext;
    }

    public static BiFunction<PreparedStatement, DAOContext, PreparedStatement> newHelperWithQueryTimeout( Integer queryTimeoutSeconds ) {
        return (ps, daoContext) -> {
            Integer resolvedQueryTimeoutSeconds = resolve( daoContext, ATT_DAO_CONTEXT_QUERY_TIMEOUT, queryTimeoutSeconds );
            SafeFunction.applyIfNotNull( resolvedQueryTimeoutSeconds,
                    () -> ps.setQueryTimeout( resolvedQueryTimeoutSeconds.intValue() ) );
            return ps;
        };
    }

    public static BiFunction<PreparedStatement, DAOContext, PreparedStatement> newHelperWithFetchSize( Integer fetchSize ) {
        return (ps, daoContext) -> {
            Integer resolvedFetchSize = resolve( daoContext, ATT_DAO_CONTEXT_FETCH_SIZE, fetchSize );
            SafeFunction.applyIfNotNull( resolvedFetchSize,
                    () -> ps.setFetchSize( resolvedFetchSize.intValue() ) );
            return ps;
        };
    }

    public static BiFunction<PreparedStatement, DAOContext, PreparedStatement> newHelperPipeline(
            BiFunction<PreparedStatement, DAOContext, PreparedStatement>... helpers) {
        return (preparedStatement, daoContext) -> {
            PreparedStatement ps = preparedStatement;
            for ( BiFunction<PreparedStatement, DAOContext, PreparedStatement> helper : helpers ) {
                ps = helper.apply(ps, daoContext);
            }
            return ps;
        };
    }
    public static BiFunction<PreparedStatement, DAOContext, PreparedStatement> hewHelperSafeSilent(
            BiFunction<PreparedStatement, DAOContext, PreparedStatement> helper) {
        return (ps, daoContext) -> {
            SimpleValue<PreparedStatement> res = new SimpleValue<>( ps );
            DAORuntimeException.applySilent( () -> res.setValue( helper.apply(ps, daoContext ) ) );
            return res.getValue();
        };
    }

}
