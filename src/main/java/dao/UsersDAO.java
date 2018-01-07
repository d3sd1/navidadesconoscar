package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UsersDAO
{

    private final String queryGetUserByMail = "SELECT * FROM users WHERE email = ?";
    private final String queryGetPermiso = "SELECT up.id_permiso FROM users_permisos up JOIN users u ON up.id_user = u.id WHERE u.email = ?";
    private final String queryRegistrarUser = "INSERT INTO users (email,clave,activo,codigo_activacion) VALUES (?,?,0,?)";
    private final String queryRegistrarUserPermisos = "INSERT INTO users_permisos (id_user,id_permiso) VALUES (?,?)";
    private final String queryRegistrarAdmin = "INSERT INTO users_administradores (id_user,nombre) VALUES (?,?)";
    private final String queryRegistrarProfe = "INSERT INTO users_profesores (id_user,nombre) VALUES (?,?)";
    private final String queryRegistrarAlumno = "INSERT INTO users_alumnos (id_user,nombre) VALUES (?,?)";
    private final String queryUserByCodigoActivacion = "SELECT * FROM users WHERE codigo_activacion = ?";
    private final String queryActivar = "UPDATE users SET activo = TRUE, codigo_activacion = NULL WHERE codigo_activacion = ?";
    private final String queryUpdateCodigo = "UPDATE users SET codigo_activacion = ? WHERE email = ?";
    private final String queryUpdatePassByCodigo = "UPDATE users SET clave = ?, codigo_activacion = NULL WHERE codigo_activacion = ?";
    private final String queryUpdatePassByEmail = "UPDATE users SET clave = ? WHERE email = ?";

    public User getUserByEmail(User usr)
    {
        User foundUsr;
        try
        {
            JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            foundUsr = (User) jdbcTemplateObject.queryForObject(queryGetUserByMail, new Object[]
            {
                usr.getEmail()
            }, new BeanPropertyRowMapper(User.class));
        }
        catch (DataAccessException ex)
        {
            foundUsr = null;
        }
        return foundUsr;
    }

    public boolean registro(User u, String nombre, int tipo)
    {
        Connection con = null;
        boolean registroCorrecto;
        try
        {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(queryRegistrarUser, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, u.getEmail());
            stmt.setString(2, u.getClave());
            stmt.setString(3, u.getCodigo_activacion());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            int idUser = 0;
            if (rs.next())
            {
                idUser = rs.getInt(1);
            }

            stmt = con.prepareStatement(queryRegistrarUserPermisos);
            stmt.setInt(1, idUser);
            stmt.setInt(2, tipo);
            stmt.executeUpdate();

            switch (tipo)
            {
                case 1:
                    stmt = con.prepareStatement(queryRegistrarAdmin);
                    break;
                case 2:
                    stmt = con.prepareStatement(queryRegistrarProfe);
                    break;
                case 3:
                    stmt = con.prepareStatement(queryRegistrarAlumno);
                    break;
            }
            stmt.setInt(1, idUser);
            stmt.setString(2, nombre);
            stmt.executeUpdate();

            registroCorrecto = true;
            con.commit();

            stmt.close();
        }
        catch (Exception ex)
        {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            registroCorrecto = false;

            try
            {
                if (con != null)
                {
                    con.rollback();
                }
            }
            catch (SQLException ex1)
            {
                Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        finally
        {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return registroCorrecto;
    }

    public User getUserByCodigoActivacion(String codigoActivacion)
    {
        User u;
        try
        {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            u = (User) jtm.queryForObject(queryUserByCodigoActivacion, new Object[]
            {
                codigoActivacion
            }, new BeanPropertyRowMapper(User.class));
        }
        catch (DataAccessException ex)
        {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            u = null;
        }
        return u;
    }

    public int activarUser(User user)
    {
        int valido = -1;
        try
        {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            if (jtm.update(queryActivar, user.getCodigo_activacion()) > 0)
            {
                valido = 1;
            }
        }
        catch (DataAccessException ex)
        {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            valido = -1;
        }

        return valido;
    }

    public boolean updateCodigo(User u)
    {
        boolean valido = false;

        try
        {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            if (jtm.update(queryUpdateCodigo, u.getCodigo_activacion(), u.getEmail()) > 0)
            {
                valido = true;
            }
        }
        catch (DataAccessException ex)
        {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            valido = false;
        }

        return valido;
    }

    public boolean updatePassByCodigo(User u)
    {
        boolean valido = false;

        try
        {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            if (jtm.update(queryUpdatePassByCodigo, u.getClave(), u.getCodigo_activacion()) > 0)
            {
                valido = true;
            }
        }
        catch (DataAccessException ex)
        {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            valido = false;
        }

        return valido;
    }

    public int getPermiso(String email)
    {
        int permiso = 0;
        try
        {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            permiso = jtm.queryForObject(queryGetPermiso, int.class, email);

        }
        catch (DataAccessException ex)
        {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return permiso;
    }

    public boolean updatePassByEmail(User u)
    {
        boolean valido = false;

        try
        {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            if (jtm.update(queryUpdatePassByEmail, u.getClave(), u.getEmail()) > 0)
            {
                valido = true;
            }
        }
        catch (DataAccessException ex)
        {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            valido = false;
        }

        return valido;
    }
}
