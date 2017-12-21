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
                errorMsg = Language.ERROR_CUENTA_ACTIVA;
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
