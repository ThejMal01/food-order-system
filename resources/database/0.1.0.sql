create
database if not exists food_order;

create
user food_order_root identified by '<password>';
grant all privileges on food_order.* to food_order_root;

flush
privileges;