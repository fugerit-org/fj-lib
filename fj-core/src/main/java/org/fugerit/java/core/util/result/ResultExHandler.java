package org.fugerit.java.core.util.result;

import org.fugerit.java.core.lang.helpers.ExHandler;
import org.fugerit.java.core.lang.helpers.Result;

/**
 * 
 * 
 * @author  Fugerit
 */
public class ResultExHandler implements ExHandler {

    private Result result;
    
    /*
     * <p>Restituisce il valore di result.</p>
     * 
     * @return il valore di result.
     */
    public Result getResult() {
        return result;
    }
    
    /*
     * <p>Imposta result.</p>
     * 
     * @param result il result da impostare.
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /*
     * <p>Crea un nuovo ResultExHandler</p>
     * 
     * 
     */
    public ResultExHandler() {
        this(new Result());
    }    
    
    /*
     * <p>Crea un nuovo ResultExHandler</p>
     * 
     * 
     */
    public ResultExHandler(Result result) {
        super();
        this.result = result;
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.lang.ExHandler#fatal(java.lang.Exception)
     */
    @Override
    public void fatal(Exception e) {
        result.putFatal(e);
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.lang.ExHandler#error(java.lang.Exception)
     */
    @Override
    public void error(Exception e) {
        result.putError(e);
    }

    /* (non-Javadoc)
     * @see org.opinf.jlib.std.lang.ExHandler#warning(java.lang.Exception)
     */
    @Override
    public void warning(Exception e) {
        result.putWarning(e);
    }

}
