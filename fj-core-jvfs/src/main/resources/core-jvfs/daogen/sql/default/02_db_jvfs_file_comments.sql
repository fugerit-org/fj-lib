COMMENT ON TABLE db_jvfs_file IS 'The files in the file system';
COMMENT ON COLUMN db_jvfs_file.file_name IS 'The file name';
COMMENT ON COLUMN db_jvfs_file.parent_path IS 'The parent path, empty for root';
COMMENT ON COLUMN db_jvfs_file.file_props IS 'File properties, for example RO; if the file is readonly';
COMMENT ON COLUMN db_jvfs_file.creation_time IS 'File creation time';
COMMENT ON COLUMN db_jvfs_file.update_time IS 'File update time';
COMMENT ON COLUMN db_jvfs_file.file_size IS 'The size of the file (not set for directories)';
COMMENT ON COLUMN db_jvfs_file.file_content IS 'The content of the file (not set for directories)';