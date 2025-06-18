package pucp.edu.pe.pucpconnect.business.impl;

import pucp.edu.pe.pucpconnect.business.NotificacionService;
import pucp.edu.pe.pucpconnect.domain.Social.Notificacion;
import pucp.edu.pe.pucpconnect.persistence.dao.Social.NotificacionDAO;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;

import java.util.List;

public class NotificacionServiceImpl implements NotificacionService {

    private NotificacionDAO notificacionDAO;

    public NotificacionServiceImpl(NotificacionDAO notificacionDAO) {
        this.notificacionDAO = notificacionDAO;
    }

    @Override
    public void enviarNotificacion(Notificacion notificacion) throws Exception {
        if (notificacion == null)
            throw new Exception("La notificación no puede ser nula.");
        if (notificacion.getMensaje() == null || notificacion.getMensaje().isBlank())
            throw new Exception("El mensaje de la notificación no puede estar vacío.");
        notificacionDAO.insertarNotificacion(notificacion);
    }

    @Override
    public List<Notificacion> listarNotificacionesPorUsuario(int idUsuario) {
        return notificacionDAO.obtenerNotificacionesPorUsuario(idUsuario);
    }

    @Override
    public void marcarComoVisto(int idNotificacion) throws Exception {
        boolean actualizado = notificacionDAO.marcarComoVisto(idNotificacion);
        if (!actualizado)
            throw new Exception("No se pudo actualizar el estado de la notificación.");
    }
}
