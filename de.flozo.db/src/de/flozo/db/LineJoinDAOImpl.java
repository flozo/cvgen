package de.flozo.db;

import de.flozo.common.appearance.LineJoin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineJoinDAOImpl implements LineJoinDAO {

    // content
    public static final String TABLE_LINE_JOINS = "line_joins";
    public static final String COLUMN_LINE_JOIN_ID = "_id";
    public static final String COLUMN_LINE_JOIN_NAME = "name";

    // sql
    public static final char QUESTION_MARK = '?';
    public static final char STAR = '*';
    public static final String SELECT = "SELECT ";
    public static final String FROM = " FROM ";
    public static final String WHERE = " WHERE ";
    public static final String EQUALS = " = ";

    // query
    public static final String QUERY_LINE_JOIN_BY_ID = SELECT + STAR + FROM + TABLE_LINE_JOINS + WHERE + COLUMN_LINE_JOIN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_LINE_JOIN_BY_SPECIFIER = SELECT + STAR + FROM + TABLE_LINE_JOINS + WHERE + COLUMN_LINE_JOIN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL_LINE_JOINS = SELECT + STAR + FROM + TABLE_LINE_JOINS;


    private Connection connection = Datasource2.INSTANCE.getConnection();

    public LineJoinDAOImpl() {
    }

    @Override
    public LineJoin get(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_LINE_JOIN_BY_ID)) {
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
    public LineJoin get(String specifier) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_LINE_JOIN_BY_SPECIFIER)) {
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
    public List<LineJoin> getAll() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL_LINE_JOINS)) {
            List<LineJoin> lineJoins = new ArrayList<>();
            while (resultSet.next()) {
                lineJoins.add(extractFromResultSet(resultSet));
            }
            return lineJoins;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    private LineJoin extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new LineJoin(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
    }

    @Override
    public String toString() {
        return "LineJoinDAOImpl{" +
                "connection=" + connection +
                '}';
    }
}
