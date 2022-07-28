package de.flozo.db;

import de.flozo.common.dto.latex.DocumentClass;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentClassDAOImpl implements DocumentClassDAO {

    // table
    public static final String TABLE_NAME = "document_classes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_INCLUDE = "include";

    // view
    public static final String VIEW_NAME = "document_class_view";
    public static final String VIEW_COLUMN_ID = "_id";
    public static final String VIEW_COLUMN_NAME = "name";
    public static final String VIEW_COLUMN_VALUE = "value";
    public static final String VIEW_COLUMN_INCLUDE = "include";
    public static final String VIEW_COLUMN_OPTION_LIST = "option_list";

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

    // document_class_view created via:

    // CREATE VIEW document_class_view AS
    // SELECT dc._id, dc.name, dc.value, dc.include,
    //   group_concat(dco.option_string, ", ") AS option_list
    // FROM document_classes AS dc
    // LEFT JOIN (SELECT * FROM document_class_options AS dco WHERE dco.include = 1) AS dco
    // ON dco.document_class_id = dc._id
    // WHERE dc.value IS NOT NULL
    // GROUP BY dc._id
    // ORDER BY dco._id
    public static final String QUERY_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + VIEW_NAME;
    public static final String QUERY_ALL_INCLUDED = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_INCLUDE + EQUALS + "1";

    public static final int NON_ID_COLUMNS = 3;

    // insert
    public static final String INSERT = INSERT_INTO + TABLE_NAME + OPENING_PARENTHESIS +
            COLUMN_NAME + COMMA +
            COLUMN_VALUE + COMMA +
            COLUMN_INCLUDE +
            CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(NON_ID_COLUMNS - 1) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ROW = UPDATE + TABLE_NAME + SET +
            COLUMN_NAME + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_VALUE + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_INCLUDE + EQUALS + QUESTION_MARK +
            WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final int UPDATE_WHERE_POSITION = NON_ID_COLUMNS + 1;

    // delete
    public static final String DELETE = DELETE_FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;

    private final Datasource2 datasource2;
    private final Connection connection;


    public DocumentClassDAOImpl(Datasource2 datasource2, Connection connection) {
        this.datasource2 = datasource2;
        this.connection = connection;
    }

    private void showSQLMessage(String queryString) {
        System.out.println("[database] Executing SQL statement \"" + queryString + "\" ...");
    }


    @Override
    public DocumentClass get(int id) {
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
    public DocumentClass get(String specifier) {
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
    public List<DocumentClass> getAll() {
        showSQLMessage(QUERY_ALL);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<DocumentClass> documentClasses = new ArrayList<>();
            while (resultSet.next()) {
                documentClasses.add(extractFromResultSet(resultSet));
            }
            System.out.println(" done!");
            return documentClasses;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<DocumentClass> getAllIncluded() {
        showSQLMessage(QUERY_ALL_INCLUDED);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL_INCLUDED)) {
            List<DocumentClass> documentClasses = new ArrayList<>();
            while (resultSet.next()) {
                documentClasses.add(extractFromResultSet(resultSet));
            }
            System.out.println(" done!");
            return documentClasses;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }


    @Override
    public void add(DocumentClass documentClass) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(INSERT);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            setAllValues(preparedStatement, documentClass);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource2.rollback(e, "Insert-documentClass");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void update(DocumentClass documentClass) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(UPDATE_ROW);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)) {
            preparedStatement.setInt(UPDATE_WHERE_POSITION, documentClass.getId());
            setAllValues(preparedStatement, documentClass);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource2.rollback(e, "Update-documentClass");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void delete(DocumentClass documentClass) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(DELETE);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, documentClass.getId());
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (SQLException e) {
            System.out.println();
            datasource2.rollback(e, "Delete-documentClass");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    private DocumentClass extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new DocumentClass(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5));
    }

    private void setAllValues(PreparedStatement preparedStatement, DocumentClass documentClass) throws SQLException {
        preparedStatement.setString(1, documentClass.getName());
        preparedStatement.setString(2, documentClass.getValue());
    }

    @Override
    public String toString() {
        return "DocumentClassDAOImpl{" +
                "datasource2=" + datasource2 +
                ", connection=" + connection +
                '}';
    }
}
