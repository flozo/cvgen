package de.flozo.db;

import java.sql.PreparedStatement;
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

    // insert
    public static final String INSERT_ADDRESS = INSERT_INTO + TABLE_ADDRESSES +
            OPENING_PARENTHESIS + COLUMN_ADDRESSES_LABEL + COMMA + COLUMN_ADDRESSES_ACADEMIC_TITLE + COMMA + COLUMN_ADDRESSES_FIRST_NAME + COMMA + COLUMN_ADDRESSES_SECOND_NAME + COMMA +
            COLUMN_ADDRESSES_LAST_NAME + COMMA + COLUMN_ADDRESSES_STREET + COMMA + COLUMN_ADDRESSES_HOUSE_NUMBER + COMMA + COLUMN_ADDRESSES_POSTAL_CODE + COMMA + COLUMN_ADDRESSES_CITY + COMMA +
            COLUMN_ADDRESSES_COUNTRY + COMMA + COLUMN_ADDRESSES_PHONE_NUMBER + COMMA + COLUMN_ADDRESSES_MOBILE_NUMBER + COMMA + COLUMN_ADDRESSES_E_MAIL_ADDRESS + COMMA +
            COLUMN_ADDRESSES_WEB_PAGE + CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(13) + CLOSING_PARENTHESIS;

    private PreparedStatement queryAddressById;
    private PreparedStatement queryAddressByLabel;
    private PreparedStatement insertIntoAddresses;


    public AddressDAOImpl() {
    }

    @Override
    public AddressDAOImpl get(int id) {
        return null;
    }

    @Override
    public AddressDAOImpl get(String specifier) {
        return null;
    }

    @Override
    public List<AddressDAOImpl> getAll() {
        return null;
    }

    @Override
    public int add(AddressDAOImpl addressDAO) {
        return 0;
    }

    @Override
    public int update(AddressDAOImpl addressDAO) {
        return 0;
    }

    @Override
    public int delete(AddressDAOImpl addressDAO) {
        return 0;
    }
}
