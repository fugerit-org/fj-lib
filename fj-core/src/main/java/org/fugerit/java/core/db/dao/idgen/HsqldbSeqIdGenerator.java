package org.fugerit.java.core.db.dao.idgen;

public class HsqldbSeqIdGenerator extends BasicSeqIdGenerator {

	public static String createSequenceQuery( String seqName) {
		return "call NEXT VALUE FOR "+seqName;
	}
	
	/* (non-Javadoc)
	 * @see org.morozko.java.mod.db.dao.idgen.BasicSeqIdGenerator#createSequenceQuery()
	 */
	@Override
	protected String createSequenceQuery() {
		return createSequenceQuery( this.getSequenceName() ); 
	}

}
