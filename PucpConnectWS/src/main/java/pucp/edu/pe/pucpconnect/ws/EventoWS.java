package pucp.edu.pe.pucpconnect.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import java.sql.SQLException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import pucp.edu.pe.pucpconnect.business.AlumnoService;

import pucp.edu.pe.pucpconnect.business.EventoService;
import pucp.edu.pe.pucpconnect.business.impl.AlumnoServiceImpl;
import pucp.edu.pe.pucpconnect.business.impl.EventoServiceImpl;
import pucp.edu.pe.pucpconnect.domain.Social.Evento;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Social.EventoDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.AlumnoDAOImpl;

@WebService(serviceName = "EventoWS")
public class EventoWS {

    private final EventoService eventoService;
    private final EventoService eventSer;
    private final AlumnoService alumnoService;

    public EventoWS() {
        BaseDAO<Evento> eventoDAO = new EventoDAOImpl();
        this.eventoService = new EventoServiceImpl(eventoDAO);
        BaseDAO<Alumno> alumnoDAO = new AlumnoDAOImpl();
        this.alumnoService = new AlumnoServiceImpl(alumnoDAO);
        this.eventSer = new EventoServiceImpl();
    }

   
    @WebMethod(operationName = "crearEvento")
    public boolean crearEvento(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "fecha") String fechaStr,
            @WebParam(name = "fechaFin") String fechaFinStr,
            @WebParam(name = "ubicacion") String ubicacion,
            @WebParam(name = "creadorId") int creadorId,
            @WebParam(name = "interesesIds") String interesesIdsStr) {

        try {
            // Validar que el string de intereses no sea nulo o vacío
            if (interesesIdsStr == null || interesesIdsStr.trim().isEmpty()) {
                throw new WebServiceException("La lista de intereses no puede estar vacía");
            }

            // Convertir String a List<Integer> con manejo de errores
            List<Integer> interesesIds;
            try {
                interesesIds = Arrays.stream(interesesIdsStr.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                throw new WebServiceException("Formato inválido para interesesIds: debe ser números separados por comas", e);
            }

            // Validar que se obtuvieron intereses
            if (interesesIds.isEmpty()) {
                throw new WebServiceException("Debe especificar al menos un interés");
            }

            // Crear y configurar evento
            Evento evento = new Evento();
            evento.setNombre(nombre);
            evento.setDescripcion(descripcion);

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime fecha = LocalDateTime.parse(fechaStr, formatter);
                LocalDateTime fechaFin = LocalDateTime.parse(fechaFinStr, formatter);

                evento.setFecha(fecha);
                evento.setFechaFin(fechaFin);
            } catch (DateTimeParseException e) {
                throw new WebServiceException("Formato de fecha inválido. Use yyyy-MM-dd HH:mm:ss", e);
            }

            evento.setUbicacion(ubicacion);
            evento.setEstado(true);

            Alumno creador = alumnoService.buscarPorId(creadorId);
            if (creador == null) {
                throw new WebServiceException("No se encontró el alumno creador con ID: " + creadorId);
            }
            evento.setCreador(creador);

            // Guardar evento e intereses
            
            eventoService.crearEvento(evento, interesesIds);
            eventSer.registrar_Participacion_Evento(evento, creador);
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
            List<Evento> eventos = eventSer.listarEventos();
            return eventos;
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

    @WebMethod(operationName = "verificarParticipacionEvento")
    public boolean verificarParticipacionEvento(
            @WebParam(name = "idEvento") int idEvento,
            @WebParam(name = "idUsuario") int idUsuario) {
        try {

            return eventSer.verificarParticipacion(idEvento, idUsuario);
        } catch (Exception e) {
            throw new WebServiceException("Error al verificar participación: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "registrarParticipacionEvento")
    public boolean registrarParticipacionEvento(
            @WebParam(name = "idEvento") int idEvento,
            @WebParam(name = "idAlumno") int idAlumno) {
        try {

            Evento evento = eventoService.buscarPorId(idEvento);

            Alumno alumno = alumnoService.buscarPorId(idAlumno);

            eventSer.registrar_Participacion_Evento(evento, alumno);
            return true;
        } catch (Exception e) {
            throw new WebServiceException("Error al registrar participación en evento: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "listarParticipantesPorEvento")
    public List<Alumno> listarParticipantesPorEvento(
            @WebParam(name = "idEvento") int idEvento) {

        try {
            return eventSer.listarParticipantesPorEvento(idEvento);
        } catch (SQLException e) {
            throw new WebServiceException("Error al listar participantes del evento: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "cancelarParticipacionEvento")
    public boolean cancelarParticipacionEvento(
            @WebParam(name = "idEvento") int idEvento,
            @WebParam(name = "idUsuario") int idUsuario) {
        try {

            return eventSer.cancelarParticipacion(idEvento, idUsuario);
        } catch (Exception e) {
            throw new WebServiceException("Error al cancelar participación: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "listarInteresesPorEvento")
    public List<Interes> listarInteresesPorEvento(
            @WebParam(name = "idEvento") int idEvento) {
        try {
            return eventSer.listarInteresesPorEvento(idEvento);
        } catch (Exception e) {
            throw new WebServiceException("Error al listar eventos con intereses: " + e.getMessage(), e);
        }
    }
    
    @WebMethod(operationName = "listarEventosPorInteres")
    public List<Evento> listarEventosPorIntereses(
            @WebParam(name = "idEvento") int idInteres) {
        try {
            return eventSer.listarEventosPorInteres(idInteres);
        } catch (SQLException e) {
            throw new WebServiceException("Error al listar eventos con intereses: " + e.getMessage(), e);
        }
    }

}
