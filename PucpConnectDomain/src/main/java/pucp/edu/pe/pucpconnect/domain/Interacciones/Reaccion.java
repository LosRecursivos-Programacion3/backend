/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.domain.Interacciones;

import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.domain.Social.Post;
import pucp.edu.pe.pucpconnect.domain.Social.Evento;
import java.time.LocalDateTime;

/**
 *
 * @author Irico
 */
public class Reaccion {
    private int id;
    private ReaccionTipo tipo;
    private LocalDateTime fecha;
    private Alumno usuario;
    private Post post;
    private Comentario comentario;
    private Evento evento;

    public Reaccion() {}

    public Reaccion(int id, ReaccionTipo tipo, LocalDateTime fecha,
                    Alumno usuario, Post post) {
        this(id, tipo, fecha, usuario);
        this.post = post;
    }

    public Reaccion(int id, ReaccionTipo tipo, LocalDateTime fecha,
                    Alumno usuario, Comentario comentario) {
        this(id, tipo, fecha, usuario);
        this.comentario = comentario;
    }

    public Reaccion(int id, ReaccionTipo tipo, LocalDateTime fecha,
                    Alumno usuario, Evento evento) {
        this(id, tipo, fecha, usuario);
        this.evento = evento;
    }

    private Reaccion(int id, ReaccionTipo tipo, LocalDateTime fecha,
                     Alumno usuario) {
        this.id = id;
        this.tipo = tipo;
        this.fecha = fecha;
        this.usuario = usuario;
    }

    public void cambiarTipo(ReaccionTipo nuevo) {
        this.setTipo(nuevo);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ReaccionTipo getTipo() {
        return tipo;
    }

    public void setTipo(ReaccionTipo tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Alumno getUsuario() {
        return usuario;
    }

    public void setUsuario(Alumno usuario) {
        this.usuario = usuario;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}
