package de.flozo.latex.tikz;

import de.flozo.common.dto.appearance.*;
import de.flozo.latex.core.LengthExpression;
import de.flozo.latex.core.LengthUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LinePath extends Path {

    // constants
    public static final String KEYWORD = "filldraw";
    public static final String OPERATION = "--";
    public static final LengthUnit DEFAULT_LENGTH_UNIT = LengthUnit.DEFAULT;
    public static final CoordinateMode DEFAULT_COORDINATE_MODE = CoordinateMode.ABSOLUTE;


    // required
    private final Position next;

    // optional
    private final List<Point> coordinateList;
    private final boolean cycle;

    public LinePath(Builder builder) {
        super(builder.origin,
                builder.optionalArguments,
                builder.name,
                builder.drawColor,
                builder.fillColor,
                builder.predefinedLineWidth,
                builder.lineCap,
                builder.lineJoin,
                builder.dashPattern,
                builder.skipLastDelimiter);
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
        sb.append(" ").append(Point.fromPosition(position).getStatement());
        sb.append(" ").append(OPERATION);
        sb.append(" ").append(Point.fromPosition(next).getStatement());
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
        private final Position origin;
        private final Position next;

        // optional
        private String name;
        private final List<Point> coordinateList = new ArrayList<>();
        private boolean cycle = false;
        private final List<String> optionalArguments = new ArrayList<>();
        private Color drawColor;
        private Color fillColor;
        private PredefinedLineWidth predefinedLineWidth;
        private LineWidth lineWidth;
        private LineCap lineCap;
        private LineJoin lineJoin;
        private DashPattern dashPattern;
        private boolean skipLastDelimiter;


        public Builder(Position origin, Position next) {
            this.origin = origin;
            this.next = next;
        }

//        public Builder(double xOrigin, double yOrigin, double xNext, double yNext, CoordinateMode coordinateMode) {
//            this(Point.fromNumbersInMode(xOrigin, yOrigin, CoordinateMode.ABSOLUTE), Point.fromNumbersInMode(xNext, yNext, coordinateMode));
//        }
//
//        public Builder(double xOrigin, double yOrigin, double xNext, double yNext) {
//            this(Point.fromNumbers(xOrigin, yOrigin), Point.fromNumbers(xNext, yNext));
//        }


        public Builder name(String name) {
            this.name = name;
            return this;
        }


        public Builder cycle(boolean cycle) {
            this.cycle = cycle;
            return this;
        }

        public Builder drawColor(Color drawColor) {
            this.drawColor = drawColor;
            addOption(NodeOption.DRAW, drawColor.getSpecifier());
            return this;
        }

        public Builder fillColor(Color fillColor) {
            this.fillColor = fillColor;
            addOption(NodeOption.FILL, fillColor.getSpecifier());
            return this;
        }

        public Builder predefinedLineWidth(PredefinedLineWidth predefinedLineWidth) {
            this.predefinedLineWidth = predefinedLineWidth;
            this.optionalArguments.add(predefinedLineWidth.getString());
            return this;
        }

        public Builder lineWidth(LineWidth lineWidth) {
            this.lineWidth = lineWidth;
            if (!Objects.equals(lineWidth.getUnit().getName(), "default")) {
                addOption(NodeOption.LINE_WIDTH, LengthExpression.fromLineWidth(lineWidth).getFormatted());
            }
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
            if (!Objects.equals(dashPattern.getName(), "default")) {
                this.dashPattern = dashPattern;
                this.optionalArguments.add(dashPattern.getName());
            }
            return this;
        }

        public Builder skipLastDelimiter(boolean skipLastDelimiter) {
            this.skipLastDelimiter = skipLastDelimiter;
            return this;
        }

        private void addOption(NodeOption key, String value) {
            // Skip empty keys or values
            if (key != null && value != null) {
                if (!key.getString().isBlank() && !value.isBlank()) {
                    this.optionalArguments.add(key.getString() + "=" + value);
                }
            }
        }

        public LinePath build() {
            return new LinePath(this);
        }
    }
}
