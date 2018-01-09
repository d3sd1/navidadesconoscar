/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Nota;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Miguel
 */
public class ProfeDAO {
    
    private final String queryGetAllNotas = "SELECT u.id, u.nombre, a.id AS id_asignatura, a.nombre AS nombre_asignatura, aa.nota "
            + "FROM users u JOIN alumnos_asignaturas aa ON u.id = aa.id_alumno"
            + "JOIN asignaturas a ON a.id = aa.id_asignatura"
            + "JOIN profesores_asignaturas pa ON pa.id_asignatura = aa.id_asignatura"
            + "WHERE pa.id_profesor = ?";
    
    public List<Nota> getAllNotas() {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<Nota> notas = jtm.query(queryGetAllNotas, new BeanPropertyRowMapper(Nota.class));

        return notas;
    }
}
