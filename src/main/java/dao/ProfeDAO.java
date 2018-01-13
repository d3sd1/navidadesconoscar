package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Asignatura;
import model.Curso;
import model.Nota;
import model.Tarea;
import model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class ProfeDAO {

    private final String queryGetAllNotasCurso = "SELECT c.nombre, c.id, a.id, a.nombre, AVG(aa.nota) "
            + "FROM users u "
            + "JOIN alumnos_asignaturas aa ON u.id = aa.id_alumno "
            + "JOIN profesores_asignaturas pa ON pa.id_asignatura = aa.id_asignatura "
            + "JOIN asignaturas a ON a.id = aa.id_asignatura "
            + "JOIN cursos c ON c.id = a.id_curso  "
            + "WHERE pa.id_profesor=? "
            + "GROUP BY aa.id_asignatura";
    private final String queryGetAllNotasCursoAlumnos = "SELECT c.nombre, c.id, a.id, a.nombre, u.id, u.nombre, aa.nota "
            + "FROM users u "
            + "JOIN alumnos_asignaturas aa ON u.id = aa.id_alumno "
            + "JOIN profesores_asignaturas pa ON pa.id_asignatura = aa.id_asignatura "
            + "JOIN asignaturas a ON a.id = aa.id_asignatura "
            + "JOIN cursos c ON c.id = a.id_curso  "
            + "WHERE pa.id_profesor=? ";
    private final String queryGetAllNotas = "SELECT u.id, u.nombre, a.id, a.nombre, c.id, c.nombre, aa.nota "
            + "FROM users u "
            + "JOIN alumnos_asignaturas aa ON u.id = aa.id_alumno "
            + "JOIN asignaturas a ON a.id = aa.id_asignatura "
            + "JOIN profesores_asignaturas pa ON pa.id_asignatura = aa.id_asignatura "
            + "JOIN cursos c ON c.id = a.id_curso "
            + "WHERE pa.id_profesor = ? ";
    private final String queryGetId = "SELECT id FROM users WHERE email = ?";
    private final String queryModNota = "UPDATE alumnos_asignaturas SET nota = ? WHERE id_alumno = ? AND id_asignatura = ?";
    private final String queryAddTarea = "INSERT INTO tareas (id_asignatura, nombre_tarea, fecha_entrega) VALUES (?,?,?)";
    private final String queryAddTareaAlumno = "INSERT INTO tareas_alumnos (id_tarea, id_alumno, completado) VALUES (?,?,0)";
    private final String queryModTarea = "UPDATE tareas SET nombre_tarea = ?, fecha_entrega = ? WHERE id_tarea = ?";
    private final String queryGetIdAlumnos = "SELECT id_alumno FROM alumnos_asignaturas WHERE id_asignatura = ?";

    public List<Nota> getAllNotas(int id) {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<Nota> notas = jtm.query(queryGetAllNotas, (ResultSet rs, int rowNum)
                -> {
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
        }, id);

        return notas;
    }

    public int getId(String email) {
        int id = 0;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            id = jtm.queryForObject(queryGetId, int.class, email);

        } catch (DataAccessException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public boolean modNota(Nota n) {
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

        List<Nota> notas = jtm.query(queryGetAllNotasCurso, (ResultSet rs, int rowNum)
                -> {
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
        }, id);
        return notas;
    }

    public List<Nota> getAllNotasCursosAlumnos(int id) {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());

        List<Nota> notas = jtm.query(queryGetAllNotasCursoAlumnos, (ResultSet rs, int rowNum)
                -> {
            Nota nota = new Nota();

            nota.setNota(rs.getDouble(7));

            Asignatura asignatura = new Asignatura();

            asignatura.setId(rs.getInt(3));
            asignatura.setNombre(rs.getString(4));
            nota.setAsignatura(asignatura);

            User alumno = new User();

            alumno.setId(rs.getInt(5));
            alumno.setNombre(rs.getString(6));
            nota.setAlumno(alumno);

            Curso curso = new Curso();
            curso.setNombre(rs.getString(1));
            curso.setId(rs.getInt(2));
            nota.setCurso(curso);
            return nota;
        }, id);
        return notas;
    }

    public Tarea addTarea(Tarea t, List<Integer> idAlumnos) {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(queryAddTarea, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, t.getId_asignatura());
            stmt.setString(2, t.getNombre_tarea());
            stmt.setDate(3, new java.sql.Date(t.getFecha_entrega().getTime()));

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                t.setId_tarea(rs.getInt(1));
            }

            for (int i = 0; i < idAlumnos.size(); i++) {
                stmt = con.prepareStatement(queryAddTareaAlumno);

                stmt.setInt(1, t.getId_tarea());
                stmt.setInt(2, idAlumnos.get(i));
                
                stmt.executeUpdate();
            }

            con.commit();
            stmt.close();
        } catch (Exception ex) {
            Logger.getLogger(ProfeDAO.class.getName()).log(Level.SEVERE, null, ex);
            t = null;
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(ProfeDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }

        return t;
    }

    public List<Integer> getIdAlumnos(int idAsignatura) {
        List<Integer> idAlumnos = null;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            idAlumnos =(List<Integer>) jtm.queryForList(queryGetIdAlumnos, Integer.class, idAsignatura);

        } catch (DataAccessException ex) {
            Logger.getLogger(ProfeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idAlumnos;
    }

    public Tarea modTarea(Tarea t) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            if (!(jtm.update(queryModTarea, t.getNombre_tarea(), t.getFecha_entrega(), t.getId_tarea()) > 0)) {
                t = null;
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            t = null;
        }
        return t;
    }
}
