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

import java.io.IOException;
import java.io.Serializable;
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
public class FieldFactory implements Serializable {

    private static final long serialVersionUID = 5728095010620830481L;
    
    public static final FieldFactory DEFAULT = new FieldFactory();

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
			return (new GenericField<String>(value, type));
		} else {
			field = this.nullField( type );
		}
		return field;
	}

	public Field newField(long value, int type) {
		return (new GenericField<Long>(value, type));
	}
    
	public Field newField(int value, int type) {
		return (new GenericField<Integer>(value, type));
	}

	public Field newField(DAOID value) {
		Field field = null;
		if ( value!=null ) {
			field = new GenericField<Long>( value.longValue() );
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
			field = new GenericField<Object>(value);
		} else {
			field = this.nullField( type );
		}
		return field;		
	}

    public Field newField(String value) {
        return new GenericField<String>(value);
    }

    public Field newField(long value) {
        return new GenericField<Long>(value);
    }
    
    public Field newField(int value) {
        return new GenericField<Integer>(value);
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
			field = (new GenericField<Object>(value));
    	}
        return field;
    }
    
    public Field nullField(int type) {
        return (new NullField(type));
    }    

}

class NullField extends Field implements Serializable {
    
    private static final long serialVersionUID = 1353453L;

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

class BlobDataField extends Field implements Serializable {
    
    private static final long serialVersionUID = -3392455269498148472L;

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

class ClobDataField  extends Field implements Serializable {
    
    private static final long serialVersionUID = 100284648506019470L;

	// code added to setup a basic conditional serialization - START
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		// this class is conditionally serializable, depending on contained object
		// special situation can be handleded using this method in future
		out.defaultWriteObject();
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		// this class is conditionally serializable, depending on contained object
		// special situation can be handleded using this method in future
		in.defaultReadObject();
	}
	
	// code added to setup a basic conditional serialization - END
    
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

class GenericField<T> extends Field implements Serializable {

	private static final long serialVersionUID = 2328668486886550398L;

	// code added to setup a basic conditional serialization - START
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		// this class is conditionally serializable, depending on contained object
		// special situation can be handleded using this method in future
		out.defaultWriteObject();
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		// this class is conditionally serializable, depending on contained object
		// special situation can be handleded using this method in future
		in.defaultReadObject();
	}
	
	// code added to setup a basic conditional serialization - END
	
	public GenericField(T value) {
		this( value, null );
	}
	
	public GenericField(T value, Integer type) {
		super();
		this.value = value;
		this.type = type;
	}

	private T value;
	
	private Integer type;
	
    @Override
    public String toString() {
        return this.getClass().getName()+"[value:"+this.value+"type:"+this.type+"]";
    }  
	
	@Override
	public void setField(PreparedStatement ps, int index) throws SQLException {
		if ( value instanceof String ) {
			ps.setString(index, (String)this.value);
		} else if ( value instanceof Long ) {
			ps.setLong(index, (Long)this.value);
		} else if ( value instanceof Integer ) {
			ps.setInt(index, (Integer)this.value);
    	} else if ( this.value instanceof java.sql.Date ) {
    		ps.setDate(index, ((java.sql.Date)this.value));
    	} else if ( this.value instanceof java.sql.Timestamp ) {
    		ps.setTimestamp(index, ((java.sql.Timestamp)this.value));
    	} else if ( this.value instanceof java.util.Date ) {
    		ps.setTimestamp(index, new java.sql.Timestamp( ((java.util.Date)this.value).getTime() ) );  
		} else {
			ps.setObject( index, this.value );
		}
	}
	
	
}