/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package pucp.edu.pe.pucpconnect.business;

import pucp.edu.pe.pucpconnect.business.impl.AlumnoServiceImpl;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Usuarios.AlumnoDAOImpl;

/**
 *
 * @author Irico
 */
public class PucpConnectBusiness {

    public static void main(String[] args) {    
        BaseDAO<Alumno> alumnoDAO = new AlumnoDAOImpl(); 
        AlumnoService alumnoService = new AlumnoServiceImpl(alumnoDAO);

        Alumno nuevoAlumno = new Alumno();
        nuevoAlumno.setNombre("Pedro");
        nuevoAlumno.setEdad(20);
        nuevoAlumno.setEmail("pedro@pucp.edu.pe");
        //nuevoAlumno.setCarrera(null);

        try {
            alumnoService.registrarAlumno(nuevoAlumno);
            System.out.println("Alumno registrado correctamente");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
