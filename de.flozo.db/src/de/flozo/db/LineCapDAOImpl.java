package de.flozo.db;

import de.flozo.common.appearance.LineCap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LineCapDAOImpl implements LineCapDAO {

    // content
    public static final String TABLE_LINE_CAPS = "line_caps";
    public static final String COLUMN_LINE_CAP_ID = "_id";
    public static final String COLUMN_LINE_CAP_NAME = "name";

    // sql
    public static final char QUESTION_MARK = '?';
    public static final char STAR = '*';
    public static final String SELECT = "SELECT ";
    public static final String FROM = " FROM ";
    public static final String WHERE = " WHERE ";
    public static final String EQUALS = " = ";

    // query
    public static final String QUERY_LINE_CAP_BY_ID = SELECT + STAR + FROM + TABLE_LINE_CAPS + WHERE + COLUMN_LINE_CAP_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_LINE_CAP_BY_SPECIFIER = SELECT + STAR + FROM + TABLE_LINE_CAPS + WHERE + COLUMN_LINE_CAP_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL_LINE_CAPS = SELECT + STAR + FROM + TABLE_LINE_CAPS;


    private Connection connection = Datasource2.INSTANCE.getConnection();

    public LineCapDAOImpl() {
    }

    @Override
    public LineCap get(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_LINE_CAP_BY_ID)) {
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
    public LineCap get(String specifier) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_LINE_CAP_BY_SPECIFIER)) {
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
    public List<LineCap> getAll() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL_LINE_CAPS)) {
            List<LineCap> lineCaps = new ArrayList<>();
            while (resultSet.next()) {
                lineCaps.add(extractFromResultSet(resultSet));
            }
            return lineCaps;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    private LineCap extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new LineCap(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
    }

    @Override
    public String toString() {
        return "LineCapDAOImpl{" +
                "connection=" + connection +
                '}';
    }
}
