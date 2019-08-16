CREATE SCHEMA fugerit;

CREATE TABLE fugerit.users (
	id bigint NOT NULL,
	username VARCHAR(128) NOT NULL,
	password VARCHAR(128) NOT NULL,
	last_login TIMESTAMP,
	state INTEGER NOT NULL
);
ALTER TABLE fugerit.users ADD CONSTRAINT users_pk PRIMARY KEY ( id );
ALTER TABLE fugerit.users ADD CONSTRAINT users_sk1 UNIQUE ( username );
COMMENT ON TABLE fugerit.users IS 'Contains users data';
COMMENT ON COLUMN fugerit.users.id IS 'User system id';
COMMENT ON COLUMN fugerit.users.username IS 'User chosen id';
COMMENT ON COLUMN fugerit.users.password IS 'Password hash';
COMMENT ON COLUMN fugerit.users.last_login IS 'Time of last user login';
COMMENT ON COLUMN fugerit.users.state IS '1 active, 0 not active';


CREATE TABLE fugerit.addresses (
	id bigint NOT NULL,
	id_user bigint NOT NULL,
	info VARCHAR(256) NOT NULL
	
);
ALTER TABLE fugerit.addresses ADD CONSTRAINT addresses_pk PRIMARY KEY ( id );
ALTER TABLE fugerit.addresses ADD CONSTRAINT addresses_fk1 FOREIGN KEY ( id_user ) REFERENCES fugerit.users ( id );
COMMENT ON TABLE fugerit.addresses IS 'Contains addresses data';
COMMENT ON COLUMN fugerit.addresses.id IS 'Address system id';
COMMENT ON COLUMN fugerit.addresses.id_user IS 'User linked to to address';
COMMENT ON COLUMN fugerit.addresses.info IS 'Address info';

INSERT INTO fugerit.users ( id, username, password, last_login, state ) VALUES ( 1, 'user1', 'a3f8h5h4h3n3n1n9', sysdate, 1 );
INSERT INTO fugerit.users ( id, username, password, last_login, state ) VALUES ( 2, 'user2', '5h4h3n3n1n9a3f8h', sysdate, 0 );

INSERT INTO fugerit.addresses ( id, id_user, info ) VALUES ( 1, 1, 'test address 01' );
INSERT INTO fugerit.addresses ( id, id_user, info ) VALUES ( 2, 2, 'test address 02' );
INSERT INTO fugerit.addresses ( id, id_user, info ) VALUES ( 3, 1, 'test address 03' );
