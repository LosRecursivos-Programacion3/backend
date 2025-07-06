/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.daoimpl.Interacciones;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pucp.edu.pe.pucpconnect.domain.Interacciones.Mensaje;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.persistence.BaseDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.DBManager;
import pucp.edu.pe.pucpconnect.persistence.dao.Interacciones.MensajeDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.AlumnoDAOImpl;

/**
 *
 * @author irico17
 */
public class MensajeDAOImpl extends BaseDAOImpl<Mensaje> implements MensajeDAO {
    
    private final AlumnoDAOImpl usuarioEmisorDAOImpl, usuarioReceptorDAOImpl;
    
    public MensajeDAOImpl(){
        this.usuarioEmisorDAOImpl = new AlumnoDAOImpl();
        this.usuarioReceptorDAOImpl = new AlumnoDAOImpl();
    }
    
    @Override
    protected PreparedStatement getInsertPS(Connection conn, Mensaje mensaje) throws SQLException {
        // Insert directo, sin SP
        String sql = "INSERT INTO Mensaje "
                   + "(emisor_id, receptor_id, contenido, fechaEnvio, estado) "
                   + "VALUES (?, ?, ?, ?, ?)";
        // Indica que queremos recuperar la clave generada por AUTO_INCREMENT
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt      (1, mensaje.getEmisor().getId());
        ps.setInt      (2, mensaje.getReceptor().getId());
        ps.setString   (3, mensaje.getContenido());
        ps.setTimestamp(4, Timestamp.valueOf(mensaje.getFechaEnvio()));
        ps.setBoolean  (5, mensaje.isEstado());
        return ps;
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Mensaje mensaje) throws SQLException {
        String query = "{CALL sp_actualizar_mensaje(?, ?, ?)}"; // Procedimiento almacenado para actualizar mensaje
        CallableStatement cs = conn.prepareCall(query);
        cs.setInt(1, mensaje.getIdMensaje());
        cs.setString(2, mensaje.getContenido());
        cs.setTimestamp(3, Timestamp.valueOf(mensaje.getFechaEnvio()));
        return cs;
    }

    @Override
    protected PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException {
        String query = "{CALL sp_eliminar_mensaje(?)}"; // Procedimiento almacenado para eliminar mensaje
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectByIdPS(Connection conn, Integer id) throws SQLException {
        String query = "SELECT * FROM mensajes WHERE id_mensaje = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
        String query = "SELECT * FROM mensajes";
        return conn.prepareStatement(query);
    }

    @Override
    protected Mensaje createFromResultSet(ResultSet rs) throws SQLException {
        Mensaje mensaje = new Mensaje();
        mensaje.setIdMensaje(rs.getInt("id_mensaje"));
        mensaje.setContenido(rs.getString("contenido"));
        mensaje.setFechaEnvio(rs.getTimestamp("fecha_envio").toLocalDateTime());
        mensaje.setEstado(rs.getBoolean("estado"));
        
        mensaje.setEmisor(usuarioEmisorDAOImpl.obtener(rs.getInt("id_emisor")));
        mensaje.setReceptor(usuarioEmisorDAOImpl.obtener(rs.getInt("id_receptor")));
        
        return mensaje;
    }

    @Override
    protected void setId(Mensaje mensaje, Integer id) {
        mensaje.setIdMensaje(id);
    }
    
    // Función adicional para obtener mensajes entre dos usuarios utilizando procedimiento almacenado
    public List<Mensaje> obtenerMensajesEntreUsuarios(int emisorId, int receptorId) {
    String sql = "SELECT m.idMensaje, m.contenido, m.fechaEnvio, m.estado, " +
                 "       m.emisor_id, m.receptor_id " +
                 "FROM Mensaje m " +
                 "WHERE (m.emisor_id = ? AND m.receptor_id = ?) " +
                 "   OR (m.emisor_id = ? AND m.receptor_id = ?) " +
                 "ORDER BY m.fechaEnvio ASC";

    List<Mensaje> lista = new ArrayList<>();
    try (Connection conn = DBManager.getInstance().obtenerConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, emisorId);
        ps.setInt(2, receptorId);
        ps.setInt(3, receptorId);
        ps.setInt(4, emisorId);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Mensaje m = new Mensaje();
                m.setIdMensaje(rs.getInt("idMensaje"));
                m.setContenido(rs.getString("contenido"));
                m.setFechaEnvio(rs.getTimestamp("fechaEnvio").toLocalDateTime());
                m.setEstado(rs.getBoolean("estado"));
                // Asume que tienes métodos para cargar sólo el ID de emisor/receptor
                Alumno em = new Alumno(); em.setIdAlumno(rs.getInt("emisor_id"));
                Alumno rec = new Alumno(); rec.setIdAlumno(rs.getInt("receptor_id"));
                m.setEmisor(em);
                m.setReceptor(rec);
                lista.add(m);
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException("Error al obtener mensajes entre usuarios", e);
    }
    return lista;
    }
}
