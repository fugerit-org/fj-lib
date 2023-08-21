package org.fugerit.java.core.jvfs.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
import org.fugerit.java.core.db.helpers.TimeHelper;
import org.fugerit.java.core.io.helper.HelperIOException;
import org.fugerit.java.core.jvfs.JFile;
import org.fugerit.java.core.jvfs.JMount;
import org.fugerit.java.core.jvfs.JVFS;
import org.fugerit.java.core.jvfs.JVFSImpl;
import org.fugerit.java.core.jvfs.db.daogen.def.facade.DbJvfsFileFinder;
import org.fugerit.java.core.jvfs.db.daogen.def.facade.EntityDbJvfsFileFacade;
import org.fugerit.java.core.jvfs.db.daogen.facade.impl.JvfsDataLogicFacade;
import org.fugerit.java.core.jvfs.db.daogen.helper.HelperDbJvfsFile;
import org.fugerit.java.core.jvfs.db.daogen.model.ModelDbJvfsFile;
import org.fugerit.java.core.jvfs.db.impl.facade.data.DataEntityDbJvfsFileFacade;
import org.fugerit.java.core.jvfs.helpers.JFileUtils;
import org.fugerit.java.core.jvfs.helpers.PathDescriptor;

public class JMountDaogenDB implements JMount {

	private ConnectionFactory cf;
	
	private EntityDbJvfsFileFacade facade;
	
	private String rootParentPath;
	
	public JMountDaogenDB(DataSource ds, EntityDbJvfsFileFacade facade) throws DAOException {
		this( ConnectionFactoryImpl.newInstance( ds ), facade );
	}
	
	public JMountDaogenDB(ConnectionFactory cf, EntityDbJvfsFileFacade facade) {
		super();
		this.cf = cf;
		this.facade = facade;
		this.rootParentPath = JFile.SEPARATOR+JFile.SEPARATOR;
	}
	
	public static JVFS createJVFSWithPrefix( ConnectionFactory cf, String prefix ) throws IOException {
		return createJVFS( cf, DataEntityDbJvfsFileFacade.newInstanceWithTable(prefix) );
	}
	
	public static JVFS createJVFSWithTableName( ConnectionFactory cf, String tableName ) throws IOException {
		return createJVFS( cf, DataEntityDbJvfsFileFacade.newInstanceWithTable(tableName) );
	}
	
	public static JVFS createJVFS( ConnectionFactory cf, EntityDbJvfsFileFacade facade ) throws IOException {
		return JVFSImpl.getInstance( new JMountDaogenDB(cf , facade ) );
	}
	
	public static JVFS createJVFS( ConnectionFactory cf ) throws IOException {
		try {
			return createJVFS( cf, JvfsDataLogicFacade.getInstance().getEntityDbJvfsFileFacade() );
		} catch (DAOException e) {
			throw HelperIOException.convertEx(e);
		}
	}
	
	public String getParentPath( PathDescriptor descriptor ) {
		String parentPath = descriptor.getParentPath();
		if ( this.rootParentPath != null && JFileUtils.isRoot( descriptor.getPath() ) ) {
			parentPath = this.rootParentPath;
		}
		return parentPath;
	}
	
	public String checkParentPath( String parentPath ) {
		String path = parentPath;
		if ( this.rootParentPath != null && this.rootParentPath.equals( parentPath ) ) {
			path = "";
		}
		return path;
	}
	
	@Override
	public JFile getJFile(JVFS jvfs, String point, String relPath) {
		JFile file = null;
		try ( CloseableDAOContext context = this.newContext() ) {
			PathDescriptor descriptor = JFileUtils.pathDescriptor(relPath);
			ModelDbJvfsFile model = this.facade.loadById( context, descriptor.getName(), this.getParentPath(descriptor) );
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
				BasicDaoResult<ModelDbJvfsFile> res = this.facade.deleteById( context, file.getFileName(), file.getParentPath() );
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
			DbJvfsFileFinder finder = new DbJvfsFileFinder();
			finder.setModel( new HelperDbJvfsFile() );
			finder.getModel().setParentPath( file.getPath() );
			BasicDaoResult<ModelDbJvfsFile> res = this.facade.loadAllByFinder( context , finder );
			if ( res.isResultOk() ) {
				list = res.getList().stream().map( 
						dbf -> new DaogenJFileDB( JFileUtils.createPath( this.checkParentPath( dbf.getParentPath() ), dbf.getFileName() ), file.getJVFS(), dbf, this )
					).collect( Collectors.toList() ).toArray( new JFile[0] );
			} else if ( res.getResultCode() == BasicDaoResult.RESULT_NODATAFOUND ) {
				list = new JFile[0];
			} else {
				throw new DAOException( "Failed to created listing : "+res );
			}
		} catch (Exception e) {
			throw HelperIOException.convertEx( "Failed to created directory listing : "+file, e );
		}
		return list;
	}
	
	public InputStream streamIn( ModelDbJvfsFile file ) throws IOException {
		InputStream is = null;
		try {
			if ( file != null && file.getFileContent() != null ) {
				byte[] data = file.getFileContent().getData();
				if ( data != null ) {
					is = new ByteArrayInputStream( file.getFileContent().getData() );	
				}
			}
		} catch (Exception e) {
			throw HelperIOException.convertEx(e);
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
	
	public OutputStream streamOut( DaogenJFileDB file ) {
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
			PathDescriptor descriptor = JFileUtils.pathDescriptor( file.getPath() );
			ModelDbJvfsFile model = this.facade.loadById( context, descriptor.getName(), this.getParentPath(descriptor) );
			boolean create = ( model == null );
			Timestamp currentTime = TimeHelper.nowTimestamp();
			if ( create ) {
				model = new HelperDbJvfsFile();
				model.setFileName( descriptor.getName() );
				model.setParentPath( this.getParentPath(descriptor) );
				model.setCreationTime( currentTime );
				file.setDbFile( model );
			}
			model.setFileProps( getFileProps( file ) );
			model.setUpdateTime( currentTime );
			if ( data != null ) {
				model.setFileSize( new BigDecimal( data.length ) );
				model.setFileContent( ByteArrayDataHandler.newHandlerByte( data ) );
			}
			// create or update?
			BasicDaoResult<ModelDbJvfsFile> updateResult = null;
			if ( create ) {
				updateResult = this.facade.create(context, model);
			} else {
				updateResult = this.facade.updateById(context, model);
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
	

	public boolean rename( DaogenJFileDB file, JFile newFile ) throws IOException {
		boolean renamed = false;
		if ( ( file.isDirectory() && newFile.isDirectory() ) || ( file.isFile() && newFile.isFile() ) ) {
			try ( CloseableDAOContext context = this.newContext() ) {
				int res = this.facade.rename( context, file, newFile );
				renamed = (res > 0);
			} catch (Exception e) {
				throw new IOException( e );
			}
		} else {
			throw new IOException( "Cannot rename to different file type "+file.describe()+" -> "+newFile.describe() );
		}	
 		return renamed;
	}	
	
	protected CloseableDAOContext newContext() throws DAOException {
		return new CloseableDAOContextSC( this.cf.getConnection() ) ;
	}

}
