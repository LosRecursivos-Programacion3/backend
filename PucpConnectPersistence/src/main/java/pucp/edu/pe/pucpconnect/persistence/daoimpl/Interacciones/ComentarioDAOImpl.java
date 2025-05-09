/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.daoimpl.Interacciones;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pucp.edu.pe.pucpconnect.domain.Interacciones.Comentario;
import pucp.edu.pe.pucpconnect.persistence.BaseDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.DBManager;

import pucp.edu.pe.pucpconnect.persistence.dao.Interacciones.ComentarioDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Social.PostDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.AlumnoDAOImpl;

/**
 *
 * @author Irico17
 */
public class ComentarioDAOImpl extends BaseDAOImpl<Comentario> implements ComentarioDAO {
    
    private final AlumnoDAOImpl alumnoDAOimpl;
    private final PostDAOImpl postDAO;

    public ComentarioDAOImpl() {
        this.alumnoDAOimpl = new AlumnoDAOImpl(); // Cargar relación con Usuario
        this.postDAO = new PostDAOImpl(); // Cargar relación con Post
    }
    
    @Override
    protected PreparedStatement getInsertPS(Connection conn, Comentario comentario) throws SQLException {
        String query = "{CALL sp_registrar_comentario(?, ?, ?, ?)}"; // Procedimiento almacenado para insertar comentario
        CallableStatement cs = conn.prepareCall(query);
        cs.setInt(1, comentario.getAutor().getId());
        cs.setInt(2, comentario.getPost().getIdPost());
        cs.setString(3, comentario.getContenido());
        cs.setTimestamp(4, Timestamp.valueOf(comentario.getFechaComentario())); // Fecha del comentario
        cs.registerOutParameter(4, Types.INTEGER); // Para obtener el ID generado
        return cs;
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Comentario comentario) throws SQLException {
        String query = "{CALL sp_actualizar_comentario(?, ?, ?, ?)}"; // Procedimiento almacenado para actualizar comentario
        CallableStatement cs = conn.prepareCall(query);
        cs.setInt(1, comentario.getIdComentario());
        cs.setString(2, comentario.getContenido());
        cs.setTimestamp(3, Timestamp.valueOf(comentario.getFechaComentario()));
        return cs;
    }

    @Override
    protected PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException {
        String query = "{CALL sp_eliminar_comentario(?)}"; // Procedimiento almacenado para eliminar comentario
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectByIdPS(Connection conn, Integer id) throws SQLException {
        String query = "SELECT * FROM comentarios WHERE id_comentario = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
        String query = "SELECT * FROM comentarios";
        return conn.prepareStatement(query);
    }

    @Override
    protected Comentario createFromResultSet(ResultSet rs) throws SQLException {
        Comentario comentario = new Comentario();
        comentario.setIdComentario(rs.getInt("id_comentario"));
        comentario.setContenido(rs.getString("contenido"));
        comentario.setFechaComentario(rs.getTimestamp("fecha_comentario").toLocalDateTime());
        comentario.setEstado(rs.getBoolean("estado"));
        
        // Cargar relaciones con Autor (Alumno) y Post
        comentario.setAutor(alumnoDAOimpl.obtener(rs.getInt("autor_id")));  // Emisor del comentario
        comentario.setPost(postDAO.obtener(rs.getInt("post_id")));  // Post relacionado con el comentario
        
        return comentario;
    }

    @Override
    protected void setId(Comentario comentario, Integer id) {
        comentario.setIdComentario(id);
    }
    
    // Función adicional para obtener comentarios de un post utilizando procedimiento almacenado
    public List<Comentario> obtenerComentariosPorPost(int postId) {
        List<Comentario> comentarios = new ArrayList<>();
        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
            String query = "{CALL sp_obtener_comentarios_por_post(?)}"; // Procedimiento almacenado para obtener comentarios por post
            CallableStatement cs = conn.prepareCall(query);
            cs.setInt(1, postId);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                comentarios.add(createFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener comentarios por post", e);
        }
        return comentarios;
    }
}
