package de.flozo.db;

import de.flozo.common.content.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDAOImpl implements AddressDAO {

    // content
    public static final String TABLE_ADDRESSES = "addresses";
    public static final String COLUMN_ADDRESSES_ID = "_id";
    public static final String COLUMN_ADDRESSES_LABEL = "label";
    public static final String COLUMN_ADDRESSES_ACADEMIC_TITLE = "academic_title";
    public static final String COLUMN_ADDRESSES_FIRST_NAME = "first_name";
    public static final String COLUMN_ADDRESSES_SECOND_NAME = "second_name";
    public static final String COLUMN_ADDRESSES_LAST_NAME = "last_name";
    public static final String COLUMN_ADDRESSES_STREET = "street";
    public static final String COLUMN_ADDRESSES_HOUSE_NUMBER = "house_number";
    public static final String COLUMN_ADDRESSES_POSTAL_CODE = "postal_code";
    public static final String COLUMN_ADDRESSES_CITY = "city";
    public static final String COLUMN_ADDRESSES_COUNTRY = "country";
    public static final String COLUMN_ADDRESSES_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_ADDRESSES_MOBILE_NUMBER = "mobile_number";
    public static final String COLUMN_ADDRESSES_E_MAIL_ADDRESS = "email_address";
    public static final String COLUMN_ADDRESSES_WEB_PAGE = "web_page";

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
    public static final String QUERY_ADDRESS_BY_ID = SELECT + STAR + FROM + TABLE_ADDRESSES + WHERE + COLUMN_ADDRESSES_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_ADDRESS_BY_SPECIFIER = SELECT + STAR + FROM + TABLE_ADDRESSES + WHERE + COLUMN_ADDRESSES_LABEL + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL_ADDRESSES = SELECT + STAR + FROM + TABLE_ADDRESSES;

    // insert
    public static final String INSERT_ADDRESS = INSERT_INTO + TABLE_ADDRESSES +
            OPENING_PARENTHESIS + COLUMN_ADDRESSES_LABEL + COMMA + COLUMN_ADDRESSES_ACADEMIC_TITLE + COMMA + COLUMN_ADDRESSES_FIRST_NAME + COMMA + COLUMN_ADDRESSES_SECOND_NAME + COMMA +
            COLUMN_ADDRESSES_LAST_NAME + COMMA + COLUMN_ADDRESSES_STREET + COMMA + COLUMN_ADDRESSES_HOUSE_NUMBER + COMMA + COLUMN_ADDRESSES_POSTAL_CODE + COMMA + COLUMN_ADDRESSES_CITY + COMMA +
            COLUMN_ADDRESSES_COUNTRY + COMMA + COLUMN_ADDRESSES_PHONE_NUMBER + COMMA + COLUMN_ADDRESSES_MOBILE_NUMBER + COMMA + COLUMN_ADDRESSES_E_MAIL_ADDRESS + COMMA +
            COLUMN_ADDRESSES_WEB_PAGE + CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(13) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ADDRESS = UPDATE + TABLE_ADDRESSES + SET +
            COLUMN_ADDRESSES_LABEL + EQUALS + QUESTION_MARK + COMMA + COLUMN_ADDRESSES_ACADEMIC_TITLE + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_ADDRESSES_FIRST_NAME + EQUALS + QUESTION_MARK + COMMA + COLUMN_ADDRESSES_SECOND_NAME + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_ADDRESSES_LAST_NAME + EQUALS + QUESTION_MARK + COMMA + COLUMN_ADDRESSES_STREET + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_ADDRESSES_HOUSE_NUMBER + EQUALS + QUESTION_MARK + COMMA + COLUMN_ADDRESSES_POSTAL_CODE + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_ADDRESSES_CITY + EQUALS + QUESTION_MARK + COMMA + COLUMN_ADDRESSES_COUNTRY + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_ADDRESSES_PHONE_NUMBER + EQUALS + QUESTION_MARK + COMMA + COLUMN_ADDRESSES_MOBILE_NUMBER + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_ADDRESSES_E_MAIL_ADDRESS + EQUALS + QUESTION_MARK + COMMA + COLUMN_ADDRESSES_WEB_PAGE + EQUALS + QUESTION_MARK +
            WHERE + COLUMN_ADDRESSES_ID + EQUALS + QUESTION_MARK;

    // count
    public static final String COUNT_ADDRESSES = SELECT + "count(*) AS count" + FROM + TABLE_ADDRESSES;

    // delete
    public static final String DELETE_ADDRESS = DELETE_FROM + TABLE_ADDRESSES + WHERE + COLUMN_ADDRESSES_ID + EQUALS + QUESTION_MARK;


    private Connection connection = Datasource2.INSTANCE.getConnection();

    public AddressDAOImpl() {
    }

    @Override
    public void showMetadata() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL_ADDRESSES)) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int column = 1; column <= metaData.getColumnCount(); column++) {
                System.out.format("Column %d: ", column);
                System.out.format("TableName = %s, ", metaData.getTableName(column));
//                System.out.format("CatalogName = %s, ", metaData.getCatalogName(column));
//                System.out.format("ColumnClassName = %s, ", metaData.getColumnClassName(column));
//                System.out.format("SchemaName = %s, ", metaData.getSchemaName(column));
                System.out.format("ColumnTypeName = %s, ", metaData.getColumnTypeName(column));
//                System.out.format("ColumnType = %s, ", metaData.getColumnType(column));
//                System.out.format("ColumnLabel = %s, ", metaData.getColumnLabel(column));
                System.out.format("ColumnName = %s, ", metaData.getColumnName(column));
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("[database] [error] Querying metadata failed!");
        }
    }

    @Override
    public int getCount() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(COUNT_ADDRESSES)) {
            return resultSet.getInt("count");
        } catch (SQLException e) {
            System.out.println("[database] [error] Counting rows failed!");
            return -1;
        }
    }


    @Override
    public Address get(int id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_ADDRESS_BY_ID)) {
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
    public Address get(String specifier) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_ADDRESS_BY_SPECIFIER)) {
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
    public List<Address> getAll() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL_ADDRESSES)) {
            List<Address> addresses = new ArrayList<>();
            while (resultSet.next()) {
                addresses.add(extractFromResultSet(resultSet));
            }
            return addresses;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    private Address extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Address(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
                resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11), resultSet.getString(12),
                resultSet.getString(13), resultSet.getString(14), resultSet.getString(15));
    }

    private void setAllValues(PreparedStatement preparedStatement, Address address) throws SQLException {
        preparedStatement.setString(1, address.getLabel());
        preparedStatement.setString(2, address.getAcademicTitle());
        preparedStatement.setString(3, address.getFirstName());
        preparedStatement.setString(4, address.getSecondName());
        preparedStatement.setString(5, address.getLastName());
        preparedStatement.setString(6, address.getStreet());
        preparedStatement.setString(7, address.getHouseNumber());
        preparedStatement.setString(8, address.getPostalCode());
        preparedStatement.setString(9, address.getCity());
        preparedStatement.setString(10, address.getCountry());
        preparedStatement.setString(11, address.getPhoneNumber());
        preparedStatement.setString(12, address.getMobileNumber());
        preparedStatement.setString(13, address.getEMailAddress());
        preparedStatement.setString(14, address.getWebPage());
    }


    @Override
    public void add(Address address) {
        // start transaction:
        Datasource2.INSTANCE.setAutoCommitBehavior(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ADDRESS)) {
            setAllValues(preparedStatement, address);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
        } catch (Exception e) {
            Datasource2.INSTANCE.rollback(e, "Insert-address");
        } finally {
            Datasource2.INSTANCE.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void update(Address address) {
        // start transaction:
        Datasource2.INSTANCE.setAutoCommitBehavior(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ADDRESS)) {
            preparedStatement.setInt(15, address.getId());
            setAllValues(preparedStatement, address);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
        } catch (Exception e) {
            Datasource2.INSTANCE.rollback(e, "Update-address");
        } finally {
            Datasource2.INSTANCE.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void delete(Address address) {
        // start transaction:
        Datasource2.INSTANCE.setAutoCommitBehavior(false);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ADDRESS)) {
            preparedStatement.setInt(1, address.getId());
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
        } catch (SQLException e) {
            Datasource2.INSTANCE.rollback(e, "Delete-address");
        } finally {
            Datasource2.INSTANCE.setAutoCommitBehavior(true);
        }
    }

}
