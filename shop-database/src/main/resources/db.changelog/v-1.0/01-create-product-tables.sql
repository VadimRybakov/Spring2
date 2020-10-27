create table products
(
    id          bigint       not null auto_increment,
    title        varchar(255) not null,
    price       decimal,
    brand_id    bigint       not null,
    category_id bigint       not null,
    primary key (id)
) engine=InnoDB;
GO

alter table products
    add constraint FKggjhdsfjgdsuysadliy334243
        foreign key (brand_id)
            references brands (id);
GO

alter table products
    add constraint FKdfgfdgdfwawaewhdhfjkhgjkr
        foreign key (category_id)
            references categories (id);
GO