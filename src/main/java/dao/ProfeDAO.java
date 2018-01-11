package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Asignatura;
import model.Curso;
import model.Nota;
import model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ProfeDAO {
    
    private final String queryGetAllNotas = "SELECT u.id, u.nombre, a.id AS id_asignatura, a.nombre AS nombre_asignatura, aa.nota "
            + "FROM users u JOIN alumnos_asignaturas aa ON u.id = aa.id_alumno "
            + "JOIN asignaturas a ON a.id = aa.id_asignatura "
            + "JOIN profesores_asignaturas pa ON pa.id_asignatura = aa.id_asignatura "
            + "WHERE pa.id_profesor = ? "
            + "ORDER BY a.id";
    private final String queryGetAllNotasCursos = "SELECT aa.nota, a.id, u.id, a.nombre, u.nombre, c.nombre, c.id "
            + "FROM users u "
            + "JOIN alumnos_asignaturas aa ON u.id = aa.id_alumno "
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
            if (jtm.update(queryModNota, n.getNota(), n.getAlumno().getId(), n.getAsignatura().getId()) > 0) {
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
        
        List<Nota> notas = jtm.query(queryGetAllNotasCursos, new RowMapper<Nota>() {
            @Override
            public Nota mapRow(ResultSet rs, int rowNum) throws SQLException {
                Nota nota = new Nota();
                        
                        
                
                nota.setNota(rs.getInt(1));

                User alumno = new User();
                alumno.setId(rs.getInt(3));
                alumno.setNombre(rs.getString(5));
                nota.setAlumno(alumno);
                
                Asignatura asignatura = new Asignatura();

                asignatura.setId(rs.getInt(2));
                asignatura.setNombre(rs.getString(4));
                nota.setAsignatura(asignatura);
                
                Curso curso = new Curso();
                curso.setNombre(rs.getString(6));
                curso.setId(rs.getInt(7));
                nota.setCurso(curso);
                return nota;
            }
        },id);
        return notas;
    }
}
