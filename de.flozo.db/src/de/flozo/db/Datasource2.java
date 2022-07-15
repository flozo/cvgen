package de.flozo.db;

import java.sql.*;

public enum Datasource2 {

    INSTANCE;


    // database
    public static final String DB_NAME = "properties.db";
    public static final String CONNECTION_STRING_PREFIX = "jdbc:sqlite:";
    public static final String RESOURCE_FOLDER = "../../../Data/CVgen_test/";

    private final String connectionString = CONNECTION_STRING_PREFIX + RESOURCE_FOLDER + DB_NAME;

    private Connection connection;


    public Connection getConnection() {
        System.out.print("Connecting to database \"" + connectionString + "\" ...");
        try {
            connection = DriverManager.getConnection(connectionString);
            System.out.println(" done!");
            return connection;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Couldn't connect to database: " + e.getMessage());
        }
        return null;
    }


    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection \"" + connection + "\": " + e.getMessage());
        }
    }

    public void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close statement \"" + statement + "\": " + e.getMessage());
        }
    }


    public void closePreparedStatement(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close prepared statement \"" + preparedStatement + "\": " + e.getMessage());
        }
    }

    public void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close result set \"" + resultSet + "\": " + e.getMessage());
        }
    }


    @Override
    public String toString() {
        return "Datasource2{" +
                "connectionString='" + connectionString + '\'' +
                '}';
    }
}
