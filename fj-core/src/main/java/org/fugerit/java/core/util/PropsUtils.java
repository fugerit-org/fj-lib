package org.fugerit.java.core.util;

import org.fugerit.java.core.io.helper.StreamHelper;

import java.io.IOException;
import java.io.InputStream;
public class PropsUtils {

    private PropsUtils() {}

    public static CheckDuplicationProperties findDuplicatedKeys(String path) throws IOException {
        try ( InputStream in = StreamHelper.resolveStream( path ) ) {
            return findDuplicatedKeys( in );
        }
    }

    public static CheckDuplicationProperties findDuplicatedKeysFromXml(String path) throws IOException {
        try ( InputStream in = StreamHelper.resolveStream( path ) ) {
            return findDuplicatedKeysFromXml( in );
        }
    }

    public static CheckDuplicationProperties  findDuplicatedKeys(InputStream in) throws IOException {
        final CheckDuplicationProperties props = new CheckDuplicationProperties();
        props.load( in );
        return props;
    }

    public static CheckDuplicationProperties  findDuplicatedKeysFromXml(InputStream in) throws IOException {
        final CheckDuplicationProperties props = new CheckDuplicationProperties();
        props.loadFromXML( in );
        return props;
    }

}
