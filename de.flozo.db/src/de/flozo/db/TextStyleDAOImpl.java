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
    public static final String COLUMN_COLOR_ID = "color_id";
    public static final String COLUMN_OPACITY_ID = "opacity_id";

    // view (read only)
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
    public static final String INNER_JOIN = " INNER JOIN ";


    // query

    // text_style_view created via:

    // CREATE VIEW text_style_view AS SELECT ts._id, ts.name,
    // fs._id AS font_size_id, fs.name AS font_size_name, fs.value AS font_size_value,
    // tf._id AS text_format_id, tf.name AS text_format_name, tf.value AS text_format_value,
    // c._id AS color_id, c.name AS color_name,
    // o._id AS opacity_id, o.value AS opacity_value
    // FROM text_styles AS ts
    // INNER JOIN font_sizes AS fs ON ts.font_size_id = fs._id
    // INNER JOIN text_formats AS tf ON ts.text_format_id = tf._id
    // INNER JOIN base_colors AS c ON ts.color_id = c._id
    // INNER JOIN predefined_opacities AS o ON ts.opacity_id = o._id
    public static final String QUERY_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + VIEW_NAME;


    // insert
    public static final String INSERT = INSERT_INTO + TABLE_NAME +
            OPENING_PARENTHESIS + COLUMN_NAME + COMMA + COLUMN_FONT_SIZE_ID + COMMA + COLUMN_TEXT_FORMAT_ID + COMMA + COLUMN_COLOR_ID + COMMA + COLUMN_OPACITY_ID + CLOSING_PARENTHESIS +
            VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(4) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ROW = UPDATE + TABLE_NAME + SET +
            COLUMN_NAME + EQUALS + QUESTION_MARK + COMMA + COLUMN_FONT_SIZE_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_TEXT_FORMAT_ID + EQUALS + QUESTION_MARK + COMMA + COLUMN_COLOR_ID + EQUALS + QUESTION_MARK + COMMA + COLUMN_OPACITY_ID + EQUALS + QUESTION_MARK +
            WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final int UPDATE_WHERE_POSITION = 6;

    // delete
    public static final String DELETE = DELETE_FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;

    private final Datasource2 datasource2;
    private final Connection connection;


    public TextStyleDAOImpl(Datasource2 datasource2, Connection connection) {
        this.datasource2 = datasource2;
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
        datasource2.setAutoCommitBehavior(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            setAllValues(preparedStatement, textStyle);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
        } catch (Exception e) {
            datasource2.rollback(e, "Insert-textStyle");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void update(TextStyle textStyle) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
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
            datasource2.rollback(e, "Update-textStyle");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void delete(TextStyle textStyle) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, textStyle.getId());
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
        } catch (SQLException e) {
            datasource2.rollback(e, "Delete-textStyle");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    private TextStyle extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new TextStyle(resultSet.getInt(1), resultSet.getString(2),
                new FontSize(resultSet.getInt(3), resultSet.getString(4), resultSet.getString(5)),
                new TextFormat(resultSet.getInt(6), resultSet.getString(7), resultSet.getString(8)),
                new BaseColor(resultSet.getInt(9), resultSet.getString(10)),
                new PredefinedOpacity(resultSet.getInt(11), resultSet.getString(12))
        );
    }

    private void setAllValues(PreparedStatement preparedStatement, TextStyle textStyle) throws SQLException {
        preparedStatement.setString(1, textStyle.getName());
        preparedStatement.setInt(2, textStyle.getFontSize().getId());
        preparedStatement.setInt(3, textStyle.getTextFormat().getId());
        preparedStatement.setInt(4, textStyle.getOpacity().getId());
    }

    @Override
    public String toString() {
        return "TextStyleDAOImpl{" +
                "datasource2=" + datasource2 +
                ", connection=" + connection +
                '}';
    }
}
