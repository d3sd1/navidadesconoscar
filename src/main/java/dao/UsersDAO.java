package dao;

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
            if (jtm.update(queryActivar, user.getCodigoActivacion()) > 0)
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
            if (jtm.update(queryUpdateCodigo, u.getCodigoActivacion(), u.getEmail()) > 0)
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
            if (jtm.update(queryUpdatePassByCodigo, u.getClave(), u.getCodigoActivacion()) > 0)
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
