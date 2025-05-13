package pucp.edu.pe.pucpconnect.business;

import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;

import java.util.List;

public class AlumnoService {

    private BaseDAO<Alumno> alumnoDAO;

    public AlumnoService(BaseDAO<Alumno> alumnoDAO) {
        this.alumnoDAO = alumnoDAO;
    }

    /**
     * RF01: Registrar un nuevo alumno
     */
    public void registrarAlumno(Alumno alumno) throws Exception {
        if (alumno == null)
            throw new Exception("El objeto Alumno no puede ser nulo.");

        if (alumno.getNombre() == null || alumno.getNombre().isBlank())
            throw new Exception("El nombre del alumno es obligatorio.");

        if (alumno.getEmail() == null || !alumno.getEmail().endsWith("@pucp.edu.pe"))
            throw new Exception("El correo debe ser institucional (@pucp.edu.pe).");

        if (alumno.getEdad() < 17)
            throw new Exception("El alumno debe tener al menos 17 años para registrarse.");

        alumnoDAO.agregar(alumno);
    }

    /**
     * RF03: Actualizar la biografía del alumno
     */
    public void actualizarBiografia(int idAlumno, String nuevaBio) throws Exception {
        Alumno alumno = alumnoDAO.obtener(idAlumno);

        if (alumno == null)
            throw new Exception("No se encontró al alumno con ID: " + idAlumno);

        if (nuevaBio == null || nuevaBio.length() > 250)
            throw new Exception("La biografía es inválida o demasiado larga.");

        alumno.setBiografia(nuevaBio);
        alumnoDAO.actualizar(alumno);
    }

    /**
     * RF03: Agregar un interés al alumno
     */
    public void agregarInteres(int idAlumno, Interes interes) throws Exception {
        Alumno alumno = alumnoDAO.obtener(idAlumno);

        if (alumno == null)
            throw new Exception("No se encontró al alumno con ID: " + idAlumno);

        if (interes == null)
            throw new Exception("El interés no puede ser nulo.");

        alumno.agregarInteres(interes);
        alumnoDAO.actualizar(alumno);
    }

    /**
     * Buscar alumno por ID
     */
    public Alumno buscarPorId(int id) throws Exception {
        Alumno alumno = alumnoDAO.obtener(id);
        if (alumno == null)
            throw new Exception("No se encontró al alumno.");
        return alumno;
    }

    /**
     * Listar todos los alumnos
     */
    public List<Alumno> listarAlumnos() {
        return alumnoDAO.listarTodos();
    }

    /**
     * Eliminar alumno por ID
     */
    public void eliminarAlumno(int id) {
        alumnoDAO.eliminar(id);
    }
}
