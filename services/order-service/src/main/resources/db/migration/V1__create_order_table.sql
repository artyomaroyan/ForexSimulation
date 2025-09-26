create table if not exists order.order(
    id uuid unique not null primary key default gen_random_uuid(),
    customer_id uuid not null ,
    currency_from varchar(5) not null ,
    currency_to varchar(5) not null ,
    amount bigint not null ,
    rate bigint not null ,
    status varchar(25) not null
)