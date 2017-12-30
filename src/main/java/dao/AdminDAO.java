/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Asignatura;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Miguel
 */
public class AdminDAO {

    private final String queryGetAllAsignaturas = "SELECT * FROM asignaturas";
    private final String queryAddAsig = "INSERT INTO asignaturas (nombre,id_curso) VALUES (?,?)";
    private final String queryModAsig = "UPDATE asignaturas SET nombre = ?, id_curso = ? WHERE id = ?";
    private final String queryDelAsig = "DELETE FROM asignaturas WHERE id = ?";

    public List<Asignatura> getAllAsignaturas() {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<Asignatura> asignaturas = jtm.query(queryGetAllAsignaturas, new BeanPropertyRowMapper(Asignatura.class));

        return asignaturas;
    }

    public Asignatura addAsig(Asignatura a) {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(queryAddAsig, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, a.getNombre());
            stmt.setInt(2, a.getId_curso());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                a.setId(rs.getInt(1));
            }

        } catch (Exception ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            a = null;
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return a;
    }

    public Asignatura modAsig(Asignatura a) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            if (!(jtm.update(queryModAsig, a.getNombre(), a.getId_curso(), a.getId()) > 0)) {
                a = null;
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            a = null;
        }
        return a;
    }
    
    public boolean delAsig(Asignatura a){
        boolean borrado = false;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            if (jtm.update(queryDelAsig, a.getId()) > 0) {
                borrado = true;
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return borrado;
    }
}
