# Host: localhost  (Version 5.7.23)
# Date: 2019-05-05 12:18:02
# Generator: MySQL-Front 6.1  (Build 1.26)


#
# Structure for table "administrativo"
#

CREATE TABLE `administrativo` (
  `idAdministrativo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`idAdministrativo`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

#
# Data for table "administrativo"
#

INSERT INTO `administrativo` VALUES (1,'solAdministrativa'),(2,'LizzAdministrativa'),(3,'LizzAdministrativa'),(4,'MicaAdministrativa'),(5,'MicaAdministrativa');

#
# Structure for table "ciudad"
#

CREATE TABLE `ciudad` (
  `idCiudad` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`idCiudad`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

#
# Data for table "ciudad"
#

INSERT INTO `ciudad` VALUES (1,'Buenos Aires'),(2,'Catamarca'),(3,'Chaco'),(4,'Chubut'),(5,'Corrientes'),(6,'Formosa'),(7,'Jujuy'),(8,'Mendoza');

#
# Structure for table "cliente"
#

CREATE TABLE `cliente` (
  `idCliente` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `dni` varchar(8) NOT NULL,
  `fechaNacimiento` date NOT NULL,
  `idMedioContacto` int(11) NOT NULL,
  PRIMARY KEY (`idCliente`),
  KEY `idMedioContacto` (`idMedioContacto`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

#
# Data for table "cliente"
#


#
# Structure for table "estadospasaje"
#

CREATE TABLE `estadospasaje` (
  `idEstadoPasaje` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` int(11) NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`idEstadoPasaje`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

#
# Data for table "estadospasaje"
#


#
# Structure for table "horario"
#

CREATE TABLE `horario` (
  `idHorario` int(11) NOT NULL AUTO_INCREMENT,
  `hora` varchar(10) NOT NULL,
  PRIMARY KEY (`idHorario`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

#
# Data for table "horario"
#

INSERT INTO `horario` VALUES (1,'1:00'),(2,'2:00'),(3,'3:00'),(4,'4:00'),(5,'4:00'),(6,'5:00'),(7,'6:00'),(8,'7:00'),(9,'8:00'),(10,'9:00'),(11,'10:00'),(12,'11:00'),(13,'12:00');

#
# Structure for table "horarioreserva"
#

CREATE TABLE `horarioreserva` (
  `idHorario` int(11) NOT NULL AUTO_INCREMENT,
  `horarioInicio` varchar(256) NOT NULL DEFAULT '0000-00-00',
  `horarioFin` varchar(255) NOT NULL DEFAULT '0000-00-00',
  PRIMARY KEY (`idHorario`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

#
# Data for table "horarioreserva"
#

INSERT INTO `horarioreserva` VALUES (1,'00:00','02:59'),(2,'03:00','05:59'),(3,'06:00','08:59'),(4,'09:00','11:59'),(5,'12:00','14:59'),(6,'15:00','17:59'),(7,'18:00','20:59'),(8,'21:00','23:59');

#
# Structure for table "mediocontacto"
#

CREATE TABLE `mediocontacto` (
  `idMedioContacto` int(11) NOT NULL AUTO_INCREMENT,
  `numeroFijo` varchar(45) NOT NULL,
  `numeroCelular` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`idMedioContacto`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

#
# Data for table "mediocontacto"
#


#
# Structure for table "pago"
#

CREATE TABLE `pago` (
  `idPago` int(11) NOT NULL AUTO_INCREMENT,
  `monto` decimal(11,0) NOT NULL,
  `fechaPago` date NOT NULL,
  PRIMARY KEY (`idPago`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

#
# Data for table "pago"
#


#
# Structure for table "pasaje"
#

CREATE TABLE `pasaje` (
  `idPasaje` int(11) NOT NULL AUTO_INCREMENT,
  `cantidadPasajeros` int(11) NOT NULL,
  `fechaVencimiento` date DEFAULT NULL,
  `valorViaje` decimal(11,0) NOT NULL,
  `idCliente` int(11) NOT NULL,
  `idViaje` int(11) NOT NULL,
  `idTransporte` int(11) NOT NULL,
  `idAdministrativo` int(11) NOT NULL,
  `idEstadoPasaje` int(11) NOT NULL,
  `idPago` int(3) NOT NULL,
  PRIMARY KEY (`idPasaje`),
  KEY `idCliente` (`idCliente`),
  KEY `idViaje` (`idViaje`),
  KEY `idTransporte` (`idTransporte`),
  KEY `idAdministrativo` (`idAdministrativo`),
  KEY `idPago` (`idPago`),
  KEY `idEstadoPasaje` (`idEstadoPasaje`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

#
# Data for table "pasaje"
#


#
# Structure for table "pasajero"
#

CREATE TABLE `pasajero` (
  `idPasajero` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `dni` varchar(8) NOT NULL,
  PRIMARY KEY (`idPasajero`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

#
# Data for table "pasajero"
#

INSERT INTO `pasajero` VALUES (1,'LizzAdministrativa','Moreno','36584266'),(2,'MicaAdministrativa','Perez','32125322'),(3,'SolAdministrativa','Hoyos','25652544'),(4,'Nico','Avila','39568922'),(5,'Juan','Perez','2563366'),(6,'Pepito','Lopez','32556988'),(7,'Mirta','Legrand','00000001');

#
# Structure for table "pasajes_pasajeros"
#

CREATE TABLE `pasajes_pasajeros` (
  `idPasajePasajero` int(11) NOT NULL AUTO_INCREMENT,
  `idPasaje` int(11) NOT NULL,
  `idPasajero` int(11) NOT NULL,
  PRIMARY KEY (`idPasajePasajero`),
  KEY `idPasaje` (`idPasaje`),
  KEY `idPasajero` (`idPasajero`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

#
# Data for table "pasajes_pasajeros"
#


#
# Structure for table "transporte"
#

CREATE TABLE `transporte` (
  `idTransporte` int(11) NOT NULL AUTO_INCREMENT,
  `capacidad` int(11) NOT NULL,
  `nombreTransporte` varchar(45) NOT NULL,
  `precioBase` decimal(11,0) NOT NULL,
  PRIMARY KEY (`idTransporte`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

#
# Data for table "transporte"
#

INSERT INTO `transporte` VALUES (1,300,'avion',1000),(2,100,'Micro',400),(3,2000,'Buquebus',20000);

#
# Structure for table "viaje"
#

CREATE TABLE `viaje` (
  `idViaje` int(11) NOT NULL AUTO_INCREMENT,
  `fechaSalida` date NOT NULL,
  `fechaLlegada` date NOT NULL,
  `precio` decimal(11,0) NOT NULL,
  `idCiudadOrigen` int(11) NOT NULL,
  `idCiudadDestino` int(11) NOT NULL,
  `horaSalida` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idViaje`),
  KEY `idCiudadOrigen` (`idCiudadOrigen`),
  KEY `idCiudadDestino` (`idCiudadDestino`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

#
# Data for table "viaje"
#

INSERT INTO `viaje` VALUES (1,'2019-05-01','2019-05-02',200,1,5,'1:00'),(2,'2019-05-04','2019-05-16',300,3,7,'3:00'),(3,'2019-05-16','2019-05-28',500,5,6,'7:00'),(4,'2019-05-29','2019-05-30',1200,1,7,'8:00');
