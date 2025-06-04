package pucp.edu.pe.pucpconnect.business;

import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;
import pucp.edu.pe.pucpconnect.domain.Social.Evento;

import java.util.List;

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

    /*falta editarPerfil */
}
