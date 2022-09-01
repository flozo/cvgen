package de.flozo.db;

import de.flozo.common.dto.latex.LatexPackage;
import de.flozo.common.dto.latex.PackageOption;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PackageOptionDAOImpl implements PackageOptionDAO {

    // table
    public static final String TABLE_NAME = "package_options";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "option_string";
    public static final String COLUMN_PACKAGE_ID = "package_id";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_INCLUDE = "include";

    // view
    public static final String VIEW_NAME = "package_option_view";
    public static final String VIEW_COLUMN_ID = "_id";
    public static final String VIEW_COLUMN_NAME = "option_string";

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

    // package_option_view created via:

    // CREATE VIEW package_option_view AS
    // SELECT po._id, po.option_string, po.description, po.include,
    //   lp._id AS package_id, lp.name AS package_name, lp.value AS package_value, lp.include AS include_package
    // FROM package_options AS po
    // INNER JOIN latex_packages AS lp ON po.package_id = lp._id

    public static final String QUERY_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + VIEW_NAME;

    public static final int NON_ID_COLUMNS = 7;

    // insert
    public static final String INSERT = INSERT_INTO + TABLE_NAME + OPENING_PARENTHESIS +
            COLUMN_NAME + COMMA +
            COLUMN_PACKAGE_ID + COMMA +
            COLUMN_DESCRIPTION + COMMA +
            COLUMN_INCLUDE +
            CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(NON_ID_COLUMNS - 1) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ROW = UPDATE + TABLE_NAME + SET +
            COLUMN_NAME + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_PACKAGE_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_DESCRIPTION + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_INCLUDE + EQUALS + QUESTION_MARK +
            WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final int UPDATE_WHERE_POSITION = NON_ID_COLUMNS + 1;

    // delete
    public static final String DELETE = DELETE_FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;

    private final Datasource datasource;
    private final Connection connection;


    public PackageOptionDAOImpl(Datasource datasource, Connection connection) {
        this.datasource = datasource;
        this.connection = connection;
    }

    private void showSQLMessage(String queryString) {
        System.out.println("[database] Executing SQL statement \"" + queryString + "\" ...");
    }

    @Override
    public PackageOption get(int id) {
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
    public PackageOption get(String specifier) {
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
    public List<PackageOption> getAll() {
        showSQLMessage(QUERY_ALL);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<PackageOption> packageOptions = new ArrayList<>();
            while (resultSet.next()) {
                packageOptions.add(extractFromResultSet(resultSet));
            }
            System.out.println(" done!");
            return packageOptions;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void add(PackageOption packageOption) {
        // start transaction:
        datasource.setAutoCommitBehavior(false);
        showSQLMessage(INSERT);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            setAllValues(preparedStatement, packageOption);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource.rollback(e, "Insert-packageOption");
        } finally {
            datasource.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void update(PackageOption packageOption) {
        // start transaction:
        datasource.setAutoCommitBehavior(false);
        showSQLMessage(UPDATE_ROW);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)) {
            preparedStatement.setInt(UPDATE_WHERE_POSITION, packageOption.getId());
            setAllValues(preparedStatement, packageOption);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource.rollback(e, "Update-packageOption");
        } finally {
            datasource.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void delete(PackageOption packageOption) {
        // start transaction:
        datasource.setAutoCommitBehavior(false);
        showSQLMessage(DELETE);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, packageOption.getId());
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (SQLException e) {
            System.out.println();
            datasource.rollback(e, "Delete-packageOption");
        } finally {
            datasource.setAutoCommitBehavior(true);
        }
    }

    private PackageOption extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new PackageOption(resultSet.getInt(1), resultSet.getString(2),
                new LatexPackage(resultSet.getInt(5), resultSet.getString(6), resultSet.getString(7), resultSet.getBoolean(8)),
                resultSet.getString(3), resultSet.getBoolean(4)
        );
    }

    private void setAllValues(PreparedStatement preparedStatement, PackageOption packageOption) throws SQLException {
        preparedStatement.setString(1, packageOption.getOptionString());
        preparedStatement.setInt(2, packageOption.getLatexPackage().getId());
        preparedStatement.setString(3, packageOption.getDescription());
        preparedStatement.setBoolean(4, packageOption.isInclude());
    }

    @Override
    public String toString() {
        return "PackageOptionDAOImpl{" +
                "datasource2=" + datasource +
                ", connection=" + connection +
                '}';
    }
}
