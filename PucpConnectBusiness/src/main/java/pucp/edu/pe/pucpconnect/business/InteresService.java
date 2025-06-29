
package pucp.edu.pe.pucpconnect.business;

import java.util.List;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;

/**
 *
 * @author Fernando
 */
public interface InteresService {
    List<Interes> listarIntereses();
    public List<Interes> obtenerInteresesUsuario(int idAlumno) throws Exception;
    public void insertarIntereses(List<Integer> ids, int idAlumno) throws Exception;
}
