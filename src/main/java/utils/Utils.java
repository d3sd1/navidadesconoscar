
package utils;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

public class Utils {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        Random r = new SecureRandom();

        while (count-- != 0) {
            int character = (int) (r.nextInt(ALPHA_NUMERIC_STRING.length()));
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
    
    private Object depuradorParametros(String param)
    {
        Object parsedParam = "";
        if(param != null)
        {
            parsedParam = param;
        }
        return parsedParam;
    }
    public int depurarParametroInt(String param)
    {
        return (int)depuradorParametros(param);
    }
    public String depurarParametroString(String param)
    {
        return depuradorParametros(param).toString();
    }
    public boolean depurarParametroBoolean(String param)
    {
        return (Boolean)depuradorParametros(param);
    }
    public Date depurarParametroDate(String param)
    {
        Object date = depuradorParametros(param);
        Date returned;
        if(date == "")
        {
            returned = new Date();
        }
        else
        {
            returned = (Date) date;
        }
        return returned;
    }
}
