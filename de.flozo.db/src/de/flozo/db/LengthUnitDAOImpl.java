package de.flozo.db;

import de.flozo.common.appearance.LengthUnit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LengthUnitDAOImpl implements LengthUnitDAO {

    // content
    public static final String TABLE_NAME = "length_units";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    // sql
    public static final char QUESTION_MARK = '?';
    public static final char STAR = '*';
    public static final String SELECT = "SELECT ";
    public static final String FROM = " FROM ";
    public static final String WHERE = " WHERE ";
    public static final String EQUALS = " = ";

    // query
    public static final String QUERY_BY_ID = SELECT + STAR + FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + TABLE_NAME + WHERE + COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + TABLE_NAME;

    private Connection connection = Datasource2.INSTANCE.getConnection();

    public LengthUnitDAOImpl() {
    }

    @Override
    public LengthUnit get(int id) {
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
    public LengthUnit get(String specifier) {
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
    public List<LengthUnit> getAll() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<LengthUnit> lengthUnits = new ArrayList<>();
            while (resultSet.next()) {
                lengthUnits.add(extractFromResultSet(resultSet));
            }
            return lengthUnits;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    private LengthUnit extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new LengthUnit(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
    }

    @Override
    public String toString() {
        return "LengthUnitDAOImpl{" +
                "connection=" + connection +
                '}';
    }
}
