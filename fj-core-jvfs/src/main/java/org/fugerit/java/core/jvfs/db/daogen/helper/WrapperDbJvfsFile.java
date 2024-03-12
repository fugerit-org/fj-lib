package org.fugerit.java.core.jvfs.db.daogen.helper;

import org.fugerit.java.core.db.daogen.BasicWrapperNG;
import org.fugerit.java.core.jvfs.db.daogen.model.ModelDbJvfsFile;

// custom import start ( code above here will be overwritten )
// custom import end ( code below here will be overwritten )

/**
 * WrapperDbJvfsFile, version : 1.0.0
 *
 * author: fugerit
 *
 * warning!: auto generated object, insert custom code only between comments :
 * // custom code start ( code above here will be overwritten )
 * // custom code end ( code below here will be overwritten )
 */
public class WrapperDbJvfsFile extends BasicWrapperNG<ModelDbJvfsFile> implements ModelDbJvfsFile {

	// custom code start ( code above here will be overwritten )
	// custom code end ( code below here will be overwritten )

	public WrapperDbJvfsFile( ModelDbJvfsFile wrapped ) {
		super( wrapped );
	}

	public ModelDbJvfsFile unwrap( WrapperDbJvfsFile wrapper ) {
		ModelDbJvfsFile res = wrapper;
		while ( res instanceof WrapperDbJvfsFile ) { 
			res = ((WrapperDbJvfsFile)res).unwrapModel();
		}
		return res;
	}

	/*
	 * fields generated for entity attributes 
	 */
	@Override
	public void setFileName( java.lang.String value ) {
		this.unwrapModel().setFileName( value );
	}

	@Override
	public java.lang.String getFileName() {
		return this.unwrapModel().getFileName();
	}

	@Override
	public void setParentPath( java.lang.String value ) {
		this.unwrapModel().setParentPath( value );
	}

	@Override
	public java.lang.String getParentPath() {
		return this.unwrapModel().getParentPath();
	}

	@Override
	public void setFileProps( java.lang.String value ) {
		this.unwrapModel().setFileProps( value );
	}

	@Override
	public java.lang.String getFileProps() {
		return this.unwrapModel().getFileProps();
	}

	@Override
	public void setCreationTime( java.util.Date value ) {
		this.unwrapModel().setCreationTime( value );
	}

	@Override
	public java.util.Date getCreationTime() {
		return this.unwrapModel().getCreationTime();
	}

	@Override
	public void setUpdateTime( java.util.Date value ) {
		this.unwrapModel().setUpdateTime( value );
	}

	@Override
	public java.util.Date getUpdateTime() {
		return this.unwrapModel().getUpdateTime();
	}

	@Override
	public void setFileSize( java.math.BigDecimal value ) {
		this.unwrapModel().setFileSize( value );
	}

	@Override
	public java.math.BigDecimal getFileSize() {
		return this.unwrapModel().getFileSize();
	}

	@Override
	public void setFileContent( org.fugerit.java.core.db.daogen.ByteArrayDataHandler value ) {
		this.unwrapModel().setFileContent( value );
	}

	@Override
	public org.fugerit.java.core.db.daogen.ByteArrayDataHandler getFileContent() {
		return this.unwrapModel().getFileContent();
	}

	@Override
	public boolean isEmpty() {
		return this.unwrapModel().isEmpty();
	}

}
