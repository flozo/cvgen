package de.flozo.db;

import java.sql.*;

public enum Datasource {

    INSTANCE;


    // database
//    public static final String DB_NAME = "properties.db";
    public static final String DB_NAME = "properties_dummy.db";

    public static final String CONNECTION_STRING_PREFIX = "jdbc:sqlite:";

    //    public static final String RESOURCE_FOLDER = "../../../Data/CVgen_test/";
    public static final String CURRENT_DIRECTORY = System.getProperty("user.dir") + "/de.flozo.db";
    public static final String RESOURCE_FOLDER = CURRENT_DIRECTORY + "/resources/";

    private static final String CONNECTION_STRING = CONNECTION_STRING_PREFIX + RESOURCE_FOLDER + DB_NAME;

    private Connection connection;


    public Connection getConnection() {
        System.out.print("[database] Connecting to database \"" + CONNECTION_STRING + "\" ...");
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            System.out.println(" done!");
            return connection;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("[database] [error] Couldn't connect to database: " + e.getMessage());
        }
        return null;
    }


    public void closeConnection() {
        System.out.print("[database] Closing connection to database \"" + CONNECTION_STRING + "\" ...");
        try {
            if (connection != null) {
                connection.close();
            }
            System.out.println(" done!");
        } catch (SQLException e) {
            System.out.println();
            System.out.println("[database] [error] Couldn't close connection \"" + connection + "\": " + e.getMessage());
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

    public void rollback(Exception e, String messageText) {
        System.out.println("[database] [error] " + messageText + " exception: " + e.getMessage());
        try {
            System.out.print("[database] Performing rollback ...");
            connection.rollback();
            // end of transaction
            System.out.println(" done!");
        } catch (SQLException e2) {
            System.out.println();
            System.out.println("[database] [error] Rollback failed! " + e2.getMessage());
        }
    }

    public void setAutoCommitBehavior(boolean autoCommitOn) {
        try {
            boolean initialAutoCommit = connection.getAutoCommit();
            System.out.print("[database] Auto-commit is \"" + initialAutoCommit + "\". ");
            System.out.print("Setting auto-commit behavior to \"" + autoCommitOn + "\" ...");
            connection.setAutoCommit(autoCommitOn);
            System.out.println(" done!");
        } catch (SQLException e) {
            System.out.println();
            System.out.println("[database] [error] Setting auto commit failed! " + e.getMessage());
        }
    }


    @Override
    public String toString() {
        return "Datasource2{" +
                "connectionString='" + CONNECTION_STRING + '\'' +
                ", connection=" + connection +
                '}';
    }
}
