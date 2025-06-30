/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.xml.ws.WebServiceException;
import pucp.edu.pe.pucpconnect.business.MensajeService;
import pucp.edu.pe.pucpconnect.business.impl.MensajeServiceImpl;
import pucp.edu.pe.pucpconnect.domain.Interacciones.Mensaje;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import java.time.LocalDateTime;
import java.util.List;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.AlumnoDAOImpl;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@WebService(serviceName = "MensajeWS")
public class MensajeWS {

    private final MensajeService mensajeService;
    private final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public MensajeWS() {
        // Aquí inicializas el servicio para los mensajes
        mensajeService = new MensajeServiceImpl();
    }

    @WebMethod(operationName = "enviarMensaje")
    public int enviarMensaje(
        @WebParam(name = "emisorId") int emisorId,
        @WebParam(name = "receptorId") int receptorId,
        @WebParam(name = "contenido") String contenido
    ) {
        try {
            Alumno emisor = new AlumnoDAOImpl().obtener(emisorId);
            Alumno receptor = new AlumnoDAOImpl().obtener(receptorId);

            // Crear el mensaje
            Mensaje mensaje = new Mensaje(0, contenido, LocalDateTime.now(), true, emisor, receptor);
            // Registrar el mensaje
            mensajeService.registrarMensaje(mensaje);

            return mensaje.getIdMensaje(); // Retorna el ID del mensaje creado
        } catch (Exception e) {
            throw new WebServiceException("Error al enviar mensaje: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "obtenerMensajesEntreUsuarios")
    public List<MensajeDTO> obtenerMensajesEntreUsuarios(
        @WebParam(name = "emisorId") int emisorId,
        @WebParam(name = "receptorId") int receptorId
    ) {
        try {
            List<Mensaje> domainList =
            mensajeService.obtenerMensajesEntreUsuarios(emisorId, receptorId);

            List<MensajeDTO> dtos = new ArrayList<>();
            for (Mensaje m : domainList) {
                MensajeDTO dto = new MensajeDTO();
                dto.setEmisorId(   m.getEmisor().getIdAlumno() );
                dto.setReceptorId( m.getReceptor().getIdAlumno() );
                dto.setContenido(  m.getContenido() );                       // ← aquí
                dto.setTimestamp(  m.getFechaEnvio().format(ISO) );          // ISO string
                dtos.add(dto);
            }
            return dtos;
        } catch (Exception e) {
            throw new WebServiceException("Error al obtener mensajes entre usuarios: " + e.getMessage(), e);
        }
    }

    @WebMethod(operationName = "eliminarMensaje")
    public void eliminarMensaje(@WebParam(name = "mensajeId") int mensajeId) {
        try {
            mensajeService.eliminarMensaje(mensajeId);
        } catch (Exception e) {
            throw new WebServiceException("Error al eliminar mensaje: " + e.getMessage(), e);
        }
    }
}