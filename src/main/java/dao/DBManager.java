package dao;

import config.Configuration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

/*
Esta clase es muy útil ya que unificas todas las excepciones de Spring JDBC
sin modificar el resultado esperado por la aplicación.
*/
public class DBManager
{
    private boolean debug = Configuration.getInstance().isDebug();
    /* Devuelve si el update fue satisfactorio */
    public boolean update(String query, Object... parametros)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        boolean querySuccess = false;
        try
        {
            querySuccess = jtm.update(query, new ArrayList<>(Arrays.asList(parametros))) > 0;
        }
        catch(DataAccessException e)
        {
            if(debug)
            {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return querySuccess;
    }
    /* queryForObject de spring JDBC con RowMapper */
    public Object queryForObject(String query, Class<?> clase, Object... parametros)
    {
        /* Abrimos conexión */
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        /* Parseamos los parámetros introducidos en los argumentos en un objeto para pasarlo a spring JDBC */
        Object[] params = new ArrayList<>(Arrays.asList(parametros)).toArray();
        Object result = null;
        try
        {
            /* Intentamos la query */
            result = jdbcTemplateObject.queryForObject(query, params, new BeanPropertyRowMapper(clase));
        }
        catch(DataAccessException e)
        {
            if(debug)
            {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
            }
            try
            {
                /* Si falla, instanciamos la clase para evitar nullpointers */
                result = clase.newInstance();
            }
            catch(IllegalAccessException | InstantiationException ex)
            {
                if(debug)
                {
                    Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }
    public int queryForInt(String query, Object... parametros)
    {
        /* Abrimos conexión */
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        /* Parseamos los parámetros introducidos en los argumentos en un objeto para pasarlo a spring JDBC */
        Object[] params = new ArrayList<>(Arrays.asList(parametros)).toArray();
        int result = 0;
        try
        {
            /* Intentamos la query */
            result = jdbcTemplateObject.queryForObject(query, params,Integer.class);
        }
        catch(DataAccessException e)
        {
            if(debug)
            {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return result;
    }
    public Object queryRunnable(String query, RowMapper<?> rowMapper, Object... parametros)
    {
        /* Abrimos conexión */
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        /* Parseamos los parámetros introducidos en los argumentos en un objeto para pasarlo a spring JDBC */
        Object[] params = new ArrayList<>(Arrays.asList(parametros)).toArray();
        Object result = null;
        try
        {
            /* Intentamos la query */
            result = jdbcTemplateObject.query(query, params, new RowMapperResultSetExtractor<>(rowMapper));
        }
        catch(DataAccessException e)
        {
            if(debug)
            {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return result;
    }
    /* queryForObject de spring JDBC sin RowMapper 
    public Object queryForObject(String query, Object... parametros)
    {
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        Object[] params = new ArrayList<>(Arrays.asList(parametros)).toArray();
        return jdbcTemplateObject.queryForObject(query, params);
    }*/
}
