--DROP DATABASE if  exists alplaneta;

CREATE DATABASE if not exists `alplaneta`;
USE alplaneta;

CREATE TABLE if not exists `cliente`
(
    `idCliente` int(11) NOT NULL AUTO_INCREMENT,
	`nombre` varchar(45) NOT NULL,
    `apellido` varchar(45) NOT NULL,
    `dni` varchar(45) NOT NULL,
    `fechaNacimiento` varchar(45) NOT NULL,
    `telefonoFijo` varchar(45) NOT NULL,
    `telefonoCelular` varchar(45) NOT NULL,
    `email` varchar(45) NOT NULL,
    PRIMARY KEY (`idCliente`)
);