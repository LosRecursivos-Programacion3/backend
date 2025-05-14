/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package pucp.edu.pe.pucpconnect.persistence;

import java.time.LocalDateTime;
import pucp.edu.pe.pucpconnect.domain.Social.Post;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Usuario;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.UsuarioDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Interacciones.ComentarioDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Interacciones.ReaccionDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Social.PostDAOImpl;

/**
 *
 * @author Irico
 */
public class PucpConnectPersistence {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        // Crear DAOs
        UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl();
        PostDAOImpl postDAO = new PostDAOImpl();
        ComentarioDAOImpl comentarioDAO = new ComentarioDAOImpl();
        ReaccionDAOImpl reaccionDAO = new ReaccionDAOImpl();
        
        // Prueba CRUD para Usuario
        System.out.println("Prueba CRUD Usuario");

        // Crear un Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre("Carlos Pérez");
        usuario.setPassword("password");
        usuario.setEstado(true);
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setEmail("carlos@example.com");
        usuario.setVisible(true);
        
        usuarioDAO.agregar(usuario);  // Inserción
        System.out.println("Usuario insertado.");

        // Leer un Usuario
        Usuario usuarioLeido = usuarioDAO.obtener(1);  // ID de ejemplo
        System.out.println("Usuario leído: " + usuarioLeido.getNombre());

        // Actualizar un Usuario
        usuarioLeido.setNombre("Carlos Pérez Actualizado");
        usuarioDAO.actualizar(usuarioLeido);  // Actualización
        System.out.println("Usuario actualizado.");

        // Eliminar un Usuario
        usuarioDAO.eliminar(1);  // Eliminación
        System.out.println("Usuario eliminado.");

        // Prueba CRUD para Post
        System.out.println("\nPrueba CRUD Post");

        // Crear un Post
        Post post = new Post();
        post.setNombre("Nuevo Post");
        post.setContenido("Este es el contenido del nuevo post.");
        post.setFecha(LocalDateTime.now());
        post.setEstado(true);

        postDAO.agregar(post);  // Inserción
        System.out.println("Post insertado.");

        // Leer un Post
        Post postLeido = postDAO.obtener(1);  // ID de ejemplo
        System.out.println("Post leído: " + postLeido.getContenido());

        // Actualizar un Post
        postLeido.setContenido("Contenido del post actualizado.");
        postDAO.actualizar(postLeido);  // Actualización
        System.out.println("Post actualizado.");

        // Eliminar un Post
        postDAO.eliminar(1);  // Eliminación
        System.out.println("Post eliminado.");

    }
}
