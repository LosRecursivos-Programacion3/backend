/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.daoimpl.Social;

import java.sql.*;
import pucp.edu.pe.pucpconnect.domain.Social.Post;
import pucp.edu.pe.pucpconnect.persistence.BaseDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.dao.Social.PostDAO;

/**
 *
 * @author USUARIO
 */
public class PostDAOImpl extends BaseDAOImpl<Post> implements PostDAO {
    @Override
    protected PreparedStatement getInsertPS(Connection conn, Post post) throws SQLException {
        String query = "{CALL sp_registrar_post(?, ?, ?, ?, ?)}"; // Procedimiento almacenado para insertar post
        CallableStatement cs = conn.prepareCall(query);
        cs.setInt(4, post.getAutor().getId());
        cs.setString(1, post.getContenido());
        cs.setTimestamp(2, Timestamp.valueOf(post.getFecha())); // Fecha del post
        cs.setBoolean(3, post.isEstado());
        cs.registerOutParameter(5, Types.INTEGER); // Para obtener el ID generado
        return cs;
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Post post) throws SQLException {
        String query = "{CALL sp_actualizar_post(?, ?, ?)}"; // Procedimiento almacenado para actualizar post
        CallableStatement cs = conn.prepareCall(query);
        cs.setInt(1, post.getIdPost());
        cs.setString(2, post.getContenido()); // Nuevo contenido del post
        cs.setBoolean(3, post.isEstado());   // Estado del post
        return cs;
    }

    @Override
    protected PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException {
        String query = "{CALL sp_eliminar_post(?)}"; // Procedimiento almacenado para eliminar post
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectByIdPS(Connection conn, Integer id) throws SQLException {
        String query = "SELECT * FROM Post WHERE idPost = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
        String query = "SELECT * FROM Post";
        return conn.prepareStatement(query);
    }

    @Override
    protected Post createFromResultSet(ResultSet rs) throws SQLException {
        Post post = new Post();
        post.setIdPost(rs.getInt("idPost"));
        post.setContenido(rs.getString("contenido"));
        post.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
        post.setEstado(rs.getBoolean("estado"));
        return post;
    }

    @Override
    protected void setId(Post post, Integer id) {
        post.setIdPost(id);
    }
}
