package pucp.edu.pe.pucpconnect.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import pucp.edu.pe.pucpconnect.ws.MensajeDTO;
import pucp.edu.pe.pucpconnect.business.MensajeService;
import pucp.edu.pe.pucpconnect.business.impl.MensajeServiceImpl;
import pucp.edu.pe.pucpconnect.domain.Interacciones.Mensaje;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.AlumnoDAOImpl;
import java.util.*;
import com.google.gson.Gson;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import pucp.edu.pe.pucpconnect.business.AlumnoService;
import pucp.edu.pe.pucpconnect.business.impl.AlumnoServiceImpl;

@ServerEndpoint("/ws/chat")
public class ChatEndpoint {
    private static Map<Integer, Session> userSessions = Collections.synchronizedMap(new HashMap<>());
    private MensajeService msgService = new MensajeServiceImpl();
    private Gson gson = new Gson();
    

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        // opcional: extraer ID de usuario de query string o cookie
        String query = session.getQueryString(); // ej: "userId=23"
        int userId = Integer.parseInt(query.split("=")[1]);
        userSessions.put(userId, session);
        System.out.println("Usuario conectado WS: " + userId);
    }

    @OnMessage
    public void onMessage(String messageJson, Session session) {
        // parsea JSON
        ChatMessage cm = gson.fromJson(messageJson, ChatMessage.class);
        Alumno em = new Alumno(); em.setId(cm.emisorId);
        Alumno rc= new Alumno(); rc.setId(cm.receptorId);
        // crea y guarda el mensaje en BD
        Mensaje m = new Mensaje(0, cm.contenido, LocalDateTime.now(), true,
                                em, rc);
        msgService.registrarMensaje(m);
        // agrega timestamp al DTO
        cm.id = m.getIdMensaje();
        cm.timestamp = m.getFechaEnvio().toString();
        String out = gson.toJson(cm);
        // envía a emisor
        Session s1 = userSessions.get(cm.emisorId);
        if (s1!=null && s1.isOpen()) s1.getAsyncRemote().sendText(out);
        // envía a receptor
        Session s2 = userSessions.get(cm.receptorId);
        if (s2!=null && s2.isOpen() && cm.receptorId!=cm.emisorId) 
            s2.getAsyncRemote().sendText(out);
    }

    @OnClose
    public void onClose(Session session) {
        // remueve sessions según valor
        userSessions.values().remove(session);
    }

    // DTO interno
    private static class ChatMessage {
        int id;
        int emisorId;
        int receptorId;
        String contenido;
        String timestamp;
    }
}
