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
import model.Asignatura;
import model.Nota;
import model.User;
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
                AjaxResponse modNota;
                try
                {
                    if(request.getParameter("id_alumno") == null || request.getParameter("id_asignatura") == null || request.getParameter("nota") == null)
                    {
                        throw new NullPointerException();
                    }
                    else
                    {
                        User alumno = new User();
                        alumno.setId(parseInt(request.getParameter("id_alumno")));
                        n.setAlumno(alumno);
                        Asignatura asignatura = new Asignatura();
                        asignatura.setId(parseInt(request.getParameter("id_asignatura")));
                        n.setAsignatura(asignatura);
                        double nota = parseInt(request.getParameter("nota"));
                        if(nota < 0)
                        {
                            nota = 0;
                        }
                        else if(nota > 10)
                        {
                            nota = 10;
                        }
                        n.setNota(nota);
                        modNota = ps.modNota(n);
                        objeto_json = ajax.parseResponse(modNota);
                    }
                }
                catch(NumberFormatException | NullPointerException e)
                {
                    objeto_json = ajax.parseResponse(ajax.errorResponse());
                }
                
                response.getWriter().print(objeto_json);
                break;

            default:
                try {
                    String email = (String) request.getSession().getAttribute(Constantes.SESSION_NOMBRE_USUARIO);
                    root.put("notas", ps.getAllNotasCursosAlumnos(email));
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
