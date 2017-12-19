
package config;

import freemarker.template.TemplateExceptionHandler;
import java.io.InputStream;
import javax.servlet.ServletContext;
import org.yaml.snakeyaml.Yaml;

public class Configuration {

    private static Configuration config;

    private Configuration() {

    }

    public static Configuration getInstance() {

        return config;
    }

    public static Configuration getInstance(InputStream file,ServletContext sc) {
        if (config == null) {
            Yaml yaml = new Yaml();
            config = (Configuration) yaml.loadAs(file, Configuration.class);
// Create your Configuration instance, and specify if up to what FreeMarker
// version (here 2.3.25) do you want to apply the fixes that are not 100%
// backward-compatible. See the Configuration JavaDoc for details.
            config.setFreeMarker(new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23));

// Specify the source where the template files come from. Here I set a
// plain directory for it, but non-file-system sources are possible too:
          //config.getFreeMarker().setServletContextForTemplateLoading(sc, "WEB-INF/templates");

// Set the preferred charset template files are stored in. UTF-8 is
// a good choice in most applications:
            config.getFreeMarker().setDefaultEncoding("UTF-8");

// Sets how errors will appear.
// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
            config.getFreeMarker().setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

// Don't log exceptions inside FreeMarker that it will thrown at you anyway:
            config.getFreeMarker().setLogTemplateExceptions(false);
        }
        return config;
    }
    private String urlDB;
    private String driverDB;
    private String userDB;
    private String passDB;
    private String mailFrom;
    private String smtpServer;
    private String smtpPort;
    private String mailPass;
    private int minutosParaValidar;
    private int longitudCodigo;
    

    private freemarker.template.Configuration freeMarker;

    public freemarker.template.Configuration getFreeMarker() {
        return freeMarker;
    }

    public void setFreeMarker(freemarker.template.Configuration freeMarker) {
        this.freeMarker = freeMarker;
    }

    
    
    public String getUrlDB() {
        return urlDB;
    }

    public void setUrlDB(String urlDB) {
        this.urlDB = urlDB;
    }

    public String getDriverDB() {
        return driverDB;
    }

    public void setDriverDB(String driverDB) {
        this.driverDB = driverDB;
    }

    public String getUserDB() {
        return userDB;
    }

    public void setUserDB(String userDB) {
        this.userDB = userDB;
    }

    public String getPassDB() {
        return passDB;
    }

    public void setPassDB(String passDB) {
        this.passDB = passDB;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getMailPass() {
        return mailPass;
    }

    public void setMailPass(String mailPass) {
        this.mailPass = mailPass;
    }
    
    public int getMinutosParaValidar(){
        return minutosParaValidar;
    }
    
    public void setMinutosParaValidar(int minutosParaValidar){
        this.minutosParaValidar = minutosParaValidar;
    }
    
    public int getLongitudCodigo(){
        return longitudCodigo;
    }
    
    public void setLongitudCodigo(int longitudCodigo){
        this.longitudCodigo = longitudCodigo;
    }

}
