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
import model.Curso;
import servicios.AdminServicios;
import servlets.Conectar;
import utils.Constantes;

@WebServlet(name = "CrudCursos", urlPatterns
        = {
            "/panel/administrador/cursos"
        })
public class CrudCursos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Template temp = Configuration.getInstance().getFreeMarker().getTemplate("/panel/administrador/crud_asignaturas.ftl");
        HashMap root = new HashMap();
        root.put("rango", request.getSession().getAttribute(Constantes.SESSION_RANGO_USUARIO));

        AdminServicios as = new AdminServicios();
        AjaxMaker ajax = new AjaxMaker();

        String accion = request.getParameter("accion");
        Curso c = new Curso();
        String objeto_json;

        if (accion == null) {
            accion = "";
        }

        switch (accion) {
            case "insertar":
                AjaxResponse addCurso;
                try {
                    c.setNombre(request.getParameter("nombre"));
                    addCurso = as.addCurso(c);
                } catch (Exception ex) {
                    addCurso = ajax.errorResponse(0);
                }
                objeto_json = ajax.parseResponse(addCurso);
                response.getWriter().print(objeto_json);
                break;

            case "modificar":
                AjaxResponse modCurso;
                try {
                    c.setId(parseInt(request.getParameter("id")));
                    c.setNombre(request.getParameter("nombre"));
                    modCurso = as.modCurso(c);
                } catch (Exception ex) {
                    modCurso = ajax.errorResponse(0);
                }
                objeto_json = ajax.parseResponse(modCurso);
                response.getWriter().print(objeto_json);
                break;

            default:
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
