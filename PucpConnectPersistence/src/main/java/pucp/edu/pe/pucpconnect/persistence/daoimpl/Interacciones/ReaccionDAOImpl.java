/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.daoimpl.Interacciones;

import java.sql.*;
import pucp.edu.pe.pucpconnect.domain.Interacciones.Reaccion;
import pucp.edu.pe.pucpconnect.domain.Interacciones.ReaccionTipo;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Usuario;
import pucp.edu.pe.pucpconnect.persistence.BaseDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.dao.Interacciones.ReaccionDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Social.EventoDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Social.PostDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.UsuarioDAOImpl;
/**
 *
 * @author irico17
 */
public class ReaccionDAOImpl extends BaseDAOImpl<Reaccion> implements ReaccionDAO {
    
    private final UsuarioDAOImpl usuarioDAOimpl;
    private final PostDAOImpl postDAO;
    private final EventoDAOImpl eventoDAO;
    private final ComentarioDAOImpl comentarioDAO;

    public ReaccionDAOImpl() {
        this.usuarioDAOimpl = new UsuarioDAOImpl(); // Cargar relación con Usuario
        this.postDAO = new PostDAOImpl(); // Cargar relación con Post
        this.eventoDAO = new EventoDAOImpl(); // Cargar relación con Evento
        this.comentarioDAO = new ComentarioDAOImpl(); // Cargar relación con Comentario
    }
    
    @Override
    protected PreparedStatement getInsertPS(Connection conn, Reaccion reaccion) throws SQLException {
        String query = "{CALL sp_registrar_reaccion(?, ?, ?, ?, ?)}"; // Procedimiento almacenado para insertar reacción
        CallableStatement cs = conn.prepareCall(query);
        cs.setInt(1, reaccion.getUsuario().getId());
        cs.setInt(2, reaccion.getTipo().ordinal()); // Reacción tipo como ordinal
        cs.setTimestamp(3, Timestamp.valueOf(reaccion.getFecha()));
        if (reaccion.getPost() != null) {
            cs.setInt(4, reaccion.getPost().getIdPost()); // ID del post
        } else if (reaccion.getComentario() != null) {
            cs.setInt(4, reaccion.getComentario().getIdComentario()); // ID del comentario
        } else if (reaccion.getEvento() != null) {
            cs.setInt(4, reaccion.getEvento().getIdEvento()); // ID del evento
        }
        cs.registerOutParameter(5, Types.INTEGER); // Para obtener el ID generado
        return cs;
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Reaccion reaccion) throws SQLException {
        String query = "{CALL sp_actualizar_reaccion(?, ?, ?, ?, ?)}"; // Procedimiento almacenado para actualizar reacción
        CallableStatement cs = conn.prepareCall(query);
        cs.setInt(1, reaccion.getId());
        cs.setInt(2, reaccion.getTipo().ordinal()); // Reacción tipo como ordinal
        cs.setTimestamp(3, Timestamp.valueOf(reaccion.getFecha()));
        return cs;
    }

    @Override
    protected PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException {
        String query = "{CALL sp_eliminar_reaccion(?)}"; // Procedimiento almacenado para eliminar reacción
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectByIdPS(Connection conn, Integer id) throws SQLException {
        String query = "SELECT * FROM reacciones WHERE id_reaccion = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
        String query = "SELECT * FROM reacciones";
        return conn.prepareStatement(query);
    }

    @Override
    protected Reaccion createFromResultSet(ResultSet rs) throws SQLException {
        Reaccion reaccion = new Reaccion();
        reaccion.setId(rs.getInt("id_reaccion"));
        reaccion.setTipo(ReaccionTipo.values()[rs.getInt("tipo")]);
        reaccion.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
        
        // Cargar relaciones con Post, Comentario, Evento
        if (rs.getInt("post_id") > 0) {
            reaccion.setPost(postDAO.obtener(rs.getInt("post_id")));
        } else if (rs.getInt("comentario_id") > 0) {
            reaccion.setComentario(comentarioDAO.obtener(rs.getInt("comentario_id")));
        } else if (rs.getInt("evento_id") > 0) {
            reaccion.setEvento(eventoDAO.obtener(rs.getInt("evento_id")));
        }
        
        // Cargar relación con el usuario (Alumno) y realizar el cast adecuado
        Usuario usuario = usuarioDAOimpl.obtener(rs.getInt("usuario_id"));
        if (usuario instanceof Alumno) {
            reaccion.setUsuario((Alumno) usuario);  // Hacemos el cast a Alumno
        }
        
        return reaccion;
    }

    @Override
    protected void setId(Reaccion reaccion, Integer id) {
        reaccion.setId(id);
    }
}
