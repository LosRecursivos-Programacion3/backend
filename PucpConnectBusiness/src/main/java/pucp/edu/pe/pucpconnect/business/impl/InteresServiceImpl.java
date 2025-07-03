/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.business.impl;

import java.util.List;
import pucp.edu.pe.pucpconnect.business.InteresService;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;

/**
 *
 * @author stefa
 */
public class InteresServiceImpl implements InteresService {
    
    private BaseDAO<Interes>InteresDAO;

    public InteresServiceImpl(BaseDAO<Interes> InteresDAO) {
        this.InteresDAO = InteresDAO;
    }
    
    @Override
    public List<Interes>listarIntereses(){
        return InteresDAO.listarTodos();
    }
    
    
}
