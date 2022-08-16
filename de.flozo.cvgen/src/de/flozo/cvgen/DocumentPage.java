package de.flozo.cvgen;

import de.flozo.common.dto.appearance.*;
import de.flozo.common.dto.appearance.LengthUnit;
import de.flozo.latex.core.*;
import de.flozo.latex.tikz.LinePath;
import de.flozo.latex.tikz.MatrixOfNodes;
import de.flozo.latex.tikz.RectanglePath;

import java.util.*;
import java.util.stream.Collectors;

public class DocumentPage {

    public static final boolean DEFAULT_INSERT_LATEX_COMMENTS = false;

    // required
    private final String name;
    private final Page pageProperties;

    // optional
    private final List<DocumentElement> documentElements;
    private final List<MatrixOfNodes> matrices;
    private final List<Line> lines;
    private final List<Rectangle> rectangles;
    private final ExpressionList pageOptions;
    private final boolean insertLatexComments;

    private DocumentPage(Builder builder) {
        this.name = builder.name;
        this.documentElements = builder.documentElements;
        this.matrices = builder.matrices;
        this.pageProperties = builder.pageProperties;
        this.lines = builder.lines;
        this.rectangles = builder.rectangles;
        this.pageOptions = new FormattedExpressionList.Builder(
                "inner xsep=0pt",
                "inner ysep=0pt",
                "trim left=0pt",
                "trim right=" + LengthExpression.fromLength(pageProperties.getWidth()).getFormatted())
                .build();
        this.insertLatexComments = builder.insertLatexComments;
    }

    private RectanglePath getBackgroundRectangle() {
        Position origin = new Position(0, "",
                new Length(0, "", 0.0, new LengthUnit(0, "", "")),
                new Length(0, "", 0.0, new LengthUnit(0, "", "")));
        Position target = new Position(0, "", pageProperties.getWidth(), pageProperties.getHeight());
        return new RectanglePath.Builder(origin, target)
                .fillColor(pageProperties.getAreaStyle().getColor())
                .drawColor(pageProperties.getLineStyle().getColor())
                .skipLastTerminator(true)
                .build();
    }

    private RectanglePath getRectangle(Rectangle rectangle) {
        Position origin = new Position(0, "", rectangle.getOrigin().getLengthX(), rectangle.getOrigin().getLengthY());
        Position target = new Position(0, "", rectangle.getTarget().getLengthX(), rectangle.getTarget().getLengthY());
        return new RectanglePath.Builder(origin, target)
                .lineWidth(LengthExpression.fromLineWidth(rectangle.getLineStyle().getLineWidth()))
                .drawColor(rectangle.getLineStyle().getColor())
                .lineCap(rectangle.getLineStyle().getLineCap())
                .lineJoin(rectangle.getLineStyle().getLineJoin())
                .dashPattern(rectangle.getLineStyle().getDashPattern())
                .fillColor(rectangle.getAreaStyle().getColor())
                .skipLastTerminator(true)
                .build();
    }

    private Map<String, RectanglePath> getRectangleMap() {
        return new LinkedHashMap<>(rectangles.stream().collect(Collectors.toMap(Rectangle::getName, this::getRectangle)));
    }

    private LinePath getLine(Line line) {
        Length xLength = null;
        Length yLength = null;
        if (Objects.equals(line.getOrientation(), "horizontal")) {
            double xTarget = line.getPosition().getLengthX().getValue() + line.getLength().getValue();
            xLength = new Length(0, "", xTarget, line.getPosition().getLengthX().getUnit());
            yLength = new Length(0, "", line.getPosition().getLengthY().getValue(), line.getPosition().getLengthY().getUnit());
        } else {
            xLength = new Length(0, "", line.getPosition().getLengthX().getValue(), line.getPosition().getLengthX().getUnit());
            double yTarget = line.getPosition().getLengthY().getValue();
            yLength = new Length(0, "", yTarget, line.getPosition().getLengthY().getUnit());
        }
        Position target = new Position(0, "", xLength, yLength);
        return new LinePath.Builder(line.getPosition(), target)
                .lineWidth(line.getLineStyle().getLineWidth())
                .drawColor(line.getLineStyle().getColor())
                .lineCap(line.getLineStyle().getLineCap())
                .lineJoin(line.getLineStyle().getLineJoin())
                .dashPattern(line.getLineStyle().getDashPattern())
                .skipLastDelimiter(true)
                .build();
    }

    private Map<String, LinePath> getLineMap() {
        return new LinkedHashMap<>(lines.stream().collect(Collectors.toMap(Line::getName, this::getLine)));
    }

    private String getCommentLine(String comment) {
        return "% " + comment.toUpperCase();
    }

    private List<String> assembleDocumentElements() {
        List<String> codeLines = new ArrayList<>();
        if (insertLatexComments) codeLines.add(getCommentLine(pageProperties.getAreaStyle().getName()));
        codeLines.add(getBackgroundRectangle().getInline());
        for (Map.Entry<String, RectanglePath> rectanglePath : getRectangleMap().entrySet()) {
            if (insertLatexComments) codeLines.add(getCommentLine(rectanglePath.getKey()));
            codeLines.add(rectanglePath.getValue().getInline());
        }
        for (Map.Entry<String, LinePath> linePath : getLineMap().entrySet()) {
            if (insertLatexComments) codeLines.add(getCommentLine(linePath.getKey()));
            codeLines.add(linePath.getValue().getInline());
        }
        for (DocumentElement documentElement : documentElements) {
            if (insertLatexComments) codeLines.add(getCommentLine(documentElement.getElementName()));
            codeLines.addAll(documentElement.getElementFieldInline());
        }
        for (MatrixOfNodes matrix : matrices) {
            if (insertLatexComments) codeLines.add(getCommentLine(matrix.getName()));
            codeLines.addAll(matrix.getBlock());
        }
        return codeLines;
    }

    private ExpressionList getTikzPictureBody() {
        return new FormattedExpressionList.Builder()
                .append(assembleDocumentElements())
                .build();
    }

    private Environment getTikzPictureEnvironment() {
        return new Environment.Builder(EnvironmentName.TIKZPICTURE)
                .optionalArguments(pageOptions.getBlock())
                .body(getTikzPictureBody().getBlock())
                .build();
    }


    public List<String> getCode() {
        return getTikzPictureEnvironment().getBlock();
    }

    @Override
    public String toString() {
        return "DocumentPage{" +
                "name='" + name + '\'' +
                ", pageProperties=" + pageProperties +
                ", documentElements=" + documentElements +
                ", matrices=" + matrices +
                ", lines=" + lines +
                ", rectangles=" + rectangles +
                ", pageOptions=" + pageOptions +
                ", insertLatexComments=" + insertLatexComments +
                '}';
    }

    public static class Builder {

        // required
        private final String name;
        private final Page pageProperties;

        // optional
        private final List<DocumentElement> documentElements = new ArrayList<>();
        private final List<MatrixOfNodes> matrices = new ArrayList<>();
        private final List<Line> lines = new ArrayList<>();
        private final List<Rectangle> rectangles = new ArrayList<>();
        private boolean insertLatexComments = DEFAULT_INSERT_LATEX_COMMENTS;

        public Builder(String name, Page pageProperties) {
            this.name = name;
            this.pageProperties = pageProperties;
        }

        public Builder addElement(List<DocumentElement> documentElements) {
            this.documentElements.addAll(documentElements);
            return this;
        }

        public Builder addElement(DocumentElement... documentElements) {
            return addElement(new ArrayList<>(List.of(documentElements)));
        }

        public Builder addMatrix(List<MatrixOfNodes> matrices) {
            this.matrices.addAll(matrices);
            return this;
        }

        public Builder addMatrix(MatrixOfNodes... matrices) {
            return addMatrix(new ArrayList<>(List.of(matrices)));
        }

        public Builder addLine(List<Line> lines) {
            this.lines.addAll(lines);
            return this;
        }

        public Builder addLine(Line... lines) {
            return addLine(new ArrayList<>(List.of(lines)));
        }

        public Builder addRectangle(List<Rectangle> rectangles) {
            this.rectangles.addAll(rectangles);
            return this;
        }

        public Builder addRectangle(Rectangle rectangles) {
            return addRectangle(new ArrayList<>(List.of(rectangles)));
        }

        public Builder insertLatexComments(boolean insertLatexComments) {
            this.insertLatexComments = insertLatexComments;
            return this;
        }

        public DocumentPage build() {
            return new DocumentPage(this);
        }
    }
}
