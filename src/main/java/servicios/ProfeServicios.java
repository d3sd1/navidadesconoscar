package servicios;

import ajax.AjaxMaker;
import ajax.AjaxResponse;
import dao.ProfeDAO;
import java.util.HashMap;
import java.util.List;
import model.Nota;
import utils.Constantes;

public class ProfeServicios {

    private final AjaxMaker ajax = new AjaxMaker();

    public List<Nota> getAllNotas(String email) {
        ProfeDAO dao = new ProfeDAO();
        int id = dao.getId(email);
        return dao.getAllNotas(id);
    }

    public AjaxResponse modNota(Nota n) {
        ProfeDAO dao = new ProfeDAO();
        AjaxResponse returnme;
        boolean notaCambiada = dao.modNota(n);

        if (notaCambiada) {
            HashMap<String, String> datos = new HashMap<>();
            datos.put(Constantes.PARAMETRO_NOTA, String.valueOf(n.getNota()));
            returnme = ajax.successResponse(datos);
        } else {
            returnme = ajax.errorResponse(16);
        }
        
        return returnme;
    }
    
    public List<Nota> getAllNotasCursos(String email) {
        ProfeDAO dao = new ProfeDAO();
        int id = dao.getId(email);
        return dao.getAllNotasCursos(id);
    }
}
