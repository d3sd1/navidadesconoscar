package servlets;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import config.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servicios.UsersServicios;
import utils.Constantes;
import utils.Language;

@WebServlet(name = "Conectar", urlPatterns =
{
    "/conectar"
})
public class Conectar extends HttpServlet
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

        String accion = request.getParameter("accion");
        if (accion == null)
        {
            accion = "";
        }

        UsersServicios us = new UsersServicios();
        AjaxMaker ajax = new AjaxMaker();
        Template temp = Configuration.getInstance().getFreeMarker().getTemplate("/conectar.ftl");

        switch (accion)
        {
            case "conectar":
                String mail = request.getParameter("mail");
                String pass = request.getParameter("pass");
                AjaxResponse conectar = us.login(mail, pass);
                if (conectar.isSuccess())
                {
                    request.getSession().setAttribute(Constantes.SESSION_NOMBRE_USUARIO, mail);

                    String rango = us.getRango(mail);
                    request.getSession().setAttribute(Constantes.SESSION_RANGO_USUARIO, rango);
                }
                String objeto_json = ajax.parseResponse(conectar);
                response.getWriter().print(objeto_json);
                break;

            case "activarUsuario":
                String codigo = request.getParameter("codigo");
                int userActivado = us.activar(codigo);
                HashMap root = new HashMap();

                switch (userActivado)
                {
                    case 1:
                        root.put("mensaje", Language.CUENTA_ACTIVADA);
                        root.put("mensaje2", Language.CUENTA_ACTIVADA_2);
                        break;
                    case 2:
                        root.put("mensaje", Language.YA_ACTIVADA);
                        root.put("mensaje2", Language.CUENTA_ACTIVADA_2);
                        break;
                    case -1:
                        root.put("mensaje", Language.ERROR_ACTIVAR);
                        root.put("mensaje2", Language.ERROR_ACTIVAR_2);
                        break;
                }

                try
                {
                    temp.process(root, response.getWriter());
                }
                catch (TemplateException ex)
                {
                    Logger.getLogger(Conectar.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            default:
                try
                {
                    temp.process(null, response.getWriter());
                }
                catch (TemplateException ex)
                {
                    Logger.getLogger(Conectar.class.getName()).log(Level.SEVERE, null, ex);
                }
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
