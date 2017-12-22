package dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class LoginDAO {

    private final String queryGetUserByMail = "SELECT * FROM users WHERE email = ?";
    private final String queryGetPermiso = "SELECT id_permiso FROM users_permisos WHERE id_user = ?";
    private final String queryGetNombreAdmin = "SELECT nombre FROM users_administradores WHERE id_user = ?";
    private final String queryGetNombreProfe = "SELECT nombre FROM users_profesores WHERE id_user = ?";
    private final String queryGetNombreAlumno = "SELECT nombre FROM users_alumnos WHERE id_user = ?";

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

    public String getNombre(int id) {
        String nombre = null;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            int permiso = jtm.queryForObject(queryGetPermiso, int.class, id);
            
            switch (permiso) {
                case 1:
                    nombre = jtm.queryForObject(queryGetNombreAdmin, String.class, id);
                    break;
                case 2:
                    nombre = jtm.queryForObject(queryGetNombreProfe, String.class, id);
                    break;
                case 3:
                    nombre = jtm.queryForObject(queryGetNombreAlumno, String.class, id);
                    break;
            }
            
        } catch (DataAccessException ex) {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            nombre = null;
        }
        return nombre;
    }
}
