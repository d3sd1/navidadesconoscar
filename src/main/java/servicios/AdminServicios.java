package servicios;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import config.Configuration;
import dao.AdminDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.Asignatura;
import model.Curso;
import model.User;
import utils.Constantes;
import utils.Language;
import utils.Parametros;
import utils.PasswordHash;
import utils.Utils;

public class AdminServicios
{

    private final AjaxMaker ajax = new AjaxMaker();

    public List<Asignatura> getAllAsignaturas()
    {
        AdminDAO dao = new AdminDAO();
        return dao.getAllAsignaturas();
    }

    public AjaxResponse addAsig(int id_curso, String nombre)
    {
        AjaxResponse returnme = ajax.errorResponse(0);
        AdminDAO dao = new AdminDAO();

        Asignatura asignatura = new Asignatura();

        asignatura.setNombre(nombre);
        asignatura.setId_curso(id_curso);

        if (dao.addAsig(asignatura))
        {
            HashMap<String, String> datos = new HashMap<>();
            datos.put(Parametros.ID, String.valueOf(asignatura.getId()));
            datos.put(Parametros.NOMBRE, asignatura.getNombre());
            datos.put(Parametros.ID_CURSO, String.valueOf(asignatura.getId_curso()));
            returnme = ajax.successResponse(datos);
        }
        return returnme;
    }

    public AjaxResponse modAsig(int id, String nombre, int id_curso)
    {
        AjaxResponse returnme;
        AdminDAO dao = new AdminDAO();
        Asignatura a = new Asignatura();

        a.setId(id);
        a.setNombre(nombre);
        a.setId_curso(id_curso);

        if (dao.modAsig(a))
        {
            HashMap<String, String> datos = new HashMap<>();
            datos.put(Parametros.ID, String.valueOf(a.getId()));
            datos.put(Parametros.NOMBRE, a.getNombre());
            datos.put(Parametros.ID_CURSO, String.valueOf(a.getId_curso()));
            returnme = ajax.successResponse(datos);
        }
        else
        {
            returnme = ajax.errorResponse(0);
        }
        return returnme;
    }

    public AjaxResponse delAsig(int id_asignatura)
    {
        AjaxResponse returnme;
        AdminDAO dao = new AdminDAO();
        Asignatura a = new Asignatura();
        a.setId(id_asignatura);
        boolean borrado = dao.delAsig(a);

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
        User profe = new User();
        profe.setId(id_profe);
        boolean errors = false;
        if (dao.eliminarProfeAsig(profe))
        {
            if (!dao.asignarInsertandoProfeAsig(id_profe, id_asignaturas))
            {
                errors = true;
            }
        }
        else
        {
            errors = true;
        }

        return (!errors ? returnme = ajax.successResponse() : ajax.errorResponse(10));

    }

    public List<User> getAllAlumnos()
    {
        AdminDAO dao = new AdminDAO();
        return dao.getAllAlumnos();
    }

    public ArrayList<ArrayList<String>> getAlumnos(int start, int length)
    {
        AdminDAO dao = new AdminDAO();
        return dao.getAlumnos(start, length);
    }

    public int getTotalAlumnos()
    {
        AdminDAO dao = new AdminDAO();
        return dao.getTotalAlumnos();
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
        User alumno = new User();
        alumno.setId(id_alumno);
        if (dao.eliminarAlumAsig(alumno))
        {
            if (!dao.asignarInsertandoAlumAsig(id_alumno, id_asignaturas))
            {
                errors = true;
            }
        }
        else
        {
            errors = true;
        }

        return (!errors ? returnme = ajax.successResponse() : ajax.errorResponse(10));
    }

    public List<User> getAllUsers()
    {
        AdminDAO dao = new AdminDAO();
        return dao.getAllUsers();
    }

    public AjaxResponse addUser(String email, String nombre, int id_permiso)
    {

        AdminDAO dao = new AdminDAO();
        AjaxResponse returnme;
        User u = new User();
        u.setEmail(email);
        u.setNombre(nombre);
        u.setId_permiso(id_permiso);

        boolean existe = dao.comprobarEmail(u);

        if (!existe)
        {
            try
            {
                Utils helper = new Utils();
                String clave = helper.randomAlphaNumeric(Configuration.getInstance().getLongitudPass());
                u.setClave(PasswordHash.getInstance().createHash(clave));
                u.setCodigoActivacion(helper.randomAlphaNumeric(Configuration.getInstance().getLongitudCodigo()));

                if (dao.addUser(u))
                {
                    helper.mandarMail(u.getEmail(), Constantes.EMAIL_CONTENT_ACTIVAR_1
                            + Constantes.LINK_EMAIL_ACTIVAR + u.getCodigoActivacion()
                            + Constantes.EMAIL_CONTENT_ACTIVAR_2 + clave
                            + Constantes.EMAIL_CONTENT_ACTIVAR_3,
                            Language.ASUNTO_EMAIL_ACTIVAR);

                    HashMap<String, String> datos = new HashMap<>();
                    datos.put(Parametros.ID, String.valueOf(u.getId()));
                    datos.put(Parametros.NOMBRE, u.getNombre());
                    datos.put(Parametros.EMAIL, u.getEmail());
                    datos.put(Parametros.TIPO, String.valueOf(u.getId_permiso()));
                    returnme = ajax.successResponse(datos);
                }
                else
                {
                    returnme = ajax.errorResponse(12);
                }
            }
            catch (Exception ex)
            {
                returnme = ajax.errorResponse(0);
            }
        }
        else
        {
            returnme = ajax.errorResponse(3);
        }
        return returnme;
    }

    public AjaxResponse modUser(int id, String email, String nombre, int id_permiso)
    {
        AdminDAO dao = new AdminDAO();
        AjaxResponse returnme;
        User u = new User();
        u.setId(id);
        u.setEmail(email);
        u.setNombre(nombre);
        u.setId_permiso(id_permiso);

        int permiso = dao.getPermiso(u);
        if (u.getId() == 1)
        {
            returnme = ajax.errorResponse(20);
        }
        else
        {
            boolean modificado = dao.modUser(u, permiso);

            if (modificado == true)
            {
                HashMap<String, String> datos = new HashMap<>();
                datos.put(Parametros.ID, String.valueOf(u.getId()));
                datos.put(Parametros.NOMBRE, u.getNombre());
                datos.put(Parametros.EMAIL, u.getEmail());
                datos.put(Parametros.TIPO, String.valueOf(u.getId_permiso()));
                returnme = ajax.successResponse(datos);
            }
            else
            {
                returnme = ajax.errorResponse(13);
            }
        }
        return returnme;
    }

    public AjaxResponse delUser(int id)
    {
        AjaxResponse returnme;
        AdminDAO dao = new AdminDAO();

        User u = new User();
        u.setId(id);

        if (u.getId() == 1)
        {
            returnme = ajax.errorResponse(20);
        }
        else
        {
            boolean borrado = dao.delUser(u);

            if (borrado == true)
            {
                returnme = ajax.successResponse();
            }
            else
            {
                returnme = ajax.errorResponse(15);
            }
        }
        return returnme;
    }

    public List<Curso> getAllCursos()
    {
        AdminDAO dao = new AdminDAO();
        return dao.getAllCursos();
    }
}
