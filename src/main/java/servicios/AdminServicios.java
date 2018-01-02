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
public class AdminServicios {

    private final AjaxMaker ajax = new AjaxMaker();

    public List<Asignatura> getAllAsignaturas() {
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
        } else {
            returnme = ajax.errorResponse(0);
        }
        return returnme;
    }

    public AjaxResponse modAsig(Asignatura a) {
        AjaxResponse returnme;
        AdminDAO dao = new AdminDAO();

        a = dao.modAsig(a);

        if (a != null) {
            HashMap<String, String> datos = new HashMap<>();
            datos.put(Constantes.PARAMETRO_ID, String.valueOf(a.getId()));
            datos.put(Constantes.PARAMETRO_NOMBRE, a.getNombre());
            datos.put(Constantes.PARAMETRO_ID_CURSO, String.valueOf(a.getId_curso()));
            returnme = ajax.successResponse(datos);
        } else {
            returnme = ajax.errorResponse(0);
        }
        return returnme;
    }

    public AjaxResponse delAsig(Asignatura a) {
        AjaxResponse returnme;
        AdminDAO dao = new AdminDAO();

        int borrado = dao.delAsig(a);

        switch (borrado) {
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

    public AjaxResponse delAsig2(Asignatura a) {
        AjaxResponse returnme;
        AdminDAO dao = new AdminDAO();

        boolean borrado = dao.delAsig2(a);

        if (borrado) {
            returnme = ajax.successResponse();
        } else {
            returnme = ajax.errorResponse(9);
        }
        return returnme;
    }

    public List<User> getAllProfes() {
        AdminDAO dao = new AdminDAO();
        return dao.getAllProfes();
    }

    public AjaxResponse asignarProfeAsig(int id_profe, String asignaturas) {
        AdminDAO dao = new AdminDAO();
        AjaxResponse returnme;
        
        String[] id_asignaturas = asignaturas.split(",");
        
        if(dao.asignarProfeAsig(id_profe, id_asignaturas)){
            returnme = ajax.successResponse();
        }else{
            returnme = ajax.errorResponse(10);
        }
        
        return returnme;
    }
    
    public AjaxResponse eliminarProfeAsig(int id_profe, String asignaturas) {
        AdminDAO dao = new AdminDAO();
        AjaxResponse returnme;
        
        String[] id_asignaturas = asignaturas.split(",");
        
        if(dao.eliminarProfeAsig(id_profe, id_asignaturas)){
            returnme = ajax.successResponse();
        }else{
            returnme = ajax.errorResponse(11);
        }
        
        return returnme;
    }
    
    public List<User> getAllAlumnos() {
        AdminDAO dao = new AdminDAO();
        return dao.getAllAlumnos();
    }

    public AjaxResponse asignarAlumAsig(int id_alumno, String asignaturas) {
        AdminDAO dao = new AdminDAO();
        AjaxResponse returnme;
        
        String[] id_asignaturas = asignaturas.split(",");
        
        if(dao.asignarAlumAsig(id_alumno, id_asignaturas)){
            returnme = ajax.successResponse();
        }else{
            returnme = ajax.errorResponse(10);
        }
        
        return returnme;
    }
    
    public AjaxResponse eliminarAlumAsig(int id_alumno, String asignaturas) {
        AdminDAO dao = new AdminDAO();
        AjaxResponse returnme;
        
        String[] id_asignaturas = asignaturas.split(",");
        
        if(dao.eliminarAlumAsig(id_alumno, id_asignaturas)){
            returnme = ajax.successResponse();
        }else{
            returnme = ajax.errorResponse(11);
        }
        
        return returnme;
    }
}
