package servlets.administrador;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import config.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Curso;
import servicios.AdminServicios;
import utils.Constantes;
import utils.Parametros;
import utils.Utils;

@WebServlet(name = "CrudCursos", urlPatterns
        = {
            "/panel/administrador/cursos"
        })
public class CrudCursos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AdminServicios as = new AdminServicios();
        AjaxMaker ajax = new AjaxMaker();
        Utils helper = new Utils();

        /* Ajax */
        AjaxResponse resp;
        String accion = helper.depurarParametroString(request.getParameter(Parametros.ACCION));
        String nombre = helper.depurarParametroString(request.getParameter(Parametros.NOMBRE));
        int id = helper.depurarParametroInt(request.getParameter(Parametros.ID));

        switch (accion) {
            case Parametros.ACCION_INSERTAR:
                resp = as.addCurso(nombre);
                response.getWriter().print(ajax.parseResponse(resp));
                break;

            case Parametros.ACCION_MODIFICAR:
                resp = as.modCurso(id,nombre);
                response.getWriter().print(ajax.parseResponse(resp));
                break;

            default:
                helper.mostrarPlantilla("/panel/administrador/crud_cursos.ftl", response.getWriter(),
                    new AbstractMap.SimpleEntry<>("cursos", as.getAllCursos())
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
            throws ServletException, IOException {
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
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
