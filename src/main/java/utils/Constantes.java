
package utils;

public class Constantes {
    public static final String LINK_EMAIL_ACTIVAR = "http://localhost:8080/conectar?accion=activarUsuario&codigo=";
    public static final String LINK_EMAIL_NUEVA_PASS = "http://localhost:8080/recuperarclave/paso2?codigo=";
    public static final String SESSION_NOMBRE_USUARIO = "nombreUsuario";
    public static final String SESSION_RANGO_USUARIO = "rangoUsuario";
    public static final String EMAIL_CONTENT_ACTIVAR_1 = "<html><body><h1>Registro <strong>completado</strong></h1><p>Muchas gracias por registrarte.</p><p>Haz click en el siguiente enlace para activar tu cuenta.</p><a href='";
    public static final String EMAIL_CONTENT_ACTIVAR_2 = "'>Activar</a><br><br><p>Contraseña: ";
    public static final String EMAIL_CONTENT_ACTIVAR_3 = "</p><br><p>Podrás cambiar tu contraseña cuando inicies sesión.</p></body></html>";
    public static final String EMAIL_CONTENT_NUEVA_PASS_1 = "<html><body><h1>Solicitud de <strong>cambio de contraseña</strong></h1><p>Hemos recibido tu solicitud para cambiar de contraseña.</p><p>Haz click en el siguiente enlace.</p><a href='";
    public static final String EMAIL_CONTENT_NUEVA_PASS_2 = "'>Cambiar contraseña</a></body></html>";
    
    public static final String PARAMETRO_ACCION = "accion";
    public static final String PARAMETRO_ID = "id";
    public static final String PARAMETRO_NOMBRE = "nombre";
    public static final String PARAMETRO_ID_CURSO = "id_curso";
    public static final String PARAMETRO_EMAIL = "email";
    public static final String PARAMETRO_TIPO = "tipo";
    public static final String PARAMETRO_NOTA = "nota";
    public static final String PARAMETRO_ID_TAREA = "id_tarea";
    public static final String PARAMETRO_ID_ASIGNATURA = "id_asignatura";
    public static final String PARAMETRO_NOMBRE_TAREA = "nombre_tarea";
    public static final String PARAMETRO_FECHA_ENTREGA = "fecha_entrega";
    public static final String PARAMETRO_COMPLETADO = "completado";
    public static final String PARAMETRO_NOMBRE_ASIGNATURA = "nombre_asignatura";
    
    public static final String FORMATO_FECHA = "dd-MM-yyyy";
}
