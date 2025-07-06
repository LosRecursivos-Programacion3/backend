/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pucp.edu.pe.pucpconnect.business.impl;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pucp.edu.pe.pucpconnect.business.InteresService;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.InteresDAOImpl;

/**
 *
 * @author Fernando
 */
public class InteresServiceImplTest {
    
    public InteresServiceImplTest() {
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
     * Test of listarIntereses method, of class InteresServiceImpl.
     */
    /*@Test
    public void testListarIntereses() {
        System.out.println("=== testListarIntereses ===");

        // Paso 1: Crear el DAO y el servicio
        BaseDAO<Interes> interesDAO = new InteresDAOImpl();
        InteresService interesService = new InteresServiceImpl(interesDAO);

        // Paso 2: Llamar al método listarIntereses
        List<Interes> intereses = interesService.listarIntereses();

        // Paso 3: Validar y mostrar resultados
        assertNotNull(intereses, "La lista de intereses no debe ser null");
        assertFalse(intereses.isEmpty(), "La lista de intereses no debe estar vacía");

        System.out.println("Cantidad de intereses encontrados: " + intereses.size());

        for (Interes interes : intereses) {
            System.out.println("ID: " + interes.getId());
            System.out.println("Nombre: " + interes.getNombre());
            System.out.println("Descripción: " + interes.getDescripcion());
            System.out.println("----------------------------");
        }
    }*/
    
}
