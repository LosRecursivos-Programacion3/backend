/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.business.impl;

import java.util.List;
import pucp.edu.pe.pucpconnect.business.PostService;
import pucp.edu.pe.pucpconnect.domain.Social.Post;
import pucp.edu.pe.pucpconnect.domain.Social.PostConAutor;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;
import pucp.edu.pe.pucpconnect.persistence.dao.Social.PostDAO;

/**
 *
 * @author Fernando
 */
public class PostServiceImpl implements PostService {
    private final PostDAO postDAO;
    
    public PostServiceImpl(PostDAO postDAO){
        this.postDAO = postDAO;
    }
    
    @Override
    public void crearPost(String descripcion, String imagen, Alumno alumno) throws Exception {
        Post post = alumno.crearPost(descripcion, imagen);
        postDAO.agregar(post);
    }
    
    @Override
    public List<PostConAutor> listarPostParaMain(int idAlumno) throws Exception{
        return postDAO.obtenerPostsAmigosYPropios(idAlumno);
    }
}
