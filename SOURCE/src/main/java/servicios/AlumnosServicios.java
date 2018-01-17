package servicios;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import dao.AlumnosDAO;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import model.Nota;
import model.Tarea;
import model.User;
import utils.Parametros;

public class AlumnosServicios
{

    private final AjaxMaker ajax = new AjaxMaker();

    public List<Nota> getAllNotas(String email)
    {
        AlumnosDAO dao = new AlumnosDAO();
        User alumno = new User();
        alumno.setEmail(email);
        return dao.getAllNotas(alumno);
    }

    public List<Tarea> getAllTareas(String email)
    {
        AlumnosDAO dao = new AlumnosDAO();
        User alumno = new User();
        alumno.setEmail(email);
        return dao.getAllTareas(alumno);
    }

    public AjaxResponse completarTarea(int id_tarea, String email)
    {
        Tarea tarea = new Tarea();
        tarea.setId_tarea(id_tarea);
        User alumno = new User();
        alumno.setEmail(email);
        AlumnosDAO dao = new AlumnosDAO();
        Date fecha_actual = new Date();

        AjaxResponse returnme = ajax.errorResponse(24);
        
        Tarea tareaDB = dao.getTareaById(tarea, alumno);
        boolean fechaLimiteSuperada = fecha_actual.compareTo(tareaDB.getFecha_entrega()) > 0;
        if (fechaLimiteSuperada)
        {
            returnme = ajax.errorResponse(25);
        }
        else if (dao.completarTarea(tarea, alumno))
        {
            HashMap<String, String> datos = new HashMap<>();
            datos.put(Parametros.COMPLETADO, "true");
            returnme = ajax.successResponse(datos);
        }
        return returnme;
    }
    private static final Logger LOG = Logger.getLogger(AlumnosServicios.class.getName());
}
