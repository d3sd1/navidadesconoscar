/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import dao.AdminDAO;
import java.util.HashMap;
import java.util.List;
import model.Asignatura;
import utils.Constantes;

/**
 *
 * @author Miguel
 */
public class AdminServicios {

    private final AjaxMaker ajax = new AjaxMaker();

    public List<Asignatura> getAllAsignaturas(){
        AdminDAO dao = new AdminDAO();
        return dao.getAllAsignaturas();
    }
    
    public AjaxResponse addAsig(Asignatura a) {
        AjaxResponse returnme;
        AdminDAO dao = new AdminDAO();

        a = dao.addAsig(a);

        if (a != null) {
            HashMap<String, String> datos = new HashMap<>();
            datos.put(Constantes.PARAMETRO_ID, String.valueOf(a.getId()));
            datos.put(Constantes.PARAMETRO_NOMBRE, a.getNombre());
            datos.put(Constantes.PARAMETRO_ID_CURSO, String.valueOf(a.getId_curso()));
            returnme = ajax.successResponse(datos);
        }else{
            returnme = ajax.errorResponse(0);
        }
        return returnme;
    }
    
    public AjaxResponse modAsig(Asignatura a){
        AjaxResponse returnme;
        AdminDAO dao = new AdminDAO();

        a = dao.modAsig(a);
        
        if (a != null) {
            HashMap<String, String> datos = new HashMap<>();
            datos.put(Constantes.PARAMETRO_ID, String.valueOf(a.getId()));
            datos.put(Constantes.PARAMETRO_NOMBRE, a.getNombre());
            datos.put(Constantes.PARAMETRO_ID_CURSO, String.valueOf(a.getId_curso()));
            returnme = ajax.successResponse(datos);
        }else{
            returnme = ajax.errorResponse(0);
        }
        return returnme;
    }
    
    public AjaxResponse delAsig(Asignatura a){
        AjaxResponse returnme;
        AdminDAO dao = new AdminDAO();

        boolean borrado = dao.delAsig(a);
        
        if (borrado) {
            returnme = ajax.successResponse();
        }else{
            returnme = ajax.errorResponse(0);
        }
        return returnme;
    }
}
