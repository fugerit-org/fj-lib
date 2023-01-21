package org.fugerit.java.core.jvfs.fun;

import java.io.IOException;

import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JFileFun;
import org.fugerit.java.core.lang.helpers.ExHandler;

public class JFileFunSafe extends JFileFunWrapper {

    private ExHandler handler;
    
    public JFileFunSafe(JFileFun fileFun, ExHandler handler) {
        super(fileFun);
        this.handler = handler;
    }
    
    public void handle(JFile file) throws IOException {
        try {
            super.handle(file);
        } catch (IOException ioe) {
            this.handler.error(ioe);
        }
    }
    
}
