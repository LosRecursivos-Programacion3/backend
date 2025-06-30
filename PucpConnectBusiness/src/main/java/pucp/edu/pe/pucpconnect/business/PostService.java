/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pucp.edu.pe.pucpconnect.business;

import java.util.List;
import pucp.edu.pe.pucpconnect.domain.Social.Post;
import pucp.edu.pe.pucpconnect.domain.Social.PostConAutor;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;

/**
 *
 * @author Fernando
 */
public interface PostService {
    public void crearPost(String descripcion, String imagen, Alumno alumno) throws Exception;
    public List<PostConAutor> listarPostParaMain(int idAlumno) throws Exception;
}
