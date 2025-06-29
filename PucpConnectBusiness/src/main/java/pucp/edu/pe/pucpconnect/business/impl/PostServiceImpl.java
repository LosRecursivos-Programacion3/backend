/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.business.impl;

import pucp.edu.pe.pucpconnect.business.PostService;
import pucp.edu.pe.pucpconnect.domain.Social.Post;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;

/**
 *
 * @author Fernando
 */
public class PostServiceImpl implements PostService {
    private BaseDAO<Post> postDAO;
    
    public PostServiceImpl(BaseDAO<Post> postDAO){
        this.postDAO = postDAO;
    }
    
    @Override
    public void crearPost(String descripcion, String imagen, Alumno alumno) throws Exception {
        Post post = alumno.crearPost(descripcion, imagen);
        postDAO.agregar(post);
    }
}
