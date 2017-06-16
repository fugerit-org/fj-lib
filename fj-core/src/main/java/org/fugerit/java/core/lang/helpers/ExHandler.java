package org.fugerit.java.core.lang.helpers;

/*
 * <p>Interfaccia per l'handling di eccezioni.</p>
 * 
 * @author  Fugerit
 */
public interface ExHandler {

    /*
     * <p>Gestisce un errore fatale.</p>
     * 
     * @param e l'eccezione
     */
    public void fatal(Exception e);

    /*
     * <p>Gestisce un errore.</p>
     * 
     * @param e l'eccezione
     */
    public void error(Exception e);
    
    /*
     * <p>Gestisce un avvertimento.</p>
     * 
     * @param e l'eccezione
     */
    public void warning(Exception e);
    
}
