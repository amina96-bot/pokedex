create table pokedex.USER
(
    USER_ID int8,
    USERNAME varchar(255) unique not null ,
    PASSWORD varchar(255),
    primary key (USER_ID)
);
