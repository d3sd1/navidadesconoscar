package model;
public class Nota {
    private int id_alumno;
    private int id_asignatura;
    private String nombre_asignatura;
    private int nota;

    public int getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(int id_alumno) {
        this.id_alumno = id_alumno;
    }

    public int getId_asignatura() {
        return id_asignatura;
    }

    public void setId_asignatura(int id_asignatura) {
        this.id_asignatura = id_asignatura;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getNombre_asignatura()
    {
        return nombre_asignatura;
    }

    public void setNombre_asignatura(String nombre_asignatura)
    {
        this.nombre_asignatura = nombre_asignatura;
    }
    
    
}
