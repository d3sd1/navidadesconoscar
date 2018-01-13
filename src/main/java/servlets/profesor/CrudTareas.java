package servlets.profesor;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.AbstractMap;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Tarea;
import servicios.ProfeServicios;
import utils.Constantes;
import utils.Utils;

@WebServlet(name = "CrudTareas", urlPatterns =
{
    "/panel/profesor/asignar_tarea"
})
public class CrudTareas extends HttpServlet
{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        ProfeServicios ps = new ProfeServicios();
        AjaxMaker ajax = new AjaxMaker();
        
        Utils helper = new Utils();
        String  objeto_json,
                profesor = request.getSession().getAttribute(Constantes.SESSION_NOMBRE_USUARIO).toString(),
                accion = helper.depurarParametroString(request.getParameter("accion"));
        
        /* Para peticiones ajax */
        AjaxResponse resp;
        Tarea t = new Tarea();
        LocalDate fecha_entrega = helper.depurarParametroLocalDate(request.getParameter("fecha_entrega"));
        t.getAsignatura().setId(helper.depurarParametroInt(request.getParameter("id_asignatura")));
        t.setNombre_tarea(helper.depurarParametroString(request.getParameter("nombre_tarea")));
        t.setFecha_entrega(Date.from(fecha_entrega.atStartOfDay().toInstant(ZoneOffset.UTC)));
        switch (accion)
        {
            case "insertar":
                resp = ps.addTarea(t, profesor);
                objeto_json = ajax.parseResponse(resp);
                response.getWriter().print(objeto_json);
                break;

            case "modificar":
                resp = ps.modTarea(t);

                objeto_json = ajax.parseResponse(resp);
                response.getWriter().print(objeto_json);
                break;

            default:
                helper.mostrarPlantilla("/panel/profesor/asignar_tareas.ftl", response.getWriter(),
                        new AbstractMap.SimpleEntry<>("tareas", ps.getAllTareas(profesor)),
                        new AbstractMap.SimpleEntry<>("asignaturas", ps.getAllAsignaturas())
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
