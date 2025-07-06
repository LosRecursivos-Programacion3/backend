/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.daoimpl.Social;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pucp.edu.pe.pucpconnect.domain.Social.Evento;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;
import pucp.edu.pe.pucpconnect.persistence.BaseDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.DBManager;
import pucp.edu.pe.pucpconnect.persistence.dao.Social.EventoDAO;

/**
 *
 * @author irico17
 */
public class EventoDAOImpl extends BaseDAOImpl<Evento> implements EventoDAO {

    @Override
    public void agregarEvento(Evento evento) {
        try (Connection conn = DBManager.getInstance().obtenerConexion(); PreparedStatement ps = getInsertPS(conn, evento)) {

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    setId(evento, rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al agregar entidad", e);
        }
    }

    @Override
    public List<Evento> listarTodosEventos() {
        List<Evento> eventos = new ArrayList<>();
        Map<Integer, Evento> eventoMap = new HashMap<>();

        // Primero: Obtener todos los eventos con una sola consulta
        String eventoQuery = "SELECT e.* FROM Evento e";

        // Segundo: Obtener todos los intereses de todos los eventos con dos consultas
        String interesesQuery = "SELECT ei.id_evento, i.* FROM Interes i "
                + "JOIN Evento_Interes ei ON i.idInteres = ei.id_interes "
                + "JOIN Evento e ON ei.id_evento = e.idEvento "
                + "WHERE e.estado = TRUE";

        try (Connection conn = DBManager.getInstance().obtenerConexion(); Statement stmt = conn.createStatement(); ResultSet eventoRs = stmt.executeQuery(eventoQuery)) {

            // Mapear todos los eventos
            while (eventoRs.next()) {
                Evento evento = createBasicEventoFromResultSet(eventoRs);
                eventos.add(evento);
                eventoMap.put(evento.getIdEvento(), evento);
            }

            // Obtener todos los intereses en una sola consulta
            try (ResultSet interesRs = stmt.executeQuery(interesesQuery)) {
                while (interesRs.next()) {
                    int idEvento = interesRs.getInt("id_evento");
                    Interes interes = new Interes();
                    interes.setId(interesRs.getInt("idInteres"));
                    interes.setNombre(interesRs.getString("nombre"));

                    // Agregar el interés al evento correspondiente
                    Evento evento = eventoMap.get(idEvento);
                    if (evento != null) {
                        evento.getIntereses().add(interes);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar eventos con intereses", e);
        }

        return eventos;
    }

    @Override
    protected PreparedStatement getInsertPS(Connection conn, Evento evento) throws SQLException {
        String query = "{CALL sp_registrar_evento(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement cs = conn.prepareCall(query);
        cs.setString(1, evento.getNombre());
        cs.setString(2, evento.getDescripcion());
        cs.setTimestamp(3, Timestamp.valueOf(evento.getFecha()));
        cs.setTimestamp(4, Timestamp.valueOf(evento.getFechaFin()));
        cs.setString(5, evento.getUbicacion());
        cs.setString(6, evento.getImagen()); // Nueva imagen como string
        cs.setBoolean(7, evento.isEstado());
        cs.setInt(8, evento.getCreador().getIdAlumno());
        cs.registerOutParameter(9, Types.INTEGER);

        cs.executeUpdate();

        int idGenerado = cs.getInt(9);
        evento.setIdEvento(idGenerado);

        return cs;
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Evento evento) throws SQLException {
        String query = "{CALL sp_actualizar_evento(?, ?, ?, ?, ?, ?, ?)}"; // Procedimiento almacenado para actualizar evento
        CallableStatement cs = conn.prepareCall(query);
        cs.setInt(1, evento.getIdEvento());
        cs.setString(2, evento.getNombre());
        cs.setString(3, evento.getDescripcion());
        cs.setTimestamp(4, Timestamp.valueOf(evento.getFecha())); // Nueva fecha
        cs.setString(5, evento.getUbicacion());
        cs.setBoolean(6, evento.isEstado());
        int id = evento.getCreador().getId();
        System.out.println(id);
        cs.setInt(7, evento.getCreador().getId());
        return cs;
    }

    @Override
    protected PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException {
        String query = "{CALL sp_eliminar_evento(?)}"; // Procedimiento almacenado para eliminar evento
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectByIdPS(Connection conn, Integer id) throws SQLException {
        String query = "SELECT * FROM Evento WHERE idEvento = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
//        String query = "SELECT * FROM Evento";
        String query = "SELECT e.*, \n" + "GROUP_CONCAT(i.nombre SEPARATOR ', ')"
                + " AS intereses FROM Evento e "
                + " LEFT JOIN Evento_Interes ei ON e.idEvento = ei.id_evento"
                + " LEFT JOIN Interes i ON ei.id_interes = i.idInteres"
                + " WHERE e.estado = TRUE"
                + " GROUP BY e.idEvento";
        return conn.prepareStatement(query);
    }

    @Override
    protected Evento createFromResultSet(ResultSet rs) throws SQLException {
        Evento evento = new Evento();
        evento.setIdEvento(rs.getInt("idEvento"));
        evento.setNombre(rs.getString("nombre"));
        evento.setDescripcion(rs.getString("descripcion"));

        // Manejo de fecha (sin conversion a LocalDateTime si no es necesaria)
        String fechaStr = rs.getString("fecha"); // Obtiene directamente como string
        evento.setFechaString(fechaStr); // Asigna directamente el string

        // Manejo de fechaFin (si existe en tu BD)
        String fechaFinStr = rs.getString("fechaFin");
        evento.setFechaFinString(fechaFinStr);

        evento.setUbicacion(rs.getString("ubicacion"));
        evento.setEstado(rs.getBoolean("estado"));
        evento.setImagen(rs.getString("imagen")); // Puede ser null

        int idCreador = rs.getInt("creador_id");
        Alumno creador = new Alumno();
        creador.setId(idCreador);
        evento.setCreador(creador);

        List<Interes> intereses = listarInteresesPorEvento(rs.getInt("idEvento"));
        evento.setIntereses(intereses);
        return evento;
    }

    @Override
    protected void setId(Evento evento, Integer id) {
        evento.setIdEvento(id);
    }

    @Override
    public void registrarParticipacionEvento(int idEvento, int idUsuario) throws SQLException {
        // Primero verificamos si ya existe la participación
        String sql = "INSERT INTO Evento_Participante (idEvento, idUsuario) VALUES (?, ?)";

        try (Connection conn = DBManager.getInstance().obtenerConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEvento);
            ps.setInt(2, idUsuario);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar participación: ", e);
        }
    }

    @Override
    public List<Alumno> listarAlumnosPorEvento(int idEvento) throws SQLException {
        List<Alumno> alumnos = new ArrayList<>();
        String sql = "SELECT a.idAlumno, u.nombre, u.email, u.fecha_Registro, "
                + "a.edad, a.carrera, a.fotoPerfil, a.ubicacion, a.biografia "
                + "FROM Evento_Participante ep "
                + "JOIN Alumno a ON ep.idUsuario = a.idUsuario "
                + "JOIN Usuario u ON a.idUsuario = u.idUsuario "
                + "WHERE ep.idEvento = ? AND u.estado = TRUE AND u.visible = TRUE";

        try (Connection conn = DBManager.getInstance().obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEvento);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Alumno alumno = new Alumno();

                // Datos de Usuario
                alumno.setId(rs.getInt("idAlumno"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setEmail(rs.getString("email"));
                alumno.setFechaRegistro(rs.getTimestamp("fecha_Registro").toLocalDateTime());

                // Datos específicos de Alumno
                alumno.setEdad(rs.getInt("edad"));
                alumno.setCarrera(rs.getString("carrera"));
                alumno.setFotoPerfil(rs.getString("fotoPerfil"));
                alumno.setUbicacion(rs.getString("ubicacion"));
                alumno.setBiografia(rs.getString("biografia"));

                alumnos.add(alumno);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar participantes por evento : ", e);
        }
        return alumnos;
    }

    @Override
    public boolean verificarParticipacion(int idEvento, int idUsuario) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Evento_Participante WHERE idEvento = ? AND idUsuario = ?";

        try (Connection conn = DBManager.getInstance().obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEvento);
            stmt.setInt(2, idUsuario);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    @Override
    public boolean cancelarParticipacion(int idEvento, int idUsuario) throws SQLException {
        String sql = "DELETE FROM Evento_Participante WHERE idEvento = ? AND idUsuario = ?";

        try (Connection conn = DBManager.getInstance().obtenerConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEvento);
            stmt.setInt(2, idUsuario);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        }
    }

    @Override
    public void registrarInteresesEvento(int idEvento, List<Integer> interesesIds) throws SQLException {
        if (interesesIds == null || interesesIds.isEmpty()) {
            return; // No hay intereses para registrar
        }

        String sql = "INSERT INTO Evento_Interes (id_evento, id_interes) VALUES (?, ?)";

        try (Connection conn = DBManager.getInstance().obtenerConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // Desactivar auto-commit para manejar como transacción
            conn.setAutoCommit(false);

            try {
                for (Integer idInteres : interesesIds) {
                    ps.setInt(1, idEvento);
                    ps.setInt(2, idInteres);
                    ps.addBatch();
                }

                int[] resultados = ps.executeBatch();
                conn.commit();

                // Verificar que todos los inserts fueron exitosos
                for (int resultado : resultados) {
                    if (resultado == PreparedStatement.EXECUTE_FAILED) {
                        throw new SQLException("Error al insertar uno o más intereses");
                    }
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    @Override
    public List<Interes> listarInteresesPorEvento(int idEvento) {
        List<Interes> intereses = new ArrayList<>();
        String sql = "SELECT i.* FROM Interes i "
                + "JOIN Evento_Interes ei ON i.idInteres = ei.id_interes "
                + "WHERE ei.id_evento = ?";

        try (Connection conn = DBManager.getInstance().obtenerConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEvento);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Interes interes = new Interes();
                interes.setId(rs.getInt("idInteres"));
                interes.setNombre(rs.getString("nombre"));
                intereses.add(interes);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar intereses por evento", e);
        }
        return intereses;
    }

    @Override
    public List<Evento> listarEventosPorInteres(int idInteres) throws SQLException {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT e.* FROM Evento e "
                + "JOIN Evento_Interes ei ON e.idEvento = ei.id_evento "
                + "WHERE ei.id_interes = ? AND e.estado = TRUE "
                + "GROUP BY e.idEvento";

        try (Connection conn = DBManager.getInstance().obtenerConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idInteres);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Evento evento = createFromResultSet(rs);
                eventos.add(evento);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar eventos por interés", e);
        }
        return eventos;
    }

    private Evento createBasicEventoFromResultSet(ResultSet rs) throws SQLException {
        Evento evento = new Evento();
        evento.setIdEvento(rs.getInt("idEvento"));
        evento.setNombre(rs.getString("nombre"));
        evento.setDescripcion(rs.getString("descripcion"));
        evento.setFechaString(rs.getString("fecha"));
        evento.setFechaFinString(rs.getString("fechaFin"));
        evento.setUbicacion(rs.getString("ubicacion"));
        evento.setEstado(rs.getBoolean("estado"));
        evento.setImagen(rs.getString("imagen"));

        int idCreador = rs.getInt("creador_id");
        Alumno creador = new Alumno();
        creador.setId(idCreador);
        evento.setCreador(creador);

        // Inicializar la lista de intereses vacía
        evento.setIntereses(new ArrayList<>());

        return evento;
    }

    /*Agregado hoy*/
    @Override
    public List<Evento> listarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Evento> eventos = new ArrayList<>();
        Map<Integer, Evento> eventoMap = new HashMap<>();

        // Consulta para obtener eventos en el rango de fechas
        String eventoQuery = "SELECT e.* FROM Evento e "
                + "WHERE e.fecha BETWEEN ? AND ? "
              //  + "AND e.estado = TRUE "
                + "ORDER BY e.fecha ASC";

        // Consulta para obtener intereses de los eventos en el rango
        String interesesQuery = "SELECT ei.id_evento, i.* FROM Interes i "
                + "JOIN Evento_Interes ei ON i.idInteres = ei.id_interes "
                + "JOIN Evento e ON ei.id_evento = e.idEvento "
                + "WHERE e.fecha BETWEEN ? AND ? "
                //+ "AND e.estado = TRUE "
                + "AND i.estado = TRUE";

        try (Connection conn = DBManager.getInstance().obtenerConexion(); PreparedStatement eventoStmt = conn.prepareStatement(eventoQuery); PreparedStatement interesStmt = conn.prepareStatement(interesesQuery)) {

            // Establecer parámetros para ambas consultas
            Timestamp inicio = Timestamp.valueOf(fechaInicio);
            Timestamp fin = Timestamp.valueOf(fechaFin);

            eventoStmt.setTimestamp(1, inicio);
            eventoStmt.setTimestamp(2, fin);

            interesStmt.setTimestamp(1, inicio);
            interesStmt.setTimestamp(2, fin);

            // Ejecutar consulta de eventos
            try (ResultSet eventoRs = eventoStmt.executeQuery()) {
                while (eventoRs.next()) {
                    Evento evento = mapearEvento(eventoRs);
                    eventos.add(evento);
                    eventoMap.put(evento.getIdEvento(), evento);
                }
            }

            // Ejecutar consulta de intereses
            try (ResultSet interesRs = interesStmt.executeQuery()) {
                while (interesRs.next()) {
                    int idEvento = interesRs.getInt("id_evento");
                    Interes interes = new Interes();
                    interes.setId(interesRs.getInt("idInteres"));
                    interes.setNombre(interesRs.getString("nombre"));
//                    interes.setDescripcion(interesRs.getString("descripcion"));

                    Evento evento = eventoMap.get(idEvento);
                    if (evento != null) {
                        evento.getIntereses().add(interes);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar eventos por rango de fechas", e);
        }

        return eventos;
    }

    private Evento mapearEvento(ResultSet rs) throws SQLException {
        Evento evento = new Evento();
        evento.setIdEvento(rs.getInt("idEvento"));
        evento.setNombre(rs.getString("nombre"));
        evento.setDescripcion(rs.getString("descripcion"));
        evento.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
        evento.setFechaFin(rs.getTimestamp("fechaFin").toLocalDateTime());
        evento.setUbicacion(rs.getString("ubicacion"));
        evento.setEstado(rs.getBoolean("estado"));
        evento.setImagen(rs.getString("imagen"));
        evento.setIntereses(new ArrayList<>()); // Inicializar lista vacía

        // Formatear fechas como strings
        evento.setFechaString(evento.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        evento.setFechaFinString(evento.getFechaFin().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        return evento;
    }

    @Override
    public boolean desactivarEventosPasados() {
        String sql = "UPDATE Evento SET estado = FALSE "
                + "WHERE estado = TRUE AND fechaFin < NOW()";

        try (Connection conn = DBManager.getInstance().obtenerConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Error al desactivar eventos pasados", e);
        }
    }

    @Override
    public int activarEventosEnCurso() {
        String sql = "UPDATE Evento SET estado = TRUE "
                + "WHERE estado = FALSE AND "
                + "fecha <= NOW() AND fechaFin >= NOW()";

        try (Connection conn = DBManager.getInstance().obtenerConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al activar eventos en curso", e);
        }
    }

}
