drop database if exists athenas;
create database athenas;
use athenas;

create table pessoa (
    Id bigint primary key auto_increment,
    Nome varchar(255) not null,
    Data_Nasc date not null,
    Sexo char(1) not null,
    Altura real not null,
    Peso real not null
);

alter table pessoa add constraint chk_sexo check (sexo in ('M', 'F'));

INSERT INTO `athenas`.`pessoa` (`Nome`, `Data_Nasc`, `Sexo`, `Altura`, `Peso`) VALUES ('Dedo', '2001-05-05', 'M', '1.75', '71.7');
INSERT INTO `athenas`.`pessoa` (`Nome`, `Data_Nasc`, `Sexo`, `Altura`, `Peso`) VALUES ('Cacatua', '2001-10-15', 'F', '1.63', '65');