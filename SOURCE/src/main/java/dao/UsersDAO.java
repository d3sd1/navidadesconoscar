package dao;

import java.util.logging.Logger;
import model.User;
import utils.Queries;

public class UsersDAO
{
    private final DBManager manager = new DBManager();
    public User getUserByEmail(User usr)
    {
        return (User) this.manager.queryForObject(Queries.queryGetUserByMail,User.class,usr.getEmail());
    }

    public User getUserByCodigoActivacion(User user)
    {
        return (User) this.manager.queryForObject(Queries.queryUserByCodigoActivacion,User.class,user.getCodigoActivacion());
    }

    public boolean activarUser(User user)
    {
        return this.manager.update(Queries.queryActivar, user.getCodigoActivacion());
    }

    public boolean updateCodigo(User user)
    {
        return this.manager.update(Queries.queryUpdateCodigo, user.getCodigoActivacion(),user.getEmail());
    }

    public boolean updatePassByCodigo(User user)
    {
        return this.manager.update(Queries.queryUpdatePassByCodigo, user.getClave(), user.getCodigoActivacion());
    }

    public int getPermiso(User user)
    {
        return this.manager.queryForInt(Queries.queryGetPermiso,user.getEmail());
    }

    public boolean updatePassByEmail(User user)
    {
        return this.manager.update(Queries.queryUpdatePassByEmail, user.getClave(), user.getEmail());
    }
    private static final Logger LOG = Logger.getLogger(UsersDAO.class.getName());
}
