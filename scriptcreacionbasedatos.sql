create database ejercicioneoris;

SET FOREIGN_KEY_CHECKS=1;

use  ejercicioneoris;

DROP TABLE IF EXISTS `catalogoidentificacion`;

CREATE TABLE `catalogoidentificacion` (
`id_tipoidenficacion` int(10) NOT NULL AUTO_INCREMENT,
`descripcion` varchar(50) NOT NULL,
PRIMARY KEY(`id_tipoidenficacion`)
)  ENGINE=INNODB AUTO_INCREMENT=20 DEFAULT CHARSET=LATIN1;

DROP TABLE IF EXISTS `persona`;

CREATE TABLE `persona` (
  `id` int(10)  NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `genero` varchar(45) NOT NULL,
  `edad` int(10) unsigned NOT NULL,
  `identificacion` int(10) NOT NULL,
  `direccion` varchar(45) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  foreign key(identificacion) 
      references `catalogoidentificacion`(id_tipoidenficacion) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;



DROP TABLE IF EXISTS `cliente`;

CREATE TABLE `cliente` (
  `IdCliente` int(10)  NOT NULL AUTO_INCREMENT,
  `contrasena` varchar(45) NOT NULL,
  `estado` boolean default true,
  `IdPersona` int(10) DEFAULT NULL,
  PRIMARY KEY (`idCliente`),
  FOREIGN KEY (IdPersona)
      REFERENCES persona(id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=519 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `cuenta`;

CREATE TABLE `cuenta` (
  `NumeroCuenta` int(10) NOT NULL AUTO_INCREMENT,
  `tipocuenta` varchar(45)  NULL,
  `saldoinicial` decimal(10,2) NULL,
  `estado` boolean default true,
  `id_cliente` int(10) NOT NULL,
  UNIQUE `unique_index`(`tipocuenta`, `id_cliente`),
  PRIMARY KEY (`NumeroCuenta`),
  FOREIGN KEY (id_cliente)
      REFERENCES cliente(IdCliente) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `movimientos`;

CREATE TABLE `movimientos` (
    `IdMovimiento` INT(10) NOT NULL AUTO_INCREMENT,
    `fecha` DATETIME NULL,/*DEFAULT CURRENT_TIMESTAMP,*/
    `tipomovimiento` VARCHAR(45) NOT NULL,
    `valor` DECIMAL(10,2)  DEFAULT NULL,
    `saldo` DECIMAL(10,2) DEFAULT NULL,
    `idCuenta` INT(10)  NOT NULL,
    PRIMARY KEY (`IdMovimiento`),
    FOREIGN KEY (idCuenta)
        REFERENCES cuenta (NumeroCuenta) on delete cascade
)  ENGINE=INNODB DEFAULT CHARSET=LATIN1;


insert into ejercicioneoris.catalogoidentificacion (descripcion ) values('IFE');
insert into ejercicioneoris.catalogoidentificacion (descripcion ) values('PASAPORTE');
insert into ejercicioneoris.catalogoidentificacion (descripcion ) values('LICENCIA');

