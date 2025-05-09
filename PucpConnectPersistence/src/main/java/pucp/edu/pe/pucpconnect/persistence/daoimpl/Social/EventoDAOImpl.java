/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.daoimpl.Social;

import java.sql.*;
import pucp.edu.pe.pucpconnect.domain.Social.Evento;
import pucp.edu.pe.pucpconnect.persistence.BaseDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.dao.Social.EventoDAO;

/**
 *
 * @author irico17
 */
public class EventoDAOImpl extends BaseDAOImpl<Evento> implements EventoDAO {
    @Override
    protected PreparedStatement getInsertPS(Connection conn, Evento evento) throws SQLException {
        String query = "{CALL sp_registrar_evento(?, ?, ?, ?, ?, ?, ?)}"; // Procedimiento almacenado para insertar evento
        CallableStatement cs = conn.prepareCall(query);
        cs.setString(1, evento.getNombre());
        cs.setString(2, evento.getDescripcion());
        cs.setTimestamp(3, Timestamp.valueOf(evento.getFecha())); // Fecha del evento
        cs.setString(4, evento.getUbicacion());
        cs.setBoolean(5, evento.isEstado());
        cs.setInt(6, evento.getCreador().getId()); // Creador del evento
        cs.registerOutParameter(7, Types.INTEGER); // Para obtener el ID generado
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
        String query = "SELECT * FROM eventos WHERE id_evento = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
        String query = "SELECT * FROM eventos";
        return conn.prepareStatement(query);
    }

    @Override
    protected Evento createFromResultSet(ResultSet rs) throws SQLException {
        Evento evento = new Evento();
        evento.setIdEvento(rs.getInt("id_evento"));
        evento.setNombre(rs.getString("nombre"));
        evento.setDescripcion(rs.getString("descripcion"));
        evento.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
        evento.setUbicacion(rs.getString("ubicacion"));
        evento.setEstado(rs.getBoolean("estado"));
        return evento;
    }

    @Override
    protected void setId(Evento evento, Integer id) {
        evento.setIdEvento(id);
    }
}
