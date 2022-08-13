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
//        return new NodeStyle.Builder()
//                .addCustomOption("rectangle")
//                .addNodeOption(NodeOption.FILL, element.getElementStyle().getAreaStyle().getColor().getSpecifier())
////                    .addNodeOption(NodeOption.DRAW, element.getElementStyle().getLineStyle().getColor().getSpecifier())
////                    .addNodeOption(NodeOption.TEXT,element.getElementStyle().getTextStyle().getColor().getSpecifier())
//                .addNodeOption(NodeOption.ALIGN, element.getElementStyle().getTextStyle().getAlignment().getValue())
//                .addNodeOption(NodeOption.INNER_X_SEP, LengthExpression.fromLength(element.getSeparationSpace().getInnerXSep()).getFormatted())
//                .addNodeOption(NodeOption.INNER_Y_SEP, LengthExpression.fromLength(element.getSeparationSpace().getInnerYSep()).getFormatted())
//                .addNodeOption(NodeOption.MINIMUM_WIDTH, LengthExpression.fromLength(element.getMinimumWidth()).getFormatted())
//                .addNodeOption(NodeOption.MINIMUM_HEIGHT, LengthExpression.fromLength(element.getMinimumHeight()).getFormatted())
//                .addNodeOption(NodeOption.TEXT_WIDTH, LengthExpression.fromLength(element.getElementStyle().getTextStyle().getTextWidth()).getFormatted())
//                .addNodeOption(NodeOption.TEXT_HEIGHT, LengthExpression.fromLength(element.getElementStyle().getTextStyle().getTextHeight()).getFormatted())
//                .build();
    }

    @Override
    public String toString() {
        return "ColumnStyle{" +
                "element=" + element +
                '}';
    }
}
