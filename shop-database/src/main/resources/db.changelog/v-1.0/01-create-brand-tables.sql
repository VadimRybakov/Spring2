create table brands
(
    id    bigint       not null auto_increment,
    title varchar(255) not null,
    primary key (id)
) engine=InnoDB;
GO