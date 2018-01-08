package config;

import freemarker.template.TemplateExceptionHandler;
import java.io.InputStream;
import javax.servlet.ServletContext;
import org.yaml.snakeyaml.Yaml;

public class Configuration
{

    private static Configuration config;

    private Configuration()
    {

    }

    public static Configuration getInstance()
    {

        return config;
    }

    public static Configuration getInstance(InputStream file, ServletContext sc)
    {
        if (config == null)
        {
            Yaml yaml = new Yaml();
            config = (Configuration) yaml.loadAs(file, Configuration.class);
            config.setFreeMarker(new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23));
            config.getFreeMarker().setServletContextForTemplateLoading(sc, "/WEB-INF/templates");
            config.getFreeMarker().setDefaultEncoding("ISO-8859-1");
            config.getFreeMarker().setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

            config.getFreeMarker().setLogTemplateExceptions(false);
        }
        return config;
    }
    private String urlDB;
    private String driverDB;
    private String mailFrom;
    private String smtpServer;
    private String smtpPort;
    private String mailPass;
    private int minutosParaValidar;
    private int longitudCodigo;
    private int longitudPass;

    private freemarker.template.Configuration freeMarker;

    public freemarker.template.Configuration getFreeMarker()
    {
        return freeMarker;
    }

    public void setFreeMarker(freemarker.template.Configuration freeMarker)
    {
        this.freeMarker = freeMarker;
    }

    public String getUrlDB()
    {
        return urlDB;
    }

    public void setUrlDB(String urlDB)
    {
        this.urlDB = urlDB;
    }

    public String getDriverDB()
    {
        return driverDB;
    }

    public void setDriverDB(String driverDB)
    {
        this.driverDB = driverDB;
    }

    public String getMailFrom()
    {
        return mailFrom;
    }

    public void setMailFrom(String mailFrom)
    {
        this.mailFrom = mailFrom;
    }

    public String getSmtpServer()
    {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer)
    {
        this.smtpServer = smtpServer;
    }

    public String getSmtpPort()
    {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort)
    {
        this.smtpPort = smtpPort;
    }

    public String getMailPass()
    {
        return mailPass;
    }

    public void setMailPass(String mailPass)
    {
        this.mailPass = mailPass;
    }

    public int getMinutosParaValidar()
    {
        return minutosParaValidar;
    }

    public void setMinutosParaValidar(int minutosParaValidar)
    {
        this.minutosParaValidar = minutosParaValidar;
    }

    public int getLongitudCodigo()
    {
        return longitudCodigo;
    }

    public void setLongitudCodigo(int longitudCodigo)
    {
        this.longitudCodigo = longitudCodigo;
    }
    
    public int getLongitudPass()
    {
        return longitudPass;
    }

    public void setLongitudPass(int longitudPass)
    {
        this.longitudPass = longitudPass;
    }

}
