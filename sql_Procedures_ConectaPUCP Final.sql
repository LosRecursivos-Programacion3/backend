-- ====================
--  PROCEDURES PARA USUARIO
-- ====================
DROP PROCEDURE IF EXISTS sp_registrar_administrador;
DELIMITER //
CREATE PROCEDURE sp_registrar_administrador (
  IN p_nombre VARCHAR(100),
  IN p_password VARCHAR(255),
  IN p_estado BOOLEAN,
  IN p_fechaRegistro DATETIME,
  IN p_email VARCHAR(100)
)
BEGIN
  DECLARE newUserId INT;

  -- Insertar en la tabla Usuario con activo fijo en TRUE
  INSERT INTO Usuario (nombre, password, estado, fecha_Registro, email)
  VALUES (p_nombre, p_password, TRUE, p_fechaRegistro, p_email);

  -- Obtener el ID generado automáticamente
  SET newUserId = LAST_INSERT_ID();

  -- Insertar en la tabla Administrador
  INSERT INTO Administrador (idUsuario)
  VALUES (newUserId);
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_actualizar_administrador;
DELIMITER //
CREATE PROCEDURE sp_actualizar_administrador (
  IN p_idUsuario INT,
  IN p_nombre VARCHAR(100),
  IN p_password VARCHAR(255),
  IN p_estado BOOLEAN,
  IN p_email VARCHAR(100)
)
BEGIN
  UPDATE Usuario
  SET
    nombre = p_nombre,
    password = p_password,
    estado = p_estado,
    email = p_email
  WHERE idUsuario = p_idUsuario AND estado = TRUE;
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_eliminar_administrador;
DELIMITER //
CREATE PROCEDURE sp_eliminar_administrador (
  IN p_idUsuario INT
)
BEGIN
  UPDATE Usuario
  SET estado = FALSE
  WHERE idUsuario = p_idUsuario;
END;
//
DELIMITER ;

-- ====================
--  PROCEDURES PARA USUARIO
-- ====================

DROP PROCEDURE IF EXISTS sp_registrar_usuario;
DELIMITER //
CREATE PROCEDURE sp_registrar_usuario (
  IN p_nombre VARCHAR(100),
  IN p_password VARCHAR(255),
  IN p_estado BOOLEAN,
  IN p_fechaRegistro DATETIME,
  IN p_email VARCHAR(100),
  IN p_visible BOOLEAN,
  OUT p_idUsuario INT  -- Parámetro de salida para el idUsuario generado
)
BEGIN
  INSERT INTO Usuario(nombre, password, estado, fecha_Registro, email, visible)
  VALUES (p_nombre, p_password, p_estado, p_fechaRegistro, p_email, p_visible);
  SET p_idUsuario = LAST_INSERT_ID();
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_eliminar_usuario;
DELIMITER //
CREATE PROCEDURE sp_eliminar_usuario (
  IN p_idUsuario INT
)
BEGIN
  UPDATE Usuario SET estado = FALSE WHERE idUsuario = p_idUsuario;
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_actualizar_usuario;
DELIMITER //
CREATE PROCEDURE sp_actualizar_usuario (
  IN p_idUsuario INT,
  IN p_nombre VARCHAR(100),
  IN p_password VARCHAR(255),
  IN p_estado BOOLEAN,
  IN p_email VARCHAR(100),
  in p_visible boolean
)
BEGIN
  UPDATE Usuario
  SET 
    nombre = p_nombre,
    password = p_password,
    email = p_email,
    estado = p_estado,
    visible = p_visible
  WHERE idUsuario = p_idUsuario;
END;
//
DELIMITER ;

-- ====================
--  PROCEDURES PARA ALUMNO
-- ====================

DELIMITER //
CREATE PROCEDURE sp_registrar_alumno (
  IN p_nombre VARCHAR(100),
  IN p_password VARCHAR(255),
  IN p_fechaRegistro DATETIME,
  IN p_email VARCHAR(100),
  IN p_edad INT,
  IN p_carrera VARCHAR(100),
  IN p_fotoPerfil VARCHAR(255),
  IN p_ubicacion VARCHAR(255),
  IN p_biografia TEXT,
  OUT p_idAlumno INT
)
BEGIN
  DECLARE v_idUsuario INT;

  -- Insertar en la tabla Usuario
  INSERT INTO Usuario (nombre, password, estado, fecha_Registro, email)
  VALUES (p_nombre, p_password, TRUE, p_fechaRegistro, p_email);

  -- Obtener el ID generado para Usuario
  SET v_idUsuario = LAST_INSERT_ID();

  -- Insertar en la tabla Alumno
  INSERT INTO Alumno (idUsuario, edad, carrera, fotoPerfil, ubicacion, biografia)
  VALUES (v_idUsuario, p_edad, p_carrera, p_fotoPerfil, p_ubicacion, p_biografia);
  SET p_idAlumno = LAST_INSERT_ID();
END;
//
DELIMITER ;


-- Desactivar Alumno (cambia activo a FALSE tanto en Alumno como en Usuario)
DROP PROCEDURE IF EXISTS sp_eliminar_alumno;
DELIMITER //
CREATE PROCEDURE sp_eliminar_alumno (IN p_idAlumno INT)
BEGIN
  DECLARE v_idUsuario INT;
  SELECT idUsuario INTO v_idUsuario FROM Alumno WHERE idAlumno = p_idAlumno;
  UPDATE Usuario SET estado = FALSE WHERE idUsuario = v_idUsuario;
END;
//
DELIMITER ;

-- Actualizar Alumno (nombre, edad, carrera, ubicacion, biografia, fotoPerfil)
DROP PROCEDURE IF EXISTS sp_actualizar_alumno;
DELIMITER //
CREATE PROCEDURE sp_actualizar_alumno (
  IN p_idAlumno INT,
  IN p_nombre VARCHAR(100),
  IN p_password VARCHAR(100),
  IN p_estado BOOLEAN,
  IN p_email VARCHAR(100),
  IN p_edad INT,
  IN p_carrera VARCHAR(100),
  IN p_fotoPerfil VARCHAR(255),
  IN p_biografia TEXT
)
BEGIN
  -- Actualizar tabla Usuario (nombre)
  DECLARE p_idUsuario INT;
  SELECT idUsuario INTO p_idUsuario FROM Alumno WHERE idAlumno = p_idAlumno;
    UPDATE Usuario
     SET 
          nombre = p_nombre,
          password = p_password,
          estado = p_estado
    WHERE idUsuario = p_idUsuario;
  
  -- Actualizar tabla Alumno (edad, carrera, ubicacion, biografia, fotoPerfil)
  UPDATE Alumno 
  SET 
    edad = p_edad,
    carrera = p_carrera,
    ubicacion = p_ubicacion,
    biografia = p_biografia,
    fotoPerfil = p_fotoPerfil
  WHERE idUsuario = p_idUsuario;
END
//
DELIMITER ;


-- Actualizar biografía del alumno;

DROP PROCEDURE IF EXISTS ActualizarBiografiaAlumno;
DELIMITER //
CREATE PROCEDURE ActualizarBiografiaAlumno (
  IN p_idAlumno INT,
  IN p_nuevaBiografia TEXT
)
BEGIN
  UPDATE Alumno
  SET biografia = p_nuevaBiografia
  WHERE idAlumno = p_idAlumno;
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS ActualizarPasswordUsuario;
DELIMITER //
CREATE PROCEDURE ActualizarPasswordUsuario (
  IN p_idAlumno INT,
  IN p_nuevaPassword VARCHAR(255)
)
BEGIN
  DECLARE p_idUsuario INT;
  SELECT idUsuario INTO p_idUsuario FROM Alumno WHERE idAlumno = p_idAlumno;
  UPDATE Usuario
  SET password = p_nuevaPassword
  WHERE idUsuario = p_idUsuario AND estado = TRUE;
END;
//
DELIMITER ;
-- ====================
--  PROCEDURES PARA INTERESES
-- ====================

DROP PROCEDURE IF EXISTS sp_registrar_interes;
DELIMITER //

CREATE PROCEDURE sp_registrar_interes (
  IN p_nombre VARCHAR(100),
  IN p_descripcion VARCHAR(255),
  OUT p_idInteres INT
)
BEGIN
  INSERT INTO Interes (nombre, estado, descripcion)
  VALUES (p_nombre, TRUE, p_descripcion);
  SET p_idIntereses = LAST_INSERT_ID();
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_actualizar_interes;
DELIMITER //

CREATE PROCEDURE sp_actualizar_interes (
  IN p_idInteres INT,
  IN p_nombre VARCHAR(100),
  IN p_descripcion VARCHAR(255)
)
BEGIN
  UPDATE Interes
  SET nombre = p_nombre,
      descripcion = p_descripcion
  WHERE idInteres = p_idInteres;
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_eliminar_interes;
DELIMITER //

CREATE PROCEDURE sp_eliminar_interes (
  IN p_idInteres INT
)
BEGIN
  UPDATE Interes
  SET estado = FALSE
  WHERE idInteres = p_idInteres;
END;
//
DELIMITER ;


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

DROP PROCEDURE IF EXISTS sp_registrar_evento;
DELIMITER //
CREATE PROCEDURE sp_registrar_evento (
  IN p_nombre VARCHAR(200),
  IN p_descripcion TEXT,
  IN p_fecha DATETIME,
  IN p_ubicacion VARCHAR(255),
  IN p_estado BOOLEAN,
  IN p_creador_id INT,
  OUT p_idEvento INT
)
BEGIN
  INSERT INTO Evento (nombre, descripcion, fecha, ubicacion, estado, creador_id)
  VALUES (p_nombre, p_descripcion, p_fecha, p_ubicacion, TRUE, p_creador_id);
  SET p_idEvento = LAST_INSERT_ID();
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_actualizar_evento;
DELIMITER //

CREATE PROCEDURE sp_actualizar_evento (
  IN p_idEvento INT,
  IN p_nombre VARCHAR(200),
  IN p_descripcion TEXT,
  IN p_fecha DATETIME,
  IN p_ubicacion VARCHAR(255),
  IN p_estado BOOLEAN,
  IN p_creador_id INT
)
BEGIN
  UPDATE Evento
  SET nombre = p_nombre,
      descripcion = p_descripcion,
      fecha = p_fecha,
      ubicacion = p_ubicacion,
      estado = p_estado,
      credor_id = p_creador_id
  WHERE idEvento = p_idEvento;
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_eliminar_evento;
DELIMITER //

CREATE PROCEDURE sp_eliminar_evento (
  IN p_idEvento INT
)
BEGIN
  UPDATE Evento
  SET estado = FALSE
  WHERE idEvento = p_idEvento;
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



DROP PROCEDURE IF EXISTS sp_registrar_post;
DELIMITER //
CREATE PROCEDURE sp_registrar_post (
  IN p_contenido TEXT,
  IN p_fecha DATETIME,
  IN p_estado BOOLEAN,
  IN p_autor_id INT,
  out p_idpost int
)
BEGIN
  INSERT INTO Post (contenido, fecha, estado, autor_id)
  VALUES (p_contenido, NOW(), p_estado, p_autor_id);
  SET p_idpost = LAST_INSERT_ID();
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_actualizar_post;
DELIMITER //

CREATE PROCEDURE sp_actualizar_post (
  IN p_idPost INT,
  IN p_contenido TEXT,
  IN p_estado BOOLEAN
)
BEGIN
  UPDATE Post
  SET contenido = p_contenido,
      estado = p_estado
  WHERE idPost = p_idPost;
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_eliminar_post;
DELIMITER //

CREATE PROCEDURE sp_eliminar_post (
  IN p_idPost INT
)
BEGIN
  UPDATE Post
  SET estado = FALSE
  WHERE idPost = p_idPost;
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
  UPDATE Comentario SET estado = FALSE WHERE idComentario = p_idComentario;
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS DesactivarPost;
DELIMITER //
CREATE PROCEDURE DesactivarPost (IN p_idPost INT)
BEGIN
  UPDATE Post SET estado = FALSE WHERE idPost = p_idPost;
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
  WHERE p.autor_id = p_idUsuario AND p.estado = TRUE;
END;
//
DELIMITER ;
-- ====================
--  PROCEDURES PARA MENSAJES
-- ====================

DROP PROCEDURE IF EXISTS sp_registrar_mensaje;
DELIMITER //

CREATE PROCEDURE sp_registrar_mensaje (
  IN p_emisor_id INT,
  IN p_receptor_id INT,
  IN p_contenido TEXT,
  IN p_fechaEnvio DATETIME
  
)
BEGIN
  INSERT INTO Mensaje ( emisor_id, receptor_id,contenido, fechaEnvio, estado)
  VALUES ( p_emisor_id, p_receptor_id,p_contenido, p_fechaEnvio, TRUE);
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_actualizar_mensaje;
DELIMITER //

CREATE PROCEDURE sp_actualizar_mensaje (
  IN p_idMensaje INT,
  IN p_contenido TEXT,
  IN p_fechaEnvio DATETIME
)
BEGIN
  UPDATE Mensaje
  SET contenido = p_contenido,
      fechaEnvio = p_fechaEnvio
  WHERE idMensaje = p_idMensaje;
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_eliminar_mensaje;
DELIMITER //

CREATE PROCEDURE sp_eliminar_mensaje (
  IN p_idMensaje INT
)
BEGIN
  UPDATE Mensaje
  SET estado = FALSE
  WHERE idMensaje = p_idMensaje;
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_obtener_mensajes_entre_usuarios;
DELIMITER //

CREATE PROCEDURE sp_obtener_mensajes_entre_usuarios (
  IN p_emisario INT,
  IN p_receptor INT
)
BEGIN
  SELECT *
  FROM Mensaje
  WHERE estado = TRUE
    AND (emisor_id = p_emisario AND receptor_id = p_receptor) 
  
  ORDER BY fechaEnvio ASC;
END;
//
DELIMITER ;


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

DROP PROCEDURE IF EXISTS sp_registrar_reaccion;
DELIMITER //
CREATE PROCEDURE sp_registrar_reaccion (
  IN p_tipoReaccion INT,
  IN p_usuario_id INT,
  IN p_post_id INT,
  IN p_comentario_id INT,
  IN p_evento_id INT
)
BEGIN
  INSERT INTO Reaccion (tipoReaccion, usuario_id, post_id, comentario_id, evento_id, estado, fecha)
  VALUES (
    CASE 
      WHEN p_tipoReaccion = 0 THEN 'LIKE'
      WHEN p_tipoReaccion = 1 THEN 'DISLIKE'
      WHEN p_tipoReaccion = 2 THEN 'LOVE'
      WHEN p_tipoReaccion = 3 THEN 'JAJA'
    END,
    p_usuario_id,
    p_post_id,
    p_comentario_id,
    p_evento_id,
    TRUE,  -- Siempre se marca como activo
    p_fecha

  -- Obtener el ID de la reacción insertada
    SET p_idReaccion = LAST_INSERT_ID();
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_actualizar_reaccion;
DELIMITER //

CREATE PROCEDURE sp_actualizar_reaccion(
  IN p_idReaccion INT,          -- ID de la reacción a actualizar
  IN p_tipoReaccion INT,        -- Tipo de la reacción (ordinal)
  IN p_fecha DATETIME           -- Fecha de la reacción
)
BEGIN
  -- Actualizar la reacción en la tabla Reaccion
  UPDATE Reaccion
  SET
    tipoReaccion = CASE 
                    WHEN p_tipoReaccion = 0 THEN 'LIKE'
                    WHEN p_tipoReaccion = 1 THEN 'DISLIKE'
                    WHEN p_tipoReaccion = 2 THEN 'LOVE'
                    WHEN p_tipoReaccion = 3 THEN 'JAJA'
                  END,
    fecha = p_fecha
  WHERE idReaccion = p_idReaccion;
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS sp_eliminar_reaccion;
DELIMITER //

CREATE PROCEDURE sp_eliminar_reaccion(
  IN p_idReaccion INT          -- ID de la reacción a eliminar (lógicamente)
)
BEGIN
  -- Actualizar el campo "activo" a FALSE para desactivar la reacción
  UPDATE Reaccion
  SET estado = FALSE
  WHERE idReaccion = p_idReaccion;
END;
//
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
  WHERE sv.idUsuario = p_idUsuario AND sv.permitido = TRUE AND s.estado = TRUE;
END;
//
DELIMITER ;

DROP PROCEDURE IF EXISTS DesactivarStory;
DELIMITER //
CREATE PROCEDURE DesactivarStory (IN p_idStory INT)
BEGIN
  UPDATE Story SET estado = FALSE WHERE idStory = p_idStory;
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
