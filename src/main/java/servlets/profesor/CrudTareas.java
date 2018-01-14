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
        Utils helper = new Utils();
        String  profesor = request.getSession().getAttribute(Constantes.SESSION_NOMBRE_USUARIO).toString(),
                accion = helper.depurarParametroString(request.getParameter(Constantes.PARAMETRO_ACCION));
        
        /* Para peticiones ajax */
        AjaxResponse resp;
        Tarea t = new Tarea();
        AjaxMaker ajax = new AjaxMaker();
        LocalDate fecha_entrega = helper.depurarParametroLocalDate(request.getParameter(Constantes.PARAMETRO_FECHA_ENTREGA));
        t.getAsignatura().setId(helper.depurarParametroInt(request.getParameter(Constantes.PARAMETRO_ID_ASIGNATURA)));
        t.setNombre_tarea(helper.depurarParametroString(request.getParameter(Constantes.PARAMETRO_NOMBRE_TAREA)));
        t.setFecha_entrega(Date.from(fecha_entrega.atStartOfDay().toInstant(ZoneOffset.UTC)));
        t.setId_tarea(helper.depurarParametroInt(request.getParameter(Constantes.PARAMETRO_ID_TAREA)));
        switch (accion)
        {
            case "insertar":
                resp = ps.addTarea(t, profesor);
                response.getWriter().print(ajax.parseResponse(resp));
                break;

            case "modificar":
                resp = ps.modTarea(t);
                response.getWriter().print(ajax.parseResponse(resp));
                break;
                
            case "eliminar":
                resp = ps.delTarea(t);
                response.getWriter().print(ajax.parseResponse(resp));
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
