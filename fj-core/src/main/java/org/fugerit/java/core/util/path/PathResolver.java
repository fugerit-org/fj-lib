/****************************************************************
<copyright>
	Morozko Java Library org.morozko.java.core 

	Copyright (c) 2006 Morozko

	All rights reserved. This program and the accompanying materials
	are made available under the terms of the Apache License v2.0
	which accompanies this distribution, and is available at
	http://www.apache.org/licenses/
	(txt version : http://www.apache.org/licenses/LICENSE-2.0.txt
	html version : http://www.apache.org/licenses/LICENSE-2.0.html)

   This product includes software developed at
   The Apache Software Foundation (http://www.apache.org/).
</copyright>
*****************************************************************/
/*
 * @(#)PathResolver.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.util.path
 * @creation	: 12-dic-2004 15.26.43
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.util.path;

/*
 * <p>Risolutore di percorsi all'interno di un albero n-nario.</p>
 * 
 * <p>Un altero pu� avere una struttura arbitraria, una istanza di PathResolver
 * server ad astrarre un percorso verso un nodo dell' albero, cos� da poterlo linearizzare.</p>
 * 
 * <p>Usi tipici di PathResolver possono essere la risoluzione di Path in un FileSystem o in
 * un documento XML.</p>
 * 
 * @author  Matteo Franci a.k.a. TUX2
 */
public interface PathResolver {

    /*
     * <p>Restituisce la percorso base.</p>
     * 
     * @return  il percorso base
     */
    public String getRoot();
    
    /*
     * <p>Restituisce il separatore di percorso.</p>
     * 
     * @return  il separatore di percorso
     */
    public String getSepartor();
    
    /*
     * <p>Restituisce il percorso del genitore del percorso dato.</p>
     * 
     * @param path  il percorso di cui trovare il genitore
     * @return      il genitore del percorso o
     *               <code>null</code> se path � la radice.
     */
    public String getParent(String path);
    
    /*
     * <p>Restituisce il nome del nodo del percorso.</p>
     * 
     * @param path  il  percorso di cui trovare il nome
     * @return      il nome del nodo (il nome della radice � la stringa vuota).
     */
    public String getName(String path);
    
    /*
     * <p>Restituisce il percorso del figlio di un percorso.</p>
     * 
     * @param parent    il percorso del genitore
     * @param name      il nome
     * @return          il percorso al figlio
     */
    public String getChild(String parent, String name);
    
    /*
     * <p>Verfifica la validit� di un percorso all'interno del PathResolver.</p>
     * 
     * @param path  il percorso da validare
     * @return      <code>true</code> se il percorso � valido
     *               <code>false</code> altrimenti
     */
    public boolean validatePath(String path);
    
    /*
     * <p>Indica se il PathResolver fa differenza tra maiuscole e minuscole</p>
     * 
     * @return      <code>true</code> se il percorso non � case sensitive
     *               <code>false</code> altrimenti
     */
    public boolean isIgnoreCase();
    

    /*
     * <p>Verifica se un percorso � la radice del PathResolver.</p>
     * 
     * @param root  il percorso da verificare
     * @return      <code>true</code> se il percorso � la radice
     *               <code>false</code> altrimenti
     */
    public boolean isRoot(String root);
    
    /*
     * <p>Verifica se due percorsi sono uguali per il PathResolver.</p>
     * 
     * @param p1    il primo percorso da confrontare
     * @param p2    il secondo percorso da confrontare
     * @return      <code>true</code> se i due percorso sono uguali per il PathResolver
     *               <code>false</code> altrimenti
     */
    public boolean equalPath(String p1, String p2);
    
}
