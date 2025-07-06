/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pucp.edu.pe.pucpconnect.domain.Social;

import pucp.edu.pe.pucpconnect.domain.Usuarios.Alumno;
import pucp.edu.pe.pucpconnect.domain.Interacciones.Reaccion;
import pucp.edu.pe.pucpconnect.domain.Interacciones.ReaccionTipo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import pucp.edu.pe.pucpconnect.domain.Usuarios.Interes;

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
    private List<Interes>intereses;
    private String fechaString;
    private String fechaFinString;
    /*agregadoo*/

    private LocalDateTime fechaFin;
    private String imagen;

    public Evento() {
    }

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
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre inválido");
        }
        this.setNombre(nombre);
        this.setDescripcion(descripcion);
        this.setFecha(fecha);
        this.setUbicacion(ubicacion);
    }

    public void eliminar() {
        this.setEstado(false);
    }

    public void agregarParticipante(Alumno alumno) {
        if (alumno == null) {
            throw new IllegalArgumentException("Alumno inválido");
        }
        if (participantes == null) {
            participantes = new ArrayList<>();
        }
        participantes.add(alumno);
    }

    public void eliminarParticipante(Alumno alumno) {
        if (alumno == null) {
            throw new IllegalArgumentException("Alumno invalido");
        }
        participantes.remove(alumno);
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

    public List<Alumno> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Alumno> participantes) {
        this.participantes = participantes;
    }

    public String getFechaAsString() {
    if (this.fecha == null) return null;
    return this.fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
}

public String getFechaFinAsString() {
    if (this.fechaFin == null) return null;
    return this.fechaFin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
}


    public static final DateTimeFormatter DB_DATE_FORMATTER
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        public List<Reaccion> getReacciones() {
        return reacciones;
    }

    public void setReacciones(List<Reaccion> reacciones) {
        this.reacciones = reacciones;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getImagen() {
        return imagen;
    }

    /*Agregado*/
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<Interes> getIntereses() {
        if(intereses == null ) intereses = new ArrayList<>();
        return intereses;
    }

    public void setIntereses(List<Interes> intereses) {
        this.intereses = intereses;
    }
    
    public String getFechaString() {
        if (this.fecha != null && this.fechaString == null) {
            this.fechaString = this.fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return fechaString;
    }
    
    public void setFechaString(String fechaString) {
        this.fechaString = fechaString;
    }
    
    public String getFechaFinString() {
        if (this.fechaFin != null && this.fechaFinString == null) {
            this.fechaFinString = this.fechaFin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return fechaFinString;
    }
    
    public void setFechaFinString(String fechaFinString) {
        this.fechaFinString = fechaFinString;
    } 
}
