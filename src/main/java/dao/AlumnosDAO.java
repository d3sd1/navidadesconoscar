package dao;

import java.sql.ResultSet;
import java.util.List;
import model.Asignatura;
import model.Nota;
import model.Tarea;
import model.User;
import utils.Queries;

public class AlumnosDAO
{
    private final DBManager manager = new DBManager();

    public List<Nota> getAllNotas(User alumno)
    {
        /* Con RowMapper personalizado para rellenar campos de objetos del modelo */
        return (List<Nota>) this.manager.queryRunnable(Queries.queryGetAllNotas,(ResultSet rs, int rowNum) ->
        {
            Nota nota = new Nota();

            nota.setNota(rs.getDouble(5));

            User alum = new User();
            alum.setId(rs.getInt(1));
            alum.setNombre(rs.getString(2));
            nota.setAlumno(alum);

            Asignatura asignatura = new Asignatura();

            asignatura.setId(rs.getInt(3));
            asignatura.setNombre(rs.getString(4));
            nota.setAsignatura(asignatura);

            return nota;
        },alumno.getEmail());
    }

    public List<Tarea> getAllTareas(User alumno)
    {
        /* Con RowMapper personalizado para rellenar campos de objetos del modelo */
        return (List<Tarea>) this.manager.queryRunnable(Queries.queryGetAllTareas,(ResultSet rs, int rowNum) ->
        {
            Tarea tarea = new Tarea();
            tarea.setId_tarea(rs.getInt(1));
            tarea.setNombre_tarea(rs.getString(2));
            tarea.setFecha_entrega(rs.getDate(3));
            tarea.setCompletada(rs.getBoolean(4));

            Asignatura asignatura = new Asignatura();
            asignatura.setId(rs.getInt(5));
            asignatura.setNombre(rs.getString(6));
            tarea.setAsignatura(asignatura);

            return tarea;
        },alumno.getEmail());
    }

    public boolean completarTarea(Tarea tarea, User alumno)
    {
        return this.manager.update(Queries.queryCompletarTarea, tarea.getId_tarea(), alumno.getEmail());
    }

    public Tarea getTareaById(Tarea tarea, User alumno)
    {
        return (Tarea) this.manager.queryForObject(Queries.queryGetTareaById,Tarea.class,tarea.getId_tarea(),alumno.getEmail());
    }
}
