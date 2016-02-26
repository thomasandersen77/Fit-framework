package com.github.fit.data;

import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;
import javax.sql.DataSource;

public class LocalContext extends InitialContext implements InitialContextFactoryBuilder, InitialContextFactory {

    Map<Object,Object> dataSources;

    public LocalContext() throws NamingException {
        super();
        dataSources = new HashMap<Object,Object>();
    }

    public void addDataSource(String name, String connectionString, String username, String password) {
        this.dataSources.put(name, new LocalDataSource(connectionString,username,password));
    }

    public InitialContextFactory createInitialContextFactory(
            Hashtable<?, ?> hsh) throws NamingException {
        dataSources.putAll(hsh);
        return this;
    }

    public Context getInitialContext(Hashtable<?, ?> arg0)
            throws NamingException {
        return this;
    }

    @Override
    public Object lookup(String name) throws NamingException {
        Object ret = dataSources.get(name);
        return (ret != null) ? ret : super.lookup(name);
    }

    class LocalDataSource implements DataSource, Serializable {

        private String connectionString;
        private String username;
        private String password;

        LocalDataSource(String connectionString, String username, String password) {
            this.connectionString = connectionString;
            this.username = username;
            this.password = password;
        }

        public Connection getConnection() throws SQLException
        {
            return DriverManager.getConnection(connectionString, username, password);
        }

        public Connection getConnection(String arg0, String arg1)
                throws SQLException
        {
            return getConnection();
        }

        public PrintWriter getLogWriter() throws SQLException {
            return null;
        }

        public int getLoginTimeout() throws SQLException {
            return 0;
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            return null;
        }

        public void setLogWriter(PrintWriter out) throws SQLException {}

        public void setLoginTimeout(int seconds) throws SQLException {}

        @Override
        public <T> T unwrap(Class<T> aClass) throws SQLException {
            return null;
        }

        @Override
        public boolean isWrapperFor(Class<?> aClass) throws SQLException {
            return false;
        }
    }
}