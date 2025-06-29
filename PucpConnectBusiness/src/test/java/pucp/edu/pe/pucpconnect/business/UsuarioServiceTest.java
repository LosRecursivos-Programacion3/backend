/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pucp.edu.pe.pucpconnect.business;

import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pucp.edu.pe.pucpconnect.business.impl.AlumnoServiceImpl;
import pucp.edu.pe.pucpconnect.business.impl.UsuarioServiceImpl;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Usuario;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.AlumnoDAOImpl;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.UsuarioDAOImpl;

/**
 *
 * @author Fernando
 */
public class UsuarioServiceTest {
    
    public UsuarioServiceTest() {
        
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of autenticarUsuario method, of class UsuarioService.
     */
    /*@Test
    public void testFlujoLoginCompleto() throws Exception {
        System.out.println("=== testFlujoLoginCompleto ===");

        // Paso 1: Autenticación
        BaseDAO<Usuario> usuarioDAO = new UsuarioDAOImpl();
        UsuarioService usuarioService = new UsuarioServiceImpl(usuarioDAO);

        String correo = "ftest@pucp.edu.pe";
        String password = "123456"; // debe coincidir con la contraseña en la BD

        Usuario usuarioAutenticado = usuarioService.autenticarUsuario(correo, password);
        assertNotNull(usuarioAutenticado, "El usuario no se autenticó. Verifica email y contraseña.");
        System.out.println("Usuario autenticado con ID: " + usuarioAutenticado.getId());

        // Paso 2: Buscar datos completos del alumno
        BaseDAO<Alumno> alumnoDAO = new AlumnoDAOImpl();
        AlumnoService alumnoService = new AlumnoServiceImpl(alumnoDAO);

        Alumno result = alumnoService.buscarPorIdUsuario(usuarioAutenticado.getId());
        assertNotNull(result, "El alumno no fue encontrado a partir del ID del usuario autenticado.");

        // Simula lo que el WS retornaría al front combinando datos
        result.setId(usuarioAutenticado.getId());
        result.setNombre(usuarioAutenticado.getNombre());
        result.setPassword(usuarioAutenticado.getPassword());
        result.setEstado(usuarioAutenticado.getEstado());
        result.setFechaRegistro(usuarioAutenticado.getFechaRegistro());
        result.setEmail(usuarioAutenticado.getEmail());
        result.setVisible(usuarioAutenticado.isVisible());

        // Paso 3: Imprimir todos los datos
        System.out.println("=== Datos completos del alumno ===");
        System.out.println("ID: " + result.getId());
        System.out.println("Nombre: " + result.getNombre());
        System.out.println("Email: " + result.getEmail());
        System.out.println("Password: " + result.getPassword());
        System.out.println("Estado: " + result.getEstado());
        System.out.println("Fecha de Registro: " + result.getFechaRegistro());
        System.out.println("Visible: " + result.isVisible());

        System.out.println("Edad: " + result.getEdad());
        System.out.println("Carrera: " + result.getCarrera());
        System.out.println("Foto Perfil: " + result.getFotoPerfil());
        System.out.println("Ubicación: " + result.getUbicacion());
        System.out.println("Biografía: " + result.getBiografia());
        System.out.println("Bloqueados: " + result.getIdsAlumnosBloqueados());

        // Paso 4: Validaciones extra
        assertNotNull(result.getCarrera());
        assertTrue(result.getEdad() > 0);
        assertNotNull(result.getFotoPerfil());
        assertNotNull(result.getUbicacion());
        assertNotNull(result.getBiografia());
        assertNotNull(result.getIdsAlumnosBloqueados());

        System.out.println("=== FIN TEST ===");
    }*/
}
