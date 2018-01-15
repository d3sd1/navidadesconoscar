package servlets;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import java.io.IOException;
import java.util.AbstractMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servicios.UsersServicios;
import utils.Constantes;
import utils.Language;
import utils.Parametros;
import utils.Utils;

@WebServlet(name = "Conectar", urlPatterns =
{
    "/conectar"
})
public class Conectar extends HttpServlet
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        request.setCharacterEncoding("ISO-8859-1");

        UsersServicios us = new UsersServicios();
        AjaxMaker ajax = new AjaxMaker();
        Utils helper = new Utils();
        
        String accion = helper.depurarParametroString(request.getParameter(Parametros.ACCION));
        
        switch (accion)
        {
            case Parametros.ACCION_CONECTAR:
                String mail = helper.depurarParametroString(request.getParameter(Parametros.EMAIL));
                String pass = helper.depurarParametroString(request.getParameter(Parametros.PASS));
                AjaxResponse conectar = us.login(mail, pass);
                if (conectar.isSuccess())
                {
                    request.getSession().setAttribute(Constantes.SESSION_NOMBRE_USUARIO, mail);

                    String rango = us.getRango(mail);
                    request.getSession().setAttribute(Constantes.SESSION_RANGO_USUARIO, rango);
                }
                response.getWriter().print(ajax.parseResponse(conectar));
                break;

            case Parametros.ACCION_ACTIVARUSER:
                String codigo = helper.depurarParametroString(request.getParameter(Parametros.CODIGO));
                boolean userActivado = us.activar(codigo);
                String mensaje,mensaje2;
                if(userActivado)
                {
                    mensaje = Language.CUENTA_ACTIVADA;
                    mensaje2 = Language.CUENTA_ACTIVADA_2;
                }
                else
                {
                    mensaje = Language.YA_ACTIVADA;
                    mensaje2 = Language.CUENTA_ACTIVADA_2;
                }
                helper.mostrarPlantilla("/conectar.ftl", response.getWriter(),
                    new AbstractMap.SimpleEntry<>("mensaje", mensaje),
                    new AbstractMap.SimpleEntry<>("mensaje2", mensaje2)
                );

                break;
            default:
                helper.mostrarPlantilla("/conectar.ftl", response.getWriter());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
