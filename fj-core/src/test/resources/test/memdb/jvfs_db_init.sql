CREATE TABLE fugerit.db_jvfs_file (
	file_name VARCHAR(1024) NOT NULL,
	parent_path VARCHAR(2048) NOT NULL,
	file_props VARCHAR(1024),
	creation_time TIMESTAMP NOT NULL,
	update_time TIMESTAMP NOT NULL,
	file_size BIGINT,
	file_content BLOB
);
ALTER TABLE fugerit.db_jvfs_file ADD CONSTRAINT db_jvfs_file_ok PRIMARY KEY ( file_name, parent_path );
COMMENT ON TABLE fugerit.db_jvfs_file IS 'The files in the file system';
COMMENT ON COLUMN fugerit.db_jvfs_file.file_name IS 'The file name';

CREATE SEQUENCE fugerit.seq_jvfs START WITH 1;

INSERT INTO fugerit.db_jvfs_file ( file_name, parent_path, file_props, creation_time, update_time, file_size, file_content ) 
VALUES ( 'test_file', 'parent_path', nextval('fugerit.seq_jvfs'), current_timestamp, current_timestamp, 0, null );
