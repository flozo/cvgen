package de.flozo.cvgen;

import de.flozo.common.dto.appearance.Element;
import de.flozo.latex.core.Delimiter;
import de.flozo.latex.core.LengthExpression;
import de.flozo.latex.tikz.Node;
import de.flozo.latex.tikz.Point;


public class DocumentElement {

    private final String elementName;
    private final ContentElement content;
    private final Element element;

    public DocumentElement(String elementName, ContentElement content, Element element) {
        this.elementName = elementName;
        this.content = content;
        this.element = element;
    }

    private Node getNode(String elementContent) {
        return new Node.Builder(elementContent)
                .name(elementName)
                .position(Point.fromNumbers(element.getPosition().getLengthX().getValue(), element.getPosition().getLengthY().getValue()))
                .anchor(element.getAnchor())
                .minimumWidth(LengthExpression.fromLength(element.getMinimumWidth()))
                .minimumHeight(LengthExpression.fromLength(element.getMinimumHeight()))
                .innerXSep(LengthExpression.fromLength(element.getSeparationSpace().getInnerXSep()))
                .innerYSep(LengthExpression.fromLength(element.getSeparationSpace().getInnerYSep()))
                .fontSize(element.getElementStyle().getTextStyle().getFontSize())
                .textWidth(LengthExpression.fromLength(element.getMinimumWidth()))
                .alignment(element.getElementStyle().getTextStyle().getAlignment())
                .textColor(element.getElementStyle().getTextStyle().getColor())
                .lineCap(element.getElementStyle().getLineStyle().getLineCap())
                .lineJoin(element.getElementStyle().getLineStyle().getLineJoin())
                .dashPatternStyle(element.getElementStyle().getLineStyle().getDashPattern())
                .drawColor(element.getElementStyle().getLineStyle().getBaseColor())
                .fillColor(element.getElementStyle().getAreaStyle().getColor())
                .bodyDelimiter(Delimiter.DOUBLE_BACKSLASH)
                .build();
    }

    public String getElementFieldInline() {
        return getNode(content.inline()).getInline();
    }

    public String getElementFieldMultiline() {
        return getNode(content.multiline()).getInline();
    }


    @Override
    public String toString() {
        return "DocumentElement{" +
                "elementName='" + elementName + '\'' +
                ", content=" + content +
                ", element=" + element +
                '}';
    }
}
