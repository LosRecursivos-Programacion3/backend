package pucp.edu.pe.pucpconnect.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import pucp.edu.pe.pucpconnect.ws.MensajeDTO;
import pucp.edu.pe.pucpconnect.business.MensajeService;
import pucp.edu.pe.pucpconnect.business.impl.MensajeServiceImpl;
import pucp.edu.pe.pucpconnect.domain.Interacciones.Mensaje;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.AlumnoDAOImpl;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/ws/chat")
public class ChatEndpoint {

    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();
    private static final ObjectMapper jsonMapper = new ObjectMapper();
    private final MensajeService mensajeService = new MensajeServiceImpl();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(String rawJson, Session session) {
        try {
            // 1) Parsear a MensajeDTO
            MensajeDTO dto = jsonMapper.readValue(rawJson, MensajeDTO.class);

            // 2) Persistir en BD
            Alumno emisor   = new AlumnoDAOImpl().obtener(dto.getEmisorId());
            Alumno receptor = new AlumnoDAOImpl().obtener(dto.getReceptorId());

            Mensaje entidad = new Mensaje(
                0,
                dto.getContenido(),
                LocalDateTime.now(),
                true,
                emisor,
                receptor
            );
            mensajeService.registrarMensaje(entidad);

            // 3) Construir nuevo DTO con timestamp
            String ts = entidad.getFechaEnvio()
                               .format(DateTimeFormatter.ofPattern("HH:mm"));
            MensajeDTO respuesta = new MensajeDTO(
                dto.getEmisorId(),
                dto.getReceptorId(),
                dto.getContenido(),
                ts
            );
            String payload = jsonMapper.writeValueAsString(respuesta);

            // 4) Broadcast
            for (Session s : sessions) {
                s.getBasicRemote().sendText(payload);
            }

        } catch (Exception ex) {
            // Opcional: informar error al cliente
            try {
                session.getBasicRemote().sendText(
                    "{\"error\":\"" + ex.getMessage().replace("\"", "") + "\"}"
                );
            } catch (Exception ignore) {}
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable t) {
        sessions.remove(session);
    }
}
