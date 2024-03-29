package de.flozo.db;

import de.flozo.common.dto.appearance.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TextStyleDAOImpl implements TextStyleDAO {

    // table
    public static final String TABLE_NAME = "text_styles";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_FONT_SIZE_ID = "font_size_id";
    public static final String COLUMN_TEXT_FORMAT_ID = "text_format_id";
    public static final String COLUMN_TEXT_WIDTH_ID = "text_width_id";
    public static final String COLUMN_TEXT_HEIGHT_ID = "text_height_id";
    public static final String COLUMN_TEXT_DEPTH_ID = "text_depth_id";
    public static final String COLUMN_ALIGNMENT_ID = "alignment_id";
    public static final String COLUMN_COLOR_ID = "color_id";
    public static final String COLUMN_OPACITY_ID = "opacity_id";

    // view
    public static final String VIEW_NAME = "text_style_view";
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

    // text_style_view created via:

    // CREATE VIEW text_style_view AS
    // SELECT ts._id, ts.name,
    //   fs._id AS font_size_id, fs.name AS font_size_name, fs.value AS font_size_value,
    //   tf._id AS text_format_id, tf.name AS text_format_name, tf.value AS text_format_value,
    //   lvtw._id AS text_width_id, lvtw.name AS text_width_name, lvtw.value AS text_width_value, lvtw.length_unit_id AS text_width_unit_id, lvtw.length_unit_name AS text_width_unit_name, lvtw.length_unit_value AS text_width_unit_value,
    //   lvth._id AS text_height_id, lvth.name AS text_height_name, lvth.value AS text_height_value, lvth.length_unit_id AS text_height_unit_id, lvth.length_unit_name AS text_height_unit_name, lvth.length_unit_value AS text_height_unit_value,
    //   lvtd._id AS text_depth_id, lvtd.name AS text_depth_name, lvtd.value AS text_depth_value, lvtd.length_unit_id AS text_depth_unit_id, lvtd.length_unit_name AS text_depth_unit_name, lvtd.length_unit_value AS text_depth_unit_value,
    //   a._id AS alignment_id, a.name AS alignment_name, a.value AS alignment_value,
    //   c._id AS color_id, c.color_string AS color_name,
    //   o._id AS opacity_id, o.value AS opacity_value
    // FROM text_styles AS ts
    // INNER JOIN font_sizes AS fs ON ts.font_size_id = fs._id
    // INNER JOIN text_formats AS tf ON ts.text_format_id = tf._id
    // INNER JOIN length_view AS lvtw ON ts.text_width_id = lvtw._id
    // INNER JOIN length_view AS lvth ON ts.text_height_id = lvth._id
    // INNER JOIN length_view AS lvtd ON ts.text_depth_id = lvtd._id
    // INNER JOIN alignments AS a ON ts.alignment_id = a._id
    // INNER JOIN colors AS c ON ts.color_id = c._id
    // INNER JOIN predefined_opacities AS o ON ts.opacity_id = o._id

    public static final String QUERY_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + VIEW_NAME;

    public static final int NON_ID_COLUMNS = 9;

    // insert
    public static final String INSERT = INSERT_INTO + TABLE_NAME + OPENING_PARENTHESIS +
            COLUMN_NAME + COMMA +
            COLUMN_FONT_SIZE_ID + COMMA +
            COLUMN_TEXT_FORMAT_ID + COMMA +
            COLUMN_TEXT_WIDTH_ID + COMMA +
            COLUMN_TEXT_HEIGHT_ID + COMMA +
            COLUMN_TEXT_DEPTH_ID + COMMA +
            COLUMN_ALIGNMENT_ID + COMMA +
            COLUMN_COLOR_ID + COMMA +
            COLUMN_OPACITY_ID +
            CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(NON_ID_COLUMNS - 1) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ROW = UPDATE + TABLE_NAME + SET +
            COLUMN_NAME + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_FONT_SIZE_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_TEXT_FORMAT_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_TEXT_WIDTH_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_TEXT_HEIGHT_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_TEXT_DEPTH_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_ALIGNMENT_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_COLOR_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_OPACITY_ID + EQUALS + QUESTION_MARK +
            WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final int UPDATE_WHERE_POSITION = NON_ID_COLUMNS + 1;

    // delete
    public static final String DELETE = DELETE_FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;

    private final Datasource datasource;
    private final Connection connection;


    public TextStyleDAOImpl(Datasource datasource, Connection connection) {
        this.datasource = datasource;
        this.connection = connection;
    }

    @Override
    public TextStyle get(int id) {
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
    public TextStyle get(String specifier) {
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
    public List<TextStyle> getAll() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<TextStyle> textStyles = new ArrayList<>();
            while (resultSet.next()) {
                textStyles.add(extractFromResultSet(resultSet));
            }
            return textStyles;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void add(TextStyle textStyle) {
        // start transaction:
        datasource.setAutoCommitBehavior(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            setAllValues(preparedStatement, textStyle);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
        } catch (Exception e) {
            datasource.rollback(e, "Insert-textStyle");
        } finally {
            datasource.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void update(TextStyle textStyle) {
        // start transaction:
        datasource.setAutoCommitBehavior(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)) {
            preparedStatement.setInt(UPDATE_WHERE_POSITION, textStyle.getId());
            setAllValues(preparedStatement, textStyle);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
        } catch (Exception e) {
            datasource.rollback(e, "Update-textStyle");
        } finally {
            datasource.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void delete(TextStyle textStyle) {
        // start transaction:
        datasource.setAutoCommitBehavior(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, textStyle.getId());
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
        } catch (SQLException e) {
            datasource.rollback(e, "Delete-textStyle");
        } finally {
            datasource.setAutoCommitBehavior(true);
        }
    }

    private TextStyle extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new TextStyle(resultSet.getInt(1), resultSet.getString(2),
                new FontSize(resultSet.getInt(3), resultSet.getString(4), resultSet.getString(5)),
                new TextFormat(resultSet.getInt(6), resultSet.getString(7), resultSet.getString(8)),
                new Length(resultSet.getInt(9), resultSet.getString(10), resultSet.getDouble(11),
                        new LengthUnit(resultSet.getInt(12), resultSet.getString(13), resultSet.getString(14))),
                new Length(resultSet.getInt(15), resultSet.getString(16), resultSet.getDouble(17),
                        new LengthUnit(resultSet.getInt(18), resultSet.getString(19), resultSet.getString(20))),
                new Length(resultSet.getInt(21), resultSet.getString(22), resultSet.getDouble(23),
                        new LengthUnit(resultSet.getInt(24), resultSet.getString(25), resultSet.getString(26))),
                new Alignment(resultSet.getInt(27), resultSet.getString(28), resultSet.getString(29)),
                new Color(resultSet.getInt(30), resultSet.getString(31)),
                new PredefinedOpacity(resultSet.getInt(32), resultSet.getString(33))
        );
    }

    private void setAllValues(PreparedStatement preparedStatement, TextStyle textStyle) throws SQLException {
        preparedStatement.setString(1, textStyle.getName());
        preparedStatement.setInt(2, textStyle.getFontSize().getId());
        preparedStatement.setInt(3, textStyle.getTextFormat().getId());
        preparedStatement.setInt(4, textStyle.getTextWidth().getId());
        preparedStatement.setInt(5, textStyle.getTextHeight().getId());
        preparedStatement.setInt(6, textStyle.getTextDepth().getId());
        preparedStatement.setInt(7, textStyle.getAlignment().getId());
        preparedStatement.setInt(8, textStyle.getColor().getId());
        preparedStatement.setInt(9, textStyle.getOpacity().getId());
    }

    @Override
    public String toString() {
        return "TextStyleDAOImpl{" +
                "datasource2=" + datasource +
                ", connection=" + connection +
                '}';
    }
}
