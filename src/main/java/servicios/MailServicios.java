
package servicios;

import config.Configuration;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class MailServicios {

    public void mandarMail(String to, String msg, String subject) {
        try {

            Email email = new SimpleEmail();

            email.setHostName(Configuration.getInstance().getSmtpServer());
            email.setSmtpPort(Integer.parseInt(Configuration.getInstance().getSmtpPort()));
            email.setAuthentication(Configuration.getInstance().getMailFrom(), Configuration.getInstance().getMailPass());
            email.setStartTLSEnabled(true);
            email.setFrom(Configuration.getInstance().getMailFrom());
            email.setSubject(subject);
            email.setContent("<html>"
                    + "<body>"
                    + "<h1>Registro <strong>completado</strong></h1>"
                    + "<p>Muchas gracias por registrarte.</p>"
                    + "<p>Haz click en el siguiente enlace para activar tu cuenta.</p>"
                    + "<a href='" + msg + "'>Activar</a>"
                    + "</body>"
                    + "</html>", "text/html");
            email.addTo(to);
            email.send();
        } catch (EmailException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(MailServicios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
