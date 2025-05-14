/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.daoimpl.Social;

import pucp.edu.pe.pucpconnect.domain.Social.Notificacion;
import pucp.edu.pe.pucpconnect.persistence.BaseDAOImpl;

import pucp.edu.pe.pucpconnect.persistence.dao.Social.NotificacionDAO;

import java.sql.*;
/**
 *
 * @author USUARIO
 */
public class NotificacionDAOImpl extends BaseDAOImpl<Notificacion> implements NotificacionDAO {
    @Override
    protected PreparedStatement getInsertPS(Connection conn, Notificacion n) throws SQLException {
        String sql = "INSERT INTO Notificacion "
                   + "(usuario_id, mensaje, tipo, fecha, estado, visto) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, n.getUsuarioId());
        ps.setString(2, n.getMensaje());
        ps.setString(3, n.getTipo());
        ps.setTimestamp(4, n.getFecha());
        ps.setBoolean(5, n.isEstado());
        ps.setBoolean(6, n.isVisto());
        return ps;
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Notificacion n) throws SQLException {
        String sql = "UPDATE Notificacion SET usuario_id=?, mensaje=?, tipo=?, fecha=?, estado=?, visto=? "
                   + "WHERE idNotificacion=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, n.getUsuarioId());
        ps.setString(2, n.getMensaje());
        ps.setString(3, n.getTipo());
        ps.setTimestamp(4, n.getFecha());
        ps.setBoolean(5, n.isEstado());
        ps.setBoolean(6, n.isVisto());
        ps.setInt(7, n.getIdNotificacion());
        return ps;
    }

    @Override
    protected PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException {
        String sql = "DELETE FROM Notificacion WHERE idNotificacion=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectByIdPS(Connection conn, Integer id) throws SQLException {
        String sql = "SELECT * FROM Notificacion WHERE idNotificacion=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
        return conn.prepareStatement("SELECT * FROM Notificacion");
    }

    @Override
    protected Notificacion createFromResultSet(ResultSet rs) throws SQLException {
        Notificacion n = new Notificacion();
        n.setIdNotificacion(rs.getInt("idNotificacion"));
        n.setUsuarioId(     rs.getInt("usuario_id") );
        n.setMensaje(       rs.getString("mensaje") );
        n.setTipo(          rs.getString("tipo") );
        n.setFecha(         rs.getTimestamp("fecha") );
        n.setEstado(        rs.getBoolean("estado") );
        n.setVisto(         rs.getBoolean("visto") );
        return n;
    }

    @Override
    protected void setId(Notificacion n, Integer id) {
        n.setIdNotificacion(id);
    }
}
