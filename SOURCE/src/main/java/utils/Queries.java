
package utils;

import java.util.logging.Logger;

public class Queries {
    public final static String queryGetUserByMail = "SELECT * FROM users WHERE email = ?";
    public final static String queryGetPermiso = "SELECT id_permiso FROM users WHERE email = ?";
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
    
    public final static String queryGetAllAsignaturas = "SELECT * FROM asignaturas";
    public final static String queryGetAllUsers = "SELECT id, email, nombre, id_permiso FROM users";
    public final static String queryAddAsig = "INSERT INTO asignaturas (nombre,id_curso) VALUES (?,?)";
    public final static String queryModAsig = "UPDATE asignaturas SET nombre = ?, id_curso = ? WHERE id = ?";
    public final static String queryDelAsig = "DELETE FROM asignaturas WHERE id = ?";
    public final static String queryDelNota = "DELETE FROM alumnos_asignaturas WHERE id_asignatura = ?";
    public final static String queryDelAsigProfe = "DELETE FROM profesores_asignaturas WHERE id_asignatura = ?";
    public final static String queryGetAllAlumnos = "SELECT * FROM users u WHERE id_permiso = 3";
    public final static String queryGetTotalAlumnos = "SELECT COUNT(*) FROM users u WHERE id_permiso = 3";
    public final static String queryGetAlumnosPaginados = "SELECT * FROM users u WHERE id_permiso = 3 LIMIT ?,?";
    public final static String queryGetAllProfes = "SELECT * FROM users u WHERE id_permiso = 2";
    public final static String queryAsignarProfeAsig = "INSERT INTO profesores_asignaturas (id_profesor, id_asignatura) VALUES (?,?)";
    public final static String queryEliminarProfeAsig = "DELETE FROM profesores_asignaturas WHERE id_profesor = ?";
    public final static String queryAsignarAlumAsig = "INSERT INTO alumnos_asignaturas (id_alumno, id_asignatura) VALUES (?,?)";
    public final static String queryEliminarAlumAsig = "DELETE FROM alumnos_asignaturas WHERE id_alumno = ?";
    public final static String queryGetAllAlumAsig = "SELECT * FROM alumnos_asignaturas";
    public final static String queryGetAllProfeAsig = "SELECT * FROM profesores_asignaturas";
    public final static String queryComprobarEmail = "SELECT email FROM users WHERE email = ?";
    public final static String queryRegistrarUser = "INSERT INTO users (email,clave,activo,codigo_activacion,nombre,id_permiso) VALUES (?,?,0,?,?,?)";
    public final static String queryModificarUser = "UPDATE users SET email = ?, nombre = ? WHERE id = ?";
    public final static String queryModificarUserPermisos = "UPDATE users SET id_permiso = ? WHERE id = ?";
    public final static String queryGetPermisoAdmin = "SELECT id_permiso FROM users WHERE id = ?";
    public final static String queryDelUser = "DELETE FROM users WHERE id = ?";
    public final static String queryAddCurso = "INSERT INTO cursos (nombre) VALUES (?)";
    public final static String queryModCurso = "UPDATE cursos SET nombre = ? WHERE id = ?";
    public final static String queryGetAllCursos = "SELECT * FROM cursos";
    public final static String queryDelTarea = "DELETE FROM tareas WHERE id_asignatura = ?";
    public final static String queryDelTareaAlumno = "DELETE FROM tareas_alumnos WHERE id_tarea IN (SELECT id_tarea FROM tareas WHERE id_asignatura = ?)";

    public final static String queryGetAllNotasCurso = "SELECT c.nombre, c.id, a.id, a.nombre, AVG(aa.nota) "
            + "FROM users u "
            + "JOIN alumnos_asignaturas aa ON u.id = aa.id_alumno "
            + "JOIN profesores_asignaturas pa ON pa.id_asignatura = aa.id_asignatura "
            + "JOIN asignaturas a ON a.id = aa.id_asignatura "
            + "JOIN cursos c ON c.id = a.id_curso  "
            + "WHERE pa.id_profesor=? "
            + "GROUP BY aa.id_asignatura";
    public final static String queryGetAllNotasCursoAlumnos = "SELECT c.nombre, c.id, a.id, a.nombre, u.id, u.nombre, aa.nota "
            + "FROM users u "
            + "JOIN alumnos_asignaturas aa ON u.id = aa.id_alumno "
            + "JOIN profesores_asignaturas pa ON pa.id_asignatura = aa.id_asignatura "
            + "JOIN asignaturas a ON a.id = aa.id_asignatura "
            + "JOIN cursos c ON c.id = a.id_curso  "
            + "WHERE pa.id_profesor=? ";
    public final static String queryGetAllNotasProfe = "SELECT u.id, u.nombre, a.id, a.nombre, c.id, c.nombre, aa.nota "
            + "FROM users u "
            + "JOIN alumnos_asignaturas aa ON u.id = aa.id_alumno "
            + "JOIN asignaturas a ON a.id = aa.id_asignatura "
            + "JOIN profesores_asignaturas pa ON pa.id_asignatura = aa.id_asignatura "
            + "JOIN cursos c ON c.id = a.id_curso "
            + "WHERE pa.id_profesor = ? ";
    public final static String queryGetId = "SELECT id FROM users WHERE email = ?";
    public final static String queryGetAsignaturaNombre = "SELECT nombre FROM asignaturas WHERE id = ?";
    public final static String queryModNota = "UPDATE alumnos_asignaturas SET nota = ? WHERE id_alumno = ? AND id_asignatura = ?";
    public final static String queryAddTarea = "INSERT INTO tareas (id_asignatura, nombre_tarea, fecha_entrega, email_profesor) VALUES (?,?,?,?)";
    public final static String queryAddTareaAlumno = "INSERT INTO tareas_alumnos (id_tarea, id_alumno, completado) VALUES (?,?,0)";
    public final static String queryModTarea = "UPDATE tareas SET nombre_tarea = ?, fecha_entrega = ? WHERE id_tarea = ?";
    public final static String queryGetIdAlumnos = "SELECT id_alumno FROM alumnos_asignaturas WHERE id_asignatura = ?";
    public final static String queryGetAllTareasProfe = "SELECT t.nombre_tarea, t.fecha_entrega, t.email_profesor, a.id, a.nombre, t.id_tarea FROM tareas t "
            + "JOIN asignaturas a ON a.id=t.id_asignatura"
            + " WHERE email_profesor = ?";
    public final static String queryDelTareaProfe = "DELETE FROM tareas WHERE id_tarea = ?";
    public final static String queryDelTareaProfeAlumno = "DELETE FROM tareas_alumnos WHERE id_tarea = ?";
    private static final Logger LOG = Logger.getLogger(Queries.class.getName());

}
