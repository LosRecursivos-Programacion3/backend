/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package pucp.edu.pe.pucpconnect.business;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import pucp.edu.pe.pucpconnect.domain.Social.Evento;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.business.impl.EventoServiceImpl;
import pucp.edu.pe.pucpconnect.business.impl.AlumnoServiceImpl;
import pucp.edu.pe.pucpconnect.business.impl.InteresServiceImpl;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Social.EventoDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.AlumnoDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.InteresDAOImpl;
/**
 *
 * @author Irico
 */
public class PucpConnectBusiness {
    
    public static void main(String[] args) throws Exception {
        
//        BaseDAO<Interes>InteresDAO = new InteresDAOImpl();
//        InteresService interesService = new InteresServiceImpl(InteresDAO);
//        
////        List<Interes>interes = interesService.listarIntereses();
//        
//        int a;
        
        BaseDAO<Alumno> alumnoDAO = new AlumnoDAOImpl();
        AlumnoService alumnoService = new AlumnoServiceImpl(alumnoDAO);
        BaseDAO<Evento> eventoDAO = new EventoDAOImpl();
        EventoService eventoService = new EventoServiceImpl(eventoDAO);
        EventoService eventSer = new EventoServiceImpl();
//        List<Interes>interes2 = eventSer.listarInteresesPorEvento(1);
        List<Evento>eventos = eventSer.listarEventos();
//        List<Evento>eventos = eventSer.listarEventosPorInteres(10);
        
        Alumno creador = new Alumno();
        creador.setId(1); // ID de un alumno existente en tu BD
        creador.setNombre("Jooel Perez");
        
        String nombreEvento = "Taller de Programación 36";
        String descripcion = "Taller evaluado";
        LocalDateTime fechaInicio = LocalDateTime.now().plusDays(1);
        LocalDateTime fechaFin = fechaInicio.plusHours(3);
        String ubicacion = "Aula 104 - Pabellón A";
        List<Integer> interesesIds = Arrays.asList(2, 4, 5);
        
        Evento nuevoEvento = new Evento();
        nuevoEvento.setNombre(nombreEvento);
        nuevoEvento.setDescripcion(descripcion);
        nuevoEvento.setFecha(fechaInicio);
        nuevoEvento.setFechaFin(fechaFin);
        nuevoEvento.setUbicacion(ubicacion);
        nuevoEvento.setEstado(true);
        nuevoEvento.setCreador(creador);
        
        eventoService.crearEvento(nuevoEvento, interesesIds);
        Evento evento = eventoService.buscarPorId(5);
        Alumno alumno = alumnoService.buscarPorId(24);
         eventSer.registrar_Participacion_Evento(evento, alumno);    
        boolean exitoo = eventSer.verificarParticipacion(5, 5);
        if(exitoo){
            System.out.println("Si existe");
        }else{
        System.out.println("No existe");
        }
        boolean e = eventSer.cancelarParticipacion(5, 5);
        if(exitoo){
            System.out.println("Si existe");
        }else{
        System.out.println("No existe");
        }
        
        exitoo = eventSer.verificarParticipacion(5, 8);
        if(exitoo){
            System.out.println("Si existe");
        }else{
        System.out.println("No existe");
        }
       
        
        
        List<Alumno> participantes = eventSer.listarParticipantesPorEvento(evento.getIdEvento());
        
        System.out.println("pucp.edu.pe.pucpconnect.business.PucpConnectBusiness.main()");
        
    }
}
