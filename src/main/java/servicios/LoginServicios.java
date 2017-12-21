
package servicios;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import dao.LoginDAO;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import utils.Constantes;
import utils.PasswordHash;

public class LoginServicios {
    
    private final AjaxMaker ajax = new AjaxMaker();
    private final LoginDAO dao = new LoginDAO();
    private AjaxResponse returnme;
    
    public AjaxResponse login(String mail, String pass){
        User u = dao.getUserByMail(mail);
        
        try {
            if(u!=null && PasswordHash.getInstance().validatePassword(pass, u.getPassword())){
                if(u.getActivo()){
                    returnme = ajax.successResponse();
                }else{
                    returnme = ajax.errorResponse(Constantes.ERROR_CUENTA_ACTIVA);
                }
            }else{
                returnme = ajax.errorResponse(Constantes.ERROR_USER_PASS);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(LoginServicios.class.getName()).log(Level.SEVERE, null, ex);
            returnme = ajax.errorResponse(Constantes.ERROR_LOGIN);
        }
        return returnme;
    }
    
    public User getUser(String mail){
        User u = dao.getUserByMail(mail);
        return u;
    }
}
