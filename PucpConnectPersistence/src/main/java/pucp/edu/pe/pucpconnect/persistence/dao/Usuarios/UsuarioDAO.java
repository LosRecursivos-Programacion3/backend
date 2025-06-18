/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.dao.Usuarios;

import pucp.edu.pe.pucpconnect.domain.Usuarios.Usuario;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;

/**
 *
 * @author USUARIO
 */
public interface UsuarioDAO extends BaseDAO  <Usuario>{
    public Usuario buscarPorId(int idUsuario);
}
