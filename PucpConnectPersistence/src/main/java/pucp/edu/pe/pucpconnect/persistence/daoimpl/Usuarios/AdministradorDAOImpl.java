/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios;

import java.sql.*;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Administrador;
import pucp.edu.pe.pucpconnect.persistence.BaseDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.dao.Usuarios.AdministradorDAO;

/**
 *
 * @author irico17
 */
public class AdministradorDAOImpl extends BaseDAOImpl<Administrador> implements AdministradorDAO {
    @Override
    protected PreparedStatement getInsertPS(Connection conn, Administrador administrador) throws SQLException {
        String query = "{CALL sp_registrar_administrador(?, ?, ?, ?, ?, ?)}"; // Procedimiento almacenado para insertar administrador
        CallableStatement cs = conn.prepareCall(query);
        cs.setString(1, administrador.getNombre());
        cs.setString(2, administrador.getPassword());
        cs.setBoolean(3, administrador.getEstado());
        cs.setTimestamp(4, Timestamp.valueOf(administrador.getFechaRegistro()));
        cs.setString(5, administrador.getEmail());
        cs.registerOutParameter(5, Types.INTEGER);
        return cs;
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Administrador administrador) throws SQLException {
        String query = "{CALL sp_actualizar_administrador(?, ?, ?, ?, ?, ?)}"; // Procedimiento almacenado para actualizar administrador
        CallableStatement cs = conn.prepareCall(query);
        cs.setInt(1, administrador.getId());
        cs.setString(2, administrador.getNombre());
        cs.setString(3, administrador.getPassword());
        cs.setBoolean(4, administrador.getEstado());
        cs.setString(5, administrador.getEmail());
        return cs;
    }

    @Override
    protected PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException {
        String query = "{CALL sp_eliminar_administrador(?)}"; // Procedimiento almacenado para eliminar administrador
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectByIdPS(Connection conn, Integer id) throws SQLException {
        String query = "SELECT * FROM administradores WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
        String query = "SELECT * FROM administradores";
        return conn.prepareStatement(query);
    }

    @Override
    protected Administrador createFromResultSet(ResultSet rs) throws SQLException {
        Administrador administrador = new Administrador();
        administrador.setId(rs.getInt("id"));
        administrador.setNombre(rs.getString("nombre"));
        administrador.setPassword(rs.getString("password"));
        administrador.setEstado(rs.getBoolean("estado"));
        administrador.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
        administrador.setEmail(rs.getString("email"));
        return administrador;
    }

    @Override
    protected void setId(Administrador administrador, Integer id) {
        administrador.setId(id);
    }
}
