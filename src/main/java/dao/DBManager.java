package dao;

import config.Configuration;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.datasource.DataSourceUtils;

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
    public Object queryAll(String query, Class<?> clase, Object... parametros)
    {
        /* Abrimos conexión */
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        /* Parseamos los parámetros introducidos en los argumentos en un objeto para pasarlo a spring JDBC */
        Object[] params = new ArrayList<>(Arrays.asList(parametros)).toArray();
        List<Object> result = null;
        try
        {
            /* Intentamos la query */
            result = jdbcTemplateObject.query(query, params, new BeanPropertyRowMapper(clase));
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
    public boolean queryForBoolean(String query, Object... parametros)
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
        return result > 0;
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
    /* eliminación simple */
    public boolean delete(String query, Object... parametros)
    {
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        Object[] params = new ArrayList<>(Arrays.asList(parametros)).toArray();
        return jdbcTemplateObject.update(query, params) > 0;
    }
    /* eliminación multiple y transaccional */
    public boolean deleteAll(AbstractMap.SimpleEntry... deletes)
    {
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        Connection conn = DataSourceUtils.getConnection(jdbcTemplateObject.getDataSource());
        boolean success;
        try
        {
            conn.setAutoCommit(false);
            for (AbstractMap.SimpleEntry<Object, String> delete : deletes)
            {
                String query = delete.getKey().toString();
                Object[] params = new ArrayList<>(Arrays.asList(delete.getValue())).toArray();
                jdbcTemplateObject.update(query, params);
            }
            conn.commit();
            success = true;
            conn.setAutoCommit(true);
        }
        catch (SQLException e) {
            try
            {
                conn.rollback();
            }
            catch(SQLException ex)
            {
                if(debug)
                {
                    Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            if(debug)
            {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
            }
            success = false;
        }
        return success;
    }
    
    public boolean updateAll(AbstractMap.SimpleEntry... deletes)
    {
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        Connection conn = DataSourceUtils.getConnection(jdbcTemplateObject.getDataSource());
        boolean success;
        try
        {
            conn.setAutoCommit(false);
            for (AbstractMap.SimpleEntry<String, Object> delete : deletes)
            {
                String query = delete.getKey();
                Object[] params = new ArrayList<>(Arrays.asList(delete.getValue())).toArray();
                jdbcTemplateObject.update(query, params);
            }
            conn.commit();
            success = true;
            conn.setAutoCommit(true);
        }
        catch (SQLException e) {
            try
            {
                conn.rollback();
            }
            catch(SQLException ex)
            {
                if(debug)
                {
                    Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            if(debug)
            {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
            }
            success = false;
        }
        return success;
    }
    /* inserción múltiple y transaccional devolviendo estado */
    public boolean insertAll(AbstractMap.SimpleEntry... inserts)
    {

        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        Connection conn = DataSourceUtils.getConnection(jdbcTemplateObject.getDataSource());
        boolean success;
        try
        {
            conn.setAutoCommit(false);
            for (AbstractMap.SimpleEntry<Object, String> insert : inserts)
            {
                String query = insert.getKey().toString();
                Object[] params = new ArrayList<>(Arrays.asList(insert.getValue())).toArray();
                jdbcTemplateObject.update(query, params);
            }
            conn.commit();
            success = true;
            conn.setAutoCommit(true);
        }
        catch (SQLException e) {
            try
            {
                conn.rollback();
            }
            catch(SQLException ex)
            {
                if(debug)
                {
                    Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            if(debug)
            {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
            }
            success = false;
        }
        return success;
    }
    /* inserción múltiple y transaccional devolviendo estado */
    public boolean insertAllList(ArrayList<SimpleEntry> inserts)
    {

        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        Connection conn = DataSourceUtils.getConnection(jdbcTemplateObject.getDataSource());
        boolean success;
        try
        {
            conn.setAutoCommit(false);
            for (AbstractMap.SimpleEntry<Object, String> insert : inserts)
            {
                String query = insert.getKey().toString();
                Object[] params = new ArrayList<>(Arrays.asList(insert.getValue())).toArray();
                jdbcTemplateObject.update(query, params);
            }
            conn.commit();
            success = true;
            conn.setAutoCommit(true);
        }
        catch (SQLException e) {
            try
            {
                conn.rollback();
            }
            catch(SQLException ex)
            {
                if(debug)
                {
                    Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            if(debug)
            {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
            }
            success = false;
        }
        return success;
    }
    public boolean updateAllList(ArrayList<SimpleEntry> updates)
    {

        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        Connection conn = DataSourceUtils.getConnection(jdbcTemplateObject.getDataSource());
        boolean success;
        try
        {
            conn.setAutoCommit(false);
            for (AbstractMap.SimpleEntry<Object, String> update : updates)
            {
                String query = update.getKey().toString();
                Object[] params = new ArrayList<>(Arrays.asList(update.getValue())).toArray();
                jdbcTemplateObject.update(query, params);
            }
            conn.commit();
            success = true;
            conn.setAutoCommit(true);
        }
        catch (SQLException e) {
            try
            {
                conn.rollback();
            }
            catch(SQLException ex)
            {
                if(debug)
                {
                    Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            if(debug)
            {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
            }
            success = false;
        }
        return success;
    }
}
