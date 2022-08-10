package de.flozo.cvgen;

import de.flozo.common.dto.appearance.Element;
import de.flozo.latex.core.LengthExpression;
import de.flozo.latex.tikz.NodeOption;
import de.flozo.latex.tikz.NodeStyle;

public class ColumnStyle {

    Element element;

    public ColumnStyle(Element element) {
        this.element = element;
    }


    public NodeStyle getStyle() {
        return new NodeStyle.Builder()
                .addCustomOption("rectangle")
                .addNodeOption(NodeOption.FILL, element.getElementStyle().getAreaStyle().getColor().getSpecifier())
//                    .addNodeOption(NodeOption.DRAW, element.getElementStyle().getLineStyle().getColor().getSpecifier())
//                    .addNodeOption(NodeOption.TEXT,element.getElementStyle().getTextStyle().getColor().getSpecifier())
                .addNodeOption(NodeOption.ALIGN, element.getElementStyle().getTextStyle().getAlignment().getValue())
                .addNodeOption(NodeOption.INNER_X_SEP, LengthExpression.fromLength(element.getSeparationSpace().getInnerXSep()).getFormatted())
                .addNodeOption(NodeOption.INNER_Y_SEP, LengthExpression.fromLength(element.getSeparationSpace().getInnerYSep()).getFormatted())
                .addNodeOption(NodeOption.MINIMUM_WIDTH, LengthExpression.fromLength(element.getMinimumWidth()).getFormatted())
                .addNodeOption(NodeOption.MINIMUM_HEIGHT, LengthExpression.fromLength(element.getMinimumHeight()).getFormatted())
                .addNodeOption(NodeOption.TEXT_WIDTH, LengthExpression.fromLength(element.getElementStyle().getTextStyle().getTextWidth()).getFormatted())
                .addNodeOption(NodeOption.TEXT_HEIGHT, LengthExpression.fromLength(element.getElementStyle().getTextStyle().getTextHeight()).getFormatted())
                .build();
    }

    @Override
    public String toString() {
        return "ColumnStyle{" +
                "element=" + element +
                '}';
    }
}
