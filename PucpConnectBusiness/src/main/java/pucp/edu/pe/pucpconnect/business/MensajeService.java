/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.business;

import pucp.edu.pe.pucpconnect.domain.Interacciones.Mensaje;
import java.util.List;

public interface MensajeService {

    // Método para registrar un nuevo mensaje
    void registrarMensaje(Mensaje mensaje);

    // Método para obtener los mensajes entre dos usuarios
    List<Mensaje> obtenerMensajesEntreUsuarios(int emisorId, int receptorId);

    // Método para eliminar un mensaje
    void eliminarMensaje(int mensajeId);
}
