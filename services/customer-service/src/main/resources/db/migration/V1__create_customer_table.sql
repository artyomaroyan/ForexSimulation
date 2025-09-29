create table if not exists customer.customer(
    id       uuid unique not null primary key default gen_random_uuid(),
    name     varchar(50) not null,
    email    varchar(70) unique not null,
    balance  decimal not null
);