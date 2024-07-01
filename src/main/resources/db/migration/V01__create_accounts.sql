create table if not exists accounts (
  id integer primary key auto_increment,
  balance decimal(10, 2) not null,
  full_name varchar(255),
  email varchar(255),
  password varchar(255)
);
