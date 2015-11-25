package com.ideas.akka.messages;

import java.io.Serializable;
import java.sql.Connection;

public class UpdateDatabase implements Serializable {

    private final String query;
    private final Connection connection;

    public String getQuery() {
        return query;
    }

    public UpdateDatabase(String query, Connection connection) {
        this.query = query;
        this.connection = connection;
    }


    public Connection getConnection() {
        return connection;
    }
}
