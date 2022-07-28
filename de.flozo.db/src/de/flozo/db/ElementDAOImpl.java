package de.flozo.db;

import de.flozo.common.dto.appearance.Element;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ElementDAOImpl implements ElementDAO {

    // table
    public static final String TABLE_NAME = "elements";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ELEMENT_STYLE_ID = "element_style_id";
    public static final String COLUMN_ON_PAGE_ID = "on_page_id";
    public static final String COLUMN_INCLUDE = "include";

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

    public static final int NON_ID_COLUMNS = 4;

    // insert
    public static final String INSERT = INSERT_INTO + TABLE_NAME + OPENING_PARENTHESIS +
            COLUMN_NAME + COMMA +
            COLUMN_ELEMENT_STYLE_ID + COMMA +
            COLUMN_ON_PAGE_ID + COMMA +
            COLUMN_INCLUDE +
            CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(NON_ID_COLUMNS - 1) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ROW = UPDATE + TABLE_NAME + SET +
            COLUMN_NAME + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_ELEMENT_STYLE_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_ON_PAGE_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_INCLUDE + EQUALS + QUESTION_MARK +
            WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final int UPDATE_WHERE_POSITION = NON_ID_COLUMNS + 1;

    // delete
    public static final String DELETE = DELETE_FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;

    private final Datasource2 datasource2;
    private final Connection connection;


    public ElementDAOImpl(Datasource2 datasource2, Connection connection) {
        this.datasource2 = datasource2;
        this.connection = connection;
    }

    private void showSQLMessage(String queryString) {
        System.out.println("[database] Executing SQL statement \"" + queryString + "\" ...");
    }


    @Override
    public Element get(int id) {
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
    public Element get(String specifier) {
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
    public List<Element> getAll() {
        showSQLMessage(QUERY_ALL);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<Element> elements = new ArrayList<>();
            while (resultSet.next()) {
                elements.add(extractFromResultSet(resultSet));
            }
            System.out.println(" done!");
            return elements;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void add(Element element) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(INSERT);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            setAllValues(preparedStatement, element);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource2.rollback(e, "Insert-element");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void update(Element element) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(UPDATE_ROW);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)) {
            preparedStatement.setInt(UPDATE_WHERE_POSITION, element.getId());
            setAllValues(preparedStatement, element);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource2.rollback(e, "Update-element");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void delete(Element element) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(DELETE);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, element.getId());
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (SQLException e) {
            System.out.println();
            datasource2.rollback(e, "Delete-element");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    private Element extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Element(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4), resultSet.getBoolean(5));
    }

    private void setAllValues(PreparedStatement preparedStatement, Element element) throws SQLException {
        preparedStatement.setString(1, element.getName());
        preparedStatement.setString(2, element.getName());
        preparedStatement.setInt(3, element.getElementStyleId());
        preparedStatement.setInt(4, element.getOnPageId());
        preparedStatement.setBoolean(5, element.isInclude());
    }

    @Override
    public String toString() {
        return "ElementDAOImpl{" +
                "datasource2=" + datasource2 +
                ", connection=" + connection +
                '}';
    }
}
