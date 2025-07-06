/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.dao.Social;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import pucp.edu.pe.pucpconnect.domain.Social.Evento;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;

/**
 *
 * @author irico17
 */
public interface EventoDAO extends BaseDAO<Evento> {
    public void registrarParticipacionEvento(int idEvento, int idUsuario)throws SQLException;
    public List<Alumno> listarAlumnosPorEvento(int idEvento) throws SQLException;
    public boolean verificarParticipacion(int idEvento, int idUsuario) throws SQLException;
    public boolean cancelarParticipacion(int idEvento, int idUsuario) throws SQLException;
    public void registrarInteresesEvento(int idEvento, List<Integer> interesesIds) throws SQLException;
    public List<Interes> listarInteresesPorEvento(int idEvento);
    public List<Evento> listarEventosPorInteres(int idInteres) throws SQLException;
    public List<Evento> listarTodosEventos();
    public void agregarEvento(Evento evento);
    public int activarEventosEnCurso();
    public boolean desactivarEventosPasados() ;
    public List<Evento> listarPorRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
}