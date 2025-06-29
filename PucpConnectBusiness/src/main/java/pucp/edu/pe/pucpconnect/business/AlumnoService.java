package pucp.edu.pe.pucpconnect.business;

import java.sql.SQLException;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;

import java.util.List;
import pucp.edu.pe.pucpconnect.domain.Social.Amistad;
import pucp.edu.pe.pucpconnect.domain.Social.Evento;

public interface AlumnoService {

    void registrarAlumno(Alumno alumno) throws Exception;

    void actualizarBiografia(int idAlumno, String nuevaBio) throws Exception;

    void agregarInteres(int idAlumno, Interes interes) throws Exception;

    void unirseAEvento(Evento evento, Alumno alumno) throws Exception;

    Alumno buscarPorId(int id) throws Exception;

    List<Alumno> listarAlumnos();

    void eliminarAlumno(int id);

    boolean bloquearAlumno(int idAlumno, int idBloqueado) throws Exception ;

    boolean desbloquearAlumno(int idAlumno, int idBloqueado) throws Exception ;

    List<Integer> obtenerAlumnosBloqueados(int idAlumno) throws Exception ;
    
    public Alumno buscarPorIdUsuario(int idUsuario) throws Exception;
    
    public void modificarAlumno(Alumno alumno) throws Exception;
    
    public List<Alumno> listarAlumnosSugeridos(List<Interes> intereses, int idAlumno) throws Exception;
    
    public void enviarSolicitudAmistad(int idUsuario1, int idUsuario2) throws Exception;
    
    public List<Amistad> listarSolicitudesEnviadas(int idAlumno) throws Exception;
    
    public List<Amistad> listarSolicitudesRecibidas(int idAlumno) throws Exception;
    
    public void aceptarSolicitud(int idAmistad) throws Exception;
    
    public void rechazarSolicitud(int idAmistad) throws Exception;
    
    public void cancelarSolicitud(int idAmistad) throws Exception;
    
     public List<Alumno> obtenerAmigosPorUsuario(int usuarioId) throws SQLException;
}
