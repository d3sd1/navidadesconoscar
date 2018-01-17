package filtros;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.Constantes;

@WebFilter(filterName = "RangoUsuario", urlPatterns =
{
    "/panel/alumno/*"
})
public class RangoUsuario implements Filter
{
    private FilterConfig filterConfig = null;
    
    public RangoUsuario()
    {
    }    
    
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException
    {
    }    
    
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException
    {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException
    {
        doBeforeProcessing(request, response);
        
        Throwable problem = null;
        try
        {
            Object rango = ((HttpServletRequest) request).getSession().getAttribute(Constantes.SESSION_RANGO_USUARIO);
            if (rango != null && rango.toString().equals("usuario"))
            {
                chain.doFilter(request, response);
            }
            else if(rango != null && !rango.toString().equals("usuario"))
            {
                ((HttpServletResponse) response).sendRedirect("/panel/inicio");
            }
            else if(rango == null)
            {
                ((HttpServletResponse) response).sendRedirect("/conectar");
            }
        }
        catch (Throwable t)
        {
            problem = t;
            t.printStackTrace();
        }
        
        doAfterProcessing(request, response);
        if (problem != null)
        {
            if (problem instanceof ServletException)
            {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException)
            {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }
    
    public FilterConfig getFilterConfig()
    {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig)
    {
        this.filterConfig = filterConfig;
    }

    public void destroy()
    {        
    }

    public void init(FilterConfig filterConfig)
    {        
        this.filterConfig = filterConfig;
        if (filterConfig != null)
        {
        }
    }

    @Override
    public String toString()
    {
        if (filterConfig == null)
        {
            return ("RangoUsuario()");
        }
        StringBuffer sb = new StringBuffer("RangoUsuario(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response)
    {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals(""))
        {
            try
            {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n");
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>");
                pw.close();
                ps.close();
                response.getOutputStream().close();
            }
            catch (Exception ex)
            {
            }
        }
        else
        {
            try
            {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            }
            catch (Exception ex)
            {
            }
        }
    }
    
    public static String getStackTrace(Throwable t)
    {
        String stackTrace = null;
        try
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        }
        catch (Exception ex)
        {
        }
        return stackTrace;
    }
    
    public void log(String msg)
    {
        filterConfig.getServletContext().log(msg);        
    }
    private static final Logger LOG = Logger.getLogger(RangoUsuario.class.getName());
    
}
