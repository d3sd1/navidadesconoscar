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
import servicios.AdminServicios;
import servlets.Conectar;
import utils.Constantes;

@WebServlet(name = "AsignaturasUsuarios", urlPatterns
        = {
            "/panel/administrador/asignaturas_usuarios"
        })
public class AsignaturasUsuarios extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Template temp = Configuration.getInstance().getFreeMarker().getTemplate("/panel/administrador/asignaturas_alumnos.ftl");
        HashMap root = new HashMap();
        root.put("rango", request.getSession().getAttribute(Constantes.SESSION_RANGO_USUARIO));

        AdminServicios as = new AdminServicios();
        AjaxMaker ajax = new AjaxMaker();

        String accion = request.getParameter("accion");

        String objeto_json;
        int id_alumno = 0;
        String asignaturas = "";

        if (accion == null) {
            accion = "";
        }

        switch (accion) {
            case "asignar":
                AjaxResponse asignarAlumAsig;
                try {
                    id_alumno = parseInt(request.getParameter("id"));
                    asignaturas = request.getParameter("asignaturas");
                    asignarAlumAsig = as.asignarAlumAsig(id_alumno, asignaturas);
                } catch (Exception ex) {
                    asignarAlumAsig = ajax.errorResponse(0);
                }
                objeto_json = ajax.parseResponse(asignarAlumAsig);
                response.getWriter().print(objeto_json);
                break;
            default:
                try {
                    root.put("alumnos", as.getAllAlumnos());
                    root.put("asignaturas", as.getAllAsignaturas());
                    root.put("asignaturas_alumnos", as.getAsigAlumno());
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
