/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import dao.ProfeDAO;
import java.util.HashMap;
import java.util.List;
import model.Nota;
import utils.Constantes;

/**
 *
 * @author Miguel
 */
public class ProfeServicios {

    private final AjaxMaker ajax = new AjaxMaker();

    public List<Nota> getAllNotas(String email) {
        ProfeDAO dao = new ProfeDAO();
        int id = dao.getId(email);
        return dao.getAllNotas(id);
    }

    public AjaxResponse modNota(Nota n) {
        ProfeDAO dao = new ProfeDAO();
        AjaxResponse returnme;
        boolean notaCambiada = dao.modNota(n);

        if (notaCambiada) {
            HashMap<String, String> datos = new HashMap<>();
            datos.put(Constantes.PARAMETRO_NOTA, String.valueOf(n.getNota()));
            returnme = ajax.successResponse(datos);
        } else {
            returnme = ajax.errorResponse(16);
        }
        
        return returnme;
    }
}
