package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import model.Asignatura;
import model.Curso;
import model.Nota;
import model.Tarea;
import model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
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
    
    public String getNombreAsignatura(int id) {
        String nombre = "";
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            nombre = jtm.queryForObject(Queries.queryGetAsignaturaNombre, String.class, id);

        } catch (DataAccessException ex) {
            
        }
        return nombre;
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
    public Tarea addTarea(Tarea t, List<Integer> idAlumnos, String email) {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(Queries.queryAddTarea, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, t.getAsignatura().getId());
            stmt.setString(2, t.getNombre_tarea());
            stmt.setDate(3, new java.sql.Date(t.getFecha_entrega().getTime()));
            stmt.setString(4, email);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                t.setId_tarea(rs.getInt(1));
            }

            for (int i = 0; i < idAlumnos.size(); i++) {
                stmt = con.prepareStatement(Queries.queryAddTareaAlumno);

                stmt.setInt(1, t.getId_tarea());
                stmt.setInt(2, idAlumnos.get(i));
                
                stmt.executeUpdate();
            }

            con.commit();
            stmt.close();
        } catch (Exception ex) {
            t = null;
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex1) {
                
            }
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }

        return t;
    }

    public List<Integer> getIdAlumnos(Asignatura asignatura) {
        return (List<Integer>) this.manager.queryAll(Queries.queryGetIdAlumnos,Integer.class, asignatura.getId());
    }

    public Tarea modTarea(Tarea t) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            if (!(jtm.update(Queries.queryModTarea, t.getNombre_tarea(), t.getFecha_entrega(), t.getId_tarea()) > 0)) {
                t = null;
            }
        } catch (DataAccessException ex) {
            t = null;
        }
        return t;
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
        boolean delTareaSuccess = this.manager.delete(Queries.queryDelTareaProfeAlumno,false,tarea.getId_tarea());
        boolean delTareaProfeSuccess = this.manager.delete(Queries.queryDelTareaProfe,false,tarea.getId_tarea());
        return ;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            
            
            con.commit();
            stmt.close();
            eliminado = true;
        } catch (Exception ex) {
            eliminado = false;
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex1) {
                
            }
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return eliminado;
    }
}
