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
    id_user  serial unique      NOT NULL,
    email    varchar(60) unique NOT NULL,
    name     varchar(30) unique NOT NULL,
    password varchar(128)       NOT NULL,
    is_admin bool DEFAULT false NOT NULL,
    PRIMARY KEY (id_user)
);
```