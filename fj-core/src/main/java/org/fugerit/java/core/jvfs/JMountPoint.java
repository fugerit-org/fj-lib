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
 * @(#)JMountPoint.java
 *
 * @project     : org.morozko.java.core
 * @package     : org.morozko.java.core.jvfs
 * @creation	: 9-dic-2004 14.10.26
 * @release		: xxxx.xx.xx
 */
package org.fugerit.java.core.jvfs;

import java.util.Properties;

/*
 * <p>
 *	<jdl:section>
 * 		<jdl:text lang='it'>
 * 		Classe che mangiene le informazioni necessarie alla gestione
 * 		di un punto di mount.
 * 		</jdl:text>
 * 		<jdl:text lang='en'>
 * 		Class helding the necessary information for handling
 * 		a mount point.
 * 		</jdl:text>  
 *	</jdl:section>
 * </p>
 *
 * @author Morozko
 *
 */
public class JMountPoint {
    
    private Properties options;		// mount options
    
    private JMount mount;			// mount handler
    
    private String point;			// mount point
	
    public String toString() {
        return this.getMount().toString()+"    "+this.getPoint();
    }
    
    /*
     * <jdl:section>
     * 		<jdl:text lang='it'><p>Crea una nuova istanza di JMountPoint.</p></jdl:text>
     * 		<jdl:text lang='en'><p>Creates a new instance of JMountPoint.</p></jdl:text>
     * </jdl:section>
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
    public JMountPoint(JMount mount, String point, Properties options) {
        this.mount = mount;
        this.point = point;
        this.options = options;
    }

	/*
	 * <p>
	 *  <jdl:section>
	 * 		<jdl:text lang='it'>Restituisce il valore del campo mount.</jdl:text>
	 * 		<jdl:text lang='en'>Returns the value of mount.</jdl:text>  
	 *  </jdl:section>
	 * </p>
	 *
	 * @return <jdl:section>
	 *         		<jdl:text lang='it'>il valore del campo mount.</jdl:text>
	 *         		<jdl:text lang='en'>the value of mount.</jdl:text> 
	 * 		   </jdl:section>
	 */
	public JMount getMount() {
		return mount;
	}

	/*
	 * <p>
	 *  <jdl:section>
	 * 		<jdl:text lang='it'>Restituisce il valore del campo options.</jdl:text>
	 * 		<jdl:text lang='en'>Returns the value of options.</jdl:text>  
	 *  </jdl:section>
	 * </p>
	 *
	 * @return <jdl:section>
	 *         		<jdl:text lang='it'>il valore del campo options.</jdl:text>
	 *         		<jdl:text lang='en'>the value of options.</jdl:text> 
	 * 		   </jdl:section>
	 */
	public Properties getOptions() {
		return options;
	}

	/*
	 * <p>
	 *  <jdl:section>
	 * 		<jdl:text lang='it'>Restituisce il valore del campo point.</jdl:text>
	 * 		<jdl:text lang='en'>Returns the value of point.</jdl:text>  
	 *  </jdl:section>
	 * </p>
	 *
	 * @return <jdl:section>
	 *         		<jdl:text lang='it'>il valore del campo point.</jdl:text>
	 *         		<jdl:text lang='en'>the value of point.</jdl:text> 
	 * 		   </jdl:section>
	 */
	public String getPoint() {
		return point;
	}
    

}
