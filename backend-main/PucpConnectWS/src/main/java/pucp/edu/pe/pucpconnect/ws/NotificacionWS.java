package pucp.edu.pe.pucpconnect.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;

import java.util.List;

import pucp.edu.pe.pucpconnect.business.NotificacionService;
import pucp.edu.pe.pucpconnect.business.impl.NotificacionServiceImpl;
import pucp.edu.pe.pucpconnect.domain.Social.Notificacion;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Social.NotificacionDAOImpl;

@WebService(serviceName = "NotificacionWS")
public class NotificacionWS {
    private final NotificacionService notificacionService;

    public NotificacionWS() {
        BaseDAO<Notificacion> dao = new NotificacionDAOImpl();
        notificacionService = new NotificacionServiceImpl(dao);
    }

    @WebMethod(operationName = "obtenerNotificaciones")
    public List<Notificacion> obtenerNotificaciones(
            @WebParam(name = "idUsuario") int idUsuario) {
        try {
            return notificacionService.listarNotificaciones(idUsuario);
        } catch (Exception e) {
            throw new WebServiceException("Error al obtener notificaciones: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "marcarComoVisto")
    public boolean marcarComoVisto(
            @WebParam(name = "idNotificacion") int idNotificacion) {
        try {
            notificacionService.marcarComoVisto(idNotificacion);
            return true;
        } catch (Exception e) {
            throw new WebServiceException("Error al marcar como visto: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "cambiarEstado")
    public boolean cambiarEstado(
            @WebParam(name = "idNotificacion") int idNotificacion,
            @WebParam(name = "estado") boolean estado) {
        try {
            notificacionService.cambiarEstado(idNotificacion, estado);
            return true;
        } catch (Exception e) {
            throw new WebServiceException("Error al cambiar estado: " + e.getMessage(), e);
        }
    }
}
