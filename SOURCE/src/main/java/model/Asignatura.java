package model;

import java.util.logging.Logger;

public class Asignatura {
    
    private int id;
    private String nombre;
    private int id_curso;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }
    private static final Logger LOG = Logger.getLogger(Asignatura.class.getName());
    
    
}
