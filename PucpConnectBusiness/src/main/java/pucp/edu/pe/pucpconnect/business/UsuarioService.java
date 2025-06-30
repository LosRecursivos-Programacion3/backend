package pucp.edu.pe.pucpconnect.business;

import java.sql.SQLException;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Usuario;

import java.util.List;

public interface UsuarioService {

    Usuario autenticarUsuario(String correo, String password) throws Exception;

    void cambiarVisibilidad(int idUsuario, boolean visible) throws Exception;

    List<Usuario> listarSugerencias(int idUsuario);  // Opcional: basado en intereses, carrera, etc.

    void bloquearUsuario(int idBloqueador, int idBloqueado) throws Exception;

    List<Usuario> listarMatches(int idUsuario); // Si decides centralizarlo aquí

    public void hacerMatch(int idAlumnoUno, int idAlumnoDos) throws SQLException;
    
    public List<Usuario>listarUsuarios();
}
