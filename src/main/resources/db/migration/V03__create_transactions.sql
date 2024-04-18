create table if not exists transactions (
  id integer primary key auto_increment,
  sender_id integer,
  receiver_id integer,
  amount decimal(10, 2) not null,
  executed_at timestamp default current_timestamp,
  foreign key (sender_id) references users (id),
  foreign key (receiver_id) references users (id)
);