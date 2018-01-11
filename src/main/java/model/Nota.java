package model;
public class Nota {
    private User alumno;
    private Asignatura asignatura;
    private Curso curso;

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

}
