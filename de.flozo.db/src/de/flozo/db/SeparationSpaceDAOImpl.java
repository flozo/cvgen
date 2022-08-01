package de.flozo.db;

import de.flozo.common.dto.appearance.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeparationSpaceDAOImpl implements SeparationSpaceDAO {

    // table
    public static final String TABLE_NAME = "separation_spaces";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_INNER_X_SEP_ID = "inner_x_sep_id";
    public static final String COLUMN_INNER_Y_SEP_ID = "inner_y_sep_id";
    public static final String COLUMN_OUTER_X_SEP_ID = "outer_x_sep_id";
    public static final String COLUMN_OUTER_Y_SEP_ID = "outer_y_sep_id";

    // view
    public static final String VIEW_NAME = "separation_spaces_view";
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

    // separation_spaces_view created via:

    // CREATE VIEW separation_spaces_view AS
    // SELECT ss._id, ss.name,
    //	 lix._id AS inner_x_sep_length_id, lix.name AS inner_x_sep_length_name, lix.value AS inner_x_sep_length_value,
    //	 luix._id AS inner_x_sep_length_unit_id, luix.name AS inner_x_sep_length_unit_name, luix.value AS inner_x_sep_length_unit_value,
    //	 liy._id AS inner_y_sep_length_id, liy.name AS inner_y_sep_length_name, liy.value AS inner_y_sep_length_value,
    //	 luiy._id AS inner_y_sep_length_unit_id, luiy.name AS inner_y_sep_length_unit_name, luiy.value AS inner_y_sep_length_unit_value,
    //	 lox._id AS outer_x_sep_length_id, lox.name AS outer_x_sep_length_name, lox.value AS outer_x_sep_length_value,
    //	 luox._id AS outer_x_sep_length_unit_id, luox.name AS outer_x_sep_length_unit_name, luox.value AS outer_x_sep_length_unit_value,
    //	 loy._id AS outer_y_sep_length_id, loy.name AS outer_y_sep_length_name, loy.value AS outer_y_sep_length_value,
    //	 luoy._id AS outer_y_sep_length_unit_id, luoy.name AS outer_y_sep_length_unit_name, luoy.value AS outer_y_sep_length_unit_value
    // FROM separation_spaces AS ss
    // INNER JOIN lengths AS lix ON ss.inner_x_sep_id = lix._id
    // INNER JOIN length_units AS luix ON lix.length_unit_id = luix._id
    // INNER JOIN lengths AS liy ON ss.inner_y_sep_id = liy._id
    // INNER JOIN length_units AS luiy ON liy.length_unit_id = luiy._id
    // INNER JOIN lengths AS lox ON ss.outer_x_sep_id = lox._id
    // INNER JOIN length_units AS luox ON lox.length_unit_id = luox._id
    // INNER JOIN lengths AS loy ON ss.outer_y_sep_id = loy._id
    // INNER JOIN length_units AS luoy ON loy.length_unit_id = luoy._id

    public static final String QUERY_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + VIEW_NAME;

    public static final int NON_ID_COLUMNS = 5;

    // insert
    public static final String INSERT = INSERT_INTO + TABLE_NAME + OPENING_PARENTHESIS +
            COLUMN_NAME + COMMA +
            COLUMN_INNER_X_SEP_ID + COMMA +
            COLUMN_INNER_Y_SEP_ID + COMMA +
            COLUMN_OUTER_X_SEP_ID + COMMA +
            COLUMN_OUTER_Y_SEP_ID +
            CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(NON_ID_COLUMNS - 1) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ROW = UPDATE + TABLE_NAME + SET +
            COLUMN_NAME + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_INNER_X_SEP_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_INNER_Y_SEP_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_OUTER_X_SEP_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_OUTER_Y_SEP_ID + EQUALS + QUESTION_MARK +
            WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final int UPDATE_WHERE_POSITION = NON_ID_COLUMNS + 1;


    // delete
    public static final String DELETE = DELETE_FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;


    private final Datasource2 datasource2;
    private final Connection connection;


    public SeparationSpaceDAOImpl(Datasource2 datasource2, Connection connection) {
        this.datasource2 = datasource2;
        this.connection = connection;
    }

    private void showSQLMessage(String queryString) {
        System.out.println("[database] Executing SQL statement \"" + queryString + "\" ...");
    }

    @Override
    public SeparationSpace get(int id) {
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
    public SeparationSpace get(String specifier) {
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
    public List<SeparationSpace> getAll() {
        showSQLMessage(QUERY_ALL);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<SeparationSpace> separationSpaces = new ArrayList<>();
            while (resultSet.next()) {
                separationSpaces.add(extractFromResultSet(resultSet));
            }
            System.out.println(" done!");
            return separationSpaces;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void add(SeparationSpace separationSpace) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(INSERT);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            setAllValues(preparedStatement, separationSpace);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource2.rollback(e, "Insert-separationSpace");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void update(SeparationSpace separationSpace) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(UPDATE_ROW);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)) {
            preparedStatement.setInt(UPDATE_WHERE_POSITION, separationSpace.getId());
            setAllValues(preparedStatement, separationSpace);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource2.rollback(e, "Update-separationSpace");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void delete(SeparationSpace separationSpace) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(DELETE);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, separationSpace.getId());
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (SQLException e) {
            System.out.println();
            datasource2.rollback(e, "Delete-separationSpace");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    private SeparationSpace extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new SeparationSpace(resultSet.getInt(1), resultSet.getString(2),
                new Length(resultSet.getInt(3), resultSet.getString(4), resultSet.getDouble(5),
                        new LengthUnit(resultSet.getInt(6), resultSet.getString(7), resultSet.getString(8))),
                new Length(resultSet.getInt(9), resultSet.getString(10), resultSet.getDouble(11),
                        new LengthUnit(resultSet.getInt(12), resultSet.getString(13), resultSet.getString(14))),
                new Length(resultSet.getInt(15), resultSet.getString(16), resultSet.getDouble(17),
                        new LengthUnit(resultSet.getInt(18), resultSet.getString(19), resultSet.getString(20))),
                new Length(resultSet.getInt(21), resultSet.getString(22), resultSet.getDouble(23),
                        new LengthUnit(resultSet.getInt(24), resultSet.getString(25), resultSet.getString(26)))
        );
    }

    private void setAllValues(PreparedStatement preparedStatement, SeparationSpace separationSpace) throws SQLException {
        preparedStatement.setString(1, separationSpace.getName());
        preparedStatement.setInt(2, separationSpace.getInnerXSep().getId());
        preparedStatement.setInt(3, separationSpace.getInnerYSep().getId());
        preparedStatement.setInt(4, separationSpace.getOuterXSep().getId());
        preparedStatement.setInt(5, separationSpace.getOuterYSep().getId());
    }

}
