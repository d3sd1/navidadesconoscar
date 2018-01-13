package dao;

import java.sql.ResultSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Asignatura;
import model.Curso;
import model.Nota;
import model.Tarea;
import model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class AlumnosDAO {
    private final String queryGetAllNotas = "SELECT u.id, u.nombre, a.id, a.nombre, aa.nota "
            + "FROM alumnos_asignaturas aa "
            + "JOIN users u ON aa.id_alumno = u.id "
            + "JOIN asignaturas a ON a.id = aa.id_asignatura "
            + "WHERE u.email = ? ";
    private final String queryGetAllTareas = "SELECT ta.id_tarea, t.nombre_tarea, t.fecha_entrega, ta.completado "
            + "FROM tareas_alumnos ta "
            + "JOIN tareas t ON ta.id_tarea = t.id_tarea "
            + "JOIN users u ON u.id = ta.id_alumno "
            + "WHERE u.email = ?";
    private final String queryCompletarTarea = "UPDATE tareas_alumnos SET completado = 1 WHERE id_tarea = ? AND id_alumno = "
            + "(SELECT id FROM users WHERE email = ?)";
    private final String queryGetTareaById = "SELECT t.* FROM tareas t "
            + "JOIN tareas_alumnos ta ON t.id_tarea = ta.id_tarea "
            + "WHERE ta.id_tarea = ? AND id_alumno = "
            + "(SELECT id FROM users WHERE email = ?)";
    
    public List<Nota> getAllNotas(String email) {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<Nota> alumnos = jtm.query(queryGetAllNotas, (ResultSet rs, int rowNum)
                -> {
            Nota nota = new Nota();

            nota.setNota(rs.getDouble(5));

            User alumno = new User();
            alumno.setId(rs.getInt(1));
            alumno.setNombre(rs.getString(2));
            nota.setAlumno(alumno);

            Asignatura asignatura = new Asignatura();

            asignatura.setId(rs.getInt(3));
            asignatura.setNombre(rs.getString(4));
            nota.setAsignatura(asignatura);

            return nota;
        }, email);

        return alumnos;
    }
    
    public List<Tarea> getAllTareas(String email) {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<Tarea> tareas = jtm.query(queryGetAllTareas, new BeanPropertyRowMapper(Tarea.class), email);

        return tareas;
    }
    
    public boolean completarTarea (Tarea t, String email){
        boolean completado;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            completado = jtm.update(queryCompletarTarea, t.getId_tarea(), email) > 0;
        } catch (DataAccessException ex) {
            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
            completado = false;
        }
        return completado;
    }
    
    public Tarea getTareaById(int id, String email) {
        Tarea t;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            t =(Tarea) jtm.queryForObject(queryGetTareaById, new Object[]{id, email}, new BeanPropertyRowMapper(Tarea.class));
        } catch (DataAccessException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            t = null;
        }
        return t;
    }
}
