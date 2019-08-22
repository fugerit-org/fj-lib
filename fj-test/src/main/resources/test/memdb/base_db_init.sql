SET DATABASE SQL SYNTAX PGS TRUE;

CREATE SCHEMA fugerit;

CREATE TABLE fugerit.user (
	id bigint NOT NULL,
	username VARCHAR(128) NOT NULL,
	password VARCHAR(128) NOT NULL,
	last_login TIMESTAMP,
	date_insert TIMESTAMP,
	date_update TIMESTAMP,
	state INTEGER NOT NULL
);
ALTER TABLE fugerit.user ADD CONSTRAINT users_pk PRIMARY KEY ( id );
ALTER TABLE fugerit.user ADD CONSTRAINT users_sk1 UNIQUE ( username );
COMMENT ON TABLE fugerit.user IS 'Contains users data';
COMMENT ON COLUMN fugerit.user.id IS 'User system id';
COMMENT ON COLUMN fugerit.user.username IS 'User chosen id';
COMMENT ON COLUMN fugerit.user.password IS 'Password hash';
COMMENT ON COLUMN fugerit.user.last_login IS 'Time of last user login';
COMMENT ON COLUMN fugerit.user.state IS '1 active, 0 not active';


CREATE TABLE fugerit.address (
	id bigint NOT NULL,
	id_user bigint NOT NULL,
	date_insert TIMESTAMP,
	date_update TIMESTAMP,	
	info VARCHAR(256) NOT NULL
	
);
ALTER TABLE fugerit.address ADD CONSTRAINT addresses_pk PRIMARY KEY ( id );
ALTER TABLE fugerit.address ADD CONSTRAINT addresses_fk1 FOREIGN KEY ( id_user ) REFERENCES fugerit.user ( id );
COMMENT ON TABLE fugerit.address IS 'Contains addresses data';
COMMENT ON COLUMN fugerit.address.id IS 'Address system id';
COMMENT ON COLUMN fugerit.address.id_user IS 'User linked to to address';
COMMENT ON COLUMN fugerit.address.info IS 'Address info';

CREATE TABLE fugerit.upload (
	id bigint NOT NULL,
	date_insert TIMESTAMP,
	date_update TIMESTAMP,	
	content BLOB
	
);
ALTER TABLE fugerit.upload ADD CONSTRAINT upload_pk PRIMARY KEY ( id );
COMMENT ON TABLE fugerit.upload IS 'Contains upload blob';


CREATE TABLE fugerit.log_data (
	id bigint NOT NULL,
	log_time TIMESTAMP,
	info VARCHAR(128) NOT NULL
);

CREATE TABLE fugerit.test_two_field_key (
	id_one bigint NOT NULL,
	id_two bigint NOT NULL,
	info VARCHAR(128) NOT NULL
);
ALTER TABLE fugerit.test_two_field_key ADD CONSTRAINT test_two_field_key_pk PRIMARY KEY ( id_one, id_two );

CREATE SEQUENCE seq_test START WITH 1;

INSERT INTO fugerit.user ( id, username, password, last_login, state ) VALUES ( nextval('seq_test'), 'user1', 'a3f8h5h4h3n3n1n9', sysdate, 1 );
INSERT INTO fugerit.user ( id, username, password, last_login, state ) VALUES ( nextval('seq_test'), 'user2', '5h4h3n3n1n9a3f8h', sysdate, 0 );

INSERT INTO fugerit.address ( id, id_user, info ) VALUES ( nextval('seq_test'), 1, 'test address 01' );
INSERT INTO fugerit.address ( id, id_user, info ) VALUES ( nextval('seq_test'), 2, 'test address 02' );
INSERT INTO fugerit.address ( id, id_user, info ) VALUES ( nextval('seq_test'), 1, 'test address 03' );

INSERT INTO fugerit.test_two_field_key ( id_one, id_two, info ) VALUES ( nextval('seq_test'), nextval('seq_test'), 'test info 01' );
