package de.flozo.db;

public class Datasource {

    // database
    public static final String DB_NAME = "properties.db";
    public static final String CONNECTION_STRING_PREFIX = "jdbc:sqlite:";

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

    public static final String TABLE_WIDTHS = "widths";
    public static final String COLUMN_WIDTH_ID = ID;
    public static final String COLUMN_WIDTH_NAME = NAME;
    public static final String COLUMN_WIDTH_VALUE = VALUE;
    public static final String COLUMN_WIDTH_UNIT_ID = UNIT + ID;

    public static final String TABLE_HEIGHTS = "heights";
    public static final String COLUMN_HEIGHT_ID = ID;
    public static final String COLUMN_HEIGHT_NAME = NAME;
    public static final String COLUMN_HEIGHT_VALUE = VALUE;
    public static final String COLUMN_HEIGHT_UNIT_ID = UNIT + ID;

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
    public static final String COLUMN_POSITION_X_VALUE = "x_value";
    public static final String COLUMN_POSITION_X_UNIT_ID = "x_unit" + ID;
    public static final String COLUMN_POSITION_Y_VALUE = "y_value";
    public static final String COLUMN_POSITION_Y_UNIT_ID = "y_unit" + ID;

    public static final String TABLE_TEXT_FORMATS = "text_formats";
    public static final String COLUMN_TEXT_FORMAT_ID = ID;
    public static final String COLUMN_TEXT_FORMAT_NAME = NAME;
    public static final String COLUMN_TEXT_FORMAT_VALUE = VALUE;

    public static final String TABLE_TEXT_STYLES = "text_styles";
    public static final String COLUMNS_TEXT_STYLE_ID = ID;
    public static final String COLUMNS_TEXT_STYLE_NAME = NAME;
    public static final String COLUMNS_TEXT_STYLE_FONT_SIZE_ID = FONT_SIZE + ID;
    public static final String COLUMNS_TEXT_STYLE_TEXT_FORMAT_ID = "text_format" + ID;
    public static final String COLUMNS_TEXT_STYLE_COLOR_ID = COLOR + ID;
    public static final String COLUMNS_TEXT_STYLE_OPACITY_ID = OPACITY + ID;

    
}
