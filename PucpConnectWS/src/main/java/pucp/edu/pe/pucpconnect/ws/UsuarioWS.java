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
import java.util.List;
import pucp.edu.pe.pucpconnect.business.AlumnoService;
import pucp.edu.pe.pucpconnect.business.UsuarioService;
import pucp.edu.pe.pucpconnect.business.impl.AlumnoServiceImpl;
import pucp.edu.pe.pucpconnect.business.impl.UsuarioServiceImpl;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Usuario;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.AlumnoDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.UsuarioDAOImpl;

/**
 *
 * @author Fernando
 */
@WebService(serviceName = "UsuarioWS")
public class UsuarioWS {
    private final AlumnoService alumnoService;
    private final UsuarioService usuarioService;
    public UsuarioWS(){
        BaseDAO<Alumno> alumnoDAO = new AlumnoDAOImpl();
        alumnoService = new AlumnoServiceImpl(alumnoDAO);

        BaseDAO<Usuario> usuarioDAO = new UsuarioDAOImpl();
        usuarioService = new UsuarioServiceImpl(usuarioDAO);
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
    public Usuario autenticarUsuario(@WebParam(name = "email") String email, @WebParam(name = "password") String password) {
        try {
            return usuarioService.autenticarUsuario(email, password);
        } catch (Exception e) {
            throw new WebServiceException("Error al autenticar usuario: " + e.getMessage(), e);
        }
    }
}
