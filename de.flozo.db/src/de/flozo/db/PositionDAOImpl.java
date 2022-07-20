package de.flozo.db;

import de.flozo.common.appearance.Length;
import de.flozo.common.appearance.LengthUnit;
import de.flozo.common.appearance.Position;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PositionDAOImpl implements PositionDAO {


    // table
    public static final String TABLE_NAME = "positions";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_X_LENGTH_ID = "x_length_id";
    public static final String COLUMN_Y_LENGTH_ID = "y_length_id";

    // view (read only)
    public static final String VIEW_NAME = "position_view";
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

    // position_view created via:

//     CREATE VIEW position_view AS
//     SELECT p._id, p.name,
//       lx._id AS x_length_id, lx.name AS x_length_name, lx.value AS x_length_value, lux._id AS x_length_unit_id, lux.name AS x_length_unit_name, lux.value AS x_length_unit_value,
//       ly._id AS y_length_id, ly.name AS y_length_name, ly.value AS y_length_value, luy._id AS y_length_unit_id, luy.name AS y_length_unit_name, luy.value AS y_length_unit_value
//     FROM positions AS p
//     INNER JOIN lengths AS lx ON p.x_length_id = lx._id
//     INNER JOIN lengths AS ly ON p.y_length_id = ly._id
//     INNER JOIN length_units AS lux ON lx.length_unit_id = lux._id
//     INNER JOIN length_units AS luy ON ly.length_unit_id = luy._id;
    public static final String QUERY_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + VIEW_NAME;

    // insert
    public static final String INSERT = INSERT_INTO + TABLE_NAME + OPENING_PARENTHESIS +
            COLUMN_NAME + COMMA +
            COLUMN_X_LENGTH_ID + COMMA +
            COLUMN_Y_LENGTH_ID +
            CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(2) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ROW = UPDATE + TABLE_NAME + SET +
            COLUMN_NAME + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_X_LENGTH_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_Y_LENGTH_ID + EQUALS + QUESTION_MARK +
            WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final int UPDATE_WHERE_POSITION = 4;

    // delete
    public static final String DELETE = DELETE_FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;

    private final Datasource2 datasource2;
    private final Connection connection;


    public PositionDAOImpl(Datasource2 datasource2, Connection connection) {
        this.datasource2 = datasource2;
        this.connection = connection;
    }

    @Override
    public Position get(int id) {
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
    public Position get(String specifier) {
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
    public List<Position> getAll() {
        System.out.print("[database] Executing SQL statement \"" + QUERY_ALL + "\" ...");
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<Position> positions = new ArrayList<>();
            while (resultSet.next()) {
                positions.add(extractFromResultSet(resultSet));
            }
            System.out.println(" done!");
            return positions;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void add(Position position) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        System.out.print("[database] Executing SQL statement \"" + INSERT + "\" ...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            setAllValues(preparedStatement, position);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource2.rollback(e, "Insert-position");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void update(Position position) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        System.out.print("[database] Executing SQL statement \"" + UPDATE_ROW + "\" ...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)) {
            preparedStatement.setInt(UPDATE_WHERE_POSITION, position.getId());
            setAllValues(preparedStatement, position);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource2.rollback(e, "Update-position");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void delete(Position position) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        System.out.print("[database] Executing SQL statement \"" + DELETE + "\" ...");
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, position.getId());
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (SQLException e) {
            System.out.println();
            datasource2.rollback(e, "Delete-position");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    private Position extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Position(resultSet.getInt(1), resultSet.getString(2),
                new Length(resultSet.getInt(3), resultSet.getString(4), resultSet.getDouble(5),
                        new LengthUnit(resultSet.getInt(6), resultSet.getString(7), resultSet.getString(8))),
                new Length(resultSet.getInt(9), resultSet.getString(10), resultSet.getDouble(11),
                        new LengthUnit(resultSet.getInt(12), resultSet.getString(13), resultSet.getString(14)))
        );
    }

    private void setAllValues(PreparedStatement preparedStatement, Position position) throws SQLException {
        preparedStatement.setString(1, position.getName());
        preparedStatement.setInt(2, position.getLengthX().getId());
        preparedStatement.setInt(3, position.getLengthY().getId());
    }


    @Override
    public String toString() {
        return "PositionDAOImpl{" +
                "datasource2=" + datasource2 +
                ", connection=" + connection +
                '}';
    }
}
