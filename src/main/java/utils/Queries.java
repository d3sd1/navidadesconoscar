
package utils;

public class Queries {
    public final static String queryGetUserByMail = "SELECT * FROM users WHERE email = ?";
    public final static String queryGetPermiso = "SELECT up.id_permiso FROM users_permisos up JOIN users u ON up.id_user = u.id WHERE u.email = ?";
    public final static String queryUserByCodigoActivacion = "SELECT * FROM users WHERE codigo_activacion = ?";
    public final static String queryActivar = "UPDATE users SET activo = TRUE, codigo_activacion = NULL WHERE codigo_activacion = ?";
    public final static String queryUpdateCodigo = "UPDATE users SET codigo_activacion = ? WHERE email = ?";
    public final static String queryUpdatePassByCodigo = "UPDATE users SET clave = ?, codigo_activacion = NULL WHERE codigo_activacion = ?";
    public final static String queryUpdatePassByEmail = "UPDATE users SET clave = ? WHERE email = ?";
    public final static String queryGetAllNotas = "SELECT u.id, u.nombre, a.id, a.nombre, aa.nota "
            + "FROM alumnos_asignaturas aa "
            + "JOIN users u ON aa.id_alumno = u.id "
            + "JOIN asignaturas a ON a.id = aa.id_asignatura "
            + "WHERE u.email = ? ";
    public final static String queryGetAllTareas = "SELECT ta.id_tarea, t.nombre_tarea, t.fecha_entrega, ta.completado, a.id, a.nombre "
            + "FROM tareas_alumnos ta "
            + "JOIN tareas t ON ta.id_tarea = t.id_tarea "
            + "JOIN users u ON u.id = ta.id_alumno "
            + "JOIN asignaturas a ON t.id_asignatura = a.id "
            + "WHERE u.email = ?";
    public final static String queryCompletarTarea = "UPDATE tareas_alumnos SET completado = 1 WHERE id_tarea = ? AND id_alumno = "
            + "(SELECT id FROM users WHERE email = ?)";
    public final static String queryGetTareaById = "SELECT t.* FROM tareas t "
            + "JOIN tareas_alumnos ta ON t.id_tarea = ta.id_tarea "
            + "WHERE ta.id_tarea = ? AND id_alumno = "
            + "(SELECT id FROM users WHERE email = ?)";
}
