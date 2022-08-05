package de.flozo.cvgen;

import de.flozo.common.dto.appearance.Length;
import de.flozo.common.dto.appearance.Line;
import de.flozo.common.dto.appearance.Page;
import de.flozo.latex.core.*;
import de.flozo.latex.tikz.LinePath;
import de.flozo.latex.tikz.Point;
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
    private final List<Line> lines;
    private final ExpressionList pageOptions;
    private final boolean insertLatexComments;

    private DocumentPage(Builder builder) {
        this.name = builder.name;
        this.documentElements = builder.documentElements;
        this.pageProperties = builder.pageProperties;
        this.lines = builder.lines;
        this.pageOptions = new FormattedExpressionList.Builder(
                "inner xsep=0pt",
                "inner ysep=0pt",
                "trim left=0pt",
                "trim right=" + LengthExpression.fromLength(pageProperties.getWidth()).getFormatted())
                .build();
        this.insertLatexComments = builder.insertLatexComments;
    }

    private RectanglePath getBackgroundRectangle() {
        return new RectanglePath.Builder(0, 0, pageProperties.getWidth().getValue(), pageProperties.getHeight().getValue())
                .fillColor(pageProperties.getAreaStyle().getColor())
                .skipLastTerminator(true)
                .build();
    }


    private LinePath getLine(Line line) {
        Length xLength = null;
        Length yLength = null;
        if (Objects.equals(line.getOrientation(), "horizontal")) {
//            if (Objects.equals(line.getPosition().getLengthX().getUnit(), line.getLength().getUnit())) {
            double xTarget = line.getPosition().getLengthX().getValue() + line.getLength().getValue();
            xLength = new Length(0, "", xTarget, line.getPosition().getLengthX().getUnit());
            yLength = new Length(0, "", line.getPosition().getLengthY().getValue(), line.getPosition().getLengthY().getUnit());
//            }
        } else {
//            if (Objects.equals(line.getPosition().getLengthX().getUnit(), line.getLength().getUnit())) {
            xLength = new Length(0, "", line.getPosition().getLengthX().getValue(), line.getPosition().getLengthX().getUnit());
            double yTarget = line.getPosition().getLengthY().getValue();
            yLength = new Length(0, "", yTarget, line.getPosition().getLengthY().getUnit());
//            }
        }
        Point origin = Point.fromLengths(
                LengthExpression.fromLength(line.getPosition().getLengthX()),
                LengthExpression.fromLength(line.getPosition().getLengthY()));
        Point target = Point.fromLengths(
                LengthExpression.fromLength(xLength),
                LengthExpression.fromLength(yLength));
        System.out.println(origin.getStatement());
        System.out.println(target.getStatement());
        return new LinePath.Builder(origin, target)
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
        if (insertLatexComments) {
            codeLines.add(getCommentLine(pageProperties.getAreaStyle().getName()));
        }
        codeLines.add(getBackgroundRectangle().getInline());
        for (Map.Entry<String, LinePath> linePath : getLineMap().entrySet()) {
            if (insertLatexComments) {
                codeLines.add(getCommentLine(linePath.getKey()));
            }
            codeLines.add(linePath.getValue().getInline());
        }
        for (DocumentElement documentElement : documentElements) {
            if (insertLatexComments) {
                codeLines.add(getCommentLine(documentElement.getElementName()));
            }
            codeLines.addAll(documentElement.getElementFieldInline());
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
                ", lines=" + lines +
                ", pageOptions=" + pageOptions +
                '}';
    }

    public static class Builder {

        // required
        private final String name;
        private final Page pageProperties;

        // optional
        private List<DocumentElement> documentElements = new ArrayList<>();
        private List<Line> lines;
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


        public Builder addLine(List<Line> lines) {
            this.lines = lines;
            return this;
        }

        public Builder addLine(Line... lines) {
            return addLine(new ArrayList<>(List.of(lines)));
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
