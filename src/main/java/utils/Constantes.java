
package utils;

public class Constantes {
    public static final String LINK_EMAIL_ACTIVAR = "http://localhost:8080/login?accion=activarUsuario&codigo=";
    public static final String LINK_EMAIL_NUEVA_PASS = "http://localhost:8080/recuperarpass2?codigo=";
    public static final String SESSION_NOMBRE_USUARIO = "nombreUsuario";
    public static final String SESSION_RANGO_USUARIO = "rangoUsuario";
    public static final String EMAIL_CONTENT_ACTIVAR_1 = "<html><body><h1>Registro <strong>completado</strong></h1><p>Muchas gracias por registrarte.</p><p>Haz click en el siguiente enlace para activar tu cuenta.</p><a href='";
    public static final String EMAIL_CONTENT_ACTIVAR_2 = "'>Activar</a></body></html>";
    public static final String EMAIL_CONTENT_NUEVA_PASS_1 = "<html><body><h1>Solicitud de <strong>cambio de contraseña</strong></h1><p>Hemos recibido tu solicitud para cambiar de contraseña.</p><p>Haz click en el siguiente enlace.</p><a href='";
    public static final String EMAIL_CONTENT_NUEVA_PASS_2 = "'>Cambiar contraseña</a></body></html>";
}
