/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.domain.Interacciones;

import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.domain.Social.Post;
import java.time.LocalDateTime;
import java.util.List;



public class Comentario {
    private int idComentario;
    private String contenido;
    private LocalDateTime fechaComentario;
    private boolean estado;
    private Alumno autor;
    private Post post;
    private List<Reaccion> reacciones;

    public Comentario() {}

    public void editarContenido(String nuevoContenido) {
        if (nuevoContenido == null || nuevoContenido.isBlank())
            throw new IllegalArgumentException("Contenido inválido");
        this.setContenido(nuevoContenido);
    }

    public void eliminar() {
        this.setEstado(false);
    }

    public boolean reaccionar(ReaccionTipo tipo) {
        // lógica para reaccionar
        return true;
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(LocalDateTime fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Alumno getAutor() {
        return autor;
    }

    public void setAutor(Alumno autor) {
        this.autor = autor;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
