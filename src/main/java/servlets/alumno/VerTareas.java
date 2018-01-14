package servlets.alumno;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import java.io.IOException;
import java.util.AbstractMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Tarea;
import servicios.AlumnosServicios;
import utils.Constantes;
import utils.Utils;

@WebServlet(name = "VerTareas", urlPatterns = {"/panel/alumno/tareas"})
public class VerTareas extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Utils helper = new Utils();
        AlumnosServicios as = new AlumnosServicios();
        AjaxMaker ajax = new AjaxMaker();

        String alumno = request.getSession().getAttribute(Constantes.SESSION_NOMBRE_USUARIO).toString();
        String accion = helper.depurarParametroString(request.getParameter("accion"));

        Tarea t = new Tarea();
        t.setId_tarea(helper.depurarParametroInt(request.getParameter("id_tarea")));
        
        AjaxResponse resp;

        switch (accion) {
            case "completar":
                resp = as.completarTarea(t, alumno);
                response.getWriter().print(ajax.parseResponse(resp));
                break;

            default:
                helper.mostrarPlantilla("/panel/alumno/tareas.ftl", response.getWriter(),
                        new AbstractMap.SimpleEntry<>("tareas", as.getAllTareas(alumno))
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
