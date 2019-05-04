CREATE DATABASE if not exists `alplaneta`;
﻿USE alplaneta;

CREATE TABLE `medioContacto`(
  `idMedioContacto` int(11) NOT NULL AUTO_INCREMENT,
  `numeroFijo` varchar(45) NOT NULL,
  `numeroCelular` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  
  PRIMARY KEY (`idMedioContacto`)
);

CREATE TABLE `cliente`(
  `idCliente` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `dni` varchar(8) NOT NULL,
  `fechaNacimiento` Date(20) NOT NULL,

  `idMedioContacto` int(11) NOT NULL,
  
  PRIMARY KEY (`idCliente`),
  FOREIGN KEY (`idMedioContacto`) references mediosContacto(`idMedioContacto`)
);

CREATE TABLE `administrativo`(
  `idAdministrativo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,  
  
  PRIMARY KEY (`idAdministrativo`)
);

CREATE TABLE `ciudad`(
  `idCiudad` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  
  PRIMARY KEY (`idCiudad`)
);

CREATE TABLE `viaje`(
  `idViaje` int(11) NOT NULL AUTO_INCREMENT,
  `fechaSalida` Date(20) NOT NULL,
  `fechaLlegada` Date(20) NOT NULL,
  `precio` decimal(11) NOT NULL,
  
  `idCiudadOrigen` int(11) NOT NULL,
  `idCiudadDestino` int(11) NOT NULL,
  
  PRIMARY KEY (`idViaje`),
  FOREIGN KEY (`idCiudadOrigen`) references ciudades (`idCiudad`),
  FOREIGN KEY (`idCiudadDestino`) references ciudades(`idCiudad`)
);

CREATE TABLE `transporte`(
  `idTransporte` int(11) NOT NULL AUTO_INCREMENT,
  `capacidad` int(11) NOT NULL,
  `nombreTransporte` varchar(45) NOT NULL,
  `precioBase` decimal(11) NOT NULL,
  
  PRIMARY KEY (`idTransporte`)
);

CREATE TABLE `pago`(
  `idPago` int(11) NOT NULL AUTO_INCREMENT,
  `monto` decimal(11) NOT NULL,
  `fechaPago` Date(20) NOT NULL,
    
  PRIMARY KEY (`idPago`)
);
 
CREATE TABLE `estadosPasaje`( /*ESTADO=PagoPendiente, PagoParcial, PagoTotal*/
  `idEstadoPasaje` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` int(11) NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  
  PRIMARY KEY (`idEstadoPasaje`)
);

CREATE TABLE `pasaje`(
  `idPasaje` int(11) NOT NULL AUTO_INCREMENT,
  `cantidadPasajeros` int(11) NOT NULL,
  `fechaVencimiento` Date(20),
  `valorViaje` decimal(11) NOT NULL,
 
  `idCliente` int (11) NOT NULL,
  `idViaje` int (11) NOT NULL,
  `idTransporte` int (11) NOT NULL,
  `idAdministrativo` int (11) NOT NULL,
  `idEstadoPasaje` int(11) NOT NULL,
  `idPago` int(3) NOT NULL,
  
  PRIMARY KEY (`idPasaje`),
  FOREIGN KEY (`idCliente`) references clientes(`idCliente`),
  FOREIGN KEY (`idViaje`) references clientes(`idViaje`),
  FOREIGN KEY (`idTransporte`) references transportes(`idTransporte`),
  FOREIGN KEY (`idAdministrativo`) references administrativos(`idAdministrativo`),
  FOREIGN KEY (`idPago`) references pagos(`idPago`),
  FOREIGN KEY (`idEstadoPasaje`) references estadosPasaje(`idEstadoPasaje`),  
);

CREATE TABLE `pasajero`(
  `idPasajero` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `dni` varchar(8) NOT NULL,
  
  `idPasaje` int(3) NOT NULL,
  
  PRIMARY KEY (`idPasajero`),
  FOREIGN KEY (`idPasaje`) references pasajes (`idPasaje`)
);

CREATE TABLE `pasajes_pasajeros`(
  `idPasajePasajero` int(11) NOT NULL AUTO_INCREMENT,
  `idPasaje` int(11) NOT NULL,
  `idPasajero` int(11) NOT NULL,
  
  PRIMARY KEY (`idPasajePasajero`),
  FOREIGN KEY (`idPasaje`) references pasajes(`idPasaje`),
  FOREIGN KEY (`idPasajero`) references pasajeros(`idPasajero`)
);

CREATE TABLE `horarioReserva`(
  `idHorario` int(11) NOT NULL AUTO_INCREMENT,
  `horarioInicio` Date(20) NOT NULL,
  `horarioFin` Date(20) NOT NULL,

  PRIMARY KEY (`idHorario`)
);