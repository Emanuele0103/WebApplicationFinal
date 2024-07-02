package com.example.webapplicationfinal.Database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DBSource {
    private final String uri;
    private final String username;
    private final String password;

    public DBSource(@Value("${db.uri}") String uri,
                    @Value("${db.username}") String username,
                    @Value("${db.password}") String password) {
        this.uri = uri;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(uri, username, password);
        return connection;
    }
}
