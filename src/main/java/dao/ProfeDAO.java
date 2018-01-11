package dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Nota;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class ProfeDAO {
    
    private final String queryGetAllNotas = "SELECT u.id, u.nombre, a.id AS id_asignatura, a.nombre AS nombre_asignatura, aa.nota "
            + "FROM users u JOIN alumnos_asignaturas aa ON u.id = aa.id_alumno "
            + "JOIN asignaturas a ON a.id = aa.id_asignatura "
            + "JOIN profesores_asignaturas pa ON pa.id_asignatura = aa.id_asignatura "
            + "WHERE pa.id_profesor = ? "
            + "ORDER BY a.id";
    private final String queryGetAllNotasCursos = "SELECT c.id AS id_curso, c.nombre AS nombre_curso, u.id, u.nombre, a.id AS id_asignatura, a.nombre AS nombre_asignatura, aa.nota "
            + "FROM users u JOIN alumnos_asignaturas aa ON u.id = aa.id_alumno "
            + "JOIN asignaturas a ON a.id = aa.id_asignatura "
            + "JOIN profesores_asignaturas pa ON pa.id_asignatura = aa.id_asignatura "
            + "JOIN cursos c ON c.id = a.id_curso "
            + "WHERE pa.id_profesor = ? "
            + "ORDER BY c.id";
    private final String queryGetId = "SELECT id FROM users WHERE email = ?";
    private final String queryModNota = "UPDATE alumnos_asignaturas SET nota = ? WHERE id_alumno = ? AND id_asignatura = ?";
    
    public List<Nota> getAllNotas(int id) {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<Nota> notas = jtm.query(queryGetAllNotas, new BeanPropertyRowMapper(Nota.class), id);

        return notas;
    }
    
    public int getId(String email){
        int id = 0;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            id = jtm.queryForObject(queryGetId, int.class, email);

        } catch (DataAccessException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    public boolean modNota(Nota n){
        boolean modificado = false;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            if (jtm.update(queryModNota, n.getNota(), n.getId_alumno(), n.getId_asignatura()) > 0) {
                modificado = true;
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ProfeDAO.class.getName()).log(Level.SEVERE, null, ex);
            modificado = false;
        }
        return modificado;
    }
    
    public List<Nota> getAllNotasCursos(int id) {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<Nota> notas = jtm.query(queryGetAllNotasCursos, new BeanPropertyRowMapper(Nota.class), id);

        return notas;
    }
}
