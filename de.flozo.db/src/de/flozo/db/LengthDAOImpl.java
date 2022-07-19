package de.flozo.db;

import de.flozo.common.appearance.Length;
import de.flozo.common.appearance.LengthUnit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LengthDAOImpl implements LengthDAO {


    // content
    public static final String TABLE_LENGTHS = "lengths";
    public static final String COLUMN_LENGTH_ID = "_id";
    public static final String COLUMN_LENGTH_NAME = "name";
    public static final String COLUMN_LENGTH_VALUE = "value";
    public static final String COLUMN_LENGTH_LENGTH_UNIT_ID = "length_unit_id";

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
//    public static final String QUERY_LENGTH_BY_ID = SELECT + STAR + FROM + TABLE_LENGTHS + WHERE + COLUMN_LENGTH_ID + EQUALS + QUESTION_MARK;
//    public static final String QUERY_LENGTH_BY_SPECIFIER = SELECT + STAR + FROM + TABLE_LENGTHS + WHERE + COLUMN_LENGTH_NAME + EQUALS + QUESTION_MARK;
//    public static final String QUERY_ALL_LENGTHS = SELECT + STAR + FROM + TABLE_LENGTHS;

    public static final String QUERY_LENGTH_BY_ID_RESOLVE_FOREIGN_KEYS = SELECT + "lengths._id, lengths.name, lengths.value, length_units._id, length_units.name, length_units.value " + FROM +
            TABLE_LENGTHS + INNER_JOIN + "length_units on lengths.length_unit_id = length_units._id where lengths._id" + EQUALS + QUESTION_MARK;
    public static final String QUERY_LENGTH_BY_SPECIFIER_RESOLVE_FOREIGN_KEYS = SELECT + "lengths._id, lengths.name, lengths.value, length_units._id, length_units.name, length_units.value " + FROM +
            TABLE_LENGTHS + INNER_JOIN + "length_units on lengths.length_unit_id = length_units._id where lengths.name" + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL_LENGTHS_RESOLVE_FOREIGN_KEYS = SELECT + "lengths._id, lengths.name, lengths.value, length_units._id, length_units.name, length_units.value " + FROM +
            TABLE_LENGTHS + INNER_JOIN + "length_units on lengths.length_unit_id = length_units._id";


    // insert
    public static final String INSERT_LENGTH = INSERT_INTO + TABLE_LENGTHS +
            OPENING_PARENTHESIS + COLUMN_LENGTH_NAME + COMMA + COLUMN_LENGTH_VALUE + COMMA + COLUMN_LENGTH_LENGTH_UNIT_ID + CLOSING_PARENTHESIS +
            VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(2) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_LENGTH = UPDATE + TABLE_LENGTHS + SET +
            COLUMN_LENGTH_NAME + EQUALS + QUESTION_MARK + COMMA + COLUMN_LENGTH_VALUE + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_LENGTH_LENGTH_UNIT_ID + EQUALS + QUESTION_MARK + WHERE + COLUMN_LENGTH_ID + EQUALS + QUESTION_MARK;

    // delete
    public static final String DELETE_LENGTH = DELETE_FROM + TABLE_LENGTHS + WHERE + COLUMN_LENGTH_ID + EQUALS + QUESTION_MARK;


    private Connection connection = Datasource2.INSTANCE.getConnection();


    public LengthDAOImpl() {
    }

    @Override
    public Length get(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_LENGTH_BY_ID_RESOLVE_FOREIGN_KEYS)) {
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
    public Length get(String specifier) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_LENGTH_BY_SPECIFIER_RESOLVE_FOREIGN_KEYS)) {
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
    public List<Length> getAll() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL_LENGTHS_RESOLVE_FOREIGN_KEYS)) {
            List<Length> lengths = new ArrayList<>();
            while (resultSet.next()) {
                lengths.add(extractFromResultSet(resultSet));
            }
            return lengths;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void add(Length length) {
        // start transaction:
        Datasource2.INSTANCE.setAutoCommitBehavior(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LENGTH)) {
            setAllValues(preparedStatement, length);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
        } catch (Exception e) {
            Datasource2.INSTANCE.rollback(e, "Insert-length");
        } finally {
            Datasource2.INSTANCE.setAutoCommitBehavior(true);
        }

    }

    @Override
    public void update(Length length) {
        // start transaction:
        Datasource2.INSTANCE.setAutoCommitBehavior(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_LENGTH)) {
            preparedStatement.setInt(4, length.getId());
            setAllValues(preparedStatement, length);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
        } catch (Exception e) {
            Datasource2.INSTANCE.rollback(e, "Update-length");
        } finally {
            Datasource2.INSTANCE.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void delete(Length length) {
        // start transaction:
        Datasource2.INSTANCE.setAutoCommitBehavior(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_LENGTH)) {
            preparedStatement.setInt(1, length.getId());
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
        } catch (SQLException e) {
            Datasource2.INSTANCE.rollback(e, "Delete-length");
        } finally {
            Datasource2.INSTANCE.setAutoCommitBehavior(true);
        }
    }

    private Length extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Length(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3),
                new LengthUnit(resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6)));
    }

    private void setAllValues(PreparedStatement preparedStatement, Length length) throws SQLException {
        preparedStatement.setString(1, length.getName());
        preparedStatement.setDouble(2, length.getValue());
        preparedStatement.setInt(3, length.getUnit().getId());
    }


    @Override
    public String toString() {
        return "LengthDAOImpl{" +
                "connection=" + connection +
                '}';
    }
}
