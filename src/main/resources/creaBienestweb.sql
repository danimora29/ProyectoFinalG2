drop database if exists bienestweb;
drop user if exists 'usuario_proyecto';

CREATE database bienestweb
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

create user 'usuario_proyecto'@'%' identified by 'proyecto_Clave.';

GRANT ALL PRIVILEGES ON bienestweb.* TO 'usuario_proyecto'@'%' WITH GRANT OPTION;
flush privileges;

use bienestweb;

CREATE TABLE doctor (
    id_doctor INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    experiencia INT,
    especialidad VARCHAR(100) NOT NULL,
    tarifa double,
    disponibilidad INT,
    estado INT,
    contacto VARCHAR(100),
    horario VARCHAR(100),
    descripcion TEXT,
    pacientes varchar(100),
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
    ruta_imagen VARCHAR(1024),
    precio DECIMAL(10,2) NULL
);

CREATE TABLE alimentacion (
    id_plan INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    tipo VARCHAR(50),
    calorias INT,
    descripcion TEXT,
    ruta_imagen varchar(1024),
    precio DECIMAL(10,2) NULL
);

CREATE TABLE contacto (
    id_contacto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    correo VARCHAR(100),
    mensaje TEXT,
    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO doctor 
    (nombre, experiencia, especialidad, tarifa, disponibilidad, estado, contacto, horario, descripcion, pacientes, telefono, correo, ruta_imagen, activo) VALUES
    ('Dra. Laura Gómez', 8, 'Nutrición', 30250.00, 20, 1, 'Ext. 101', 'L-V 8:00-16:00', 'Especialista en dietas personalizadas y nutrición deportiva.', '150', '8888-1234', 'lgomez@bienestweb.com', 'img/doctores/laura.jpg', TRUE),
    ('Dr. Carlos Pérez', 12, 'Cardiología', 41525.00, 15, 1, 'Ext. 102', 'M-J 9:00-17:00', 'Experto en arritmias y rehabilitación cardíaca.', '100', '8888-5678', 'cperez@bienestweb.com', 'img/doctores/carlos.jpg', TRUE),
    ('Dra. Ana Torres', 5, 'Dermatología', 24750.00, 25, 1, 'Ext. 103', 'L-S 10:00-14:00', 'Especializada en acné y tratamientos estéticos de la piel.', '200', '8888-9012', 'atorres@bienestweb.com', 'img/doctores/ana.jpg', TRUE),
    ('Dr. Ricardo Marín', 15, 'Psicología', 35750.00, 18, 1, 'Ext. 104', 'M-V 14:00-18:00', 'Terapeuta cognitivo-conductual, manejo de ansiedad.', '80', '8888-3456', 'rmarin@bienestweb.com', 'img/doctores/ricardo.jpg', TRUE),
    ('Dra. Sofía Castro', 10, 'Fisioterapia', 27500.00, 30, 1, 'Ext. 105', 'L-V 7:00-15:00', 'Rehabilitación post-lesiones deportivas y dolor crónico.', '180', '8888-7890', 'scastro@bienestweb.com', 'img/doctores/sofia.jpg', TRUE);

INSERT INTO libro 
    (titulo, autor, descripcion, precio, ruta_imagen, activo) VALUES
    ('Cómo hacer tu día mejor', 'María Fernández', 'Guía práctica para el bienestar diario.', 5494.50, 'img/libros/dia_mejor.jpg', TRUE),
    ('Vive Saludable', 'José Ramírez', 'Consejos para mejorar tu salud y felicidad.', 6875.00, 'img/libros/vive_saludable.jpg', TRUE),
    ('El poder del ahora', 'Eckhart Tolle', 'Una guía para la iluminación espiritual.', 8662.50, 'img/libros/poder_ahora.jpg', TRUE),
    ('Hábitos Atómicos', 'James Clear', 'Cambios pequeños, resultados notables.', 9900.00, 'img/libros/habitos_atomicos.jpg', FALSE), 
    ('La dieta de la felicidad', 'Elena Santos', 'Nutrición para mejorar el estado de ánimo.', 6160.00, 'img/libros/dieta_felicidad.jpg', TRUE);

INSERT INTO ejercicio 
    (nombre, duracion, nivel, ruta_imagen, precio) VALUES
    ('Caminata rápida', 30, 'Bajo', 'img/ejercicios/caminata.jpg', 2750.00),
    ('Ciclismo', 45, 'Medio', 'img/ejercicios/ciclismo.jpg', 4675.00),
    ('Running', 60, 'Alto', 'img/ejercicios/running.jpg', 6600.00),
    ('Salto de Cuerda', 20, 'Medio', 'img/ejercicios/salto_cuerda.jpg', 3300.00),
    ('Elíptica', 40, 'Medio', 'img/ejercicios/eliptica.jpg', 5087.50);

INSERT INTO alimentacion 
    (nombre, tipo, calorias, descripcion, ruta_imagen, precio) VALUES
    ('Plan Vegetariano', 'Vegetariana', 1800, 'Ideal para una dieta balanceada sin carne y rica en nutrientes.', 'img/alimentacion/vegetariano.jpg', 14294.50),
    ('Plan Proteico', 'Alta en proteínas', 2000, 'Perfecto para quienes buscan ganancia muscular y hacen ejercicio intenso.', 'img/alimentacion/proteico.jpg', 19525.00),
    ('Plan Mediterráneo', 'Balanceada', 1600, 'Dieta basada en verduras, aceite de oliva y pescado, excelente para el corazón.', 'img/alimentacion/mediterraneo.jpg', 16494.50),
    ('Dieta Keto Rápida', 'Baja en carbohidratos', 1500, 'Régimen estricto para inducir la cetosis y quemar grasa rápidamente.', 'img/alimentacion/keto.jpg', 22000.00),
    ('Desintoxicación (Detox)', 'Líquidos y jugos', 1200, 'Plan corto a base de jugos y batidos para limpiar el sistema digestivo.', 'img/alimentacion/detox.jpg', 10994.50);

CREATE TABLE usuario (
  id_usuario INT AUTO_INCREMENT PRIMARY KEY,
  correo VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  tipo VARCHAR(20),  -- "ADMIN", "DOCTOR", "USER"
  activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE usuario_rol (
  id_usuario INT,
  id_rol INT,
  PRIMARY KEY(id_usuario, id_rol),
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
  FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);

CREATE TABLE ruta (
    id_ruta INT AUTO_INCREMENT PRIMARY KEY,
    ruta VARCHAR(255) NOT NULL,
    requiere_rol BOOLEAN NOT NULL DEFAULT TRUE,
    id_rol INT NULL,
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);

INSERT INTO rol(nombre) VALUES ('ROLE_ADMIN');
INSERT INTO rol(nombre) VALUES ('ROLE_DOCTOR');
INSERT INTO rol(nombre) VALUES ('ROLE_USER');


INSERT INTO usuario (correo, password, tipo, activo) VALUES 
('admin@bienestweb.com', 'admin123', 'ADMIN', true);

INSERT INTO usuario (correo, password, tipo, activo) VALUES 
('doctor@bienestweb.com', 'doctor123', 'DOCTOR', true);

INSERT INTO usuario (correo, password, tipo, activo) VALUES 
('usuario@bienestweb.com', 'usuario123', 'USER', true);

INSERT INTO usuario_rol (id_usuario, id_rol) SELECT u.id_usuario, r.id_rol FROM usuario u, rol r WHERE u.correo = 'admin@bienestweb.com' AND r.nombre = 'ROLE_ADMIN';
INSERT INTO usuario_rol (id_usuario, id_rol) SELECT u.id_usuario, r.id_rol FROM usuario u, rol r WHERE u.correo = 'doctor@bienestweb.com' AND r.nombre = 'ROLE_DOCTOR';
INSERT INTO usuario_rol (id_usuario, id_rol) SELECT u.id_usuario, r.id_rol FROM usuario u, rol r WHERE u.correo = 'usuario@bienestweb.com' AND r.nombre = 'ROLE_USER';

INSERT INTO ruta (ruta, requiere_rol, id_rol) VALUES ('/', 0, null);
INSERT INTO ruta (ruta, requiere_rol, id_rol) VALUES ('/index', 0, null);
INSERT INTO ruta (ruta, requiere_rol, id_rol) VALUES ('/login', 0, null);
INSERT INTO ruta (ruta, requiere_rol, id_rol) VALUES ('/registro/**', 0, null);
INSERT INTO ruta (ruta, requiere_rol, id_rol) VALUES ('/js/**', 0, null);
INSERT INTO ruta (ruta, requiere_rol, id_rol) VALUES ('/css/**', 0, null);
INSERT INTO ruta (ruta, requiere_rol, id_rol) VALUES ('/img/**', 0, null);
INSERT INTO ruta (ruta, requiere_rol, id_rol) VALUES ('/webjars/**', 0, null);
INSERT INTO ruta (ruta, requiere_rol, id_rol) VALUES ('/admin/**', 1, 1);
INSERT INTO ruta (ruta, requiere_rol, id_rol) VALUES ('/doctor/**', 1, 2);
INSERT INTO ruta (ruta, requiere_rol, id_rol) VALUES ('/usuario/**', 1, 3);