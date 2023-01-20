package org.fugerit.java.core.jvfs.fun;

import java.io.IOException;

import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JFileFun;

public class JFileFunWrapper implements JFileFun {

    public JFileFunWrapper(JFileFun fileFun) {
        this.wrappedFileFun = fileFun;
    }
    
    private JFileFun wrappedFileFun;
    
    public void handle(JFile file) throws IOException {
        this.wrappedFileFun.handle(file);
    }

}
