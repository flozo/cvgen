package de.flozo.db;

import de.flozo.common.appearance.Anchor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnchorDAOImpl implements AnchorDAO {

    // content
    public static final String TABLE_ANCHORS = "anchors";
    public static final String COLUMN_ANCHOR_ID = "_id";
    public static final String COLUMN_ANCHOR_NAME = "name";

    // sql
    public static final char QUESTION_MARK = '?';
    public static final char STAR = '*';
    public static final String SELECT = "SELECT ";
    public static final String FROM = " FROM ";
    public static final String WHERE = " WHERE ";
    public static final String EQUALS = " = ";

    // query
    public static final String QUERY_ANCHOR_BY_ID = SELECT + STAR + FROM + TABLE_ANCHORS + WHERE + COLUMN_ANCHOR_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_ANCHOR_BY_SPECIFIER = SELECT + STAR + FROM + TABLE_ANCHORS + WHERE + COLUMN_ANCHOR_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL_ANCHORS = SELECT + STAR + FROM + TABLE_ANCHORS;


    private Connection connection = Datasource2.INSTANCE.getConnection();


    public AnchorDAOImpl() {
    }

    @Override
    public Anchor get(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_ANCHOR_BY_ID)) {
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
    public Anchor get(String specifier) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_ANCHOR_BY_SPECIFIER)) {
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
    public List<Anchor> getAll() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL_ANCHORS)) {
            List<Anchor> anchors = new ArrayList<>();
            while (resultSet.next()) {
                anchors.add(extractFromResultSet(resultSet));
            }
            return anchors;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    private Anchor extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Anchor(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
    }


    @Override
    public String toString() {
        return "AnchorDAOImpl{" +
                "connection=" + connection +
                '}';
    }
}