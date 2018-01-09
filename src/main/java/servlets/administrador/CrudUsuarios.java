package servlets.administrador;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import config.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import servicios.AdminServicios;
import servlets.Conectar;
import utils.Constantes;

@WebServlet(name = "CrudUsuarios", urlPatterns
        =
        {
            "/panel/administrador/usuarios"
        })
public class CrudUsuarios extends HttpServlet
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        Template temp = Configuration.getInstance().getFreeMarker().getTemplate("/panel/administrador/crud_usuarios.ftl");
        HashMap root = new HashMap();
        root.put("rango", request.getSession().getAttribute(Constantes.SESSION_RANGO_USUARIO));

        AdminServicios as = new AdminServicios();
        AjaxMaker ajax = new AjaxMaker();
        User u = new User();

        String accion = request.getParameter("accion");
        String objeto_json;

        if (accion == null)
        {
            accion = "";
        }

        switch (accion)
        {
            case "insertar":
                u.setEmail(request.getParameter("email"));
                u.setNombre(request.getParameter("nombre"));
                u.setId_permiso(parseInt(request.getParameter("tipo")));
                AjaxResponse insertarUser = as.addUser(u);
                objeto_json = ajax.parseResponse(insertarUser);
                response.getWriter().print(objeto_json);
                break;

            case "modificar":
                u.setId(parseInt(request.getParameter("id")));
                u.setEmail(request.getParameter("email"));
                u.setNombre(request.getParameter("nombre"));
                u.setId_permiso(parseInt(request.getParameter("tipo")));
                AjaxResponse modificarUser = as.modUser(u);
                objeto_json = ajax.parseResponse(modificarUser);
                response.getWriter().print(objeto_json);
                break;

            case "borrar":
                u.setId(parseInt(request.getParameter("id")));
                AjaxResponse delUser = as.delUser(u);
                objeto_json = ajax.parseResponse(delUser);
                response.getWriter().print(objeto_json);
                break;

            case "borrar2":
                u.setId(parseInt(request.getParameter("id")));
                AjaxResponse delUser2 = as.delUser2(u);
                objeto_json = ajax.parseResponse(delUser2);
                response.getWriter().print(objeto_json);
                break;

            default:
                try
                {
                    root.put("users", as.getAllUsers());
                    temp.process(root, response.getWriter());
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
