create table author (id bigint not null auto_increment, key varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table book (first_publish_year integer, id bigint not null auto_increment, key varchar(255), title varchar(255), primary key (id)) engine=InnoDB;
create table book_author (author_id bigint not null, book_id bigint not null) engine=InnoDB;
create table edition (book_id bigint, id bigint not null auto_increment, key varchar(255), language varchar(255), title varchar(255), primary key (id)) engine=InnoDB;
create table review (rating integer, book_id bigint, created_at datetime(6), id bigint not null auto_increment, user_id bigint, comment varchar(255), primary key (id)) engine=InnoDB;
create table user (id bigint not null auto_increment, email varchar(255), password varchar(255), username varchar(255), primary key (id)) engine=InnoDB;
alter table book_author add constraint FKbjqhp85wjv8vpr0beygh6jsgo foreign key (author_id) references author (id);
alter table book_author add constraint FKhwgu59n9o80xv75plf9ggj7xn foreign key (book_id) references book (id);
alter table edition add constraint FK7q57m1a7u3voirj9k9ncugsdr foreign key (book_id) references book (id);
alter table review add constraint FK70yrt09r4r54tcgkrwbeqenbs foreign key (book_id) references book (id);
alter table review add constraint FKiyf57dy48lyiftdrf7y87rnxi foreign key (user_id) references user (id);
