create table usuarios(

    id bigint not null auto_increment,
    nome varchar(100) not null,
    email varchar(100) not null unique,
    usuario varchar(100) not null unique,
    senha varchar(255) not null unique,

    primary key(id)

);