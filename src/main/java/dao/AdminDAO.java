package dao;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import model.Asignatura;
import model.Curso;
import model.User;
import utils.Queries;

public class AdminDAO
{
    private final DBManager manager = new DBManager();

    public List<Asignatura> getAllAsignaturas()
    {
        return (List<Asignatura>) this.manager.queryAll(Queries.queryGetAllAsignaturas,Asignatura.class);
    }

    public List<User> getAllUsers()
    {
        return (List<User>) this.manager.queryAll(Queries.queryGetAllUsers,User.class);
    }

    public List<Asignatura> getAsignaturasAlumno()
    {
        return (List<Asignatura>) this.manager.queryAll(Queries.queryGetAllAlumAsig,Asignatura.class);
    }

    public List<Asignatura> getAsignaturasProfesor()
    {
        return (List<Asignatura>) this.manager.queryAll(Queries.queryGetAllProfeAsig,Asignatura.class);
    }

    public boolean addAsig(Asignatura asignatura)
    {
        return this.manager.insertAll(
            new AbstractMap.SimpleEntry<>(Queries.queryAddAsig, new Object[]{asignatura.getNombre(),asignatura.getId_curso()})
        );
    }

    public boolean modAsig(Asignatura asignatura)
    {
        return this.manager.update(Queries.queryModAsig, asignatura.getNombre(), asignatura.getId_curso(), asignatura.getId());

    }

    public boolean delAsig(Asignatura asignatura)
    {
        return this.manager.deleteAll(
            new AbstractMap.SimpleEntry<>(Queries.queryDelNota, new Object[]{asignatura.getId()}),
            new AbstractMap.SimpleEntry<>(Queries.queryDelAsigProfe, new Object[]{asignatura.getId()}),
            new AbstractMap.SimpleEntry<>(Queries.queryDelTareaAlumno, new Object[]{asignatura.getId()}),
            new AbstractMap.SimpleEntry<>(Queries.queryDelTarea, new Object[]{asignatura.getId()}),
            new AbstractMap.SimpleEntry<>(Queries.queryDelAsig, new Object[]{asignatura.getId()})
        );
    }

    public List<User> getAllAlumnos()
    {
        return (List<User>) this.manager.queryAll(Queries.queryGetAllAlumnos,User.class);
    }

    public int getTotalAlumnos()
    {
        return (int) this.manager.queryForInt(Queries.queryGetTotalAlumnos);
    }
    public ArrayList<ArrayList<String>> getAlumnos(int start, int length)
    {
        List<User> alumnos = (List<User>) this.manager.queryAll(Queries.queryGetAlumnosPaginados,User.class);
        ArrayList<ArrayList<String>> devolver = new ArrayList<>();
        for (User alumno : alumnos)
        {
            ArrayList<String> actualAlumno = new ArrayList<>();
            actualAlumno.add(alumno.getId() + "");
            actualAlumno.add(alumno.getNombre());
            actualAlumno.add(alumno.getEmail());
            actualAlumno.add(Boolean.toString(alumno.getActivo()));
            devolver.add(actualAlumno);
        }
        return devolver;
    }

    public List<User> getAllProfes()
    {
        return (List<User>) this.manager.queryAll(Queries.queryGetAllProfes,User.class);
    }

    public boolean asignarProfeAsig(int id_profe, String[] id_asignaturas)
    {
        ArrayList updates = new ArrayList<>();
        /* Añadir tareas a los alumnos */
        for (int i = 0; i < id_asignaturas.length; i++) {
            updates.add(new AbstractMap.SimpleEntry<>(Queries.queryAsignarProfeAsig, new Object[]{
                id_profe,
                id_asignaturas[i]
            }));
        }
        return this.manager.updateAllList(
            updates
        );
    }

    public boolean asignarInsertandoAlumAsig(int id_alumno, String[] id_asignaturas)
    {
        ArrayList updates = new ArrayList<>();
        /* Añadir tareas a los alumnos */
        for (int i = 0; i < id_asignaturas.length; i++) {
            updates.add(new AbstractMap.SimpleEntry<>(Queries.queryAsignarAlumAsig, new Object[]{
                id_alumno,
                id_asignaturas[i]
            }));
        }
        return this.manager.updateAllList(
            updates
        );
    }

    public boolean asignarInsertandoProfeAsig(int id_profe, String[] id_asignaturas)
    {
        ArrayList updates = new ArrayList<>();
        /* Añadir tareas a los alumnos */
        for (int i = 0; i < id_asignaturas.length; i++) {
            updates.add(new AbstractMap.SimpleEntry<>(Queries.queryAsignarProfeAsig, new Object[]{
                id_profe,
                id_asignaturas[i]
            }));
        }
        return this.manager.updateAllList(
            updates
        );
    }

    public boolean eliminarAlumAsig(User alumno)
    {
        return this.manager.deleteAll(
            new AbstractMap.SimpleEntry<>(Queries.queryEliminarAlumAsig, new Object[]{alumno.getId()})
        );
    }

    public boolean eliminarProfeAsig(User profe)
    {
        return this.manager.deleteAll(
            new AbstractMap.SimpleEntry<>(Queries.queryEliminarProfeAsig, new Object[]{profe.getId()})
        );
    }

    public boolean comprobarEmail(User user)
    {
        return this.manager.queryForBoolean(Queries.queryComprobarEmail,user.getEmail());
    }

    public int addUser(User u)
    {
        return this.manager.insertRetKey(
            Queries.queryRegistrarUser,
                u.getEmail(),
                u.getClave(),
                u.getCodigoActivacion(),
                u.getNombre(),
                1
        );
    }

    public boolean modUser(User u, int tipo)
    {
        return this.manager.updateAll(
            new AbstractMap.SimpleEntry<>(Queries.queryModificarUser, new Object[]{u.getEmail(),u.getNombre(),u.getId()}),
            new AbstractMap.SimpleEntry<>(Queries.queryModificarUserPermisos, new Object[]{u.getId_permiso(),u.getId()})
        );
    }

    public int getPermiso(User user)
    {
        return (int) this.manager.queryForInt(Queries.queryGetPermisoAdmin,user.getId());
    }

    public boolean delUser(User user)
    {
        return this.manager.deleteAll(
            new AbstractMap.SimpleEntry<>(Queries.queryEliminarAlumAsig, new Object[]{user.getId()}),
            new AbstractMap.SimpleEntry<>(Queries.queryEliminarProfeAsig, new Object[]{user.getId()}),
            new AbstractMap.SimpleEntry<>(Queries.queryDelUser, new Object[]{user.getId()})
        );
    }
    public List<Curso> getAllCursos()
    {
        return (List<Curso>) this.manager.queryAll(Queries.queryGetAllCursos,Curso.class);
    }
}
