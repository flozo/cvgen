package de.flozo.db;

import de.flozo.common.dto.appearance.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineDAOImpl implements LineDAO {

    // table
    public static final String TABLE_NAME = "lines";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_POSITION_ID = "position_id";
    public static final String COLUMN_LENGTH_ID = "length_id";
    public static final String COLUMN_LINE_STYLE_ID = "line_style_id";
    public static final String COLUMN_ORIENTATION = "orientation";

    // view
    public static final String VIEW_NAME = "lines_view";
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

    // lines_view created via:

    // CREATE VIEW lines_view AS
    // SELECT l._id, l.name,
    //   pv._id AS position_id, pv.name AS position_name,
    //     pv.x_length_id, pv.x_length_name, pv.x_length_value, pv.x_length_unit_id, pv.x_length_unit_name, pv.x_length_unit_value,
    //     pv.y_length_id, pv.y_length_name, pv.y_length_value, pv.y_length_unit_id, pv.y_length_unit_name, pv.y_length_unit_value,
    //   lv._id AS length_id, lv.name AS length_name, lv.value AS length_value, lv.length_unit_id, lv.length_unit_name, lv.length_unit_value,
    //   lsv._id AS line_style_id, lsv.name AS line_style_name, lsv.line_width_id, lsv.line_width_name, lsv.line_width_value, lsv.line_width_unit_id, lsv.line_width_unit_name, lsv.line_width_unit_value,
    //     lsv.line_cap_id, lsv.line_cap_name, lsv.line_cap_value,
    //     lsv.line_join_id, lsv.line_join_name, lsv.line_join_value,
    //     lsv.dash_pattern_id, lsv.dash_pattern_name,
    //     lsv.color_id, lsv.color_name,
    //     lsv.opacity_id, lsv.opacity_name,
    //     l.orientation
    // FROM lines AS l
    // INNER JOIN position_view AS pv ON l.position_id = pv._id
    // INNER JOIN length_view AS lv ON l.length_id = lv._id
    // INNER JOIN line_style_view AS lsv ON l.line_style_id = lsv._id
    public static final String QUERY_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + VIEW_NAME;

    public static final int NON_ID_COLUMNS = 5;

    // insert
    public static final String INSERT = INSERT_INTO + TABLE_NAME + OPENING_PARENTHESIS +
            COLUMN_NAME + COMMA +
            COLUMN_POSITION_ID + COMMA +
            COLUMN_LENGTH_ID + COMMA +
            COLUMN_LINE_STYLE_ID + COMMA +
            COLUMN_ORIENTATION +
            CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(NON_ID_COLUMNS - 1) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ROW = UPDATE + TABLE_NAME + SET +
            COLUMN_NAME + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_POSITION_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_LENGTH_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_LINE_STYLE_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_ORIENTATION + EQUALS + QUESTION_MARK +
            WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final int UPDATE_WHERE_POSITION = NON_ID_COLUMNS + 1;

    // delete
    public static final String DELETE = DELETE_FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;

    private final Datasource2 datasource2;
    private final Connection connection;

    public LineDAOImpl(Datasource2 datasource2, Connection connection) {
        this.datasource2 = datasource2;
        this.connection = connection;
    }

    private void showSQLMessage(String queryString) {
        System.out.println("[database] Executing SQL statement \"" + queryString + "\" ...");
    }


    @Override
    public Line get(int id) {
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
    public Line get(String specifier) {
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
    public List<Line> getAll() {
        showSQLMessage(QUERY_ALL);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<Line> lines = new ArrayList<>();
            while (resultSet.next()) {
                lines.add(extractFromResultSet(resultSet));
            }
            return lines;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void add(Line line) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(INSERT);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            setAllValues(preparedStatement, line);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
        } catch (Exception e) {
            datasource2.rollback(e, "Insert-line");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void update(Line line) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(UPDATE_ROW);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)) {
            preparedStatement.setInt(UPDATE_WHERE_POSITION, line.getId());
            setAllValues(preparedStatement, line);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
        } catch (Exception e) {
            datasource2.rollback(e, "Update-line");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void delete(Line line) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(DELETE);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, line.getId());
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
        } catch (SQLException e) {
            datasource2.rollback(e, "Delete-line");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    private Line extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Line(resultSet.getInt(1), resultSet.getString(2),
                new Position(resultSet.getInt(3), resultSet.getString(4),
                        // position x
                        new Length(resultSet.getInt(5), resultSet.getString(6), resultSet.getDouble(7),
                                new LengthUnit(resultSet.getInt(8), resultSet.getString(9), resultSet.getString(10))),
                        // position y
                        new Length(resultSet.getInt(11), resultSet.getString(12), resultSet.getDouble(13),
                                new LengthUnit(resultSet.getInt(14), resultSet.getString(15), resultSet.getString(16)))),
                new Length(resultSet.getInt(17), resultSet.getString(18), resultSet.getDouble(19),
                        new LengthUnit(resultSet.getInt(20), resultSet.getString(21), resultSet.getString(22))),
                new LineStyle(resultSet.getInt(23), resultSet.getString(24),
                        new LineWidth(resultSet.getInt(25), resultSet.getString(26), resultSet.getDouble(27),
                                new LengthUnit(resultSet.getInt(28), resultSet.getString(29), resultSet.getString(30))),
                        new LineCap(resultSet.getInt(31), resultSet.getString(32), resultSet.getString(33)),
                        new LineJoin(resultSet.getInt(34), resultSet.getString(35), resultSet.getString(36)),
                        new DashPattern(resultSet.getInt(37), resultSet.getString(38)),
                        new BaseColor(resultSet.getInt(39), resultSet.getString(40)),
                        new PredefinedOpacity(resultSet.getInt(41), resultSet.getString(42))),
                resultSet.getString(43)
        );
    }

    private void setAllValues(PreparedStatement preparedStatement, Line line) throws SQLException {
        preparedStatement.setString(1, line.getName());
        preparedStatement.setInt(2, line.getPosition().getId());
        preparedStatement.setInt(3, line.getLength().getId());
        preparedStatement.setInt(4, line.getLineStyle().getId());
        preparedStatement.setString(5, line.getOrientation());
    }

    @Override
    public String toString() {
        return "LineDAOImpl{" +
                "datasource2=" + datasource2 +
                ", connection=" + connection +
                '}';
    }
}
