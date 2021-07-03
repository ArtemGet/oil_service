# SQL schemes

### Database creation

```postgresql
create database oil_db
    with owner postgres;
```    

### Users scheme

```postgresql
create table if not exists users
(
    email    varchar(60) unique NOT NULL,
    user_name     varchar(30) unique NOT NULL,
    password varchar(128)       NOT NULL,
    is_admin bool DEFAULT false NOT NULL,
    PRIMARY KEY (user_name)
);
```

### Oil scheme

```postgresql
create table if not exists oil_types
(
    output_place varchar(128)       NOT NULL,
    oil_name         varchar(32) unique NOT NULL,
    density20    double precision   NOT NULL,
    density50    double precision   NOT NULL,
    viscosity20  double precision   NOT NULL,
    viscosity50  double precision   NOT NULL,
    HK_350       double precision   NOT NULL,
    record_id int NOT NULL,
    PRIMARY KEY (oil_name),
    CONSTRAINT fk_record_id
        FOREIGN KEY(record_id)
            REFERENCES oil_records(record_id)
            ON DELETE CASCADE
);
```

### Record scheme

```postgresql
create table if not exists oil_records
(
    record_id serial unique NOT NULL,
    user_name varchar(30) NOT NULL,
    PRIMARY KEY (record_id),
    CONSTRAINT fk_user_name
        FOREIGN KEY(user_name)
            REFERENCES users(user_name)
            ON DELETE CASCADE
)
```