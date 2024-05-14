package org.fugerit.java.core.util;

import lombok.Getter;

import java.io.IOException;
import java.util.*;

/**
 * Properties collecting values which where previously set.
 *
 * Especially useful after a <code>load()</code> or <code>loadFromXml()</code> methods to check duplications.
 *
 * NOTE: this object has some limitations, especially it depends on current implementation of <code>load()</code> and <code>put()</code> methods.
 *
 * since fu-core 8.6.0
 */
public class CheckDuplicationProperties extends Properties {

    private static final long serialVersionUID = 1432652578L;

    @Override
    public synchronized int hashCode() {
        // super class implementation is ok
        return super.hashCode();
    }

    @Override
    public synchronized boolean equals(Object o) {
        // super class implementation is ok - is equals if all contained elements are equals
        return super.equals(o);
    }

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

    @Getter
    private Collection<Map.Entry<String, String>> duplications;

    public CheckDuplicationProperties() {
        this.duplications = new ArrayList<>();
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        if ( this.containsKey( key ) ) {
            duplications.add( new AbstractMap.SimpleEntry( key.toString(), this.getProperty( key.toString() ) ) );
        }
        return super.put( key, value );
    }

}
