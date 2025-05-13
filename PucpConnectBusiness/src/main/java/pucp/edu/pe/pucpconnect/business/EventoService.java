package pucp.edu.pe.pucpconnect.business;

import pucp.edu.pe.pucpconnect.domain.Social.Evento;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;

import java.util.List;

public interface EventoService {

    void crearEvento(Evento evento) throws Exception;

    void unirseAEvento(Evento evento, Alumno alumno) throws Exception;

    void cancelarParticipacion(Evento evento, Alumno alumno) throws Exception;

    List<Evento> listarEventos();

    Evento buscarPorId(int idEvento) throws Exception;
}
