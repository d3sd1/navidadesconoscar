package servicios;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import config.Configuration;
import dao.UsersDAO;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
        UsersDAO dao = new UsersDAO();
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
            Logger.getLogger(UsersServicios.class.getName()).log(Level.SEVERE, null, ex);
            returnme = ajax.errorResponse(0);
        }
        return returnme;
    }

    public String getNombre(String mail) {
        UsersDAO dao = new UsersDAO();
        User u = dao.getUserByMail(mail);
        String nombre = dao.getNombre(u.getId());
        return nombre;
    }

    public AjaxResponse registro(String mail, String pass, String nombre, int tipo) {
        UsersDAO dao = new UsersDAO();
        AjaxResponse returnme;
        
        User u = dao.getUserByMail(mail);

        if (u == null) {
            try {
                u = new User();
                u.setEmail(mail);
                u.setClave(PasswordHash.getInstance().createHash(pass));
                u.setCodigo_activacion(Utils.randomAlphaNumeric(Configuration.getInstance().getLongitudCodigo()));
                boolean userRegistrado = dao.registro(u, nombre, tipo);
                if(userRegistrado == true){
                    returnme = ajax.successResponse();
                    MailServicios nuevoMail = new MailServicios();
                    nuevoMail.mandarMail(u.getEmail(), Constantes.LINK_EMAIL + u.getCodigo_activacion(), Language.ASUNTO_EMAIL);
                }else{
                    returnme = ajax.errorResponse(0);
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                Logger.getLogger(UsersServicios.class.getName()).log(Level.SEVERE, null, ex);
                returnme = ajax.errorResponse(0);
            }
        }else{
            returnme = ajax.errorResponse(3);
        }
        return returnme;
    }
}
