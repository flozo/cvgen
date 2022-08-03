package de.flozo.cvgen;

import de.flozo.common.dto.appearance.Line;
import de.flozo.common.dto.appearance.Page;
import de.flozo.latex.core.*;
import de.flozo.latex.tikz.Rectangle;

import java.util.ArrayList;
import java.util.List;

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
        this.pageOptions = new FormattedExpressionList.Builder("inner xsep=0pt", "inner ysep=0pt", "trim left=0pt", "trim right=" + LengthExpression.fromLength(pageProperties.getWidth()).getFormatted()).build();
    }

    private Rectangle getBackgroundRectangle() {
        return new Rectangle.Builder(0, 0, pageProperties.getWidth().getValue(), pageProperties.getHeight().getValue())
                .fillColor(pageProperties.getAreaStyle().getColor())
                .skipLastTerminator(true)
                .build();
    }

    private List<String> assembleDocumentElements() {
        List<String> codeLines = new ArrayList<>();
        codeLines.add(getBackgroundRectangle().getInline());
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
