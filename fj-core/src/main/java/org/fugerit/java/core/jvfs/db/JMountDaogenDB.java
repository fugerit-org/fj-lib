package org.fugerit.java.core.jvfs.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.db.connect.ConnectionFactory;
import org.fugerit.java.core.db.connect.ConnectionFactoryImpl;
import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.db.daogen.BasicDaoResult;
import org.fugerit.java.core.db.daogen.ByteArrayDataHandler;
import org.fugerit.java.core.db.daogen.CloseableDAOContext;
import org.fugerit.java.core.db.daogen.CloseableDAOContextSC;
import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JMount;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.db.daogen.DbJvfsFileFinder;
import org.fugerit.java.core.jvfs.db.daogen.EntityDbJvfsFileFacade;
import org.fugerit.java.core.jvfs.db.daogen.JvfsLogicFacade;
import org.fugerit.java.core.jvfs.db.daogen.ModelDbJvfsFile;
import org.fugerit.java.core.jvfs.db.daogen.impl.HelperDbJvfsFile;
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
		try ( CloseableDAOContext context = newContext() ) {
			EntityDbJvfsFileFacade fileFacade = facade.getEntityDbJvfsFileFacade();
			DbJvfsFileFinder finder = new DbJvfsFileFinder();
			finder.setModel( new HelperDbJvfsFile() );
			finder.getModel().setParentPath( file.getPath() );
			BasicDaoResult<ModelDbJvfsFile> res = fileFacade.loadAllByFinder( context , finder );
			if ( res.isResultOk() ) {
				list = res.getList().stream().map( 
						dbf -> new DaogenJFileDB( dbf.getParentPath()+JFile.SEPARATOR+dbf.getFileName(), file.getJVFS(), dbf, this )
					).collect( Collectors.toList() ).toArray( new JFile[0] );
			}
		} catch (Exception e) {
			throw new IOException( "Failed to created directory listing : "+file );
		}
		return list;
	}
	
	public InputStream streamIn( ModelDbJvfsFile file ) throws IOException {
		InputStream is = null;
		if ( file != null ) {
			is = new ByteArrayInputStream( file.getFileContent().getData() );
		}
		return is;
	}
	
	public String getFileProps( DaogenJFileDB file ) throws IOException {
		String props = null;
		if ( !file.isCanWrite() ) {
			props = DaogenJFileDB.FLAG_RO;
		}
		return props;
	}
	
	public OutputStream streamOut( DaogenJFileDB file ) throws IOException {
		return new OutputStream() {
			private ByteArrayOutputStream baos = new ByteArrayOutputStream();
			@Override
			public void close() throws IOException {
				super.close();
				byte[] data = baos.toByteArray();
				this.baos = null;
				if ( !updateOrCreate(file, data) ) {
					throw new IOException( "Failed to save file : "+file );
				}
			}
			@Override
			public void write(int b) throws IOException {
				baos.write( b );
			}
		};
	}
	
	public boolean updateOrCreate( DaogenJFileDB file, byte[] data  ) throws IOException {
		boolean updated = false;
		try ( CloseableDAOContext context = newContext() ) {
			EntityDbJvfsFileFacade fileFacade = facade.getEntityDbJvfsFileFacade();
			PathDescriptor descriptor = JFileUtils.pathDescriptor( file.getPath() );
			ModelDbJvfsFile model = fileFacade.loadById( context, descriptor.getName(), descriptor.getParentPath() );
			boolean create = ( model == null );
			Date currentTime = new Date();
			if ( create ) {
				model = new HelperDbJvfsFile();
				model.setFileName( descriptor.getName() );
				model.setParentPath( descriptor.getParentPath() );
				model.setCreationTime( currentTime );
				file.setDbFile( model );
			}
			model.setFileProps( getFileProps( file ) );
			if ( data != null ) {
				model.setFileSize( new BigDecimal( data.length ) );
				model.setFileContent( ByteArrayDataHandler.newHandlerByte( data ) );
			}
			// create or update?
			BasicDaoResult<ModelDbJvfsFile> updateResult = null;
			if ( create ) {
				updateResult = fileFacade.create(context, model);
			} else {
				updateResult = fileFacade.updateById(context, model);
			}
			updated = updateResult.isResultOk();
			if ( !updated ) {
				throw new DAOException( "Fail:"+updateResult );
			}
		} catch (Exception e) {
			throw new IOException( "Write error "+file+"["+e+"]", e );
		}
		return updated;
	}
	
	public boolean mkdir( DaogenJFileDB file, boolean recurse ) throws IOException {
		boolean created = false;
		if ( !file.isDirectory() ) {
			throw new IOException( "File is not a directory : "+file.getPath() );
		} else if ( file.exists() ) {
			throw new IOException( "Directory already exists : "+file.getPath() );
		} else {
			// parent handling
			DaogenJFileDB parent = (DaogenJFileDB)file.getParent();
			if ( parent != null && !parent.exists() ) {
				if ( !recurse ) {
					throw new IOException( "Parent directory does not exist : "+parent.getParent() );
				} else if ( !this.mkdir(parent, recurse) ) {
					throw new IOException( "Failed to create parent directory : "+parent.getParent() );
				}
			}
			created = this.updateOrCreate(file, null);
			if ( !created ) {
				throw new IOException( "Failed to created directory : "+file.getPath() );
			}
		}
 		return created;
	}	
	
	protected CloseableDAOContext newContext() throws DAOException {
		return new CloseableDAOContextSC( this.cf.getConnection() ) ;
	}

}
