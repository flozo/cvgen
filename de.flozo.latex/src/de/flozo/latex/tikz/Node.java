package de.flozo.latex.tikz;

import de.flozo.common.dto.appearance.*;
import de.flozo.common.dto.appearance.Alignment;
import de.flozo.latex.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node extends Path {

    public static final String KEYWORD = "node";
    public static final Bracket BODY_BRACKETS = Bracket.CURLY_BRACES;
    public static final boolean DEFAULT_SKIP_LAST_DELIMITER = true;
    public static final boolean DEFAULT_IS_MATRIX = false;

    // required
    private final List<String> body;

    // optional
    private final String name;
    private final Delimiter bodyDelimiter;

    private Node(Builder builder) {
        super(builder.position,
                builder.optionalArguments,
                builder.name,
                builder.drawColor,
                builder.fillColor,
                builder.predefinedLineWidth,
                builder.lineCap,
                builder.lineJoin,
                builder.dashPattern,
                builder.skipLastDelimiter);
        this.body = builder.body;
        this.name = builder.name;
        this.bodyDelimiter = builder.bodyDelimiter;
    }

    @Override
    public String getInline() {
        StringBuilder sb = new StringBuilder(assembleOpeningTag());
        // Append options if at least one option is present
        if (!optionalArguments.isEmpty()) {
            sb.append(" ").append(inlineOptions());
        }
        // Append remaining required parts
        sb.append(" ");
        sb.append(BODY_BRACKETS.getLeftBracket());
        sb.append(String.join(bodyDelimiter.getString(), body));
        sb.append(BODY_BRACKETS.getRightBracket());
        sb.append(DELIMITER.getString());
        return sb.toString();
    }


    public List<String> getBlock() {
        List<String> lines = new ArrayList<>();
        lines.add(assembleOpeningTag());
        // Append block options if at least one option is present
        if (!optionalArguments.isEmpty()) {
            lines.addAll(blockOptions());
        }
        // Append remaining required parts
        lines.addAll(buildBody().getBlock());
        lines.add(DELIMITER.getString());
        return lines;
    }

    private ExpressionList buildBody() {
        return new FormattedExpressionList.Builder(body)
                .brackets(Bracket.CURLY_BRACES)
                .delimiter(bodyDelimiter)
                .skipLastDelimiter(skipLastDelimiter)
                .indentBlock(true)
                .build();
    }

    private String assembleOpeningTag() {
        StringBuilder sb = new StringBuilder(COMMAND_MARKER_CHAR + KEYWORD);
        // Append name in parentheses if name is not null, empty, or only whitespaces
        if (name != null && !name.strip().equals("")) {
            sb.append(String.format(" (%s)", name));
        }
        // Append optional positioning statement
        if (position != null) {
            sb.append(" at ");
            sb.append(position.getStatement());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Node{" +
                "body=" + body +
                ", name='" + name + '\'' +
                ", bodyDelimiter=" + bodyDelimiter +
                '}';
    }

    public static class Builder {

        // required
        private final List<String> body;

        // optional
        private String name;
        private Point position;
        private final List<String> optionalArguments = new ArrayList<>();
        private Delimiter bodyDelimiter = Delimiter.NONE;
        private boolean skipLastDelimiter = DEFAULT_SKIP_LAST_DELIMITER;
        private Anchor anchor;
        private FontSize fontSize = new FontSize(1, "default", "");
        private Color textColor;
        private Color drawColor;
        private Color fillColor;
        private PredefinedLineWidth predefinedLineWidth;
        private LineCap lineCap;
        private LineJoin lineJoin;
        private DashPattern dashPattern;
        private LengthExpression xShift;
        private LengthExpression yShift;
        private LengthExpression textWidth;
        private LengthExpression textHeight;
        private LengthExpression textDepth;
        private LengthExpression minimumWidth;
        private LengthExpression minimumHeight;
        private Alignment alignment;
        private LengthExpression innerXSep;
        private LengthExpression innerYSep;
        private LengthExpression outerXSep;
        private LengthExpression outerYSep;
        private PredefinedOpacity textOpacity;
        private PredefinedOpacity lineOpacity;
        private PredefinedOpacity areaOpacity;
        private boolean isMatrix = DEFAULT_IS_MATRIX;

        public Builder(String... body) {
            this(new ArrayList<>(List.of(body)));
        }

        public Builder(List<String> body) {
            this.body = body;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder position(Point position) {
            this.position = position;
            return this;
        }

        public Builder bodyDelimiter(Delimiter bodyDelimiter) {
            this.bodyDelimiter = bodyDelimiter;
            return this;
        }

        public Builder skipLastDelimiter(boolean skipLastDelimiter) {
            this.skipLastDelimiter = skipLastDelimiter;
            return this;
        }

        public Builder anchor(Anchor anchor) {
            this.anchor = anchor;
            addOption(NodeOption.ANCHOR, anchor.getValue());
            return this;
        }

        public Builder fontSize(FontSize fontSize) {
            this.fontSize = fontSize;
            if (!fontSize.getValue().isBlank()) {
                addOption(NodeOption.FONT, new GenericCommand.Builder(fontSize.getValue()).build().getInline());
            }
            return this;
        }

        public Builder drawColor(Color drawColor) {
            this.drawColor = drawColor;
            if (!Objects.equals(textColor.getSpecifier(), "black")) {
                addOption(NodeOption.DRAW, drawColor.getSpecifier());
            }
            return this;
        }

        public Builder textColor(Color textColor) {
            this.textColor = textColor;
            if (!Objects.equals(textColor.getSpecifier(), "black")) {
                addOption(NodeOption.TEXT, textColor.getSpecifier());
            }
            return this;
        }

        public Builder fillColor(Color fillColor) {
            this.fillColor = fillColor;
            addOption(NodeOption.FILL, fillColor.getSpecifier());
            return this;
        }

        public Builder textOpacity(PredefinedOpacity textOpacity) {
            this.textOpacity = textOpacity;
            if (!Objects.equals(textOpacity.getValue(), "opaque")) {
                addOption(NodeOption.TEXT_OPACITY, textOpacity.getValue());
            }
            return this;
        }

        public Builder lineOpacity(PredefinedOpacity lineOpacity) {
            this.lineOpacity = lineOpacity;
            if (!Objects.equals(lineOpacity.getValue(), "opaque")) {
                addOption(NodeOption.DRAW_OPACITY, lineOpacity.getValue());
            }
            return this;
        }

        public Builder areaOpacity(PredefinedOpacity areaOpacity) {
            this.areaOpacity = areaOpacity;
            if (!Objects.equals(areaOpacity.getValue(), "opaque")) {
                addOption(NodeOption.FILL_OPACITY, areaOpacity.getValue());
            }
            return this;
        }

        public Builder predefinedLineWidth(PredefinedLineWidth predefinedLineWidth) {
            this.predefinedLineWidth = predefinedLineWidth;
            this.optionalArguments.add(predefinedLineWidth.getString());
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

        public Builder dashPatternStyle(DashPattern dashPattern) {
            if (!Objects.equals(dashPattern.getName(), "default")) {
                this.dashPattern = dashPattern;
                this.optionalArguments.add(dashPattern.getName());
            }
            return this;
        }

        public Builder xShift(LengthExpression xShift) {
            this.xShift = xShift;
            addOption(NodeOption.X_SHIFT, xShift.getFormatted());
            return this;
        }

        public Builder yShift(LengthExpression yShift) {
            this.yShift = yShift;
            addOption(NodeOption.Y_SHIFT, yShift.getFormatted());
            return this;
        }

        public Builder textWidth(LengthExpression textWidth) {
            this.textWidth = textWidth;
            if (textWidth.getNumericalValue() != 0) {
                addOption(NodeOption.TEXT_WIDTH, textWidth.getFormatted());
            }
            return this;
        }

        public Builder textHeight(LengthExpression textHeight) {
            this.textHeight = textHeight;
            if (textHeight.getNumericalValue() != 0) {
                addOption(NodeOption.TEXT_HEIGHT, textHeight.getFormatted());
            }
            return this;
        }

        public Builder textDepth(LengthExpression textDepth) {
            this.textDepth = textDepth;
            if (textDepth.getNumericalValue() != 0) {
                addOption(NodeOption.TEXT_DEPTH, textDepth.getFormatted());
            }
            return this;
        }


        public Builder minimumWidth(LengthExpression minimumWidth) {
            this.minimumWidth = minimumWidth;
            if (minimumWidth.getNumericalValue() != 0) {
                addOption(NodeOption.MINIMUM_WIDTH, minimumWidth.getFormatted());
            }
            return this;
        }

        public Builder minimumHeight(LengthExpression minimumHeight) {
            this.minimumHeight = minimumHeight;
            if (minimumHeight.getNumericalValue() != 0) {
                addOption(NodeOption.MINIMUM_HEIGHT, minimumHeight.getFormatted());
            }
            return this;
        }

        public Builder alignment(Alignment alignment) {
            this.alignment = alignment;
            addOption(NodeOption.ALIGN, alignment.getValue());
            return this;
        }

        public Builder innerXSep(LengthExpression innerXSep) {
            this.innerXSep = innerXSep;
            if (innerXSep.getNumericalValue() != 0) {
                addOption(NodeOption.INNER_X_SEP, innerXSep.getFormatted());
            }
            return this;
        }

        public Builder innerYSep(LengthExpression innerYSep) {
            this.innerYSep = innerYSep;
            if (innerYSep.getNumericalValue() != 0) {
                addOption(NodeOption.INNER_Y_SEP, innerYSep.getFormatted());
            }
            return this;
        }

        public Builder outerXSep(LengthExpression outerXSep) {
            this.outerXSep = outerXSep;
            if (outerXSep.getNumericalValue() != 0) {
                addOption(NodeOption.OUTER_X_SEP, outerXSep.getFormatted());
            }
            return this;
        }

        public Builder outerYSep(LengthExpression outerYSep) {
            this.outerYSep = outerYSep;
            if (outerYSep.getNumericalValue() != 0) {
                addOption(NodeOption.OUTER_Y_SEP, outerYSep.getFormatted());
            }
            return this;
        }

        public Builder isMatrix(boolean isMatrix) {
            this.optionalArguments.add(0, "matrix");
            this.isMatrix = isMatrix;
            return this;
        }

        public Builder addCustomOption(String customOption) {
            this.optionalArguments.add(customOption);
            return this;
        }

//        private String createColorPropertyString(BaseColor color, String property) {
//            return !Objects.equals(color.getName(), "default") ? property + "=" + color.getName() : "";
//        }

        private void addOption(NodeOption key, String value) {
            // Skip empty keys or values
            if (key != null && value != null) {
                if (!key.getString().isBlank() && !value.isBlank()) {
                    this.optionalArguments.add(key.getString() + "=" + value);
                }
            }
        }


        public Node build() {
            return new Node(this);
        }
    }
}
