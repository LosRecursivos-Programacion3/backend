/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.persistence.BaseDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.DBManager;
import pucp.edu.pe.pucpconnect.persistence.dao.Usuarios.AlumnoDAO;

/**
 *
 * @author USUARIO
 */
public class AlumnoDAOImpl extends BaseDAOImpl<Alumno> implements AlumnoDAO {
    @Override
    protected PreparedStatement getInsertPS(Connection conn, Alumno alumno) throws SQLException {
        String query = "{CALL sp_registrar_alumno(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}"; // Procedimiento almacenado para insertar alumno
        CallableStatement cs = conn.prepareCall(query);
        cs.setString(1, alumno.getNombre());
        cs.setString(2, alumno.getPassword());
        cs.setTimestamp(3, Timestamp.valueOf(alumno.getFechaRegistro()));
        cs.setString(4, alumno.getEmail());
        cs.setInt(5, alumno.getEdad());
        cs.setString(6, alumno.getCarrera());
        cs.setString(7, alumno.getFotoPerfil());
        cs.setString(8, alumno.getUbicacion());
        cs.setString(9, alumno.getBiografia());
        cs.registerOutParameter(10, Types.INTEGER);
        return cs;
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Alumno alumno) throws SQLException {
        String query = "{CALL sp_actualizar_alumno(?, ?, ?, ?, ?, ?, ?, ?, ?)}"; // Procedimiento almacenado para actualizar alumno
        CallableStatement cs = conn.prepareCall(query);
        cs.setInt(1, alumno.getId());
        cs.setString(2, alumno.getNombre());
        cs.setString(3, alumno.getPassword());
        cs.setBoolean(4, alumno.getEstado());
        cs.setString(5, alumno.getEmail());
        cs.setInt(6, alumno.getEdad());
        cs.setString(7, alumno.getCarrera());
        cs.setString(8, alumno.getFotoPerfil());
        cs.setString(9, alumno.getBiografia());
        return cs;
    }

    @Override
    protected PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException {
        String query = "{CALL sp_eliminar_alumno(?)}"; // Procedimiento almacenado para eliminar alumno
        CallableStatement ps = conn.prepareCall(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectByIdPS(Connection conn, Integer id) throws SQLException {
        String query = "SELECT * FROM Usuario u INNER JOIN Alumno a ON u.idUsuario = a.idAlumno WHERE u.idUsuario = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
        String query = "SELECT * FROM Alumno";
        return conn.prepareStatement(query);
    }

    @Override
    protected Alumno createFromResultSet(ResultSet rs) throws SQLException {
        Alumno alumno = new Alumno();
        alumno.setId(rs.getInt("idAlumno"));
        alumno.setNombre(rs.getString("nombre"));
        alumno.setPassword(rs.getString("password"));
        alumno.setEstado(rs.getBoolean("estado"));
        alumno.setFechaRegistro(rs.getTimestamp("fecha_Registro").toLocalDateTime());
        alumno.setEmail(rs.getString("email"));
        alumno.setEdad(rs.getInt("edad"));
        alumno.setCarrera(rs.getString("carrera"));
        alumno.setFotoPerfil(rs.getString("fotoPerfil"));
        alumno.setUbicacion(rs.getString("biografia"));
        return alumno;
    }

    @Override
    protected void setId(Alumno alumno, Integer id) {
        alumno.setId(id);
    }

    @Override
    public boolean bloquearAlumno(int idAlumno, int idBloqueado) {
        String sql = "INSERT INTO Alumnos_bloqueados (id_Alumno, id_Alumno_bloqueado, fecha) VALUES (?, ?, NOW())";
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idAlumno);
            pstmt.setInt(2, idBloqueado);
            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean desbloquearAlumno(int idAlumno, int idBloqueado) {
        String sql = "DELETE FROM Alumnos_bloqueados WHERE id_Alumno = ? AND id_Alumno_bloqueado = ?";
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idAlumno);
            pstmt.setInt(2, idBloqueado);
            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    
    @Override
    public List<Integer> obtenerAlumnosBloqueados(int idAlumno) {
        List<Integer> idsBloqueados = new ArrayList<>();
        String sql = "SELECT id_Alumno_bloqueado FROM Alumnos_bloqueados WHERE id_Alumno = ?";

        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idAlumno);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                idsBloqueados.add(rs.getInt("id_Alumno_bloqueado"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idsBloqueados;
    }

}
