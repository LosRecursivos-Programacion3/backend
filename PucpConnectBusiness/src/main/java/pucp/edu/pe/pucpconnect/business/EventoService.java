package pucp.edu.pe.pucpconnect.business;

import java.sql.SQLException;
import pucp.edu.pe.pucpconnect.domain.Social.Evento;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;

import java.util.List;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;

public interface EventoService {

    public void crearEvento(Evento evento, List<Integer> intereses) throws Exception;

    void unirseAEvento(Evento evento, Alumno alumno) throws Exception;

    void cancelarParticipacion(Evento evento, Alumno alumno) throws Exception;

    List<Evento> listarEventos();

    Evento buscarPorId(int idEvento) throws Exception;

    public void registrar_Participacion_Evento(Evento evento, Alumno alumno );
    
    public List<Alumno> listarParticipantesPorEvento(int idEvento) throws SQLException;
    
    public boolean verificarParticipacion(int idEvento, int idUsuario);
    
    public boolean cancelarParticipacion(int idEvento, int idUsuario);
    
     public List<Interes> listarInteresesPorEvento(int idEvento) ;
     
     public List<Evento>listarEventosPorInteres(int id_interes) throws SQLException;
}