create table if not exists currency.rate (
    id uuid unique not null primary key default gen_random_uuid(),
    currency_from varchar(3) not null,
    currency_to varchar(3) not null,
    rate bigint not null,
    last_updated timestamp not null default current_timestamp
);