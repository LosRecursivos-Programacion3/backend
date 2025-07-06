/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.daoimpl.Social;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import pucp.edu.pe.pucpconnect.domain.Social.Post;
import pucp.edu.pe.pucpconnect.domain.Social.PostConAutor;
import pucp.edu.pe.pucpconnect.persistence.BaseDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.DBManager;
import pucp.edu.pe.pucpconnect.persistence.dao.Social.PostDAO;

/**
 *
 * @author USUARIO
 */
public class PostDAOImpl extends BaseDAOImpl<Post> implements PostDAO {
    @Override
    protected PreparedStatement getInsertPS(Connection conn, Post post) throws SQLException {
        String query = "INSERT INTO Post (contenido, fecha, estado, autor_id, imagen) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, post.getContenido());
        ps.setTimestamp(2, Timestamp.valueOf(post.getFecha()));
        ps.setBoolean(3, post.isEstado());
        ps.setInt(4, post.getAutor());
        ps.setString(5, post.getImagen());
        return ps;
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
    
    @Override
    public List<PostConAutor> obtenerPostsAmigosYPropios(int idUsuario) throws SQLException {
        List<PostConAutor> posts = new ArrayList<>();

        String sql = """
            SELECT 
                p.idPost,
                p.contenido,
                p.imagen,
                p.fecha,
                p.estado,
                p.autor_id,
                u.nombre AS nombre_autor,
                a.carrera AS carrera_autor,
                a.fotoPerfil AS foto_autor
            FROM Post p
            JOIN Alumno a ON p.autor_id = a.idAlumno
            JOIN Usuario u ON a.idUsuario = u.idUsuario
            WHERE p.autor_id = ?
               OR p.autor_id IN (
                   SELECT CASE 
                              WHEN am.idAlumnoUno = ? THEN am.idAlumnoDos
                              ELSE am.idAlumnoUno
                          END
                   FROM Amistades am
                   WHERE (am.idAlumnoUno = ? OR am.idAlumnoDos = ?)
                     AND am.estado = 1
               )
            ORDER BY p.fecha DESC
        """;

        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idUsuario);
            ps.setInt(3, idUsuario);
            ps.setInt(4, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                while (rs.next()) {
                    Timestamp ts = rs.getTimestamp("fecha");
                    String fechaStr = ts != null ? sdf.format(ts) : "";

                    PostConAutor post = new PostConAutor(
                        rs.getInt("idPost"),
                        rs.getString("contenido"),
                        rs.getString("imagen"),
                        fechaStr,
                        rs.getBoolean("estado"),
                        rs.getInt("autor_id"),
                        rs.getString("nombre_autor"),
                        rs.getString("carrera_autor"),
                        rs.getString("foto_autor")
                    );
                    posts.add(post);
                }
            }
        }

        return posts;
    }
}
