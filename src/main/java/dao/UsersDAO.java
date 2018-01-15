package dao;

import model.User;
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
        User foundUsr = new User();
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        foundUsr = (User) jdbcTemplateObject.queryForObject(queryGetUserByMail, new Object[]
        {
            usr.getEmail()
        }, new BeanPropertyRowMapper(User.class));
        return foundUsr;
    }

    public User getUserByCodigoActivacion(User usr)
    {
        User u = new User();
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        u = (User) jtm.queryForObject(queryUserByCodigoActivacion, new Object[]
        {
            usr.getCodigoActivacion()
        }, new BeanPropertyRowMapper(User.class));
        return u;
    }

    public boolean activarUser(User user)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        return jtm.update(queryActivar, user.getCodigoActivacion()) > 0;
    }

    public boolean updateCodigo(User u)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        return jtm.update(queryUpdateCodigo, u.getCodigoActivacion(), u.getEmail()) > 0;
    }

    public boolean updatePassByCodigo(User u)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        return jtm.update(queryUpdatePassByCodigo, u.getClave(), u.getCodigoActivacion()) > 0;
    }

    public int getPermiso(User user)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        return jtm.queryForObject(queryGetPermiso, int.class, user.getEmail());
    }

    public boolean updatePassByEmail(User u)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        return jtm.update(queryUpdatePassByEmail, u.getClave(), u.getEmail()) > 0;
    }
}
