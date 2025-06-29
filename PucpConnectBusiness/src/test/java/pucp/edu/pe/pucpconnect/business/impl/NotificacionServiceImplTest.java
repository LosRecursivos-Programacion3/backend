package pucp.edu.pe.pucpconnect.business.impl;

import org.junit.jupiter.api.*;
import pucp.edu.pe.pucpconnect.business.NotificacionService;
import pucp.edu.pe.pucpconnect.domain.Social.Notificacion;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Social.NotificacionDAOImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NotificacionServiceImplTest {

    /* 

    // Usamos dos usuarios de prueba que deben existir en la tabla Usuario
    private static final int TEST_USER_A = 1;
    private static final int TEST_USER_B = 2;

    private BaseDAO<Notificacion> dao;
    private NotificacionService service;

    @BeforeAll
    static void setUpClass() {
        // Aquí podrías verificar que TEST_USER_A y TEST_USER_B existan
    }

    @BeforeEach
    void setUp() {
        dao     = new NotificacionDAOImpl();
        service = new NotificacionServiceImpl(dao);
    }

    @AfterEach
    void tearDown() {
        // Limpiamos las notificaciones de ambos usuarios antes de cada test
        dao.listarTodos().stream()
           .filter(n -> n.getUsuarioId() == TEST_USER_A || n.getUsuarioId() == TEST_USER_B)
           .forEach(n -> dao.eliminar(n.getIdNotificacion()));
    }

    @Test
    void testCrearNotificacion() throws Exception {
        Notificacion n = new Notificacion();
        n.setUsuarioId(TEST_USER_A);
        n.setTipo("TEST_CREAR");
        n.setMensaje("Creando notificación");

        service.crearNotificacion(n);

        assertTrue(n.getIdNotificacion() > 0);
        assertNotNull(n.getFecha());
        assertTrue(n.isEstado());
        assertFalse(n.isVisto());
    }

    @Test
    void testListarNotificaciones() throws Exception {
        // Creamos una notificación para A
        Notificacion n = new Notificacion();
        n.setUsuarioId(TEST_USER_A);
        n.setTipo("TEST_LISTAR");
        n.setMensaje("Listado de prueba");
        service.crearNotificacion(n);

        List<Notificacion> listaA = service.listarNotificaciones(TEST_USER_A);
        assertNotNull(listaA);
        assertFalse(listaA.isEmpty());
        assertTrue(listaA.stream().allMatch(x -> x.getUsuarioId() == TEST_USER_A));
    }

    @Test
    void testNotificarMatchDistintos() throws Exception {
        // Usuario A hace match con B
        service.notificarMatch(TEST_USER_A, TEST_USER_B);

        List<Notificacion> listaA = service.listarNotificaciones(TEST_USER_A);
        assertTrue(listaA.stream().anyMatch(n -> "MATCH".equals(n.getTipo())),
                "Usuario A debe recibir notificación MATCH");

        List<Notificacion> listaB = service.listarNotificaciones(TEST_USER_B);
        assertTrue(listaB.stream().anyMatch(n -> "MATCH".equals(n.getTipo())),
                "Usuario B debe recibir notificación MATCH");
    }

    @Test
    void testNotificarMensajeRecibido() throws Exception {
        service.notificarMensajeRecibido(TEST_USER_A, TEST_USER_B, "Hola Mundo");

        List<Notificacion> listaB = service.listarNotificaciones(TEST_USER_B);
        assertTrue(listaB.stream()
                .anyMatch(n -> "MENSAJE".equals(n.getTipo())
                             && n.getMensaje().contains("Hola Mundo")));
    }

    @Test
    void testNotificarMensajeRecibidoGenerico() throws Exception {
        service.notificarMensajeRecibido(TEST_USER_A, TEST_USER_B, "");

        List<Notificacion> listaB = service.listarNotificaciones(TEST_USER_B);
        assertTrue(listaB.stream()
                .anyMatch(n -> "MENSAJE".equals(n.getTipo())
                             && n.getMensaje().toLowerCase()
                                              .contains("nuevo mensaje de " + TEST_USER_A)));
    }

    @Test
    void testNotificarSolicitudConexion() throws Exception {
        // Solicitud de A a B
        service.notificarSolicitudConexion(TEST_USER_A, TEST_USER_B);

        List<Notificacion> listaB = service.listarNotificaciones(TEST_USER_B);
        assertTrue(listaB.stream()
                .anyMatch(n -> "SOLICITUD_CONEXION".equals(n.getTipo())),
                "Usuario B debe recibir notificación SOLICITUD_CONEXION");
    }

    @Test
    void testMarcarComoVisto() throws Exception {
        // Creamos en B
        Notificacion n = new Notificacion();
        n.setUsuarioId(TEST_USER_B);
        n.setTipo("TEST_VISTO");
        n.setMensaje("Marcar visto");
        service.crearNotificacion(n);
        int id = n.getIdNotificacion();

        service.marcarComoVisto(id);

        Notificacion actualizado = service.listarNotificaciones(TEST_USER_B).stream()
                .filter(x -> x.getIdNotificacion() == id)
                .findFirst().orElseThrow();
        assertTrue(actualizado.isVisto());
    }

    @Test
    void testCambiarEstado() throws Exception {
        // Creamos en A
        Notificacion n = new Notificacion();
        n.setUsuarioId(TEST_USER_A);
        n.setTipo("TEST_ESTADO");
        n.setMensaje("Cambio de estado");
        service.crearNotificacion(n);
        int id = n.getIdNotificacion();

        service.cambiarEstado(id, false);

        Notificacion actualizado = service.listarNotificaciones(TEST_USER_A).stream()
                .filter(x -> x.getIdNotificacion() == id)
                .findFirst().orElseThrow();
        assertFalse(actualizado.isEstado());
    }
        */
}
