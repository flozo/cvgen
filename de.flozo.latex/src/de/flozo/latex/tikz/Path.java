package de.flozo.latex.tikz;

import de.flozo.common.dto.appearance.*;
import de.flozo.latex.core.Bracket;
import de.flozo.latex.core.Delimiter;
import de.flozo.latex.core.FormattedExpressionList;

import java.util.List;

public abstract class Path {

    // TikZ definition: a path is a series of straight and curved line segments

    // constants
    public static final String COMMAND_MARKER_CHAR = "\\";
    public static final Bracket OPTIONS_BRACKETS = Bracket.SQUARE_BRACKETS;
    public static final Delimiter DELIMITER = Delimiter.SEMICOLON;

    // optional; visible for subclasses
    protected Point position;
    protected List<String> optionalArguments;
    protected String name;
    protected Color drawColor;
    protected Color fillColor;
    protected PredefinedLineWidth predefinedLineWidth;
    protected LineCap lineCap;
    protected LineJoin lineJoin;
    protected DashPattern dashPattern;
    protected boolean skipLastDelimiter;


    public Path(Point position, List<String> optionalArguments, String name,
                Color drawColor, Color fillColor, PredefinedLineWidth predefinedLineWidth,
                LineCap lineCap, LineJoin lineJoin, DashPattern dashPattern,
                boolean skipLastDelimiter) {
        this.position = position;
        this.optionalArguments = optionalArguments;
        this.name = name;
        this.drawColor = drawColor;
        this.fillColor = fillColor;
        this.predefinedLineWidth = predefinedLineWidth;
        this.lineCap = lineCap;
        this.lineJoin = lineJoin;
        this.dashPattern = dashPattern;
        this.skipLastDelimiter = skipLastDelimiter;
    }

    public abstract String getInline();

    private FormattedExpressionList.Builder buildOptionList() {
        return new FormattedExpressionList.Builder(optionalArguments)
                .brackets(OPTIONS_BRACKETS)
                .delimiter(Delimiter.COMMA)
                .skipLastDelimiter(skipLastDelimiter)
                .inlineSpacing(true);
    }

    protected String inlineOptions() {
        return buildOptionList().build().getInline();
    }

    protected List<String> blockOptions() {
        return buildOptionList().indentBlock(true).build().getBlock();
    }


    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Path{" +
                "position=" + position +
                ", optionalArguments=" + optionalArguments +
                ", name='" + name + '\'' +
                ", drawColor=" + drawColor +
                ", fillColor=" + fillColor +
                ", predefinedLineWidth=" + predefinedLineWidth +
                ", lineCap=" + lineCap +
                ", lineJoin=" + lineJoin +
                ", dashPattern=" + dashPattern +
                ", skipLastDelimiter=" + skipLastDelimiter +
                '}';
    }
}
