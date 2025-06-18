package pucp.edu.pe.pucpconnect.business.impl;

import pucp.edu.pe.pucpconnect.business.UsuarioService;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Usuario;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;

import java.util.ArrayList;
import java.util.List;

public class UsuarioServiceImpl implements UsuarioService {

    private BaseDAO<Usuario> usuarioDAO;

    public UsuarioServiceImpl(BaseDAO<Usuario> usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @Override
    public boolean autenticarUsuario(String correo, String password) throws Exception {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(correo) && u.autenticar(password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void cambiarVisibilidad(int idUsuario, boolean visible) throws Exception {
        Usuario usuario = usuarioDAO.obtener(idUsuario);
        if (usuario == null) {
            throw new Exception("Usuario no encontrado.");
        }
        usuario.setVisible(visible);
        usuarioDAO.actualizar(usuario);
    }

    @Override
    public List<Usuario> listarSugerencias(int idUsuario) {
        List<Usuario> sugerencias = new ArrayList<>();
        List<Usuario> todos = usuarioDAO.listarTodos();
    
        // Obtener la carrera del usuario actual
        Usuario usuarioActual = usuarioDAO.buscarPorId(idUsuario);
        if (usuarioActual == null || !(usuarioActual instanceof Alumno)) {
            return sugerencias; // Si no es alumno o no existe, retornamos vacío
        }
        String carreraUsuario = ((Alumno) usuarioActual).getCarrera();
    
        for (Usuario u : todos) {
            if (u.getId() != idUsuario && u.isVisible()) {
                if (u instanceof Alumno) {
                    Alumno alumno = (Alumno) u;
                    if (carreraUsuario.equalsIgnoreCase(alumno.getCarrera())) {
                        // Priorizar sugerencias de la misma carrera
                        sugerencias.add(0, u); // Insertar al inicio
                    } else {
                        // Sugerencias de otras carreras van al final
                        sugerencias.add(u);
                    }
                } else {
                    sugerencias.add(u); // Si no es alumno, igual se sugiere
                }
            }
        }
        return sugerencias;
    }

    @Override
    public void bloquearUsuario(int idBloqueador, int idBloqueado) throws Exception {
        // Por ahora, puedes lanzar un mensaje indicando que la función aún no está implementada en persistencia
        throw new UnsupportedOperationException("Funcionalidad de bloqueo aun no implementada.");
    }

    @Override
    public List<Usuario> listarMatches(int idUsuario) {
        List<Usuario> matches = new ArrayList<>();
    
        String query = "{CALL ListarMatches(?)}";
    
        try (Connection conn = Conexion.obtenerConexion();
             CallableStatement cs = conn.prepareCall(query)) {
    
            cs.setInt(1, idUsuario);
    
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("idUsuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setEstado(rs.getBoolean("estado"));
                    u.setFechaRegistro(rs.getTimestamp("fechaRegistro").toLocalDateTime());
                    matches.add(u);
                }
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return matches;
    }
}
