
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
            email.setContent(msg, "text/html");
            email.addTo(to);
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(MailServicios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
