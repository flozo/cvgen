package de.flozo.db;

import de.flozo.dto.appearance.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ElementStyleDAOImpl implements ElementStyleDAO {

    // table
    public static final String TABLE_NAME = "element_styles";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_POSITION_ID = "position_id";
    public static final String COLUMN_WIDTH_ID = "width_id";
    public static final String COLUMN_HEIGHT_ID = "height_id";
    public static final String COLUMN_ANCHOR_ID = "anchor_id";
    public static final String COLUMN_TEXT_STYLE_ID = "text_style_id";
    public static final String COLUMN_LINE_STYLE_ID = "line_style_id";
    public static final String COLUMN_AREA_STYLE_ID = "area_style_id";

    // view
    public static final String VIEW_NAME = "element_styles_view";
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
    public static final String INNER_JOIN = " INNER JOIN ";

    // query

    // element_styles_view created via:

    // CREATE VIEW element_styles_view AS
    // SELECT es._id, es.name,
    //	 pv._id AS position_id, pv.name AS position_name, pv.x_length_id AS position_x_length_id, pv.x_length_name AS position_x_length_name, pv.x_length_value AS position_x_length_value, pv.x_length_unit_id AS position_x_length_unit_id, pv.x_length_unit_name AS position_x_length_unit_name, pv.x_length_unit_value AS position_x_length_unit_value, pv.y_length_id AS position_y_length_id, pv.y_length_name AS position_y_length_name, pv.y_length_value AS position_y_length_value, pv.y_length_unit_id AS position_y_length_unit_id, pv.y_length_unit_name AS position_y_length_unit_name, pv.y_length_unit_value AS position_y_length_unit_value,
    //	 lvw._id AS width_id, lvw.name AS width_name, lvw.value AS width_value, lvw.length_unit_id AS width_unit_id, lvw.length_unit_name AS width_unit_name, lvw.length_unit_value AS width_unit_value,
    //	 lvh._id AS height_id, lvh.name AS height_name, lvh.value AS height_value, lvh.length_unit_id AS height_unit_id, lvh.length_unit_name AS height_unit_name, lvh.length_unit_value AS height_unit_value,
    //	 an._id AS anchor_id, an.name AS anchor_name, an.value AS anchor_value,
    //	 tsv._id AS text_style_id, tsv.name AS text_style_name, font_size_id, font_size_name, font_size_value, text_format_id, text_format_name, text_format_value, tsv.color_id AS text_color_id, tsv.color_name AS text_color_name, tsv.opacity_id AS text_opacity_id, tsv.opacity_value AS text_opacity_value,
    //	 lsv._id AS line_style_id, lsv.name AS line_style_name, line_width_id, line_width_name, line_width_value, line_width_unit_id, line_width_unit_name, line_width_unit_value, line_cap_id, line_cap_name, line_cap_value, line_join_id, line_join_name, line_join_value, dash_pattern_id, dash_pattern_name, lsv.color_id AS line_color_id, lsv.color_name AS line_color_name, lsv.opacity_id AS line_opacity_id, lsv.opacity_name AS line_opacity_name,
    //	 asv._id AS area_style_id, asv.name AS area_style_name, asv.color_id AS area_color_id, asv.color_name AS area_color_name, asv.opacity_id AS area_opacity_id, asv.opacity_value AS area_opacity_value
    // FROM element_styles AS es
    // INNER JOIN position_view AS pv ON es.position_id = pv._id
    // INNER JOIN length_view AS lvw ON es.width_id = lvw._id
    // INNER JOIN length_view AS lvh ON es.height_id = lvh._id
    // INNER JOIN anchors AS an ON es.anchor_id = an._id
    // INNER JOIN text_style_view AS tsv ON es.text_style_id = tsv._id
    // INNER JOIN line_style_view AS lsv ON es.line_style_id = lsv._id
    // INNER JOIN area_style_view AS asv ON es.area_style_id = asv._id
    public static final String QUERY_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + VIEW_NAME;

    public static final int NON_ID_COLUMNS = 8;

    // insert
    public static final String INSERT = INSERT_INTO + TABLE_NAME + OPENING_PARENTHESIS +
            COLUMN_NAME + COMMA +
            COLUMN_POSITION_ID + COMMA +
            COLUMN_WIDTH_ID + COMMA +
            COLUMN_HEIGHT_ID + COMMA +
            COLUMN_ANCHOR_ID + COMMA +
            COLUMN_TEXT_STYLE_ID + COMMA +
            COLUMN_LINE_STYLE_ID + COMMA +
            COLUMN_AREA_STYLE_ID + COMMA +
            CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(NON_ID_COLUMNS - 1) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ROW = UPDATE + TABLE_NAME + SET +
            COLUMN_NAME + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_POSITION_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_WIDTH_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_HEIGHT_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_ANCHOR_ID + EQUALS + QUESTION_MARK +
            COLUMN_TEXT_STYLE_ID + EQUALS + QUESTION_MARK +
            COLUMN_LINE_STYLE_ID + EQUALS + QUESTION_MARK +
            COLUMN_AREA_STYLE_ID + EQUALS + QUESTION_MARK +
            WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final int UPDATE_WHERE_POSITION = NON_ID_COLUMNS + 1;

    // delete
    public static final String DELETE = DELETE_FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;

    private final Datasource2 datasource2;
    private final Connection connection;


    public ElementStyleDAOImpl(Datasource2 datasource2, Connection connection) {
        this.datasource2 = datasource2;
        this.connection = connection;
    }


    private void showSQLMessage(String queryString) {
        System.out.println("[database] Executing SQL statement \"" + queryString + "\" ...");
    }


    @Override
    public ElementStyle get(int id) {
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
    public ElementStyle get(String specifier) {
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
    public List<ElementStyle> getAll() {
        showSQLMessage(QUERY_ALL);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<ElementStyle> elementStyles = new ArrayList<>();
            while (resultSet.next()) {
                elementStyles.add(extractFromResultSet(resultSet));
            }
            System.out.println(" done!");
            return elementStyles;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void add(ElementStyle elementStyle) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(INSERT);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            setAllValues(preparedStatement, elementStyle);
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
    public void update(ElementStyle elementStyle) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(UPDATE_ROW);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)) {
            preparedStatement.setInt(UPDATE_WHERE_POSITION, elementStyle.getId());
            setAllValues(preparedStatement, elementStyle);
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
    public void delete(ElementStyle elementStyle) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(DELETE);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, elementStyle.getId());
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

    private ElementStyle extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new ElementStyle(resultSet.getInt(1), resultSet.getString(2),
                new Position(resultSet.getInt(3), resultSet.getString(4),
                        // position x value
                        new Length(resultSet.getInt(5),resultSet.getString(6), resultSet.getDouble(7),
                                new LengthUnit(resultSet.getInt(8), resultSet.getString(9),resultSet.getString(10))),
                        // position y value
                        new Length(resultSet.getInt(11),resultSet.getString(12), resultSet.getDouble(13),
                                new LengthUnit(resultSet.getInt(14), resultSet.getString(15),resultSet.getString(16)))),
                // width
                new Length(resultSet.getInt(17), resultSet.getString(18),resultSet.getDouble(19),
                        new LengthUnit(resultSet.getInt(20), resultSet.getString(21), resultSet.getString(22))),
                // height
                new Length(resultSet.getInt(23), resultSet.getString(24),resultSet.getDouble(25),
                        new LengthUnit(resultSet.getInt(26), resultSet.getString(27), resultSet.getString(28))),
                new Anchor(resultSet.getInt(29), resultSet.getString(30), resultSet.getString(31)),
                new TextStyle(resultSet.getInt(32), resultSet.getString(33),
                        new FontSize(resultSet.getInt(34), resultSet.getString(35), resultSet.getString(36)),
                        new TextFormat(resultSet.getInt(37), resultSet.getString(38), resultSet.getString(39)),
                        new BaseColor(resultSet.getInt(40), resultSet.getString(41)),
                        new PredefinedOpacity(resultSet.getInt(42), resultSet.getString(43))),
                new LineStyle(resultSet.getInt(44), resultSet.getString(45),
                        new LineWidth(resultSet.getInt(46), resultSet.getString(47), resultSet.getDouble(48),
                                new LengthUnit(resultSet.getInt(49), resultSet.getString(50),resultSet.getString(51))),
                        new LineCap(resultSet.getInt(52), resultSet.getString(53), resultSet.getString(54)),
                        new LineJoin(resultSet.getInt(55), resultSet.getString(56), resultSet.getString(57)),
                        new DashPattern(resultSet.getInt(58), resultSet.getString(59)),
                        new BaseColor(resultSet.getInt(60), resultSet.getString(61)),
                        new PredefinedOpacity(resultSet.getInt(62), resultSet.getString(63))),
                new AreaStyle(resultSet.getInt(64), resultSet.getString(65),
                        new BaseColor(resultSet.getInt(66), resultSet.getString(67)),
                        new PredefinedOpacity(resultSet.getInt(68), resultSet.getString(69)))
        );
    }

    private void setAllValues(PreparedStatement preparedStatement, ElementStyle elementStyle) throws SQLException {
        preparedStatement.setString(1, elementStyle.getName());
        preparedStatement.setInt(2, elementStyle.getPosition().getId());
        preparedStatement.setInt(3, elementStyle.getWidth().getId());
        preparedStatement.setInt(4, elementStyle.getHeight().getId());
        preparedStatement.setInt(5, elementStyle.getAnchor().getId());
        preparedStatement.setInt(6, elementStyle.getTextStyle().getId());
        preparedStatement.setInt(7, elementStyle.getLineStyle().getId());
        preparedStatement.setInt(8, elementStyle.getAreaStyle().getId());
    }

    @Override
    public String toString() {
        return "ElementStyleDAOImpl{" +
                "datasource2=" + datasource2 +
                ", connection=" + connection +
                '}';
    }
}