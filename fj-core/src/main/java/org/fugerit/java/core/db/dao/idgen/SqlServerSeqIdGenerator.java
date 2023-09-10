package org.fugerit.java.core.db.dao.idgen;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class SqlServerSeqIdGenerator extends BasicSeqIdGenerator {

	private Function<String, String> querySequence;
	
	public static String createSequenceQuery( String seqName) {
		return "SELECT * FROM sys.sequences WHERE name = "+seqName;		// need testing on real server
	}
	
	public SqlServerSeqIdGenerator() {
		this( s -> createSequenceQuery(s) );
	}
	
	public SqlServerSeqIdGenerator( UnaryOperator<String> querySequenceFun) {
		super();
		this.querySequence = querySequenceFun;
	}

	/* (non-Javadoc)
	 * @see org.morozko.java.mod.db.dao.idgen.BasicSeqIdGenerator#createSequenceQuery()
	 */
	@Override
	protected String createSequenceQuery() {
		return querySequence.apply( this.getSequenceName() ); 
	}
	
}
