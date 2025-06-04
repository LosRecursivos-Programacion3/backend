package pucp.edu.pe.pucpconnect.business.impl;

import pucp.edu.pe.pucpconnect.business.AlumnoService;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;

import java.util.List;

public class AlumnoServiceImpl implements AlumnoService {

    private BaseDAO<Alumno> alumnoDAO;

    public AlumnoServiceImpl(BaseDAO<Alumno> alumnoDAO) {
        this.alumnoDAO = alumnoDAO;
    }

    @Override
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

    @Override
    public void actualizarBiografia(int idAlumno, String nuevaBio) throws Exception {
        Alumno alumno = alumnoDAO.obtener(idAlumno);
        if (alumno == null)
            throw new Exception("No se encontró al alumno con ID: " + idAlumno);

        if (nuevaBio == null || nuevaBio.length() > 250)
            throw new Exception("La biografía es inválida o demasiado larga.");

        alumno.setBiografia(nuevaBio);
        alumnoDAO.actualizar(alumno);
    }

    @Override
    public void agregarInteres(int idAlumno, Interes interes) throws Exception {
        Alumno alumno = alumnoDAO.obtener(idAlumno);
        if (alumno == null)
            throw new Exception("No se encontró al alumno con ID: " + idAlumno);

        if (interes == null)
            throw new Exception("El interés no puede ser nulo.");

        alumno.agregarInteres(interes);
        alumnoDAO.actualizar(alumno);
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
    public Alumno buscarPorId(int id) throws Exception {
        Alumno alumno = alumnoDAO.obtener(id);
        if (alumno == null)
            throw new Exception("No se encontró al alumno.");
        return alumno;
    }

    @Override
    public List<Alumno> listarAlumnos() {
        return alumnoDAO.listarTodos();
    }

    @Override
    public void eliminarAlumno(int id) {
        alumnoDAO.eliminar(id);
    }
}
