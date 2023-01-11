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
 * @(#)JVFS.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.jvfs
 * @creation	: 9-dic-2004 13.58.24
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.jvfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.fugerit.java.core.cfg.ConfigException;
import org.fugerit.java.core.io.line.LineWriter;
import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.util.path.DefaultPathResolver;
import org.fugerit.java.core.util.path.PathResolver;
import org.fugerit.java.core.xml.dom.DOMUtils;
import org.fugerit.java.core.xml.dom.SearchDOM;
import org.w3c.dom.Element;

/*
 * <p>
 *	<jdl:section>
 * 		<jdl:text lang='it'>
 * 		Implementazione predefinita dell' interfaccia JVFS.
 * 		</jdl:text>
 * 		<jdl:text lang='en'>
 * 		Default implementation of JVFS interface.
 * 		</jdl:text>  
 *	</jdl:section>
 * </p>
 *
 * @author Morozko
 *
 */
public class JVFSImpl implements JVFS {
    
	public static final Properties BLANK_PROPS = new Properties();
	
	/*
	 *	<jdl:section>
	 * 		<jdl:text lang='it'>Risolutore di path predefinito.</jdl:text>
	 * 		<jdl:text lang='en'>Default path resolver.</jdl:text>  
	 *	</jdl:section>
	 */	
    public static final PathResolver DEFAULT_PATHRESOLVER = DefaultPathResolver.DEFAULT_RESOLVER;
    
    /*
     * (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JVFS#getRoot()
     */
    public JFile getRoot() {
        return this.getJFile(this.getPathResolver().getRoot());
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return this.getClass().getName()+"[resolver:"+this.resolver+"]";
    }
    
    /*
     * (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JVFS#getPathResolver()
     */
    public PathResolver getPathResolver() {
        return resolver;
    }  
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Stampa una tabelle di mount del JVFS.</jdl:text>
     * 		<jdl:text lang='en'>Print the mount table of the JVFS.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param lw	<jdl:section>
     * 					<jdl:text lang='it'>Un <code>LineWriter</code> dove ridirigere l'output.</jdl:text>
     * 					<jdl:text lang='en'>A <code>LineWriter</code> to redirect output.</jdl:text>  
     *				</jdl:section>
     */
    public void printMountTable(LineWriter lw) {
        JMountPoint[] table = this.getMountTable();
        for (int k=0; k<table.length; k++) {
        	lw.println( table[k].toString() );
        }
    }
    
    /*
     *  (non-Javadoc)
     * @see org.morozko.java.core.jvfs.JVFS#getJFile(java.lang.String)
     */
    public JFile getJFile(String absPath) {
        JFile file = null;
        JMountPoint[] table = this.getMountTable();
        boolean found = false;
        for (int k=0; k<table.length && !found; k++) {
            JMountPoint mp = table[k];
            String point = mp.getPoint();
            int index = absPath.indexOf(mp.getPoint());
            if (index==0) {
                //System.out.println("Using mount "+mp.getMount());
                file = mp.getMount().getJFile(this, point, absPath.substring(point.length()));
                found = true;
            }
        }
        return file;
    }
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Crea una nuova instanza di JVFSImpl.</jdl:text>
     * 		<jdl:text lang='en'>Create a new JVFSImpl instance.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>La struttura del file di configurazione deve essere del tipo :</jdl:text>
     * 		<jdl:text lang='en'>The configuration file structure is : .</jdl:text>  
     *	</jdl:section>
     * </p>
     * 
     * <code>
     * &lt;jvfs-config jvfs-root=&quot;/&quot; jvfs-separator=&quot;/&quot;&gt;
     * <br/>
     * &nbsp; &nbsp;&lt;mount mount-type=&quot;org.morozko.java.core.jvfs.file.RealJMount&quot; mount-point=&quot;&quot; real-base-path=&quot;C:\&quot;/&gt;
     * <br/>
     * &lt;/jvfs-config&gt;
     * </code>
     * 
     * @param doc					<jdl:section>
     * 									<jdl:text lang='it'>La root del documento xml che esegue la configurazione.</jdl:text>
     * 									<jdl:text lang='en'>The root of the xml document for configuring the JVFS.</jdl:text>  
     *								</jdl:section>		
     * @return						<jdl:section>
     * 									<jdl:text lang='it'>Il file system virtuale.</jdl:text>
     * 									<jdl:text lang='en'>The virtual file system.</jdl:text>  
     *								</jdl:section>	
     * @throws ConfigException		<jdl:section>
     * 									<jdl:text lang='it'>Se si verifica qualche problema durante la configurazione.</jdl:text>
     * 									<jdl:text lang='en'>If something goes wrong during configuration.</jdl:text>  
     *								</jdl:section>
     */
    public static JVFSImpl getInstance( Element doc ) throws ConfigException {
    	JVFSImpl jvfs = null;
    	try {
    		SearchDOM searchDOM = SearchDOM.newInstance( true , true );
        	Element rootTag = searchDOM.findTag( doc , "jvfs-config" );
        	Properties mainProps = DOMUtils.attributesToProperties( rootTag );
        	// path resolver
			PathResolver pathResolver = DefaultPathResolver.getInstance( 
										mainProps.getProperty( "jvfs-root" , DefaultPathResolver.DEF_ROOT ), 
										mainProps.getProperty( "jvfs-separator", DefaultPathResolver.DEF_SEPARATOR  ), 
										"true".equalsIgnoreCase( mainProps.getProperty( "jvfs-ignorecase" ) ) );
			jvfs = new JVFSImpl( pathResolver );
			// elenco dei mount
			List<Element> mountTagList = searchDOM.findAllTags( rootTag, "mount" );
			Iterator<Element> mountIt = mountTagList.iterator();
			while ( mountIt.hasNext() ) {
				Element mountTag = (Element)mountIt.next();
				createMount( mountTag , jvfs );
			}
		} catch (Exception e) {
			throw ( new ConfigException( e ) );
		}
    	return jvfs;
    }
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Crea una nuova instanza di JVFSImpl.</jdl:text>
     * 		<jdl:text lang='en'>Create a new JVFSImpl instance.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param root		<jdl:section>
     * 						<jdl:text lang='it'>Mount radice.</jdl:text>
     * 						<jdl:text lang='en'>Root mount.</jdl:text>  
     *					</jdl:section>	
     * @return						<jdl:section>
     * 									<jdl:text lang='it'>Il file system virtuale.</jdl:text>
     * 									<jdl:text lang='en'>The virtual file system.</jdl:text>  
     *								</jdl:section>	
     * @throws IOException			<jdl:section>
     * 									<jdl:text lang='it'>Se si verifica qualche problema durante la configurazione.</jdl:text>
     * 									<jdl:text lang='en'>If something goes wrong during configuration.</jdl:text>  
     *								</jdl:section>
     */    
    public static JVFSImpl getInstance(JMount root) throws IOException {
        return getInstance(root, BLANK_PROPS);
    }
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Crea una nuova instanza di JVFSImpl.</jdl:text>
     * 		<jdl:text lang='en'>Create a new JVFSImpl instance.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param root		<jdl:section>
     * 						<jdl:text lang='it'>Mount radice.</jdl:text>
     * 						<jdl:text lang='en'>Root mount.</jdl:text>  
     *					</jdl:section>	
     * @param options	<jdl:section>
     * 						<jdl:text lang='it'>Opzioni di mount.</jdl:text>
     * 						<jdl:text lang='en'>Mount options.</jdl:text>  
     *					</jdl:section>	
     * @return						<jdl:section>
     * 									<jdl:text lang='it'>Il file system virtuale.</jdl:text>
     * 									<jdl:text lang='en'>The virtual file system.</jdl:text>  
     *								</jdl:section>	
     * @throws IOException			<jdl:section>
     * 									<jdl:text lang='it'>Se si verifica qualche problema durante la configurazione.</jdl:text>
     * 									<jdl:text lang='en'>If something goes wrong during configuration.</jdl:text>  
     *								</jdl:section>
     */    
    public static JVFSImpl getInstance(JMount root, Properties options) throws IOException {
        return getInstance(root, options, DEFAULT_PATHRESOLVER);
    }

    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Crea una nuova instanza di JVFSImpl.</jdl:text>
     * 		<jdl:text lang='en'>Create a new JVFSImpl instance.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param root		<jdl:section>
     * 						<jdl:text lang='it'>Mount radice.</jdl:text>
     * 						<jdl:text lang='en'>Root mount.</jdl:text>  
     *					</jdl:section>	
     * @param options	<jdl:section>
     * 						<jdl:text lang='it'>Opzioni di mount.</jdl:text>
     * 						<jdl:text lang='en'>Mount options.</jdl:text>  
     *					</jdl:section>	
     * @param resolver	<jdl:section>
     * 						<jdl:text lang='it'>Risolutore di path.</jdl:text>
     * 						<jdl:text lang='en'>Path resolver.</jdl:text>  
     *					</jdl:section>	
     * @return						<jdl:section>
     * 									<jdl:text lang='it'>Il file system virtuale.</jdl:text>
     * 									<jdl:text lang='en'>The virtual file system.</jdl:text>  
     *								</jdl:section>	
     * @throws IOException			<jdl:section>
     * 									<jdl:text lang='it'>Se si verifica qualche problema durante la configurazione.</jdl:text>
     * 									<jdl:text lang='en'>If something goes wrong during configuration.</jdl:text>  
     *								</jdl:section>
     */
    public static JVFSImpl getInstance(JMount root, Properties options, PathResolver resolver) throws IOException {
        JVFSImpl jvfs = new JVFSImpl(resolver);
        jvfs.mount(root, resolver.getRoot(), options);
        return jvfs;
    }    
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Crea una nuova instanza di JVFSImpl.</jdl:text>
     * 		<jdl:text lang='en'>Create a new JVFSImpl instance.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param root		<jdl:section>
     * 						<jdl:text lang='it'>Mount radice.</jdl:text>
     * 						<jdl:text lang='en'>Root mount.</jdl:text>  
     *					</jdl:section>	
     * @param resolver	<jdl:section>
     * 						<jdl:text lang='it'>Risolutore di path.</jdl:text>
     * 						<jdl:text lang='en'>Path resolver.</jdl:text>  
     *					</jdl:section>	
     * @return						<jdl:section>
     * 									<jdl:text lang='it'>Il file system virtuale.</jdl:text>
     * 									<jdl:text lang='en'>The virtual file system.</jdl:text>  
     *								</jdl:section>	
     * @throws IOException			<jdl:section>
     * 									<jdl:text lang='it'>Se si verifica qualche problema durante la configurazione.</jdl:text>
     * 									<jdl:text lang='en'>If something goes wrong during configuration.</jdl:text>  
     *								</jdl:section>
     */    
    public static JVFSImpl getInstance(JMount root,  PathResolver resolver) throws IOException {
        return getInstance(root, BLANK_PROPS, resolver);
    }
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Restituisce l'elenco dei punti di mount.</jdl:text>
     * 		<jdl:text lang='en'>Returns the mount point list.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @return	<jdl:section>
     * 				<jdl:text lang='it'>L'elenco dei punti di mount.</jdl:text>
     * 				<jdl:text lang='en'>The mount point list.</jdl:text>  
     *			</jdl:section>
     */
    public JMountPoint[] getMountTable() {
        return this.getMounts().toArray( new JMountPoint[0] ); 
    }
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Aggiunge un punto di mount all' elenco dei mount.</jdl:text>
     * 		<jdl:text lang='en'>Add a mount point to the mount list.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param mount		<jdl:section>
     * 						<jdl:text lang='it'>Il gestore di mount.</jdl:text>
     * 						<jdl:text lang='en'>The mount handler.</jdl:text>  
     *					</jdl:section>	
     * @param point		<jdl:section>
     * 						<jdl:text lang='it'>Il punto di mount.</jdl:text>
     * 						<jdl:text lang='en'>The mount point.</jdl:text>  
     *					</jdl:section>	
     */    
    public void mount(JMount mount, String point) {
        this.mount(mount, point, BLANK_PROPS);
    }
    
    /*
     * <p> 
     *	<jdl:section>
     * 		<jdl:text lang='it'>Aggiunge un punto di mount all' elenco dei mount.</jdl:text>
     * 		<jdl:text lang='en'>Add a mount point to the mount list.</jdl:text>  
     *	</jdl:section>
     * </p> 
     * 
     * @param mount		<jdl:section>
     * 						<jdl:text lang='it'>Il gestore di mount.</jdl:text>
     * 						<jdl:text lang='en'>The mount handler.</jdl:text>  
     *					</jdl:section>	
     * @param point		<jdl:section>
     * 						<jdl:text lang='it'>Il punto di mount.</jdl:text>
     * 						<jdl:text lang='en'>The mount point.</jdl:text>  
     *					</jdl:section>	
     * @param options	<jdl:section>
     * 						<jdl:text lang='it'>Opzioni di mount.</jdl:text>
     * 						<jdl:text lang='en'>Mount options.</jdl:text>  
     *					</jdl:section>	
     */
    public void mount(JMount mount, String point, Properties options) {
        List<JMountPoint> mountList = this.getMounts();
        JMountPoint mp = new JMountPoint(mount, point, options);
        boolean insert = false;
        for (int k=0; k<mountList.size() && !insert; k++) {
            JMountPoint current = (JMountPoint)mountList.get(k);
            if (current.getPoint().indexOf(point)!=0) {
                mountList.add(k, mp);
                insert = true;
            }
        }
        if (!insert)
            mountList.add(mp);
        this.setMounts(mountList);
    }
    
    // private constructor
    private JVFSImpl(PathResolver resolver) {
        this.mounts = new ArrayList<>();
        this.resolver = resolver;
    }
    
    // getter method for mount point list
    private synchronized List<JMountPoint> getMounts() {
        return this.mounts;
    }
    
    // setter method for mount point list
    private synchronized void setMounts(List<JMountPoint> v) {
        this.mounts = v;
    }
    
    private List<JMountPoint> mounts;			// mount point list
    
    private PathResolver resolver;	// the path resolver
    
    // creats a new mount point based upon an xml configuration
    private static void createMount( Element tag, JVFSImpl jvfs ) throws Exception {
    	Properties props = DOMUtils.attributesToProperties( tag );
    	String type = props.getProperty( "mount-type" );
    	String point = props.getProperty( "mount-point" );
    	JMount mount = (JMount)ClassHelper.newInstance( type );
    	mount.configure( props );
    	jvfs.mount( mount, point );
    }    
    
}

