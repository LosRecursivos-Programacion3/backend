
package pucp.edu.pe.pucpconnect.domain.Usuarios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import pucp.edu.pe.pucpconnect.domain.Interacciones.Mensaje;
import pucp.edu.pe.pucpconnect.domain.Social.Evento;
import pucp.edu.pe.pucpconnect.domain.Social.Post;


public class Alumno extends Usuario {
    private int edad;
    private String carrera;
    private String fotoPerfil;
    private String ubicacion;
    private String biografia;
    private List<Interes> intereses = new ArrayList<>();
    private List<Evento> eventos = new ArrayList<>();

    public Alumno() { super(); }

    public Alumno(int id, String nombre, String password, boolean estado,
                  LocalDateTime fechaRegistro, String email,
                  int edad, String carrera, String fotoPerfil,
                  String ubicacion, String biografia) {
        super(id, nombre, password, estado, fechaRegistro, email);
        this.edad = edad;
        this.carrera = carrera;
        this.fotoPerfil = fotoPerfil;
        this.ubicacion = ubicacion;
        this.biografia = biografia;
    }

    public Post crearPost(String nombre, String contenido) {
        return new Post(0, nombre, contenido, LocalDateTime.now(), true, this);
    }

    public Evento crearEvento(String nombre, String descripcion,
                               LocalDateTime fecha, String ubicacion) {
        return new Evento(0, nombre, descripcion, fecha, ubicacion,
                          true, this, new ArrayList<>(), new ArrayList<>());
    }

    public void agregarInteres(Interes interes) {
        if (interes == null)
            throw new IllegalArgumentException("Interés nulo");
        intereses.add(interes);
    }

    public void agregarEvento(Evento evento){
        if(evento == null)
            throw new IllegalArgumentException("Evento nulo");
        eventos.add(evento);
    }
        
    public Mensaje enviarMensaje(Alumno receptor, String contenido) {
        if (receptor == null)
            throw new IllegalArgumentException("Receptor nulo");
        return new Mensaje(0, contenido, LocalDateTime.now(), true, this, receptor);
    }

    // Getters y Setters...

    /**
     * @return the edad
     */
    public int getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * @return the carrera
     */
    public String getCarrera() {
        return carrera;
    }

    /**
     * @param carrera the carrera to set
     */
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    /**
     * @return the fotoPerfil
     */
    public String getFotoPerfil() {
        return fotoPerfil;
    }

    /**
     * @param fotoPerfil the fotoPerfil to set
     */
    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    /**
     * @return the ubicacion
     */
    public String getUbicacion() {
        return ubicacion;
    }

    /**
     * @param ubicacion the ubicacion to set
     */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    /**
     * @return the biografia
     */
    public String getBiografia() {
        return biografia;
    }

    /**
     * @param biografia the biografia to set
     */
    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }
}
