package servlets.administrador;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import java.io.IOException;
import java.util.AbstractMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import servicios.AdminServicios;
import utils.Parametros;
import utils.Utils;

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

        AdminServicios as = new AdminServicios();
        AjaxMaker ajax = new AjaxMaker();
        Utils helper = new Utils();

        /* Ajax */
        AjaxResponse resp;
        String accion = helper.depurarParametroString(request.getParameter(Parametros.ACCION));
        String nombre = helper.depurarParametroString(request.getParameter(Parametros.NOMBRE));
        String email = helper.depurarParametroString(request.getParameter(Parametros.EMAIL));
        int id = helper.depurarParametroInt(request.getParameter(Parametros.ID));
        int id_permiso = helper.depurarParametroInt(request.getParameter(Parametros.TIPO));
        
        switch (accion)
        {
            case Parametros.ACCION_INSERTAR:
                resp = as.addUser(email,nombre,id_permiso);
                response.getWriter().print(ajax.parseResponse(resp));
                break;

            case Parametros.ACCION_MODIFICAR:
                resp = as.modUser(id,email,nombre,id_permiso);
                response.getWriter().print(ajax.parseResponse(resp));
                break;

            case Parametros.ACCION_BORRAR:
                resp = as.delUser(id);
                response.getWriter().print(ajax.parseResponse(resp));
                break;

            default:
                helper.mostrarPlantilla("/panel/administrador/crud_usuarios.ftl", response.getWriter(),
                    new AbstractMap.SimpleEntry<>("users", as.getAllUsers())
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
