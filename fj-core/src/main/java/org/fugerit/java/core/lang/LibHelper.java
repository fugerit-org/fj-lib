package org.fugerit.java.core.lang;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.cfg.ConfigRuntimeException;

import java.io.File;
import java.io.FilenameFilter;

@Slf4j
public class LibHelper {

    private LibHelper() {}

    private static final String LLP = "java.library.path";

    public static int ll(String name ) {
        return ll( name, 1 );
    }

    public static int ll(String name, int checkCount ) {
        return ll( name, (f, n) -> n.contains(name), checkCount );
    }

    public static int ll(String name, FilenameFilter ffn, int checkCount ) {
        String libPath = System.getProperty( LLP );
        log.info( "loading library path [{}]", libPath );
        int count = 0;
        if ( libPath != null ) {
            String[] listPath = libPath.split(File.pathSeparator );
            for ( int k=0; k<listPath.length; k++ ) {
                File folder = new File( listPath[k] );
                if ( folder.isDirectory() ) {
                    count+=folder.list(ffn).length;
                }
            }
        }
        log.info( "name : {}, checkCount : {}, count : {} ", name, checkCount, count );
        if ( count > checkCount ) {
            throw new ConfigRuntimeException( String.format( "%s is too many library paths : %s", name, count ) );
        } else if ( count > 1 ) {
            log.info( "loading library [{}]", name );
            System.loadLibrary( name );
        } else {
            log.info( "skip loading library [{}]", name );
        }
        return count;
    }

}
