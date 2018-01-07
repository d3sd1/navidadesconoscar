/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Asignatura;
import model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Miguel
 */
public class AdminDAO
{

    private final String queryGetAllAsignaturas = "SELECT * FROM asignaturas";
    private final String queryAddAsig = "INSERT INTO asignaturas (nombre,id_curso) VALUES (?,?)";
    private final String queryModAsig = "UPDATE asignaturas SET nombre = ?, id_curso = ? WHERE id = ?";
    private final String queryDelAsig = "DELETE FROM asignaturas WHERE id = ?";
    private final String queryDelNota = "DELETE FROM alumnos_asignaturas WHERE id_asignatura = ?";
    private final String queryDelAsigProfe = "DELETE FROM profesores_asignaturas WHERE id_asignatura = ?";
    private final String queryGetAllAlumnos = "SELECT * FROM users u JOIN users_alumnos ua ON u.id=ua.id_user JOIN users_permisos up ON u.id = up.id_user WHERE id_permiso = 3";
    private final String queryGetAllProfes = "SELECT * FROM users u JOIN users_profesores ua ON u.id=ua.id_user JOIN users_permisos up ON u.id = up.id_user WHERE id_permiso = 2";
    private final String queryAsignarProfeAsig = "INSERT INTO profesores_asignaturas (id_profesor, id_asignatura) VALUES (?,?)";
    private final String queryEliminarProfeAsig = "DELETE FROM profesores_asignaturas WHERE id_profesor = ?";
    private final String queryAsignarAlumAsig = "INSERT INTO alumnos_asignaturas (id_alumno, id_asignatura) VALUES (?,?)";
    private final String queryEliminarAlumAsig = "DELETE FROM alumnos_asignaturas WHERE id_alumno = ?";
    private final String queryGetAllAlumAsig = "SELECT * FROM alumnos_asignaturas";
    private final String queryGetAllProfeAsig = "SELECT * FROM profesores_asignaturas";

    public List<Asignatura> getAllAsignaturas()
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<Asignatura> asignaturas = jtm.query(queryGetAllAsignaturas, new BeanPropertyRowMapper(Asignatura.class));

        return asignaturas;
    }

    public List<Asignatura> getAsignaturasAlumno()
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List asignaturas = jtm.queryForList(queryGetAllAlumAsig);

        return asignaturas;
    }
    public List<Asignatura> getAsignaturasProfesor()
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List asignaturas = jtm.queryForList(queryGetAllProfeAsig);

        return asignaturas;
    }

    public Asignatura addAsig(Asignatura a)
    {
        Connection con = null;

        try
        {
            con = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(queryAddAsig, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, a.getNombre());
            stmt.setInt(2, a.getId_curso());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
            {
                a.setId(rs.getInt(1));
            }

            stmt.close();
        }
        catch (Exception ex)
        {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            a = null;
        }
        finally
        {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return a;
    }

    public Asignatura modAsig(Asignatura a)
    {
        try
        {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            if (!(jtm.update(queryModAsig, a.getNombre(), a.getId_curso(), a.getId()) > 0))
            {
                a = null;
            }
        }
        catch (DataAccessException ex)
        {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            a = null;
        }
        return a;
    }

    public int delAsig(Asignatura a)
    {
        int borrado = -1;
        try
        {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            if (jtm.update(queryDelAsig, a.getId()) > 0)
            {
                borrado = 1;
            }
        }
        catch (DataIntegrityViolationException ex)
        {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            borrado = 0;
        }
        catch (DataAccessException ex)
        {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            borrado = -1;
        }
        return borrado;
    }

    public boolean delAsig2(Asignatura a)
    {
        Connection con = null;
        boolean borrado = false;
        try
        {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(queryDelNota);
            stmt.setInt(1, a.getId());
            stmt.executeUpdate();

            stmt = con.prepareStatement(queryDelAsigProfe);
            stmt.setInt(1, a.getId());
            stmt.executeUpdate();

            stmt = con.prepareStatement(queryDelAsig);
            stmt.setInt(1, a.getId());
            stmt.executeUpdate();

            con.commit();
            borrado = true;

            stmt.close();
        }
        catch (Exception ex)
        {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            try
            {
                if (con != null)
                {
                    con.rollback();
                }
            }
            catch (SQLException ex1)
            {
                Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        finally
        {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return borrado;
    }

    public List<User> getAllAlumnos()
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<User> alumnos = jtm.query(queryGetAllAlumnos, new BeanPropertyRowMapper(User.class));
        return alumnos;
    }

    public List<User> getAllProfes()
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<User> profesores = jtm.query(queryGetAllProfes, new BeanPropertyRowMapper(User.class));

        return profesores;
    }

    public boolean asignarProfeAsig(int id_profe, String[] id_asignaturas)
    {
        boolean asignado = false;
        Connection con = null;
        try
        {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(queryAsignarProfeAsig);

            for (int i = 0; i < id_asignaturas.length; i++)
            {
                stmt.setInt(1, id_profe);
                stmt.setInt(2, parseInt(id_asignaturas[i]));
                stmt.executeUpdate();
            }

            asignado = true;
            con.commit();
            stmt.close();

        }
        catch (Exception ex)
        {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            try
            {
                if (con != null)
                {
                    con.rollback();
                }
            }
            catch (SQLException ex1)
            {
                Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        finally
        {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return asignado;
    }

    public boolean asignarInsertandoAlumAsig(int id_alumno, String[] id_asignaturas)
    {
        boolean asignado = false;
        Connection con = null;
        try
        {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(queryAsignarAlumAsig);
            for (int i = 0; i < id_asignaturas.length; i++) {
                stmt.setInt(1, id_alumno);
                stmt.setInt(2, parseInt(id_asignaturas[i]));
                stmt.executeUpdate();
            }

            asignado = true;
            con.commit();
            stmt.close();

        }
        catch (Exception ex)
        {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            try
            {
                if (con != null)
                {
                    con.rollback();
                }
            }
            catch (SQLException ex1)
            {
                Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        finally
        {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return asignado;
    }
    public boolean asignarInsertandoProfeAsig(int id_profe, String[] id_asignaturas)
    {
        boolean asignado = false;
        Connection con = null;
        try
        {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(queryAsignarProfeAsig);
            for (int i = 0; i < id_asignaturas.length; i++) {
                stmt.setInt(1, id_profe);
                stmt.setInt(2, parseInt(id_asignaturas[i]));
                stmt.executeUpdate();
            }

            asignado = true;
            con.commit();
            stmt.close();

        }
        catch (Exception ex)
        {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            try
            {
                if (con != null)
                {
                    con.rollback();
                }
            }
            catch (SQLException ex1)
            {
                Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        finally
        {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return asignado;
    }

    public boolean eliminarAlumAsig(int id_alumno)
    {
        boolean eliminado = false;
        Connection con = null;
        try
        {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(queryEliminarAlumAsig);

            stmt.setInt(1, id_alumno);
            stmt.executeUpdate();

            eliminado = true;
            con.commit();
            stmt.close();
        }
        catch (Exception ex)
        {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            try
            {
                if (con != null)
                {
                    con.rollback();
                }
            }
            catch (SQLException ex1)
            {
                Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        finally
        {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return eliminado;
    }
    public boolean eliminarProfeAsig(int id_profe)
    {
        boolean eliminado = false;
        Connection con = null;
        try
        {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(queryEliminarProfeAsig);

            stmt.setInt(1, id_profe);
            stmt.executeUpdate();

            eliminado = true;
            con.commit();
            stmt.close();
        }
        catch (Exception ex)
        {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            try
            {
                if (con != null)
                {
                    con.rollback();
                }
            }
            catch (SQLException ex1)
            {
                Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        finally
        {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return eliminado;
    }
}
