package de.flozo.cvgen;

import de.flozo.common.dto.appearance.Element;
import de.flozo.latex.core.Delimiter;
import de.flozo.latex.core.LengthExpression;
import de.flozo.latex.tikz.Node;
import de.flozo.latex.tikz.Point;

import java.util.List;


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
//                .position(Point.fromLengths(LengthExpression.fromLength(element.getPosition().getLengthX()), LengthExpression.fromLength(element.getPosition().getLengthY())))
                .anchor(element.getAnchor())
                .minimumWidth(LengthExpression.fromLength(element.getMinimumWidth()))
                .minimumHeight(LengthExpression.fromLength(element.getMinimumHeight()))
                .innerXSep(LengthExpression.fromLength(element.getSeparationSpace().getInnerXSep()))
                .innerYSep(LengthExpression.fromLength(element.getSeparationSpace().getInnerYSep()))
                .outerXSep(LengthExpression.fromLength(element.getSeparationSpace().getOuterXSep()))
                .outerYSep(LengthExpression.fromLength(element.getSeparationSpace().getOuterYSep()))
                .fontSize(element.getElementStyle().getTextStyle().getFontSize())
                .textWidth(LengthExpression.fromLength(element.getMinimumWidth()))
                .alignment(element.getElementStyle().getTextStyle().getAlignment())
                .textColor(element.getElementStyle().getTextStyle().getColor())
                .textOpacity(element.getElementStyle().getTextStyle().getOpacity())
                .lineCap(element.getElementStyle().getLineStyle().getLineCap())
                .lineJoin(element.getElementStyle().getLineStyle().getLineJoin())
                .dashPatternStyle(element.getElementStyle().getLineStyle().getDashPattern())
                .drawColor(element.getElementStyle().getLineStyle().getBaseColor())
                .lineOpacity(element.getElementStyle().getLineStyle().getOpacity())
                .fillColor(element.getElementStyle().getAreaStyle().getColor())
                .areaOpacity(element.getElementStyle().getAreaStyle().getOpacity())
                .bodyDelimiter(Delimiter.DOUBLE_BACKSLASH)
                .build();
    }

    public String getElementFieldInline() {
        return getNode(content.getContentElement()).getInline();
    }

    public List<String> getElementFieldBlock() {
        return getNode(content.getContentElement()).getBlock();
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
