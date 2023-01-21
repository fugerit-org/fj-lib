package org.fugerit.java.core.jvfs.db.daogen;

import org.fugerit.java.core.jvfs.db.daogen.impl.HelperDbJvfsFile;

// custom import start ( code above here will be overwritten )
// custom import end ( code below here will be overwritten )

/**
 * ModelDbJvfsFile, version : 1.0.0
 *
 * author: fugerit
 *
 * warning!: auto generated object, insert custom code only between comments :
 * // custom code start ( code above here will be overwritten )
 * // custom code end ( code below here will be overwritten )
 */
@org.fugerit.java.core.lang.annotate.DefineImpl(as = HelperDbJvfsFile.class)
public interface ModelDbJvfsFile extends org.fugerit.java.core.lang.compare.CheckEmpty {

	// custom code start ( code above here will be overwritten )
	// custom code end ( code below here will be overwritten )

	/**
	 * Getter method for property : fileName (nullable: no)
	 *
	 * The file name
	 *
	 * @return the value of fileName
	 */
	java.lang.String getFileName();

	/**
	 * Setter method for property : fileName (nullable: no)
	 *
	 * The file name
	 *
	 * @param value the value of fileName
	 */
	void setFileName( java.lang.String value );

	/**
	 * Getter method for property : parentPath (nullable: no)
	 *
	 * The parent path, empty for root
	 *
	 * @return the value of parentPath
	 */
	java.lang.String getParentPath();

	/**
	 * Setter method for property : parentPath (nullable: no)
	 *
	 * The parent path, empty for root
	 *
	 * @param value the value of parentPath
	 */
	void setParentPath( java.lang.String value );

	/**
	 * Getter method for property : fileProps (nullable: yes)
	 *
	 * File properties, for example RO; if the file is readonly
	 *
	 * @return the value of fileProps
	 */
	java.lang.String getFileProps();

	/**
	 * Setter method for property : fileProps (nullable: yes)
	 *
	 * File properties, for example RO; if the file is readonly
	 *
	 * @param value the value of fileProps
	 */
	void setFileProps( java.lang.String value );

	/**
	 * Getter method for property : creationTime (nullable: no)
	 *
	 * File creation time
	 *
	 * @return the value of creationTime
	 */
	java.util.Date getCreationTime();

	/**
	 * Setter method for property : creationTime (nullable: no)
	 *
	 * File creation time
	 *
	 * @param value the value of creationTime
	 */
	void setCreationTime( java.util.Date value );

	/**
	 * Getter method for property : updateTime (nullable: no)
	 *
	 * File update time
	 *
	 * @return the value of updateTime
	 */
	java.util.Date getUpdateTime();

	/**
	 * Setter method for property : updateTime (nullable: no)
	 *
	 * File update time
	 *
	 * @param value the value of updateTime
	 */
	void setUpdateTime( java.util.Date value );

	/**
	 * Getter method for property : fileSize (nullable: yes)
	 *
	 * The size of the file (not set for directories)
	 *
	 * @return the value of fileSize
	 */
	java.math.BigDecimal getFileSize();

	/**
	 * Setter method for property : fileSize (nullable: yes)
	 *
	 * The size of the file (not set for directories)
	 *
	 * @param value the value of fileSize
	 */
	void setFileSize( java.math.BigDecimal value );

	/**
	 * Getter method for property : fileContent (nullable: yes)
	 *
	 * The content of the file (not set for directories)
	 *
	 * @return the value of fileContent
	 */
	org.fugerit.java.core.db.daogen.ByteArrayDataHandler getFileContent();

	/**
	 * Setter method for property : fileContent (nullable: yes)
	 *
	 * The content of the file (not set for directories)
	 *
	 * @param value the value of fileContent
	 */
	void setFileContent( org.fugerit.java.core.db.daogen.ByteArrayDataHandler value );

}
