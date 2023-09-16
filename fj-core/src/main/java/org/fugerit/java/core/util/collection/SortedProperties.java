package org.fugerit.java.core.util.collection;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

/**
 * 
 * Instance of java.util.Properties keeping the insert order of the keys.
 * 
 * Status : experimental
 * 
 * @author Matteo a.k.a. Fugerit
 *
 */
public class SortedProperties extends Properties {

    private static final long serialVersionUID = -5401542664800054702L;
  
	@Override
	public synchronized int hashCode() {
		// super class implementation is ok
		return super.hashCode();
	}

	@Override
	public synchronized boolean equals(Object o) {
		// super class implementation is ok
		return super.equals(o);
	}
    
	private HashSet<String> sortedKeys;

    public SortedProperties() {
        this.sortedKeys = new LinkedHashSet<>();
    }

    @Override
    public Set<Object> keySet() {
        return Collections.unmodifiableSet( this.sortedKeys );
    }

    @Override
    public Enumeration<Object> keys() {
    	return new Enumeration<Object>() {
    		private Iterator<String> it = sortedKeys.iterator();
			@Override
			public Object nextElement() {
				return it.next();
			}
			
			@Override
			public boolean hasMoreElements() {
				return it.hasNext();
			}
		};
    }

    @Override
    public synchronized String toString() {
        return super.toString();
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        this.sortedKeys.add( (String)key );
        return super.put(key, value);
    }
	
}
