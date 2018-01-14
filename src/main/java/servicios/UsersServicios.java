package servicios;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import config.Configuration;
import dao.UsersDAO;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import model.User;
import utils.Constantes;
import utils.Language;
import utils.PasswordHash;
import utils.Utils;

public class UsersServicios
{

    private final AjaxMaker ajax = new AjaxMaker();

    public AjaxResponse login(String mail, String pass)
    {
        AjaxResponse returnme = ajax.errorResponse(1);
        UsersDAO dao = new UsersDAO();
        User user = new User();
        try
        {
            user.setEmail(mail);
            user.setClave(pass);

            User foundUser = dao.getUserByEmail(user);
            if (foundUser != null)
            {
                boolean validPass = PasswordHash.getInstance().validatePassword(user.getClave(), foundUser.getClave());
                if (validPass)
                {
                    if (foundUser.getActivo())
                    {
                        returnme = ajax.successResponse();
                    }
                    else
                    {
                        returnme = ajax.errorResponse(2);
                    }
                }
            }

        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException ex)
        {
            returnme = ajax.errorResponse(0);
        }
        return returnme;
    }

    public int activar(String codigo)
    {
        UsersDAO dao = new UsersDAO();
        User u = dao.getUserByCodigoActivacion(codigo);

        int activar;

        if (u == null)
        {
            activar = -1;
        }
        else if (u.getActivo() == false)
        {
            activar = dao.activarUser(u);
        }
        else
        {
            activar = 2;
        }
        return activar;
    }

    public AjaxResponse mandarMail(String email)
    {
        AjaxResponse returnme;
        UsersDAO dao = new UsersDAO();
        User u = new User();
        u.setEmail(email);
        u = dao.getUserByEmail(u);

        if (u != null && u.getEmail() != "")
        {
            Utils helper = new Utils();
            u.setCodigoActivacion(helper.randomAlphaNumeric(Configuration.getInstance().getLongitudCodigo()));
            if (dao.updateCodigo(u))
            {
                MailServicios nuevoMail = new MailServicios();
                nuevoMail.mandarMail(email, Constantes.EMAIL_CONTENT_NUEVA_PASS_1
                        + Constantes.LINK_EMAIL_NUEVA_PASS
                        + u.getCodigoActivacion()
                        + Constantes.EMAIL_CONTENT_NUEVA_PASS_2,
                        Language.ASUNTO_EMAIL_NUEVA_PASS);

                returnme = ajax.successResponse();
            }
            else
            {
                returnme = ajax.errorResponse(5);
            }
        }
        else
        {
            returnme = ajax.errorResponse(4);
        }
        return returnme;
    }

    public AjaxResponse restaurarPass(String pass, String codigo)
    {
        AjaxResponse returnme;
        if (codigo != null && !codigo.equals(""))
        {
            try
            {
                UsersDAO dao = new UsersDAO();
                User u = new User();
                u.setClave(PasswordHash.getInstance().createHash(pass));
                u.setCodigoActivacion(codigo);

                if (dao.updatePassByCodigo(u))
                {
                    returnme = ajax.successResponse();
                }
                else
                {
                    returnme = ajax.errorResponse(6);
                }
            }
            catch (NoSuchAlgorithmException | InvalidKeySpecException ex)
            {
                returnme = ajax.errorResponse(0);
            }
        }
        else
        {
            returnme = ajax.errorResponse(0);
        }
        return returnme;
    }

    public String getRango(String email)
    {
        UsersDAO dao = new UsersDAO();
        int idPermiso = dao.getPermiso(email);
        String rango = "";
        switch (idPermiso)
        {
            case 1:
                rango = "administrador";
                break;
            case 2:
                rango = "profesor";
                break;
            case 3:
                rango = "usuario";
                break;
        }
        return rango;
    }

    public AjaxResponse cambiarPass(String passActual, String nuevaPass, String email)
    {
        AjaxResponse returnme;
        UsersDAO dao = new UsersDAO();
        User u = new User();
        u.setEmail(email);

        try
        {
            u = dao.getUserByEmail(u);
            boolean validPass
                    = PasswordHash.getInstance().validatePassword(passActual, u.getClave());

            if (validPass)
            {
                u.setClave(PasswordHash.getInstance().createHash(nuevaPass));
                if (dao.updatePassByEmail(u))
                {
                    returnme = ajax.successResponse();
                }
                else
                {
                    returnme = ajax.errorResponse(0);
                }
            }
            else
            {
                returnme = ajax.errorResponse(7);
            }
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException | NullPointerException ex)
        {
            returnme = ajax.errorResponse(0);
        }
        return returnme;
    }
}
