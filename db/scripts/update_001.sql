create table if not exists items (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   created  TIMESTAMP,
   done boolean
);

create table if not exists users (
    id serial primary key,
    name varchar(2000),
    password varchar(2000),
    item_id int not null references users(id)
);