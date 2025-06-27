package pucp.edu.pe.pucpconnect.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;

import java.time.LocalDateTime;
import java.util.List;

import pucp.edu.pe.pucpconnect.business.EventoService;
import pucp.edu.pe.pucpconnect.business.impl.EventoServiceImpl;
import pucp.edu.pe.pucpconnect.domain.Social.Evento;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Social.EventoDAOImpl;

@WebService(serviceName = "EventoWS")
public class EventoWS {

    private final EventoService eventoService;

    public EventoWS() {
        BaseDAO<Evento> eventoDAO = new EventoDAOImpl();
        this.eventoService = new EventoServiceImpl(eventoDAO);
    }

    @WebMethod(operationName = "crearEvento")
    public boolean crearEvento(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "fecha") LocalDateTime fecha,
            @WebParam(name = "ubicacion") String ubicacion,
            @WebParam(name = "creadorId") int creadorId
    ) {
        try {
            Evento evento = new Evento();
            evento.setNombre(nombre);
            evento.setDescripcion(descripcion);
            evento.setFecha(fecha);
            evento.setUbicacion(ubicacion);
            evento.setEstado(true);

            Alumno creador = new Alumno();
            creador.setId(creadorId);
            evento.setCreador(creador);

            eventoService.crearEvento(evento);
            return true;
        } catch (Exception e) {
            throw new WebServiceException("Error al crear evento: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "unirseAEvento")
    public boolean unirseAEvento(
            @WebParam(name = "idEvento") int idEvento,
            @WebParam(name = "idAlumno") int idAlumno
    ) {
        try {
            Evento evento = eventoService.buscarPorId(idEvento);
            Alumno alumno = new Alumno();
            alumno.setId(idAlumno);

            eventoService.unirseAEvento(evento, alumno);
            return true;
        } catch (Exception e) {
            throw new WebServiceException("Error al unirse al evento: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "cancelarParticipacion")
    public boolean cancelarParticipacion(
            @WebParam(name = "idEvento") int idEvento,
            @WebParam(name = "idAlumno") int idAlumno
    ) {
        try {
            Evento evento = eventoService.buscarPorId(idEvento);
            Alumno alumno = new Alumno();
            alumno.setId(idAlumno);

            eventoService.cancelarParticipacion(evento, alumno);
            return true;
        } catch (Exception e) {
            throw new WebServiceException("Error al cancelar participación: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "listarEventos")
    public List<Evento> listarEventos() {
        try {
            return eventoService.listarEventos();
        } catch (Exception e) {
            throw new WebServiceException("Error al listar eventos: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "buscarEventoPorId")
    public Evento buscarEventoPorId(@WebParam(name = "idEvento") int idEvento) {
        try {
            return eventoService.buscarPorId(idEvento);
        } catch (Exception e) {
            throw new WebServiceException("Error al buscar evento por ID: " + e.getMessage(), e);
        }
    }
}
