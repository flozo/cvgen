package de.flozo.cvgen;

import de.flozo.common.dto.appearance.Element;
import de.flozo.latex.core.Delimiter;
import de.flozo.latex.core.GenericCommand;
import de.flozo.latex.core.LengthExpression;
import de.flozo.latex.tikz.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class DocumentElement {

    private final String elementName;
    private final ContentElement content;
    private final Element element;

    public DocumentElement(String elementName, ContentElement content, Element element) {
        this.elementName = elementName;
        this.content = content;
        this.element = element;
    }

    private String applyTextFormat(String text) {
        if (!Objects.equals(element.getElementStyle().getTextStyle().getTextFormat().getName(), "default")) {
            return new GenericCommand.Builder(element.getElementStyle().getTextStyle().getTextFormat().getValue())
                    .body(text)
                    .build().getInline();
        }
        return text;
    }

    private Node getNode(String elementContent) {
        return new Node.Builder(applyTextFormat(elementContent))
                .name(elementName)
                .position(element.getPosition())
                .anchor(element.getAnchor())
                .xShift(LengthExpression.fromLength(element.getXShift()))
                .yShift(LengthExpression.fromLength(element.getYShift()))
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
                .lineWidth(element.getElementStyle().getLineStyle().getLineWidth())
                .lineCap(element.getElementStyle().getLineStyle().getLineCap())
                .lineJoin(element.getElementStyle().getLineStyle().getLineJoin())
                .dashPatternStyle(element.getElementStyle().getLineStyle().getDashPattern())
                .drawColor(element.getElementStyle().getLineStyle().getColor())
                .lineOpacity(element.getElementStyle().getLineStyle().getOpacity())
                .fillColor(element.getElementStyle().getAreaStyle().getColor())
                .areaOpacity(element.getElementStyle().getAreaStyle().getOpacity())
                .bodyDelimiter(Delimiter.DOUBLE_BACKSLASH)
                .build();
    }

    public List<String> getElementFieldInline() {
        return new ArrayList<>(List.of(getNode(content.getInline()).getInline()));
    }


    public List<String> getElementFieldBlock() {
        return getNode(content.getInline()).getBlock();
    }


    public String getElementName() {
        return elementName;
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
