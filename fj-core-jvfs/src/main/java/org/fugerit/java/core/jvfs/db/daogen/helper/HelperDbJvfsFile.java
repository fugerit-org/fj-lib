package org.fugerit.java.core.jvfs.db.daogen.helper;

import org.fugerit.java.core.db.daogen.BasicHelper;
import org.fugerit.java.core.jvfs.db.daogen.model.ModelDbJvfsFile;

// custom import start ( code above here will be overwritten )
// custom import end ( code below here will be overwritten )

/**
 * HelperDbJvfsFile, version : 1.0.0
 *
 * author: fugerit
 *
 * warning!: auto generated object, insert custom code only between comments :
 * // custom code start ( code above here will be overwritten )
 * // custom code end ( code below here will be overwritten )
 */
public class HelperDbJvfsFile extends BasicHelper implements ModelDbJvfsFile {

	// custom code start ( code above here will be overwritten )
	
	// code added to setup a basic conditional serialization - START
	
	private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
		// this class is conditionally serializable, depending on contained object
		// you are encouraged to handle special situation using this method
		out.defaultWriteObject();
	}

	private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
		// this class is conditionally serializable, depending on contained object
		// you are encouraged to handle special situation using this method
		in.defaultReadObject();
	}
	
	// code added to setup a basic conditional serialization - END
	
	// custom code end ( code below here will be overwritten )

	private static final long serialVersionUID = 405195931944L;

	/*
	 * fields generated for entity attributes 
	 */
	private java.lang.String fileName;

	@Override
	public void setFileName( java.lang.String value ) {
		this.fileName = value;
	}

	@Override
	public java.lang.String getFileName() {
		return this.fileName;
	}

	private java.lang.String parentPath;

	@Override
	public void setParentPath( java.lang.String value ) {
		this.parentPath = value;
	}

	@Override
	public java.lang.String getParentPath() {
		return this.parentPath;
	}

	private java.lang.String fileProps;

	@Override
	public void setFileProps( java.lang.String value ) {
		this.fileProps = value;
	}

	@Override
	public java.lang.String getFileProps() {
		return this.fileProps;
	}

	private java.util.Date creationTime;

	@Override
	public void setCreationTime( java.util.Date value ) {
		this.creationTime = value;
	}

	@Override
	public java.util.Date getCreationTime() {
		return this.creationTime;
	}

	private java.util.Date updateTime;

	@Override
	public void setUpdateTime( java.util.Date value ) {
		this.updateTime = value;
	}

	@Override
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	private java.math.BigDecimal fileSize;

	@Override
	public void setFileSize( java.math.BigDecimal value ) {
		this.fileSize = value;
	}

	@Override
	public java.math.BigDecimal getFileSize() {
		return this.fileSize;
	}

	private org.fugerit.java.core.db.daogen.ByteArrayDataHandler fileContent;

	@Override
	public void setFileContent( org.fugerit.java.core.db.daogen.ByteArrayDataHandler value ) {
		this.fileContent = value;
	}

	@Override
	public org.fugerit.java.core.db.daogen.ByteArrayDataHandler getFileContent() {
		return this.fileContent;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append( this.getClass().getSimpleName() );
		buffer.append( "[fileName=" );
		buffer.append( this.getFileName() );
		buffer.append( ",parentPath=" );
		buffer.append( this.getParentPath() );
		buffer.append( ",fileProps=" );
		buffer.append( this.getFileProps() );
		buffer.append( ",creationTime=" );
		buffer.append( this.getCreationTime() );
		buffer.append( ",updateTime=" );
		buffer.append( this.getUpdateTime() );
		buffer.append( ",fileSize=" );
		buffer.append( this.getFileSize() );
		buffer.append( ",fileContent=" );
		buffer.append( this.getFileContent() );
		buffer.append( "]" );
		return buffer.toString();
	}

	@Override
	public boolean isEmpty() {
		return  ( org.fugerit.java.core.lang.compare.CheckEmptyHelper.isEmpty( this.getFileName() ) )
		 &&  ( org.fugerit.java.core.lang.compare.CheckEmptyHelper.isEmpty( this.getParentPath() ) )
		 &&  ( org.fugerit.java.core.lang.compare.CheckEmptyHelper.isEmpty( this.getFileProps() ) )
		 &&  ( org.fugerit.java.core.lang.compare.CheckEmptyHelper.isEmpty( this.getCreationTime() ) )
		 &&  ( org.fugerit.java.core.lang.compare.CheckEmptyHelper.isEmpty( this.getUpdateTime() ) )
		 &&  ( org.fugerit.java.core.lang.compare.CheckEmptyHelper.isEmpty( this.getFileSize() ) )
		 &&  ( org.fugerit.java.core.lang.compare.CheckEmptyHelper.isEmpty( this.getFileContent() ) );
	}

}
