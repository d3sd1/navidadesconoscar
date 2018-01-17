package servicios;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import config.Configuration;
import dao.UsersDAO;
import java.util.logging.Logger;
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
        user.setEmail(mail);
        user.setClave(pass);

        User usuarioEncontrado = dao.getUserByEmail(user);
        boolean validPass = PasswordHash.getInstance().validatePassword(user.getClave(), usuarioEncontrado.getClave()),
                usuarioActivo = usuarioEncontrado.getActivo();
        if (validPass && usuarioActivo)
        {
            returnme = ajax.successResponse();
        }
        else if(validPass && !usuarioActivo)
        {
            returnme = ajax.errorResponse(2);
        }

        return returnme;
    }

    public boolean activar(String codigo)
    {
        UsersDAO dao = new UsersDAO();
        User user = new User();
        user.setCodigoActivacion(codigo);
        User usuarioEncontrado = dao.getUserByCodigoActivacion(user);

        boolean activado = false;

        if (usuarioEncontrado.getActivo() == false)
        {
            activado = dao.activarUser(usuarioEncontrado);
        }
        return activado;
    }

    public AjaxResponse reenviarMailActivacion(String email)
    {
        UsersDAO dao = new UsersDAO();
        User user = new User();
        user.setEmail(email);
        user = dao.getUserByEmail(user);
        
        AjaxResponse returnme = ajax.errorResponse(4);

        if (user.getEmail() != null && !user.getEmail().equals(""))
        {
            Utils helper = new Utils();
            user.setCodigoActivacion(helper.randomAlphaNumeric(Configuration.getInstance().getLongitudCodigo()));
            if (dao.updateCodigo(user))
            {
                helper.mandarMail(email, Constantes.EMAIL_CONTENT_NUEVA_PASS_1
                        + Constantes.LINK_EMAIL_NUEVA_PASS
                        + user.getCodigoActivacion()
                        + Constantes.EMAIL_CONTENT_NUEVA_PASS_2,
                        Language.ASUNTO_EMAIL_NUEVA_PASS);

                returnme = ajax.successResponse();
            }
            else
            {
                returnme = ajax.errorResponse(5);
            }
        }
        return returnme;
    }

    public AjaxResponse restaurarPass(String pass, String codigo)
    {
        AjaxResponse returnme = ajax.errorResponse(0);
        if (codigo != null && !codigo.equals(""))
        {
            UsersDAO dao = new UsersDAO();
            User user = new User();
            user.setClave(PasswordHash.getInstance().createHash(pass));
            user.setCodigoActivacion(codigo);

            if (dao.updatePassByCodigo(user))
            {
                returnme = ajax.successResponse();
            }
            else
            {
                returnme = ajax.errorResponse(6);
            }
        }
        return returnme;
    }

    public String getRango(String email)
    {
        UsersDAO dao = new UsersDAO();
        User user = new User();
        user.setEmail(email);
        String rango = "";
        switch (dao.getPermiso(user))
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
        AjaxResponse returnme = ajax.errorResponse(0);
        UsersDAO dao = new UsersDAO();
        User user = new User();
        user.setEmail(email);

        User userEncontrado = dao.getUserByEmail(user);
        
        boolean validPass = PasswordHash.getInstance().validatePassword(passActual, userEncontrado.getClave());
        if (validPass)
        {
            user.setClave(PasswordHash.getInstance().createHash(nuevaPass));
            if (dao.updatePassByEmail(user))
            {
                returnme = ajax.successResponse();
            }
        }
        else
        {
            returnme = ajax.errorResponse(7);
        }
        return returnme;
    }
    private static final Logger LOG = Logger.getLogger(UsersServicios.class.getName());
}
