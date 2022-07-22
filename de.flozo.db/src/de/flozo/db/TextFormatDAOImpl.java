package de.flozo.db;

import de.flozo.dto.appearance.TextFormat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TextFormatDAOImpl implements TextFormatDAO {

    // content
    public static final String TABLE_NAME = "text_formats";
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

    public TextFormatDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public TextFormat get(int id) {
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
    public TextFormat get(String specifier) {
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
    public List<TextFormat> getAll() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<TextFormat> textFormats = new ArrayList<>();
            while (resultSet.next()) {
                textFormats.add(extractFromResultSet(resultSet));
            }
            return textFormats;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    private TextFormat extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new TextFormat(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
    }

    @Override
    public String toString() {
        return "TextFormatDAOImpl{" +
                "connection=" + connection +
                '}';
    }
}
