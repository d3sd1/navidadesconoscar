package servlets.alumno;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servicios.AlumnosServicios;
import utils.Constantes;
import utils.Utils;

@WebServlet(name = "VerNotas", urlPatterns
        =
        {
            "/panel/alumno/notas"
        })
public class VerNotas extends HttpServlet
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        AlumnosServicios as = new AlumnosServicios();

        String alumno = request.getSession().getAttribute(Constantes.SESSION_NOMBRE_USUARIO).toString();
        Utils helper = new Utils();

        helper.mostrarPlantilla("/panel/alumno/notas_ver.ftl", response.getWriter(),
                new AbstractMap.SimpleEntry<>("notas", as.getAllNotas(alumno))
        );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo()
    {
        return "Short description";
    }
    private static final Logger LOG = Logger.getLogger(VerNotas.class.getName());

}
