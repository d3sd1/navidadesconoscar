package dao;

import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Asignatura;
import model.Curso;
import model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.Queries;

public class AdminDAO
{
    private final DBManager manager = new DBManager();

    public List<Asignatura> getAllAsignaturas()
    {
        return (List<Asignatura>) this.manager.queryAll(Queries.queryGetAllAsignaturas,Asignatura.class);
    }

    public List<User> getAllUsers()
    {
        return (List<User>) this.manager.queryAll(Queries.queryGetAllUsers,User.class);
    }

    public List<Asignatura> getAsignaturasAlumno()
    {
        return (List<Asignatura>) this.manager.queryAll(Queries.queryGetAllAlumAsig,Asignatura.class);
    }

    public List<Asignatura> getAsignaturasProfesor()
    {
        return (List<Asignatura>) this.manager.queryAll(Queries.queryGetAllProfeAsig,Asignatura.class);
    }

    public Asignatura addAsig(Asignatura a)
    {
        Connection con = null;

        try
        {
            con = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(Queries.queryAddAsig, Statement.RETURN_GENERATED_KEYS);
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
            if (!(jtm.update(Queries.queryModAsig, a.getNombre(), a.getId_curso(), a.getId()) > 0))
            {
                a = null;
            }
        }
        catch (DataAccessException ex)
        {
            a = null;
        }
        return a;
    }

    public boolean delAsig(Asignatura a)
    {
        Connection con = null;
        boolean borrado = false;
        try
        {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(Queries.queryDelNota);
            stmt.setInt(1, a.getId());
            stmt.executeUpdate();

            stmt = con.prepareStatement(Queries.queryDelAsigProfe);
            stmt.setInt(1, a.getId());
            stmt.executeUpdate();

            stmt = con.prepareStatement(Queries.queryDelTareaAlumno);
            stmt.setInt(1, a.getId());
            stmt.executeUpdate();

            stmt = con.prepareStatement(Queries.queryDelTarea);
            stmt.setInt(1, a.getId());
            stmt.executeUpdate();

            stmt = con.prepareStatement(Queries.queryDelAsig);
            stmt.setInt(1, a.getId());
            stmt.executeUpdate();

            con.commit();
            borrado = true;

            stmt.close();
        }
        catch (Exception ex)
        {
            try
            {
                if (con != null)
                {
                    con.rollback();
                }
            }
            catch (SQLException ex1)
            {
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
        List<User> alumnos = jtm.query(Queries.queryGetAllAlumnos, new BeanPropertyRowMapper(User.class));
        return alumnos;
    }

    public int getTotalAlumnos()
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        int total = jtm.queryForObject(Queries.queryGetTotalAlumnos, Integer.class);
        return total;
    }

    public ArrayList<ArrayList<String>> getAlumnos(int start, int length)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<User> alumnos = jtm.query(Queries.queryGetAlumnosPaginados, new BeanPropertyRowMapper(User.class), start, length);
        ArrayList<ArrayList<String>> devolver = new ArrayList<>();
        for (User alumno : alumnos)
        {
            ArrayList<String> actualAlumno = new ArrayList<>();
            actualAlumno.add(alumno.getId() + "");
            actualAlumno.add(alumno.getNombre());
            actualAlumno.add(alumno.getEmail());
            actualAlumno.add(Boolean.toString(alumno.getActivo()));
            devolver.add(actualAlumno);
        }
        return devolver;
    }

    public List<User> getAllProfes()
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<User> profesores = jtm.query(Queries.queryGetAllProfes, new BeanPropertyRowMapper(User.class));

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

            PreparedStatement stmt = con.prepareStatement(Queries.queryAsignarProfeAsig);

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
            try
            {
                if (con != null)
                {
                    con.rollback();
                }
            }
            catch (SQLException ex1)
            {
                
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

            PreparedStatement stmt = con.prepareStatement(Queries.queryAsignarAlumAsig);
            for (int i = 0; i < id_asignaturas.length; i++)
            {
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
            try
            {
                if (con != null)
                {
                    con.rollback();
                }
            }
            catch (SQLException ex1)
            {
                
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

            PreparedStatement stmt = con.prepareStatement(Queries.queryAsignarProfeAsig);
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
            try
            {
                if (con != null)
                {
                    con.rollback();
                }
            }
            catch (SQLException ex1)
            {
                
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

            PreparedStatement stmt = con.prepareStatement(Queries.queryEliminarAlumAsig);

            stmt.setInt(1, id_alumno);
            stmt.executeUpdate();

            eliminado = true;
            con.commit();
            stmt.close();
        }
        catch (Exception ex)
        {
            try
            {
                if (con != null)
                {
                    con.rollback();
                }
            }
            catch (SQLException ex1)
            {
                
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

            PreparedStatement stmt = con.prepareStatement(Queries.queryEliminarProfeAsig);

            stmt.setInt(1, id_profe);
            stmt.executeUpdate();

            eliminado = true;
            con.commit();
            stmt.close();
        }
        catch (Exception ex)
        {
            try
            {
                if (con != null)
                {
                    con.rollback();
                }
            }
            catch (SQLException ex1)
            {
                
            }
        }
        finally
        {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return eliminado;
    }

    public boolean comprobarEmail(String email)
    {
        boolean existe = false;
        try
        {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            String emailDB = jtm.queryForObject(Queries.queryComprobarEmail, String.class, email);

            if (emailDB != null)
            {
                existe = true;
            }
        }
        catch (DataAccessException ex)
        {
        }
        return existe;
    }

    public User addUser(User u)
    {
        Connection con = null;

        try
        {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(Queries.queryRegistrarUser, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, u.getEmail());
            stmt.setString(2, u.getClave());
            stmt.setString(3, u.getCodigoActivacion());
            stmt.setString(4, u.getNombre());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
            {
                u.setId(rs.getInt(1));
            }

            stmt = con.prepareStatement(Queries.queryRegistrarUserPermisos);
            stmt.setInt(1, u.getId());
            stmt.setInt(2, u.getId_permiso());
            stmt.executeUpdate();

            con.commit();

            stmt.close();
        }
        catch (Exception ex)
        {
            u = null;
            try
            {
                if (con != null)
                {
                    con.rollback();
                }
            }
            catch (SQLException ex1)
            {
                
            }
        }
        finally
        {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return u;
    }

    public boolean modUser(User u, int tipo)
    {
        Connection con = null;
        boolean userModificado = false;
        try
        {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(Queries.queryModificarUser);
            stmt.setString(1, u.getEmail());
            stmt.setString(2, u.getNombre());
            stmt.setInt(3, u.getId());
            stmt.executeUpdate();

            stmt = con.prepareStatement(Queries.queryModificarUserPermisos);
            stmt.setInt(1, u.getId_permiso());
            stmt.setInt(2, u.getId());
            stmt.executeUpdate();

            stmt.executeUpdate();

            con.commit();
            userModificado = true;
            stmt.close();
        }
        catch (Exception ex)
        {
            userModificado = false;
            try
            {
                if (con != null)
                {
                    con.rollback();
                }
            }
            catch (SQLException ex1)
            {
                
            }
        }
        finally
        {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return userModificado;
    }

    public int getPermiso(int id)
    {
        int permiso = 0;
        try
        {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            permiso = jtm.queryForObject(Queries.queryGetPermisoAdmin, int.class, id);

        }
        catch (DataAccessException ex)
        {
            
        }
        return permiso;
    }

    public boolean delUser(User u)
    {
        Connection con = null;
        boolean borrado = false;
        try
        {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(Queries.queryEliminarAlumAsig);
            stmt.setInt(1, u.getId());
            stmt.executeUpdate();

            stmt = con.prepareStatement(Queries.queryEliminarProfeAsig);
            stmt.setInt(1, u.getId());
            stmt.executeUpdate();

            stmt = con.prepareStatement(Queries.queryDelUserPermiso);
            stmt.setInt(1, u.getId());
            stmt.executeUpdate();

            stmt = con.prepareStatement(Queries.queryDelUser);
            stmt.setInt(1, u.getId());
            stmt.executeUpdate();

            con.commit();
            borrado = true;

            stmt.close();
        }
        catch (Exception ex)
        {
            try
            {
                if (con != null)
                {
                    con.rollback();
                }
            }
            catch (SQLException ex1)
            {
                
            }
        }
        finally
        {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return borrado;
    }
    public List<Curso> getAllCursos()
    {
        return (List<Curso>) this.manager.queryAll(Queries.queryGetAllCursos,Curso.class);
    }
}
