DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS devices;
DROP TABLE IF EXISTS locations;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
  id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name       VARCHAR                           NOT NULL,
  email      VARCHAR                           NOT NULL,
  password   VARCHAR                           NOT NULL,
  registered TIMESTAMP           DEFAULT now() NOT NULL,
  enabled    BOOL                DEFAULT TRUE  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE locations
(
  id       INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name     VARCHAR NOT NULL,
  city     VARCHAR NOT NULL,
  street   VARCHAR NOT NULL,
  building VARCHAR NOT NULL,
  room     INTEGER
);
CREATE TABLE devices
(
  id           INTEGER PRIMARY KEY         DEFAULT nextval('global_seq'),
  location_id  INTEGER                                   NOT NULL,
  dnsname      VARCHAR                                   NOT NULL,
  model        VARCHAR                                   NOT NULL,
  serial       VARCHAR                                   NOT NULL,
  inventory    VARCHAR                                   NOT NULL,
  manufactured TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
  description  VARCHAR                                   NOT NULL,
  contacts     VARCHAR                                   NOT NULL,
  FOREIGN KEY (location_id) REFERENCES locations (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX devices_unuque_dnsname_idx ON devices (dnsname);