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
import model.Asignatura;
import servicios.AdminServicios;
import servlets.Conectar;
import utils.Constantes;

@WebServlet(name = "CrudAsignaturas", urlPatterns
        = {
            "/panel/administrador/asignaturas"
        })
public class CrudAsignaturas extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Template temp = Configuration.getInstance().getFreeMarker().getTemplate("/panel/administrador/crud_asignaturas.ftl");
        HashMap root = new HashMap();
        root.put("rango", request.getSession().getAttribute(Constantes.SESSION_RANGO_USUARIO));

        AdminServicios as = new AdminServicios();
        AjaxMaker ajax = new AjaxMaker();

        String accion = request.getParameter("accion");
        Asignatura a = new Asignatura();
        String objeto_json;

        if (accion == null) {
            accion = "";
        }

        switch (accion) {
            case "insertar":
                AjaxResponse addAsig;
                try {
                    a.setNombre(request.getParameter("nombre"));
                    a.setId_curso(parseInt(request.getParameter("id_curso")));
                    addAsig = as.addAsig(a);
                } catch (Exception ex) {
                    addAsig = ajax.errorResponse(0);
                }
                objeto_json = ajax.parseResponse(addAsig);
                response.getWriter().print(objeto_json);
                break;

            case "modificar":
                AjaxResponse modAsig;
                try {
                    a.setId(parseInt(request.getParameter("id")));
                    a.setNombre(request.getParameter("nombre"));
                    a.setId_curso(parseInt(request.getParameter("id_curso")));
                    modAsig = as.modAsig(a);
                } catch (Exception ex) {
                    modAsig = ajax.errorResponse(0);
                }
                objeto_json = ajax.parseResponse(modAsig);
                response.getWriter().print(objeto_json);
                break;

            case "borrar":
                AjaxResponse delAsig2;
                try {
                    a.setId(parseInt(request.getParameter("id")));
                    delAsig2 = as.delAsig2(a);
                } catch (Exception ex) {
                    delAsig2 = ajax.errorResponse(0);
                }
                objeto_json = ajax.parseResponse(delAsig2);
                response.getWriter().print(objeto_json);
                break;

            default:
                root.put("asignaturas", as.getAllAsignaturas());
                root.put("cursos", as.getAllCursos());
                try {
                    temp.process(root, response.getWriter());
                } catch (TemplateException ex) {
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
