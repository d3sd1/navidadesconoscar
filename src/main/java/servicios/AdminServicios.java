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
import model.User;
import utils.Constantes;

/**
 *
 * @author Miguel
 */
public class AdminServicios
{

    private final AjaxMaker ajax = new AjaxMaker();

    public List<Asignatura> getAllAsignaturas()
    {
        AdminDAO dao = new AdminDAO();
        return dao.getAllAsignaturas();
    }

    public AjaxResponse addAsig(Asignatura a)
    {
        AjaxResponse returnme;
        AdminDAO dao = new AdminDAO();

        a = dao.addAsig(a);

        if (a != null)
        {
            HashMap<String, String> datos = new HashMap<>();
            datos.put(Constantes.PARAMETRO_ID, String.valueOf(a.getId()));
            datos.put(Constantes.PARAMETRO_NOMBRE, a.getNombre());
            datos.put(Constantes.PARAMETRO_ID_CURSO, String.valueOf(a.getId_curso()));
            returnme = ajax.successResponse(datos);
        }
        else
        {
            returnme = ajax.errorResponse(0);
        }
        return returnme;
    }

    public AjaxResponse modAsig(Asignatura a)
    {
        AjaxResponse returnme;
        AdminDAO dao = new AdminDAO();

        a = dao.modAsig(a);

        if (a != null)
        {
            HashMap<String, String> datos = new HashMap<>();
            datos.put(Constantes.PARAMETRO_ID, String.valueOf(a.getId()));
            datos.put(Constantes.PARAMETRO_NOMBRE, a.getNombre());
            datos.put(Constantes.PARAMETRO_ID_CURSO, String.valueOf(a.getId_curso()));
            returnme = ajax.successResponse(datos);
        }
        else
        {
            returnme = ajax.errorResponse(0);
        }
        return returnme;
    }

    public AjaxResponse delAsig(Asignatura a)
    {
        AjaxResponse returnme;
        AdminDAO dao = new AdminDAO();

        int borrado = dao.delAsig(a);

        switch (borrado)
        {
            case 0:
                returnme = ajax.errorResponse(8);
                break;

            case 1:
                returnme = ajax.successResponse();
                break;

            case -1:
                returnme = ajax.errorResponse(9);
                break;

            default:
                returnme = ajax.errorResponse(0);
        }
        return returnme;
    }

    public AjaxResponse delAsig2(Asignatura a)
    {
        AjaxResponse returnme;
        AdminDAO dao = new AdminDAO();

        boolean borrado = dao.delAsig2(a);

        if (borrado)
        {
            returnme = ajax.successResponse();
        }
        else
        {
            returnme = ajax.errorResponse(9);
        }
        return returnme;
    }

    public List<User> getAllProfes()
    {
        AdminDAO dao = new AdminDAO();
        return dao.getAllProfes();
    }

    public AjaxResponse asignarProfeAsig(int id_profe, String asignaturas)
    {
         AdminDAO dao = new AdminDAO();
        AjaxResponse returnme;

        String[] id_asignaturas = asignaturas.split(",");
        
        /* Primero se eliminan los registros del usuario y luego se actualizan...
        Así se evita tener que ir fila por fila revisando si existe o no.
        De este modo... También se agiliza el proceso.
        */
        boolean errors = false;
        if(dao.eliminarProfeAsig(id_profe))
        {
            if(!dao.asignarInsertandoProfeAsig(id_profe, id_asignaturas))
            {
                errors = true;
            }
        }
        else
        {
            errors = true;
        }
        
        return (!errors ? returnme = ajax.successResponse():ajax.errorResponse(10));
        
    }
    
    public List<User> getAllAlumnos()
    {
        AdminDAO dao = new AdminDAO();
        return dao.getAllAlumnos();
    }

    public List getAsigAlumno()
    {
        AdminDAO dao = new AdminDAO();
        return dao.getAsignaturasAlumno();
    }
    
    public List getAsigProfesor()
    {
        AdminDAO dao = new AdminDAO();
        return dao.getAsignaturasProfesor();
    }

    public AjaxResponse asignarAlumAsig(int id_alumno, String asignaturas)
    {
        AdminDAO dao = new AdminDAO();
        AjaxResponse returnme;

        String[] id_asignaturas = asignaturas.split(",");
        
        /* Primero se eliminan los registros del usuario y luego se actualizan...
        Así se evita tener que ir fila por fila revisando si existe o no.
        De este modo... También se agiliza el proceso.
        */
        boolean errors = false;
        if(dao.eliminarAlumAsig(id_alumno))
        {
            if(!dao.asignarInsertandoAlumAsig(id_alumno, id_asignaturas))
            {
                errors = true;
            }
        }
        else
        {
            errors = true;
        }
        
        return (!errors ? returnme = ajax.successResponse():ajax.errorResponse(10));
    }
}
