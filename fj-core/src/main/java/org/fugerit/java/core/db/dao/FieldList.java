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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.fugerit.java.core.db.daogen.ByteArrayDataHandler;
import org.fugerit.java.core.db.daogen.CharArrayDataHandler;
import org.fugerit.java.core.db.helpers.BlobData;
import org.fugerit.java.core.db.helpers.DAOID;

/**
 * 
 *
 * @author Fugerit
 *
 */
public class FieldList implements Serializable {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 2575285768771302903L;

	private FieldFactory fieldFactory;
	
    private List<Field> list;
    
    public Field getField(int index) {
        return (Field)this.list.get(index);
    }
    
    public int size() {
        return this.list.size();
    }
    
    public void addField(Field f) {
        this.list.add(f);
    }

    public FieldList() {
        this( new FieldFactory() );
    }

    public FieldList( FieldFactory fieldFactory ) {
        super();
        this.list = new ArrayList<Field>();
        this.fieldFactory = fieldFactory;
    }

    public FieldList(FieldFactory fieldFactory, Field f) {
        this( fieldFactory );
        this.addField(f);
    }

	public void addField(DAOID value) {
		this.addField( value.longValue() );
	}

	public Field newField(int value, int type) {
		return fieldFactory.newField(value, type);
	}

	public void addField(int value) {
		this.addField( fieldFactory.newField(value) );
	}

	public void addField(long value, int type) {
		this.addField( fieldFactory.newField(value, type) );
	}

	public void addField(long value) {
		this.addField( fieldFactory.newField(value) );
	}

	public void addField(Object value, int type) {
		this.addField( fieldFactory.newField(value, type) );
	}

	public void addField(Object value) {
		this.addField( fieldFactory.newField(value) );
	}

	public void addField(String value, int type) {
		this.addField( fieldFactory.newField(value, type) );
	}

	public void addField(String value) {
		this.addField( fieldFactory.newField(value) );
	}

	public void addNullField(int type) {
		this.addField( fieldFactory.nullField(type) );
	}    
	
	public void addField( ByteArrayDataHandler value) {
		this.addField( fieldFactory.newField( BlobData.valueOf( value) ) );
	}

	public void addField( CharArrayDataHandler value) {
		this.addField( fieldFactory.newField( (CharArrayDataHandler)value ) );
	}

	public static FieldList newFieldList() {
		return new FieldList();
	}
	
	public static FieldList newFieldList( Field... fields ) {
		FieldList fl = newFieldList();
		if ( fields != null ) {
			for ( Field f : fields ) {
				fl.addField( f );
			}
		}
		return fl;
	}

	public static FieldList newFieldList( Object... fields ) {
		FieldList fl = newFieldList();
		if ( fields != null ) {
			for ( Object f : fields ) {
				fl.addField( f );
			}
		}
		return fl;
	}

}
