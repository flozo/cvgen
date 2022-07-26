package de.flozo.db;

import de.flozo.dto.latex.LatexCommand;
import de.flozo.dto.latex.LatexPackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LatexCommandDAOImpl implements LatexCommandDAO {

    // table
    public static final String TABLE_NAME = "latex_commands";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    // view
    public static final String VIEW_NAME = "latex_command_view";
    public static final String VIEW_COLUMN_ID = "_id";
    public static final String VIEW_COLUMN_NAME = "name";


    // sql
    public static final char OPENING_PARENTHESIS = '(';
    public static final char CLOSING_PARENTHESIS = ')';
    public static final char QUESTION_MARK = '?';
    public static final char STAR = '*';
    public static final String COMMA = ", ";
    public static final String INSERT_INTO = "INSERT INTO ";
    public static final String VALUES = " VALUES ";
    public static final String SELECT = "SELECT ";
    public static final String FROM = " FROM ";
    public static final String WHERE = " WHERE ";
    public static final String EQUALS = " = ";

    // query

    // latex_command_view created via:

    // CREATE VIEW latex_command_view AS
    // SELECT lc._id, lc.name,
    //   lp._id AS package_id, lp.name AS package_name, lp.value AS package_value
    // FROM latex_commands AS lc
    // INNER JOIN latex_packages AS lp ON lc.needs_package = lp._id

    public static final String QUERY_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + VIEW_NAME;


    private final Datasource2 datasource2;
    private final Connection connection;


    public LatexCommandDAOImpl(Datasource2 datasource2, Connection connection) {
        this.datasource2 = datasource2;
        this.connection = connection;
    }

    private void showSQLMessage(String queryString) {
        System.out.println("[database] Executing SQL statement \"" + queryString + "\" ...");
    }



    @Override
    public LatexCommand get(int id) {
        showSQLMessage(QUERY_BY_ID);
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractFromResultSet(resultSet);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public LatexCommand get(String specifier) {
        showSQLMessage(QUERY_BY_SPECIFIER);
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_BY_SPECIFIER)) {
            preparedStatement.setString(1, specifier);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractFromResultSet(resultSet);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<LatexCommand> getAll() {
        showSQLMessage(QUERY_ALL);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<LatexCommand> latexCommands = new ArrayList<>();
            while (resultSet.next()) {
                latexCommands.add(extractFromResultSet(resultSet));
            }
            return latexCommands;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    private LatexCommand extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new LatexCommand(resultSet.getInt(1), resultSet.getString(2),
                new LatexPackage(resultSet.getInt(3), resultSet.getString(4), resultSet.getString(5))
        );
    }

    @Override
    public String toString() {
        return "LatexCommandDAOImpl{" +
                "datasource2=" + datasource2 +
                ", connection=" + connection +
                '}';
    }
}
