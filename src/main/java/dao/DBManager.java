package dao;

import config.Configuration;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Transactional;

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
            querySuccess = jtm.update(query, parametros) > 0;
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
        Object result = null;
        try
        {
            /* Intentamos la query */
            result = jdbcTemplateObject.queryForObject(query, parametros, new BeanPropertyRowMapper(clase));
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
    public Object queryAll(String query, Class<?> clase, Object... parametros)
    {
        /* Abrimos conexión */
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<Object> result = null;
        try
        {
            /* Intentamos la query */
            result = jdbcTemplateObject.query(query, parametros, new BeanPropertyRowMapper(clase));
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
    public int queryForInt(String query, Object... parametros)
    {
        /* Abrimos conexión */
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        /* Parseamos los parámetros introducidos en los argumentos en un objeto para pasarlo a spring JDBC */
        int result = 0;
        try
        {
            /* Intentamos la query */
            result = jdbcTemplateObject.queryForObject(query, parametros,Integer.class);
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
    public boolean queryForBoolean(String query, Object... parametros)
    {
        /* Abrimos conexión */
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        int result = 0;
        try
        {
            /* Intentamos la query */
            result = jdbcTemplateObject.queryForObject(query, parametros,Integer.class);
        }
        catch(DataAccessException e)
        {
            if(debug)
            {
                //Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return result > 0;
    }
    public Object queryRunnable(String query, RowMapper<?> rowMapper, Object... parametros)
    {
        /* Abrimos conexión */
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        Object result = null;
        try
        {
            /* Intentamos la query */
            result = jdbcTemplateObject.query(query, parametros, new RowMapperResultSetExtractor<>(rowMapper));
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
    /* eliminación simple */
    public boolean delete(String query, Object... parametros)
    {
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        return jdbcTemplateObject.update(query, parametros) > 0;
    }
    /* eliminación multiple y transaccional */
    @Transactional
    public boolean deleteAll(AbstractMap.SimpleEntry... deletes)
    {
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        boolean success;
        try
        {
            for (AbstractMap.SimpleEntry<String, Object[]> delete : deletes)
            {
                jdbcTemplateObject.update(delete.getKey(), delete.getValue());
            }
            success = true;
        }
        catch (DataAccessException e) {
            if(debug)
            {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
            }
            success = false;
        }
        return success;
    }
    
    @Transactional
    public boolean updateAll(AbstractMap.SimpleEntry... deletes)
    {
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        boolean success;
        try
        {
            for (AbstractMap.SimpleEntry<String, Object[]> delete : deletes)
            {
                jdbcTemplateObject.update(delete.getKey(), delete.getValue());
            }
            success = true;
        }
        catch (DataAccessException e) {
            if(debug)
            {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
            }
            success = false;
        }
        return success;
    }
    /* inserción múltiple y transaccional devolviendo estado */
    @Transactional
    public boolean insertAll(AbstractMap.SimpleEntry... inserts)
    {

        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        boolean success;
        try
        {
            for (AbstractMap.SimpleEntry<String, Object[]> insert : inserts)
            {
                jdbcTemplateObject.update(insert.getKey(), insert.getValue());
            }
            success = true;
        }
        catch (DataAccessException e) {
            if(debug)
            {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
            }
            success = false;
        }
        return success;
    }
    /* inserción múltiple y transaccional devolviendo estado */
    @Transactional
    public boolean insertAllList(ArrayList<SimpleEntry> inserts)
    {

        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        boolean success;
        try
        {
            for (AbstractMap.SimpleEntry<String, Object[]> insert : inserts)
            {
                jdbcTemplateObject.update(insert.getKey(), insert.getValue());
            }
            success = true;
        }
        catch (DataAccessException e) {
            if(debug)
            {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
            }
            success = false;
        }
        return success;
    }
    @Transactional
    public boolean updateAllList(ArrayList<SimpleEntry> updates)
    {

        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        boolean success;
        try
        {
            for (AbstractMap.SimpleEntry<String, Object[]> update : updates)
            {
                jdbcTemplateObject.update(update.getKey(), update.getValue());
            }
            success = true;
        }
        catch (DataAccessException e) {
            if(debug)
            {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
            }
            success = false;
        }
        return success;
    }
}
