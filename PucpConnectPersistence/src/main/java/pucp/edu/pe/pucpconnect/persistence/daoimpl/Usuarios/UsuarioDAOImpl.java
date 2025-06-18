/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios;

import pucp.edu.pe.pucpconnect.domain.Usuarios.Usuario;
import pucp.edu.pe.pucpconnect.persistence.BaseDAOImpl;
import java.sql.*;
import pucp.edu.pe.pucpconnect.persistence.dao.Usuarios.UsuarioDAO;

/**
 *
 * @author irico17
 */
public class UsuarioDAOImpl extends BaseDAOImpl<Usuario> implements UsuarioDAO {
    @Override
    protected PreparedStatement getInsertPS(Connection conn, Usuario usuario) throws SQLException {
        String query = "{CALL sp_registrar_usuario(?, ?, ?, ?, ?, ?,?)}"; // Procedimiento almacenado para insertar usuario
        CallableStatement cs = conn.prepareCall(query);
        cs.setString(1, usuario.getNombre());
        cs.setString(2, usuario.getPassword());
        cs.setBoolean(3, usuario.getEstado());
        cs.setTimestamp(4, Timestamp.valueOf(usuario.getFechaRegistro()));
        cs.setString(5, usuario.getEmail());
        cs.setBoolean(6, usuario.isVisible());
        cs.registerOutParameter(7, Types.INTEGER);
        return cs;
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Usuario usuario) throws SQLException {
        String query = "{CALL sp_actualizar_usuario(?, ?, ?, ?, ?, ?)}"; // Procedimiento almacenado para actualizar usuario
        CallableStatement cs = conn.prepareCall(query);
        cs.setInt(1, usuario.getId());
        cs.setString(2, usuario.getNombre());
        cs.setString(3, usuario.getPassword());
        cs.setBoolean(4, usuario.getEstado());
        cs.setString(5, usuario.getEmail());
        cs.setBoolean(6, usuario.isVisible());
        return cs;
    }

    @Override
    protected PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException {
        String query = "{CALL sp_eliminar_usuario(?)}"; // Procedimiento almacenado para eliminar usuario
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectByIdPS(Connection conn, Integer id) throws SQLException {
        String query = "SELECT * FROM Usuario WHERE idUsuario = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
        String query = "SELECT * FROM Usuario";
        return conn.prepareStatement(query);
    }

    @Override
    protected Usuario createFromResultSet(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("idUsuario"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setPassword(rs.getString("password"));
        usuario.setEstado(rs.getBoolean("estado"));
        usuario.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
        usuario.setEmail(rs.getString("email"));
        usuario.setVisible(rs.getBoolean("visible"));
        return usuario;
    }

    @Override
    protected void setId(Usuario usuario, Integer id) {
        usuario.setId(id);
    }

}
