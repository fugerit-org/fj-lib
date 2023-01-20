package org.fugerit.java.core.jvfs.fun;

import org.fugerit.java.core.jvfs.JFileFun;
import org.fugerit.java.core.lang.helpers.Result;
import org.fugerit.java.core.lang.helpers.ResultExHandler;

public class JFileFunResult extends JFileFunSafe {

    private Result result;
    
    public JFileFunResult(JFileFun fileFun) {
        this(fileFun, new Result());
    }
    
    public JFileFunResult(JFileFun fileFun, Result result) {
        super(fileFun, new ResultExHandler(result));
        this.result = result;
    }

    public Result getResult() {
        return result;
    }
    
    public void reset() {
        this.result.clear();
    }
    
}
