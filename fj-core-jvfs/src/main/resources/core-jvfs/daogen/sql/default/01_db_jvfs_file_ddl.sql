SET DATABASE SQL SYNTAX ORA TRUE;

CREATE TABLE db_jvfs_file (
	file_name VARCHAR2(1024) NOT NULL,
	parent_path VARCHAR2(2048) NOT NULL,
	file_props VARCHAR2(1024),
	creation_time TIMESTAMP NOT NULL,
	update_time TIMESTAMP NOT NULL,
	file_size BIGINT,
	file_content BLOB
);
ALTER TABLE db_jvfs_file ADD CONSTRAINT db_jvfs_file_ok PRIMARY KEY ( file_name, parent_path );
COMMENT ON TABLE db_jvfs_file IS 'The files in the file system';
COMMENT ON COLUMN db_jvfs_file.file_name IS 'The file name';
COMMENT ON COLUMN db_jvfs_file.parent_path IS 'The parent path, empty for root';
COMMENT ON COLUMN db_jvfs_file.file_props IS 'File properties, for example RO; if the file is readonly';
COMMENT ON COLUMN db_jvfs_file.creation_time IS 'File creation time';
COMMENT ON COLUMN db_jvfs_file.update_time IS 'File update time';
COMMENT ON COLUMN db_jvfs_file.file_size IS 'The size of the file (not set for directories)';
COMMENT ON COLUMN db_jvfs_file.file_content IS 'The content of the file (not set for directories)';