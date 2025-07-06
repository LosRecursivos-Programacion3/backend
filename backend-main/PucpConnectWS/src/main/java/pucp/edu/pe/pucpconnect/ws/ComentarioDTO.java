package pucp.edu.pe.pucpconnect.ws;

import jakarta.xml.bind.annotation.XmlType;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;

@XmlType(name = "ComentarioDTO", propOrder = {
    "idComentario",
    "contenido",
    "fechaComentarioStr",
    "autor"
})
public class ComentarioDTO {
    private int idComentario;
    private String contenido;
    private String fechaComentarioStr;  // <-- nueva propiedad
    private Alumno autor;

    // getters & setters

    public int getIdComentario() { return idComentario; }
    public void setIdComentario(int idComentario) { this.idComentario = idComentario; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public String getFechaComentarioStr() { return fechaComentarioStr; }
    public void setFechaComentarioStr(String fechaComentarioStr) {
        this.fechaComentarioStr = fechaComentarioStr;
    }

    public Alumno getAutor() { return autor; }
    public void setAutor(Alumno autor) { this.autor = autor; }
}