package pucp.edu.pe.pucpconnect.business;

import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;

import java.util.List;

public interface AlumnoService {

    void registrarAlumno(Alumno alumno) throws Exception;

    void actualizarBiografia(int idAlumno, String nuevaBio) throws Exception;

    void agregarInteres(int idAlumno, Interes interes) throws Exception;

    Alumno buscarPorId(int id) throws Exception;

    List<Alumno> listarAlumnos();

    void eliminarAlumno(int id);

    /*falta editarPerfil */
}
