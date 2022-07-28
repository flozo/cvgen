package de.flozo.db;

import de.flozo.common.dto.latex.TikzLibrary;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TikzLibraryDAOImpl implements TikzLibraryDAO {

    // table
    public static final String TABLE_NAME = "tikz_libraries";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_INCLUDE = "include";
    public static final String COLUMN_DESCRIPTION = "description";

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
    public static final String UPDATE = "UPDATE ";
    public static final String SET = " SET ";
    public static final String DELETE_FROM = "DELETE FROM ";

    // query
    public static final String QUERY_BY_ID = SELECT + STAR + FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + TABLE_NAME + WHERE + COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + TABLE_NAME;

    public static final int NON_ID_COLUMNS = 3;

    // insert
    public static final String INSERT = INSERT_INTO + TABLE_NAME + OPENING_PARENTHESIS +
            COLUMN_NAME + COMMA +
            COLUMN_INCLUDE + COMMA +
            COLUMN_DESCRIPTION +
            CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(NON_ID_COLUMNS - 1) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ROW = UPDATE + TABLE_NAME + SET +
            COLUMN_NAME + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_INCLUDE + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_DESCRIPTION + EQUALS + QUESTION_MARK +
            WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final int UPDATE_WHERE_POSITION = NON_ID_COLUMNS + 1;

    // delete
    public static final String DELETE = DELETE_FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;

    private final Datasource2 datasource2;
    private final Connection connection;


    public TikzLibraryDAOImpl(Datasource2 datasource2, Connection connection) {
        this.datasource2 = datasource2;
        this.connection = connection;
    }

    private void showSQLMessage(String queryString) {
        System.out.println("[database] Executing SQL statement \"" + queryString + "\" ...");
    }


    @Override
    public TikzLibrary get(int id) {
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
    public TikzLibrary get(String specifier) {
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
    public List<TikzLibrary> getAll() {
        showSQLMessage(QUERY_ALL);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<TikzLibrary> tikzLibraries = new ArrayList<>();
            while (resultSet.next()) {
                tikzLibraries.add(extractFromResultSet(resultSet));
            }
            System.out.println(" done!");
            return tikzLibraries;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void add(TikzLibrary tikzLibrary) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(INSERT);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            setAllValues(preparedStatement, tikzLibrary);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource2.rollback(e, "Insert-tikzLibrary");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void update(TikzLibrary tikzLibrary) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(UPDATE_ROW);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)) {
            preparedStatement.setInt(UPDATE_WHERE_POSITION, tikzLibrary.getId());
            setAllValues(preparedStatement, tikzLibrary);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource2.rollback(e, "Update-tikzLibrary");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void delete(TikzLibrary tikzLibrary) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(DELETE);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, tikzLibrary.getId());
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (SQLException e) {
            System.out.println();
            datasource2.rollback(e, "Delete-tikzLibrary");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    private TikzLibrary extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new TikzLibrary(resultSet.getInt(1), resultSet.getString(2), resultSet.getBoolean(3), resultSet.getString(4));
    }

    private void setAllValues(PreparedStatement preparedStatement, TikzLibrary tikzLibrary) throws SQLException {
        preparedStatement.setString(1, tikzLibrary.getName());
        preparedStatement.setBoolean(2, tikzLibrary.isInclude());
        preparedStatement.setString(3, tikzLibrary.getDescription());
    }

    @Override
    public String toString() {
        return "TikzLibraryDAOImpl{" +
                "datasource2=" + datasource2 +
                ", connection=" + connection +
                '}';
    }
}
