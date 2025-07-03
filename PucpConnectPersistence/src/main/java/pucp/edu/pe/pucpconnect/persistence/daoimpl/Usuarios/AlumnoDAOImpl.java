/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import pucp.edu.pe.pucpconnect.domain.Social.Amistad;
import pucp.edu.pe.pucpconnect.domain.Social.Post;

import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;
import pucp.edu.pe.pucpconnect.persistence.BaseDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.DBManager;
import pucp.edu.pe.pucpconnect.persistence.dao.Usuarios.AlumnoDAO;

/**
 *
 * @author USUARIO
 */
public class AlumnoDAOImpl extends BaseDAOImpl<Alumno> implements AlumnoDAO {
    @Override
    protected PreparedStatement getInsertPS(Connection conn, Alumno alumno) throws SQLException {
        String query = "{CALL sp_registrar_alumno(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}"; // Procedimiento almacenado para insertar alumno
        CallableStatement cs = conn.prepareCall(query);
        cs.setString(1, alumno.getNombre());
        cs.setString(2, alumno.getPassword());
        cs.setTimestamp(3, Timestamp.valueOf(alumno.getFechaRegistro()));
        cs.setString(4, alumno.getEmail());
        cs.setInt(5, alumno.getEdad());
        cs.setString(6, alumno.getCarrera());
        cs.setString(7, alumno.getFotoPerfil());
        cs.setString(8, alumno.getUbicacion());
        cs.setString(9, alumno.getBiografia());
        cs.registerOutParameter(10, Types.INTEGER);
        return cs;
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Alumno alumno) throws SQLException {
        String query = "{CALL sp_actualizar_alumno(?, ?, ?, ?, ?, ?, ?, ?, ?)}"; // Procedimiento almacenado para actualizar alumno
        CallableStatement cs = conn.prepareCall(query);
        cs.setInt(1, alumno.getId());
        cs.setString(2, alumno.getNombre());
        cs.setString(3, alumno.getPassword());
        cs.setBoolean(4, alumno.getEstado());
        cs.setString(5, alumno.getEmail());
        cs.setInt(6, alumno.getEdad());
        cs.setString(7, alumno.getCarrera());
        cs.setString(8, alumno.getFotoPerfil());
        cs.setString(9, alumno.getBiografia());
        return cs;
    }

    @Override
    protected PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException {
        String query = "{CALL sp_eliminar_alumno(?)}"; // Procedimiento almacenado para eliminar alumno
        CallableStatement ps = conn.prepareCall(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectByIdPS(Connection conn, Integer id) throws SQLException {
        String query = "SELECT * FROM Usuario u INNER JOIN Alumno a ON u.idUsuario = a.idUsuario WHERE u.idUsuario = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
        String query = "SELECT * FROM Alumno";
        return conn.prepareStatement(query);
    }

    @Override
    protected Alumno createFromResultSet(ResultSet rs) throws SQLException {
        Alumno alumno = new Alumno();
        alumno.setId(rs.getInt("idUsuario"));
        alumno.setIdAlumno(rs.getInt("idAlumno"));
        alumno.setNombre(rs.getString("nombre"));
        alumno.setPassword(rs.getString("password"));
        alumno.setEstado(rs.getBoolean("estado"));
        alumno.setFechaRegistro(rs.getTimestamp("fecha_Registro").toLocalDateTime());
        alumno.setEmail(rs.getString("email"));
        alumno.setEdad(rs.getInt("edad"));
        alumno.setCarrera(rs.getString("carrera"));
        alumno.setFotoPerfil(rs.getString("fotoPerfil"));
        alumno.setUbicacion(rs.getString("biografia"));
        return alumno;
    }

    @Override
    protected void setId(Alumno alumno, Integer id) {
        alumno.setId(id);
    }

    @Override
    public boolean bloquearAlumno(int idAlumno, int idBloqueado) {
        String sql = "INSERT INTO Alumnos_bloqueados (id_Alumno, id_Alumno_bloqueado, fecha) VALUES (?, ?, NOW())";
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idAlumno);
            pstmt.setInt(2, idBloqueado);
            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean desbloquearAlumno(int idAlumno, int idBloqueado) {
        String sql = "DELETE FROM Alumnos_bloqueados WHERE id_Alumno = ? AND id_Alumno_bloqueado = ?";
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idAlumno);
            pstmt.setInt(2, idBloqueado);
            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    
    @Override
    public List<Integer> obtenerAlumnosBloqueados(int idAlumno) {
        List<Integer> idsBloqueados = new ArrayList<>();
        String sql = "SELECT id_Alumno_bloqueado FROM Alumnos_bloqueados WHERE id_Alumno = ?";

        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idAlumno);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                idsBloqueados.add(rs.getInt("id_Alumno_bloqueado"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
 
        return idsBloqueados;
    }
    
    @Override
    public Alumno buscarPorIdUsuario(int idUsuario) {
        String query = "SELECT a.*, u.nombre, u.password, u.estado, u.fecha_registro, u.email, u.visible FROM Alumno a INNER JOIN Usuario u ON a.idUsuario = u.idUsuario WHERE a.idUsuario = ?";
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Alumno alumno = new Alumno();
                alumno.setId(rs.getInt("idUsuario")); // el ID base del Usuario
                alumno.setIdAlumno(rs.getInt("idAlumno"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setPassword(rs.getString("password"));
                alumno.setEstado(rs.getBoolean("estado"));
                alumno.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
                alumno.setEmail(rs.getString("email"));
                alumno.setVisible(rs.getBoolean("visible"));

                alumno.setEdad(rs.getInt("edad"));
                alumno.setCarrera(rs.getString("carrera"));
                alumno.setFotoPerfil(rs.getString("fotoPerfil"));
                alumno.setUbicacion(rs.getString("ubicacion"));
                alumno.setBiografia(rs.getString("biografia"));

                // Alumnos bloqueados opcional
                List<Integer> bloqueados = obtenerAlumnosBloqueados(alumno.getId());
                alumno.setIdsAlumnosBloqueados(bloqueados);

                return alumno;
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log real en prod
        }

        return null; // No es alumno
    }
    
    @Override
    public Alumno modificar(Alumno alumno) throws SQLException {
        String updateUsuario = "UPDATE Usuario SET nombre = ? WHERE idUsuario = ?";
        String updateAlumno = "UPDATE Alumno SET edad = ?, carrera = ?, fotoPerfil = ?, ubicacion = ?, biografia = ? WHERE idAlumno = ?";

        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
            conn.setAutoCommit(false); // Empezamos una transacción manual

            // 1. Actualizar tabla Usuario
            try (PreparedStatement psUsuario = conn.prepareStatement(updateUsuario)) {
                psUsuario.setString(1, alumno.getNombre());
                psUsuario.setInt(2, alumno.getId());
                psUsuario.executeUpdate();
            }

            // 2. Actualizar tabla Alumno
            try (PreparedStatement psAlumno = conn.prepareStatement(updateAlumno)) {
                psAlumno.setInt(1, alumno.getEdad());
                psAlumno.setString(2, alumno.getCarrera());
                psAlumno.setString(3, alumno.getFotoPerfil());
                psAlumno.setString(4, alumno.getUbicacion());
                psAlumno.setString(5, alumno.getBiografia());
                psAlumno.setInt(6, alumno.getIdAlumno());
                psAlumno.executeUpdate();
            }

            conn.commit(); // Confirmamos ambas actualizaciones
            return alumno;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Repropagamos para que el servicio lo maneje
        }
    }
    
    @Override
    public List<Alumno> listarAmigosSugeridos(List<Interes> intereses, int idAlumnoBuscador) throws SQLException {
        List<Alumno> sugeridos = new ArrayList<>();

        // Convertir lista de intereses a CSV
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < intereses.size(); i++) {
            sb.append(intereses.get(i).getId());
            if (i < intereses.size() - 1) sb.append(",");
        }
        String interesesCsv = sb.toString();

        String sql = "{CALL sp_sugerir_amigos(?, ?)}";

        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, interesesCsv);
            cs.setInt(2, idAlumnoBuscador);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Alumno a = new Alumno();
                    a.setIdAlumno(rs.getInt("idAlumno"));
                    a.setEdad(rs.getInt("edad"));
                    a.setCarrera(rs.getString("carrera"));
                    a.setFotoPerfil(rs.getString("fotoPerfil"));
                    a.setUbicacion(rs.getString("ubicacion"));
                    a.setBiografia(rs.getString("biografia"));
                    a.setId(rs.getInt("idUsuario"));
                    a.setNombre(rs.getString("nombre"));
                    sugeridos.add(a);
                }
            }
        }

        return sugeridos;
    }
    
    @Override
    public void registrarSolicitudAmistad(int idUsuario1, int idUsuario2) throws SQLException{
        String sql = "INSERT INTO Amistades (idAlumnoUno, idAlumnoDos, estado, fecha) VALUES (?,?,?,NOW())";
        try(Connection conn = DBManager.getInstance().obtenerConexion();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, idUsuario1);
            ps.setInt(2, idUsuario2);
            ps.setInt(3, 0);
            
            ps.executeUpdate();
        }
    }
    @Override
    public List<Amistad> listarSolicitudesEnviadas(int idAlumno) throws SQLException {
        String sql = "SELECT a.idAmistad, a.estado, a.fecha, al1.idAlumno AS idAlumnoUno, u1.nombre AS nombreAlumnoUno, al1.fotoPerfil AS fotoPerfilAlumnoUno, al2.idAlumno AS idAlumnoDos, u2.nombre AS nombreAlumnoDos, al2.fotoPerfil AS fotoPerfilAlumnoDos FROM Amistades a INNER JOIN Alumno al1 ON a.idAlumnoUno = al1.idAlumno INNER JOIN Usuario u1 ON u1.idUsuario = al1.idUsuario INNER JOIN Alumno al2 ON a.idAlumnoDos = al2.idAlumno INNER JOIN Usuario u2 ON u2.idUsuario = al2.idUsuario WHERE a.idAlumnoUno = ? AND a.estado = 0;";
        List<Amistad> amistades = new ArrayList<>();
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Amistad amistad = new Amistad();
                amistad.setIdAmistad(rs.getInt("idAmistad"));
                amistad.setIdAlumnoUno(rs.getInt("idAlumnoUno"));
                amistad.setIdAlumnoDos(rs.getInt("idAlumnoDos"));
                amistad.setEstado(rs.getInt("estado"));
                amistad.setFotoPerfilAlumnoUno(rs.getString("fotoPerfilAlumnoUno"));
                amistad.setFotoPerfilAlumnoDos(rs.getString("fotoPerfilAlumnoDos"));
                amistad.setNombreAlumnoUno(rs.getString("nombreAlumnoUno"));
                amistad.setNombreAlumnoDos(rs.getString("nombreAlumnoDos"));
                LocalDateTime fecha = rs.getTimestamp("fecha").toLocalDateTime();
                amistad.setFecha(fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                amistades.add(amistad);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log real en prod
        }
        return amistades;
    }
    
    @Override
    public List<Amistad> listarSolicitudesRecibidas(int idAlumno) throws SQLException {
        String sql = "SELECT a.idAmistad, a.estado, a.fecha, al1.idAlumno AS idAlumnoUno, u1.nombre AS nombreAlumnoUno, al1.fotoPerfil AS fotoPerfilAlumnoUno, al2.idAlumno AS idAlumnoDos, u2.nombre AS nombreAlumnoDos, al2.fotoPerfil AS fotoPerfilAlumnoDos FROM Amistades a INNER JOIN Alumno al1 ON a.idAlumnoUno = al1.idAlumno INNER JOIN Usuario u1 ON u1.idUsuario = al1.idUsuario INNER JOIN Alumno al2 ON a.idAlumnoDos = al2.idAlumno INNER JOIN Usuario u2 ON u2.idUsuario = al2.idUsuario WHERE a.idAlumnoDos = ? AND a.estado = 0;";
        List<Amistad> amistades = new ArrayList<>();
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Amistad amistad = new Amistad();
                amistad.setIdAmistad(rs.getInt("idAmistad"));
                amistad.setIdAlumnoUno(rs.getInt("idAlumnoUno"));
                amistad.setIdAlumnoDos(rs.getInt("idAlumnoDos"));
                amistad.setEstado(rs.getInt("estado"));
                amistad.setFotoPerfilAlumnoUno(rs.getString("fotoPerfilAlumnoUno"));
                amistad.setFotoPerfilAlumnoDos(rs.getString("fotoPerfilAlumnoDos"));
                amistad.setNombreAlumnoUno(rs.getString("nombreAlumnoUno"));
                amistad.setNombreAlumnoDos(rs.getString("nombreAlumnoDos"));
                LocalDateTime fecha = rs.getTimestamp("fecha").toLocalDateTime();
                amistad.setFecha(fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                amistades.add(amistad);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log real en prod
        }
        return amistades;
    }
    
    @Override
    public void actualizarEstadoAmistad(int idAmistad, int nuevoEstado) throws SQLException {
        String sql = "UPDATE Amistades SET estado = ? WHERE idAmistad = ?";

        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nuevoEstado);
            ps.setInt(2, idAmistad);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // En producción usar logger
            throw e; // Opcionalmente relanza la excepción para manejarla en capa superior
        }
    }
    @Override
    public List<Alumno> obtenerAmigosPorUsuario(int usuarioId) throws SQLException {
        String sql =
          "SELECT u.idUsuario, u.nombre, u.password, u.estado AS usuarioEstado, "
        + "       u.fecha_Registro, u.email, u.visible, "
        + "       a.idAlumno, a.edad, a.carrera, a.fotoPerfil, a.ubicacion, a.biografia "
        + "  FROM Usuario u "
        + "  INNER JOIN Alumno a ON u.idUsuario = a.idUsuario "
        + "  INNER JOIN Amistades am ON ( "
        + "       (am.idAlumnoUno = ? AND am.idAlumnoDos   = a.idAlumno) "
        + "    OR (am.idAlumnoDos = ? AND am.idAlumnoUno   = a.idAlumno) ) "
        + " WHERE am.estado = 1";

        List<Alumno> amigos = new ArrayList<>();

        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            ps.setInt(2, usuarioId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Alumno a = new Alumno();
                    // Campos de Usuario
                    a.setId(rs.getInt("idUsuario"));
                    a.setNombre(rs.getString("nombre"));
                    a.setPassword(rs.getString("password"));
                    a.setEstado(rs.getBoolean("usuarioEstado"));
                    a.setFechaRegistro(rs.getTimestamp("fecha_registro")
                                          .toLocalDateTime());
                    a.setEmail(rs.getString("email"));
                    a.setVisible(rs.getBoolean("visible"));

                    // Campos de Alumno
                    a.setIdAlumno(   rs.getInt("idAlumno"));
                    a.setEdad(       rs.getInt("edad"));
                    a.setCarrera(    rs.getString("carrera"));
                    a.setFotoPerfil(rs.getString("fotoPerfil"));
                    a.setUbicacion( rs.getString("ubicacion"));
                    a.setBiografia( rs.getString("biografia"));

                    amigos.add(a);
                }
            }
        }

        return amigos;
    }
    @Override
    public List<Post> obtenerPostPorUsuario(int usuarioId) throws SQLException {
        String sql = "SELECT * FROM Post p WHERE p.autor_id = ? ORDER BY 3 DESC";

        List<Post> posts = new ArrayList<>();

        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Post p = new Post();
                    // Campos de Usuario
                    p.setIdPost(rs.getInt("idPost"));
                    p.setContenido(rs.getString("contenido"));
                    p.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                    p.setEstado(rs.getBoolean("estado"));
                    p.setImagen(rs.getString("imagen"));
                    p.setAutor(rs.getInt("autor_id"));
                    posts.add(p);
                }
            }
        }
        return posts;
    }
    
    @Override
    public List<Alumno> listarAmigosSugeridos_Match(List<Interes> intereses, int idAlumnoBuscador) throws SQLException {
        List<Alumno> sugeridos = new ArrayList<>();

        // Convertir lista de intereses a CSV
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < intereses.size(); i++) {
            sb.append(intereses.get(i).getId());
            if (i < intereses.size() - 1) sb.append(",");
        }
        String interesesCsv = sb.toString();

        String sql = "{CALL sp_sugerir_amigos_para_match(?, ?)}";

        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setString(1, interesesCsv);
            cs.setInt(2, idAlumnoBuscador);

            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Alumno a = new Alumno();
                    a.setIdAlumno(rs.getInt("idAlumno"));
                    a.setEdad(rs.getInt("edad"));
                    a.setCarrera(rs.getString("carrera"));
                    a.setFotoPerfil(rs.getString("fotoPerfil"));
                    a.setUbicacion(rs.getString("ubicacion"));
                    a.setBiografia(rs.getString("biografia"));
                    a.setId(rs.getInt("idUsuario"));
                    a.setNombre(rs.getString("nombre"));
                    sugeridos.add(a);
                }
            }
        }
        return sugeridos;
    }
    
    @Override
    public int existeInteraccion(int idAlumnoDos, int idAlumnoUno) throws SQLException {
        String sql = "SELECT 1 FROM Interaccion WHERE " +
                     "(alumnoUno_id = ? AND alumnoDos_id = ?) " +
                     "OR (alumnoUno_id = ? AND alumnoDos_id = ?) " +
                     "LIMIT 1";

        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAlumnoUno);  // Primer orden
            ps.setInt(2, idAlumnoDos);
            ps.setInt(3, idAlumnoDos);  // Segundo orden
            ps.setInt(4, idAlumnoUno);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idInteraccion");
                } else {
                    return -1;
                }
            }
        }
    }
    
    @Override
    public void aceptarInteraccion(int idInteraccion) throws SQLException {
        String sql = "UPDATE Interaccion SET estado = 1 WHERE idInteraccion = ?";

        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idInteraccion);
            ps.executeUpdate();
        }
    }
    
    @Override
    public void agregarInteraccion(int idAlumnoDos, int idAlumnoUno) throws SQLException {
        String sql = "INSERT INTO Interaccion (alumnoUno_id, alumnoDos_id, estado, tipo, fecha) " +
                 "VALUES (?, ?, 0, 'M', CURDATE())";
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAlumnoUno);
            ps.setInt(2, idAlumnoDos);
            ps.executeUpdate();
        }
    }
    
    @Override
    public void agregarAmistad(int idAlumnoUno, int idAlumnoDos) throws Exception{
        String sql = "INSERT INTO Amistades (idAlumnoUno, idAlumnoDos, estado, fecha) " +
                 "VALUES (?, ?, 1, CURDATE())";

        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAlumnoUno);
            ps.setInt(2, idAlumnoDos);
            ps.executeUpdate();
        }
    }
}


