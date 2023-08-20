package org.fugerit.java.core.jvfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.fugerit.java.core.io.helper.HelperIOException;
import org.fugerit.java.core.io.line.LineWriter;

public class JVFSImpl implements JVFS {
    
	private static final Properties BLANK_PROPS = new Properties();
	
	@Override
    public JFile getRoot() {
        return this.getJFile( JFile.SEPARATOR );
    }
    
	@Override
	public JFile getJFile(String absPath) {
        JFile file = null;
        JMountPoint[] table = this.getMountTable();
        boolean found = false;
        for (int k=0; k<table.length && !found; k++) {
            JMountPoint mp = table[k];
            String point = mp.getPoint();
            int index = absPath.indexOf(mp.getPoint());
            if (index==0) {
                file = mp.getMount().getJFile(this, point, absPath.substring(point.length()-JFile.SEPARATOR.length()));
                found = true;
            }
        }
        return file;
    }
	
    public void printMountTable(LineWriter lw) {
        JMountPoint[] table = this.getMountTable();
        for (int k=0; k<table.length; k++) {
        	lw.println( table[k].toString() );
        }
    }
    
    public static JVFSImpl getInstance(JMount root) throws IOException {
        return getInstance(root, BLANK_PROPS);
    }

    public static JVFSImpl getInstance(JMount root, Properties options) throws IOException {
        JVFSImpl jvfs = new JVFSImpl();
        try {
        	jvfs.mount(root, JFile.SEPARATOR, options);	
        } catch (Exception e) {
        	throw HelperIOException.convertEx( e );
        }
        return jvfs;
    }    
  
    public JMountPoint[] getMountTable() {
        return this.getMounts().toArray( new JMountPoint[0] ); 
    }
    
    public void mount(JMount mount, String point) {
        this.mount(mount, point, BLANK_PROPS);
    }
    
    public void mount(JMount mount, String point, Properties options) {
        List<JMountPoint> mountList = this.getMounts();
        JMountPoint mp = new JMountPoint(mount, point, options);
        boolean insert = false;
        for (int k=0; k<mountList.size() && !insert; k++) {
            JMountPoint current = mountList.get(k);
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
    private JVFSImpl() {
        this.mounts = new ArrayList<>();
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
        
    
}

