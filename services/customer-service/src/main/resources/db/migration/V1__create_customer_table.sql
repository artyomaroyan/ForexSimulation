create table if not exists customer.customer(
    id       uuid unique not null primary key default gen_random_uuid(),
    name     varchar(50) not null,
    email    varchar(70) not null,
    balance  numeric(12, 2)      not null
);