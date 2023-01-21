package org.fugerit.java.core.jvfs;

import java.util.Properties;

public class JMountPoint {
    
    private Properties options;		// mount options
    
    private JMount mount;			// mount handler
    
    private String point;			// mount point
	
    public String toString() {
        return this.getMount().toString()+"    "+this.getPoint();
    }
    public JMountPoint(JMount mount, String point, Properties options) {
        this.mount = mount;
        this.point = point;
        this.options = options;
    }

	public JMount getMount() {
		return mount;
	}

	public Properties getOptions() {
		return options;
	}

	public String getPoint() {
		return point;
	}
    
}
