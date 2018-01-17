package model;

import java.util.logging.Logger;

public class Nota {
    private User alumno;
    private Asignatura asignatura;
    private Curso curso;
    private double nota;

    public Nota()
    {
        asignatura = new Asignatura();
        alumno = new User();
        curso = new Curso();
    }
    public User getAlumno()
    {
        return alumno;
    }

    public void setAlumno(User alumno)
    {
        this.alumno = alumno;
    }

    public Asignatura getAsignatura()
    {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura)
    {
        this.asignatura = asignatura;
    }

    public Curso getCurso()
    {
        return curso;
    }

    public void setCurso(Curso curso)
    {
        this.curso = curso;
    }

    public double getNota()
    {
        return nota;
    }

    public void setNota(double nota)
    {
        if(nota < 0)
        {
            nota = 0;
        }
        else if(nota > 10)
        {
            nota = 10;
        }
        this.nota = nota;
    }
    private static final Logger LOG = Logger.getLogger(Nota.class.getName());

}
