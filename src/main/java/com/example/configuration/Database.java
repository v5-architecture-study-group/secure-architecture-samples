package com.example.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {

    private Database() {
    }

    public static Connection getConnection(DatabaseConfiguration configuration) throws SQLException {
        DriverManager.setLoginTimeout((int) configuration.loginTimeout().toSeconds());
        return DriverManager.getConnection(configuration.url().toString(),
                configuration.username().value(),
                new String(configuration.password().value()));
    }
}
