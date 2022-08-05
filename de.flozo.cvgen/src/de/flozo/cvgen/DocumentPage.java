package de.flozo.cvgen;

import de.flozo.common.dto.appearance.Length;
import de.flozo.common.dto.appearance.Line;
import de.flozo.common.dto.appearance.Page;
import de.flozo.latex.core.*;
import de.flozo.latex.tikz.LinePath;
import de.flozo.latex.tikz.Point;
import de.flozo.latex.tikz.RectanglePath;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DocumentPage {

    // required
    private final String name;
    private final Page pageProperties;

    // optional
    private final List<DocumentElement> documentElements;
    private final List<Line> lines;
    private final ExpressionList pageOptions;

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
                .build();
    }

    private List<LinePath> getLineList() {
        return lines.stream().map(this::getLine).collect(Collectors.toList());
//        List<LinePath> linePaths = new ArrayList<>();
//        for (Line line : lines) {
//            linePaths.add(getLine(line));
//        }
//        return linePaths;
    }

    private List<String> assembleDocumentElements() {
        List<String> codeLines = new ArrayList<>();
        codeLines.add(getBackgroundRectangle().getInline());
        for (LinePath linePath : getLineList()) {
            codeLines.add(linePath.getInline());
        }
        for (DocumentElement documentElement : documentElements) {
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
        private List<DocumentElement> documentElements;
        private List<Line> lines;

        public Builder(String name, Page pageProperties) {
            this.name = name;
            this.pageProperties = pageProperties;
        }

        public Builder addElement(List<DocumentElement> documentElements) {
            this.documentElements = documentElements;
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


        public DocumentPage build() {
            return new DocumentPage(this);
        }


    }
}
