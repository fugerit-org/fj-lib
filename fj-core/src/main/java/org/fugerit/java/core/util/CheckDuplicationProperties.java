package org.fugerit.java.core.util;

import lombok.Getter;

import java.util.*;

/**
 * Properties collecting values which where previously set.
 *
 * Especially useful after a <code>load()</code> or <code>loadFromXml()</code> methods to check duplications.
 *
 * NOTE: this object has some limitations, especially it depends on current implementation of <code>load()</code> and <code>put()</code> methods.
 *
 */
public class CheckDuplicationProperties extends Properties {

    private static final long serialVersionUID = 1432652578L;

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
