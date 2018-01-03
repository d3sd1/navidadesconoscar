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
public class AlumnosDAO {
    
    private final String queryGetAllNotas = "SELECT * FROM alumnos_asignaturas aa JOIN users u ON aa.id_alumno = u.id WHERE u.email = ?";
    
    
    public List<Nota> getAllNotas(String email) {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<Nota> alumnos = jtm.query(queryGetAllNotas, new BeanPropertyRowMapper(Nota.class), email);

        return alumnos;
    }
}
