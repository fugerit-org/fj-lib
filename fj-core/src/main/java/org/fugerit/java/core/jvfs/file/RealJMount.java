package org.fugerit.java.core.jvfs.file;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JMount;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.JVFSImpl;

public class RealJMount implements JMount , Serializable {

	/*
	 * 
	 */
	private static final long serialVersionUID = 2946128203628261102L;

	public static JVFS createDefaultJVFS() {
		JVFS jvfs = null;
		try {
			jvfs = createJVFS(new File("/"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jvfs;
	}

	public static JVFS createJVFS(File root) throws IOException {
		return JVFSImpl.getInstance(new RealJMount(root));
	}

	public String toString() {
		return this.getClass().getSimpleName() + "[base:" + this.base + "]";
	}

	private File base; // the base path for the JMount

	public RealJMount(File base) {
		this.base = base;
	}

	@Override
	public JFile getJFile(JVFS jvfs, String point, String relPath) {
		return (new RealJFile(point+relPath, jvfs, new File(base, relPath)));
	}

}
