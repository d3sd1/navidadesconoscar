package dao;

import java.sql.ResultSet;
import java.util.List;
import model.Asignatura;
import model.Nota;
import model.Tarea;
import model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class AlumnosDAO
{

    private final String queryGetAllNotas = "SELECT u.id, u.nombre, a.id, a.nombre, aa.nota "
            + "FROM alumnos_asignaturas aa "
            + "JOIN users u ON aa.id_alumno = u.id "
            + "JOIN asignaturas a ON a.id = aa.id_asignatura "
            + "WHERE u.email = ? ";
    private final String queryGetAllTareas = "SELECT ta.id_tarea, t.nombre_tarea, t.fecha_entrega, ta.completado, a.id, a.nombre "
            + "FROM tareas_alumnos ta "
            + "JOIN tareas t ON ta.id_tarea = t.id_tarea "
            + "JOIN users u ON u.id = ta.id_alumno "
            + "JOIN asignaturas a ON t.id_asignatura = a.id "
            + "WHERE u.email = ?";
    private final String queryCompletarTarea = "UPDATE tareas_alumnos SET completado = 1 WHERE id_tarea = ? AND id_alumno = "
            + "(SELECT id FROM users WHERE email = ?)";
    private final String queryGetTareaById = "SELECT t.* FROM tareas t "
            + "JOIN tareas_alumnos ta ON t.id_tarea = ta.id_tarea "
            + "WHERE ta.id_tarea = ? AND id_alumno = "
            + "(SELECT id FROM users WHERE email = ?)";

    public List<Nota> getAllNotas(User alumno)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<Nota> alumnos = jtm.query(queryGetAllNotas, (ResultSet rs, int rowNum) ->
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
        }, alumno.getEmail());

        return alumnos;
    }

    public List<Tarea> getAllTareas(User alumno)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<Tarea> tareas = jtm.query(queryGetAllTareas, (ResultSet rs, int rowNum) ->
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
        }, alumno.getEmail());
        return tareas;
    }

    public boolean completarTarea(Tarea tarea, User alumno)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        boolean completado = jtm.update(queryCompletarTarea, tarea.getId_tarea(), alumno.getEmail()) > 0;
        return completado;
    }

    public Tarea getTareaById(Tarea tarea, User alumno)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        tarea = (Tarea) jtm.queryForObject(queryGetTareaById, new Object[]
        {
            tarea.getId_tarea(),
            alumno.getEmail()
        }, new BeanPropertyRowMapper(Tarea.class));
        
        return tarea;
    }
}
