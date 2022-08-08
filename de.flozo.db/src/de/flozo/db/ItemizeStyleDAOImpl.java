package de.flozo.db;

import de.flozo.common.dto.appearance.ItemizeLabel;
import de.flozo.common.dto.appearance.ItemizeStyle;
import de.flozo.common.dto.appearance.Length;
import de.flozo.common.dto.appearance.LengthUnit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemizeStyleDAOImpl implements ItemizeStyleDAO {

    // table
    public static final String TABLE_NAME = "itemize_styles";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TOP_SEP_ID = "topsep_id";
    public static final String COLUMN_LEFT_MARGIN_ID = "leftmargin_id";
    public static final String COLUMN_LABEL_SEP_ID = "labelsep_id";
    public static final String COLUMN_ITEM_INDENT_ID = "itemindent_id";
    public static final String COLUMN_ITEM_SEP_ID = "itemsep_id";
    public static final String COLUMN_LABEL_ID = "label_id";

    // view
    public static final String VIEW_NAME = "itemize_style_view";
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

    // letter_content_view created via:

    // CREATE VIEW itemize_style_view AS
    // SELECT its._id, its.name,
    //	 lvts._id AS topsep_id, lvts.name AS topsep_name, lvts.value AS topsep_value, lvts.length_unit_id AS topsep_unit_id, lvts.length_unit_name AS topsep_unit_name, lvts.length_unit_value AS topsep_unit_value,
    //	 lvlm._id AS leftmargin_id, lvlm.name AS leftmargin_name, lvlm.value AS leftmargin_value, lvlm.length_unit_id AS leftmargin_unit_id, lvlm.length_unit_name AS leftmargin_unit_name, lvlm.length_unit_value AS leftmargin_unit_value,
    //	 lvls._id AS labelsep_id, lvls.name AS labelsep_name, lvls.value AS labelsep_value, lvls.length_unit_id AS labelsep_unit_id, lvls.length_unit_name AS labelsep_unit_name, lvls.length_unit_value AS labelsep_unit_value,
    //	 lvii._id AS itemindent_id, lvii.name AS itemindent_name, lvii.value AS itemindent_value, lvii.length_unit_id AS itemindent_unit_id, lvii.length_unit_name AS itemindent_unit_name, lvii.length_unit_value AS itemindent_unit_value,
    //	 lvis._id AS itemsep_id, lvis.name AS itemsep_name, lvis.value AS itemsep_value, lvis.length_unit_id AS itemsep_unit_id, lvis.length_unit_name AS itemsep_unit_name, lvis.length_unit_value AS itemsep_unit_value,
    //	 il._id AS label_id, il.name AS label_name, il.value AS label_value
    // FROM itemize_styles AS its
    // INNER JOIN length_view AS lvts ON its.topsep_id = lvts._id
    // INNER JOIN length_view AS lvlm ON its.leftmargin_id = lvlm._id
    // INNER JOIN length_view AS lvls ON its.labelsep_id = lvls._id
    // INNER JOIN length_view AS lvii ON its.itemindent_id = lvii._id
    // INNER JOIN length_view AS lvis ON its.itemsep_id = lvis._id
    // INNER JOIN itemize_labels AS il ON its.label_id = il._id

    public static final String QUERY_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + VIEW_NAME;

    public static final int NON_ID_COLUMNS = 7;

    // insert
    public static final String INSERT = INSERT_INTO + TABLE_NAME + OPENING_PARENTHESIS +
            COLUMN_NAME + COMMA +
            COLUMN_TOP_SEP_ID + COMMA +
            COLUMN_LEFT_MARGIN_ID + COMMA +
            COLUMN_LABEL_SEP_ID + COMMA +
            COLUMN_ITEM_INDENT_ID + COMMA +
            COLUMN_ITEM_SEP_ID + COMMA +
            COLUMN_LABEL_ID +
            CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(NON_ID_COLUMNS - 1) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ROW = UPDATE + TABLE_NAME + SET +
            COLUMN_NAME + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_TOP_SEP_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_LEFT_MARGIN_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_LABEL_SEP_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_ITEM_INDENT_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_ITEM_SEP_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_LABEL_ID + EQUALS + QUESTION_MARK +
            WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final int UPDATE_WHERE_POSITION = NON_ID_COLUMNS + 1;


    // delete
    public static final String DELETE = DELETE_FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;


    private final Datasource2 datasource2;
    private final Connection connection;

    public ItemizeStyleDAOImpl(Datasource2 datasource2, Connection connection) {
        this.datasource2 = datasource2;
        this.connection = connection;
    }

    private void showSQLMessage(String queryString) {
        System.out.println("[database] Executing SQL statement \"" + queryString + "\" ...");
    }

    @Override
    public ItemizeStyle get(int id) {
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
    public ItemizeStyle get(String specifier) {
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
    public List<ItemizeStyle> getAll() {
        showSQLMessage(QUERY_ALL);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<ItemizeStyle> itemizeStyles = new ArrayList<>();
            while (resultSet.next()) {
                itemizeStyles.add(extractFromResultSet(resultSet));
            }
            System.out.println(" done!");
            return itemizeStyles;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void add(ItemizeStyle itemizeStyle) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(INSERT);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            setAllValues(preparedStatement, itemizeStyle);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource2.rollback(e, "Insert-itemizeStyle");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void update(ItemizeStyle itemizeStyle) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(UPDATE_ROW);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)) {
            preparedStatement.setInt(UPDATE_WHERE_POSITION, itemizeStyle.getId());
            setAllValues(preparedStatement, itemizeStyle);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource2.rollback(e, "Update-itemizeStyle");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void delete(ItemizeStyle itemizeStyle) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(DELETE);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, itemizeStyle.getId());
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (SQLException e) {
            System.out.println();
            datasource2.rollback(e, "Delete-itemizeStyle");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    private ItemizeStyle extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new ItemizeStyle(resultSet.getInt(1), resultSet.getString(2),
                // topsep
                new Length(resultSet.getInt(3), resultSet.getString(4), resultSet.getDouble(5),
                        new LengthUnit(resultSet.getInt(6), resultSet.getString(7), resultSet.getString(8))),
                // leftmargin
                new Length(resultSet.getInt(9), resultSet.getString(10), resultSet.getDouble(11),
                        new LengthUnit(resultSet.getInt(12), resultSet.getString(13), resultSet.getString(14))),
                // labelsep
                new Length(resultSet.getInt(15), resultSet.getString(16), resultSet.getDouble(17),
                        new LengthUnit(resultSet.getInt(18), resultSet.getString(19), resultSet.getString(20))),
                // itemindent
                new Length(resultSet.getInt(21), resultSet.getString(22), resultSet.getDouble(23),
                        new LengthUnit(resultSet.getInt(24), resultSet.getString(25), resultSet.getString(26))),
                // itemsep
                new Length(resultSet.getInt(27), resultSet.getString(28), resultSet.getDouble(29),
                        new LengthUnit(resultSet.getInt(30), resultSet.getString(31), resultSet.getString(32))),
                new ItemizeLabel(resultSet.getInt(33), resultSet.getString(34), resultSet.getString(35))
        );
    }

    private void setAllValues(PreparedStatement preparedStatement, ItemizeStyle itemizeStyle) throws SQLException {
        preparedStatement.setString(1, itemizeStyle.getName());
        preparedStatement.setInt(2, itemizeStyle.getTopSep().getId());
        preparedStatement.setInt(3, itemizeStyle.getLeftMargin().getId());
        preparedStatement.setInt(4, itemizeStyle.getLabelSep().getId());
        preparedStatement.setInt(5, itemizeStyle.getItemIndent().getId());
        preparedStatement.setInt(6, itemizeStyle.getItemSep().getId());
        preparedStatement.setInt(7, itemizeStyle.getLabel().getId());
    }

    @Override
    public String toString() {
        return "ItemizeStyleDAOImpl{" +
                "datasource2=" + datasource2 +
                ", connection=" + connection +
                '}';
    }
}
