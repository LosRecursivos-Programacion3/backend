-- ====================
--  PROCEDURES PARA USUARIO
-- ====================

DROP PROCEDURE IF EXISTS CrearUsuario;
DELIMITER //
CREATE PROCEDURE CrearUsuario (
  IN p_nombre VARCHAR(100),
  IN p_password VARCHAR(255),
  IN p_email VARCHAR(100)
)
BEGIN
  INSERT INTO Usuario (nombre, password, fechaRegistro, email)
  VALUES (p_nombre, p_password, NOW(), p_email);
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS DesactivarUsuario;
DELIMITER //
CREATE PROCEDURE DesactivarUsuario (
  IN p_idUsuario INT
)
BEGIN
  UPDATE Usuario SET activo = FALSE WHERE idUsuario = p_idUsuario;
END;
//
DELIMITER ;

-- ====================
--  PROCEDURES PARA ALUMNO
-- ====================

DROP PROCEDURE IF EXISTS CrearAlumno;
DELIMITER //
CREATE PROCEDURE CrearAlumno (
  IN p_idUsuario INT,
  IN p_edad INT,
  IN p_carrera VARCHAR(100),
  IN p_fotoPerfil VARCHAR(255),
  IN p_ubicacion VARCHAR(255),
  IN p_biografia TEXT
)
BEGIN
  INSERT INTO Alumno (idUsuario, edad, carrera, fotoPerfil, ubicacion, biografia)
  VALUES (p_idUsuario, p_edad, p_carrera, p_fotoPerfil, p_ubicacion, p_biografia);
END;
//
DELIMITER ;

--Desactivar Alumno (cambia activo a FALSE tanto en Alumno como en Usuario)
DROP PROCEDURE IF EXISTS DesactivarAlumno;
DELIMITER //
CREATE PROCEDURE DesactivarAlumno (IN p_idUsuario INT)
BEGIN
  UPDATE Alumno SET activo = FALSE WHERE idUsuario = p_idUsuario;
  UPDATE Usuario SET activo = FALSE WHERE idUsuario = p_idUsuario;
END;
//
DELIMITER ;

-- Actualizar Alumno (nombre, edad, carrera, ubicacion, biografia, fotoPerfil)
DELIMITER //
CREATE PROCEDURE ActualizarAlumno (
  IN p_idUsuario INT,
  IN p_nombre VARCHAR(100),
  IN p_edad INT,
  IN p_carrera VARCHAR(100),
  IN p_ubicacion VARCHAR(255),
  IN p_biografia TEXT,
  IN p_fotoPerfil VARCHAR(255)
)
BEGIN
  -- Actualizar tabla Usuario (nombre)
  UPDATE Usuario SET nombre = p_nombre WHERE idUsuario = p_idUsuario;
  
  -- Actualizar tabla Alumno (edad, carrera, ubicacion, biografia, fotoPerfil)
  UPDATE Alumno 
  SET 
    edad = p_edad,
    carrera = p_carrera,
    ubicacion = p_ubicacion,
    biografia = p_biografia,
    fotoPerfil = p_fotoPerfil
  WHERE idUsuario = p_idUsuario;
END//
DELIMITER ;

--Actualizar biografía del alumno
DROP PROCEDURE IF EXISTS ActualizarBiografiaAlumno;
DELIMITER //
CREATE PROCEDURE ActualizarBiografiaAlumno (
  IN p_idUsuario INT,
  IN p_nuevaBiografia TEXT
)
BEGIN
  UPDATE Alumno
  SET biografia = p_nuevaBiografia
  WHERE idUsuario = p_idUsuario;
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS ActualizarPasswordUsuario;
DELIMITER //
CREATE PROCEDURE ActualizarPasswordUsuario (
  IN p_idUsuario INT,
  IN p_nuevaPassword VARCHAR(255)
)
BEGIN
  UPDATE Usuario
  SET password = p_nuevaPassword
  WHERE idUsuario = p_idUsuario AND activo = TRUE;
END;
//
DELIMITER ;
-- ====================
--  PROCEDURES PARA INTERESES
-- ====================

DROP PROCEDURE IF EXISTS AsignarInteresAAlumno;
DELIMITER //
CREATE PROCEDURE AsignarInteresAAlumno (
  IN p_idUsuario INT,
  IN p_idInteres INT
)
BEGIN
  INSERT INTO Alumno_Interes (idUsuario, idInteres)
  VALUES (p_idUsuario, p_idInteres);
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS DesactivarInteresDeAlumno;
DELIMITER //
CREATE PROCEDURE DesactivarInteresDeAlumno (
  IN p_idUsuario INT,
  IN p_idInteres INT
)
BEGIN
  DELETE FROM Alumno_Interes
  WHERE idUsuario = p_idUsuario AND idInteres = p_idInteres;
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS ListarInteresesDeAlumno;
DELIMITER //
CREATE PROCEDURE ListarInteresesDeAlumno (
  IN p_idUsuario INT
)
BEGIN
  SELECT i.idInteres, i.nombre
  FROM Alumno_Interes ai
  JOIN Interes i ON ai.idInteres = i.idInteres
  WHERE ai.idUsuario = p_idUsuario;
END;
//
DELIMITER ;

-- ====================
--  PROCEDURES PARA EVENTOS
-- ====================

DROP PROCEDURE IF EXISTS CrearEvento;
DELIMITER //
CREATE PROCEDURE CrearEvento (
  IN p_nombre VARCHAR(200),
  IN p_descripcion TEXT,
  IN p_fecha DATETIME,
  IN p_ubicacion VARCHAR(255),
  IN p_creador_id INT
)
BEGIN
  INSERT INTO Evento (nombre, descripcion, fecha, ubicacion, creador_id)
  VALUES (p_nombre, p_descripcion, p_fecha, p_ubicacion, p_creador_id);
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS ParticiparEnEvento;
DELIMITER //
CREATE PROCEDURE ParticiparEnEvento (
  IN p_idEvento INT,
  IN p_idUsuario INT
)
BEGIN
  INSERT INTO Evento_Participante (idEvento, idUsuario)
  VALUES (p_idEvento, p_idUsuario);
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS DesactivarEvento;
DELIMITER //
CREATE PROCEDURE DesactivarEvento (IN p_idEvento INT)
BEGIN
  UPDATE Evento SET activo = FALSE WHERE idEvento = p_idEvento;
END//
DELIMITER ;

DROP PROCEDURE IF EXISTS ListarEventosDeUsuario;
DELIMITER //
CREATE PROCEDURE ListarEventosDeUsuario (
  IN p_idUsuario INT
)
BEGIN
  SELECT e.idEvento, e.nombre, e.descripcion, e.fecha, e.ubicacion
  FROM Evento e
  JOIN Evento_Participante ep ON e.idEvento = ep.idEvento
  WHERE ep.idUsuario = p_idUsuario;
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS ListarParticipantesEvento;
DELIMITER //
CREATE PROCEDURE ListarParticipantesEvento (
  IN p_idEvento INT
)
BEGIN
  SELECT u.idUsuario, u.nombre, u.email
  FROM Evento_Participante ep
  JOIN Usuario u ON ep.idUsuario = u.idUsuario
  WHERE ep.idEvento = p_idEvento;
END;
//
DELIMITER;

-- ====================
--  PROCEDURES PARA POST
-- ====================

DROP PROCEDURE IF EXISTS CrearPost;
DELIMITER //
CREATE PROCEDURE CrearPost (
  IN p_contenido TEXT,
  IN p_autor_id INT
)
BEGIN
  INSERT INTO Post (contenido, fecha, autor_id)
  VALUES (p_contenido, NOW(), p_autor_id);
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS ComentarPost;
DELIMITER //
CREATE PROCEDURE ComentarPost (
  IN p_contenido TEXT,
  IN p_autor_id INT,
  IN p_post_id INT
)
BEGIN
  INSERT INTO Comentario (contenido, fechaComentario, autor_id, post_id)
  VALUES (p_contenido, NOW(), p_autor_id, p_post_id);
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS EliminarComentario;
DELIMITER //
CREATE PROCEDURE EliminarComentario (
  IN p_idComentario INT
)
BEGIN
  UPDATE Comentario SET activo = FALSE WHERE idComentario = p_idComentario;
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS DesactivarPost;
DELIMITER //
CREATE PROCEDURE DesactivarPost (IN p_idPost INT)
BEGIN
  UPDATE Post SET activo = FALSE WHERE idPost = p_idPost;
END//
DELIMITER ;

DROP PROCEDURE IF EXISTS ListarPostsDeUsuario;
DELIMITER //
CREATE PROCEDURE ListarPostsDeUsuario (
  IN p_idUsuario INT
)
BEGIN
  SELECT p.idPost, p.contenido, p.fecha
  FROM Post p
  WHERE p.autor_id = p_idUsuario AND p.activo = TRUE;
END;
//
DELIMITER ;
-- ====================
--  PROCEDURES PARA MENSAJES
-- ====================

DROP PROCEDURE IF EXISTS EnviarMensaje;
DELIMITER //
CREATE PROCEDURE EnviarMensaje (
  IN p_contenido TEXT,
  IN p_emisor_id INT,
  IN p_receptor_id INT
)
BEGIN
  INSERT INTO Mensaje (contenido, fechaEnvio, emisor_id, receptor_id)
  VALUES (p_contenido, NOW(), p_emisor_id, p_receptor_id);
END;
//
DELIMITER ;

-- ====================
--  PROCEDURES PARA INTERACCIONES (Tipo "Match")
-- ====================

DROP PROCEDURE IF EXISTS RegistrarInteraccion;
DELIMITER //
CREATE PROCEDURE RegistrarInteraccion (
  IN p_alumnoUno_id INT,
  IN p_alumnoDos_id INT,
  IN p_tipo VARCHAR(50)
)
BEGIN
  INSERT INTO Interaccion (alumnoUno_id, alumnoDos_id, tipo, fecha)
  VALUES (p_alumnoUno_id, p_alumnoDos_id, p_tipo, NOW());
END;
//
DELIMITER ;

-- ====================
--  PROCEDURES PARA REACCIONES
-- ====================

DROP PROCEDURE IF EXISTS Reaccionar;
DELIMITER //
CREATE PROCEDURE Reaccionar (
  IN p_tipoReaccion ENUM('LIKE','DISLIKE','LOVE','JAJA'),
  IN p_usuario_id INT,
  IN p_post_id INT,
  IN p_comentario_id INT,
  IN p_evento_id INT
)
BEGIN
  INSERT INTO Reaccion (tipoReaccion, usuario_id, post_id, comentario_id, evento_id, fecha)
  VALUES (p_tipoReaccion, p_usuario_id, p_post_id, p_comentario_id, p_evento_id, NOW());
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS DesactivarReaccion;
DELIMITER //
CREATE PROCEDURE DesactivarReaccion (IN p_idReaccion INT)
BEGIN
  UPDATE Reaccion SET activo = FALSE WHERE idReaccion = p_idReaccion;
END//
DELIMITER ;

-- ====================
--  PROCEDURES PARA STORIES
-- ====================

-- 1) Crear una nueva story
DROP PROCEDURE IF EXISTS CrearStory;
DELIMITER //
CREATE PROCEDURE CrearStory (
  IN p_usuario_id INT,
  IN p_urlContenido VARCHAR(500),
  IN p_tipo ENUM('IMAGEN','VIDEO')
)
BEGIN
  INSERT INTO Story (usuario_id, urlContenido, tipo, fechaCreacion)
  VALUES (p_usuario_id, p_urlContenido, p_tipo, NOW());
END;
//
DELIMITER ;

-- 2) Asignar usuario autorizado para ver una story
DROP PROCEDURE IF EXISTS AgregarUsuarioAStory;
DELIMITER //
CREATE PROCEDURE AgregarUsuarioAStory (
  IN p_idStory INT,
  IN p_idUsuario INT
)
BEGIN
  INSERT INTO Story_Visualizacion (idStory, idUsuario, permitido)
  VALUES (p_idStory, p_idUsuario, TRUE)
  ON DUPLICATE KEY UPDATE permitido = TRUE;
END;
//
DELIMITER ;

-- 3) Listar usuarios que pueden ver una story
DROP PROCEDURE IF EXISTS ListarUsuariosDeStory;
DELIMITER //
CREATE PROCEDURE ListarUsuariosDeStory (
  IN p_idStory INT
)
BEGIN
  SELECT u.idUsuario, u.nombre, u.email
  FROM Story_Visualizacion sv
  JOIN Usuario u ON sv.idUsuario = u.idUsuario
  WHERE sv.idStory = p_idStory AND sv.permitido = TRUE;
END;
//
DELIMITER ;

-- 4) Listar stories visibles para un usuario
DROP PROCEDURE IF EXISTS ListarStoriesParaUsuario;
DELIMITER //
CREATE PROCEDURE ListarStoriesParaUsuario (
  IN p_idUsuario INT
)
BEGIN
  SELECT s.idStory, s.usuario_id AS autor_id, s.urlContenido, s.tipo, s.fechaCreacion
  FROM Story s
  JOIN Story_Visualizacion sv ON s.idStory = sv.idStory
  WHERE sv.idUsuario = p_idUsuario AND sv.permitido = TRUE AND s.activo = TRUE;
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS DesactivarStory;
DELIMITER //
CREATE PROCEDURE DesactivarStory (IN p_idStory INT)
BEGIN
  UPDATE Story SET activo = FALSE WHERE idStory = p_idStory;
END//
DELIMITER ;
-- ====================
--  PROCEDURES PARA NOTIFICACIONES
-- ====================

DROP PROCEDURE IF EXISTS CrearNotificacion;
DELIMITER //
CREATE PROCEDURE CrearNotificacion (
  IN p_usuario_id INT,
  IN p_mensaje TEXT,
  IN p_tipo VARCHAR(50)
)
BEGIN
  INSERT INTO Notificacion (usuario_id, mensaje, tipo, fecha)
  VALUES (p_usuario_id, p_mensaje, p_tipo, NOW());
END;
//
DELIMITER ;
