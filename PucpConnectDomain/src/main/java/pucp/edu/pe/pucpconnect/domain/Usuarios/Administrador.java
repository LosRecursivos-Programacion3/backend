
package pucp.edu.pe.pucpconnect.domain.Usuarios;


public class Administrador extends Usuario {
    public Administrador() { super(); }

    public Administrador(int id, String nombre, String password, boolean estado,
                         java.time.LocalDateTime fechaRegistro, String email) {
        super(id, nombre, password, estado, fechaRegistro, email);
    }

    public void gestionarUsuarios() {
        // lógica de negocio para gestionar usuarios
    }

    public void moderarPost(int idPost) {
        // lógica de control / moderación
    }

    /*public Reporte generarReportes() {
        // stub para generación de reportes
        return new Reporte();
    }*/
}
