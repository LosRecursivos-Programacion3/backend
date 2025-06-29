/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;

/**
 *
 * @author Fernando
 */
public class AlumnoDAOImplTest {
    
    public AlumnoDAOImplTest() {
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
     * Test of getInsertPS method, of class AlumnoDAOImpl.
     */
    @Test
    public void testBuscarPorIdUsuario() {
        System.out.println("=== testBuscarPorIdUsuario ===");
        int idUsuario = 42;
        AlumnoDAOImpl instance = new AlumnoDAOImpl();

        Alumno result = instance.buscarPorIdUsuario(idUsuario);

        if (result != null) {
            System.out.println("Alumno encontrado:");
             
            assertNotNull(result, "El alumno no debería ser null");

            // Validar y mostrar todos los datos
            System.out.println("ID: " + result.getId());
            assertEquals(idUsuario, result.getId());

            System.out.println("Nombre: " + result.getNombre());
            assertNotNull(result.getNombre());

            System.out.println("Password: " + result.getPassword());
            assertNotNull(result.getPassword());

            System.out.println("Estado: " + result.getEstado());

            System.out.println("Fecha de registro: " + result.getFechaRegistro());
            assertNotNull(result.getFechaRegistro());

            System.out.println("Email: " + result.getEmail());
            assertNotNull(result.getEmail());

            System.out.println("Visible: " + result.isVisible());

            System.out.println("Edad: " + result.getEdad());
            assertTrue(result.getEdad() > 0);

            System.out.println("Carrera: " + result.getCarrera());
            assertNotNull(result.getCarrera());

            System.out.println("Foto Perfil: " + result.getFotoPerfil());
            assertNotNull(result.getFotoPerfil());

            System.out.println("Ubicación: " + result.getUbicacion());
            assertNotNull(result.getUbicacion());

            System.out.println("Biografía: " + result.getBiografia());
            assertNotNull(result.getBiografia());
            
            // agrega más atributos si deseas
        } else {
            System.out.println("No se encontró ningún alumno con idUsuario = " + idUsuario);
        }

        // Puedes ajustar esta parte según lo que esperas:
        assertNotNull(result); // Fallará si no existe ese alumno (útil para test real)
    }
    
}
