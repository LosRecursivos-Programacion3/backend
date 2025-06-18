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
import pucp.edu.pe.pucpconnect.business.AlumnoService;
import pucp.edu.pe.pucpconnect.business.impl.AlumnoServiceImpl;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.AlumnoDAOImpl;

/**
 *
 * @author Fernando
 */
@WebService(serviceName = "UsuarioWS")
public class UsuarioWS {
    private final AlumnoService alumnoService;
    public UsuarioWS(){
        BaseDAO<Alumno> alumnoDAO = new AlumnoDAOImpl();
        alumnoService = new AlumnoServiceImpl(alumnoDAO);
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
}
