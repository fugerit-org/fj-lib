package org.fugerit.java.core.io;

import org.fugerit.java.core.db.daogen.ByteArrayDataHandler;
import org.fugerit.java.core.db.daogen.CharArrayDataHandler;
import org.fugerit.java.core.function.SafeFunction;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ArchiveIO {

    private ArchiveIO() {}

    public static void addEntry(String entryName, ZipOutputStream zos, byte[] data ) {
        SafeFunction.applyIfNotNull( data, () -> {
            ZipEntry entryOp = new ZipEntry( entryName );
            zos.putNextEntry( entryOp );
            zos.write( data );
            zos.flush();
            zos.closeEntry();
        } );
    }

    public static void addEntry(String entryName, ZipOutputStream zos, ByteArrayDataHandler dh ) {
        SafeFunction.applyIfNotNull( dh, () -> addEntry( entryName, zos, dh.getData() ) );
    }

    public static void addEntry(String entryName, ZipOutputStream zos, CharArrayDataHandler dh ) {
        SafeFunction.applyIfNotNull( dh, () -> addEntry( entryName, zos, dh.toBytes() ) );
    }

}
