/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;
import pucp.edu.pe.pucpconnect.persistence.BaseDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.DBManager;
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
        String query = "SELECT * FROM Interes WHERE id = ?";
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
    
    @Override
    public List<Interes> listarInteresesUsuario(int idUsuario) throws SQLException {
        String query = "SELECT i.idInteres, i.nombre, i.descripcion, i.estado " +
                       "FROM Interes i " +
                       "INNER JOIN Alumno_Interes a ON i.idInteres = a.idInteres " +
                       "WHERE a.idUsuario = ?";

        List<Interes> intereses = new ArrayList<>();

        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Interes interes = new Interes();
                interes.setId(rs.getInt("idInteres"));
                interes.setNombre(rs.getString("nombre"));
                interes.setDescripcion(rs.getString("descripcion"));
                intereses.add(interes);
            }

            return intereses;

        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rethrow si necesitas que el WS lo capture
        }
    }
    
    @Override
    public void agregarInteresUsuario(List<Integer> nuevosIds, int idAlumno) throws SQLException {
        
        String selectQuery = "SELECT idInteres FROM Alumno_Interes WHERE idUsuario = ?";
        String insertQuery = "INSERT INTO Alumno_Interes (idUsuario, idInteres, estado) VALUES (?, ?, ?)";
        String deleteQuery = "DELETE FROM Alumno_Interes WHERE idUsuario = ? AND idInteres = ?";

        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
            conn.setAutoCommit(false);

            // Aseguramos que no haya duplicados
            Set<Integer> nuevosSet = new HashSet<>(nuevosIds);
            Set<Integer> actualesSet = new HashSet<>();

            // 1. Obtener los intereses actuales del usuario
            try (PreparedStatement psSelect = conn.prepareStatement(selectQuery)) {
                psSelect.setInt(1, idAlumno);
                try (ResultSet rs = psSelect.executeQuery()) {
                    while (rs.next()) {
                        actualesSet.add(rs.getInt("idInteres"));
                    }
                }
            }

            // 2. Eliminar intereses que ya no están seleccionados
            try (PreparedStatement psDelete = conn.prepareStatement(deleteQuery)) {
                for (int idActual : actualesSet) {
                    if (!nuevosSet.contains(idActual)) {
                        psDelete.setInt(1, idAlumno);
                        psDelete.setInt(2, idActual);
                        psDelete.addBatch();
                    }
                }
                psDelete.executeBatch();
            }

            // 3. Insertar nuevos intereses (solo si no existen)
            String checkExistsQuery = "SELECT COUNT(*) FROM Alumno_Interes WHERE idUsuario = ? AND idInteres = ?";
            try (PreparedStatement psInsert = conn.prepareStatement(insertQuery);
                 PreparedStatement psCheck = conn.prepareStatement(checkExistsQuery)) {

                for (int idNuevo : nuevosSet) {
                    psCheck.setInt(1, idAlumno);
                    psCheck.setInt(2, idNuevo);
                    try (ResultSet rs = psCheck.executeQuery()) {
                        if (rs.next() && rs.getInt(1) == 0) {
                            psInsert.setInt(1, idAlumno);
                            psInsert.setInt(2, idNuevo);
                            psInsert.setBoolean(3, true);
                            psInsert.addBatch();
                        }
                    }
                }
                psInsert.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
