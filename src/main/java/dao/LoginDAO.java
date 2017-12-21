package dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class LoginDAO {

    private final String queryGetUserByMail = "SELECT * FROM users WHERE email = ?";
    private final String queryTabla = "SELECT permiso FROM permisos p JOIN users_permisos up ON p.id=up.id_permiso JOIN users u ON up.id_user=u.id WHERE email=?";
    private final String queryGetNombre = "SELECT nombre FROM ? WHERE id_user = ?";
    
    public User getUserByMail(String mail) {
        User u;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            u = (User) jtm.queryForObject(queryGetUserByMail, new Object[]{mail}, new BeanPropertyRowMapper(User.class));
        } catch (DataAccessException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            u = null;
        }
        return u;
    }

    public String getNombre(String mail, int id) {
        String nombre;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            String tabla = jtm.queryForObject(queryTabla, String.class, mail);
            nombre = jtm.queryForObject(queryGetNombre, String.class, tabla, id);
        } catch (DataAccessException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            nombre = null;
        }
        return nombre;
    }
}
