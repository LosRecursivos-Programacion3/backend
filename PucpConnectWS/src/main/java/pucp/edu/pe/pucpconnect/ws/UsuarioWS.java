package pucp.edu.pe.pucpconnect.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import pucp.edu.pe.pucpconnect.business.UsuarioService;
import pucp.edu.pe.pucpconnect.business.AlumnoService;
import pucp.edu.pe.pucpconnect.business.impl.UsuarioServiceImpl;
import pucp.edu.pe.pucpconnect.business.impl.AlumnoServiceImpl;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Usuario;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.UsuarioDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.AlumnoDAOImpl;

/**
 * WebService único que expone tanto operaciones de UsuarioService
 * como de AlumnoService, según se probó en el front.
 */
@WebService(serviceName = "UsuarioWS")
public class UsuarioWS {
    private final UsuarioService usuarioService;
    private final AlumnoService alumnoService;

    public UsuarioWS() {
        BaseDAO<Usuario> userDao = new UsuarioDAOImpl();
        BaseDAO<Alumno>  alumDao = new AlumnoDAOImpl();
        this.usuarioService = new UsuarioServiceImpl(userDao);
        this.alumnoService  = new AlumnoServiceImpl(alumDao);
    }

    @WebMethod(operationName = "registrarAlumno")
    public boolean registrarAlumno(
        @WebParam(name = "nombre")    String nombre,
        @WebParam(name = "password")  String password,
        @WebParam(name = "email")     String email,
        @WebParam(name = "edad")      int edad,
        @WebParam(name = "biografia") String biografia,
        @WebParam(name = "carrera")   String carrera,
        @WebParam(name = "fotoPerfil")String fotoPerfil,
        @WebParam(name = "ubicacion") String ubicacion
    ) {
        try {
            Alumno a = new Alumno();
            a.setNombre(nombre);
            a.setPassword(password);
            a.setEmail(email);
            a.setEdad(edad);
            a.setBiografia(biografia);
            a.setCarrera(carrera);
            a.setFotoPerfil(fotoPerfil);
            a.setUbicacion(ubicacion);
            a.setVisible(true);
            a.setEstado(true);
            a.setFechaRegistro(LocalDateTime.now());

            alumnoService.registrarAlumno(a);
            return true;
        } catch (Exception e) {
            throw new WebServiceException("Error al registrar alumno: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "autenticarUsuario")
    public Alumno autenticarUsuario(
        @WebParam(name = "email")    String email,
        @WebParam(name = "password") String password
    ) {
        try {
            Usuario u = usuarioService.autenticarUsuario(email, password);
            if (u == null) return null;
            Alumno a = alumnoService.buscarPorIdUsuario(u.getId());
            // Rellenar campos de Usuario en Alumno
            a.setId(u.getId());
            a.setNombre(u.getNombre());
            a.setPassword(u.getPassword());
            a.setEstado(u.getEstado());
            a.setFechaRegistro(u.getFechaRegistro());
            a.setEmail(u.getEmail());
            a.setVisible(u.isVisible());
            return a;
        } catch (Exception e) {
            throw new WebServiceException("Error al autenticar usuario: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "cambiarVisibilidad")
    public boolean cambiarVisibilidad(
        @WebParam(name = "idUsuario") int idUsuario,
        @WebParam(name = "visible")   boolean visible
    ) {
        try {
            usuarioService.cambiarVisibilidad(idUsuario, visible);
            return true;
        } catch (Exception e) {
            throw new WebServiceException("Error al cambiar visibilidad: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "listarSugerencias")
    public List<Usuario> listarSugerencias(
        @WebParam(name = "idUsuario") int idUsuario
    ) {
        try {
            return usuarioService.listarSugerencias(idUsuario);
        } catch (Exception e) {
            throw new WebServiceException("Error al listar sugerencias: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "listarMatches")
    public List<Usuario> listarMatches(
        @WebParam(name = "idUsuario") int idUsuario
    ) {
        try {
            return usuarioService.listarMatches(idUsuario);
        } catch (Exception e) {
            throw new WebServiceException("Error al listar matches: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "hacerMatch")
    public boolean hacerMatch(
        @WebParam(name = "idAlumnoUno") int idAlumnoUno,
        @WebParam(name = "idAlumnoDos") int idAlumnoDos
    ) {
        try {
            usuarioService.hacerMatch(idAlumnoUno, idAlumnoDos);
            return true;
        } catch (SQLException e) {
            throw new WebServiceException("Error SQL al hacer match: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new WebServiceException("Error al hacer match: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "bloquearUsuario")
    public boolean bloquearUsuario(
        @WebParam(name = "idBloqueador") int idBloqueador,
        @WebParam(name = "idBloqueado")  int idBloqueado
    ) {
        try {
            usuarioService.bloquearUsuario(idBloqueador, idBloqueado);
            return true;
        } catch (UnsupportedOperationException e) {
            throw new WebServiceException("No implementado bloqueo: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new WebServiceException("Error al bloquear usuario: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "desbloquearUsuario")
    public boolean desbloquearUsuario(
        @WebParam(name = "idBloqueador") int idAlumno,
        @WebParam(name = "idBloqueado")  int idBloqueado
    ) {
        try {
            return alumnoService.desbloquearAlumno(idAlumno, idBloqueado);
        } catch (Exception e) {
            throw new WebServiceException("Error al desbloquear alumno: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "obtenerAlumnosBloqueados")
    public List<Integer> obtenerAlumnosBloqueados(
        @WebParam(name = "idAlumno") int idAlumno
    ) {
        try {
            return alumnoService.obtenerAlumnosBloqueados(idAlumno);
        } catch (Exception e) {
            throw new WebServiceException("Error al obtener bloqueados: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "agregarInteres")
    public boolean agregarInteres(
        @WebParam(name = "idAlumno")   int idAlumno,
        @WebParam(name = "idInteres")  int idInteres
    ) {
        try {
            Interes i = new Interes();
            i.setId(idInteres);
            alumnoService.agregarInteres(idAlumno, i);
            return true;
        } catch (Exception e) {
            throw new WebServiceException("Error al agregar interés: " + e.getMessage(), e);
        }
    }
}
