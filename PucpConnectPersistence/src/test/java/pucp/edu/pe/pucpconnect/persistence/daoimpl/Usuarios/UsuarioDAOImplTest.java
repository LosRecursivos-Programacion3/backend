
package pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios;

import org.junit.jupiter.api.*;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Usuario;
import pucp.edu.pe.pucpconnect.persistence.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDAOImplTest {

    private Connection conn;
    private UsuarioDAOImpl usuarioDAO;

    @BeforeAll
    public static void setUpClass() {
        System.out.println("Iniciando tests de UsuarioDAOImpl...");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("Finalizando tests de UsuarioDAOImpl...");
    }

    @BeforeEach
    public void setUp() throws Exception {
        conn = DBManager.getInstance().obtenerConexion();
        usuarioDAO = new UsuarioDAOImpl();
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Test
    public void testGetInsertPS() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNombre("TestUser");
        usuario.setPassword("123456");
        usuario.setEstado(true);
        usuario.setEmail("test@example.com");
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setVisible(true);

        PreparedStatement ps = usuarioDAO.getInsertPS(conn, usuario);
        assertNotNull(ps);
        int result = ps.executeUpdate();
        assertTrue(result >= 0);
    }

    @Test
    public void testBuscarPorId() {
        int idUsuario = 1; // Asegúrate de que este ID exista en la base de datos
        Usuario result = usuarioDAO.buscarPorId(idUsuario);
        assertNotNull(result);
        assertEquals(idUsuario, result.getId());
    }
}
