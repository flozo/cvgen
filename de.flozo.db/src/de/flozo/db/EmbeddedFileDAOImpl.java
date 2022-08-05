package de.flozo.db;

import de.flozo.common.dto.content.EmbeddedFile;
import de.flozo.common.dto.content.File;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmbeddedFileDAOImpl implements EmbeddedFileDAO {

    // table
    public static final String TABLE_NAME = "embedded_files";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FILE_ID = "file_id";
    public static final String COLUMN_INCLUDE = "include";

    // view
    public static final String VIEW_NAME = "embedded_files_view";
    public static final String VIEW_COLUMN_ID = "_id";
    public static final String VIEW_COLUMN_DESCRIPTION = "description";

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

    // embedded_files_view created via:

    // CREATE VIEW embedded_files_view AS
    // SELECT ef._id,
    //	 f._id AS file_id, f.description AS file_description, f.path AS file_path,
    //	 ef.include
    // FROM embedded_files AS ef
    // INNER JOIN files AS f ON ef.file_id = f._id
    public static final String QUERY_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_DESCRIPTION + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + VIEW_NAME;
    public static final String QUERY_ALL_INCLUDED = SELECT + STAR + FROM + VIEW_NAME + WHERE + COLUMN_INCLUDE + EQUALS + "1";

    public static final int NON_ID_COLUMNS = 2;

    // insert
    public static final String INSERT = INSERT_INTO + TABLE_NAME + OPENING_PARENTHESIS +
            COLUMN_FILE_ID + COMMA +
            COLUMN_INCLUDE +
            CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(NON_ID_COLUMNS - 1) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ROW = UPDATE + TABLE_NAME + SET +
            COLUMN_FILE_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_INCLUDE + EQUALS + QUESTION_MARK +
            WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final int UPDATE_WHERE_POSITION = NON_ID_COLUMNS + 1;

    // delete
    public static final String DELETE = DELETE_FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;

    private final Datasource2 datasource2;
    private final Connection connection;

    public EmbeddedFileDAOImpl(Datasource2 datasource2, Connection connection) {
        this.datasource2 = datasource2;
        this.connection = connection;
    }

    private void showSQLMessage(String queryString) {
        System.out.println("[database] Executing SQL statement \"" + queryString + "\" ...");
    }

    @Override
    public EmbeddedFile get(int id) {
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
    public EmbeddedFile get(String specifier) {
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
    public List<EmbeddedFile> getAll() {
        showSQLMessage(QUERY_ALL);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<EmbeddedFile> embeddedFiles = new ArrayList<>();
            while (resultSet.next()) {
                embeddedFiles.add(extractFromResultSet(resultSet));
            }
            System.out.println(" done!");
            return embeddedFiles;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<EmbeddedFile> getAllIncluded() {
        showSQLMessage(QUERY_ALL_INCLUDED);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL_INCLUDED)) {
            List<EmbeddedFile> embeddedFiles = new ArrayList<>();
            while (resultSet.next()) {
                embeddedFiles.add(extractFromResultSet(resultSet));
            }
            System.out.println(" done!");
            return embeddedFiles;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void add(EmbeddedFile embeddedFile) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(INSERT);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            setAllValues(preparedStatement, embeddedFile);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource2.rollback(e, "Insert-embeddedFile");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void update(EmbeddedFile embeddedFile) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(UPDATE_ROW);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)) {
            preparedStatement.setInt(UPDATE_WHERE_POSITION, embeddedFile.getId());
            setAllValues(preparedStatement, embeddedFile);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource2.rollback(e, "Update-embeddedFile");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }

    }

    @Override
    public void delete(EmbeddedFile embeddedFile) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(DELETE);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, embeddedFile.getId());
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (SQLException e) {
            System.out.println();
            datasource2.rollback(e, "Delete-embeddedFile");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    private EmbeddedFile extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new EmbeddedFile(resultSet.getInt(1),
                new File(resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4)),
                resultSet.getBoolean(5)
        );
    }

    private void setAllValues(PreparedStatement preparedStatement, EmbeddedFile embeddedFile) throws SQLException {
        preparedStatement.setInt(1, embeddedFile.getFile().getId());
        preparedStatement.setBoolean(2, embeddedFile.isInclude());
    }

    @Override
    public String toString() {
        return "EmbeddedFileDAOImpl{" +
                "datasource2=" + datasource2 +
                ", connection=" + connection +
                '}';
    }
}
