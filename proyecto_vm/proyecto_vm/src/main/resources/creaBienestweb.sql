drop database if exists bienestweb;
drop user if exists 'usuario_proyecto';

CREATE database bienestweb
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

create user 'usuario_proyecto'@'%' identified by 'proyecto_Clave.';

GRANT SELECT, INSERT, UPDATE, DELETE ON bienestweb.* TO 'usuario_proyecto'@'%';
flush privileges;

use bienestweb;

CREATE TABLE doctor (
    id_doctor INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100),
    ruta_imagen VARCHAR(1024),
    PRIMARY KEY (id_doctor),
    activo BOOLEAN
);

CREATE TABLE libro (
    id_libro INT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(100),
    descripcion VARCHAR(50),
    precio DECIMAL(10,2),
    ruta_imagen varchar(1024),
    PRIMARY KEY (id_libro),
    activo boolean,
    unique (descripcion)
);

CREATE TABLE ejercicio (
    id_ejercicio INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    duracion INT,
    nivel VARCHAR(50),
    ruta_imagen VARCHAR(1024)
);

CREATE TABLE alimentacion (
    id_plan INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo VARCHAR(50),
    calorias INT,
    descripcion TEXT,
    ruta_imagen varchar(1024)
);

CREATE TABLE contacto (
    id_contacto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    correo VARCHAR(100),
    mensaje TEXT,
    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO doctor (nombre, especialidad, telefono, correo, ruta_imagen) VALUES
('Dra. Laura Gómez', 'Nutrición', '8888-1234', 'lgomez@bienestweb.com', 'img/doctores/laura.jpg'),
('Dr. Carlos Pérez', 'Cardiología', '8888-5678', 'cperez@bienestweb.com', 'img/doctores/carlos.jpg');

INSERT INTO libro (titulo, autor, descripcion, precio, ruta_imagen) VALUES
('Cómo hacer tu día mejor', 'María Fernández', 'Guía práctica para el bienestar diario.', 9.99, 'img/libros/dia_mejor.jpg'),
('Vive Saludable', 'José Ramírez', 'Consejos para mejorar tu salud y felicidad.', 12.50, 'img/libros/vive_saludable.jpg');

INSERT INTO ejercicio (nombre, duracion, nivel, ruta_imagen) VALUES
('Caminata rápida', 30, 'Bajo', 'img/ejercicios/caminata.jpg'),
('Ciclismo', 45, 'Medio', 'img/ejercicios/ciclismo.jpg'),
('Running', 60, 'Alto', 'img/ejercicios/running.jpg');

INSERT INTO alimentacion (nombre, tipo, calorias, descripcion, ruta_imagen) VALUES
('Plan Vegetariano', 'Vegetariana', 1800, 'Ideal para una dieta balanceada sin carne.', 'img/alimentacion/vegetariano.jpg'),
('Plan Proteico', 'Alta en proteínas', 2000, 'Perfecto para quienes hacen ejercicio.', 'img/alimentacion/proteico.jpg');

INSERT INTO contacto (nombre, correo, mensaje) VALUES
('Ana Torres', 'ana.torres@gmail.com', 'Gracias por sus consejos, me encantó la sección de nutrición.');

