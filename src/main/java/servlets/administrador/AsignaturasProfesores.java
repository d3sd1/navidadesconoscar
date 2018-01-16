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
import servicios.AdminServicios;
import utils.Parametros;
import utils.Utils;

@WebServlet(name = "AsignaturasProfesores", urlPatterns
        = {
            "/panel/administrador/asignaturas_profesores"
        })
public class AsignaturasProfesores extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Utils helper = new Utils();
        AdminServicios as = new AdminServicios();
        AjaxMaker ajax = new AjaxMaker();

        String accion = helper.depurarParametroString(request.getParameter(Parametros.ACCION));

        switch (accion) {
            case Parametros.ACCION_ASIGNAR:
                int id_profesor = helper.depurarParametroInt(request.getParameter(Parametros.ID));
                String asignaturas = helper.depurarParametroString(request.getParameter(Parametros.ASIGNATURAS));
                AjaxResponse asignarProfeAsig = as.asignarProfeAsig(id_profesor, asignaturas);
                
                response.getWriter().print(ajax.parseResponse(asignarProfeAsig));
                break;

            default:
                helper.mostrarPlantilla("/panel/administrador/asignaturas_profesores.ftl", response.getWriter(),
                    new AbstractMap.SimpleEntry<>("profesores", as.getAllProfes()),
                    new AbstractMap.SimpleEntry<>("asignaturas", as.getAllAsignaturas()),
                    new AbstractMap.SimpleEntry<>("asignaturas_profesores", as.getAsigProfesor())
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
