package pucp.edu.pe.pucpconnect.business.impl;

import pucp.edu.pe.pucpconnect.business.EventoService;
import pucp.edu.pe.pucpconnect.domain.Social.Evento;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;

import java.time.LocalDateTime;
import java.util.List;

public class EventoServiceImpl implements EventoService {

    private BaseDAO<Evento> eventoDAO;

    public EventoServiceImpl(BaseDAO<Evento> eventoDAO) {
        this.eventoDAO = eventoDAO;
    }

    @Override
    public void crearEvento(Evento evento) throws Exception {
        if (evento == null)
            throw new Exception("El evento no puede ser nulo.");

        if (evento.getNombre() == null || evento.getNombre().isBlank())
            throw new Exception("El nombre del evento es obligatorio.");

        if (evento.getFecha() == null || evento.getFecha().isBefore(LocalDateTime.now()))
            throw new Exception("La fecha del evento debe ser futura.");

        eventoDAO.agregar(evento);
    }

    @Override
    public void unirseAEvento(Evento evento, Alumno alumno) throws Exception {
        if (evento == null || alumno == null)
            throw new Exception("Evento y alumno no pueden ser nulos.");

        if (evento.getParticipantes().contains(alumno))
            throw new Exception("El alumno ya está inscrito en este evento.");

        evento.agregarParticipante(alumno);
        eventoDAO.actualizar(evento);
    }

    @Override
    public void cancelarParticipacion(Evento evento, Alumno alumno) throws Exception {
        if (evento == null || alumno == null)
            throw new Exception("Evento y alumno no pueden ser nulos.");

        if (!evento.getParticipantes().contains(alumno))
            throw new Exception("El alumno no está inscrito en este evento.");

        evento.eliminarParticipante(alumno);
        eventoDAO.actualizar(evento);
    }

    @Override
    public List<Evento> listarEventos() {
        return eventoDAO.listarTodos();
    }

    @Override
    public Evento buscarPorId(int idEvento) throws Exception {
        Evento e = eventoDAO.obtener(idEvento);
        if (e == null)
            throw new Exception("No se encontró el evento con ID: " + idEvento);
        return e;
    }
}
