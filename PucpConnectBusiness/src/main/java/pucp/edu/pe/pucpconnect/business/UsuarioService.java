package pucp.edu.pe.pucpconnect.business;

import pucp.edu.pe.pucpconnect.domain.Usuarios.Usuario;

import java.util.List;

public interface UsuarioService {

    boolean autenticarUsuario(String correo, String password) throws Exception;

    void cambiarVisibilidad(int idUsuario, boolean visible) throws Exception;

    List<Usuario> listarSugerencias(int idUsuario);  // Opcional: basado en intereses, carrera, etc.

    void bloquearUsuario(int idBloqueador, int idBloqueado) throws Exception;

    List<Usuario> listarMatches(int idUsuario); // Si decides centralizarlo aquí
}
