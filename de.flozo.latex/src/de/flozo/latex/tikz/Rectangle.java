package de.flozo.latex.tikz;

import de.flozo.common.dto.appearance.*;
import de.flozo.latex.core.LengthExpression;

import java.util.ArrayList;
import java.util.List;

public class Rectangle extends Path {

    // constants
    public static final String KEYWORD = "filldraw";
    public static final String OPERATION = "rectangle";

    // required
    private final Point oppositeCorner;


    private Rectangle(Builder builder) {
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
        this.oppositeCorner = builder.oppositeCorner;
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
        sb.append(" ").append(oppositeCorner.getStatement());
        sb.append(DELIMITER.getString());
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "oppositeCorner=" + oppositeCorner +
                '}';
    }

    public static class Builder {
        // required
        private final Point origin;
        private final Point oppositeCorner;

        // optional
        private String name;
        private final List<String> optionalArguments = new ArrayList<>();
        private BaseColor drawColor;
        private BaseColor fillColor;
        private PredefinedLineWidth predefinedLineWidth;
        private LengthExpression lineWidth;
        private LineCap lineCap;
        private LineJoin lineJoin;
        private DashPattern dashPattern;
        private boolean skipLastTerminator;

        public Builder(Point origin, Point oppositeCorner) {
            this.origin = origin;
            this.oppositeCorner = oppositeCorner;
        }

        public Builder(double xOrigin, double yOrigin, double xOppositeCorner, double yOppositeCorner) {
            this(Point.fromNumbers(xOrigin, yOrigin), Point.fromNumbers(xOppositeCorner, yOppositeCorner));
        }


        public Builder name(String name) {
            this.name = name;
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


        public Rectangle build() {
            return new Rectangle(this);
        }
    }
}
