/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import dao.ProfeDAO;
import java.util.List;
import model.Nota;

/**
 *
 * @author Miguel
 */
public class ProfeServicios {
    
    private final AjaxMaker ajax = new AjaxMaker();
    
    public List<Nota> getAllNotas(String email){
        ProfeDAO dao = new ProfeDAO();
        int id = dao.getId(email);
        return dao.getAllNotas(id);
    }
}
