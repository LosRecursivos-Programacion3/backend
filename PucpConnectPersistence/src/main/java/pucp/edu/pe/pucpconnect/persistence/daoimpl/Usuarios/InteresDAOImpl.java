/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios;

import java.sql.*;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;
import pucp.edu.pe.pucpconnect.persistence.BaseDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.dao.Usuarios.InteresDAO;

/**
 *
 * @author irico17
 */
public class InteresDAOImpl extends BaseDAOImpl<Interes> implements InteresDAO {
    @Override
    protected PreparedStatement getInsertPS(Connection conn, Interes interes) throws SQLException {
        String query = "{CALL sp_registrar_interes(?, ?, ?)}"; // Procedimiento almacenado para insertar interes
        CallableStatement cs = conn.prepareCall(query);
        cs.setString(1, interes.getNombre());
        cs.setString(2, interes.getDescripcion());
        cs.registerOutParameter(3, Types.INTEGER); // Para obtener el ID generado
        return cs;
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Interes interes) throws SQLException {
        String query = "{CALL sp_actualizar_interes(?, ?, ?)}"; // Procedimiento almacenado para actualizar interes
        CallableStatement cs = conn.prepareCall(query);
        cs.setInt(1, interes.getId());
        cs.setString(2, interes.getNombre());
        cs.setString(3, interes.getDescripcion());
        return cs;
    }

    @Override
    protected PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException {
        String query = "{CALL sp_eliminar_interes(?)}"; // Procedimiento almacenado para eliminar interes
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectByIdPS(Connection conn, Integer id) throws SQLException {
        String query = "SELECT * FROM Interes WHERE idInteres = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
        String query = "SELECT * FROM Interes";
        return conn.prepareStatement(query);
    }

    @Override
    protected Interes createFromResultSet(ResultSet rs) throws SQLException {
        Interes interes = new Interes();
        interes.setId(rs.getInt("idInteres"));
        interes.setNombre(rs.getString("nombre"));
        interes.setDescripcion(rs.getString("descripcion"));
        return interes;
    }

    @Override
    protected void setId(Interes interes, Integer id) {
        interes.setId(id); // Establece el ID del interes
    }
}
