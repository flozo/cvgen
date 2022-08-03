package de.flozo.cvgen;

import de.flozo.latex.core.Environment;
import de.flozo.latex.core.EnvironmentName;
import de.flozo.latex.core.ExpressionList;
import de.flozo.latex.core.FormattedExpressionList;

import java.util.ArrayList;
import java.util.List;

public class DocumentPage {

    private final List<DocumentElement> documentElements;
    private final ExpressionList pageOptions;

    public DocumentPage(ExpressionList pageOptions, List<DocumentElement> documentElements) {
        this.documentElements = documentElements;
        this.pageOptions = pageOptions;
    }

    public DocumentPage(ExpressionList pageOptions, DocumentElement... documentElements) {
        this(pageOptions, new ArrayList<>(List.of(documentElements)));
    }

//    private Rectangle getBackgroundRectangle() {
//        return new Rectangle.Builder(0, 0, totalWidth.getNumericalValue(), totalHeight.getNumericalValue())
//                .fillColor(backgroundColor)
//                .drawColor(StandardColor.NONE)
//                .skipLastTerminator(true)
//                .build();
//    }
//

    private List<String> assembleDocumentElements() {
        List<String> codeLines = new ArrayList<>();
        for (DocumentElement documentElement : documentElements) {
            codeLines.addAll(documentElement.getElementFieldInline());
        }
        return codeLines;
    }

    private ExpressionList getTikzPictureBody() {
        return new FormattedExpressionList.Builder().append(assembleDocumentElements()).build();
    }

    private Environment getTikzPictureEnvironment() {
        return new Environment.Builder(EnvironmentName.TIKZPICTURE)
                .optionalArguments(pageOptions.getBlock())
                .body(getTikzPictureBody().getBlock())
                .build();
    }


    public List<String> getCode() {
        return getTikzPictureEnvironment().getBlock();
//        return documentElements.stream().map(DocumentElement::getElementFieldInline).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "DocumentPage{" +
                "documentElements=" + documentElements +
                ", pageOptions=" + pageOptions +
                '}';
    }
}
