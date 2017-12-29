package ajax;

import com.google.gson.Gson;
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
            case 6:
                errorMsg = Language.ERROR_PASSOLVIDADA_CODIGOINVALIDO;
            break;
            default:
                errorMsg = Language.ERROR_GENERIC;
        }
        return errorMsg;
    }
    public String parseResponse(AjaxResponse resp)
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
}
