package servlets.profesor;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import java.io.IOException;
import java.util.AbstractMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servicios.ProfeServicios;
import utils.Constantes;
import utils.Parametros;
import utils.Utils;

@WebServlet(name = "CambiarNotas", urlPatterns
        = {
            "/panel/profesor/modificar_notas"
        })
public class CambiarNotas extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Utils helper = new Utils();
        String accion = helper.depurarParametroString(request.getParameter(Parametros.ACCION));
        String profe = request.getSession().getAttribute(Constantes.SESSION_NOMBRE_USUARIO).toString();
        ProfeServicios ps = new ProfeServicios();
        AjaxMaker ajax = new AjaxMaker();
        
        switch (accion) {
            case Parametros.ACCION_MODIFICAR:
                
                AjaxResponse modNota;
                int id_alumno = helper.depurarParametroInt(request.getParameter(Parametros.ID_ALUMNO));
                int id_asignatura = helper.depurarParametroInt(request.getParameter(Parametros.ID_ASIGNATURA));
                double nota = helper.depurarParametroDouble(request.getParameter(Parametros.NOTA));
                
                modNota = ps.modNota(id_alumno,id_asignatura,nota);
                
                response.getWriter().print(ajax.parseResponse(modNota));
                break;

            default:
                helper.mostrarPlantilla("/panel/profesor/notas_cambiar.ftl", response.getWriter(),
                    new AbstractMap.SimpleEntry<>("notas", ps.getAllNotas(profe))
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
