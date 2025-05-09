/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.domain.Social;

import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.domain.Interacciones.Reaccion;
import pucp.edu.pe.pucpconnect.domain.Interacciones.ReaccionTipo;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Irico
 */
public class Evento {
    private int idEvento;
    private String nombre;
    private String descripcion;
    private LocalDateTime fecha;
    private String ubicacion;
    private boolean estado;
    private Alumno creador;
    private List<Alumno> participantes;
    private List<Reaccion> reacciones;

    public Evento() {}

    public Evento(int id, String nombre, String descripcion, LocalDateTime fecha, String ubicacion, boolean estado,
                  Alumno creador, List<Alumno> participantes, List<Reaccion> reacciones) {
        this.idEvento = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.creador = creador;
        this.participantes = participantes;
        this.reacciones = reacciones;
    }

    public void editarDatos(String nombre, String descripcion, LocalDateTime fecha, String ubicacion) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("Nombre inválido");
        this.setNombre(nombre);
        this.setDescripcion(descripcion);
        this.setFecha(fecha);
        this.setUbicacion(ubicacion);
    }

    public void eliminar() {
        this.setEstado(false);
    }

    public void agregarParticipante(Alumno alumno) {
        if (alumno == null)
            throw new IllegalArgumentException("Alumno inválido");
        participantes.add(alumno);
    }

    public Reaccion agregarReaccion(Alumno usuario, ReaccionTipo tipo) {
        Reaccion r = new Reaccion(0, tipo, LocalDateTime.now(), usuario, this);
        reacciones.add(r);
        return r;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Alumno getCreador() {
        return creador;
    }

    public void setCreador(Alumno creador) {
        this.creador = creador;
    }
}
