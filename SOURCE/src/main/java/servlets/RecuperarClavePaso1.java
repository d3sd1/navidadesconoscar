package servlets;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servicios.UsersServicios;
import utils.Parametros;
import utils.Utils;

@WebServlet(name = "RecuperarClavePaso1", urlPatterns =
{
    "/recuperarclave/paso1"
})
public class RecuperarClavePaso1 extends HttpServlet
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
            case Parametros.ACCION_MANDARMAIL:
                AjaxResponse mandarMail = us.reenviarMailActivacion(helper.depurarParametroString(request.getParameter(Parametros.EMAIL)));
                String objeto_json = ajax.parseResponse(mandarMail);
                response.getWriter().print(objeto_json);
                break;

            default:
                helper.mostrarPlantilla("/recuperar_clave_paso_1.ftl", response.getWriter());
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
    private static final Logger LOG = Logger.getLogger(RecuperarClavePaso1.class.getName());

}
