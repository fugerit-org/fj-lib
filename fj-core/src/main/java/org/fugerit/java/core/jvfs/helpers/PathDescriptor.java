package org.fugerit.java.core.jvfs.helpers;

import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.lang.helpers.StringUtils;

public class PathDescriptor {

	@Override
	public String toString() {
		return "PathDescriptor [parentPath=" + parentPath + ", name=" + name + "]";
	}

	public PathDescriptor(String parentPath, String name) {
		super();
		this.parentPath = parentPath;
		this.name = name;
	}

	private String parentPath;
	
	private String name;

	public String getParentPath() {
		return parentPath;
	}

	public String getName() {
		return name;
	}
	
	public String getPath() {
		String path = this.getName();
		if ( StringUtils.isNotEmpty( this.parentPath ) ) {
			path = this.getParentPath()+JFile.SEPARATOR+this.getName();
		}
		return path;
	}
	
}
