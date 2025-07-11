/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.dao.Usuarios;

import java.sql.SQLException;
import java.util.List;
import pucp.edu.pe.pucpconnect.domain.Social.Amistad;
import pucp.edu.pe.pucpconnect.domain.Social.Post;

import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;

/**
 *
 * @author USUARIO
 */
public interface AlumnoDAO extends BaseDAO<Alumno> {
    boolean bloquearAlumno(int idAlumno, int idBloqueado);
    boolean desbloquearAlumno(int idAlumno, int idBloqueado);
    List<Integer> obtenerAlumnosBloqueados(int idAlumno);
    public Alumno buscarPorIdUsuario(int idUsuario);
    public Alumno modificar(Alumno alumno) throws SQLException;
    public List<Alumno> listarAmigosSugeridos(List<Interes> intereses, int idAlumnoBuscador) throws SQLException;
    public void registrarSolicitudAmistad(int idUsuario1, int idUsuario2) throws SQLException;
    public List<Amistad> listarSolicitudesEnviadas(int idAlumno) throws SQLException;
    public List<Amistad> listarSolicitudesRecibidas(int idAlumno) throws SQLException;
    public void actualizarEstadoAmistad(int idAmistad, int nuevoEstado) throws SQLException;
    public List<Alumno> obtenerAmigosPorUsuario(int usuarioId) throws SQLException;
    public List<Post> obtenerPostPorUsuario(int usuarioId) throws SQLException;
    public List<Alumno> listarAmigosSugeridos_Match(List<Interes> intereses, int idAlumnoBuscador) throws SQLException;
    public int existeInteraccion(int idAlumnoDos, int idAlumnoUno) throws SQLException;
    public void aceptarInteraccion(int idInteraccion) throws SQLException;
    public void agregarInteraccion(int idAlumnoDos, int idAlumnoUno) throws SQLException;
    public void agregarAmistad(int idAlumnoUno, int idAlumnoDos) throws Exception;
}
