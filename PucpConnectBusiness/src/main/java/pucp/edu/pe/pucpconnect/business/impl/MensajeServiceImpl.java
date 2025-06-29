/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.business.impl;

import pucp.edu.pe.pucpconnect.business.MensajeService;
import pucp.edu.pe.pucpconnect.domain.Interacciones.Mensaje;
import pucp.edu.pe.pucpconnect.persistence.daoimpl.Interacciones.MensajeDAOImpl;

import java.util.List;

public class MensajeServiceImpl implements MensajeService {
    
    private final MensajeDAOImpl mensajeDAO;

    public MensajeServiceImpl() {
        mensajeDAO = new MensajeDAOImpl();
    }

    @Override
    public void registrarMensaje(Mensaje mensaje) {
        mensajeDAO.agregar(mensaje);
    }

    @Override
    public List<Mensaje> obtenerMensajesEntreUsuarios(int emisorId, int receptorId) {
        return mensajeDAO.obtenerMensajesEntreUsuarios(emisorId, receptorId);
    }

    @Override
    public void eliminarMensaje(int mensajeId) {
        mensajeDAO.eliminar(mensajeId);
    }
}