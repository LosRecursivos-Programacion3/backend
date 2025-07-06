/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.dao.Interacciones;

import java.util.List;
import pucp.edu.pe.pucpconnect.domain.Interacciones.Comentario;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;

/**
 *
 * @author USUARIO
 */
public interface ComentarioDAO extends BaseDAO<Comentario> {
     public List<Comentario> obtenerComentariosPorPost(int postId);
}
