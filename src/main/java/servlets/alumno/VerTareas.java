/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets.alumno;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import config.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.PrintWriter;
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
import servicios.AlumnosServicios;
import servlets.Conectar;
import utils.Constantes;

/**
 *
 * @author Miguel
 */
@WebServlet(name = "VerTareas", urlPatterns = {"/panel/usuario/tareas"})
public class VerTareas extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Template temp = Configuration.getInstance().getFreeMarker().getTemplate("/panel/alumno/tareas.ftl");

        HashMap root = new HashMap();
        root.put("rango", request.getSession().getAttribute(Constantes.SESSION_RANGO_USUARIO));

        AlumnosServicios as = new AlumnosServicios();
        AjaxMaker ajax = new AjaxMaker();
        String objeto_json;
        String accion = request.getParameter("accion");

        if (accion == null) {
            accion = "";
        }

        switch (accion) {
            case "completar":
                AjaxResponse completarTarea = null;
                
                try {
                    Tarea t = new Tarea();
                    t.setId_tarea(Integer.parseInt(request.getParameter("id_tarea")));
                    String email = (String) request.getSession().getAttribute(Constantes.SESSION_NOMBRE_USUARIO);
                    completarTarea = as.completarTarea(t, email);
                } catch (NumberFormatException ex) {
                    completarTarea = ajax.errorResponse(0);
                }
                
                objeto_json = ajax.parseResponse(completarTarea);
                response.getWriter().print(objeto_json);
                break;

            default:
                try {
                    root.put("tareas", as.getAllTareas((String) request.getSession().getAttribute(Constantes.SESSION_NOMBRE_USUARIO)));
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
