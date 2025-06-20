/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.dao.Usuarios;

import java.util.List;

import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;

/**
 *
 * @author USUARIO
 */
public interface AlumnoDAO extends BaseDAO<Alumno> {
    boolean bloquearAlumno(int idAlumno, int idBloqueado);
    boolean desbloquearAlumno(int idAlumno, int idBloqueado);
    List<Integer> obtenerAlumnosBloqueados(int idAlumno);
}
