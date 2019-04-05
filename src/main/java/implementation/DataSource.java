/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author herin
 */
public class DataSource {

    private final static Object threadLock = new Object();
    private static DataSource dataSource;
    private BasicDataSource ds;

    public DataSource() throws IOException, SQLException, PropertyVetoException {
        ds = new BasicDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUsername("admin");
        ds.setPassword("1234");
        ds.setUrl("jdbc:postgresql://localhost:" + 25000 + "/postgres");

        ds.setMaxWaitMillis(1000 * 60 * 10); //wait 60 seconds to get new connection
        ds.setMaxTotal(45);
        ds.setMaxIdle(45);
        ds.setInitialSize(45);
        ds.setTestWhileIdle(true);
        ds.setTestOnReturn(true);
        ds.setTimeBetweenEvictionRunsMillis(10000); // 10 sec to wait to run evictor process
        ds.setSoftMinEvictableIdleTimeMillis(10000); // 10 sec to wait to run evictor process
        ds.setMinEvictableIdleTimeMillis(60000); // 60 seconds to wait before idle connection is evicted
        ds.setMaxConnLifetimeMillis(1000 * 60 * 10); // 10 minutes is max life time
    }

    public static DataSource getInstance() {
        if (dataSource == null) {
            synchronized (threadLock) {
                if (dataSource == null) {
                    try {
                        dataSource = new DataSource();
                    } catch (IOException ex) {
                        Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (PropertyVetoException ex) {
                        Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return dataSource;
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
