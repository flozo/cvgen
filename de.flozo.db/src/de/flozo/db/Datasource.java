package de.flozo.db;

import de.flozo.common.appearance.*;
import de.flozo.common.content.Address;
import de.flozo.common.content.Enclosure;

import java.sql.*;

public class Datasource {

    // database
    public static final String DB_NAME = "properties.db";
    public static final String CONNECTION_STRING_PREFIX = "jdbc:sqlite:";
    public static final String RESOURCE_FOLDER = "../../../Data/CVgen_test/";
//    public static final String RESOURCE_FOLDER = "src/resource/";

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
    // appearance
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

    public static final String TABLE_LINES = "lines";
    public static final String COLUMN_LINES_ID = ID;
    public static final String COLUMN_LINES_NAME = NAME;
    public static final String COLUMN_LINES_POSITION_ID = POSITION + ID;
    public static final String COLUMN_LINES_LENGTH_ID = "length" + ID;
    public static final String COLUMN_LINES_LINE_STYLE_ID = "line_style" + ID;


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

    // content
    public static final String TABLE_ADDRESSES = "addresses";
    public static final String COLUMN_ADDRESSES_ID = ID;
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

    public static final String TABLE_ENCLOSURES = "enclosures";
    public static final String COLUMN_ENCLOSURES_ID = ID;
    public static final String COLUMN_ENCLOSURES_NAME = NAME;
    public static final String COLUMN_ENCLOSURES_FILE = "file";


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
    public static final String QUERY_LENGTH_UNIT = SELECT + STAR + FROM + TABLE_LENGTH_UNITS + WHERE + COLUMN_LENGTH_UNIT_ID + EQUALS + QUESTION_MARK;
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

    public static final String QUERY_LINE_BY_ID = SELECT + STAR + FROM + TABLE_LINES + WHERE + COLUMN_LINE_STYLE_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_ELEMENT_BY_ID = SELECT + STAR + FROM + TABLE_ELEMENTS + WHERE + COLUMN_ELEMENT_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_ELEMENT_BY_NAME = SELECT + STAR + FROM + TABLE_ELEMENTS + WHERE + COLUMN_ELEMENT_NAME + EQUALS + QUESTION_MARK;


    public static final String QUERY_ADDRESS_BY_ID = SELECT + STAR + FROM + TABLE_ADDRESSES + WHERE + COLUMN_ADDRESSES_ID + EQUALS + QUESTION_MARK;
    public static final String QUERY_ENCLOSURE_BY_ID = SELECT + STAR + FROM + TABLE_ENCLOSURES + WHERE + COLUMN_ENCLOSURES_ID + EQUALS + QUESTION_MARK;

    public static final String QUERY_ADDRESS_BY_LABEL = SELECT + STAR + FROM + TABLE_ADDRESSES + WHERE + COLUMN_ADDRESSES_LABEL + EQUALS + QUESTION_MARK;
    public static final String QUERY_ENCLOSURE_BY_NAME = SELECT + STAR + FROM + TABLE_ENCLOSURES + WHERE + COLUMN_ENCLOSURES_NAME + EQUALS + QUESTION_MARK;


    // insert
    public static final String INSERT_POSITION = INSERT_INTO + TABLE_POSITIONS +
            OPENING_PARENTHESIS + COLUMN_POSITION_X_VALUE + COMMA + COLUMN_POSITION_X_UNIT_ID + COMMA + COLUMN_POSITION_Y_VALUE + COMMA + COLUMN_POSITION_Y_UNIT_ID + CLOSING_PARENTHESIS +
            VALUES + OPENING_PARENTHESIS + QUESTION_MARK + COMMA + QUESTION_MARK + COMMA + QUESTION_MARK + COMMA + QUESTION_MARK + CLOSING_PARENTHESIS;

    private final String connectionString = CONNECTION_STRING_PREFIX + RESOURCE_FOLDER + DB_NAME;

    private Connection connection;

    private PreparedStatement queryLengthUnitById;
    private PreparedStatement queryLengthById;
    private PreparedStatement queryPositionById;
    private PreparedStatement queryAnchorById;
    private PreparedStatement queryFontSizeById;
    private PreparedStatement queryTextFormatById;
    private PreparedStatement queryBaseColorById;
    private PreparedStatement queryOpacityById;
    private PreparedStatement queryLineWidthById;
    private PreparedStatement queryLineCapById;
    private PreparedStatement queryLineJoinById;
    private PreparedStatement queryDashPatternById;

    private PreparedStatement queryTextStyleById;
    private PreparedStatement queryLineStyleById;
    private PreparedStatement queryAreaStyleById;

    private PreparedStatement queryLineById;
    private PreparedStatement queryElementById;
    private PreparedStatement queryElementByName;
    private PreparedStatement queryAddressById;
    private PreparedStatement queryAddressByLabel;
    private PreparedStatement queryEnclosureById;
    private PreparedStatement queryEnclosureByName;

    private PreparedStatement insertIntoPositions;

    private static final Datasource INSTANCE = new Datasource();

    private Datasource() {
    }

    public static Datasource getInstance() {
        return INSTANCE;
    }

    public boolean open() {
        try {
            connection = DriverManager.getConnection(connectionString);
            queryLengthUnitById = connection.prepareStatement(QUERY_LENGTH_UNIT);
            queryLengthById = connection.prepareStatement(QUERY_LENGTH);
            queryPositionById = connection.prepareStatement(QUERY_POSITION);
            queryAnchorById = connection.prepareStatement(QUERY_ANCHOR);
            queryFontSizeById = connection.prepareStatement(QUERY_FONT_SIZE);
            queryTextFormatById = connection.prepareStatement(QUERY_TEXT_FORMAT);
            queryBaseColorById = connection.prepareStatement(QUERY_BASE_COLOR);
            queryOpacityById = connection.prepareStatement(QUERY_OPACITY);
            queryTextStyleById = connection.prepareStatement(QUERY_TEXT_STYLE);
            queryLineStyleById = connection.prepareStatement(QUERY_LINE_STYLE);
            queryAreaStyleById = connection.prepareStatement(QUERY_AREA_STYLE);
            queryLineWidthById = connection.prepareStatement(QUERY_LINE_WIDTH);
            queryLineCapById = connection.prepareStatement(QUERY_LINE_CAP);
            queryLineJoinById = connection.prepareStatement(QUERY_LINE_JOIN);
            queryDashPatternById = connection.prepareStatement(QUERY_DASH_PATTERN);
            queryLineById = connection.prepareStatement(QUERY_LINE_BY_ID);
            queryElementById = connection.prepareStatement(QUERY_ELEMENT_BY_ID);
            queryElementByName = connection.prepareStatement(QUERY_ELEMENT_BY_NAME);
            queryAddressById = connection.prepareStatement(QUERY_ADDRESS_BY_ID);
            queryAddressByLabel = connection.prepareStatement(QUERY_ADDRESS_BY_LABEL);
            queryEnclosureById = connection.prepareStatement(QUERY_ENCLOSURE_BY_ID);
            queryEnclosureByName = connection.prepareStatement(QUERY_ENCLOSURE_BY_NAME);

//            insertIntoPositions = connection.prepareStatement(INSERT_POSITION, Statement.RETURN_GENERATED_KEYS);

            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (queryLengthUnitById != null) {
                queryLengthUnitById.close();
            }
            if (queryLengthById != null) {
                queryLengthById.close();
            }
            if (queryPositionById != null) {
                queryPositionById.close();
            }
            if (queryAnchorById != null) {
                queryAnchorById.close();
            }
            if (queryFontSizeById != null) {
                queryFontSizeById.close();
            }
            if (queryTextFormatById != null) {
                queryTextFormatById.close();
            }
            if (queryBaseColorById != null) {
                queryBaseColorById.close();
            }
            if (queryOpacityById != null) {
                queryOpacityById.close();
            }
            if (queryElementById != null) {
                queryElementById.close();
            }
            if (queryElementByName != null) {
                queryElementByName.close();
            }
            if (queryTextStyleById != null) {
                queryTextStyleById.close();
            }
            if (queryLineStyleById != null) {
                queryLineStyleById.close();
            }
            if (queryAreaStyleById != null) {
                queryAreaStyleById.close();
            }
            if (queryLineWidthById != null) {
                queryLineWidthById.close();
            }
            if (queryLineCapById != null) {
                queryLineCapById.close();
            }
            if (queryLineJoinById != null) {
                queryLineJoinById.close();
            }
            if (queryDashPatternById != null) {
                queryDashPatternById.close();
            }
            if (queryAddressById != null) {
                queryAddressById.close();
            }
            if (queryAddressByLabel != null) {
                queryAddressByLabel.close();
            }
            if (queryEnclosureById != null) {
                queryEnclosureById.close();
            }
            if (queryEnclosureByName != null) {
                queryEnclosureByName.close();
            }
            if (queryLineById != null) {
                queryLineById.close();
            }

        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    private void showMessage(PreparedStatement preparedStatement, String message) {
        System.out.println("Query \"" + preparedStatement + "\" failed: " + message);
    }

    public LengthUnit lengthUnitById(int id) {
        try {
            queryLengthUnitById.setInt(1, id);
            ResultSet resultSet = queryLengthUnitById.executeQuery();
            return new LengthUnit(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
        } catch (SQLException e) {
            showMessage(queryLengthUnitById, e.getMessage());
            return null;
        }
    }

    public Length lengthById(int id) {
        try {
            queryLengthById.setInt(1, id);
            ResultSet resultSet = queryLengthById.executeQuery();
            return new Length(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), lengthUnitById(resultSet.getInt(4)));
        } catch (SQLException e) {
            showMessage(queryLengthById, e.getMessage());
            return null;
        }
    }

    public Position positionById(int id) {
        try {
            queryPositionById.setInt(1, id);
            ResultSet resultSet = queryPositionById.executeQuery();
            return new Position(resultSet.getInt(1), resultSet.getString(2), lengthById(resultSet.getInt(3)), lengthById(resultSet.getInt(4)));
        } catch (SQLException e) {
            showMessage(queryPositionById, e.getMessage());
            return null;
        }
    }

    public Anchor anchorById(int id) {
        try {
            queryAnchorById.setInt(1, id);
            ResultSet resultSet = queryAnchorById.executeQuery();
            return new Anchor(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
        } catch (SQLException e) {
            showMessage(queryAnchorById, e.getMessage());
            return null;
        }
    }

    public FontSize fontSizeById(int id) {
        try {
            queryFontSizeById.setInt(1, id);
            ResultSet resultSet = queryFontSizeById.executeQuery();
            return new FontSize(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
        } catch (SQLException e) {
            showMessage(queryFontSizeById, e.getMessage());
            return null;
        }
    }

    public TextFormat textFormatById(int id) {
        try {
            queryTextFormatById.setInt(1, id);
            ResultSet resultSet = queryTextFormatById.executeQuery();
            return new TextFormat(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
        } catch (SQLException e) {
            showMessage(queryTextFormatById, e.getMessage());
            return null;
        }
    }

    public BaseColor baseColorById(int id) {
        try {
            queryBaseColorById.setInt(1, id);
            ResultSet resultSet = queryBaseColorById.executeQuery();
            return new BaseColor(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException e) {
            showMessage(queryBaseColorById, e.getMessage());
            return null;
        }
    }

    public PredefinedOpacity opacityById(int id) {
        try {
            queryOpacityById.setInt(1, id);
            ResultSet resultSet = queryOpacityById.executeQuery();
            return new PredefinedOpacity(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException e) {
            showMessage(queryOpacityById, e.getMessage());
            return null;
        }
    }

    public LineWidth lineWidthById(int id) {
        try {
            queryLineWidthById.setInt(1, id);
            ResultSet resultSet = queryLineWidthById.executeQuery();
            return new LineWidth(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3), lengthUnitById(resultSet.getInt(4)));
        } catch (SQLException e) {
            showMessage(queryLineWidthById, e.getMessage());
            return null;
        }
    }

    public LineCap lineCapById(int id) {
        try {
            queryLineCapById.setInt(1, id);
            ResultSet resultSet = queryLineCapById.executeQuery();
            return new LineCap(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException e) {
            showMessage(queryLineCapById, e.getMessage());
            return null;
        }
    }

    public LineJoin lineJoinById(int id) {
        try {
            queryLineJoinById.setInt(1, id);
            ResultSet resultSet = queryLineJoinById.executeQuery();
            return new LineJoin(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException e) {
            showMessage(queryLineJoinById, e.getMessage());
            return null;
        }
    }

    public DashPattern dashPatternById(int id) {
        try {
            queryDashPatternById.setInt(1, id);
            ResultSet resultSet = queryDashPatternById.executeQuery();
            return new DashPattern(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException e) {
            showMessage(queryDashPatternById, e.getMessage());
            return null;
        }
    }


    public TextStyle textStyleById(int id) {
        try {
            queryTextStyleById.setInt(1, id);
            ResultSet resultSet = queryTextStyleById.executeQuery();
            return new TextStyle(resultSet.getInt(1), resultSet.getString(2), fontSizeById(resultSet.getInt(3)),
                    textFormatById(resultSet.getInt(4)), baseColorById(resultSet.getInt(5)), opacityById(resultSet.getInt(6)));
        } catch (SQLException e) {
            showMessage(queryTextStyleById, e.getMessage());
            return null;
        }
    }

    public LineStyle lineStyleById(int id) {
        try {
            queryLineStyleById.setInt(1, id);
            ResultSet resultSet = queryLineStyleById.executeQuery();
            return new LineStyle(resultSet.getInt(1), resultSet.getString(2), lineWidthById(resultSet.getInt(3)),
                    lineCapById(resultSet.getInt(4)), lineJoinById(resultSet.getInt(5)), dashPatternById(resultSet.getInt(6)),
                    baseColorById(resultSet.getInt(7)), opacityById(resultSet.getInt(8)));
        } catch (SQLException e) {
            showMessage(queryLineStyleById, e.getMessage());
            return null;
        }
    }

    public AreaStyle areaStyleById(int id) {
        try {
            queryAreaStyleById.setInt(1, id);
            ResultSet resultSet = queryAreaStyleById.executeQuery();
            return new AreaStyle(resultSet.getInt(1), resultSet.getString(2), baseColorById(resultSet.getInt(3)), opacityById(resultSet.getInt(4)));
        } catch (SQLException e) {
            showMessage(queryAreaStyleById, e.getMessage());
            return null;
        }
    }


    public Element elementById(int id) {
        try {
            queryElementById.setInt(1, id);
            ResultSet resultSet = queryElementById.executeQuery();
            return new Element(resultSet.getInt(1), resultSet.getString(2), positionById(resultSet.getInt(3)),
                    lengthById(resultSet.getInt(4)), lengthById(resultSet.getInt(5)), anchorById(resultSet.getInt(6)),
                    textStyleById(resultSet.getInt(7)), lineStyleById(resultSet.getInt(8)), areaStyleById(resultSet.getInt(9)));
        } catch (SQLException e) {
            showMessage(queryElementById, e.getMessage());
            return null;
        }
    }

    public Element elementByName(String elementName) {
        try {
            queryElementByName.setString(1, elementName);
            ResultSet resultSet = queryElementByName.executeQuery();
            return new Element(resultSet.getInt(1), resultSet.getString(2), positionById(resultSet.getInt(3)),
                    lengthById(resultSet.getInt(4)), lengthById(resultSet.getInt(5)), anchorById(resultSet.getInt(6)),
                    textStyleById(resultSet.getInt(7)), lineStyleById(resultSet.getInt(8)), areaStyleById(resultSet.getInt(9)));
        } catch (SQLException e) {
            showMessage(queryElementByName, e.getMessage());
            return null;
        }
    }

    public Address addressById(int id) {
        try {
            queryAddressById.setInt(1, id);
            ResultSet resultSet = queryAddressById.executeQuery();
            return new Address(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
                    resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11), resultSet.getString(12),
                    resultSet.getString(13), resultSet.getString(14), resultSet.getString(15));
        } catch (SQLException e) {
            showMessage(queryAddressById, e.getMessage());
            return null;
        }
    }

    public Address addressByLabel(String label) {
        try {
            queryAddressByLabel.setString(1, label);
            ResultSet resultSet = queryAddressByLabel.executeQuery();
            return new Address(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
                    resultSet.getString(7), resultSet.getString(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11), resultSet.getString(12),
                    resultSet.getString(13), resultSet.getString(14), resultSet.getString(15));
        } catch (SQLException e) {
            showMessage(queryAddressByLabel, e.getMessage());
            return null;
        }
    }

    public Enclosure enclosureById(int id) {
        try {
            queryEnclosureById.setInt(1, id);
            ResultSet resultSet = queryEnclosureById.executeQuery();
            return new Enclosure(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
        } catch (SQLException e) {
            showMessage(queryEnclosureById, e.getMessage());
            return null;
        }
    }

    public Enclosure enclosureByName(String name) {
        try {
            queryEnclosureByName.setString(1, name);
            ResultSet resultSet = queryEnclosureByName.executeQuery();
            return new Enclosure(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
        } catch (SQLException e) {
            showMessage(queryEnclosureByName, e.getMessage());
            return null;
        }
    }

    public Line lineById(int id) {
        try {
            queryLineById.setInt(1, id);
            ResultSet resultSet = queryLineById.executeQuery();
            return new Line(resultSet.getInt(1), resultSet.getString(2), positionById(resultSet.getInt(3)), lengthById(resultSet.getInt(4)), lineStyleById(resultSet.getInt(5)));
        } catch (SQLException e) {
            showMessage(queryLineById, e.getMessage());
            return null;
        }
    }



//    private Predicate<Integer> checkIntRangeOfId() {
//        return idNumber -> idNumber.equals(0);
//    }



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
