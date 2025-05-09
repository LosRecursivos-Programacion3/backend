/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.domain.Social;

import java.time.LocalDateTime;
import java.util.List;
import pucp.edu.pe.pucpconnect.domain.Interacciones.Comentario;
import pucp.edu.pe.pucpconnect.domain.Interacciones.Reaccion;
import pucp.edu.pe.pucpconnect.domain.Interacciones.ReaccionTipo;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;

/**
 *
 * @author Irico
 */
public class Post {
    private int idPost;
    private String nombre;
    private String contenido;
    private LocalDateTime fecha;
    private boolean estado;
    private Alumno autor;
    private List<Reaccion> reacciones;

    public Post() {}

    public Post(int idPost, String nombre, String contenido, LocalDateTime fecha, boolean estado, Alumno autor) {
        this.idPost = idPost;
        this.nombre = nombre;
        this.contenido = contenido;
        this.fecha = fecha;
        this.estado = estado;
        this.autor = autor;
    }

    public void editarContenido(String nuevoContenido) {
        if (nuevoContenido == null || nuevoContenido.isBlank())
            throw new IllegalArgumentException("Contenido inválido");
        this.setContenido(nuevoContenido);
    }

    public void eliminar() {
        this.setEstado(false);
    }

    public void agregarComentario(Comentario comentario) {
        // Lógica para agregar un comentario
    }

    public Reaccion agregarReaccion(Alumno usuario, ReaccionTipo tipo) {
        Reaccion r = new Reaccion(0, tipo, LocalDateTime.now(), usuario, this);
        reacciones.add(r);
        return r;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
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
}
