/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import dao.AlumnosDAO;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import model.Nota;
import model.Tarea;
import utils.Constantes;

/**
 *
 * @author Miguel
 */
public class AlumnosServicios {
    
    private final AjaxMaker ajax = new AjaxMaker();
    
    public List<Nota> getAllNotas(String email){
        AlumnosDAO dao = new AlumnosDAO();
        return dao.getAllNotas(email);
    }
    
    public List<Tarea> getAllTareas (String email){
        AlumnosDAO dao = new AlumnosDAO();
        return dao.getAllTareas(email);
    }
    
    public AjaxResponse completarTarea(Tarea t, String email){
        AjaxResponse returnme;
        AlumnosDAO dao = new AlumnosDAO();
        Date fecha_actual = new Date();
        Tarea tareaDB = dao.getTareaById(t.getId_tarea());
        
        if(fecha_actual.compareTo(tareaDB.getFecha_entrega())>0){
            returnme = ajax.errorResponse(25);
        }else{
            
            boolean completado = dao.completarTarea(t, email);
            
            if(completado == true){
                HashMap<String, String> datos = new HashMap<>();
                datos.put(Constantes.PARAMETRO_COMPLETADO, String.valueOf(completado));
                returnme = ajax.successResponse(datos);
            }else{
                returnme = ajax.errorResponse(24);
            }
        }
        return returnme;
    }
}
