/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pucp.edu.pe.pucpconnect.business;

import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;

/**
 *
 * @author Fernando
 */
public interface PostService {
    public void crearPost(String descripcion, String imagen, Alumno alumno) throws Exception;
}
