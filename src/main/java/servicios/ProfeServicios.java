package servicios;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import dao.ProfeDAO;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import model.Nota;
import model.Tarea;
import utils.Constantes;

public class ProfeServicios {

    private final AjaxMaker ajax = new AjaxMaker();

    public List<Nota> getAllNotas(String email) {
        ProfeDAO dao = new ProfeDAO();
        int id = dao.getId(email);
        return dao.getAllNotas(id);
    }
    
    public List<Nota> getAllNotasCursosAlumnos(String email) {
        ProfeDAO dao = new ProfeDAO();
        int id = dao.getId(email);
        return dao.getAllNotasCursosAlumnos(id);
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
    
    public List<Nota> getAllNotasCursos(String email) {
        ProfeDAO dao = new ProfeDAO();
        int id = dao.getId(email);
        return dao.getAllNotasCursos(id);
    }
    
    public AjaxResponse addTarea(Tarea t){
        ProfeDAO dao = new ProfeDAO();
        AjaxResponse returnme;
        
        t = dao.addTarea(t);

        if (t != null) {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            HashMap<String, String> datos = new HashMap<>();
            datos.put(Constantes.PARAMETRO_ID_TAREA, String.valueOf(t.getId_tarea()));
            datos.put(Constantes.PARAMETRO_ID_ASIGNATURA, String.valueOf(t.getId_asignatura()));
            datos.put(Constantes.PARAMETRO_NOMBRE_TAREA, t.getNombre_tarea());
            datos.put(Constantes.PARAMETRO_FECHA_ENTREGA, df.format(t.getFecha_entrega()));
            returnme = ajax.successResponse(datos);
        } else {
            returnme = ajax.errorResponse(21);
        }
        return returnme;
    }
    
    public AjaxResponse modTarea(Tarea t){
        ProfeDAO dao = new ProfeDAO();
        AjaxResponse returnme;
        
        t = dao.modTarea(t);

        if (t != null) {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            HashMap<String, String> datos = new HashMap<>();
            datos.put(Constantes.PARAMETRO_ID_TAREA, String.valueOf(t.getId_tarea()));
            datos.put(Constantes.PARAMETRO_ID_ASIGNATURA, String.valueOf(t.getId_asignatura()));
            datos.put(Constantes.PARAMETRO_NOMBRE_TAREA, t.getNombre_tarea());
            datos.put(Constantes.PARAMETRO_FECHA_ENTREGA, df.format(t.getFecha_entrega()));
            returnme = ajax.successResponse(datos);
        } else {
            returnme = ajax.errorResponse(22);
        }
        return returnme;
    }
}
