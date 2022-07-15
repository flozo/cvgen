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

    // query
    public static final String QUERY_ADDRESS_BY_ID = SELECT + STAR + FROM + TABLE_ADDRESSES + WHERE + COLUMN_ADDRESSES_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_ADDRESS_BY_LABEL = SELECT + STAR + FROM + TABLE_ADDRESSES + WHERE + COLUMN_ADDRESSES_LABEL + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL_ADDRESSES = SELECT + STAR + FROM + TABLE_ADDRESSES;

    // insert
    public static final String INSERT_ADDRESS = INSERT_INTO + TABLE_ADDRESSES +
            OPENING_PARENTHESIS + COLUMN_ADDRESSES_LABEL + COMMA + COLUMN_ADDRESSES_ACADEMIC_TITLE + COMMA + COLUMN_ADDRESSES_FIRST_NAME + COMMA + COLUMN_ADDRESSES_SECOND_NAME + COMMA +
            COLUMN_ADDRESSES_LAST_NAME + COMMA + COLUMN_ADDRESSES_STREET + COMMA + COLUMN_ADDRESSES_HOUSE_NUMBER + COMMA + COLUMN_ADDRESSES_POSTAL_CODE + COMMA + COLUMN_ADDRESSES_CITY + COMMA +
            COLUMN_ADDRESSES_COUNTRY + COMMA + COLUMN_ADDRESSES_PHONE_NUMBER + COMMA + COLUMN_ADDRESSES_MOBILE_NUMBER + COMMA + COLUMN_ADDRESSES_E_MAIL_ADDRESS + COMMA +
            COLUMN_ADDRESSES_WEB_PAGE + CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(13) + CLOSING_PARENTHESIS;

    //    private PreparedStatement queryAddressById;
//    private PreparedStatement queryAddressByLabel;
    //    private PreparedStatement queryAllAddresses;
//    private PreparedStatement insertIntoAddresses;
    private Connection connection = Datasource2.INSTANCE.getConnection();


    public AddressDAOImpl() {
    }

//    private void close(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
//        Datasource2.INSTANCE.closeResultSet(resultSet);
//        Datasource2.INSTANCE.closePreparedStatement(preparedStatement);
//        Datasource2.INSTANCE.closeConnection(connection);
//    }
//    private void close(Connection connection) {
////        Datasource2.INSTANCE.closeResultSet(resultSet);
////        Datasource2.INSTANCE.closePreparedStatement(preparedStatement);
//        Datasource2.INSTANCE.closeConnection(connection);
//    }


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
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_ADDRESS_BY_LABEL)) {
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
            Datasource.getInstance().close(connection, statement, resultSet);
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


//    @Override
//    public List<Address> getAll() {
//        try {
//            Connection connection = Datasource.getInstance().getConnection();
//            System.out.println(QUERY_ALL_ADDRESSES);
//            queryAllAddresses = connection.prepareStatement(QUERY_ALL_ADDRESSES);
//            ResultSet resultSet = queryAllAddresses.executeQuery();
//            List<Address> addresses = new ArrayList<>();
//            while (resultSet.next()) {
//                addresses.add(new Address(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
//                        resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11), resultSet.getString(12),
//                        resultSet.getString(13), resultSet.getString(14), resultSet.getString(15)));
//            }
//            Datasource.getInstance().closeResultSet(resultSet);
//            Datasource.getInstance().closePreparedStatement(queryAllAddresses);
//            Datasource.getInstance().closeConnection(connection);
//            return addresses;
//        } catch (SQLException e) {
//            System.out.println("Query failed: " + e.getMessage());
//            return null;
//        }
//    }

    @Override
    public int add(Address address) {
        return 0;
    }

    @Override
    public int update(Address address) {
        return 0;
    }

    @Override
    public int delete(Address address) {
        return 0;
    }

}
