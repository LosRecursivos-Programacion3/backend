-- 1) Tabla base Usuario
DROP TABLE IF EXISTS Usuario;
CREATE TABLE IF NOT EXISTS Usuario (
  idUsuario INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100)     NOT NULL,
  password VARCHAR(255)   NOT NULL,
  activo BOOLEAN          NOT NULL DEFAULT TRUE,
  fechaRegistro DATETIME  NOT NULL,
  email VARCHAR(100)      NOT NULL UNIQUE
);

-- 2) Tabla Administrador (1:1 con Usuario)
DROP TABLE IF EXISTS Administrador;
CREATE TABLE IF NOT EXISTS Administrador (
  idUsuario INT PRIMARY KEY,
  FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario)
);

-- 3) Tabla Alumno (1:1 con Usuario)
DROP TABLE IF EXISTS Alumno;
CREATE TABLE IF NOT EXISTS Alumno (
  idUsuario INT PRIMARY KEY,
  edad INT,
  carrera VARCHAR(100),
  fotoPerfil VARCHAR(255),
  ubicacion VARCHAR(255),
  biografia TEXT,
  activo BOOLEAN NOT NULL DEFAULT TRUE,
  FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario)
);

-- 4) Tabla Interes
DROP TABLE IF EXISTS Interes;
CREATE TABLE IF NOT EXISTS Interes (
  idInteres INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  activo BOOLEAN  NOT NULL DEFAULT TRUE,
  descripcion VARCHAR(255)
);

-- 4.1) Tabla Alumno_Interes
DROP TABLE IF EXISTS Alumno_Interes;
CREATE TABLE IF NOT EXISTS Alumno_Interes (
  idUsuario INT NOT NULL,
  idInteres INT NOT NULL,
  activo BOOLEAN  NOT NULL DEFAULT TRUE,
  PRIMARY KEY (idUsuario, idInteres),
  FOREIGN KEY (idUsuario) REFERENCES Alumno(idUsuario),
  FOREIGN KEY (idInteres) REFERENCES Interes(idInteres)
);

-- 5) Tabla Evento
DROP TABLE IF EXISTS Evento;
CREATE TABLE IF NOT EXISTS Evento (
  idEvento INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(200) NOT NULL,
  descripcion TEXT,
  fecha DATETIME NOT NULL,
  ubicacion VARCHAR(255),
  activo BOOLEAN NOT NULL DEFAULT TRUE,
  creador_id INT NOT NULL,
  FOREIGN KEY (creador_id) REFERENCES Alumno(idUsuario)
);

-- 5.1) Tabla Evento_Participante
DROP TABLE IF EXISTS Evento_Participante;
CREATE TABLE IF NOT EXISTS Evento_Participante (
  idEvento INT NOT NULL,
  idUsuario INT NOT NULL,
  activo BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (idEvento, idUsuario),
  FOREIGN KEY (idEvento) REFERENCES Evento(idEvento),
  FOREIGN KEY (idUsuario) REFERENCES Alumno(idUsuario)
);

-- 6) Tabla Post
DROP TABLE IF EXISTS Post;
CREATE TABLE IF NOT EXISTS Post (
  idPost INT AUTO_INCREMENT PRIMARY KEY,
  contenido TEXT,
  fecha DATETIME NOT NULL,
  activo BOOLEAN NOT NULL DEFAULT TRUE,
  autor_id INT NOT NULL,
  FOREIGN KEY (autor_id) REFERENCES Alumno(idUsuario)
);

-- 6.1) Tabla Comentario
DROP TABLE IF EXISTS Comentario;
CREATE TABLE IF NOT EXISTS Comentario (
  idComentario INT AUTO_INCREMENT PRIMARY KEY,
  contenido TEXT NOT NULL,
  fechaComentario DATETIME NOT NULL,
  activo BOOLEAN NOT NULL DEFAULT TRUE,
  autor_id INT NOT NULL,
  post_id INT NOT NULL,
  FOREIGN KEY (autor_id) REFERENCES Alumno(idUsuario),
  FOREIGN KEY (post_id) REFERENCES Post(idPost)
);

-- 7) Tabla Mensaje
DROP TABLE IF EXISTS Mensaje;
CREATE TABLE IF NOT EXISTS Mensaje (
  idMensaje INT AUTO_INCREMENT PRIMARY KEY,
  contenido TEXT NOT NULL,
  fechaEnvio DATETIME NOT NULL,
  activo BOOLEAN NOT NULL DEFAULT TRUE,
  emisor_id INT NOT NULL,
  receptor_id INT NOT NULL,
  FOREIGN KEY (emisor_id)   REFERENCES Alumno(idUsuario),
  FOREIGN KEY (receptor_id) REFERENCES Alumno(idUsuario)
);

-- 8) Tabla Interaccion
DROP TABLE IF EXISTS Interaccion;
CREATE TABLE IF NOT EXISTS Interaccion (
  idInteraccion INT AUTO_INCREMENT PRIMARY KEY,
  alumnoUno_id INT NOT NULL,
  alumnoDos_id INT NOT NULL,
  activo BOOLEAN NOT NULL DEFAULT TRUE,
  tipo VARCHAR(50),
  fecha DATETIME NOT NULL,
  FOREIGN KEY (alumnoUno_id) REFERENCES Alumno(idUsuario),
  FOREIGN KEY (alumnoDos_id) REFERENCES Alumno(idUsuario)
);

-- 9) Tabla Reaccion
DROP TABLE IF EXISTS Reaccion;
CREATE TABLE IF NOT EXISTS Reaccion (
  idReaccion INT AUTO_INCREMENT PRIMARY KEY,
  tipoReaccion ENUM('LIKE','DISLIKE','LOVE','JAJA') NOT NULL,
  usuario_id INT NOT NULL,
  post_id INT,
  comentario_id INT,
  evento_id INT,
  activo BOOLEAN NOT NULL DEFAULT TRUE,
  fecha DATETIME NOT NULL,
  FOREIGN KEY (usuario_id)    REFERENCES Alumno(idUsuario),
  FOREIGN KEY (post_id)       REFERENCES Post(idPost),
  FOREIGN KEY (comentario_id) REFERENCES Comentario(idComentario),
  FOREIGN KEY (evento_id)     REFERENCES Evento(idEvento)
);

-- 10) Tabla Story 
DROP TABLE IF EXISTS Story;
CREATE TABLE IF NOT EXISTS Story (
  idStory INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT NOT NULL,
  urlContenido VARCHAR(500) NOT NULL,
  tipo ENUM('IMAGEN','VIDEO') NOT NULL,
  activo BOOLEAN NOT NULL DEFAULT TRUE,
  fechaCreacion DATETIME NOT NULL,
  FOREIGN KEY (usuario_id) REFERENCES Alumno(idUsuario)
);

  -- 11.1) Tabla Story_Visualizacion (Gestiona quién puede ver cada story)
DROP TABLE IF EXISTS Story_Visualizacion;
CREATE TABLE IF NOT EXISTS Story_Visualizacion (
  idStory INT NOT NULL,
  idUsuario INT NOT NULL,
  activo BOOLEAN NOT NULL DEFAULT TRUE,
  fechaDATETIME NOT NULL,
  PRIMARY KEY (idStory, idUsuario),
  FOREIGN KEY (idStory) REFERENCES Story(idStory),
  FOREIGN KEY (idUsuario) REFERENCES Alumno(idUsuario)
);

-- 11) Tabla Notificacion
DROP TABLE IF EXISTS Notificacion;
CREATE TABLE IF NOT EXISTS Notificacion (
  idNotificacion INT AUTO_INCREMENT PRIMARY KEY,
  usuario_id INT NOT NULL,
  mensaje TEXT NOT NULL,
  tipo VARCHAR(50) NOT NULL,
  fecha DATETIME NOT NULL,
  activo BOOLEAN NOT NULL DEFAULT TRUE,
  visto BOOLEAN NOT NULL DEFAULT FALSE,
  FOREIGN KEY (usuario_id) REFERENCES Usuario(idUsuario)
);

