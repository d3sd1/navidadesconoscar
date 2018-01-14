package dao;

import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Asignatura;
import model.Curso;
import model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class AdminDAO {

    private final String queryGetAllAsignaturas = "SELECT * FROM asignaturas";
    private final String queryGetAllUsers = "SELECT u.id, u.email, u.nombre, up.id_permiso FROM users u JOIN users_permisos up ON u.id = up.id_user";
    private final String queryAddAsig = "INSERT INTO asignaturas (nombre,id_curso) VALUES (?,?)";
    private final String queryModAsig = "UPDATE asignaturas SET nombre = ?, id_curso = ? WHERE id = ?";
    private final String queryDelAsig = "DELETE FROM asignaturas WHERE id = ?";
    private final String queryDelNota = "DELETE FROM alumnos_asignaturas WHERE id_asignatura = ?";
    private final String queryDelAsigProfe = "DELETE FROM profesores_asignaturas WHERE id_asignatura = ?";
    private final String queryGetAllAlumnos = "SELECT * FROM users u JOIN users_permisos up ON u.id = up.id_user WHERE id_permiso = 3";
    private final String queryGetAllProfes = "SELECT * FROM users u JOIN users_permisos up ON u.id = up.id_user WHERE id_permiso = 2";
    private final String queryAsignarProfeAsig = "INSERT INTO profesores_asignaturas (id_profesor, id_asignatura) VALUES (?,?)";
    private final String queryEliminarProfeAsig = "DELETE FROM profesores_asignaturas WHERE id_profesor = ?";
    private final String queryAsignarAlumAsig = "INSERT INTO alumnos_asignaturas (id_alumno, id_asignatura) VALUES (?,?)";
    private final String queryEliminarAlumAsig = "DELETE FROM alumnos_asignaturas WHERE id_alumno = ?";
    private final String queryGetAllAlumAsig = "SELECT * FROM alumnos_asignaturas";
    private final String queryGetAllProfeAsig = "SELECT * FROM profesores_asignaturas";
    private final String queryComprobarEmail = "SELECT email FROM users WHERE email = ?";
    private final String queryRegistrarUser = "INSERT INTO users (email,clave,activo,codigo_activacion,nombre) VALUES (?,?,0,?,?)";
    private final String queryRegistrarUserPermisos = "INSERT INTO users_permisos (id_user,id_permiso) VALUES (?,?)";
    private final String queryModificarUser = "UPDATE users SET email = ?, nombre = ? WHERE id = ?";
    private final String queryModificarUserPermisos = "UPDATE users_permisos SET id_permiso = ? WHERE id_user = ?";
    private final String queryGetPermiso = "SELECT id_permiso FROM users_permisos WHERE id_user = ?";
    private final String queryDelUser = "DELETE FROM users WHERE id = ?";
    private final String queryDelUserPermiso = "DELETE FROM users_permisos WHERE id_user = ?";
    private final String queryAddCurso = "INSERT INTO cursos (nombre) VALUES (?)";
    private final String queryModCurso = "UPDATE cursos SET nombre = ? WHERE id = ?";
    private final String queryGetAllCursos = "SELECT * FROM cursos";
    private final String queryDelTarea = "DELETE FROM tareas WHERE id_asignatura = ?";
    private final String queryDelTareaAlumno = "DELETE FROM tareas_alumnos "
            + "WHERE id_tarea IN "
            + "(SELECT id_tarea FROM tareas WHERE id_asignatura = ?)";

    public List<Asignatura> getAllAsignaturas() {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<Asignatura> asignaturas = jtm.query(queryGetAllAsignaturas, new BeanPropertyRowMapper(Asignatura.class));

        return asignaturas;
    }

    public List<User> getAllUsers() {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<User> users = jtm.query(queryGetAllUsers, new BeanPropertyRowMapper(User.class));

        return users;
    }

    public List<Asignatura> getAsignaturasAlumno() {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List asignaturas = jtm.queryForList(queryGetAllAlumAsig);

        return asignaturas;
    }

    public List<Asignatura> getAsignaturasProfesor() {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List asignaturas = jtm.queryForList(queryGetAllProfeAsig);

        return asignaturas;
    }

    public Asignatura addAsig(Asignatura a) {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(queryAddAsig, Statement.RETURN_GENERATED_KEYS);
            System.out.println("NOMBRE "+  a.getNombre() + " curso " + a.getId_curso());
            stmt.setString(1, a.getNombre());
            stmt.setInt(2, a.getId_curso());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                a.setId(rs.getInt(1));
            }

            stmt.close();
        } catch (Exception ex) {
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
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            a = null;
        }
        return a;
    }

    public int delAsig(Asignatura a) {
        int borrado = -1;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            if (jtm.update(queryDelAsig, a.getId()) > 0) {
                borrado = 1;
            }
        } catch (DataIntegrityViolationException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            borrado = 0;
        } catch (DataAccessException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            borrado = -1;
        }
        return borrado;
    }

    public boolean delAsig2(Asignatura a) {
        Connection con = null;
        boolean borrado = false;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(queryDelNota);
            stmt.setInt(1, a.getId());
            stmt.executeUpdate();

            stmt = con.prepareStatement(queryDelAsigProfe);
            stmt.setInt(1, a.getId());
            stmt.executeUpdate();
            
            stmt = con.prepareStatement(queryDelTareaAlumno);
            stmt.setInt(1, a.getId());
            stmt.executeUpdate();
            
            stmt = con.prepareStatement(queryDelTarea);
            stmt.setInt(1, a.getId());
            stmt.executeUpdate();

            stmt = con.prepareStatement(queryDelAsig);
            stmt.setInt(1, a.getId());
            stmt.executeUpdate();

            con.commit();
            borrado = true;

            stmt.close();
        } catch (Exception ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return borrado;
    }

    public List<User> getAllAlumnos() {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<User> alumnos = jtm.query(queryGetAllAlumnos, new BeanPropertyRowMapper(User.class));
        return alumnos;
    }

    public List<User> getAllProfes() {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<User> profesores = jtm.query(queryGetAllProfes, new BeanPropertyRowMapper(User.class));

        return profesores;
    }

    public boolean asignarProfeAsig(int id_profe, String[] id_asignaturas) {
        boolean asignado = false;
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(queryAsignarProfeAsig);

            for (int i = 0; i < id_asignaturas.length; i++) {
                stmt.setInt(1, id_profe);
                stmt.setInt(2, parseInt(id_asignaturas[i]));
                stmt.executeUpdate();
            }

            asignado = true;
            con.commit();
            stmt.close();

        } catch (Exception ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return asignado;
    }

    public boolean asignarInsertandoAlumAsig(int id_alumno, String[] id_asignaturas) {
        boolean asignado = false;
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(queryAsignarAlumAsig);
            for (int i = 0; i < id_asignaturas.length; i++) {
                stmt.setInt(1, id_alumno);
                stmt.setInt(2, parseInt(id_asignaturas[i]));
                stmt.executeUpdate();
            }

            asignado = true;
            con.commit();
            stmt.close();

        } catch (Exception ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return asignado;
    }

    public boolean asignarInsertandoProfeAsig(int id_profe, String[] id_asignaturas) {
        boolean asignado = false;
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(queryAsignarProfeAsig);
            for (int i = 0; i < id_asignaturas.length; i++) {
                stmt.setInt(1, id_profe);
                stmt.setInt(2, parseInt(id_asignaturas[i]));
                stmt.executeUpdate();
            }

            asignado = true;
            con.commit();
            stmt.close();

        } catch (Exception ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return asignado;
    }

    public boolean eliminarAlumAsig(int id_alumno) {
        boolean eliminado = false;
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(queryEliminarAlumAsig);

            stmt.setInt(1, id_alumno);
            stmt.executeUpdate();

            eliminado = true;
            con.commit();
            stmt.close();
        } catch (Exception ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return eliminado;
    }

    public boolean eliminarProfeAsig(int id_profe) {
        boolean eliminado = false;
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(queryEliminarProfeAsig);

            stmt.setInt(1, id_profe);
            stmt.executeUpdate();

            eliminado = true;
            con.commit();
            stmt.close();
        } catch (Exception ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return eliminado;
    }

    public boolean comprobarEmail(String email) {
        boolean existe = false;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            String emailDB = jtm.queryForObject(queryComprobarEmail, String.class, email);

            if (emailDB != null) {
                existe = true;
            }
        } catch (DataAccessException ex) {
        }
        return existe;
    }

    public User addUser(User u) {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(queryRegistrarUser, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, u.getEmail());
            stmt.setString(2, u.getClave());
            stmt.setString(3, u.getCodigoActivacion());
            stmt.setString(4, u.getNombre());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                u.setId(rs.getInt(1));
            }

            stmt = con.prepareStatement(queryRegistrarUserPermisos);
            stmt.setInt(1, u.getId());
            stmt.setInt(2, u.getId_permiso());
            stmt.executeUpdate();

            con.commit();

            stmt.close();
        } catch (Exception ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            u = null;
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return u;
    }

    public boolean modUser(User u, int tipo) {
        Connection con = null;
        boolean userModificado = false;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(queryModificarUser);
            stmt.setString(1, u.getEmail());
            stmt.setString(2, u.getNombre());
            stmt.setInt(3, u.getId());
            stmt.executeUpdate();

            stmt = con.prepareStatement(queryModificarUserPermisos);
            stmt.setInt(1, u.getId_permiso());
            stmt.setInt(2, u.getId());
            stmt.executeUpdate();

            stmt.executeUpdate();

            con.commit();
            userModificado = true;
            stmt.close();
        } catch (Exception ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
            userModificado = false;
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return userModificado;
    }
    
    
    public int getPermiso(int id) {
        int permiso = 0;
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            permiso = jtm.queryForObject(queryGetPermiso, int.class, id);

        } catch (DataAccessException ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return permiso;
    }
    
    public int delUser(User u){
        Connection con = null;
        int borrado = -1;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            
            PreparedStatement stmt = con.prepareStatement(queryDelUserPermiso);
            stmt.setInt(1, u.getId());
            stmt.executeUpdate();

            stmt = con.prepareStatement(queryDelUser);
            stmt.setInt(1, u.getId());
            stmt.executeUpdate();
            
            borrado = 1;
            
            con.commit();
            stmt.close();
            
        } catch (SQLIntegrityConstraintViolationException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            
            borrado = 0;
            
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            borrado = -1;
            
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return borrado;
    }
    
    public boolean delUser2(User u) {
        Connection con = null;
        boolean borrado = false;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement stmt = con.prepareStatement(queryEliminarAlumAsig);
            stmt.setInt(1, u.getId());
            stmt.executeUpdate();

            stmt = con.prepareStatement(queryEliminarProfeAsig);
            stmt.setInt(1, u.getId());
            stmt.executeUpdate();
            
            stmt = con.prepareStatement(queryDelUserPermiso);
            stmt.setInt(1, u.getId());
            stmt.executeUpdate();

            stmt = con.prepareStatement(queryDelUser);
            stmt.setInt(1, u.getId());
            stmt.executeUpdate();

            con.commit();
            borrado = true;

            stmt.close();
        } catch (Exception ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return borrado;
    }
    
    public Curso addCurso(Curso c) {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(queryAddCurso, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, c.getNombre());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                c.setId(rs.getInt(1));
            }

            stmt.close();
        } catch (Exception ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            c = null;
        } finally {
            DBConnection.getInstance().cerrarConexion(con);
        }
        return c;
    }
    
    public Curso modCurso(Curso c) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            if (!(jtm.update(queryModCurso, c.getNombre(), c.getId()) > 0)) {
                c = null;
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
            c = null;
        }
        return c;
    }
    
    public List<Curso> getAllCursos() {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<Curso> cursos = jtm.query(queryGetAllCursos, new BeanPropertyRowMapper(Asignatura.class));

        return cursos;
    }
}
