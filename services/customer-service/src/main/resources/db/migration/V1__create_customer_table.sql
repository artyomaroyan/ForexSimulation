create table if not exists customer.customer(
    id uuid unique not null primary key default pg_catalog.gen_random_uuid(),
    username varchar(50) not null ,
    password varchar(20) not null, -- password length should be greater if it hashed but since I do not hash I set 20.
    name varchar(50) not null ,
    email varchar(70) not null ,
    balance bigint not null
)