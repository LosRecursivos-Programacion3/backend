package pucp.edu.pe.pucpconnect.business.impl;

import java.sql.SQLException;
import pucp.edu.pe.pucpconnect.business.EventoService;
import pucp.edu.pe.pucpconnect.domain.Social.Evento;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Social.EventoDAOImpl;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;
import pucp.edu.pe.pucpconnect.persistence.dao.Social.EventoDAO;

public class EventoServiceImpl implements EventoService {

    private BaseDAO<Evento> eventoDAO;
    private EventoDAO eventoDAOImpl;

    public EventoServiceImpl(BaseDAO<Evento> eventoDAO) {
        this.eventoDAO = eventoDAO;
    }

    public EventoServiceImpl() {
        this.eventoDAOImpl = new EventoDAOImpl();
    }

    @Override
    public void crearEvento(Evento evento, List<Integer> interesesIds) throws Exception {
        // Validaciones básicas
        if (evento == null) throw new Exception("El evento no puede ser nulo");
        if (interesesIds == null) throw new Exception("La lista de intereses no puede ser nula");
        
        // Validar fechas
        if (evento.getFecha() == null || evento.getFecha().isBefore(LocalDateTime.now())) {
            throw new Exception("La fecha del evento debe ser futura");
        }
        
        if (evento.getFechaFin() == null || evento.getFechaFin().isBefore(evento.getFecha())) {
            throw new Exception("La fecha de fin debe ser posterior a la de inicio");
        }

        // 1. Crear el evento (primera conexión)
        ((EventoDAOImpl)eventoDAO).agregarEvento(evento);
        // 2. Registrar intereses (segunda conexión por cada interés)
        if (!interesesIds.isEmpty()) {
        ((EventoDAOImpl) eventoDAO).registrarInteresesEvento(evento.getIdEvento(), interesesIds);
        }
    }

    @Override
    public void unirseAEvento(Evento evento, Alumno alumno) throws Exception {
        if (evento == null || alumno == null) {
            throw new Exception("Evento y alumno no pueden ser nulos.");
        }

        if (evento.getParticipantes() != null && !evento.getParticipantes().isEmpty()) {
            if (evento.getParticipantes().contains(alumno)) {
                throw new Exception("El alumno ya está inscrito en este evento.");
            }
        }

        evento.agregarParticipante(alumno);
        eventoDAO.actualizar(evento);

    }

    @Override
    public void cancelarParticipacion(Evento evento, Alumno alumno) throws Exception {
        if (evento == null || alumno == null) {
            throw new Exception("Evento y alumno no pueden ser nulos.");
        }

        if (!evento.getParticipantes().contains(alumno)) {
            throw new Exception("El alumno no está inscrito en este evento.");
        }

        evento.eliminarParticipante(alumno);
        eventoDAO.actualizar(evento);
    }

    @Override
    public List<Evento> listarEventos() {
        return eventoDAOImpl.listarTodosEventos();
    }

    @Override
    public Evento buscarPorId(int idEvento) throws Exception {
        
        Evento e = eventoDAO.obtener(idEvento);
        
        if (e == null) {
            throw new Exception("No se encontró el evento con ID: " + idEvento);
        }
        return e;
    }

    @Override
    public void registrar_Participacion_Evento(Evento evento, Alumno alumno) {
        evento.agregarParticipante(alumno);
        try {
            eventoDAOImpl.registrarParticipacionEvento(evento.getIdEvento(), alumno.getId());
        } catch (SQLException ex) {
            Logger.getLogger(EventoServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public List<Alumno> listarParticipantesPorEvento(int idEvento) throws SQLException {
        return eventoDAOImpl.listarAlumnosPorEvento(idEvento);
    }

    @Override
    public boolean verificarParticipacion(int idEvento, int idUsuario) {
        try {
            return eventoDAOImpl.verificarParticipacion(idEvento, idUsuario);
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar participación en evento", e);
        }
    }

    @Override
    public boolean cancelarParticipacion(int idEvento, int idUsuario) {
        try {
            // Verificar primero si existe la participación activa
            if (!eventoDAOImpl.verificarParticipacion(idEvento, idUsuario)) {
                return false;
            }
            return eventoDAOImpl.cancelarParticipacion(idEvento, idUsuario);
        } catch (SQLException e) {
            throw new RuntimeException("Error al cancelar participación en evento", e);
        }
    }
    
    @Override
    public List<Interes> listarInteresesPorEvento(int idEvento) {
        return eventoDAOImpl.listarInteresesPorEvento(idEvento);
    }
    @Override
    public List<Evento>listarEventosPorInteres(int id_interes) throws SQLException {
        return eventoDAOImpl.listarEventosPorInteres(id_interes);
    }
    
    @Override
    public List<Evento> listarEventosPorFechaInicio(LocalDateTime fechaInicio) {
        // Obtener eventos del día específico
        LocalDateTime inicioDia = fechaInicio.toLocalDate().atStartOfDay();
        LocalDateTime finDia = inicioDia.plusDays(1).minusSeconds(1);
        
        return listarEventosPorRangoFechas(inicioDia, finDia);
    }

    @Override
    public List<Evento> listarEventosPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Evento> eventos = eventoDAOImpl.listarPorRangoFechas(fechaInicio, fechaFin);
        
        // Formatear fechas como strings para la respuesta
        eventos.forEach(evento -> {
            evento.setFechaString(evento.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            evento.setFechaFinString(evento.getFechaFin().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        });
        
        return eventos;
    }

    @Override
    public boolean actualizarEstadosEventos() {
        // Desactivar eventos pasados
        boolean desactivados = eventoDAOImpl.desactivarEventosPasados();
        
        // Activar eventos en curso
        //int activados = ((EventoDAOImpl)eventoDAO).activarEventosEnCurso();
        
        return desactivados;
    }
    
    
}
