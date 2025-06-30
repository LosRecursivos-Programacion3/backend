/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.dao.Social;

import java.sql.SQLException;
import java.util.List;
import pucp.edu.pe.pucpconnect.domain.Social.Post;
import pucp.edu.pe.pucpconnect.domain.Social.PostConAutor;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;

/**
 *
 * @author iricl17
 */
public interface PostDAO extends BaseDAO<Post> {
    public List<PostConAutor> obtenerPostsAmigosYPropios(int idUsuario) throws SQLException;
}
