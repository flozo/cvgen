package de.flozo.db;

import de.flozo.common.dto.content.TextItem;
import de.flozo.common.dto.content.TimelineItem;
import de.flozo.common.dto.content.TimelineTextItemLink;
import de.flozo.common.dto.content.TimelineType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimelineItemDAOImpl implements TimelineItemDAO {

    // table
    public static final String TABLE_NAME = "timeline_items";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PERIOD_START_MONTH = "period_start_month";
    public static final String COLUMN_PERIOD_START_YEAR = "period_start_year";
    public static final String COLUMN_PERIOD_END_MONTH = "period_end_month";
    public static final String COLUMN_PERIOD_END_YEAR = "period_end_year";
    public static final String COLUMN_TASK = "task";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_COMPANY = "company";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_TIMELINE_TYPE_ID = "timeline_type_id";

    // view
    public static final String VIEW_NAME = "timeline_item_view";
    public static final String VIEW_COLUMN_ID = "_id";
    public static final String VIEW_COLUMN_NAME = "name";
    public static final String VIEW_COLUMN_TIMELINE_TYPE_ID = "timeline_type_id";
    public static final String VIEW_COLUMN_TIMELINE_TYPE_NAME = "timeline_type_name";

    // link table
    public static final String LINK_TABLE_NAME = "timeline_text_item_link_view";
    public static final String LINK_TABLE_COLUMN_ID = "_id";
    public static final String LINK_TABLE_COLUMN_NAME = "name";
    public static final String LINK_TABLE_COLUMN_TEXT_ITEM_ID = "text_item_id";
    public static final String LINK_TABLE_COLUMN_TEXT_ITEM_NAME = "text_item_name";
    public static final String LINK_TABLE_COLUMN_TEXT_ITEM_VALUE = "text_item_value";


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

    // timeline_item_view created via:

    // CREATE VIEW timeline_item_view AS
    // SELECT ti._id, ti.name, ti.period_start_month, ti.period_start_year, ti.period_end_month, ti.period_end_year, ti.task, ti.description, ti.company, ti.location,
    //	 tt._id AS timeline_type_id, tt.name AS timeline_type_name, tt.description AS timeline_type_description
    // FROM timeline_items AS ti
    // INNER JOIN timeline_types AS tt ON ti.timeline_type_id = tt._id

    // timeline_text_item_link_view created via:

    // CREATE VIEW timeline_text_item_link_view AS
    // SELECT tiv._id, tiv.name,
    //	 ti._id AS text_item_id, ti.name AS text_item_name, ti.value AS text_item_value
    // FROM timeline_item_view AS tiv
    // INNER JOIN timeline_text_item_link AS ttil ON ttil.timeline_item_id = tiv._id
    // INNER JOIN text_items AS ti ON ttil.text_item_id = ti._id
    public static final String QUERY_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + COLUMN_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL = SELECT + STAR + FROM + VIEW_NAME;
    public static final String QUERY_ALL_OF_TYPE_BY_ID = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_TIMELINE_TYPE_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_ALL_OF_TYPE_BY_SPECIFIER = SELECT + STAR + FROM + VIEW_NAME + WHERE + VIEW_COLUMN_TIMELINE_TYPE_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_TEXT_ITEMS_BY_ID = SELECT + STAR + FROM + LINK_TABLE_NAME + WHERE + LINK_TABLE_COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_TEXT_ITEMS_BY_SPECIFIER = SELECT + STAR + FROM + LINK_TABLE_NAME + WHERE + LINK_TABLE_COLUMN_NAME + EQUALS + QUESTION_MARK;

    // count
    public static final String COUNT_ITEMS = SELECT + "COUNT(DISTINCT " + LINK_TABLE_COLUMN_ID + ") AS count" + FROM + LINK_TABLE_NAME + WHERE ;


    public static final int NON_ID_COLUMNS = 10;

    // insert
    public static final String INSERT = INSERT_INTO + TABLE_NAME + OPENING_PARENTHESIS +
            COLUMN_NAME + COMMA +
            COLUMN_PERIOD_START_MONTH + COMMA +
            COLUMN_PERIOD_START_YEAR + COMMA +
            COLUMN_PERIOD_END_MONTH + COMMA +
            COLUMN_PERIOD_END_YEAR + COMMA +
            COLUMN_TASK + COMMA +
            COLUMN_DESCRIPTION + COMMA +
            COLUMN_COMPANY + COMMA +
            COLUMN_LOCATION + COMMA +
            COLUMN_TIMELINE_TYPE_ID +
            CLOSING_PARENTHESIS + VALUES + OPENING_PARENTHESIS + QUESTION_MARK + (COMMA + QUESTION_MARK).repeat(NON_ID_COLUMNS - 1) + CLOSING_PARENTHESIS;

    // update
    public static final String UPDATE_ROW = UPDATE + TABLE_NAME + SET +
            COLUMN_NAME + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_PERIOD_START_MONTH + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_PERIOD_START_YEAR + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_PERIOD_END_MONTH + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_PERIOD_END_YEAR + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_TASK + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_DESCRIPTION + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_COMPANY + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_LOCATION + EQUALS + QUESTION_MARK + COMMA +
            COLUMN_TIMELINE_TYPE_ID + EQUALS + QUESTION_MARK +
            WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;
    public static final int UPDATE_WHERE_POSITION = NON_ID_COLUMNS + 1;

    // delete
    public static final String DELETE = DELETE_FROM + TABLE_NAME + WHERE + COLUMN_ID + EQUALS + QUESTION_MARK;

    private final Datasource2 datasource2;
    private final Connection connection;

    public TimelineItemDAOImpl(Datasource2 datasource2, Connection connection) {
        this.datasource2 = datasource2;
        this.connection = connection;
    }

    private void showSQLMessage(String queryString) {
        System.out.println("[database] Executing SQL statement \"" + queryString + "\" ...");
    }

    @Override
    public int getCount() {
        showSQLMessage(COUNT_ITEMS);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(COUNT_ITEMS)) {
            return resultSet.getInt("count");
        } catch (SQLException e) {
            System.out.println("[database] [error] Counting items failed: " + e.getMessage());
            return -1;
        }

    }

    @Override
    public TimelineItem get(int id) {
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
    public TimelineItem get(String specifier) {
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
    public List<TimelineItem> getAll() {
        showSQLMessage(QUERY_ALL);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_ALL)) {
            List<TimelineItem> timelineItems = new ArrayList<>();
            while (resultSet.next()) {
                timelineItems.add(extractFromResultSet(resultSet));
            }
            System.out.println(" done!");
            return timelineItems;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<TimelineItem> getAllOfType(int id) {
        showSQLMessage(QUERY_ALL_OF_TYPE_BY_ID);
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_BY_ID)) {
            preparedStatement.setInt(1, id);
            List<TimelineItem> timelineItems = new ArrayList<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    timelineItems.add(extractFromResultSet(resultSet));
                }
            }
            System.out.println(" done!");
            return timelineItems;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<TimelineItem> getAllOfType(String specifier) {
        showSQLMessage(QUERY_ALL_OF_TYPE_BY_SPECIFIER);
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_ALL_OF_TYPE_BY_SPECIFIER)) {
            preparedStatement.setString(1, specifier);
            List<TimelineItem> timelineItems = new ArrayList<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    timelineItems.add(extractFromResultSet(resultSet));
                }
            }
            System.out.println(" done!");
            return timelineItems;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<TimelineTextItemLink> getTextItems(int id) {
        showSQLMessage(QUERY_TEXT_ITEMS_BY_ID);
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_TEXT_ITEMS_BY_ID)) {
            preparedStatement.setInt(1, id);
            List<TimelineTextItemLink> timelineTextItemLinks = new ArrayList<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    timelineTextItemLinks.add(extractFromLinkResultSet(resultSet));
                }
            }
            System.out.println(" done!");
            return timelineTextItemLinks;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<TimelineTextItemLink> getTextItems(String specifier) {
        showSQLMessage(QUERY_TEXT_ITEMS_BY_SPECIFIER);
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_TEXT_ITEMS_BY_SPECIFIER)) {
            preparedStatement.setString(1, specifier);
            List<TimelineTextItemLink> timelineTextItemLinks = new ArrayList<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    timelineTextItemLinks.add(extractFromLinkResultSet(resultSet));
                }
            }
            System.out.println(" done!");
            return timelineTextItemLinks;
        } catch (SQLException e) {
            System.out.println();
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void add(TimelineItem timelineItem) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(INSERT);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            setAllValues(preparedStatement, timelineItem);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource2.rollback(e, "Insert-timelineItem");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }

    }

    @Override
    public void update(TimelineItem timelineItem) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(UPDATE_ROW);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROW)) {
            preparedStatement.setInt(UPDATE_WHERE_POSITION, timelineItem.getId());
            setAllValues(preparedStatement, timelineItem);
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (Exception e) {
            System.out.println();
            datasource2.rollback(e, "Update-timelineItem");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }

    }

    @Override
    public void delete(TimelineItem timelineItem) {
        // start transaction:
        datasource2.setAutoCommitBehavior(false);
        showSQLMessage(DELETE);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, timelineItem.getId());
            // do it
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 1) {
                connection.commit();
                // end of transaction
            }
            System.out.println(" done!");
        } catch (SQLException e) {
            System.out.println();
            datasource2.rollback(e, "Delete-timelineItem");
        } finally {
            datasource2.setAutoCommitBehavior(true);
        }
    }

    private TimelineTextItemLink extractFromLinkResultSet(ResultSet resultSet) throws SQLException {
        return new TimelineTextItemLink(resultSet.getInt(1), resultSet.getString(2),
                new TextItem(resultSet.getInt(3), resultSet.getString(4), resultSet.getString(5)));
    }


    private TimelineItem extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new TimelineItem(resultSet.getInt(1), resultSet.getString(2),
                // period start
                resultSet.getInt(3), resultSet.getInt(4),
                // period end
                resultSet.getInt(5), resultSet.getInt(6),
                resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10),
                new TimelineType(resultSet.getInt(11), resultSet.getString(12), resultSet.getString(13))
        );
    }

    private void setAllValues(PreparedStatement preparedStatement, TimelineItem timelineItem) throws SQLException {
        preparedStatement.setString(1, timelineItem.getName());
        preparedStatement.setInt(2, timelineItem.getPeriodStartMonth());
        preparedStatement.setInt(3, timelineItem.getPeriodStartYear());
        preparedStatement.setInt(4, timelineItem.getPeriodEndMonth());
        preparedStatement.setInt(5, timelineItem.getPeriodEndYear());
        preparedStatement.setString(6, timelineItem.getTask());
        preparedStatement.setString(7, timelineItem.getDescription());
        preparedStatement.setString(8, timelineItem.getCompany());
        preparedStatement.setString(9, timelineItem.getLocation());
        preparedStatement.setInt(10, timelineItem.getTimelineType().getId());
    }

    @Override
    public String toString() {
        return "TimelineItemDAOImpl{" +
                "datasource2=" + datasource2 +
                ", connection=" + connection +
                '}';
    }
}
