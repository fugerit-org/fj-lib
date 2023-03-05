package org.fugerit.java.core.util.collection;

import java.util.Collections;
import java.util.Enumeration;
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
    
	private Set<Object> sortedKeys;

    public SortedProperties() {
        this.sortedKeys = new LinkedHashSet<>();
    }

    @Override
    public Set<Object> keySet() {
        return Collections.unmodifiableSet( this.sortedKeys );
    }

    @Override
    public Enumeration<Object> keys() {
    	return Collections.enumeration( this.sortedKeys );
    }

    @Override
    public synchronized String toString() {
        return super.toString();
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        this.sortedKeys.add( key );
        return super.put(key, value);
    }
	
}
