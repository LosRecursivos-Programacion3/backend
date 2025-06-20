/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pucp.edu.pe.pucpconnect.persistence.dao.Social;

import java.util.List;

import pucp.edu.pe.pucpconnect.domain.Social.Notificacion;
import pucp.edu.pe.pucpconnect.persistence.BaseDAO;

/**
 *
 * @author USUARIO
 */
public interface NotificacionDAO extends BaseDAO<Notificacion> {
    boolean insertarNotificacion(Notificacion notificacion);
    List<Notificacion> obtenerNotificacionesPorUsuario(int idUsuario);
    boolean marcarComoVisto(int idNotificacion);
}
