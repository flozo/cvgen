package de.flozo.db;

import de.flozo.common.dto.appearance.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ElementDAOImpl implements ElementDAO {

    // table
    public static final String TABLE_NAME = "elements";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_POSITION_ID = "position_id";
    public static final String COLUMN_ANCHOR_ID = "anchor_id";
    public static final String COLUMN_MINIMUM_WIDTH_ID = "minimum_width_id";
    public static final String COLUMN_MINIMUM_HEIGHT_ID = "minimum_height_id";
    public static final String COLUMN_SEPARATION_SPACES_ID = "separation_spaces_id";
    public static final String COLUMN_ELEMENT_STYLE_ID = "element_style_id";
    public static final String COLUMN_ON_PAGE_ID = "on_page_id";
    public static final String COLUMN_ON_LAYER_ID = "on_layer_id";
    public static final String COLUMN_INCLUDE = "include";

    // view
    public static final String VIEW_NAME = "element_view";
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

    // element_view created via:

    // CREATE VIEW element_view AS
    // SELECT e._id, e.name,
    //   pv._id AS position_id, pv.name AS position_name, pv.x_length_id AS position_x_length_id, pv.x_length_name AS position_x_length_name, pv.x_length_value AS position_x_length_value, pv.x_length_unit_id AS position_x_length_unit_id, pv.x_length_unit_name AS position_x_length_unit_name, pv.x_length_unit_value AS position_x_length_unit_value, pv.y_length_id AS position_y_length_id, pv.y_length_name AS position_y_length_name, pv.y_length_value AS position_y_length_value, pv.y_length_unit_id AS position_y_length_unit_id, pv.y_length_unit_name AS position_y_length_unit_name, pv.y_length_unit_value AS position_y_length_unit_value,
    //   a._id AS anchor_id, a.name AS anchor_name, a.value AS anchor_value,
    //   lvmw._id AS minimum_width_id, lvmw.name AS minimum_width_name, lvmw.value AS minimum_width_value, lvmw.length_unit_id AS minimum_width_unit_id, lvmw.length_unit_name AS minimum_width_unit_name, lvmw.length_unit_value AS minimum_width_unit_value,
    //   lvmh._id AS minimum_height_id, lvmh.name AS minimum_height_name, lvmh.value AS minimum_height_value, lvmh.length_unit_id AS minimum_height_unit_id, lvmh.length_unit_name AS minimum_height_unit_name, lvmh.length_unit_value AS minimum_height_unit_value,
    //   ssv._id AS separation_spaces_id, ssv.name AS separation_spaces_name,
    //     inner_x_sep_length_id, inner_x_sep_length_name, inner_x_sep_length_value, inner_x_sep_length_unit_id, inner_x_sep_length_unit_name, inner_x_sep_length_unit_value,
    //     inner_y_sep_length_id, inner_y_sep_length_name, inner_y_sep_length_value, inner_y_sep_length_unit_id, inner_y_sep_length_unit_name, inner_y_sep_length_unit_value,
    //     outer_x_sep_length_id, outer_x_sep_length_name, outer_x_sep_length_value, outer_x_sep_length_unit_id, outer_x_sep_length_unit_name, outer_x_sep_length_unit_value,
    //     outer_y_sep_length_id, outer_y_sep_length_name, outer_y_sep_length_value, outer_y_sep_length_unit_id, outer_y_sep_length_unit_name, outer_y_sep_length_unit_value,
    //   esv.*,
    //   e.on_page_id, e.on_layer_id, e.include
    // FROM elements AS e
    // INNER JOIN position_view AS pv ON e.position_id = pv._id
    // INNER JOIN anchors AS a ON e.anchor_id = a._id
    // INNER JOIN length_view AS lvmw ON e.minimum_width_id = lvmw._id
    // INNER JOIN length_view AS lvmh ON e.minimum_height_id = lvmh._id
    // INNER JOIN separation_spaces_view AS ssv ON e.separation_spaces_id = ssv._id
    // INNER JOIN element_styles_view AS esv ON e.element_style_id = esv._id

    public static final String QUERY_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + VIEW_NAME;
    public static final String QUERY_ALL_INCLUDED = SELECT + STAR + FROM + VIEW_NAME + WHERE + COLUMN_INCLUDE + EQUALS + "1";

    public static final int NON_ID_COLUMNS = 10;

    // insert
    public static final String INSERT = INSERT_INTO + TABLE_NAME + OPENING_PARENTHESIS +
            COLUMN_NAME + COMMA +
            COLUMN_POSITION_ID + COMMA +
            COLUMN_ANCHOR_ID + COMMA +
            COLUMN_MINIMUM_WIDTH_ID + COMMA +
            COLUMN_MINIMUM_HEIGHT_ID + COMMA +
            COLUMN_SEPARATION_SPACES_ID + COMMA +
            COLUMN_ELEMENT_STYLE_ID + COMMA +
            COLUMN_ON_PAGE_ID + COMMA +
            COLUMN_ON_LAYER_ID + COMMA +
            COLUMN_INCLUDE +
            CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(NON_ID_COLUMNS - 1) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ROW = UPDATE + TABLE_NAME + SET +
            COLUMN_NAME + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_POSITION_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_ANCHOR_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_MINIMUM_WIDTH_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_MINIMUM_HEIGHT_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_SEPARATION_SPACES_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_ELEMENT_STYLE_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_ON_PAGE_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_ON_LAYER_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_INCLUDE + EQUALS + QUESTION_MARK +
            WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final int UPDATE_WHERE_POSITION = NON_ID_COLUMNS + 1;

    // delete
    public static final String DELETE = DELETE_FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;

    private final Datasource2 datasource2;
    private final Connection connection;


    public ElementDAOImpl(Datasource2 datasource2, Connection connection) {
        this.datasource2 = datasource2;
        this.connection = connection;
    }

    private void showSQLMessage(String queryString) {
        System.out.println("[database] Executing SQL statement \"" + queryString + "\" ...");
    }


    @Override
    public Element get(int id) {
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
    public Element get(String specifier) {
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
    public List<Element> getAll() {
        showSQLMessage(QUERY_ALL);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<Element> elements = new ArrayList<>();
            while (resultSet.next()) {
                elements.add(extractFromResultSet(resultSet));
            }
            System.out.println(" done!");
            return elements;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Element> getAllIncluded() {
        showSQLMessage(QUERY_ALL_INCLUDED);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL_INCLUDED)) {
            List<Element> elements = new ArrayList<>();
            while (resultSet.next()) {
                elements.add(extractFromResultSet(resultSet));
            }
            System.out.println(" done!");
            return elements;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void add(Element element) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(INSERT);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            setAllValues(preparedStatement, element);
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
    public void update(Element element) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(UPDATE_ROW);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)) {
            preparedStatement.setInt(UPDATE_WHERE_POSITION, element.getId());
            setAllValues(preparedStatement, element);
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
    public void delete(Element element) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(DELETE);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, element.getId());
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

    private Element extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Element(resultSet.getInt(1), resultSet.getString(2),
                new Position(resultSet.getInt(3), resultSet.getString(4),
                        // position x
                        new Length(resultSet.getInt(5), resultSet.getString(6), resultSet.getDouble(7),
                                new LengthUnit(resultSet.getInt(8), resultSet.getString(9), resultSet.getString(10))),
                        // position y
                        new Length(resultSet.getInt(11), resultSet.getString(12), resultSet.getDouble(13),
                                new LengthUnit(resultSet.getInt(14), resultSet.getString(15), resultSet.getString(16)))),
                new Anchor(resultSet.getInt(17), resultSet.getString(18), resultSet.getString(19)),
                // minimum width
                new Length(resultSet.getInt(20), resultSet.getString(21), resultSet.getDouble(22),
                        new LengthUnit(resultSet.getInt(23), resultSet.getString(24), resultSet.getString(25))),
                // minimum height
                new Length(resultSet.getInt(26), resultSet.getString(27), resultSet.getDouble(28),
                        new LengthUnit(resultSet.getInt(29), resultSet.getString(30), resultSet.getString(31))),
                new SeparationSpace(resultSet.getInt(32), resultSet.getString(33),
                        new Length(resultSet.getInt(34), resultSet.getString(35), resultSet.getDouble(36),
                                new LengthUnit(resultSet.getInt(37), resultSet.getString(38), resultSet.getString(39))),
                        new Length(resultSet.getInt(40), resultSet.getString(41), resultSet.getDouble(42),
                                new LengthUnit(resultSet.getInt(43), resultSet.getString(44), resultSet.getString(45))),
                        new Length(resultSet.getInt(46), resultSet.getString(47), resultSet.getDouble(48),
                                new LengthUnit(resultSet.getInt(49), resultSet.getString(50), resultSet.getString(51))),
                        new Length(resultSet.getInt(52), resultSet.getString(53), resultSet.getDouble(54),
                                new LengthUnit(resultSet.getInt(55), resultSet.getString(56), resultSet.getString(57)))),
                new ElementStyle(resultSet.getInt(58), resultSet.getString(59),
                        new TextStyle(resultSet.getInt(60), resultSet.getString(61),
                                new FontSize(resultSet.getInt(62), resultSet.getString(63), resultSet.getString(64)),
                                new TextFormat(resultSet.getInt(65), resultSet.getString(66), resultSet.getString(67)),
                                new Length(resultSet.getInt(68), resultSet.getString(69), resultSet.getDouble(70),
                                        new LengthUnit(resultSet.getInt(71), resultSet.getString(72), resultSet.getString(73))),
                                new Length(resultSet.getInt(74), resultSet.getString(75), resultSet.getDouble(76),
                                        new LengthUnit(resultSet.getInt(77), resultSet.getString(78), resultSet.getString(79))),
                                new Length(resultSet.getInt(80), resultSet.getString(81), resultSet.getDouble(82),
                                        new LengthUnit(resultSet.getInt(83), resultSet.getString(84), resultSet.getString(85))),
                                new Alignment(resultSet.getInt(86), resultSet.getString(87), resultSet.getString(88)),
                                new Color(resultSet.getInt(89), resultSet.getString(90)),
                                new PredefinedOpacity(resultSet.getInt(91), resultSet.getString(92))),
                        new LineStyle(resultSet.getInt(93), resultSet.getString(94),
                                new LineWidth(resultSet.getInt(95), resultSet.getString(96), resultSet.getDouble(97),
                                        new LengthUnit(resultSet.getInt(98), resultSet.getString(99), resultSet.getString(100))),
                                new LineCap(resultSet.getInt(101), resultSet.getString(102), resultSet.getString(103)),
                                new LineJoin(resultSet.getInt(104), resultSet.getString(105), resultSet.getString(106)),
                                new DashPattern(resultSet.getInt(107), resultSet.getString(108)),
                                new Color(resultSet.getInt(109), resultSet.getString(110)),
                                new PredefinedOpacity(resultSet.getInt(111), resultSet.getString(112))),
                        new AreaStyle(resultSet.getInt(113), resultSet.getString(114),
                                new Color(resultSet.getInt(115), resultSet.getString(116)),
                                new PredefinedOpacity(resultSet.getInt(117), resultSet.getString(118)))),
                resultSet.getInt(119), resultSet.getInt(120), resultSet.getBoolean(121)
        );
    }

    private void setAllValues(PreparedStatement preparedStatement, Element element) throws SQLException {
        preparedStatement.setString(1, element.getName());
        preparedStatement.setInt(2, element.getPosition().getId());
        preparedStatement.setInt(3, element.getAnchor().getId());
        preparedStatement.setInt(4, element.getMinimumWidth().getId());
        preparedStatement.setInt(5, element.getMinimumHeight().getId());
        preparedStatement.setInt(6, element.getSeparationSpace().getId());
        preparedStatement.setInt(7, element.getElementStyle().getId());
        preparedStatement.setInt(8, element.getOnPageId());
        preparedStatement.setInt(9, element.getOnLayerId());
        preparedStatement.setBoolean(10, element.isInclude());
    }

    @Override
    public String toString() {
        return "ElementDAOImpl{" +
                "datasource2=" + datasource2 +
                ", connection=" + connection +
                '}';
    }
}
