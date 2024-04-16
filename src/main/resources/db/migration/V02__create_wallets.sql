create table if not exists wallets (
  id integer primary key auto_increment,
  owner_id varchar(255) not null,
  balance decimal(10, 2) not null,
  foreign key (owner_id) references users (id)
);
