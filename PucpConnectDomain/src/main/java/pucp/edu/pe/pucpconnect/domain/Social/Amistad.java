package pucp.edu.pe.pucpconnect.domain.Social;

public class Amistad {
    private int idAmistad;
    private int idAlumnoUno;
    private int idAlumnoDos;
    private String nombreAlumnoUno;
    private String nombreAlumnoDos;
    private String fotoPerfilAlumnoUno;
    private String fotoPerfilAlumnoDos;
    private int estado; // 0: pendiente, 1: aceptado, 2: rechazado
    private String fecha;

    public Amistad() {}

    public Amistad(int idAmistad, int idAlumnoUno, int idAlumnoDos, int estado, String fecha) {
        this.idAmistad = idAmistad;
        this.idAlumnoUno = idAlumnoUno;
        this.idAlumnoDos = idAlumnoDos;
        this.estado = estado;
        this.fecha = fecha;
    }

    public int getIdAmistad() {
        return idAmistad;
    }

    public void setIdAmistad(int idAmistad) {
        this.idAmistad = idAmistad;
    }

    public int getIdAlumnoUno() {
        return idAlumnoUno;
    }

    public void setIdAlumnoUno(int idAlumnoUno) {
        this.idAlumnoUno = idAlumnoUno;
    }

    public int getIdAlumnoDos() {
        return idAlumnoDos;
    }

    public void setIdAlumnoDos(int idAlumnoDos) {
        this.idAlumnoDos = idAlumnoDos;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombreAlumnoUno() {
        return nombreAlumnoUno;
    }

    public void setNombreAlumnoUno(String nombreAlumnoUno) {
        this.nombreAlumnoUno = nombreAlumnoUno;
    }

    public String getNombreAlumnoDos() {
        return nombreAlumnoDos;
    }

    public void setNombreAlumnoDos(String nombreAlumnoDos) {
        this.nombreAlumnoDos = nombreAlumnoDos;
    }

    public String getFotoPerfilAlumnoUno() {
        return fotoPerfilAlumnoUno;
    }

    public void setFotoPerfilAlumnoUno(String fotoPerfilAlumnoUno) {
        this.fotoPerfilAlumnoUno = fotoPerfilAlumnoUno;
    }

    public String getFotoPerfilAlumnoDos() {
        return fotoPerfilAlumnoDos;
    }

    public void setFotoPerfilAlumnoDos(String fotoPerfilAlumnoDos) {
        this.fotoPerfilAlumnoDos = fotoPerfilAlumnoDos;
    }
}
