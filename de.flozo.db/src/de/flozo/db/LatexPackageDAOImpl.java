package de.flozo.db;

import de.flozo.common.dto.latex.LatexPackage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LatexPackageDAOImpl implements LatexPackageDAO {

    // table
    public static final String TABLE_NAME = "latex_packages";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    // view
    public static final String VIEW_NAME = "package_list_with_included_options_view";
    public static final String VIEW_COLUMN_ID = "_id";
    public static final String VIEW_COLUMN_NAME = "name";
    public static final String VIEW_COLUMN_INCLUDE = "include";


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

    // query

    // package_list_with_included_options_view created via:

    // CREATE VIEW package_list_with_included_options_view AS
    // SELECT lp._id, lp.name, lp.value, lp.include,
    //   group_concat(pov.option_string, ", ") AS option_list
    // FROM latex_packages AS lp
    // LEFT JOIN (SELECT * FROM package_option_view AS pov WHERE pov.include = 1) AS pov
    // ON pov.package_id = lp._id
    // WHERE lp.value IS NOT NULL
    // GROUP BY lp._id

    public static final String QUERY_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + VIEW_NAME;
    public static final String QUERY_ALL_INCLUDED = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_INCLUDE + EQUALS + "1";

    private final Datasource2 datasource2;
    private final Connection connection;


    public LatexPackageDAOImpl(Datasource2 datasource2, Connection connection) {
        this.datasource2 = datasource2;
        this.connection = connection;
    }

    private void showSQLMessage(String queryString) {
        System.out.println("[database] Executing SQL statement \"" + queryString + "\" ...");
    }

    @Override
    public LatexPackage get(int id) {
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
    public LatexPackage get(String specifier) {
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
    public List<LatexPackage> getAll() {
        showSQLMessage(QUERY_ALL);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<LatexPackage> latexPackages = new ArrayList<>();
            while (resultSet.next()) {
                latexPackages.add(extractFromResultSet(resultSet));
            }
            return latexPackages;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<LatexPackage> getAllIncluded() {
        showSQLMessage(QUERY_ALL_INCLUDED);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL_INCLUDED)) {
            List<LatexPackage> latexPackages = new ArrayList<>();
            while (resultSet.next()) {
                latexPackages.add(extractFromResultSet(resultSet));
            }
            return latexPackages;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    private LatexPackage extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new LatexPackage(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBoolean(4), resultSet.getString(5));
    }


    @Override
    public String toString() {
        return "LatexPackageDAOImpl{" +
                "datasource2=" + datasource2 +
                ", connection=" + connection +
                '}';
    }
}
