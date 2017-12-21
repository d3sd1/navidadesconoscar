package servicios;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import dao.LoginDAO;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import utils.PasswordHash;

public class LoginServicios {

    private final AjaxMaker ajax = new AjaxMaker();

    public AjaxResponse login(String mail, String pass) {
        LoginDAO dao = new LoginDAO();
        User u = dao.getUserByMail(mail);
        AjaxResponse returnme;

        try {
            if (u != null && PasswordHash.getInstance().validatePassword(pass, u.getClave())) {
                if (u.getActivo()) {
                    returnme = ajax.successResponse();
                } else {
                    returnme = ajax.errorResponse(2);
                }
            } else {
                returnme = ajax.errorResponse(1);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(LoginServicios.class.getName()).log(Level.SEVERE, null, ex);
            returnme = ajax.errorResponse(0);
        }
        return returnme;
    }

    public String getNombre(String mail) {
        LoginDAO dao = new LoginDAO();
        User u = dao.getUserByMail(mail);
        String nombre = dao.getNombre(u.getEmail(), u.getId());
        return nombre;
    }
}
