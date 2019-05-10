DROP DATABASE if exists `alplaneta`;
CREATE DATABASE if not exists `alplaneta`;
USE alplaneta;

CREATE TABLE `rol` (
  `idRol` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`idRol`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

INSERT INTO `rol` VALUES (1,'administrador'),(2,'administrativo'),(3,'coordinador'),(4,'contador'),(5,'cliente');

CREATE TABLE `login` (
  `idLogin` int(11) NOT NULL AUTO_INCREMENT,
  `usuario` varchar(45) NOT NULL,
  `contrasena` varchar(45) NOT NULL,
  PRIMARY KEY (`idLogin`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

INSERT INTO `login` VALUES (1,'sol','sol123'),(2,'lizz','liz123'),(3,'Mica','mica123');


CREATE TABLE `administrativo` (
  `idAdministrativo` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `idLogin` int(11) NOT NULL,
  `idRol` int(11) NOT NULL,  
  
  PRIMARY KEY (`idAdministrativo`),
  FOREIGN KEY (`idLogin`) REFERENCES login(`idLogin`),
  FOREIGN KEY (`idRol`) REFERENCES rol(`idRol`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

INSERT INTO `administrativo` VALUES (1,'solAdministrativa',1,2),(2,'LizzAdministrativa',2,2),(3,'MicaAdministrativa',3,2);


CREATE TABLE `ciudad` (
  `idCiudad` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`idCiudad`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

INSERT INTO `ciudad` VALUES (1,'Buenos Aires'),(2,'Catamarca'),(3,'Chaco'),(4,'Chubut'),(5,'Corrientes'),(6,'Formosa'),(7,'Jujuy'),(8,'Mendoza');

CREATE TABLE `mediocontacto` (
  `idMedioContacto` int(11) NOT NULL AUTO_INCREMENT,
  `numeroFijo` varchar(45) NOT NULL,
  `numeroCelular` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`idMedioContacto`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE `cliente` (
  `idCliente` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `dni` varchar(8) NOT NULL,
  `fechaNacimiento` date,
  `idMedioContacto` int(11) NOT NULL,
  PRIMARY KEY (`idCliente`),
  FOREIGN KEY (`idMedioContacto`) REFERENCES mediocontacto(`idMedioContacto`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `mediocontacto` VALUES (1,'44514236','1532691249','lizzmoreno@gmail.com'),(2,'46649865','1546823599','perez.mica@gmail.com'),(3,'44329865','1523234598','solhoyos@hotmail.com'),(4,'44513295','1546853265','avila_nico@yahoo.com'),(5,'44661634','1598564571','juan.p@gmail.com'),(6,'44513269','1562773216','pepito.lopez@hotmail.com'),(7,'44519723','1565379812','legrand_mirta@yahoo.com.ar');
INSERT INTO `cliente` VALUES (1,'Lizz','Moreno','36584266','1996-05-08',1),(2,'Mica','Perez','32125322','1995-04-12',2),(3,'Sol','Hoyos','25652544','1996-03-06',3),(4,'Nico','Avila','39568922','1994-05-08',4),(5,'Juan','Perez','2563366','1994-06-04',5),(6,'Pepito','Lopez','32556988','1995-12-06',6),(7,'Mirta','Legrand','00000001','1994-09-07',7);


CREATE TABLE `estadospasaje` (
  `idEstadoPasaje` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` int(11) NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`idEstadoPasaje`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE `horario` (
  `idHorario` int(11) NOT NULL AUTO_INCREMENT,
  `hora` varchar(10) NOT NULL,
  PRIMARY KEY (`idHorario`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

INSERT INTO `horario` VALUES (1,'1:00'),(2,'2:00'),(3,'3:00'),(4,'4:00'),(5,'4:00'),(6,'5:00'),(7,'6:00'),(8,'7:00'),(9,'8:00'),(10,'9:00'),(11,'10:00'),(12,'11:00'),(13,'12:00');

CREATE TABLE `pago` (
  `idPago` int(11) NOT NULL AUTO_INCREMENT,
  `monto` decimal(11,0) NOT NULL,
  `fechaPago` date NOT NULL,
  PRIMARY KEY (`idPago`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

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
  FOREIGN KEY (`idCliente`) REFERENCES cliente(`idCliente`),
  FOREIGN KEY (`idViaje`) REFERENCES viaje(`idViaje`),
  FOREIGN KEY (`idTransporte`) REFERENCES transporte(`idTransporte`),
  FOREIGN KEY (`idAdministrativo`) REFERENCES administrativo(`idAdministrativo`),
  FOREIGN KEY (`idPago`) REFERENCES pago(`idPago`),
  FOREIGN KEY (`idEstadoPasaje`) REFERENCES estadospasaje(`idEstadoPasaje`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;


CREATE TABLE `pasajes_pasajeros` (
  `idPasajePasajero` int(11) NOT NULL AUTO_INCREMENT,
  `idPasaje` int(11) NOT NULL,
  `idCliente` int(11) NOT NULL,
  PRIMARY KEY (`idPasajePasajero`),
  FOREIGN KEY (`idPasaje`) REFERENCES pasaje(`idPasaje`),
  FOREIGN KEY (`idCliente`) REFERENCES cliente(`idCliente`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE `transporte` (
  `idTransporte` int(11) NOT NULL AUTO_INCREMENT,
  `capacidad` int(11) NOT NULL,
  `nombreTransporte` varchar(45) NOT NULL,
  `precioBase` decimal(11,0) NOT NULL,
  PRIMARY KEY (`idTransporte`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

INSERT INTO `transporte` VALUES (1,300,'avion',1000),(2,100,'Micro',400),(3,2000,'Buquebus',20000);

CREATE TABLE `viaje` (
  `idViaje` int(11) NOT NULL AUTO_INCREMENT,
  `fechaSalida` date NOT NULL,
  `fechaLlegada` date NOT NULL,
  `precio` decimal(11,0) NOT NULL,
  `idCiudadOrigen` int(11) NOT NULL,
  `idCiudadDestino` int(11) NOT NULL,
  `horaSalida` varchar(255) DEFAULT NULL,
  `idTransporte` int(11) NOT NULL,
  PRIMARY KEY (`idViaje`),
  FOREIGN KEY (`idCiudadOrigen`) REFERENCES ciudad(`idCiudadOrigen`),
  FOREIGN KEY (`idCiudadDestino`) REFERENCES ciudad(`idCiudadDestino`),
  FOREIGN KEY (`idTransporte`) REFERENCES transporte(`idTransporte`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

INSERT INTO `viaje` VALUES (1,'2019-05-01','2019-05-02',200,1,5,'1:00',1),(2,'2019-05-04','2019-05-16',300,3,7,'3:00',2),(3,'2019-05-16','2019-05-28',500,5,6,'7:00',1),(4,'2019-05-29','2019-05-30',1200,1,7,'8:00',3);
