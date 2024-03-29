package de.flozo.db;

import de.flozo.common.dto.appearance.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PageDAOImpl implements PageDAO {

    // table
    public static final String TABLE_NAME = "pages";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_WIDTH_ID = "text_style_id";
    public static final String COLUMN_HEIGHT_ID = "line_style_id";
    public static final String COLUMN_LINE_STYLE_ID = "line_style_id";
    public static final String COLUMN_AREA_STYLE_ID = "area_style_id";

    // view
    public static final String VIEW_NAME = "page_view";
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

    // page_view created via:

    // CREATE VIEW page_view AS
    // SELECT p._id, p.name,
    //   lvw._id AS width_id, lvw.name AS width_name, lvw.value AS width_value, lvw.length_unit_id AS width_unit_id, lvw.length_unit_name AS width_unit_name, lvw.length_unit_value AS width_unit_value,
    //   lvh._id AS height_id, lvh.name AS height_name, lvh.value AS height_value, lvh.length_unit_id AS height_unit_id, lvh.length_unit_name AS height_unit_name, lvh.length_unit_value AS height_unit_value,
    //   lsv._id AS line_style_id, lsv.name AS line_style_name, lsv.line_width_id, lsv.line_width_name, lsv.line_width_value, lsv.line_width_unit_id, lsv.line_width_unit_name, lsv.line_width_unit_value, lsv.line_cap_id, lsv.line_cap_name, lsv.line_cap_value, lsv.line_join_id, lsv.line_join_name, lsv.line_join_value, lsv.dash_pattern_id, lsv.dash_pattern_name, lsv.color_id AS line_color_id, lsv.color_name AS line_color_name, lsv.opacity_id AS line_opacity_id, lsv.opacity_name AS line_opacity_value,
    //   asv._id AS area_style_id, asv.name AS area_style_name, asv.color_id AS background_color_id, asv.color_name AS background_color_name, asv.opacity_id AS background_opacity_id, asv.opacity_value AS background_opacity_value
    // FROM pages AS p
    // INNER JOIN length_view AS lvw ON p.width_id = lvw._id
    // INNER JOIN length_view AS lvh ON p.height_id = lvh._id
    // INNER JOIN line_style_view AS lsv ON p.line_style_id = lsv._id
    // INNER JOIN area_style_view AS asv ON p.area_style_id = asv._id

    public static final String QUERY_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + VIEW_NAME;

    public static final int NON_ID_COLUMNS = 5;

    // insert
    public static final String INSERT = INSERT_INTO + TABLE_NAME + OPENING_PARENTHESIS +
            COLUMN_NAME + COMMA +
            COLUMN_WIDTH_ID + COMMA +
            COLUMN_HEIGHT_ID + COMMA +
            COLUMN_LINE_STYLE_ID + COMMA +
            COLUMN_AREA_STYLE_ID +
            CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(NON_ID_COLUMNS - 1) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ROW = UPDATE + TABLE_NAME + SET +
            COLUMN_NAME + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_WIDTH_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_HEIGHT_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_LINE_STYLE_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_AREA_STYLE_ID + EQUALS + QUESTION_MARK +
            WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final int UPDATE_WHERE_POSITION = NON_ID_COLUMNS + 1;

    // delete
    public static final String DELETE = DELETE_FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;

    private final Datasource datasource;
    private final Connection connection;


    public PageDAOImpl(Datasource datasource, Connection connection) {
        this.datasource = datasource;
        this.connection = connection;
    }

    private void showSQLMessage(String queryString) {
        System.out.println("[database] Executing SQL statement \"" + queryString + "\" ...");
    }

    @Override
    public Page get(int id) {
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
    public Page get(String specifier) {
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
    public List<Page> getAll() {
        showSQLMessage(QUERY_ALL);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<Page> pages = new ArrayList<>();
            while (resultSet.next()) {
                pages.add(extractFromResultSet(resultSet));
            }
            System.out.println(" done!");
            return pages;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void add(Page page) {
        // start transaction:
        datasource.setAutoCommitBehavior(false);
        showSQLMessage(INSERT);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            setAllValues(preparedStatement, page);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource.rollback(e, "Insert-page");
        } finally {
            datasource.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void update(Page page) {
        // start transaction:
        datasource.setAutoCommitBehavior(false);
        showSQLMessage(UPDATE_ROW);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)) {
            preparedStatement.setInt(UPDATE_WHERE_POSITION, page.getId());
            setAllValues(preparedStatement, page);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource.rollback(e, "Update-page");
        } finally {
            datasource.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void delete(Page page) {
        // start transaction:
        datasource.setAutoCommitBehavior(false);
        showSQLMessage(DELETE);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, page.getId());
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (SQLException e) {
            System.out.println();
            datasource.rollback(e, "Delete-page");
        } finally {
            datasource.setAutoCommitBehavior(true);
        }

    }

    private Page extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Page(resultSet.getInt(1), resultSet.getString(2),
                new Length(resultSet.getInt(3), resultSet.getString(4), resultSet.getDouble(5),
                        new LengthUnit(resultSet.getInt(6), resultSet.getString(7), resultSet.getString(8))),
                new Length(resultSet.getInt(9), resultSet.getString(10), resultSet.getDouble(11),
                        new LengthUnit(resultSet.getInt(12), resultSet.getString(13), resultSet.getString(14))),
                new LineStyle(resultSet.getInt(15), resultSet.getString(16),
                        new LineWidth(resultSet.getInt(17), resultSet.getString(18), resultSet.getDouble(19),
                                new LengthUnit(resultSet.getInt(20), resultSet.getString(21), resultSet.getString(22))),
                        new LineCap(resultSet.getInt(23), resultSet.getString(24), resultSet.getString(25)),
                        new LineJoin(resultSet.getInt(26), resultSet.getString(27), resultSet.getString(28)),
                        new DashPattern(resultSet.getInt(29), resultSet.getString(30)),
                        new Color(resultSet.getInt(31), resultSet.getString(32)),
                        new PredefinedOpacity(resultSet.getInt(33), resultSet.getString(34))),
                new AreaStyle(resultSet.getInt(35), resultSet.getString(36),
                        new Color(resultSet.getInt(37), resultSet.getString(38)),
                        new PredefinedOpacity(resultSet.getInt(39), resultSet.getString(40)))
        );
    }

    private void setAllValues(PreparedStatement preparedStatement, Page page) throws SQLException {
        preparedStatement.setString(1, page.getName());
        preparedStatement.setInt(2, page.getWidth().getId());
        preparedStatement.setInt(3, page.getHeight().getId());
        preparedStatement.setInt(4, page.getLineStyle().getId());
        preparedStatement.setInt(5, page.getAreaStyle().getId());
    }

    @Override
    public String toString() {
        return "PageDAOImpl{" +
                "datasource2=" + datasource +
                ", connection=" + connection +
                '}';
    }
}
