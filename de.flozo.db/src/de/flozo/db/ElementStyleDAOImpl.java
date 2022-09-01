package de.flozo.db;

import de.flozo.common.dto.appearance.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ElementStyleDAOImpl implements ElementStyleDAO {

    // table
    public static final String TABLE_NAME = "element_styles";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
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

    // query

    // element_styles_view created via:

    // CREATE VIEW element_styles_view AS
    // SELECT es._id, es.name,
    //   tsv._id AS text_style_id, tsv.name AS text_style_name, font_size_id, font_size_name, font_size_value, text_format_id, text_format_name, text_format_value,
    //     text_width_id, text_width_name, text_width_value, text_width_unit_id, text_width_unit_name, text_width_unit_value,
    //     text_height_id, text_height_name, text_height_value, text_height_unit_id, text_height_unit_name, text_height_unit_value,
    //     text_depth_id, text_depth_name, text_depth_value, text_depth_unit_id, text_depth_unit_name, text_depth_unit_value,
    //   alignment_id, alignment_name, alignment_value, tsv.color_id AS text_color_id, tsv.color_name AS text_color_name, tsv.opacity_id AS text_opacity_id, tsv.opacity_value AS text_opacity_value,
    //   lsv._id AS line_style_id, lsv.name AS line_style_name, line_width_id, line_width_name, line_width_value, line_width_unit_id, line_width_unit_name, line_width_unit_value, line_cap_id, line_cap_name, line_cap_value, line_join_id, line_join_name, line_join_value, dash_pattern_id, dash_pattern_name, lsv.color_id AS line_color_id, lsv.color_name AS line_color_name, lsv.opacity_id AS line_opacity_id, lsv.opacity_name AS line_opacity_name,
    //   asv._id AS area_style_id, asv.name AS area_style_name, asv.color_id AS area_color_id, asv.color_name AS area_color_name, asv.opacity_id AS area_opacity_id, asv.opacity_value AS area_opacity_value
    // FROM element_styles AS es
    // INNER JOIN text_style_view AS tsv ON es.text_style_id = tsv._id
    // INNER JOIN line_style_view AS lsv ON es.line_style_id = lsv._id
    // INNER JOIN area_style_view AS asv ON es.area_style_id = asv._id
    public static final String QUERY_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + VIEW_NAME;

    public static final int NON_ID_COLUMNS = 4;

    // insert
    public static final String INSERT = INSERT_INTO + TABLE_NAME + OPENING_PARENTHESIS +
            COLUMN_NAME + COMMA +
            COLUMN_TEXT_STYLE_ID + COMMA +
            COLUMN_LINE_STYLE_ID + COMMA +
            COLUMN_AREA_STYLE_ID +
            CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(NON_ID_COLUMNS - 1) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ROW = UPDATE + TABLE_NAME + SET +
            COLUMN_NAME + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_TEXT_STYLE_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_LINE_STYLE_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_AREA_STYLE_ID + EQUALS + QUESTION_MARK +
            WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final int UPDATE_WHERE_POSITION = NON_ID_COLUMNS + 1;

    // delete
    public static final String DELETE = DELETE_FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;

    private final Datasource datasource;
    private final Connection connection;


    public ElementStyleDAOImpl(Datasource datasource, Connection connection) {
        this.datasource = datasource;
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
        datasource.setAutoCommitBehavior(false);
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
            datasource.rollback(e, "Insert-element");
        } finally {
            datasource.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void update(ElementStyle elementStyle) {
        // start transaction:
        datasource.setAutoCommitBehavior(false);
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
            datasource.rollback(e, "Update-element");
        } finally {
            datasource.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void delete(ElementStyle elementStyle) {
        // start transaction:
        datasource.setAutoCommitBehavior(false);
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
            datasource.rollback(e, "Delete-element");
        } finally {
            datasource.setAutoCommitBehavior(true);
        }
    }

    private ElementStyle extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new ElementStyle(resultSet.getInt(1), resultSet.getString(2),
                new TextStyle(resultSet.getInt(3), resultSet.getString(4),
                        new FontSize(resultSet.getInt(5), resultSet.getString(6), resultSet.getString(7)),
                        new TextFormat(resultSet.getInt(8), resultSet.getString(9), resultSet.getString(10)),
                        new Length(resultSet.getInt(11), resultSet.getString(12), resultSet.getDouble(13),
                                new LengthUnit(resultSet.getInt(14), resultSet.getString(15), resultSet.getString(16))),
                        new Length(resultSet.getInt(17), resultSet.getString(18), resultSet.getDouble(19),
                                new LengthUnit(resultSet.getInt(20), resultSet.getString(21), resultSet.getString(22))),
                        new Length(resultSet.getInt(23), resultSet.getString(24), resultSet.getDouble(25),
                                new LengthUnit(resultSet.getInt(26), resultSet.getString(27), resultSet.getString(28))),
                        new Alignment(resultSet.getInt(29), resultSet.getString(30), resultSet.getString(31)),
                        new Color(resultSet.getInt(32), resultSet.getString(33)),
                        new PredefinedOpacity(resultSet.getInt(34), resultSet.getString(35))),
                new LineStyle(resultSet.getInt(36), resultSet.getString(37),
                        new LineWidth(resultSet.getInt(38), resultSet.getString(39), resultSet.getDouble(40),
                                new LengthUnit(resultSet.getInt(41), resultSet.getString(42), resultSet.getString(43))),
                        new LineCap(resultSet.getInt(44), resultSet.getString(45), resultSet.getString(46)),
                        new LineJoin(resultSet.getInt(47), resultSet.getString(48), resultSet.getString(49)),
                        new DashPattern(resultSet.getInt(50), resultSet.getString(51)),
                        new Color(resultSet.getInt(52), resultSet.getString(53)),
                        new PredefinedOpacity(resultSet.getInt(54), resultSet.getString(55))),
                new AreaStyle(resultSet.getInt(56), resultSet.getString(57),
                        new Color(resultSet.getInt(58), resultSet.getString(59)),
                        new PredefinedOpacity(resultSet.getInt(60), resultSet.getString(61)))
        );
    }

    private void setAllValues(PreparedStatement preparedStatement, ElementStyle elementStyle) throws SQLException {
        preparedStatement.setString(1, elementStyle.getName());
        preparedStatement.setInt(2, elementStyle.getTextStyle().getId());
        preparedStatement.setInt(3, elementStyle.getLineStyle().getId());
        preparedStatement.setInt(4, elementStyle.getAreaStyle().getId());
    }

    @Override
    public String toString() {
        return "ElementStyleDAOImpl{" +
                "datasource2=" + datasource +
                ", connection=" + connection +
                '}';
    }
}
