/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package pucp.edu.pe.pucpconnect.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import pucp.edu.pe.pucpconnect.business.AlumnoService;
import pucp.edu.pe.pucpconnect.business.InteresService;
import pucp.edu.pe.pucpconnect.business.UsuarioService;
import pucp.edu.pe.pucpconnect.business.impl.AlumnoServiceImpl;
import pucp.edu.pe.pucpconnect.business.impl.InteresServiceImpl;
import pucp.edu.pe.pucpconnect.business.impl.UsuarioServiceImpl;
import pucp.edu.pe.pucpconnect.domain.Social.Amistad;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Usuario;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.AlumnoDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.InteresDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.UsuarioDAOImpl;

/**
 *
 * @author Fernando
 */

@WebService(serviceName = "UsuarioWS")
public class UsuarioWS {
    private final AlumnoService alumnoService;
    private final UsuarioService usuarioService;
    private final InteresService interesService;
    public UsuarioWS(){
        BaseDAO<Alumno> alumnoDAO = new AlumnoDAOImpl();
        alumnoService = new AlumnoServiceImpl(alumnoDAO);

        BaseDAO<Usuario> usuarioDAO = new UsuarioDAOImpl();
        usuarioService = new UsuarioServiceImpl(usuarioDAO);
        
        BaseDAO<Interes> interesDAO = new InteresDAOImpl();
        interesService = new InteresServiceImpl(interesDAO);
    }
    
    @WebMethod(operationName = "registrarAlumno")
    public boolean registrarAlumno(@WebParam(name = "nombre") String nombre, @WebParam(name = "password") String password, @WebParam(name = "estado") boolean estado,
        @WebParam(name = "email") String email, @WebParam(name = "edad") int edad,
        @WebParam(name = "carrera") String carrera, @WebParam(name = "fotoPerfil") String fotoPerfil, @WebParam(name = "ubicacion") String ubicacion,
        @WebParam(name = "biografia") String biografia, @WebParam(name = "visible") boolean visible) {
        try {
            Alumno alumno = new Alumno();
            alumno.setNombre(nombre);
            alumno.setPassword(password);
            alumno.setEstado(estado);
            alumno.setFechaRegistro(LocalDateTime.now());
            alumno.setEmail(email);
            alumno.setEdad(edad);
            alumno.setCarrera(carrera);
            alumno.setFotoPerfil(fotoPerfil);
            alumno.setUbicacion(ubicacion);
            alumno.setBiografia(biografia);
            alumno.setVisible(visible);

            alumnoService.registrarAlumno(alumno);
        } catch (Exception e) {
            throw new WebServiceException("Error al registrar alumno: " + e.getMessage(), e);
        }
        return false;
    }
    @WebMethod(operationName = "autenticarUsuario")
    public Alumno autenticarUsuario(@WebParam(name = "email") String email, @WebParam(name = "password") String password) {
        try {
            Usuario usuarioBase = usuarioService.autenticarUsuario(email, password);
            Alumno alumno = alumnoService.buscarPorIdUsuario(usuarioBase.getId());
            alumno.setId(usuarioBase.getId());
            alumno.setNombre(usuarioBase.getNombre());
            alumno.setPassword(usuarioBase.getPassword());
            alumno.setEstado(usuarioBase.getEstado());
            alumno.setFechaRegistro(usuarioBase.getFechaRegistro());
            alumno.setEmail(usuarioBase.getEmail());
            alumno.setVisible(usuarioBase.isVisible());

            return alumno;
        } catch (Exception e) {
            throw new WebServiceException("Error al autenticar usuario: " + e.getMessage(), e);
        }
    }
    @WebMethod(operationName = "listarIntereses")
    public List<Interes> listarIntereses() {
        try {
            return interesService.listarIntereses();
        } catch (Exception e) {
            // Cambia el mensaje para saber que el fallo ocurrió en intereses
            throw new WebServiceException("Error al listar intereses: " + e.getMessage(), e);
        }
    }
    
    @WebMethod(operationName = "obtenerInteresesUsuario")
    public List<Interes> obtenerInteresesUsuario(@WebParam(name = "idAlumno") int idAlumno) {
        try {
            List<Interes> intereses = interesService.obtenerInteresesUsuario(idAlumno);
            if(intereses == null) return new ArrayList<>();
            return intereses;
        } catch (Exception e) {
            // Cambia el mensaje para saber que el fallo ocurrió en intereses
            throw new WebServiceException("Error al obtener intereses: " + e.getMessage(), e);
        }
    }
    @WebMethod(operationName = "registrarInteresesUsuario")
    public boolean registrarInteresesUsuario(@WebParam(name = "idAlumno") int idAlumno, @WebParam(name = "ids") List<Integer> ids) 
    {
        try {
            interesService.insertarIntereses(ids,idAlumno);
            return true;
        } catch (Exception e) {
            throw new WebServiceException("Error al registrar intereses: " + e.getMessage(), e);
        }
    }
    
    @WebMethod(operationName = "actualizarAlumno")
    public boolean actualizarAlumno(
            @WebParam(name = "id") int id,
            @WebParam(name = "idAlumno") int idAlumno,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "edad") int edad,
            @WebParam(name = "carrera") String carrera,
            @WebParam(name = "fotoPerfil") String fotoPerfil,
            @WebParam(name = "ubicacion") String ubicacion,
            @WebParam(name = "biografia") String biografia) {

        try {
            // Armar el objeto Alumno con los datos recibidos
            Alumno alumno = new Alumno();
            alumno.setId(id);
            alumno.setIdAlumno(idAlumno);
            alumno.setNombre(nombre);
            alumno.setEdad(edad);
            alumno.setCarrera(carrera);
            alumno.setFotoPerfil(fotoPerfil);
            alumno.setUbicacion(ubicacion);
            alumno.setBiografia(biografia);

            alumnoService.modificarAlumno(alumno);
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new WebServiceException("Error al actualizar alumno: " + ex.getMessage(), ex);
        }
    }

    @WebMethod(operationName = "cambiarVisibilidad")
    public boolean cambiarVisibilidad(@WebParam(name = "idUsuario") int idUsuario,
                                      @WebParam(name = "visible") boolean visible) {
        try {
            usuarioService.cambiarVisibilidad(idUsuario, visible);
            return true;
        } catch (Exception e) {
            throw new WebServiceException("Error al cambiar visibilidad: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "listarSugerencias")
    public List<Usuario> listarSugerencias(@WebParam(name = "idUsuario") int idUsuario) {
        try {
            return usuarioService.listarSugerencias(idUsuario);
        } catch (Exception e) {
            throw new WebServiceException("Error al listar sugerencias: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "bloquearUsuario")
    public boolean bloquearUsuario(@WebParam(name = "idBloqueador") int idBloqueador,
                                   @WebParam(name = "idBloqueado") int idBloqueado) {
        try {
            usuarioService.bloquearUsuario(idBloqueador, idBloqueado);
            return true;
        } catch (Exception e) {
            throw new WebServiceException("Error al bloquear usuario: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "listarMatches")
    public List<Usuario> listarMatches(@WebParam(name = "idUsuario") int idUsuario) {
        try {
            return usuarioService.listarMatches(idUsuario);
        } catch (Exception e) {
            throw new WebServiceException("Error al listar matches: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "hacerMatch")
    public boolean hacerMatch(@WebParam(name = "idAlumnoUno") int idAlumnoUno,
                              @WebParam(name = "idAlumnoDos") int idAlumnoDos) {
        try {
            usuarioService.hacerMatch(idAlumnoUno, idAlumnoDos);
            return true;
        } catch (Exception e) {
            throw new WebServiceException("Error al hacer match: " + e.getMessage(), e);
        }
    }
    
    @WebMethod(operationName= "listarSugeridos")
    public List<Alumno> listarSugeridos(@WebParam(name = "idAlumno") int idAlumno){
        try{
            List<Interes> intereses = interesService.obtenerInteresesUsuario(idAlumno);
            List<Alumno> alumnosSugeridos = alumnoService.listarAlumnosSugeridos(intereses, idAlumno);
            return alumnosSugeridos;
        } catch (Exception e) {
            throw new WebServiceException("Error al hacer match: " + e.getMessage(), e);
        }
    }
    
    @WebMethod(operationName= "enviarSolicitudAmistad")
    public void enviarSolicitudAmistad(@WebParam(name = "idUsuario1") int idUsuario1,
                                 @WebParam(name = "idUsuario2") int idUsuario2){
        try{
            alumnoService.enviarSolicitudAmistad(idUsuario1, idUsuario2);
        } catch (Exception e) {
            throw new WebServiceException("Error al hacer match: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName= "listarSolicitudesEnviadas")
    public List<Amistad> listarSolicitudesEnviadas(@WebParam(name = "idAlumno") int idAlumno){
        try{
            return alumnoService.listarSolicitudesEnviadas(idAlumno);
        } catch (Exception e) {
            throw new WebServiceException("Error al hacer match: " + e.getMessage(), e);
        }
    }
    @WebMethod(operationName= "listarSolicitudesRecibidas")
    public List<Amistad> listarSolicitudesRecibidas(@WebParam(name = "idAlumno") int idAlumno){
        try{
            return alumnoService.listarSolicitudesRecibidas(idAlumno);
        } catch (Exception e) {
            throw new WebServiceException("Error al hacer match: " + e.getMessage(), e);
        }
    }
    @WebMethod(operationName= "aceptarSolicitud")
    public void aceptarSolicitud(@WebParam(name = "idAmistad") int idAmistad){
        try{
            alumnoService.aceptarSolicitud(idAmistad);
        } catch (Exception e) {
            throw new WebServiceException("Error al hacer match: " + e.getMessage(), e);
        }
    }
    
    @WebMethod(operationName= "rechazarSolicitud")
    public void rechazarSolicitud(@WebParam(name = "idAmistad") int idAmistad){
        try{
            alumnoService.rechazarSolicitud(idAmistad);
        } catch (Exception e) {
            throw new WebServiceException("Error al hacer match: " + e.getMessage(), e);
        }
    }
    
    @WebMethod(operationName= "cancelarSolicitud")
    public void cancelarSolicitud(@WebParam(name = "idAmistad") int idAmistad){
        try{
            alumnoService.cancelarSolicitud(idAmistad);
        } catch (Exception e) {
            throw new WebServiceException("Error al hacer match: " + e.getMessage(), e);
        }
    }
    
    @WebMethod(operationName= "listarAmigosPorId")
    public List<Alumno> listarAmigosPorId(@WebParam(name = "idUsuario") int idUsuario){
        try{
            return alumnoService.obtenerAmigosPorUsuario(idUsuario);
        } catch (Exception e) {
            throw new WebServiceException("Error al hacer match: " + e.getMessage(), e);
        }
    }
    
    @WebMethod(operationName = "listarAmigos")
    public List<Alumno> listarAmigos(@WebParam(name = "usuarioId") int usuarioId){
        try{
            return usuarioService.listarAmigos(usuarioId);
        }
        catch (Exception e) {
            throw new WebServiceException("Error al hacer match: " + e.getMessage(), e);
        }
    }
}