/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.dao.Usuarios;

import java.sql.SQLException;
import java.util.List;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;

/**
 *
 * @author irico17
 */
public interface InteresDAO extends BaseDAO<Interes> {
    public List<Interes> listarInteresesUsuario(int idUsuario) throws SQLException;
    public void agregarInteresUsuario(List<Integer> ids, int idAlumno) throws SQLException;
}
