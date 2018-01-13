package servicios;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import dao.AdminDAO;
import dao.ProfeDAO;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import model.Asignatura;
import model.Nota;
import model.Tarea;
import model.User;
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

    public AjaxResponse addTarea(Tarea t, String email) {
        ProfeDAO dao = new ProfeDAO();
        AjaxResponse returnme;
        List<Integer> idAlumnos = dao.getIdAlumnos(t.getAsignatura().getId());

        if (idAlumnos.size() > 0) {
            t = dao.addTarea(t, idAlumnos, email);

            if (t != null) {
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                HashMap<String, String> datos = new HashMap<>();
                datos.put(Constantes.PARAMETRO_ID_TAREA, String.valueOf(t.getId_tarea()));
                datos.put(Constantes.PARAMETRO_ID_ASIGNATURA, String.valueOf(t.getAsignatura().getId()));
                datos.put(Constantes.PARAMETRO_NOMBRE_TAREA, t.getNombre_tarea());
                datos.put(Constantes.PARAMETRO_FECHA_ENTREGA, df.format(t.getFecha_entrega()));
                returnme = ajax.successResponse(datos);
            } else {
                returnme = ajax.errorResponse(21);
            }
        } else {
            returnme = ajax.errorResponse(23);
        }

        return returnme;
    }

    public AjaxResponse modTarea(Tarea t) {
        ProfeDAO dao = new ProfeDAO();
        AjaxResponse returnme;

        t = dao.modTarea(t);

        if (t != null) {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            HashMap<String, String> datos = new HashMap<>();
            datos.put(Constantes.PARAMETRO_ID_TAREA, String.valueOf(t.getId_tarea()));
            datos.put(Constantes.PARAMETRO_ID_ASIGNATURA, String.valueOf(t.getAsignatura().getId()));
            datos.put(Constantes.PARAMETRO_NOMBRE_TAREA, t.getNombre_tarea());
            datos.put(Constantes.PARAMETRO_FECHA_ENTREGA, df.format(t.getFecha_entrega()));
            returnme = ajax.successResponse(datos);
        } else {
            returnme = ajax.errorResponse(22);
        }
        return returnme;
    }
    
    public List<Tarea> getAllTareas(String email){
        ProfeDAO dao = new ProfeDAO();
        return dao.getAllTareas(email);
    }
    public List<Asignatura> getAllAsignaturas()
    {
        AdminDAO dao = new AdminDAO();
        return dao.getAllAsignaturas();
    }
}
