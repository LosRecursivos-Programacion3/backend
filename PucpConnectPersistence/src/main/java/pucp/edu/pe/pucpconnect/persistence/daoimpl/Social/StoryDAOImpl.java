/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.daoimpl.Social;

import pucp.edu.pe.pucpconnect.persistence.BaseDAOImpl;

import pucp.edu.pe.pucpconnect.persistence.dao.Social.StoryDAO;

import java.sql.*;
import pucp.edu.pe.pucpconnect.domain.Social.Story;

/**
 *
 * @author USUARIO
 */
public class StoryDAOImpl extends BaseDAOImpl<Story> implements StoryDAO {
    @Override
    protected PreparedStatement getInsertPS(Connection conn, Story s) throws SQLException {
        String sql = "INSERT INTO Story (usuario_id, urlContenido, tipo, estado, fechaCreacion) "
                   + "VALUES (?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, s.getUsuarioId());
        ps.setString(2, s.getUrlContenido());
        ps.setString(3, s.getTipo());
        ps.setBoolean(4, s.isEstado());
        ps.setTimestamp(5, s.getFechaCreacion());
        return ps;
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Story s) throws SQLException {
        String sql = "UPDATE Story SET usuario_id=?, urlContenido=?, tipo=?, estado=?, fechaCreacion=? "
                   + "WHERE idStory=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, s.getUsuarioId());
        ps.setString(2, s.getUrlContenido());
        ps.setString(3, s.getTipo());
        ps.setBoolean(4, s.isEstado());
        ps.setTimestamp(5, s.getFechaCreacion());
        ps.setInt(6, s.getIdStory());
        return ps;
    }

    @Override
    protected PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException {
        String sql = "DELETE FROM Story WHERE idStory=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectByIdPS(Connection conn, Integer id) throws SQLException {
        String sql = "SELECT * FROM Story WHERE idStory=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
        return conn.prepareStatement("SELECT * FROM Story");
    }

    @Override
    protected Story createFromResultSet(ResultSet rs) throws SQLException {
        Story s = new Story();
        s.setIdStory(     rs.getInt("idStory") );
        s.setUsuarioId(   rs.getInt("usuario_id") );
        s.setUrlContenido(rs.getString("urlContenido") );
        s.setTipo(        rs.getString("tipo") );
        s.setEstado(      rs.getBoolean("estado") );
        s.setFechaCreacion(rs.getTimestamp("fechaCreacion") );
        return s;
    }

    @Override
    protected void setId(Story s, Integer id) {
        s.setIdStory(id);
    }
}
