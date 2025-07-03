/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.daoimpl.Interacciones;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pucp.edu.pe.pucpconnect.domain.Interacciones.Mensaje;
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
        String query = "{CALL sp_registrar_mensaje(?, ?, ?, ?, ?)}"; // Procedimiento almacenado para insertar mensaje
        CallableStatement cs = conn.prepareCall(query);
        cs.setInt(1, mensaje.getEmisor().getId());
        cs.setInt(2, mensaje.getReceptor().getId());
        cs.setString(3, mensaje.getContenido());
        cs.setTimestamp(4, Timestamp.valueOf(mensaje.getFechaEnvio())); // Fecha de envío
        cs.registerOutParameter(5, Types.INTEGER); // Para obtener el ID generado
        return cs;
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
        List<Mensaje> mensajes = new ArrayList<>();
        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
            String query = "{CALL sp_obtener_mensajes_entre_usuarios(?, ?)}"; // Procedimiento almacenado para obtener mensajes entre usuarios
            CallableStatement cs = conn.prepareCall(query);
            cs.setInt(1, emisorId);
            cs.setInt(2, receptorId);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                mensajes.add(createFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener mensajes entre usuarios", e);
        }
        return mensajes;
    }
}
