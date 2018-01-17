package model;

import java.util.logging.Logger;

public class AsignaturaUser {
    
    private int id_profesor;
    private int id_alumno;
    private int id_asignatura;
    private int nota;

    public int getId_profesor()
    {
        return id_profesor;
    }

    public void setId_profesor(int id_profesor)
    {
        this.id_profesor = id_profesor;
    }

    public int getId_alumno()
    {
        return id_alumno;
    }

    public void setId_alumno(int id_alumno)
    {
        this.id_alumno = id_alumno;
    }

    public int getId_asignatura()
    {
        return id_asignatura;
    }

    public void setId_asignatura(int id_asignatura)
    {
        this.id_asignatura = id_asignatura;
    }

    public int getNota()
    {
        return nota;
    }

    public void setNota(int nota)
    {
        this.nota = nota;
    }
    private static final Logger LOG = Logger.getLogger(AsignaturaUser.class.getName());

}
