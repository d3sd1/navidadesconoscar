package ajax;

import com.google.gson.Gson;
import java.util.HashMap;

public class AjaxMaker
{
    public String parseResponse(AjaxResponse resp)
    {
        return new Gson().toJson(resp);
    }
    public AjaxResponse errorResponse(String ex)
    {
        return errorResponse(ex,0);
    }
    public AjaxResponse errorResponse(String ex, int code)
    {
        return new AjaxResponse(false,ex,code);
    }
    public AjaxResponse successResponse()
    {
        return successResponse(new HashMap<String, String>());
    }
    public AjaxResponse successResponse(HashMap<String, String> data)
    {
        return new AjaxResponse(true,"",0,data);
    }
}
