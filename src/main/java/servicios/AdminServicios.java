package servicios;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import config.Configuration;
import dao.AdminDAO;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Asignatura;
import model.Curso;
import model.User;
import utils.Constantes;
import utils.Language;
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
        if (dao.eliminarProfeAsig(id_profe))
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
        return dao.getAlumnos(start,length);
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
        if (dao.eliminarAlumAsig(id_alumno))
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

    public AjaxResponse addUser(User u)
    {

        AdminDAO dao = new AdminDAO();
        AjaxResponse returnme;

        boolean existe = dao.comprobarEmail(u.getEmail());

        if (existe == false)
        {
            try
            {
                Utils helper = new Utils();
                String clave = helper.randomAlphaNumeric(Configuration.getInstance().getLongitudPass());
                u.setClave(PasswordHash.getInstance().createHash(clave));
                u.setCodigoActivacion(helper.randomAlphaNumeric(Configuration.getInstance().getLongitudCodigo()));
                u = dao.addUser(u);

                if (u != null)
                {
                    MailServicios nuevoMail = new MailServicios();
                    nuevoMail.mandarMail(u.getEmail(), Constantes.EMAIL_CONTENT_ACTIVAR_1
                            + Constantes.LINK_EMAIL_ACTIVAR + u.getCodigoActivacion()
                            + Constantes.EMAIL_CONTENT_ACTIVAR_2 + clave
                            + Constantes.EMAIL_CONTENT_ACTIVAR_3,
                            Language.ASUNTO_EMAIL_ACTIVAR);

                    HashMap<String, String> datos = new HashMap<>();
                    datos.put(Constantes.PARAMETRO_ID, String.valueOf(u.getId()));
                    datos.put(Constantes.PARAMETRO_NOMBRE, u.getNombre());
                    datos.put(Constantes.PARAMETRO_EMAIL, u.getEmail());
                    datos.put(Constantes.PARAMETRO_TIPO, String.valueOf(u.getId_permiso()));
                    returnme = ajax.successResponse(datos);
                }
                else
                {
                    returnme = ajax.errorResponse(12);
                }
            }
            catch (NoSuchAlgorithmException | InvalidKeySpecException ex)
            {
                Logger.getLogger(UsersServicios.class.getName()).log(Level.SEVERE, null, ex);
                returnme = ajax.errorResponse(0);
            }
        }
        else
        {
            returnme = ajax.errorResponse(3);
        }
        return returnme;
    }

    public AjaxResponse modUser(User u)
    {
        AdminDAO dao = new AdminDAO();
        AjaxResponse returnme;

        int permiso = dao.getPermiso(u.getId());
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
                datos.put(Constantes.PARAMETRO_ID, String.valueOf(u.getId()));
                datos.put(Constantes.PARAMETRO_NOMBRE, u.getNombre());
                datos.put(Constantes.PARAMETRO_EMAIL, u.getEmail());
                datos.put(Constantes.PARAMETRO_TIPO, String.valueOf(u.getId_permiso()));
                returnme = ajax.successResponse(datos);
            }
            else
            {
                returnme = ajax.errorResponse(13);
            }
        }
        return returnme;
    }

    public AjaxResponse delUser(User u)
    {
        AdminDAO dao = new AdminDAO();
        AjaxResponse returnme;

        if (u.getId() == 1)
        {
            returnme = ajax.errorResponse(20);
        }
        else
        {
            int borrado = dao.delUser(u);

            switch (borrado)
            {
                case 0:
                    returnme = ajax.errorResponse(14);
                    break;

                case 1:
                    returnme = ajax.successResponse();
                    break;

                case -1:
                    returnme = ajax.errorResponse(15);
                    break;

                default:
                    returnme = ajax.errorResponse(0);
            }
        }
        return returnme;
    }

    public AjaxResponse delUser2(User u)
    {
        AjaxResponse returnme;
        AdminDAO dao = new AdminDAO();

        if (u.getId() == 1)
        {
            returnme = ajax.errorResponse(20);
        }
        else
        {
            boolean borrado = dao.delUser2(u);

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

    public AjaxResponse addCurso(Curso c)
    {
        AjaxResponse returnme;
        AdminDAO dao = new AdminDAO();

        c = dao.addCurso(c);

        if (c != null)
        {
            HashMap<String, String> datos = new HashMap<>();
            datos.put(Constantes.PARAMETRO_ID, String.valueOf(c.getId()));
            datos.put(Constantes.PARAMETRO_NOMBRE, c.getNombre());
            returnme = ajax.successResponse(datos);
        }
        else
        {
            returnme = ajax.errorResponse(17);
        }
        return returnme;
    }

    public AjaxResponse modCurso(Curso c)
    {
        AjaxResponse returnme;
        AdminDAO dao = new AdminDAO();

        c = dao.modCurso(c);

        if (c != null)
        {
            HashMap<String, String> datos = new HashMap<>();
            datos.put(Constantes.PARAMETRO_ID, String.valueOf(c.getId()));
            datos.put(Constantes.PARAMETRO_NOMBRE, c.getNombre());
            returnme = ajax.successResponse(datos);
        }
        else
        {
            returnme = ajax.errorResponse(18);
        }
        return returnme;
    }
}
