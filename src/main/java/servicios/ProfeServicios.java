package servicios;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import dao.AdminDAO;
import dao.ProfeDAO;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import model.Asignatura;
import model.Nota;
import model.Tarea;
import utils.Constantes;
import utils.Parametros;

public class ProfeServicios
{

    private final AjaxMaker ajax = new AjaxMaker();

    public List<Nota> getAllNotas(String email)
    {
        ProfeDAO dao = new ProfeDAO();
        int id = dao.getId(email);
        return dao.getAllNotas(id);
    }

    public List<Nota> getAllNotasCursosAlumnos(String email)
    {
        ProfeDAO dao = new ProfeDAO();
        int id = dao.getId(email);
        return dao.getAllNotasCursosAlumnos(id);
    }

    public AjaxResponse modNota(Nota n)
    {
        ProfeDAO dao = new ProfeDAO();
        AjaxResponse returnme;
        boolean notaCambiada = dao.modNota(n);

        if (notaCambiada)
        {
            HashMap<String, String> datos = new HashMap<>();
            datos.put(Parametros.NOTA, String.valueOf(n.getNota()));
            returnme = ajax.successResponse(datos);
        }
        else
        {
            returnme = ajax.errorResponse(16);
        }

        return returnme;
    }

    public List<Nota> getAllNotasCursos(String email)
    {
        ProfeDAO dao = new ProfeDAO();
        int id = dao.getId(email);
        return dao.getAllNotasCursos(id);
    }

    public List<Asignatura> getAllAsignaturas()
    {
        AdminDAO dao = new AdminDAO();
        return dao.getAllAsignaturas();
    }

    /* TAREAS */
    public AjaxResponse agregarTarea(int id_asignatura, LocalDate fecha_entrega, String nombre_tarea, int id_tarea, String email)
    {
        Tarea t = new Tarea();
        t.getAsignatura().setId(id_asignatura);
        t.setId_tarea(id_tarea);
        t.setNombre_tarea(nombre_tarea);
        t.setFecha_entrega(Date.from(fecha_entrega.atStartOfDay().toInstant(ZoneOffset.UTC)));
        t.setId_tarea(id_tarea);
        //t.getAsignatura().setNombre();

        ProfeDAO dao = new ProfeDAO();
        AjaxResponse returnme;
        List<Integer> idAlumnos = dao.getIdAlumnos(t.getAsignatura().getId());

        if (idAlumnos.size() > 0)
        {
            t = dao.addTarea(t, idAlumnos, email);

            if (t != null)
            {
                DateFormat df = new SimpleDateFormat(Constantes.FORMATO_FECHA);
                HashMap<String, String> datos = new HashMap<>();
                datos.put(Parametros.ID_TAREA, String.valueOf(t.getId_tarea()));
                datos.put(Parametros.ID_ASIGNATURA, String.valueOf(t.getAsignatura().getId()));
                datos.put(Parametros.NOMBRE_TAREA, t.getNombre_tarea());
                datos.put(Parametros.NOMBRE_ASIGNATURA, dao.getNombreAsignatura(t.getAsignatura().getId()));
                datos.put(Parametros.FECHA_ENTREGA, df.format(t.getFecha_entrega()));
                returnme = ajax.successResponse(datos);
            }
            else
            {
                returnme = ajax.errorResponse(21);
            }
        }
        else
        {
            returnme = ajax.errorResponse(23);
        }

        return returnme;
    }

    public AjaxResponse modTarea(Tarea t)
    {
        ProfeDAO dao = new ProfeDAO();
        AjaxResponse returnme;

        t = dao.modTarea(t);

        if (t != null)
        {
            DateFormat df = new SimpleDateFormat(Constantes.FORMATO_FECHA);
            HashMap<String, String> datos = new HashMap<>();
            datos.put(Parametros.ID_TAREA, String.valueOf(t.getId_tarea()));
            datos.put(Parametros.ID_ASIGNATURA, String.valueOf(t.getAsignatura().getId()));
            datos.put(Parametros.NOMBRE_TAREA, t.getNombre_tarea());
            datos.put(Parametros.FECHA_ENTREGA, df.format(t.getFecha_entrega()));
            datos.put(Parametros.NOMBRE_ASIGNATURA, dao.getNombreAsignatura(t.getAsignatura().getId()));
            returnme = ajax.successResponse(datos);
        }
        else
        {
            returnme = ajax.errorResponse(22);
        }
        return returnme;
    }

    public AjaxResponse delTarea(Tarea t)
    {
        ProfeDAO dao = new ProfeDAO();
        AjaxResponse returnme;

        boolean eliminado = dao.delTarea(t);

        if (eliminado)
        {
            returnme = ajax.successResponse();
        }
        else
        {
            returnme = ajax.errorResponse(26);
        }
        return returnme;
    }

    public List<Tarea> getAllTareas(String email)
    {
        ProfeDAO dao = new ProfeDAO();
        return dao.getAllTareas(email);
    }
}
