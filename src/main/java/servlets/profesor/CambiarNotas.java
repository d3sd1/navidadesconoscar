package servlets.profesor;

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
import model.Nota;
import servicios.ProfeServicios;
import servlets.Conectar;
import utils.Constantes;

@WebServlet(name = "CambiarNotas", urlPatterns
        = {
            "/panel/profesor/modificar_notas"
        })
public class CambiarNotas extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Template temp = Configuration.getInstance().getFreeMarker().getTemplate("/panel/profesor/notas_cambiar.ftl");
        HashMap root = new HashMap();
        root.put("rango", request.getSession().getAttribute(Constantes.SESSION_RANGO_USUARIO));

        String accion = request.getParameter("accion");
        ProfeServicios ps = new ProfeServicios();
        AjaxMaker ajax = new AjaxMaker();
        String objeto_json;
        
        if (accion == null) {
            accion = "";
        }

        switch (accion) {
            case "modificar":
                Nota n = new Nota();
                n.setId_alumno(parseInt(request.getParameter("idAlumno")));
                n.setId_asignatura(parseInt(request.getParameter("idAsignatura")));
                n.setNota(parseInt(request.getParameter("nota")));
                AjaxResponse modNota = ps.modNota(n);
                objeto_json = ajax.parseResponse(modNota);
                response.getWriter().print(objeto_json);
                break;

            default:
                try {
                    String email = (String) request.getSession().getAttribute(Constantes.SESSION_NOMBRE_USUARIO);
                    root.put("notas", ps.getAllNotas(email));
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
