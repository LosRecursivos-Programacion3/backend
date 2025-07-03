/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceException;
import java.util.List;
import pucp.edu.pe.pucpconnect.business.InteresService;
import pucp.edu.pe.pucpconnect.business.impl.InteresServiceImpl;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.InteresDAOImpl;

/**
 *
 * @author stefa
 */

@WebService(serviceName = "InteresWS")
public class InteresWS {

    private InteresService interesService;
    
    public InteresWS() {
       BaseDAO<Interes>InteresDAO = new InteresDAOImpl();
       interesService = new InteresServiceImpl(InteresDAO);
    }
    @WebMethod(operationName = "listarInteres")
    public List<Interes> listarInteres() {
        try {
            return interesService.listarIntereses();
        } catch (Exception e) {
            throw new WebServiceException("Error al listar intereses: " + e.getMessage(), e);
        }
    }
    
    
    
    
    
}
