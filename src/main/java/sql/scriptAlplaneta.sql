CREATE DATABASE if not exists `alplaneta`;

USE alplaneta;

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
  `fechaNacimiento` Date NOT NULL,

  `idMedioContacto` int(11) NOT NULL,
  
  PRIMARY KEY (`idCliente`),
  FOREIGN KEY (`idMedioContacto`) references medioContacto(`idMedioContacto`)
);

CREATE TABLE `administrativo`(
  `idAdministrativo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,  
  
  PRIMARY KEY (`idAdministrativo`)
);

CREATE TABLE `administrador`(
  `idAdministrador` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,  
  
  PRIMARY KEY (`idAdministrador`)
);

CREATE TABLE `ciudad`(
  `idCiudad` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  
  PRIMARY KEY (`idCiudad`)
);

CREATE TABLE `viaje`(
  `idViaje` int(11) NOT NULL AUTO_INCREMENT,
  `fechaSalida` Date NOT NULL,
  `fechaLlegada` Date NOT NULL,
  `precio` decimal(11) NOT NULL,
  
  `idCiudadOrigen` int(11) NOT NULL,
  `idCiudadDestino` int(11) NOT NULL,
  
  PRIMARY KEY (`idViaje`),
  FOREIGN KEY (`idCiudadOrigen`) references ciudad (`idCiudad`),
  FOREIGN KEY (`idCiudadDestino`) references ciudad(`idCiudad`)
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
  `fechaPago` Date NOT NULL,
    
  PRIMARY KEY (`idPago`)
);
 
CREATE TABLE `estadoPasaje`( /*ESTADO=PagoPendiente, PagoParcial, PagoTotal*/
  `idEstadoPasaje` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` int(11) NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  
  PRIMARY KEY (`idEstadoPasaje`)
);

CREATE TABLE `pasaje`(
  `idPasaje` int(11) NOT NULL AUTO_INCREMENT,
  `cantidadPasajeros` int(11) NOT NULL,
  `fechaVencimiento` Date,
  `valorViaje` decimal(11) NOT NULL,
 
  `idCliente` int (11) NOT NULL,
  `idViaje` int (11) NOT NULL,
  `idTransporte` int (11) NOT NULL,
  `idAdministrativo` int (11) NOT NULL,
  `idEstadoPasaje` int(11) NOT NULL,
  `idPago` int(3) NOT NULL,
  
  PRIMARY KEY (`idPasaje`),
  FOREIGN KEY (`idCliente`) references cliente(`idCliente`),
  FOREIGN KEY (`idViaje`) references viaje(`idViaje`),
  FOREIGN KEY (`idTransporte`) references transporte(`idTransporte`),
  FOREIGN KEY (`idAdministrativo`) references administrativo(`idAdministrativo`),
  FOREIGN KEY (`idPago`) references pago(`idPago`),
  FOREIGN KEY (`idEstadoPasaje`) references estadoPasaje(`idEstadoPasaje`)  
);

CREATE TABLE `pasajero`(
  `idPasajero` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `dni` varchar(8) NOT NULL,
  
  `idPasaje` int(3) NOT NULL,
  
  PRIMARY KEY (`idPasajero`),
  FOREIGN KEY (`idPasaje`) references pasaje (`idPasaje`)
);

CREATE TABLE `pasaje_pasajero`(
  `idPasajePasajero` int(11) NOT NULL AUTO_INCREMENT,
  `idPasaje` int(11) NOT NULL,
  `idPasajero` int(11) NOT NULL,
  
  PRIMARY KEY (`idPasajePasajero`),
  FOREIGN KEY (`idPasaje`) references pasaje(`idPasaje`),
  FOREIGN KEY (`idPasajero`) references pasajero(`idPasajero`)
);

CREATE TABLE `horarioReserva`(
  `idHorario` int(11) NOT NULL AUTO_INCREMENT,
  `horarioInicio` Date NOT NULL,
  `horarioFin` Date NOT NULL,

  PRIMARY KEY (`idHorario`)
);