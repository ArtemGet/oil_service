set -e

psql -v ON_ERROR_STOP=1 --dbname "postgres" <<-EOSQL
  create database oil_db
    with owner postgres
    ENCODING 'UTF8';

  \connect oil_db postgres
  BEGIN;
    create table if not exists users
(
    email     varchar(60) unique NOT NULL,
    user_name varchar(30) unique NOT NULL,
    password  varchar(128)       NOT NULL,
    is_admin  bool DEFAULT false NOT NULL,
    PRIMARY KEY (user_name)
);
  COMMIT;
  BEGIN;
create table if not exists oil_records
(
    record_id serial unique NOT NULL,
    user_name varchar(30)   NOT NULL,
    PRIMARY KEY (record_id),
    CONSTRAINT fk_user_name
        FOREIGN KEY (user_name)
            REFERENCES users (user_name)
            ON DELETE CASCADE
);
  COMMIT;
  BEGIN;
create table if not exists oil_types
(
    oil_id       serial unique    NOT NULL,
    output_place varchar(128)     NOT NULL,
    oil_name     varchar(128)     NOT NULL,
    density20    double precision NOT NULL,
    density50    double precision NOT NULL,
    viscosity20  double precision NOT NULL,
    viscosity50  double precision NOT NULL,
    HK_350       double precision NOT NULL,
    record_id    int              NOT NULL,
    PRIMARY KEY (oil_id),
    CONSTRAINT fk_record_id
        FOREIGN KEY (record_id)
            REFERENCES oil_records (record_id)
            ON DELETE CASCADE
);
  COMMIT;
  BEGIN;
  INSERT INTO users (user_name,email,password,is_admin)
  VALUES ('admin', 'admin@mail.ru', '1234', 'true');
  COMMIT;