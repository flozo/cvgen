package de.flozo.cvgen;

import de.flozo.common.dto.appearance.Element;
import de.flozo.latex.core.Delimiter;
import de.flozo.latex.core.ExpressionList;
import de.flozo.latex.core.LengthExpression;
import de.flozo.latex.tikz.Node;

public class ColumnStyle {

    Element element;

    public ColumnStyle(Element element) {
        this.element = element;
    }


    public ExpressionList getStyle() {
        return new Node.Builder()
                .addCustomOption("rectangle")
                .minimumWidth(LengthExpression.fromLength(element.getMinimumWidth()))
                .minimumHeight(LengthExpression.fromLength(element.getMinimumHeight()))
                .innerXSep(LengthExpression.fromLength(element.getSeparationSpace().getInnerXSep()))
                .innerYSep(LengthExpression.fromLength(element.getSeparationSpace().getInnerYSep()))
                .outerXSep(LengthExpression.fromLength(element.getSeparationSpace().getOuterXSep()))
                .outerYSep(LengthExpression.fromLength(element.getSeparationSpace().getOuterYSep()))
                .fontSize(element.getElementStyle().getTextStyle().getFontSize())
                .textColor(element.getElementStyle().getTextStyle().getColor())
                .textOpacity(element.getElementStyle().getTextStyle().getOpacity())
                .drawColor(element.getElementStyle().getLineStyle().getColor())
                .lineOpacity(element.getElementStyle().getLineStyle().getOpacity())
                .lineCap(element.getElementStyle().getLineStyle().getLineCap())
                .lineJoin(element.getElementStyle().getLineStyle().getLineJoin())
                .dashPatternStyle(element.getElementStyle().getLineStyle().getDashPattern())
                .fillColor(element.getElementStyle().getAreaStyle().getColor())
                .areaOpacity(element.getElementStyle().getAreaStyle().getOpacity())
                .alignment(element.getElementStyle().getTextStyle().getAlignment())
                .textWidth(LengthExpression.fromLength(element.getElementStyle().getTextStyle().getTextWidth()))
                .textHeight(LengthExpression.fromLength(element.getElementStyle().getTextStyle().getTextHeight()))
                .textDepth(LengthExpression.fromLength(element.getElementStyle().getTextStyle().getTextDepth()))
                .bodyDelimiter(Delimiter.NONE)
                .build()
                .getOptions();
    }

    @Override
    public String toString() {
        return "ColumnStyle{" +
                "element=" + element +
                '}';
    }
}
