/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.business.impl;

import java.util.List;
import pucp.edu.pe.pucpconnect.business.InteresService;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;
import pucp.edu.pe.pucpconnect.persistence.dao.Usuarios.InteresDAO;

/**
 *
 * @author Fernando
 */
public class InteresServiceImpl implements InteresService{
    private final BaseDAO<Interes> interesDAO;

    public InteresServiceImpl(BaseDAO<Interes> interesDAO) {
        this.interesDAO = interesDAO;
    }
    
    @Override
    public List<Interes> listarIntereses(){
       List<Interes> intereses = interesDAO.listarTodos();
       return intereses;
    }
    
    @Override
    public List<Interes> obtenerInteresesUsuario(int idAlumno) throws Exception{
        InteresDAO dao = (InteresDAO) interesDAO;
        List<Interes> interesesUsuario = dao.listarInteresesUsuario(idAlumno);
        return interesesUsuario;
    }
    
    @Override
    public void insertarIntereses(List<Integer> ids, int idAlumno) throws Exception{
        InteresDAO dao = (InteresDAO) interesDAO;
        dao.agregarInteresUsuario(ids, idAlumno);
    }
}
