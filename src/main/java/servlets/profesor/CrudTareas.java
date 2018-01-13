package servlets.profesor;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import config.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Tarea;
import servicios.ProfeServicios;
import servlets.Conectar;
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

        Template temp = Configuration.getInstance().getFreeMarker().getTemplate("/panel/profesor/asignar_tareas.ftl");
        HashMap root = new HashMap();
        root.put("rango", request.getSession().getAttribute(Constantes.SESSION_RANGO_USUARIO));

        ProfeServicios ps = new ProfeServicios();
        AjaxMaker ajax = new AjaxMaker();
        Tarea t = new Tarea();
        String objeto_json;
        String accion = request.getParameter("accion");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        if (accion == null)
        {
            accion = "";
        }
        Utils helper = new Utils();
        switch (accion)
        {
            case "insertar":
                AjaxResponse addTarea;

                try
                {
                    String cadenaFecha = helper.depurarParametroString(request.getParameter("fecha_entrega"));
                    LocalDate fecha_entrega = LocalDate.parse(cadenaFecha, dtf);
                    t.getAsignatura().setId(Integer.parseInt(request.getParameter("id_asignatura")));
                    t.setNombre_tarea(request.getParameter("nombre_tarea"));
                    t.setFecha_entrega(Date.from(fecha_entrega.atStartOfDay().toInstant(ZoneOffset.UTC)));
                    addTarea = ps.addTarea(t, (String) request.getSession().getAttribute(Constantes.SESSION_NOMBRE_USUARIO));
                }
                catch (NumberFormatException ex)
                {
                    addTarea = ajax.errorResponse(0);
                }

                objeto_json = ajax.parseResponse(addTarea);
                response.getWriter().print(objeto_json);
                break;

            case "modificar":
                AjaxResponse modTarea;

                try
                {
                    String cadenaFecha = request.getParameter("fecha_entrega");
                    LocalDate fecha_entrega = LocalDate.parse(cadenaFecha, dtf);
                    t.setId_tarea(Integer.parseInt(request.getParameter("id_tarea")));
                    t.setNombre_tarea(request.getParameter("nombre_tarea"));
                    t.setFecha_entrega(Date.from(fecha_entrega.atStartOfDay().toInstant(ZoneOffset.UTC)));
                    modTarea = ps.modTarea(t);
                }
                catch (NumberFormatException ex)
                {
                    modTarea = ajax.errorResponse(0);
                }

                objeto_json = ajax.parseResponse(modTarea);
                response.getWriter().print(objeto_json);
                break;

            default:
                try
                {
                    root.put("tareas", ps.getAllTareas((String) request.getSession().getAttribute(Constantes.SESSION_NOMBRE_USUARIO)));
                    root.put("asignaturas", ps.getAllAsignaturas());
                    temp.process(root, response.getWriter());
                }
                catch (TemplateException ex)
                {
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
