package model;

import java.util.Date;


public class Tarea {
    private int id_tarea;
    private Asignatura asignatura;
    private String nombre_tarea;
    private Date fecha_entrega;
    private boolean completada;

    public int getId_tarea() {
        return id_tarea;
    }

    public void setId_tarea(int id_tarea) {
        this.id_tarea = id_tarea;
    }

    public String getNombre_tarea() {
        return nombre_tarea;
    }

    public void setNombre_tarea(String nombre_tarea) {
        this.nombre_tarea = nombre_tarea;
    }

    public Date getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(Date fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public Asignatura getAsignatura()
    {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura)
    {
        this.asignatura = asignatura;
    }

    public boolean isCompletada()
    {
        return completada;
    }

    public void setCompletada(boolean completada)
    {
        this.completada = completada;
    }
    
    
}
