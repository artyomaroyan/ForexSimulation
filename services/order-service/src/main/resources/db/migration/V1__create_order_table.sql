create table if not exists orders.orders(
    id uuid unique not null primary key default gen_random_uuid(),
    customer_id uuid not null ,
    currency_from varchar(5) not null ,
    currency_to varchar(5) not null ,
    amount decimal not null ,
    rate decimal not null ,
    status varchar(25) not null
)