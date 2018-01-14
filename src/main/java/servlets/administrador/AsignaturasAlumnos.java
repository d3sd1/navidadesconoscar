package servlets.administrador;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import ajax.PaginateResponse;
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

@WebServlet(name = "AsignaturasUsuarios", urlPatterns
        = {
            "/panel/administrador/asignaturas_usuarios"
        })
public class AsignaturasAlumnos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AdminServicios as = new AdminServicios();
        AjaxMaker ajax = new AjaxMaker();
        Utils helper = new Utils();

        String accion = helper.depurarParametroString(request.getParameter(Parametros.ACCION));

        switch (accion) {
            case Parametros.ACCION_ASIGNAR:
                int id_alumno = helper.depurarParametroInt(request.getParameter(Parametros.ID));
                String asignaturas = helper.depurarParametroString(request.getParameter(Parametros.ASIGNATURAS));
                AjaxResponse asignarAlumAsig = as.asignarAlumAsig(id_alumno, asignaturas);
                response.getWriter().print(ajax.parseResponse(asignarAlumAsig));
                break;
            case Parametros.ACCION_GETALUMNOS:
                int start = helper.depurarParametroInt(request.getParameter(Parametros.INICIO));
                int length = helper.depurarParametroInt(request.getParameter(Parametros.CANTIDAD));
                PaginateResponse resp = ajax.paginateResponse(as.getAlumnos(start,length), as.getTotalAlumnos());
                response.getWriter().print(ajax.parseResponse(resp));
            break;
            default:
                helper.mostrarPlantilla("/panel/administrador/asignaturas_alumnos.ftl", response.getWriter(),
                    new AbstractMap.SimpleEntry<>("asignaturas", as.getAllAsignaturas()),
                    new AbstractMap.SimpleEntry<>("asignaturas_alumnos", as.getAsigAlumno())
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
