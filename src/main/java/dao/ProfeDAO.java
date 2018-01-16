package dao;

import java.sql.ResultSet;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import model.Asignatura;
import model.Curso;
import model.Nota;
import model.Tarea;
import model.User;
import utils.Queries;

public class ProfeDAO {

    private final DBManager manager = new DBManager();
    public List<Nota> getAllNotas(User profe) {
        /* Con RowMapper personalizado para rellenar campos de objetos del modelo */
        return (List<Nota>) this.manager.queryRunnable(Queries.queryGetAllNotasProfe,(ResultSet rs, int rowNum) ->
        {
            Nota nota = new Nota();

            nota.setNota(rs.getDouble(7));

            User alumno = new User();
            alumno.setId(rs.getInt(1));
            alumno.setNombre(rs.getString(2));
            nota.setAlumno(alumno);

            Asignatura asignatura = new Asignatura();

            asignatura.setId(rs.getInt(3));
            asignatura.setNombre(rs.getString(4));
            nota.setAsignatura(asignatura);

            Curso curso = new Curso();
            curso.setId(rs.getInt(5));
            curso.setNombre(rs.getString(6));
            nota.setCurso(curso);
            return nota;
        },profe.getId());
    }

    public int getId(User profe) {
        return (int) this.manager.queryForInt(Queries.queryGetId,profe.getEmail());
    }
    
    public String getNombreAsignatura(Asignatura asignatura) {
        return (String) this.manager.queryForObject(Queries.queryGetAsignaturaNombre,String.class,asignatura.getId());
    }

    public boolean modificarNota(Nota nota) {
        return this.manager.update(Queries.queryModNota, nota.getNota(), nota.getAlumno().getId(), nota.getAsignatura().getId());
    }

    public List<Nota> getAllNotasCursos(User profe) {
        /* Con RowMapper personalizado para rellenar campos de objetos del modelo */
        return (List<Nota>) this.manager.queryRunnable(Queries.queryGetAllNotasCurso,(ResultSet rs, int rowNum) ->
        {
            Nota nota = new Nota();

            nota.setNota(rs.getDouble(5));

            Asignatura asignatura = new Asignatura();

            asignatura.setId(rs.getInt(3));
            asignatura.setNombre(rs.getString(4));
            nota.setAsignatura(asignatura);

            Curso curso = new Curso();
            curso.setNombre(rs.getString(1));
            curso.setId(rs.getInt(2));
            nota.setCurso(curso);
            return nota;
        },profe.getId());
    }

    public List<Nota> getAllNotasCursosAlumnos(User alumno) {
        
        return (List<Nota>) this.manager.queryRunnable(Queries.queryGetAllNotasCursoAlumnos,(ResultSet rs, int rowNum) ->
        {
            Nota nota = new Nota();

            nota.setNota(rs.getDouble(7));

            Asignatura asignatura = new Asignatura();

            asignatura.setId(rs.getInt(3));
            asignatura.setNombre(rs.getString(4));
            nota.setAsignatura(asignatura);

            User alum = new User();

            alum.setId(rs.getInt(5));
            alum.setNombre(rs.getString(6));
            nota.setAlumno(alum);

            Curso curso = new Curso();
            curso.setNombre(rs.getString(1));
            curso.setId(rs.getInt(2));
            nota.setCurso(curso);
            return nota;
        },alumno.getId());
    }

    /* por aqui */
    public boolean addTarea(Tarea tarea, List<Integer> idAlumnos, String email) {
        ArrayList inserts = new ArrayList<>();
        /* Añadir tarea */
        inserts.add(new AbstractMap.SimpleEntry<>(Queries.queryAddTarea, new Object[]{
            tarea.getAsignatura().getId(),
            tarea.getNombre_tarea(),
            new java.sql.Date(tarea.getFecha_entrega().getTime()),
            email
        }));
        /* Añadir tareas a los alumnos */
        for (int i = 0; i < idAlumnos.size(); i++) {
            inserts.add(new AbstractMap.SimpleEntry<>(Queries.queryAddTareaAlumno, new Object[]{
                tarea.getId_tarea(),
                idAlumnos.get(i)
            }));
        }
        return this.manager.insertAllList(
            inserts
        );
    }

    public List<Integer> getIdAlumnos(Asignatura asignatura) {
        return (List<Integer>) this.manager.queryAll(Queries.queryGetIdAlumnos,Integer.class, asignatura.getId());
    }

    public boolean modTarea(Tarea tarea) {
        return this.manager.update(Queries.queryModTarea, tarea.getNombre_tarea(), tarea.getFecha_entrega(), tarea.getId_tarea());
    }
    
    public List<Tarea> getAllTareas(User profe) {
        /* Con RowMapper personalizado para rellenar campos de objetos del modelo */
        return (List<Tarea>) this.manager.queryRunnable(Queries.queryGetAllTareasProfe,(ResultSet rs, int rowNum) ->
        {
            Tarea tarea = new Tarea();
            tarea.setNombre_tarea(rs.getString(1));
            tarea.setFecha_entrega(new java.util.Date(rs.getTimestamp(2).getTime()));
            tarea.setEmail_profesor(rs.getString(3));
            tarea.getAsignatura().setId(rs.getInt(4));
            tarea.getAsignatura().setNombre(rs.getString(5));
            tarea.setId_tarea(rs.getInt(6));

            return tarea;
        },profe.getEmail());
    }
    
    public boolean delTarea (Tarea tarea){
        return this.manager.deleteAll(
            new AbstractMap.SimpleEntry<>(Queries.queryDelTareaProfeAlumno, new Object[]{tarea.getId_tarea()}),
            new AbstractMap.SimpleEntry<>(Queries.queryDelTareaProfe, new Object[]{tarea.getId_tarea()})
        );
    }
}
