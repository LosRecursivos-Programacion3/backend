/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.domain.Social;


public class PostConAutor {
    private int idPost;
    private String contenido;
    private String imagen;
    private String fecha; // fecha como String formateado
    private boolean estado;

    // Datos del autor
    private int autorId;
    private String nombreAutor;
    private String carreraAutor;
    private String fotoAutor;

    public PostConAutor() {
    }

    public PostConAutor(int idPost, String contenido, String imagen, String fecha, boolean estado,
                        int autorId, String nombreAutor, String carreraAutor, String fotoAutor) {
        this.idPost = idPost;
        this.contenido = contenido;
        this.imagen = imagen;
        this.fecha = fecha;
        this.estado = estado;
        this.autorId = autorId;
        this.nombreAutor = nombreAutor;
        this.carreraAutor = carreraAutor;
        this.fotoAutor = fotoAutor;
    }

    // Getters y setters
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getAutorId() {
        return autorId;
    }

    public void setAutorId(int autorId) {
        this.autorId = autorId;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public String getCarreraAutor() {
        return carreraAutor;
    }

    public void setCarreraAutor(String carreraAutor) {
        this.carreraAutor = carreraAutor;
    }

    public String getFotoAutor() {
        return fotoAutor;
    }

    public void setFotoAutor(String fotoAutor) {
        this.fotoAutor = fotoAutor;
    }
}