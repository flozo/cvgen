package de.flozo.db;

import de.flozo.common.Length;
import de.flozo.common.LengthUnit;
import de.flozo.common.NamedLength;

import java.sql.*;

public class Datasource {

    // database
    public static final String DB_NAME = "properties.db";
    public static final String CONNECTION_STRING_PREFIX = "jdbc:sqlite:";
    public static final String RESOURCE_FOLDER = "src/resource/";

    // general
    public static final String ID = "_id";
    public static final String COLOR = "color";
    public static final String OPACITY = "opacity";
    public static final String NAME = "name";
    public static final String VALUE = "value";
    public static final String POSITION = "position";
    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";
    public static final String ANCHOR = "anchor";
    public static final String FONT_SIZE = "font_size";
    public static final String LINE = "line";
    public static final String AREA = "area";
    public static final String UNIT = "unit";


    // tables and columns
    public static final String TABLE_ANCHORS = "anchor_styles";
    public static final String COLUMN_ANCHOR_ID = ID;
    public static final String COLUMN_ANCHOR_VALUE = VALUE;

    public static final String TABLE_AREA_STYLES = "area_styles";
    public static final String COLUMN_AREA_STYLE_ID = ID;
    public static final String COLUMN_AREA_STYLE_NAME = NAME;
    public static final String COLUMN_AREA_STYLE_COLOR_ID = COLOR + ID;
    public static final String COLUMN_AREA_STYLE_OPACITY_ID = OPACITY + ID;

    public static final String TABLE_BASE_COLORS = "base_colors";
    public static final String COLUMN_BASE_COLOR_ID = ID;
    public static final String COLUMN_BASE_COLOR_NAME = NAME;

    public static final String TABLE_DASH_PATTERNS = "dash_patterns";
    public static final String COLUMN_DASH_PATTERN_ID = ID;
    public static final String COLUMN_DASH_PATTERN_NAME = NAME;

    public static final String TABLE_ELEMENTS = "elements";
    public static final String COLUMN_ELEMENT_ID = ID;
    public static final String COLUMN_ELEMENT_NAME = NAME;
    public static final String COLUMN_ELEMENT_POSITION_ID = POSITION + ID;
    public static final String COLUMN_ELEMENT_WIDTH_ID = WIDTH + ID;
    public static final String COLUMN_ELEMENT_HEIGHT_ID = HEIGHT + ID;
    public static final String COLUMN_ELEMENT_ANCHOR_ID = ANCHOR + ID;
    public static final String COLUMN_ELEMENT_FONT_SIZE_ID = FONT_SIZE + ID;
    public static final String COLUMN_ELEMENT_LINE_STYLE_ID = LINE + "_style" + ID;
    public static final String COLUMN_ELEMENT_AREA_STYLE = AREA + "_style" + ID;

    public static final String TABLE_FONT_SIZES = "font_sizes";
    public static final String COLUMN_FONT_SIZE_ID = ID;
    public static final String COLUMN_FONT_SIZE_NAME = NAME;
    public static final String COLUMN_FONT_SIZE_VALUE = VALUE;

    public static final String TABLE_NAMED_LENGTHS = "named_lengths";
    public static final String COLUMN_NAMED_LENGTH_ID = ID;
    public static final String COLUMN_NAMED_LENGTH_NAME = NAME;
    public static final String COLUMN_NAMED_LENGTH_VALUE = VALUE;
    public static final String COLUMN_NAMED_LENGTH_LENGTH_UNIT_ID = UNIT + ID;

    public static final String TABLE_LENGTH_UNITS = "length_units";
    public static final String COLUMN_LENGTH_UNIT_ID = ID;
    public static final String COLUMN_LENGTH_UNIT_NAME = NAME;
    public static final String COLUMN_LENGTH_UNIT_VALUE = VALUE;


    public static final String TABLE_LINE_CAPS = "line_caps";
    public static final String COLUMN_LINE_CAP_ID = ID;
    public static final String COLUMN_LINE_CAP_NAME = NAME;

    public static final String TABLE_LINE_JOINS = "line_joins";
    public static final String COLUMN_LINE_JOIN_ID = ID;
    public static final String COLUMN_LINE_JOIN_NAME = NAME;

    public static final String TABLE_LINE_STYLES = "line_styles";
    public static final String COLUMN_LINE_STYLE_ID = ID;
    public static final String COLUMN_LINE_STYLE_NAME = NAME;
    public static final String COLUMN_LINE_STYLE_LINE_WIDTH_ID = "line_width" + ID;
    public static final String COLUMN_LINE_STYLE_LINE_CAP_ID = "line_cap" + ID;
    public static final String COLUMN_LINE_STYLE_LINE_JOIN_ID = "line_join" + ID;
    public static final String COLUMN_LINE_STYLE_DASH_PATTERN_ID = "dash_pattern" + ID;
    public static final String COLUMN_LINE_STYLE_COLOR_ID = COLOR + ID;
    public static final String COLUMN_LINE_STYLE_OPACITY_ID = OPACITY + ID;

    public static final String TABLE_NAMED_LINE_WIDTHS = "named_line_widths";
    public static final String COLUMN_NAMED_LINE_WIDTH_ID = ID;
    public static final String COLUMN_NAMED_LINE_WIDTH_NAME = NAME;

    public static final String TABLE_NAMED_OPACITIES = "named_opacities";
    public static final String COLUMN_NAMED_OPACITY_ID = ID;
    public static final String COLUMN_NAMED_OPACITY_NAME = NAME;

    public static final String TABLE_POSITIONS = "positions";
    public static final String COLUMN_POSITION_ID = ID;
    public static final String COLUMN_POSITION_NAME = NAME;
    public static final String COLUMN_POSITION_X_VALUE = "x_value";
    public static final String COLUMN_POSITION_X_UNIT_ID = "x_unit" + ID;
    public static final String COLUMN_POSITION_Y_VALUE = "y_value";
    public static final String COLUMN_POSITION_Y_UNIT_ID = "y_unit" + ID;

    public static final String TABLE_TEXT_FORMATS = "text_formats";
    public static final String COLUMN_TEXT_FORMAT_ID = ID;
    public static final String COLUMN_TEXT_FORMAT_NAME = NAME;
    public static final String COLUMN_TEXT_FORMAT_VALUE = VALUE;

    public static final String TABLE_TEXT_STYLES = "text_styles";
    public static final String COLUMN_TEXT_STYLE_ID = ID;
    public static final String COLUMN_TEXT_STYLE_NAME = NAME;
    public static final String COLUMN_TEXT_STYLE_FONT_SIZE_ID = FONT_SIZE + ID;
    public static final String COLUMN_TEXT_STYLE_TEXT_FORMAT_ID = "text_format" + ID;
    public static final String COLUMN_TEXT_STYLE_COLOR_ID = COLOR + ID;
    public static final String COLUMN_TEXT_STYLE_OPACITY_ID = OPACITY + ID;


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
    // SELECT * FROM length_units WHERE _id=?
    public static final String QUERY_LENGTH_UNIT = SELECT + STAR + FROM + TABLE_LENGTH_UNITS + WHERE + COLUMN_LENGTH_UNIT_ID + EQUALS + QUESTION_MARK;
    // SELECT * FROM named_lengths WHERE _id=?;
    public static final String QUERY_NAMED_LENGTH = SELECT + STAR + FROM + TABLE_NAMED_LENGTHS + WHERE + COLUMN_LENGTH_UNIT_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_POSITION = SELECT + STAR + FROM + TABLE_POSITIONS + WHERE + COLUMN_POSITION_ID + EQUALS + QUESTION_MARK;


    //    public static final String QUERY_POSITION = SELECT + COLUMN_POSITION_ID + FROM + TABLE_POSITIONS + WHERE + COLUMN_POSITION_NAME + EQUALS + QUESTION_MARK;
    public static final String QUERY_ELEMENT = SELECT + COLUMN_ELEMENT_ID + FROM + TABLE_ELEMENTS + WHERE + COLUMN_ELEMENT_NAME + EQUALS + QUESTION_MARK;

    // insert
    public static final String INSERT_POSITION = INSERT_INTO + TABLE_POSITIONS +
            OPENING_PARENTHESIS + COLUMN_POSITION_X_VALUE + COMMA + COLUMN_POSITION_X_UNIT_ID + COMMA + COLUMN_POSITION_Y_VALUE + COMMA + COLUMN_POSITION_Y_UNIT_ID + CLOSING_PARENTHESIS +
            VALUES + OPENING_PARENTHESIS + QUESTION_MARK + COMMA + QUESTION_MARK + COMMA + QUESTION_MARK + COMMA + QUESTION_MARK + CLOSING_PARENTHESIS;

    private final String connectionString = CONNECTION_STRING_PREFIX + RESOURCE_FOLDER + DB_NAME;

    private Connection connection;

    private PreparedStatement queryLengthUnit;
    private PreparedStatement queryNamedLength;
    private PreparedStatement queryPosition;


    private PreparedStatement queryElement;
    private PreparedStatement insertIntoPositions;

    private static Datasource instance = new Datasource();

    private Datasource() {
    }

    public static Datasource getInstance() {
        return instance;
    }


    public boolean open() {
        try {
            connection = DriverManager.getConnection(connectionString);
            queryLengthUnit = connection.prepareStatement(QUERY_LENGTH_UNIT);
            queryNamedLength = connection.prepareStatement(QUERY_NAMED_LENGTH);
            queryPosition = connection.prepareStatement(QUERY_POSITION, Statement.RETURN_GENERATED_KEYS);

//            insertIntoPositions = connection.prepareStatement(INSERT_POSITION, Statement.RETURN_GENERATED_KEYS);

            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (queryLengthUnit != null) {
                queryLengthUnit.close();
            }
            if (queryNamedLength != null) {
                queryNamedLength.close();
            }
            if (queryPosition != null) {
                queryPosition.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }


    public LengthUnit queryLengthUnitById(int id) {
        try {
            queryLengthUnit.setInt(1, id);
            ResultSet resultSet = queryLengthUnit.executeQuery();
            return new LengthUnit(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public NamedLength queryNamedLengthById(int id) {
        try {
            queryNamedLength.setInt(1, id);
            ResultSet resultSet = queryNamedLength.executeQuery();
            Length length = new Length(resultSet.getDouble(3), queryLengthUnitById(resultSet.getInt(4)));
            return new NamedLength(resultSet.getInt(1), resultSet.getString(2), length);
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

//    public NamedPosition queryPositionById(int id) {
//        try {
//            queryPosition.setInt(1, id);
//            ResultSet resultSet = queryPosition.executeQuery();
//            NamedLength lengthX = new NamedLength(queryNamedLengthById(resultSet.getInt(3)).getName(), queryNamedLengthById(resultSet.getInt(4)).getLength());
//            NamedLength lengthY = new NamedLength(resultSet.getInt(3), resultSet.getInt(4));
//            Position position = new Position(lengthX, lengthY);
//            return new NamedPosition(resultSet.getInt(1), resultSet.getString(2), position);
//        } catch (SQLException e) {
//            System.out.println("Query failed: " + e.getMessage());
//            return null;
//        }
//    }
//
//
//    public List<Element> queryElementByName(String elementName) {
//        try {
//            queryElement.setString(1, elementName);
//            ResultSet resultSet = queryElement.executeQuery();
//            List<Element> elements = new ArrayList<>();
//            while (resultSet.next()) {
//                Element element = new Element();
//                elements.add(element);
//            }
//            return elements;
//        } catch (SQLException e) {
//            System.out.println("Query failed: " + e.getMessage());
//            return null;
//        }
//    }
//
//
//    private int insertPosition(double xValue, String xUnit, double yValue, String yUnit) throws SQLException {
//        .setString(1, name);
//        ResultSet results = queryAlbum.executeQuery();
//        if (results.next()) {
//            return results.getInt(1);
//        } else {
//            // Insert the album
//            insertIntoAlbums.setString(1, name);
//            insertIntoAlbums.setInt(2, artistId);
//            int affectedRows = insertIntoAlbums.executeUpdate();
//            // expect number of affected rows to be 1
//            if (affectedRows != 1) {
//                throw new SQLException("Couldn't insert album!");
//            }
//
//            ResultSet generatedKeys = insertIntoAlbums.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                return generatedKeys.getInt(1);
//            } else {
//                throw new SQLException("Couldn't get _id for album");
//            }
//        }
//    }

}
