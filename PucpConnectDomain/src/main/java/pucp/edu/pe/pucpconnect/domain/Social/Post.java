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
    private String contenido;
    private String imagen;
    private LocalDateTime fecha;
    private boolean estado;
    private int autor;
    private List<Reaccion> reacciones;

    public Post() {}

    public Post(int idPost, String contenido, String imagen, LocalDateTime fecha, boolean estado, int autor) {
        this.idPost = idPost;
        this.contenido = contenido;
        this.imagen = imagen;
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

    public int getAutor() {
        return autor;
    }

    public void setAutor(int autor) {
        this.autor = autor;
    }

    /**
     * @return the imagen
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
