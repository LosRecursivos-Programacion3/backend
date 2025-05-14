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
        // Esta lógica puede mejorar con intereses comunes, etc.
        List<Usuario> sugerencias = new ArrayList<>();
        List<Usuario> todos = usuarioDAO.listarTodos();

        for (Usuario u : todos) {
            if (u.getId() != idUsuario && u.isVisible()) {
                sugerencias.add(u);
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
        // Dependerá de si tienes lógica/módulo de emparejamientos
        return new ArrayList<>(); // Por ahora retorna vacío
    }
}
