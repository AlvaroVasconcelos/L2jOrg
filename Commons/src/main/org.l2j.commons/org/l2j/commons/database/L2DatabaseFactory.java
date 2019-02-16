package org.l2j.commons.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

import static java.util.Objects.isNull;

public class L2DatabaseFactory {
    private static Logger LOGGER = LoggerFactory.getLogger(L2DatabaseFactory.class);

    private static L2DatabaseFactory instance;
    private final HikariDataSource _dataSource;

    public L2DatabaseFactory() throws SQLException {
        _dataSource = new HikariDataSource(new HikariConfig());
        _dataSource.getConnection().close();
    }

    public void shutdown() {
        try {
            _dataSource.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    // TODO remove access from external modules
    public static L2DatabaseFactory getInstance() throws SQLException {
        if (isNull(instance)) {
            instance = new L2DatabaseFactory();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            return _dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
}