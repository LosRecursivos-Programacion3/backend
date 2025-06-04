
package pucp.edu.pe.pucpconnect.domain.Usuarios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Usuario {
    private int id;
    private String nombre;
    private String password;
    private boolean estado;
    private LocalDateTime fechaRegistro;
    private String email;
    private boolean visible;
    // NUEVO: Lista de IDs de usuarios bloqueados (RF12)
    private List<Integer> idsUsuariosBloqueados;

    public Usuario() {
        this.idsUsuariosBloqueados = new ArrayList<>();
    }

    public Usuario(int id, String nombre, String password, boolean estado,
                   LocalDateTime fechaRegistro, String email) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.email = email;
    }

    public boolean autenticar(String pw) {
        return this.password.equals(pw);
    }

    public void editarPerfil(String nombre, String email, String password) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("Nombre inválido");
        if (!validarEmail(email))
            throw new IllegalArgumentException("Email inválido");
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    public boolean validarEmail(String email) {
        return email != null && email.contains("@");
    }

    public boolean validarEmail() {
        return validarEmail(this.email);
    }

    public void bloquearUsuario(int idUsuario) {
        if (!idsUsuariosBloqueados.contains(idUsuario)) {
            idsUsuariosBloqueados.add(idUsuario);
        }
    }

    public void desbloquearUsuario(int idUsuario) {
        idsUsuariosBloqueados.remove(Integer.valueOf(idUsuario));
    }

    public boolean estaBloqueado(int idUsuario) {
        return idsUsuariosBloqueados.contains(idUsuario);
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean getEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public boolean isVisible() {return visible;}
    public void setVisible(boolean visible) {this.visible = visible;}
    public List<Integer> getIdsUsuariosBloqueados() {
        return idsUsuariosBloqueados;
    }

    public void setIdsUsuariosBloqueados(List<Integer> idsUsuariosBloqueados) {
        this.idsUsuariosBloqueados = idsUsuariosBloqueados;
    }
}
