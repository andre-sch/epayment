create table if not exists users (
  id integer primary key auto_increment,
  document varchar(255) unique not null,
  full_name varchar(255) not null,
  email varchar(255) unique not null,
  password varchar(255) not null
);
