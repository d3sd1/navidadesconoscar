/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import dao.AlumnosDAO;
import java.util.List;
import model.Nota;

/**
 *
 * @author Miguel
 */
public class AlumnosServicios {
    public List<Nota> getAllNotas(String email){
        AlumnosDAO dao = new AlumnosDAO();
        return dao.getAllNotas(email);
    }
}
