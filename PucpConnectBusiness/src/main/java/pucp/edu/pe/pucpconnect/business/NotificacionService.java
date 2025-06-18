package pucp.edu.pe.pucpconnect.business;

import pucp.edu.pe.pucpconnect.domain.Social.Notificacion;

import java.util.List;

public interface NotificacionService {

    void enviarNotificacion(Notificacion notificacion) throws Exception;

    List<Notificacion> listarNotificacionesPorUsuario(int idUsuario);
    
    void marcarComoVisto(int idNotificacion) throws Exception;
}