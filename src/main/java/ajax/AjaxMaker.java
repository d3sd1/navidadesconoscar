package ajax;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import utils.Language;

public class AjaxMaker
{
    
    /* MÉTODOS GLOBALES */
    public String transformCodeToError(int errorCode)
    {
        String errorMsg;
        switch(errorCode)
        {
            case 0:
                errorMsg = Language.ERROR_GENERIC;
            break;
            case 1:
                errorMsg = Language.ERROR_USER_PASS;
            break;
            case 2:
                errorMsg = Language.ERROR_CUENTA_INACTIVA;
            break;
            case 3:
                errorMsg = Language.ERROR_EMAIL_EXISTE;
            break;
            case 4:
                errorMsg = Language.ERROR_EMAIL_NOEXISTE;
            break;
            case 5:
                errorMsg = Language.ERROR_PASSOLVIDADA_GENERICO;
            break;
            case 6:
                errorMsg = Language.ERROR_PASSOLVIDADA_CODIGOINVALIDO;
            break;
            case 7:
                errorMsg = Language.ERROR_NUEVAPASS_PASSINVALIDA;
            break;
            case 8:
                errorMsg = Language.ERROR_INTEGRIDAD_ASIGNATURAS;
            break;
            case 9:
                errorMsg = Language.ERROR_BORRAR_ASIGNATURAS;
            break;
            case 10:
                errorMsg = Language.ERROR_ASIGNAR_ASIGNATURAS;
            break;
            case 11:
                errorMsg = Language.ERROR_QUITAR_ASIGNATURAS;
            break;
            case 12:
                errorMsg = Language.ERROR_INSERTAR_USER;
                break;
            case 13:
                errorMsg = Language.ERROR_MODIFICAR_USER;
                break;
            case 14:
                errorMsg = Language.ERROR_BORRAR_USER_INTEGRIDAD;
                break;
            case 15:
                errorMsg = Language.ERROR_BORRAR_USER;
                break;
            case 16:
                errorMsg = Language.ERROR_MODIFICAR_NOTA;
                break;
            case 17:
                errorMsg = Language.ERROR_INSERTAR_CURSO;
                break;
            case 18:
                errorMsg = Language.ERROR_MODIFICAR_CURSO;
                break;
            case 19:
                errorMsg = Language.ERROR_BORRAR_CURSO;
                break;
            case 20:
                errorMsg = Language.ERROR_BORRAR_ADMIN;
                break;
            case 21:
                errorMsg = Language.ERROR_INSERTAR_TAREA;
                break;
            case 22:
                errorMsg = Language.ERROR_MODIFICAR_TAREA;
                break;
            case 23:
                errorMsg = Language.ERROR_INSERTAR_TAREA_NO_ALUMNOS;
                break;
            case 24:
                errorMsg = Language.ERROR_COMPLETAR_TAREA;
                break;
            case 25:
                errorMsg = Language.ERROR_COMPLETAR_TAREA_TIEMPO;
                break;
            case 26:
                errorMsg = Language.ERROR_BORRAR_TAREA;
                break;
            default:
                errorMsg = Language.ERROR_GENERIC;
        }
        return errorMsg;
    }
    public String parseResponse(Object resp)
    {
        return new Gson().toJson(resp);
    }
    
    /* MENSAJES DE ERROR */
    public AjaxResponse errorResponse()
    {
        return errorResponse(0);
    }
    public AjaxResponse errorResponse(int code)
    {
        return new AjaxResponse(false,transformCodeToError(code),code);
    }
    
    /* MENSAJES DE ACIERTO */
    public AjaxResponse successResponse()
    {
        return successResponse(new HashMap<String, String>());
    }
    public AjaxResponse successResponse(HashMap<String, String> data)
    {
        return new AjaxResponse(true,"",0,data);
    }
    public PaginateResponse paginateResponse(ArrayList<ArrayList<String>> users, int total)
    {
        return new PaginateResponse(1,total,total,users);
    }
}
