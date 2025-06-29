package pucp.edu.pe.pucpconnect.business.impl;

import pucp.edu.pe.pucpconnect.business.NotificacionService;
import pucp.edu.pe.pucpconnect.domain.Social.Notificacion;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;
import pucp.edu.pe.pucpconnect.persistence.dao.Social.NotificacionDAO;

import java.sql.Timestamp;
import java.util.List;

public class NotificacionServiceImpl implements NotificacionService {
    private final BaseDAO<Notificacion> notificacionDAO;

    public NotificacionServiceImpl(BaseDAO<Notificacion> notificacionDAO) {
        this.notificacionDAO = notificacionDAO;
    }

    @Override
    public void crearNotificacion(Notificacion n) throws Exception {
        if (n == null) 
            throw new Exception("Notificación no puede ser nula.");
        n.setFecha(new Timestamp(System.currentTimeMillis()));
        n.setEstado(true);
        n.setVisto(false);
        notificacionDAO.agregar(n);
    }

    @Override
    public void notificarMatch(int usuarioAId, int usuarioBId) throws Exception {
        // Para A
        Notificacion na = new Notificacion();
        na.setUsuarioId(usuarioAId);
        na.setTipo("MATCH");
        na.setMensaje("¡Tienes match con el usuario " + usuarioBId + "!");
        crearNotificacion(na);
        // Para B
        Notificacion nb = new Notificacion();
        nb.setUsuarioId(usuarioBId);
        nb.setTipo("MATCH");
        nb.setMensaje("¡Tienes match con el usuario " + usuarioAId + "!");
        crearNotificacion(nb);
    }

    @Override
    public void notificarMensajeRecibido(int emisorId, int receptorId, String contenido) throws Exception {
        if (emisorId <= 0 || receptorId <= 0) 
            throw new Exception("IDs de emisor o receptor inválidos.");
        Notificacion n = new Notificacion();
        n.setUsuarioId(receptorId);
        n.setTipo("MENSAJE");
        String texto = (contenido != null && !contenido.isBlank())
            ? "Nuevo mensaje de " + emisorId + ": \"" + contenido + "\""
            : "Nuevo mensaje de " + emisorId;
        n.setMensaje(texto);
        crearNotificacion(n);
    }

    @Override
    public void notificarSolicitudConexion(int solicitanteId, int destinatarioId) throws Exception {
        if (solicitanteId == destinatarioId) 
            throw new Exception("No puedes enviarte solicitud a ti mismo.");
        Notificacion n = new Notificacion();
        n.setUsuarioId(destinatarioId);
        n.setTipo("SOLICITUD_CONEXION");
        n.setMensaje("El usuario " + solicitanteId + " te ha enviado una solicitud de conexión.");
        crearNotificacion(n);
    }

    @Override
    public void notificarEventoSugerido(int usuarioId, String nombreEvento) throws Exception {
        if (usuarioId <= 0 || nombreEvento == null || nombreEvento.isBlank())
            throw new Exception("Datos de evento sugerido inválidos.");
        Notificacion n = new Notificacion();
        n.setUsuarioId(usuarioId);
        n.setTipo("EVENTO_SUGERIDO");
        n.setMensaje("Te sugerimos el evento: \"" + nombreEvento + "\"");
        crearNotificacion(n);
    }

    @Override
    public List<Notificacion> listarNotificaciones(int idUsuario) throws Exception {
        if (idUsuario <= 0) 
            throw new Exception("ID de usuario inválido.");
        NotificacionDAO dao = (NotificacionDAO) notificacionDAO;
        return dao.obtenerNotificacionesPorUsuario(idUsuario);
    }

    @Override
    public void marcarComoVisto(int idNotificacion) throws Exception {
        if (idNotificacion <= 0) 
            throw new Exception("ID de notificación inválido.");
        NotificacionDAO dao = (NotificacionDAO) notificacionDAO;
        boolean ok = dao.marcarComoVisto(idNotificacion);
        if (!ok) throw new Exception("Error al marcar la notificación como vista.");
    }

    @Override
    public void cambiarEstado(int idNotificacion, boolean estado) throws Exception {
        Notificacion existente = notificacionDAO.obtener(idNotificacion);
        if (existente == null) 
            throw new Exception("Notificación no encontrada: " + idNotificacion);
        existente.setEstado(estado);
        notificacionDAO.actualizar(existente);
    }
}
