package de.flozo.db;

import de.flozo.common.dto.appearance.LengthUnit;
import de.flozo.common.dto.appearance.LineWidth;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineWidthDAOImpl implements LineWidthDAO {

    // table
    public static final String TABLE_NAME = "line_widths";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_LENGTH_UNIT_ID = "length_unit_id";

    // view (read only)
    public static final String VIEW_NAME = "line_width_view";
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

    // line_width_view created via:

    // CREATE VIEW line_width_view AS
    // SELECT lw._id, lw.name, lw.value,
    //   lu._id AS length_unit_id, lu.name AS length_unit_name, lu.value AS length_unit_value
    // FROM line_widths AS lw
    // INNER JOIN length_units AS lu ON lw.length_unit_id = lu._id

    public static final String QUERY_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + VIEW_NAME;

    public static final int NON_ID_COLUMNS = 3;

    // insert
    public static final String INSERT = INSERT_INTO + TABLE_NAME + OPENING_PARENTHESIS +
            COLUMN_NAME + COMMA +
            COLUMN_VALUE + COMMA +
            COLUMN_LENGTH_UNIT_ID +
            CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(NON_ID_COLUMNS - 1) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ROW = UPDATE + TABLE_NAME + SET +
            COLUMN_NAME + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_VALUE + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_LENGTH_UNIT_ID + EQUALS + QUESTION_MARK +
            WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final int UPDATE_WHERE_POSITION = NON_ID_COLUMNS + 1;


    // delete
    public static final String DELETE = DELETE_FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;


    private final Datasource datasource;
    private final Connection connection;


    public LineWidthDAOImpl(Datasource datasource, Connection connection) {
        this.datasource = datasource;
        this.connection = connection;
    }

    @Override
    public LineWidth get(int id) {
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
    public LineWidth get(String specifier) {
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
    public List<LineWidth> getAll() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<LineWidth> lineWidths = new ArrayList<>();
            while (resultSet.next()) {
                lineWidths.add(extractFromResultSet(resultSet));
            }
            return lineWidths;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void add(LineWidth lineWidth) {
        // start transaction:
        datasource.setAutoCommitBehavior(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            setAllValues(preparedStatement, lineWidth);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
        } catch (Exception e) {
            datasource.rollback(e, "Insert-lineWidth");
        } finally {
            datasource.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void update(LineWidth lineWidth) {
        // start transaction:
        datasource.setAutoCommitBehavior(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)) {
            preparedStatement.setInt(UPDATE_WHERE_POSITION, lineWidth.getId());
            setAllValues(preparedStatement, lineWidth);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
        } catch (Exception e) {
            datasource.rollback(e, "Update-lineWidth");
        } finally {
            datasource.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void delete(LineWidth lineWidth) {
        // start transaction:
        datasource.setAutoCommitBehavior(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, lineWidth.getId());
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
        } catch (SQLException e) {
            datasource.rollback(e, "Delete-lineWidth");
        } finally {
            datasource.setAutoCommitBehavior(true);
        }
    }

    private LineWidth extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new LineWidth(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3),
                new LengthUnit(resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6)));
    }

    private void setAllValues(PreparedStatement preparedStatement, LineWidth lineWidth) throws SQLException {
        preparedStatement.setString(1, lineWidth.getName());
        preparedStatement.setDouble(2, lineWidth.getValue());
        preparedStatement.setInt(3, lineWidth.getUnit().getId());
    }

    @Override
    public String toString() {
        return "LineWidthDAOImpl{" +
                "datasource2=" + datasource +
                ", connection=" + connection +
                '}';
    }
}
