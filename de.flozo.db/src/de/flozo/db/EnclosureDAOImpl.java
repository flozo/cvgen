package de.flozo.db;

import de.flozo.common.dto.content.Enclosure;
import de.flozo.common.dto.content.File;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnclosureDAOImpl implements EnclosureDAO {

    // table
    public static final String TABLE_NAME = "enclosures";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CAPTION = "caption";
    public static final String COLUMN_FILE_ID = "file_id";
    public static final String COLUMN_INCLUDE_CAPTION = "include_caption";
    public static final String COLUMN_INCLUDE_FILE = "include_file";

    // view
    public static final String VIEW_NAME = "enclosures_view";
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
    public static final String UPDATE = "UPDATE ";
    public static final String SET = " SET ";
    public static final String DELETE_FROM = "DELETE FROM ";

    // query

    // enclosures_view created via:

    // CREATE VIEW enclosures_view AS
    // SELECT e._id, e.name, e.caption,
    //	 f._id AS file_id, f.description AS file_description, f.path AS file_path,
    //	 e.include_caption, e.include_file
    // FROM enclosures AS e
    // INNER JOIN files AS f ON e.file_id = f._id
    public static final String QUERY_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + VIEW_NAME;
    public static final String QUERY_ALL_INCLUDED = SELECT + STAR + FROM + VIEW_NAME + WHERE + COLUMN_INCLUDE_CAPTION + EQUALS + "1";

    public static final int NON_ID_COLUMNS = 5;

    // insert
    public static final String INSERT = INSERT_INTO + TABLE_NAME + OPENING_PARENTHESIS +
            COLUMN_NAME + COMMA +
            COLUMN_CAPTION + COMMA +
            COLUMN_FILE_ID + COMMA +
            COLUMN_INCLUDE_CAPTION + COMMA +
            COLUMN_INCLUDE_FILE +
            CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(NON_ID_COLUMNS - 1) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ROW = UPDATE + TABLE_NAME + SET +
            COLUMN_NAME + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_CAPTION + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_FILE_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_INCLUDE_CAPTION + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_INCLUDE_FILE + EQUALS + QUESTION_MARK +
            WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final int UPDATE_WHERE_POSITION = NON_ID_COLUMNS + 1;

    // delete
    public static final String DELETE = DELETE_FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;

    private final Datasource2 datasource2;
    private final Connection connection;


    public EnclosureDAOImpl(Datasource2 datasource2, Connection connection) {
        this.datasource2 = datasource2;
        this.connection = connection;
    }

    private void showSQLMessage(String queryString) {
        System.out.println("[database] Executing SQL statement \"" + queryString + "\" ...");
    }

    @Override
    public Enclosure get(int id) {
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
    public Enclosure get(String specifier) {
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
    public List<Enclosure> getAll() {
        showSQLMessage(QUERY_ALL);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<Enclosure> enclosures = new ArrayList<>();
            while (resultSet.next()) {
                enclosures.add(extractFromResultSet(resultSet));
            }
            return enclosures;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Enclosure> getAllIncluded() {
        showSQLMessage(QUERY_ALL_INCLUDED);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL_INCLUDED)) {
            List<Enclosure> enclosures = new ArrayList<>();
            while (resultSet.next()) {
                enclosures.add(extractFromResultSet(resultSet));
            }
            return enclosures;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void add(Enclosure enclosure) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(INSERT);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            setAllValues(preparedStatement, enclosure);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
        } catch (Exception e) {
            datasource2.rollback(e, "Insert-enclosure");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void update(Enclosure enclosure) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(UPDATE_ROW);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)) {
            preparedStatement.setInt(UPDATE_WHERE_POSITION, enclosure.getId());
            setAllValues(preparedStatement, enclosure);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
        } catch (Exception e) {
            datasource2.rollback(e, "Update-enclosure");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void delete(Enclosure enclosure) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(DELETE);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, enclosure.getId());
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
        } catch (SQLException e) {
            datasource2.rollback(e, "Delete-enclosure");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    private Enclosure extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Enclosure(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                new File(resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6)),
                resultSet.getBoolean(7), resultSet.getBoolean(8)
        );
    }

    private void setAllValues(PreparedStatement preparedStatement, Enclosure enclosure) throws SQLException {
        preparedStatement.setString(1, enclosure.getName());
        preparedStatement.setString(2, enclosure.getCaption());
        preparedStatement.setInt(3, enclosure.getFile().getId());
        preparedStatement.setBoolean(4, enclosure.isIncludeCaption());
        preparedStatement.setBoolean(5, enclosure.isIncludeFile());
    }

    @Override
    public String toString() {
        return "EnclosureDAOImpl{" +
                "datasource2=" + datasource2 +
                ", connection=" + connection +
                '}';
    }
}
