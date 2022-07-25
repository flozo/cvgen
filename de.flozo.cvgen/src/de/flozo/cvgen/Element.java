package de.flozo.cvgen;

import de.flozo.dto.appearance.ElementStyle;
import de.flozo.latex.core.Delimiter;
import de.flozo.latex.core.LengthExpression;
import de.flozo.latex.tikz.Node;
import de.flozo.latex.tikz.Point;


public class Element {

    private final String elementName;
    private final ContentElement content;
    private final ElementStyle style;

    public Element(String elementName, ContentElement content, ElementStyle style) {
        this.elementName = elementName;
        this.content = content;
        this.style = style;
    }

    public String getElementField() {
        return new Node.Builder(content.inline())
                .name(elementName)
                .position(Point.fromNumbers(style.getPosition().getLengthX().getValue(), style.getPosition().getLengthY().getValue()))
//                .anchor(style.getAnchor())
                .minimumWidth(LengthExpression.fromLength(style.getWidth()))
                .minimumHeight(LengthExpression.fromLength(style.getHeight()))
                .fillColor(style.getAreaStyle().getColor())
                .drawColor(style.getLineStyle().getBaseColor())
                .textColor(style.getTextStyle().getColor())
                .textWidth(LengthExpression.fromLength(style.getWidth()))
//                .alignment(style.getTextStyle().)
                .fontSize(style.getTextStyle().getFontSize())
                .bodyDelimiter(Delimiter.DOUBLE_BACKSLASH)
                .build().getInline();
    }


    @Override
    public String toString() {
        return "Element{" +
                "elementName='" + elementName + '\'' +
                ", content=" + content +
                ", style=" + style +
                '}';
    }
}
