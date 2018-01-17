package ajax;

import java.util.HashMap;
import java.util.logging.Logger;

public class AjaxResponse {
    private boolean success;
    private String reason;
    private int code;
    private HashMap data;
    public AjaxResponse(boolean success, String reason, int code)
    {
        this.success = success;
        this.reason = reason;
        this.code = code;
    }
    
    public AjaxResponse(boolean success, String reason, int code, HashMap data)
    {
        this.success = success;
        this.reason = reason;
        this.code = code;
        this.data = data;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public HashMap getData()
    {
        return data;
    }

    public void setData(HashMap data)
    {
        this.data = data;
    }
    private static final Logger LOG = Logger.getLogger(AjaxResponse.class.getName());

    
}
