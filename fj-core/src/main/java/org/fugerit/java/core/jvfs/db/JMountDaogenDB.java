package org.fugerit.java.core.jvfs.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.sql.DataSource;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.connect.ConnectionFactoryImpl;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.daogen.BasicDaoResult;
import org.fugerit.java.core.db.daogen.CloseableDAOContext;
import org.fugerit.java.core.db.daogen.CloseableDAOContextSC;
import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JMount;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.db.daogen.EntityDbJvfsFileFacade;
import org.fugerit.java.core.jvfs.db.daogen.JvfsLogicFacade;
import org.fugerit.java.core.jvfs.db.daogen.ModelDbJvfsFile;
import org.fugerit.java.core.jvfs.db.daogen.impl.JvfsDataLogicFacade;
import org.fugerit.java.core.jvfs.helpers.JFileUtils;
import org.fugerit.java.core.jvfs.helpers.PathDescriptor;

public class JMountDaogenDB implements JMount, Serializable {

	private static final long serialVersionUID = -2733658870079838L;

	private JvfsLogicFacade facade = new JvfsDataLogicFacade();
	
	private ConnectionFactory cf;
	
	public JMountDaogenDB(DataSource ds) throws DAOException {
		this( ConnectionFactoryImpl.newInstance( ds ) );
	}
	
	public JMountDaogenDB(ConnectionFactory cf) {
		super();
		this.cf = cf;
	}

	@Override
	public JFile getJFile(JVFS jvfs, String point, String relPath) {
		JFile file = null;
		try ( CloseableDAOContext context = this.newContext() ) {
			EntityDbJvfsFileFacade fileFacade = facade.getEntityDbJvfsFileFacade();
			PathDescriptor descriptor = JFileUtils.pathDescriptor(relPath);
			ModelDbJvfsFile model = fileFacade.loadById( context, descriptor.getName(), descriptor.getParentPath() );
			file = new DaogenJFileDB(relPath, jvfs, model, this);
		} catch (Exception e) {
			throw new ConfigRuntimeException( "Error loading file "+relPath, e );
		}
		return file;
	}
	
	public boolean delete( ModelDbJvfsFile file ) throws IOException {
		boolean ok = false;
		if ( file != null ) {
			try ( CloseableDAOContext context = this.newContext() ) {
				EntityDbJvfsFileFacade fileFacade = facade.getEntityDbJvfsFileFacade();
				BasicDaoResult<ModelDbJvfsFile> res = fileFacade.deleteById( context, file.getFileName(), file.getParentPath() );
				ok = res.isResultOk();
			} catch (Exception e) {
				throw new IOException( "Delete error "+file+"["+e+"]", e );
			}	
		}
		return ok;
	}
	
	public JFile[] listFiles( DaogenJFileDB file ) throws IOException {
		JFile[] list = null;
		//TODO : implements listFiles;
		return list;
	}
	
	public InputStream streamIn( ModelDbJvfsFile file ) throws IOException {
		InputStream is = null;
		if ( file != null ) {
			is = new ByteArrayInputStream( file.getFileContent().getData() );
		}
		return is;
	}
	
	public OutputStream streamOut( DaogenJFileDB file ) throws IOException {
		return new OutputStream() {
			private ByteArrayOutputStream baos = new ByteArrayOutputStream();
			@Override
			public void close() throws IOException {
				super.close();
				try ( CloseableDAOContext context = newContext() ) {
					//TODO : implement output stream
				} catch (Exception e) {
					throw new IOException( "Write error "+file+"["+e+"]", e );
				}
			}
			@Override
			public void write(int b) throws IOException {
				baos.write( b );
			}
		};
	}
	
	public boolean mkdir( DaogenJFileDB file, boolean recurse ) throws IOException {
		boolean created = false;
		//TODO : implement mkdirs
		return created;
	}	
	
	protected CloseableDAOContext newContext() throws DAOException {
		return new CloseableDAOContextSC( this.cf.getConnection() ) ;
	}

}
