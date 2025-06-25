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
import pucp.edu.pe.pucpconnect.persistence.dao.Social.NotificacionDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Social.NotificacionDAOImpl;

/**
 * Web Service para exponer las operaciones de notificaciones.
 */
@WebService(serviceName = "NotificacionWS")
public class NotificacionWS {

    private final NotificacionService notificacionService;

    public NotificacionWS() {
        NotificacionDAO dao = new NotificacionDAOImpl();
        this.notificacionService = new NotificacionServiceImpl(dao);
    }

    @WebMethod(operationName = "enviarNotificacion")
    public boolean enviarNotificacion(
            @WebParam(name = "usuarioId") int usuarioId,
            @WebParam(name = "mensaje") String mensaje,
            @WebParam(name = "tipo") String tipo
    ) {
        try {
            Notificacion n = new Notificacion();
            n.setUsuarioId(usuarioId);
            n.setMensaje(mensaje);
            n.setTipo(tipo);
            n.setFecha(new Timestamp(System.currentTimeMillis()));
            n.setEstado(true);
            n.setVisto(false);

            notificacionService.enviarNotificacion(n);
            return true;
        } catch (Exception e) {
            throw new WebServiceException("Error al enviar notificación: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "listarNotificacionesPorUsuario")
    public List<Notificacion> listarNotificacionesPorUsuario(
            @WebParam(name = "idUsuario") int idUsuario
    ) {
        try {
            return notificacionService.listarNotificacionesPorUsuario(idUsuario);
        } catch (Exception e) {
            throw new WebServiceException("Error al listar notificaciones: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "marcarComoVisto")
    public boolean marcarComoVisto(
            @WebParam(name = "idNotificacion") int idNotificacion
    ) {
        try {
            notificacionService.marcarComoVisto(idNotificacion);
            return true;
        } catch (Exception e) {
            throw new WebServiceException("Error al marcar como visto: " + e.getMessage(), e);
        }
    }
}
