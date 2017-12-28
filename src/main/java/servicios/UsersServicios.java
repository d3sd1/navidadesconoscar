package servicios;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import config.Configuration;
import dao.UsersDAO;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import utils.Constantes;
import utils.Language;
import utils.PasswordHash;
import utils.Utils;

public class UsersServicios {

    private final AjaxMaker ajax = new AjaxMaker();

    public AjaxResponse login(String mail, String pass) {
        AjaxResponse returnme = ajax.errorResponse(1);
        UsersDAO dao = new UsersDAO();
        User user = new User();
        try {
            user.setEmail(mail);
            user.setClave(pass);

            User foundUser = dao.getUserByEmail(user);
            if (foundUser != null) {
                boolean validPass = PasswordHash.getInstance().validatePassword(user.getClave(), foundUser.getClave());
                if (validPass) {
                    if (foundUser.getActivo()) {
                        returnme = ajax.successResponse();
                    } else {
                        returnme = ajax.errorResponse(2);
                    }
                }
            }

        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            returnme = ajax.errorResponse(0);
        }
        return returnme;
    }

    public AjaxResponse registro(String mail, String pass, String nombre, int tipo) {
        UsersDAO dao = new UsersDAO();
        AjaxResponse returnme;

        User userRegister = new User();
        userRegister.setEmail(mail);
        userRegister.setClave(pass);

        User u = dao.getUserByEmail(userRegister);

        if (u == null) {
            try {
                u = new User();
                u.setEmail(mail);
                u.setClave(PasswordHash.getInstance().createHash(pass));
                u.setCodigo_activacion(Utils.randomAlphaNumeric(Configuration.getInstance().getLongitudCodigo()));
                boolean userRegistrado = dao.registro(u, nombre, tipo);
                if (userRegistrado == true) {
                    returnme = ajax.successResponse();
                    MailServicios nuevoMail = new MailServicios();
                    nuevoMail.mandarMail(u.getEmail(), Constantes.EMAIL_CONTENT_ACTIVAR_1
                            + Constantes.LINK_EMAIL_ACTIVAR + u.getCodigo_activacion()
                            + Constantes.EMAIL_CONTENT_ACTIVAR_2,
                            Language.ASUNTO_EMAIL_ACTIVAR);
                } else {
                    returnme = ajax.errorResponse(0);
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                Logger.getLogger(UsersServicios.class.getName()).log(Level.SEVERE, null, ex);
                returnme = ajax.errorResponse(0);
            }
        } else {
            returnme = ajax.errorResponse(3);
        }
        return returnme;
    }

    public int activar(String codigo) {
        UsersDAO dao = new UsersDAO();
        User u = dao.getUserByCodigoActivacion(codigo);

        int activar;

        if (u == null) {
            activar = -1;
        } else if (u.getActivo() == false) {
            activar = dao.activarUser(u);
        } else {
            activar = 2;
        }
        return activar;
    }

    public AjaxResponse mandarMail(String email) {
        AjaxResponse returnme;
        UsersDAO dao = new UsersDAO();
        User u = new User();
        u.setEmail(email);
        u = dao.getUserByEmail(u);

        if (u != null) {
            u.setCodigo_activacion(Utils.randomAlphaNumeric(Configuration.getInstance().getLongitudCodigo()));
            if (dao.updateCodigo(u)) {
                MailServicios nuevoMail = new MailServicios();
                nuevoMail.mandarMail(email, Constantes.EMAIL_CONTENT_NUEVA_PASS_1
                        + Constantes.LINK_EMAIL_NUEVA_PASS
                        + u.getCodigo_activacion()
                        + Constantes.EMAIL_CONTENT_NUEVA_PASS_2,
                        Language.ASUNTO_EMAIL_NUEVA_PASS);

                returnme = ajax.successResponse();
            } else {
                returnme = ajax.errorResponse(0);
            }
        } else {
            returnme = ajax.errorResponse(0);
        }
        return returnme;
    }

    public AjaxResponse restaurarPass(String pass, String codigo) {
        AjaxResponse returnme;

        try {
            UsersDAO dao = new UsersDAO();
            User u = new User();
            u.setClave(PasswordHash.getInstance().createHash(pass));
            u.setCodigo_activacion(codigo);

            if (dao.updatePass(u)) {
                returnme = ajax.successResponse();
            } else {
                returnme = ajax.errorResponse(0);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(UsersServicios.class.getName()).log(Level.SEVERE, null, ex);
            returnme = ajax.errorResponse(0);
        }
        return returnme;
    }
}
