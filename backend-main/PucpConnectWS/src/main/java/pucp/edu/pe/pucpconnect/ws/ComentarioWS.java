package pucp.edu.pe.pucpconnect.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



import java.util.List;
import java.util.stream.Collectors;
import pucp.edu.pe.pucpconnect.domain.Interacciones.Comentario;
import pucp.edu.pe.pucpconnect.domain.Social.Post;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.persistence.dao.Interacciones.ComentarioDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Interacciones.ComentarioDAOImpl;

@WebService(serviceName = "ComentarioWS")
public class ComentarioWS {
private ComentarioDAO service = new ComentarioDAOImpl();
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @WebMethod(operationName="listarComentariosPorPost")
    public List<ComentarioDTO> listarComentariosPorPost(@WebParam(name="idPost") int idPost) {
        List<Comentario> lista = service.obtenerComentariosPorPost(idPost);
        return lista.stream().map(c -> {
            ComentarioDTO dto = new ComentarioDTO();
            dto.setIdComentario(c.getIdComentario());
            dto.setContenido(c.getContenido());
            // formatea LocalDateTime a cadena
            dto.setFechaComentarioStr(c.getFechaComentario().format(FMT));
            dto.setAutor(c.getAutor());
            return dto;
        }).collect(Collectors.toList());
    }

    @WebMethod(operationName="registrarComentario")
    public int registrarComentario(
        @WebParam(name="idPost") int idPost,
        @WebParam(name="idUsuario") int idUsuario,
        @WebParam(name="contenido") String contenido
    ) {
        Post post = new Post();
        post.setIdPost(idPost);
        Alumno alum = new Alumno();
        alum.setIdAlumno(idUsuario);

        Comentario c = new Comentario();
        c.setPost(post);
        c.setAutor(alum);
        c.setContenido(contenido);
        c.setFechaComentario(java.time.LocalDateTime.now());
        c.setEstado(true);

        service.agregar(c);
        return c.getIdComentario();
    }
}
