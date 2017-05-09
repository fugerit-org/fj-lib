package org.fugerit.java.core.db.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

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
    
    public Field newField(Object value) {
    	Field field = null;
    	if ( value instanceof BlobData ) {
    		field = new BlobDataField( (BlobData)value );
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
    
    public String toString() {
        return this.getClass().getName()+"[type:"+this.value+"]";
    }    
    
    public NullField(int value) {
        this.value = value;
    }
    
    /* (non-Javadoc)
     * @see it.finanze.secin.shared.dao.Field#setField(java.sql.PreparedStatement, int)
     */
    public void setField(PreparedStatement ps, int index) throws SQLException {
        ps.setNull(index, this.value);
    }
    
    private int value;
    
}


class ObjectField extends Field {
    
    public String toString() {
        return this.getClass().getName()+"[value:"+this.value+", class:"+this.value.getClass().getName()+"]";
    }    
    
    public ObjectField(Object value) {
        this.value = value;
    }
    
    /* (non-Javadoc)
     * @see it.finanze.secin.shared.dao.Field#setField(java.sql.PreparedStatement, int)
     */
    public void setField(PreparedStatement ps, int index) throws SQLException {
        ps.setObject(index, this.value);
    }
    
    private Object value;
    
}

class BlobDataField extends Field {
    
    public String toString() {
        return this.getClass().getName()+"[value:"+this.value+"]";
    }   
    
    public BlobDataField(BlobData value) {
        this.value = value;
    }
    
    /* (non-Javadoc)
     * @see it.finanze.secin.shared.dao.Field#setField(java.sql.PreparedStatement, int)
     */
    public void setField(PreparedStatement ps, int index) throws SQLException {
        ps.setBytes(index, this.value.getData());
    }
    
    private BlobData value;
    
}

class StringField extends Field {
    
    public String toString() {
        return this.getClass().getName()+"[value:"+this.value+"]";
    }   
    
    public StringField(String value) {
        this.value = value;
    }
    
    /* (non-Javadoc)
     * @see it.finanze.secin.shared.dao.Field#setField(java.sql.PreparedStatement, int)
     */
    public void setField(PreparedStatement ps, int index) throws SQLException {
        ps.setString(index, this.value);
    }
    
    private String value;
    
}

class LongField extends Field {
    
    public String toString() {
        return this.getClass().getName()+"[value:"+this.value+"]";
    }    
    
    public LongField(long value) {
        this.value = value;
    }
    
    /* (non-Javadoc)
     * @see it.finanze.secin.shared.dao.Field#setField(java.sql.PreparedStatement, int)
     */
    public void setField(PreparedStatement ps, int index) throws SQLException {
        ps.setLong(index, this.value);
    }
    
    private long value;
    
}

class IntField extends Field {
    
    public String toString() {
        return this.getClass().getName()+"[value:"+this.value+"]";
    }
    
    public IntField(int value) {
        this.value = value;
    }
    
    /* (non-Javadoc)
     * @see it.finanze.secin.shared.dao.Field#setField(java.sql.PreparedStatement, int)
     */
    public void setField(PreparedStatement ps, int index) throws SQLException {
        ps.setInt(index, this.value);
    }
    
    private int value;
    
}
