CREATE database IF NOT EXISTS enfermeria;

USE enfermeria;

DROP TABLE IF EXISTS grupos;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS permisos;
DROP TABLE IF EXISTS grupos_permisos;
DROP TABLE IF EXISTS usuarios_permisos;
DROP TABLE IF EXISTS asignaturas;
DROP TABLE IF EXISTS seminarios_asignaturas;
DROP TABLE IF EXISTS rubricas;
DROP TABLE IF EXISTS grupos_criterios_rubricas;
DROP TABLE IF EXISTS criterios_rubricas;
DROP TABLE IF EXISTS profesores_asociados;
DROP TABLE IF EXISTS portafolios;
DROP TABLE IF EXISTS estancias_unidad_clinica;
DROP TABLE IF EXISTS seminarios_realizados;
DROP TABLE IF EXISTS casos_clinicos;
DROP TABLE IF EXISTS trabajos_de_campo_info;
DROP TABLE IF EXISTS trabajos_de_campo;
DROP TABLE IF EXISTS diarios_reflexivos;
DROP TABLE IF EXISTS anexos;
DROP TABLE IF EXISTS puntuacion_criterios;
DROP TABLE IF EXISTS errores_log;



CREATE TABLE IF NOT EXISTS grupos (
	id_grupo INT(3) auto_increment,  
	nombre VARCHAR(100) NOT NULL,
	descripcion TEXT,
	PRIMARY KEY (id_grupo)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;



CREATE TABLE IF NOT EXISTS usuarios (
	id_usuario INT(10) NOT NULL auto_increment,
	id_grupo INT(3)  NOT NULL,
	correo VARCHAR(254) NOT NULL UNIQUE,
	contrasenya VARCHAR(50) NOT NULL,
	nombre VARCHAR(100) NOT NULL,
	apellido1 VARCHAR(100) NOT NULL,
	apellido2 VARCHAR(100),
	dni VARCHAR(10) NOT NULL UNIQUE,
	telefono VARCHAR(20),
	foto LONGBLOB, 
	PRIMARY KEY (id_usuario),
	FOREIGN KEY (id_grupo)
		REFERENCES grupos(id_grupo) 
		ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;



CREATE TABLE IF NOT EXISTS permisos (
	id_permiso INT(10) NOT NULL auto_increment,  
	nombre VARCHAR(100) NOT NULL,
	descripcion TEXT,
	PRIMARY KEY (id_permiso)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;



CREATE TABLE IF NOT EXISTS usuarios_permisos (
	id_usuario INT(10) NOT NULL,
	id_permiso INT(10) NOT NULL,
	PRIMARY KEY(id_permiso,id_usuario),
	FOREIGN KEY (id_permiso)
		REFERENCES permisos(id_permiso)
		ON DELETE CASCADE,
	FOREIGN KEY (id_usuario)
		REFERENCES usuarios(id_usuario)
		ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;



CREATE TABLE IF NOT EXISTS grupos_permisos (
	id_grupo INT(3) NOT NULL,
	id_permiso INT(10) NOT NULL,
	PRIMARY KEY(id_permiso,id_grupo),
	FOREIGN KEY (id_permiso)
		REFERENCES permisos(id_permiso)
		ON DELETE CASCADE,
	FOREIGN KEY (id_grupo)
		REFERENCES grupos(id_grupo)
		ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;



CREATE TABLE IF NOT EXISTS asignaturas (
	id_asignatura INT(3)  auto_increment,
	nombre VARCHAR(100) UNIQUE NOT NULL,
	codigo VARCHAR(10) NOT NULL,
	curso INT(1),
	descripcion TEXT,
	PRIMARY KEY (id_asignatura)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;



CREATE TABLE IF NOT EXISTS seminarios_asignaturas (
	id_seminario INT(4) auto_increment,
	id_asignatura INT(3) NOT NULL,
	nombre VARCHAR(100) NOT NULL,
	codigo VARCHAR(10) UNIQUE NOT NULL,
	descripcion TEXT,
	PRIMARY KEY (id_seminario),
	FOREIGN KEY (id_asignatura)
		REFERENCES asignaturas(id_asignatura)
		ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;



CREATE TABLE IF NOT EXISTS rubricas (
	id_asignatura INT(3) NOT NULL,
	competencias TEXT NOT NULL,
	numero_criterios INT(3) NOT NULL,
	anexo VARCHAR(100) NOT NULL,
	PRIMARY KEY (id_asignatura),
	FOREIGN KEY (id_asignatura)
		REFERENCES asignaturas(id_asignatura)
		ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;



CREATE TABLE IF NOT EXISTS grupos_criterios_rubricas (
	id_grupo_criterio INT(4) auto_increment,
	id_asignatura INT(3) NOT NULL,
	nombre VARCHAR(100) NOT NULL,
	tipo ENUM('NOTA','TEXTO'),
	PRIMARY KEY (id_grupo_criterio),
	FOREIGN KEY (id_asignatura)
		REFERENCES asignaturas(id_asignatura)
		ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;



CREATE TABLE IF NOT EXISTS criterios_rubricas (
	id_criterio INT(5) auto_increment,
	id_asignatura INT(3) NOT NULL,
	id_grupo_criterio INT(4) NOT NULL,
	nombre TEXT NOT NULL,
	PRIMARY KEY (id_criterio),
	FOREIGN KEY (id_asignatura)
		REFERENCES asignaturas(id_asignatura)
		ON DELETE CASCADE,
	FOREIGN KEY (id_grupo_criterio)
		REFERENCES grupos_criterios_rubricas(id_grupo_criterio)
		ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;



CREATE TABLE IF NOT EXISTS profesores_asociados (
	id_profesor INT(10) NOT NULL,
	id_asignatura INT(3) NOT NULL,
	anyo_academico VARCHAR(9) NOT NULL,
	centro_asociado VARCHAR(200) NOT NULL,
	turno VARCHAR(100),
	PRIMARY KEY(id_profesor, id_asignatura, anyo_academico),
	FOREIGN KEY (id_profesor)
		REFERENCES usuarios(id_usuario)
		ON DELETE CASCADE,
	FOREIGN KEY (id_asignatura)
		REFERENCES asignaturas(id_asignatura)
		ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;



CREATE TABLE IF NOT EXISTS portafolios (
	id_portafolio INT(50) auto_increment,
	id_alumno INT(10) NOT NULL,
	id_profesor INT(10) NOT NULL,
	id_asignatura INT(3) NOT NULL,
	anyo_academico VARCHAR(9) NOT NULL,
	PRIMARY KEY (id_portafolio),
	FOREIGN KEY (id_alumno)
		REFERENCES usuarios(id_usuario)
		ON DELETE CASCADE,
	FOREIGN KEY (id_profesor)
		REFERENCES profesores_asociados(id_profesor)
		ON DELETE CASCADE,
	FOREIGN KEY (id_asignatura)
		REFERENCES asignaturas(id_asignatura)
		ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;



CREATE TABLE IF NOT EXISTS estancias_unidad_clinica (
	id_estancia_unidad INT(10) NOT NULL auto_increment,
	id_portafolio INT(50) NOT NULL,
	centro_asociado VARCHAR(200) NOT NULL,
	unidad_clinica VARCHAR(200) NOT NULL,
	turno VARCHAR(100),
	fecha_inicio DATE,
	fecha_fin DATE,
	PRIMARY KEY(id_estancia_unidad, id_portafolio),
	FOREIGN KEY (id_portafolio)
		REFERENCES portafolios(id_portafolio)
		ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;


CREATE TABLE IF NOT EXISTS trabajos_de_campo_info (	
	id_trabajo_info INT(50) NOT NULL auto_increment,
	nombre VARCHAR(100) NOT NULL,
	enunciado LONGBLOB,
	nombre_archivo VARCHAR(100),
	descripcion TEXT,
	PRIMARY KEY(id_trabajo_info)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;


CREATE TABLE IF NOT EXISTS trabajos_de_campo (	
	id_trabajo_de_campo INT(10) NOT NULL auto_increment,
	id_portafolio INT(50) NOT NULL,
	id_trabajo_info INT(50) NOT NULL,
	nombre_trabajo VARCHAR(100),
	trabajo_de_campo LONGBLOB,
	nombre_correccion VARCHAR(100),
	correccion_trabajo LONGBLOB,
	fecha_limite DATETIME,
	PRIMARY KEY(id_trabajo_de_campo, id_portafolio),
	FOREIGN KEY (id_portafolio)
		REFERENCES portafolios(id_portafolio)
		ON DELETE CASCADE,
	FOREIGN KEY (id_trabajo_info)
		REFERENCES trabajos_de_campo_info(id_trabajo_info)
		ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;



CREATE TABLE IF NOT EXISTS seminarios_realizados (
	id_portafolio INT(50) NOT NULL,
	id_seminario INT(4) NOT NULL,
	PRIMARY KEY(id_portafolio, id_seminario),
	FOREIGN KEY (id_portafolio)
		REFERENCES portafolios(id_portafolio)
		ON DELETE CASCADE,
	FOREIGN KEY (id_seminario)
		REFERENCES seminarios_asignaturas(id_seminario)
		ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;



CREATE TABLE IF NOT EXISTS diarios_reflexivos (
	id_diario_reflexivo INT(10) NOT NULL auto_increment,
	id_portafolio INT(50) NOT NULL,
	nombre VARCHAR(100) NOT NULL,
	diario_reflexivo LONGBLOB,
	fecha_subida DATETIME,
	PRIMARY KEY(id_diario_reflexivo, id_portafolio),
	FOREIGN KEY (id_portafolio)
		REFERENCES portafolios(id_portafolio)
		ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;



CREATE TABLE IF NOT EXISTS anexos (
	id_anexo INT(10) NOT NULL auto_increment,
	id_portafolio INT(50) NOT NULL,
	nombre VARCHAR(100) NOT NULL,
	anexo LONGBLOB,
	fecha_subida DATETIME,
	PRIMARY KEY(id_anexo, id_portafolio),
	FOREIGN KEY (id_portafolio)
		REFERENCES portafolios(id_portafolio)
		ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;



CREATE TABLE IF NOT EXISTS casos_clinicos (
	id_caso_clinico INT(10) NOT NULL auto_increment,
	id_portafolio INT(50) NOT NULL,
	nombre VARCHAR(100) NOT NULL,
	caso_clinico LONGBLOB,
	fecha_subida DATETIME,
	PRIMARY KEY(id_caso_clinico, id_portafolio),
	FOREIGN KEY (id_portafolio)
		REFERENCES portafolios(id_portafolio)
		ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;



CREATE TABLE IF NOT EXISTS puntuacion_criterios (
	id_portafolio INT(50) NOT NULL,
	id_criterio INT(5) NOT NULL,
	nota TEXT,
	PRIMARY KEY(id_portafolio,id_criterio),
	FOREIGN KEY (id_portafolio) 
		REFERENCES portafolios(id_portafolio)
		ON DELETE CASCADE,
	FOREIGN KEY (id_criterio) 
		REFERENCES criterios_rubricas(id_criterio)
		ON DELETE CASCADE
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;


CREATE TABLE IF NOT EXISTS errores_log (
	id_error INT(10) NOT NULL auto_increment,
	tipo ENUM('error_usuarios','error_profesores', 'error_alumnos', 'anyo_academico') NOT NULL,
	descripcion TEXT,
	fecha DATETIME,
	PRIMARY KEY(id_error)
	
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_spanish_ci;

