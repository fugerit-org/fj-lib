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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.fugerit.java.core.db.daogen.ByteArrayDataHandler;
import org.fugerit.java.core.db.daogen.CharArrayDataHandler;
import org.fugerit.java.core.db.helpers.BlobData;
import org.fugerit.java.core.db.helpers.DAOID;

/*
 * <p>.</p>
 *
 * Fugerit
 */
public class FieldFactory {

    /*
     * <p>Crea una nuova istanza di FieldFactory.</p>
     *
     * 
     */
    public FieldFactory() {
        super();
    }

	public Field newField(String value, int type) {
		Field field = null;
		if ( value!=null ) {
			field = (new StringField(value));
		} else {
			field = this.nullField( type );
		}
		return field;
	}

	public Field newField(long value, int type) {
		return (new LongField(value));
	}
    
	public Field newField(int value, int type) {
		return (new IntField(value));
	}

	public Field newField(DAOID value) {
		Field field = null;
		if ( value!=null ) {
			field = this.newField( value.longValue() );
		} else {
			field = this.nullField( Types.BIGINT );
		}
		return field;		
	}
    
	public Field newField(Object value, int type) {
		Field field = null;
		if ( value instanceof BlobData ) {
			field = this.newField( (BlobData)value );
		} else if (value instanceof DAOID) {
			field = this.newField( (DAOID)value );
		} else if ( value!=null ) {
			field = (new ObjectField(value));
		} else {
			field = this.nullField( type );
		}
		return field;		
	}

    public Field newField(String value) {
        return (new StringField(value));
    }

    public Field newField(long value) {
        return (new LongField(value));
    }
    
    public Field newField(int value) {
        return (new IntField(value));
    }
    
    public Field newField(ByteArrayDataHandler value) {
        return new BlobDataField( BlobData.valueOf( (ByteArrayDataHandler)value ) );
    }
    
    public Field newField(CharArrayDataHandler value) {
        return new ClobDataField( value );
    }
    
    public Field newField(Object value) {
    	Field field = null;
    	if ( value instanceof BlobData ) {
    		field = new BlobDataField( (BlobData)value );
    	} else if( value instanceof CharArrayDataHandler ) {
    		field = newField( ((CharArrayDataHandler)value) ); 
    	} else if( value instanceof ByteArrayDataHandler ) {
        	field = newField( ((ByteArrayDataHandler)value) );    		
    	} else if (value instanceof DAOID) {
    		field = this.newField( (DAOID)value );
    	} else {
			field = (new ObjectField(value));
    	}
        return field;
    }
    
    public Field nullField(int type) {
        return (new NullField(type));
    }    
    
}

////////////////////////////////////////////////////////////////
//        Classi implementano i Field di vari tipo            //
////////////////////////////////////////////////////////////////

class NullField extends Field {
    
    @Override
    public String toString() {
        return this.getClass().getName()+"[type:"+this.value+"]";
    }    
    
    public NullField(int value) {
        this.value = value;
    }
    
    /* (non-Javadoc)
     * @see it.finanze.secin.shared.dao.Field#setField(java.sql.PreparedStatement, int)
     */
    @Override
    public void setField(PreparedStatement ps, int index) throws SQLException {
        ps.setNull(index, this.value);
    }
    
    private int value;
    
}


class ObjectField extends Field {
    
    @Override
    public String toString() {
        return this.getClass().getName()+"[value:"+this.value+", class:"+this.value.getClass().getName()+"]";
    }    
    
    public ObjectField(Object value) {
        this.value = value;
    }
    
    /* (non-Javadoc)
     * @see it.finanze.secin.shared.dao.Field#setField(java.sql.PreparedStatement, int)
     */
    @Override
    public void setField(PreparedStatement ps, int index) throws SQLException {
    	if ( this.value == null ) {
    		ps.setObject(index, this.value);	
    	} else if ( this.value instanceof java.sql.Date ) {
    		ps.setDate(index, ((java.sql.Date)this.value));
    	} else if ( this.value instanceof java.sql.Timestamp ) {
    		ps.setTimestamp(index, ((java.sql.Timestamp)this.value));
    	} else if ( this.value instanceof java.util.Date ) {
    		ps.setTimestamp(index, new java.sql.Timestamp( ((java.util.Date)this.value).getTime() ) );    		
    	} else {
    		ps.setObject(index, this.value);
    	}
    }
    
    private Object value;
    
}

class BlobDataField extends Field {
    
    @Override
    public String toString() {
        return this.getClass().getName()+"[value:skipped for blob]";
    }   
    
    public BlobDataField(BlobData value) {
        this.value = value;
    }
    
    /* (non-Javadoc)
     * @see it.finanze.secin.shared.dao.Field#setField(java.sql.PreparedStatement, int)
     */
    @Override
    public void setField(PreparedStatement ps, int index) throws SQLException {
        ps.setBytes(index, this.value.getData());
    }
    
    private BlobData value;
    
}

class ClobDataField extends Field {
    
    @Override
    public String toString() {
        return this.getClass().getName()+"[value:skipped for clob]";
    }   
    
    public ClobDataField( CharArrayDataHandler value ) {
        this.value = value;
    }
    
    /* (non-Javadoc)
     * @see it.finanze.secin.shared.dao.Field#setField(java.sql.PreparedStatement, int)
     */
    @Override
    public void setField(PreparedStatement ps, int index) throws SQLException {
        ps.setCharacterStream(index, this.value.toReader() );
    }
    
    private CharArrayDataHandler value;
    
}

class StringField extends Field {
    
    @Override
    public String toString() {
        return this.getClass().getName()+"[value:"+this.value+"]";
    }   
    
    public StringField(String value) {
        this.value = value;
    }
    
    /* (non-Javadoc)
     * @see it.finanze.secin.shared.dao.Field#setField(java.sql.PreparedStatement, int)
     */
    @Override
    public void setField(PreparedStatement ps, int index) throws SQLException {
        ps.setString(index, this.value);
    }
    
    private String value;
    
}

class LongField extends Field {
    
    @Override
    public String toString() {
        return this.getClass().getName()+"[value:"+this.value+"]";
    }    
    
    public LongField(long value) {
        this.value = value;
    }
    
    /* (non-Javadoc)
     * @see it.finanze.secin.shared.dao.Field#setField(java.sql.PreparedStatement, int)
     */
    @Override
    public void setField(PreparedStatement ps, int index) throws SQLException {
        ps.setLong(index, this.value);
    }
    
    private long value;
    
}

class IntField extends Field {
    
    @Override
    public String toString() {
        return this.getClass().getName()+"[value:"+this.value+"]";
    }
    
    public IntField(int value) {
        this.value = value;
    }
    
    /* (non-Javadoc)
     * @see it.finanze.secin.shared.dao.Field#setField(java.sql.PreparedStatement, int)
     */
    @Override
    public void setField(PreparedStatement ps, int index) throws SQLException {
        ps.setInt(index, this.value);
    }
    
    private int value;
    
}
