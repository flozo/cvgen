package de.flozo.latex.tikz;

import de.flozo.common.dto.appearance.BaseColor;
import de.flozo.common.dto.appearance.DashPattern;
import de.flozo.common.dto.appearance.LineCap;
import de.flozo.common.dto.appearance.LineJoin;
import de.flozo.latex.core.LengthExpression;
import de.flozo.latex.core.LengthUnit;

import java.util.ArrayList;
import java.util.List;

public class Line extends Path {

    // constants
    public static final String KEYWORD = "filldraw";
    public static final String OPERATION = "--";
    public static final LengthUnit DEFAULT_LENGTH_UNIT = LengthUnit.DEFAULT;
    public static final CoordinateMode DEFAULT_COORDINATE_MODE = CoordinateMode.ABSOLUTE;


    // required
    private final Point next;

    // optional
    private final List<Point> coordinateList;
    private final boolean cycle;

    public Line(Builder builder) {
        super(builder.origin,
                builder.optionalArguments,
                builder.name,
                builder.drawColor,
                builder.fillColor,
                builder.predefinedLineWidth,
                builder.lineCap,
                builder.lineJoin,
                builder.dashPattern,
                builder.skipLastTerminator);
        this.next = builder.next;
        this.coordinateList = builder.coordinateList;
        this.cycle = builder.cycle;
    }


    @Override
    public String getInline() {
        StringBuilder sb = new StringBuilder(COMMAND_MARKER_CHAR + KEYWORD);
        // Append name in parentheses if name is not null, empty, or only whitespaces
        if (name != null && !name.strip().equals("")) {
            sb.append(String.format(" (%s)", name));
        }
        // Append options if at least one option is present
        if (!optionalArguments.isEmpty()) {
            sb.append(" ").append(inlineOptions());
        }
        // Append required parts
        sb.append(" ").append(position.getStatement());
        sb.append(" ").append(OPERATION);
        sb.append(" ").append(next.getStatement());
        // Append line segments if at least one more is present
        if (!coordinateList.isEmpty()) {
            for (Point point : coordinateList) {
                sb.append(" ").append(OPERATION);
                sb.append(" ").append(point.getStatement());
            }
        }
        // Optional cycle command
        if (cycle) {
            sb.append(" ").append(OPERATION);
            sb.append(" ").append("cycle");
        }
        sb.append(DELIMITER.getString());
        return sb.toString();
    }


    @Override
    public String toString() {
        return "Line{" +
                "next=" + next +
                ", coordinateList=" + coordinateList +
                ", cycle=" + cycle +
                '}';
    }

    public static class Builder {

        // required
        private final Point origin;
        private final Point next;

        // optional
        private String name;
        private final List<Point> coordinateList = new ArrayList<>();
        private boolean cycle = false;
        private final List<String> optionalArguments = new ArrayList<>();
        private BaseColor drawColor;
        private BaseColor fillColor;
        private PredefinedLineWidth predefinedLineWidth;
        private LengthExpression lineWidth;
        private LineCap lineCap;
        private LineJoin lineJoin;
        private DashPattern dashPattern;
        private boolean skipLastTerminator;


        public Builder(Point origin, Point next) {
            this.origin = origin;
            this.next = next;
        }

        public Builder(double xOrigin, double yOrigin, double xNext, double yNext, CoordinateMode coordinateMode) {
            this(Point.fromNumbersInMode(xOrigin, yOrigin, CoordinateMode.ABSOLUTE), Point.fromNumbersInMode(xNext, yNext, coordinateMode));
        }

        public Builder(double xOrigin, double yOrigin, double xNext, double yNext) {
            this(Point.fromNumbers(xOrigin, yOrigin), Point.fromNumbers(xNext, yNext));
        }


        public Builder name(String name) {
            this.name = name;
            return this;
        }

        // Point coordinates with optional CoordinateMode parameter

//        public Builder nextPoint(double x, double y) {
//            return nextPoint(x, y, DEFAULT_COORDINATE_MODE);
//        }
//
//        public Builder nextPoint(double x, double y, CoordinateMode coordinateMode) {
//            return nextPoint(x, y, coordinateMode, DEFAULT_LENGTH_UNIT);
//        }
//
//        public Builder nextPoint(double x, double y, LengthUnit lengthUnit) {
//            return nextPoint(x, y, DEFAULT_COORDINATE_MODE, lengthUnit);
//        }

//        public Builder nextPoint(double x, double y, CoordinateMode coordinateMode, LengthUnit lengthUnit) {
//            this.coordinateList.add(Point.fromLengthsInMode(LengthExpression.fromLength(, lengthUnit), LengthExpression.fromLength(y, lengthUnit), coordinateMode));
//            return this;
//        }

        public Builder cycle(boolean cycle) {
            this.cycle = cycle;
            return this;
        }

        public Builder drawColor(BaseColor drawColor) {
            this.drawColor = drawColor;
            addOption(NodeOption.DRAW, drawColor.getName());
            return this;
        }

        public Builder fillColor(BaseColor fillColor) {
            this.fillColor = fillColor;
            addOption(NodeOption.FILL, fillColor.getName());
            return this;
        }

        public Builder predefinedLineWidth(PredefinedLineWidth predefinedLineWidth) {
            this.predefinedLineWidth = predefinedLineWidth;
            this.optionalArguments.add(predefinedLineWidth.getString());
            return this;
        }

        public Builder lineWidth(LengthExpression lineWidth) {
            this.lineWidth = lineWidth;
            addOption(NodeOption.LINE_WIDTH, lineWidth.getFormatted());
            return this;
        }

        public Builder lineCap(LineCap lineCap) {
            this.lineCap = lineCap;
            addOption(NodeOption.LINE_CAP, lineCap.getValue());
            return this;
        }

        public Builder lineJoin(LineJoin lineJoin) {
            this.lineJoin = lineJoin;
            addOption(NodeOption.LINE_JOIN, lineJoin.getValue());
            return this;
        }

        public Builder dashPattern(DashPattern dashPattern) {
            this.dashPattern = dashPattern;
            this.optionalArguments.add(dashPattern.getName());
            return this;
        }

        public Builder skipLastTerminator(boolean skipLastTerminator) {
            this.skipLastTerminator = skipLastTerminator;
            return this;
        }

        private void addOption(NodeOption key, String value) {
            // Skip empty keys or values
            if (key != null && value != null) {
                if (!key.getString().isEmpty() && !value.isEmpty()) {
                    this.optionalArguments.add(key.getString() + "=" + value);
                }
            }
        }

        public Line build() {
            return new Line(this);
        }
    }
}
