package pucp.edu.pe.pucpconnect.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;

import java.sql.Timestamp;
import java.util.List;

import pucp.edu.pe.pucpconnect.business.NotificacionService;
import pucp.edu.pe.pucpconnect.business.impl.NotificacionServiceImpl;
import pucp.edu.pe.pucpconnect.domain.Social.Notificacion;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;
import pucp.edu.pe.pucpconnect.persistence.dao.Social.NotificacionDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Social.NotificacionDAOImpl;

@WebService(serviceName = "NotificacionWS", targetNamespace = "http://pucp.edu.pe/pucpconnect")
public class NotificacionWS {

    private final NotificacionService notificacionService;

    public NotificacionWS() {
        BaseDAO<Notificacion> notificacionDAO = new NotificacionDAOImpl();
        notificacionService = new NotificacionServiceImpl((NotificacionDAO) notificacionDAO);
    }
    /*
    @WebMethod(operationName = "enviarNotificacion")
    public boolean enviarNotificacion(
        @WebParam(name = "usuarioId") int usuarioId,
        @WebParam(name = "mensaje") String mensaje,
        @WebParam(name = "tipo") String tipo
    ) {
        try {
            Notificacion notificacion = new Notificacion();
            notificacion.setUsuarioId(usuarioId);
            notificacion.setMensaje(mensaje);
            notificacion.setTipo(tipo);
            notificacion.setFecha(new Timestamp(System.currentTimeMillis()));
            notificacion.setEstado(true);
            notificacion.setVisto(false);

            notificacionService.enviarNotificacion(notificacion);
            return true;
        } catch (Exception e) {
            throw new WebServiceException("Error al enviar la notificación: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "listarNotificacionesPorUsuario")
    public List<Notificacion> listarNotificacionesPorUsuario(@WebParam(name = "idUsuario") int idUsuario) {
        try {
            return notificacionService.listarNotificacionesPorUsuario(idUsuario);
        } catch (Exception e) {
            throw new WebServiceException("Error al listar notificaciones: " + e.getMessage(), e);
        }
    }*/

    @WebMethod(operationName = "marcarNotificacionComoVisto")
    public boolean marcarNotificacionComoVisto(@WebParam(name = "idNotificacion") int idNotificacion) {
        try {
            notificacionService.marcarComoVisto(idNotificacion);
            return true;
        } catch (Exception e) {
            throw new WebServiceException("Error al marcar como visto: " + e.getMessage(), e);
        }
    }
}
