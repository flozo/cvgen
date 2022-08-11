package de.flozo.db;

import de.flozo.common.dto.content.Address;
import de.flozo.common.dto.content.LetterContent;
import de.flozo.common.dto.content.Person;
import de.flozo.common.dto.content.Valediction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LetterContentDAOImpl implements LetterContentDAO {

    // table
    public static final String TABLE_NAME = "letter_content";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SENDER_ID = "sender_id";
    public static final String COLUMN_RECEIVER_ID = "receiver_id";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_LETTER_DATE = "letter_date";
    public static final String COLUMN_BODY_TEXT = "body_text";
    public static final String COLUMN_VALEDICTION_ID = "valediction_id";

    // view
    public static final String VIEW_NAME = "letter_content_view";
    public static final String VIEW_COLUMN_ID = "_id";
    public static final String VIEW_COLUMN_NAME = "name";

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

    // letter_content_view created via:

    // CREATE VIEW letter_content_view AS
    // SELECT lc._id, lc.name,
    //   avsd._id AS sender_id, avsd.label AS sender_label,
    //     avsd.person_id AS sender_person_id, avsd.person_label AS sender_person_label, avsd.academic_title AS sender_academic_title, avsd.first_name AS sender_first_name, avsd.second_name AS sender_second_name, avsd.last_name AS sender_last_name, avsd.date_of_birth AS sender_date_of_birth, avsd.place_of_birth AS sender_place_of_birth, avsd.marital_status AS sender_marital_status, avsd.children AS sender_children, avsd.nationality AS sender_nationality,
    //     avsd.company AS sender_company, avsd.street AS sender_street, avsd.house_number AS sender_house_number, avsd.postal_code AS sender_postal_code, avsd.city AS sender_city, avsd.country AS sender_country, avsd.phone_number AS sender_phone_number, avsd.mobile_number AS sender_mobile_number, avsd.email_address AS sender_email_address, avsd.web_page AS sender_web_page,
    //   avrc._id AS receiver_id, avrc.label AS receiver_label,
    //     avrc.person_id AS receiver_person_id, avrc.person_label AS receiver_person_label, avrc.academic_title AS receiver_academic_title, avrc.first_name AS receiver_first_name, avrc.second_name AS receiver_second_name, avrc.last_name AS receiver_last_name, avrc.date_of_birth AS receiver_date_of_birth, avrc.place_of_birth AS receiver_place_of_birth, avrc.marital_status AS receiver_marital_status, avrc.children AS receiver_children, avrc.nationality AS receiver_nationality,
    //     avrc.company AS receiver_company, avrc.street AS receiver_street, avrc.house_number AS receiver_house_number, avrc.postal_code AS receiver_postal_code, avrc.city AS receiver_city, avrc.country AS receiver_country, avrc.phone_number AS receiver_phone_number, avrc.mobile_number AS receiver_mobile_number, avrc.email_address AS receiver_email_address, avrc.web_page AS receiver_web_page,
    //   lc.subject, lc.letter_date, lc.body_text,
    //   v._id AS valediction_id, v.name AS valediction_name, v.value AS valediction_value
    // FROM letter_content AS lc
    // INNER JOIN address_view AS avsd ON lc.sender_id = avsd._id
    // INNER JOIN address_view AS avrc ON lc.receiver_id = avrc._id
    // INNER JOIN valedictions AS v ON lc.valediction_id = v._id


    public static final String QUERY_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + VIEW_NAME;

    public static final int NON_ID_COLUMNS = 7;

    // insert
    public static final String INSERT = INSERT_INTO + TABLE_NAME + OPENING_PARENTHESIS +
            COLUMN_NAME + COMMA +
            COLUMN_SENDER_ID + COMMA +
            COLUMN_RECEIVER_ID + COMMA +
            COLUMN_SUBJECT + COMMA +
            COLUMN_LETTER_DATE + COMMA +
            COLUMN_BODY_TEXT + COMMA +
            COLUMN_VALEDICTION_ID +
            CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(NON_ID_COLUMNS - 1) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ROW = UPDATE + TABLE_NAME + SET +
            COLUMN_NAME + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_SENDER_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_RECEIVER_ID + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_SUBJECT + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_LETTER_DATE + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_BODY_TEXT + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_VALEDICTION_ID + EQUALS + QUESTION_MARK +
            WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final int UPDATE_WHERE_POSITION = NON_ID_COLUMNS + 1;


    // delete
    public static final String DELETE = DELETE_FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;


    private final Datasource2 datasource2;
    private final Connection connection;


    public LetterContentDAOImpl(Datasource2 datasource2, Connection connection) {
        this.datasource2 = datasource2;
        this.connection = connection;
    }

    private void showSQLMessage(String queryString) {
        System.out.println("[database] Executing SQL statement \"" + queryString + "\" ...");
    }

    @Override
    public LetterContent get(int id) {
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
    public LetterContent get(String specifier) {
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
    public List<LetterContent> getAll() {
        showSQLMessage(QUERY_ALL);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<LetterContent> letterContents = new ArrayList<>();
            while (resultSet.next()) {
                letterContents.add(extractFromResultSet(resultSet));
            }
            System.out.println(" done!");
            return letterContents;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void add(LetterContent letterContent) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(INSERT);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            setAllValues(preparedStatement, letterContent);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource2.rollback(e, "Insert-letterContent");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void update(LetterContent letterContent) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(UPDATE_ROW);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)) {
            preparedStatement.setInt(UPDATE_WHERE_POSITION, letterContent.getId());
            setAllValues(preparedStatement, letterContent);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource2.rollback(e, "Update-letterContent");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    @Override
    public void delete(LetterContent letterContent) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(DELETE);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, letterContent.getId());
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (SQLException e) {
            System.out.println();
            datasource2.rollback(e, "Delete-letterContent");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    private LetterContent extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new LetterContent(resultSet.getInt(1), resultSet.getString(2),
                new Address(resultSet.getInt(3), resultSet.getString(4),
                        new Person(resultSet.getInt(5),resultSet.getString(6), resultSet.getString(7), resultSet.getString(8),
                                resultSet.getString(9), resultSet.getString(10), resultSet.getString(11), resultSet.getString(12),
                                resultSet.getString(13), resultSet.getString(14), resultSet.getString(15)),
                        resultSet.getString(16), resultSet.getString(17), resultSet.getString(18), resultSet.getString(19),
                        resultSet.getString(20), resultSet.getString(21), resultSet.getString(22), resultSet.getString(23),
                        resultSet.getString(24), resultSet.getString(25)),
                new Address(resultSet.getInt(26), resultSet.getString(27),
                        new Person(resultSet.getInt(28),resultSet.getString(29), resultSet.getString(30), resultSet.getString(31),
                                resultSet.getString(32), resultSet.getString(33), resultSet.getString(34), resultSet.getString(35),
                                resultSet.getString(36), resultSet.getString(37), resultSet.getString(38)),
                        resultSet.getString(39), resultSet.getString(40), resultSet.getString(41), resultSet.getString(42),
                        resultSet.getString(43), resultSet.getString(44), resultSet.getString(45), resultSet.getString(46),
                        resultSet.getString(47), resultSet.getString(48)),
                resultSet.getString(49), resultSet.getString(50), resultSet.getString(51),
                new Valediction(resultSet.getInt(52), resultSet.getString(53), resultSet.getString(54))
        );
    }

    private void setAllValues(PreparedStatement preparedStatement, LetterContent letterContent) throws SQLException {
        preparedStatement.setString(1, letterContent.getName());
        preparedStatement.setInt(2, letterContent.getSender().getId());
        preparedStatement.setInt(3, letterContent.getReceiver().getId());
        preparedStatement.setString(4, letterContent.getSubject());
        preparedStatement.setString(5, letterContent.getDate());
        preparedStatement.setString(6, letterContent.getBodyText());
        preparedStatement.setInt(7, letterContent.getValediction().getId());
    }


    @Override
    public String toString() {
        return "LetterContentDAOImpl{" +
                "datasource2=" + datasource2 +
                ", connection=" + connection +
                '}';
    }
}
