CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 50;
CREATE TABLE book
(
    id      bigserial primary key,
    name    varchar(255)   not null,
    author  varchar(255)   not null,
    deleted boolean        not null default false,
    price   numeric(19, 2) not null
);
ALTER SEQUENCE book_id_seq INCREMENT BY 50;