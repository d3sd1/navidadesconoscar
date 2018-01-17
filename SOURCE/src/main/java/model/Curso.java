package model;

import java.util.logging.Logger;

public class Curso {
    
    private int id;
    private String nombre;

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
    private static final Logger LOG = Logger.getLogger(Curso.class.getName());
    
    
}
