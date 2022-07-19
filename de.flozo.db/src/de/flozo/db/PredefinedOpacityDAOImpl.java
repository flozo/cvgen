package de.flozo.db;

import de.flozo.common.appearance.PredefinedOpacity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PredefinedOpacityDAOImpl implements PredefinedOpacityDAO {

    // content
    public static final String TABLE_NAME = "predefined_opacities";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_VALUE = "value";

    // sql
    public static final char QUESTION_MARK = '?';
    public static final char STAR = '*';
    public static final String SELECT = "SELECT ";
    public static final String FROM = " FROM ";
    public static final String WHERE = " WHERE ";
    public static final String EQUALS = " = ";

    // query
    public static final String QUERY_BY_ID = SELECT + STAR + FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + TABLE_NAME + WHERE + COLUMN_VALUE + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + TABLE_NAME;


    private final Connection connection;

    public PredefinedOpacityDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public PredefinedOpacity get(int id) {
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
    public PredefinedOpacity get(String specifier) {
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
    public List<PredefinedOpacity> getAll() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<PredefinedOpacity> predefinedOpacities = new ArrayList<>();
            while (resultSet.next()) {
                predefinedOpacities.add(extractFromResultSet(resultSet));
            }
            return predefinedOpacities;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    private PredefinedOpacity extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new PredefinedOpacity(resultSet.getInt(1), resultSet.getString(2));
    }

    @Override
    public String toString() {
        return "PredefinedOpacityDAOImpl{" +
                "connection=" + connection +
                '}';
    }
}
