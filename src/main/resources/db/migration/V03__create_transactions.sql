create table if not exists transactions (
  id integer primary key auto_increment,
  sender_id integer not null,
  receiver_id integer not null,
  amount decimal(10, 2) not null,
  executed_at timestamp default current_timestamp,
  foreign key (sender_id) references wallets (id),
  foreign key (receiver_id) references wallets (id)
);