package pucp.edu.pe.pucpconnect.business;

import pucp.edu.pe.pucpconnect.domain.Social.Notificacion;
import java.util.List;

public interface NotificacionService {
    void crearNotificacion(Notificacion notificacion) throws Exception;

    void notificarMatch(int usuarioAId, int usuarioBId) throws Exception;
    void notificarMensajeRecibido(int emisorId, int receptorId, String contenido) throws Exception;
    void notificarSolicitudConexion(int solicitanteId, int destinatarioId) throws Exception;
    void notificarEventoSugerido(int usuarioId, String nombreEvento) throws Exception;
    
    List<Notificacion> listarNotificaciones(int idUsuario) throws Exception;
    void marcarComoVisto(int idNotificacion) throws Exception;
    void cambiarEstado(int idNotificacion, boolean estado) throws Exception;
}
