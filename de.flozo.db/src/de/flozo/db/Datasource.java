package de.flozo.db;

import de.flozo.common.*;

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
    public static final String TABLE_ANCHORS = "anchors";
    public static final String COLUMN_ANCHOR_ID = ID;
    public static final String COLUMN_ANCHOR_NAME = NAME;
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

    public static final String TABLE_LENGTHS = "lengths";
    public static final String COLUMN_LENGTH_ID = ID;
    public static final String COLUMN_LENGTH_NAME = NAME;
    public static final String COLUMN_LENGTH_VALUE = VALUE;
    public static final String COLUMN_LENGTH_LENGTH_UNIT_ID = UNIT + ID;

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

    public static final String TABLE_LINE_WIDTHS = "line_widths";
    public static final String COLUMN_LINE_WIDTH_ID = ID;
    public static final String COLUMN_LINE_WIDTH_NAME = NAME;
    public static final String COLUMN_LINE_WIDTH_VALUE = VALUE;
    public static final String COLUMN_LINE_WIDTH_LENGTH_UNIT_ID = "length_unit" + ID;

    public static final String TABLE_PREDEFINED_OPACITIES = "predefined_opacities";
    public static final String COLUMN_PREDEFINED_OPACITY_ID = ID;
    public static final String COLUMN_PREDEFINED_OPACITY_NAME = NAME;

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
    public static final String QUERY_LENGTH = SELECT + STAR + FROM + TABLE_LENGTHS + WHERE + COLUMN_LENGTH_UNIT_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_POSITION = SELECT + STAR + FROM + TABLE_POSITIONS + WHERE + COLUMN_POSITION_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_ANCHOR = SELECT + STAR + FROM + TABLE_ANCHORS + WHERE + COLUMN_ANCHOR_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_FONT_SIZE = SELECT + STAR + FROM + TABLE_FONT_SIZES + WHERE + COLUMN_FONT_SIZE_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_TEXT_FORMAT = SELECT + STAR + FROM + TABLE_TEXT_FORMATS + WHERE + COLUMN_TEXT_FORMAT_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_BASE_COLOR = SELECT + STAR + FROM + TABLE_BASE_COLORS + WHERE + COLUMN_BASE_COLOR_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_OPACITY = SELECT + STAR + FROM + TABLE_PREDEFINED_OPACITIES + WHERE + COLUMN_PREDEFINED_OPACITY_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_TEXT_STYLE = SELECT + STAR + FROM + TABLE_TEXT_STYLES + WHERE + COLUMN_TEXT_STYLE_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_LINE_STYLE = SELECT + STAR + FROM + TABLE_LINE_STYLES + WHERE + COLUMN_LINE_STYLE_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_AREA_STYLE = SELECT + STAR + FROM + TABLE_AREA_STYLES + WHERE + COLUMN_AREA_STYLE_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_LINE_WIDTH = SELECT + STAR + FROM + TABLE_LINE_WIDTHS + WHERE + COLUMN_LINE_WIDTH_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_LINE_CAP = SELECT + STAR + FROM + TABLE_LINE_CAPS + WHERE + COLUMN_LINE_CAP_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_LINE_JOIN = SELECT + STAR + FROM + TABLE_LINE_JOINS + WHERE + COLUMN_LINE_JOIN_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_DASH_PATTERN = SELECT + STAR + FROM + TABLE_DASH_PATTERNS + WHERE + COLUMN_DASH_PATTERN_ID + EQUALS + QUESTION_MARK;

    public static final String QUERY_ELEMENT = SELECT + STAR + FROM + TABLE_ELEMENTS + WHERE + COLUMN_ELEMENT_ID + EQUALS + QUESTION_MARK;

    // insert
    public static final String INSERT_POSITION = INSERT_INTO + TABLE_POSITIONS +
            OPENING_PARENTHESIS + COLUMN_POSITION_X_VALUE + COMMA + COLUMN_POSITION_X_UNIT_ID + COMMA + COLUMN_POSITION_Y_VALUE + COMMA + COLUMN_POSITION_Y_UNIT_ID + CLOSING_PARENTHESIS +
            VALUES + OPENING_PARENTHESIS + QUESTION_MARK + COMMA + QUESTION_MARK + COMMA + QUESTION_MARK + COMMA + QUESTION_MARK + CLOSING_PARENTHESIS;

    private final String connectionString = CONNECTION_STRING_PREFIX + RESOURCE_FOLDER + DB_NAME;

    private Connection connection;

    private PreparedStatement queryLengthUnit;
    private PreparedStatement queryLength;
    private PreparedStatement queryPosition;
    private PreparedStatement queryAnchor;
    private PreparedStatement queryFontSize;
    private PreparedStatement queryTextFormat;
    private PreparedStatement queryBaseColor;
    private PreparedStatement queryOpacity;
    private PreparedStatement queryLineWidth;
    private PreparedStatement queryLineCap;
    private PreparedStatement queryLineJoin;
    private PreparedStatement queryDashPattern;

    private PreparedStatement queryTextStyle;
    private PreparedStatement queryLineStyle;
    private PreparedStatement queryAreaStyle;

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
            queryLength = connection.prepareStatement(QUERY_LENGTH);
            queryPosition = connection.prepareStatement(QUERY_POSITION);
            queryAnchor = connection.prepareStatement(QUERY_ANCHOR);
            queryFontSize = connection.prepareStatement(QUERY_FONT_SIZE);
            queryTextFormat = connection.prepareStatement(QUERY_TEXT_FORMAT);
            queryBaseColor = connection.prepareStatement(QUERY_BASE_COLOR);
            queryOpacity = connection.prepareStatement(QUERY_OPACITY);
            queryElement = connection.prepareStatement(QUERY_ELEMENT);
            queryTextStyle = connection.prepareStatement(QUERY_TEXT_STYLE);
            queryLineStyle = connection.prepareStatement(QUERY_LINE_STYLE);
            queryAreaStyle = connection.prepareStatement(QUERY_AREA_STYLE);
            queryLineWidth = connection.prepareStatement(QUERY_LINE_WIDTH);
            queryLineCap = connection.prepareStatement(QUERY_LINE_CAP);
            queryLineJoin = connection.prepareStatement(QUERY_LINE_JOIN);
            queryDashPattern = connection.prepareStatement(QUERY_DASH_PATTERN);

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
            if (queryLength != null) {
                queryLength.close();
            }
            if (queryPosition != null) {
                queryPosition.close();
            }
            if (queryAnchor != null) {
                queryAnchor.close();
            }
            if (queryFontSize != null) {
                queryFontSize.close();
            }
            if (queryTextFormat != null) {
                queryTextFormat.close();
            }
            if (queryBaseColor != null) {
                queryBaseColor.close();
            }
            if (queryOpacity != null) {
                queryOpacity.close();
            }
            if (queryElement != null) {
                queryElement.close();
            }
            if (queryTextStyle != null) {
                queryTextStyle.close();
            }
            if (queryLineStyle != null) {
                queryLineStyle.close();
            }
            if (queryAreaStyle != null) {
                queryAreaStyle.close();
            }
            if (queryLineWidth != null) {
                queryLineWidth.close();
            }
            if (queryLineCap != null) {
                queryLineCap.close();
            }
            if (queryLineJoin != null) {
                queryLineJoin.close();
            }
            if (queryDashPattern != null) {
                queryDashPattern.close();
            }

        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    private void showMessage(PreparedStatement preparedStatement, String message) {
        System.out.println("Query \"" + preparedStatement + "\" failed: " + message);
    }

    public LengthUnit queryLengthUnitById(int id) {
        try {
            queryLengthUnit.setInt(1, id);
            ResultSet resultSet = queryLengthUnit.executeQuery();
            return new LengthUnit(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
        } catch (SQLException e) {
            showMessage(queryLengthUnit, e.getMessage());
            return null;
        }
    }

    public Length queryLengthById(int id) {
        try {
            queryLength.setInt(1, id);
            ResultSet resultSet = queryLength.executeQuery();
            return new Length(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), queryLengthUnitById(resultSet.getInt(4)));
        } catch (SQLException e) {
            showMessage(queryLength, e.getMessage());
            return null;
        }
    }

    public Position queryPositionById(int id) {
        try {
            queryPosition.setInt(1, id);
            ResultSet resultSet = queryPosition.executeQuery();
            return new Position(resultSet.getInt(1), resultSet.getString(2), queryLengthById(resultSet.getInt(3)), queryLengthById(resultSet.getInt(4)));
        } catch (SQLException e) {
            showMessage(queryPosition, e.getMessage());
            return null;
        }
    }

    public Anchor queryAnchorById(int id) {
        try {
            queryAnchor.setInt(1, id);
            ResultSet resultSet = queryAnchor.executeQuery();
            return new Anchor(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
        } catch (SQLException e) {
            showMessage(queryAnchor, e.getMessage());
            return null;
        }
    }

    public FontSize queryFontSizeById(int id) {
        try {
            queryFontSize.setInt(1, id);
            ResultSet resultSet = queryFontSize.executeQuery();
            return new FontSize(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
        } catch (SQLException e) {
            showMessage(queryFontSize, e.getMessage());
            return null;
        }
    }

    public TextFormat queryTextFormatById(int id) {
        try {
            queryTextFormat.setInt(1, id);
            ResultSet resultSet = queryTextFormat.executeQuery();
            return new TextFormat(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
        } catch (SQLException e) {
            showMessage(queryTextFormat, e.getMessage());
            return null;
        }
    }

    public BaseColor queryBaseColorById(int id) {
        try {
            queryBaseColor.setInt(1, id);
            ResultSet resultSet = queryBaseColor.executeQuery();
            return new BaseColor(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException e) {
            showMessage(queryBaseColor, e.getMessage());
            return null;
        }
    }

    public PredefinedOpacity queryOpacityById(int id) {
        try {
            queryOpacity.setInt(1, id);
            ResultSet resultSet = queryOpacity.executeQuery();
            return new PredefinedOpacity(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException e) {
            showMessage(queryOpacity, e.getMessage());
            return null;
        }
    }

    public LineWidth queryLineWidthById(int id) {
        try {
            queryLineWidth.setInt(1, id);
            ResultSet resultSet = queryLineWidth.executeQuery();
            return new LineWidth(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), queryLengthUnitById(resultSet.getInt(4)));
        } catch (SQLException e) {
            showMessage(queryLineWidth, e.getMessage());
            return null;
        }
    }

    public LineCap queryLineCapById(int id) {
        try {
            queryLineCap.setInt(1, id);
            ResultSet resultSet = queryLineCap.executeQuery();
            return new LineCap(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException e) {
            showMessage(queryLineCap, e.getMessage());
            return null;
        }
    }

    public LineJoin queryLineJoinById(int id) {
        try {
            queryLineJoin.setInt(1, id);
            ResultSet resultSet = queryLineJoin.executeQuery();
            return new LineJoin(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException e) {
            showMessage(queryLineJoin, e.getMessage());
            return null;
        }
    }

    public DashPattern queryDashPatternById(int id) {
        try {
            queryDashPattern.setInt(1, id);
            ResultSet resultSet = queryDashPattern.executeQuery();
            return new DashPattern(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException e) {
            showMessage(queryDashPattern, e.getMessage());
            return null;
        }
    }


    public TextStyle queryTextStyleById(int id) {
        try {
            queryTextStyle.setInt(1, id);
            ResultSet resultSet = queryTextStyle.executeQuery();
            return new TextStyle(resultSet.getInt(1), resultSet.getString(2), queryFontSizeById(resultSet.getInt(3)),
                    queryTextFormatById(resultSet.getInt(4)), queryBaseColorById(resultSet.getInt(5)), queryOpacityById(resultSet.getInt(6)));
        } catch (SQLException e) {
            showMessage(queryTextStyle, e.getMessage());
            return null;
        }
    }

    public LineStyle queryLineStyleById(int id) {
        try {
            queryLineStyle.setInt(1, id);
            ResultSet resultSet = queryLineStyle.executeQuery();
            return new LineStyle(resultSet.getInt(1), resultSet.getString(2), queryLineWidthById(resultSet.getInt(3)),
                    queryLineCapById(resultSet.getInt(4)), queryLineJoinById(resultSet.getInt(5)), queryDashPatternById(resultSet.getInt(6)),
                    queryBaseColorById(resultSet.getInt(7)), queryOpacityById(resultSet.getInt(8)));
        } catch (SQLException e) {
            showMessage(queryLineStyle, e.getMessage());
            return null;
        }
    }

    public AreaStyle queryAreaStyleById(int id) {
        try {
            queryAreaStyle.setInt(1, id);
            ResultSet resultSet = queryAreaStyle.executeQuery();
            return new AreaStyle(resultSet.getInt(1), resultSet.getString(2), queryBaseColorById(resultSet.getInt(3)), queryOpacityById(resultSet.getInt(4)));
        } catch (SQLException e) {
            showMessage(queryAreaStyle, e.getMessage());
            return null;
        }
    }


    public Element queryElementById(int id) {
        try {
            queryElement.setInt(1, id);
            ResultSet resultSet = queryElement.executeQuery();
            return new Element(resultSet.getInt(1), resultSet.getString(2), queryPositionById(resultSet.getInt(3)),
                    queryLengthById(resultSet.getInt(4)), queryLengthById(resultSet.getInt(5)), queryAnchorById(resultSet.getInt(6)),
                    queryTextStyleById(resultSet.getInt(7)), queryLineStyleById(resultSet.getInt(8)), queryAreaStyleById(resultSet.getInt(9)));
        } catch (SQLException e) {
            showMessage(queryElement, e.getMessage());
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
