package de.flozo.db;

import de.flozo.dto.appearance.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineStyleDAOImpl implements LineStyleDAO {

    // table
    public static final String TABLE_NAME = "line_styles";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LINE_WIDTH_ID = "line_width_id";
    public static final String COLUMN_LINE_CAP_ID = "line_cap_id";
    public static final String COLUMN_LINE_JOIN_ID = "line_join_id";
    public static final String COLUMN_DASH_PATTERN_ID = "dash_pattern_id";
    public static final String COLUMN_COLOR_ID = "color_id";
    public static final String COLUMN_OPACITY_ID = "opacity_id";

    // view (read only)
    public static final String VIEW_NAME = "line_style_view";
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

    // line_style_view created via:

    // CREATE VIEW line_style_view AS
    // SELECT ls._id, ls.name,
    //   lwv._id AS line_width_id, lwv.name AS line_width_name, lwv.value AS line_width_value, lwv.length_unit_id AS line_width_unit_id, lwv.length_unit_name AS line_width_unit_name, lwv.length_unit_value AS line_width_unit_value,
    //   lc._id AS line_cap_id, lc.name AS line_cap_name, lc.value AS line_cap_value,
    //   lj._id AS line_join_id, lj.name AS line_join_name, lj.value AS line_join_value,
    //   dp._id AS dash_pattern_id, dp.name AS dash_pattern_name,
    //   c._id AS color_id, c.name AS color_name,
    //   o._id AS opacity_id, o.value AS opacity_name
    // FROM line_styles AS ls
    // INNER JOIN line_width_view AS lwv ON ls.line_width_id = lwv._id
    // INNER JOIN line_caps AS lc ON ls.line_cap_id = lc._id
    // INNER JOIN line_joins AS lj ON ls.line_join_id = lj._id
    // INNER JOIN dash_patterns AS dp ON ls.dash_pattern_id = dp._id
    // INNER JOIN base_colors AS c ON ls.color_id = c._id
    // INNER JOIN predefined_opacities AS o ON ls.opacity_id = o._id
    public static final String QUERY_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + VIEW_NAME;

    public static final int NON_ID_COLUMNS = 7;

    // insert
    public static final String INSERT = INSERT_INTO + TABLE_NAME + OPENING_PARENTHESIS +
            COLUMN_NAME + COMMA +
            COLUMN_LINE_WIDTH_ID + COMMA +
            COLUMN_LINE_CAP_ID + COMMA +
            COLUMN_LINE_JOIN_ID + COMMA +
            COLUMN_DASH_PATTERN_ID + COMMA +
            COLUMN_COLOR_ID + COMMA +
            COLUMN_OPACITY_ID +
            CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(NON_ID_COLUMNS - 1) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ROW = UPDATE + TABLE_NAME + SET +
            COLUMN_NAME + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_LINE_WIDTH_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_LINE_CAP_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_LINE_JOIN_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_DASH_PATTERN_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_COLOR_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_OPACITY_ID + EQUALS + QUESTION_MARK +
            WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final int UPDATE_WHERE_POSITION = NON_ID_COLUMNS + 1;

    // delete
    public static final String DELETE = DELETE_FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;

    private final Datasource2 datasource2;
    private final Connection connection;


    public LineStyleDAOImpl(Datasource2 datasource2, Connection connection) {
        this.datasource2 = datasource2;
        this.connection = connection;
    }

    @Override
    public LineStyle get(int id) {
        System.out.println("[database] Executing SQL statement \"" + QUERY_BY_ID + "\" ...");
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
    public LineStyle get(String specifier) {
        System.out.println("[database] Executing SQL statement \"" + QUERY_BY_SPECIFIER + "\" ...");
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
    public List<LineStyle> getAll() {
        System.out.print("[database] Executing SQL statement \"" + QUERY_ALL + "\" ...");
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<LineStyle> lineStyles = new ArrayList<>();
            while (resultSet.next()) {
                lineStyles.add(extractFromResultSet(resultSet));
            }
            System.out.println(" done!");
            return lineStyles;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void add(LineStyle lineStyle) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        System.out.print("[database] Executing SQL statement \"" + INSERT + "\" ...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            setAllValues(preparedStatement, lineStyle);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource2.rollback(e, "Insert-lineStyle");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void update(LineStyle lineStyle) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        System.out.print("[database] Executing SQL statement \"" + UPDATE_ROW + "\" ...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)) {
            preparedStatement.setInt(UPDATE_WHERE_POSITION, lineStyle.getId());
            setAllValues(preparedStatement, lineStyle);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource2.rollback(e, "Update-lineStyle");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void delete(LineStyle lineStyle) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        System.out.print("[database] Executing SQL statement \"" + DELETE + "\" ...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, lineStyle.getId());
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (SQLException e) {
            System.out.println();
            datasource2.rollback(e, "Delete-lineStyle");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    private LineStyle extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new LineStyle(resultSet.getInt(1), resultSet.getString(2),
                new LineWidth(resultSet.getInt(3), resultSet.getString(4), resultSet.getDouble(5),
                        new LengthUnit(resultSet.getInt(6), resultSet.getString(7), resultSet.getString(8))),
                new LineCap(resultSet.getInt(9), resultSet.getString(10), resultSet.getString(11)),
                new LineJoin(resultSet.getInt(12), resultSet.getString(13), resultSet.getString(14)),
                new DashPattern(resultSet.getInt(15), resultSet.getString(16)),
                new BaseColor(resultSet.getInt(17), resultSet.getString(18)),
                new PredefinedOpacity(resultSet.getInt(19), resultSet.getString(20))
        );
    }

    private void setAllValues(PreparedStatement preparedStatement, LineStyle lineStyle) throws SQLException {
        preparedStatement.setString(1, lineStyle.getName());
        preparedStatement.setInt(2, lineStyle.getLineWidth().getId());
        preparedStatement.setInt(3, lineStyle.getLineCap().getId());
        preparedStatement.setInt(4, lineStyle.getLineJoin().getId());
        preparedStatement.setInt(5, lineStyle.getDashPattern().getId());
        preparedStatement.setInt(6, lineStyle.getBaseColor().getId());
        preparedStatement.setInt(7, lineStyle.getOpacity().getId());
    }

    @Override
    public String toString() {
        return "LineStyleDAOImpl{" +
                "datasource2=" + datasource2 +
                ", connection=" + connection +
                '}';
    }
}
