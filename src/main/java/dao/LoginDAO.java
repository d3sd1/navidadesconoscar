package dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class LoginDAO
{

    private final String queryGetUserByMail = "SELECT * FROM users WHERE email = ?";

    public User getUserByMail(String mail)
    {
        User u;
        try
        {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            u = (User) jtm.queryForObject(queryGetUserByMail, new Object[]
            {
                mail
            }, new BeanPropertyRowMapper(User.class));
        }
        catch (Exception ex)
        {
            Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            u = null;
        }
        return u;
    }
}
