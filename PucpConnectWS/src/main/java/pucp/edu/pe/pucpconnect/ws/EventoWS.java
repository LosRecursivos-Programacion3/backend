/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.ws;


import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import pucp.edu.pe.pucpconnect.business.EventoService;
import pucp.edu.pe.pucpconnect.business.impl.EventoServiceImpl;
import pucp.edu.pe.pucpconnect.domain.Social.Evento;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Social.EventoDAOImpl;
import pucp.edu.pe.pucpconnect.business.AlumnoService;
import pucp.edu.pe.pucpconnect.business.impl.AlumnoServiceImpl;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.AlumnoDAOImpl;
/**
 *
 * @author stefa
 */
@WebService(serviceName = "EventoWS" , targetNamespace = "pucp.edu.pe.pucpconnect")
public class EventoWS {
    private final EventoService eventoService;
    public EventoWS() {
        BaseDAO<Evento> eventoDAO = new EventoDAOImpl();
        eventoService = new EventoServiceImpl(eventoDAO);
    }

    @WebMethod(operationName = "registrarEvento")
   public boolean registrarEvento(
    @WebParam(name = "nombre") String nombre, 
    @WebParam(name = "descripcion") String descripcion, 
    @WebParam(name = "fechaEvento") String fechaEvento, 
    @WebParam(name = "ubicacion") String ubicacion, 
    @WebParam(name = "id_creador") int idcreador) {
    try {
        LocalDateTime fecha = LocalDateTime.parse(fechaEvento); // convertir string a fecha
        Evento evento = new Evento();
        evento.setDescripcion(descripcion);
        evento.setEstado(true);
        evento.setFecha(fecha);
        evento.setNombre(nombre);
        evento.setUbicacion(ubicacion);
        BaseDAO<Alumno> alumnoDAO = new AlumnoDAOImpl();
        AlumnoService alumnoservice = new AlumnoServiceImpl(alumnoDAO);
        Alumno creador = alumnoDAO.obtener(idcreador);
        evento.setCreador(creador);
        evento.setParticipantes(new ArrayList<>());
        eventoService.crearEvento(evento);
        
    } catch (Exception e) {
        throw new WebServiceException("Error al registrar evento: " + e.getMessage(), e);
    }
    return false;
    }
}