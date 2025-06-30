SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = 'agenda-contactos-web';

DROP DATABASE IF EXISTS "agenda-contactos-web";
CREATE DATABASE "agenda-contactos-web";
\c "agenda-contactos-web"

CREATE TABLE cac01_tipo_medio_contacto (
  id_tipo int4 NOT NULL,
  tx_nombre varchar(50) NOT NULL,
  tx_descripcion varchar(255) NOT NULL,
  st_activo bool NOT NULL,
  PRIMARY KEY (id_tipo)
);

CREATE TABLE tac01_usuario (
  id_usuario SERIAL NOT NULL,
  tx_nombre varchar(100) NOT NULL,
  tx_primer_apellido varchar(100),
  tx_segundo_apellido varchar(100),
  tx_login varchar(50) NOT NULL,
  tx_password varchar(256) NOT NULL,
  PRIMARY KEY (id_usuario)
);

CREATE TABLE tac02_contacto (
  id_contacto SERIAL NOT NULL,
  id_usuario int4 NOT NULL,
  tx_nombre varchar(100) NOT NULL,
  tx_primer_apellido varchar(100) NOT NULL,
  tx_segundo_apellido varchar(100),
  fh_nacimiento date,
  PRIMARY KEY (id_contacto),
  CONSTRAINT FKtac02_cont967501 FOREIGN KEY (id_usuario)
    REFERENCES tac01_usuario (id_usuario)
);

CREATE TABLE tac03_medio_contacto (
  id_medio_contacto SERIAL NOT NULL,
  id_contacto int4 NOT NULL,
  id_tipo int4 NOT NULL,
  tx_valor varchar(100) NOT NULL,
  PRIMARY KEY (id_medio_contacto),
  CONSTRAINT FKtac03_medi24601 FOREIGN KEY (id_contacto)
    REFERENCES tac02_contacto (id_contacto),
  CONSTRAINT FKtac03_medi174907 FOREIGN KEY (id_tipo)
    REFERENCES cac01_tipo_medio_contacto (id_tipo)
);

