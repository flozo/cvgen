package de.flozo.db;

import de.flozo.common.dto.appearance.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RectangleDAOImpl implements RectangleDAO {

    // table
    public static final String TABLE_NAME = "rectangles";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ORIGIN_ID = "origin_id";
    public static final String COLUMN_TARGET_ID = "target_id";
    public static final String COLUMN_LINE_STYLE_ID = "line_style_id";
    public static final String COLUMN_AREA_STYLE_ID = "area_style_id";
    public static final String COLUMN_INCLUDE = "include";

    // view
    public static final String VIEW_NAME = "rectangle_view";
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

    // rectangle_view created via:

    // CREATE VIEW rectangle_view AS
    // SELECT r._id, r.name,
    //   pvo._id AS origin_id, pvo.name AS origin_name,
    //	   pvo.x_length_id AS origin_x_length_id, pvo.x_length_name AS origin_x_length_name, pvo.x_length_value AS origin_x_length_value, pvo.x_length_unit_id AS origin_x_length_unit_id, pvo.x_length_unit_name AS origin_x_length_unit_name, pvo.x_length_unit_value AS origin_x_length_unit_value,
    //	   pvo.y_length_id AS origin_y_length_id, pvo.y_length_name AS origin_y_length_name, pvo.y_length_value AS origin_y_length_value, pvo.y_length_unit_id AS origin_y_length_unit_id, pvo.y_length_unit_name AS origin_y_length_unit_name, pvo.y_length_unit_value AS origin_y_length_unit_value,
    //   pvt._id AS target_id, pvt.name AS target_name,
    //	   pvt.x_length_id AS target_x_length_id, pvt.x_length_name AS target_x_length_name, pvt.x_length_value AS target_x_length_value, pvt.x_length_unit_id AS target_x_length_unit_id, pvt.x_length_unit_name AS target_x_length_unit_name, pvt.x_length_unit_value AS target_x_length_unit_value,
    //	   pvt.y_length_id AS target_y_length_id, pvt.y_length_name AS target_y_length_name, pvt.y_length_value AS target_y_length_value, pvt.y_length_unit_id AS target_y_length_unit_id, pvt.y_length_unit_name AS target_y_length_unit_name, pvt.y_length_unit_value AS target_y_length_unit_value,
    //	 lsv._id AS line_style_id, lsv.name AS line_style_name, line_width_id, line_width_name, line_width_value, line_width_unit_id, line_width_unit_name, line_width_unit_value, line_cap_id, line_cap_name, line_cap_value, line_join_id, line_join_name, line_join_value, dash_pattern_id, dash_pattern_name, lsv.color_id AS line_color_id, lsv.color_name AS line_color_name, lsv.opacity_id AS line_opacity_id, lsv.opacity_name AS line_opacity_name,
    //	 asv._id AS area_style_id, asv.name AS area_style_name, asv.color_id AS area_color_id, asv.color_name AS area_color_name, asv.opacity_id AS area_opacity_id, asv.opacity_value AS area_opacity_value,
    //	 include
    // FROM rectangles AS r
    // INNER JOIN position_view AS pvo ON r.origin_id = pvo._id
    // INNER JOIN position_view AS pvt ON r.target_id = pvt._id
    // INNER JOIN line_style_view AS lsv ON r.line_style_id = lsv._id
    // INNER JOIN area_style_view AS asv ON r.area_style_id = asv._id

    public static final String QUERY_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + VIEW_NAME;
    public static final String QUERY_ALL_INCLUDED = SELECT + STAR + FROM + VIEW_NAME + WHERE + COLUMN_INCLUDE + EQUALS + "1";

    public static final int NON_ID_COLUMNS = 6;

    // insert
    public static final String INSERT = INSERT_INTO + TABLE_NAME + OPENING_PARENTHESIS +
            COLUMN_NAME + COMMA +
            COLUMN_ORIGIN_ID + COMMA +
            COLUMN_TARGET_ID + COMMA +
            COLUMN_LINE_STYLE_ID + COMMA +
            COLUMN_AREA_STYLE_ID + COMMA +
            COLUMN_INCLUDE +
            CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(NON_ID_COLUMNS - 1) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ROW = UPDATE + TABLE_NAME + SET +
            COLUMN_NAME + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_ORIGIN_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_TARGET_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_LINE_STYLE_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_AREA_STYLE_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_INCLUDE +
            WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final int UPDATE_WHERE_POSITION = NON_ID_COLUMNS + 1;

    // delete
    public static final String DELETE = DELETE_FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;

    private final Datasource datasource;
    private final Connection connection;

    public RectangleDAOImpl(Datasource datasource, Connection connection) {
        this.datasource = datasource;
        this.connection = connection;
    }

    private void showSQLMessage(String queryString) {
        System.out.println("[database] Executing SQL statement \"" + queryString + "\" ...");
    }


    @Override
    public Rectangle get(int id) {
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
    public Rectangle get(String specifier) {
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
    public List<Rectangle> getAll() {
        showSQLMessage(QUERY_ALL);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<Rectangle> rectangles = new ArrayList<>();
            while (resultSet.next()) {
                rectangles.add(extractFromResultSet(resultSet));
            }
            System.out.println(" done!");
            return rectangles;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Rectangle> getAllIncluded() {
        showSQLMessage(QUERY_ALL_INCLUDED);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL_INCLUDED)) {
            List<Rectangle> rectangles = new ArrayList<>();
            while (resultSet.next()) {
                rectangles.add(extractFromResultSet(resultSet));
            }
            System.out.println(" done!");
            return rectangles;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }

    }

    @Override
    public void add(Rectangle rectangle) {
        // start transaction:
        datasource.setAutoCommitBehavior(false);
        showSQLMessage(INSERT);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            setAllValues(preparedStatement, rectangle);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource.rollback(e, "Insert-rectangle");
        } finally {
            datasource.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void update(Rectangle rectangle) {
        // start transaction:
        datasource.setAutoCommitBehavior(false);
        showSQLMessage(UPDATE_ROW);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)) {
            preparedStatement.setInt(UPDATE_WHERE_POSITION, rectangle.getId());
            setAllValues(preparedStatement, rectangle);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource.rollback(e, "Update-rectangle");
        } finally {
            datasource.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void delete(Rectangle rectangle) {
        // start transaction:
        datasource.setAutoCommitBehavior(false);
        showSQLMessage(DELETE);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, rectangle.getId());
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (SQLException e) {
            System.out.println();
            datasource.rollback(e, "Delete-rectangle");
        } finally {
            datasource.setAutoCommitBehavior(true);
        }
    }

    private Rectangle extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Rectangle(resultSet.getInt(1), resultSet.getString(2),
                // origin
                new Position(resultSet.getInt(3), resultSet.getString(4),
                        // position x
                        new Length(resultSet.getInt(5), resultSet.getString(6), resultSet.getDouble(7),
                                new LengthUnit(resultSet.getInt(8), resultSet.getString(9), resultSet.getString(10))),
                        // position y
                        new Length(resultSet.getInt(11), resultSet.getString(12), resultSet.getDouble(13),
                                new LengthUnit(resultSet.getInt(14), resultSet.getString(15), resultSet.getString(16)))),
                // target
                new Position(resultSet.getInt(17), resultSet.getString(18),
                        // position x
                        new Length(resultSet.getInt(19), resultSet.getString(20), resultSet.getDouble(21),
                                new LengthUnit(resultSet.getInt(22), resultSet.getString(23), resultSet.getString(24))),
                        // position y
                        new Length(resultSet.getInt(25), resultSet.getString(26), resultSet.getDouble(27),
                                new LengthUnit(resultSet.getInt(28), resultSet.getString(29), resultSet.getString(30)))),
                new LineStyle(resultSet.getInt(31), resultSet.getString(32),
                        new LineWidth(resultSet.getInt(33), resultSet.getString(34), resultSet.getDouble(35),
                                new LengthUnit(resultSet.getInt(36), resultSet.getString(37), resultSet.getString(38))),
                        new LineCap(resultSet.getInt(39), resultSet.getString(40), resultSet.getString(41)),
                        new LineJoin(resultSet.getInt(42), resultSet.getString(43), resultSet.getString(44)),
                        new DashPattern(resultSet.getInt(45), resultSet.getString(46)),
                        new Color(resultSet.getInt(47), resultSet.getString(48)),
                        new PredefinedOpacity(resultSet.getInt(49), resultSet.getString(50))),
                new AreaStyle(resultSet.getInt(51), resultSet.getString(52),
                        new Color(resultSet.getInt(53), resultSet.getString(54)),
                        new PredefinedOpacity(resultSet.getInt(55), resultSet.getString(56))),
                resultSet.getBoolean(57)
        );
    }

    private void setAllValues(PreparedStatement preparedStatement, Rectangle rectangle) throws SQLException {
        preparedStatement.setString(1, rectangle.getName());
        preparedStatement.setInt(2, rectangle.getOrigin().getId());
        preparedStatement.setInt(3, rectangle.getTarget().getId());
        preparedStatement.setInt(4, rectangle.getLineStyle().getId());
        preparedStatement.setInt(5, rectangle.getAreaStyle().getId());
        preparedStatement.setBoolean(6, rectangle.isInclude());
    }

    @Override
    public String toString() {
        return "RectangleDAOImpl{" +
                "datasource2=" + datasource +
                ", connection=" + connection +
                '}';
    }
}
