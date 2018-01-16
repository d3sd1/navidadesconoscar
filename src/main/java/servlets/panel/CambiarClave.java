package servlets.panel;

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
import utils.Parametros;
import utils.Utils;

@WebServlet(name = "CambiarClave", urlPatterns
        =
        {
            "/panel/cambiar_clave"
        })
public class CambiarClave extends HttpServlet
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {


        UsersServicios us = new UsersServicios();
        AjaxMaker ajax = new AjaxMaker();
        Utils helper = new Utils();

        String accion = helper.depurarParametroString(request.getParameter(Parametros.ACCION));

        switch (accion)
        {
            case Parametros.CAMBIARPASS:
                String passActual = helper.depurarParametroString(request.getParameter(Parametros.CAMBIARPASS_ACTUAL));
                String nuevaPass = helper.depurarParametroString( request.getParameter(Parametros.CAMBIARPASS_NUEVA));
                String email = (String) request.getSession().getAttribute(Constantes.SESSION_NOMBRE_USUARIO);
                AjaxResponse cambiarPass = us.cambiarPass(passActual, nuevaPass, email);
                response.getWriter().print(ajax.parseResponse(cambiarPass));
                break;

            default:
            helper.mostrarPlantilla("/panel/cambiar_clave.ftl", response.getWriter(),
                new AbstractMap.SimpleEntry<>("rango", request.getSession().getAttribute(Constantes.SESSION_RANGO_USUARIO))
            );
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
