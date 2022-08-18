package de.flozo.cvgen;

import de.flozo.common.dto.appearance.Element;
import de.flozo.common.dto.appearance.ItemizeStyle;
import de.flozo.common.dto.content.TextItem;
import de.flozo.common.dto.content.TimelineItem;
import de.flozo.common.dto.content.TimelineTextItemLink;
import de.flozo.common.dto.content.TimelineType;
import de.flozo.latex.core.ItemizeEnvironment;
import de.flozo.latex.core.LengthExpression;
import de.flozo.latex.tikz.MatrixOfNodes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Timeline {

    private final String timelineName;
    private final TextItem timelineTitle;
    private final Element timelineTitleStyle;
    private final List<TimelineItem> items;
    private final List<TimelineTextItemLink> textItems;
    private final ItemizeStyle itemizeStyle;
    private final Element itemMatrixStyle;
    private final List<Element> columnStyles;

    public Timeline(String timelineName, TextItem timelineTitle, Element timelineTitleStyle, List<TimelineItem> items, List<TimelineTextItemLink> textItems, ItemizeStyle itemizeStyle, Element itemMatrixStyle, List<Element> columnStyles) {
        this.timelineName = timelineName;
        this.timelineTitle = timelineTitle;
        this.timelineTitleStyle = timelineTitleStyle;
        this.items = items;
        this.textItems = textItems;
        this.itemizeStyle = itemizeStyle;
        this.itemMatrixStyle = itemMatrixStyle;
        this.columnStyles = columnStyles;
    }

    private ContentElement getTitle() {
        return new ContentElement.Builder()
                .addComponent(timelineTitle.getValue())
                .build();
    }

    private List<String> itemizeOptions() {
        List<String> itemizeOptions = new ArrayList<>();
        itemizeOptions.add(String.format("topsep=%s", LengthExpression.fromLength(itemizeStyle.getTopSep()).getFormatted()));
        itemizeOptions.add(String.format("leftmargin=%s", LengthExpression.fromLength(itemizeStyle.getLeftMargin()).getFormatted()));
        itemizeOptions.add(String.format("labelsep=%s", LengthExpression.fromLength(itemizeStyle.getLabelSep()).getFormatted()));
        itemizeOptions.add(String.format("itemindent=%s", LengthExpression.fromLength(itemizeStyle.getItemIndent()).getFormatted()));
        itemizeOptions.add(String.format("itemsep=%s", LengthExpression.fromLength(itemizeStyle.getItemSep()).getFormatted()));
        itemizeOptions.add(String.format("label=%s", itemizeStyle.getLabel().getValue()));
        return itemizeOptions;
    }

    private ItemizeEnvironment textItems(int timelineId) {
        // Select textItems associated with the current Timeline.
        List<String> itemList = textItems.stream()
                .filter(e -> e.getId() == timelineId)
                .map(TimelineTextItemLink::getTextItem)
                .map(TextItem::getValue)
                .map(e -> e + "\n")
                .collect(Collectors.toList());
        return new ItemizeEnvironment(itemizeOptions(), itemList);
    }

    public DocumentElement getTitleField() {
        return new DocumentElement(timelineTitle.getName(), getTitle(), timelineTitleStyle);
    }

    public MatrixOfNodes getItemMatrix(int startIndex, int endIndex, Element headline, Element elementItems) {
        MatrixOfNodes.Builder matrixBuilder = new MatrixOfNodes.Builder(timelineName, itemMatrixStyle);
        for (TimelineItem item : items.subList(startIndex, endIndex + 1)) {
            matrixBuilder.addRow(headline, TimelinePeriod.withDefaultFormat(item).getPeriodTag(), item.getCompany(), item.getTask());
            // Add textItems if present for current TimelineItem.
            if (textItems.stream()
                    .map(TimelineTextItemLink::getTimelineType)
                    .map(TimelineType::getId)
                    .collect(Collectors.toList())
                    .contains(item.getTimelineType().getId())) {
                // Use elementStyle if present.
                matrixBuilder.addRow(elementItems, "", textItems(item.getId()).getEnvironment().getInline(), "");
//                    matrixBuilder.addRowOfNodes(
//                            new Node.Builder("").build(),
//                            new Node.Builder(textItems(item.getId()).getEnvironment().getInline())
//                                    .fontSize(elementStyle.getTextStyle().getFontSize())
//                                    .textColor(elementStyle.getTextStyle().getColor())
//                                    .textWidth(LengthExpression.fromLength(elementStyle.getTextStyle().getTextWidth()))
//                                    .textHeight(LengthExpression.fromLength(elementStyle.getTextStyle().getTextHeight()))
//                                    .textDepth(LengthExpression.fromLength(elementStyle.getTextStyle().getTextDepth()))
//                                    .textOpacity(elementStyle.getTextStyle().getOpacity())
//                                    .lineCap(elementStyle.getLineStyle().getLineCap())
//                                    .lineJoin(elementStyle.getLineStyle().getLineJoin())
//                                    .drawColor(elementStyle.getLineStyle().getColor())
//                                    .lineOpacity(elementStyle.getLineStyle().getOpacity())
//                                    .dashPatternStyle(elementStyle.getLineStyle().getDashPattern())
//                                    .fillColor(elementStyle.getAreaStyle().getColor())
//                                    .build(),
//                            new Node.Builder("").build());
            }
        }
        for (Element style : columnStyles) {
            matrixBuilder.addColumnStyle(new ColumnStyle(style).getStyle());
        }
        return matrixBuilder.build();
    }

    public MatrixOfNodes getItemMatrix(Element headline, Element elementItems) {
        return getItemMatrix(0, items.size() - 1, headline, elementItems);
    }

    public MatrixOfNodes getItemMatrix(int startIndex, int endIndex) {
        return getItemMatrix(startIndex, endIndex, null, null);
    }

    public MatrixOfNodes getItemMatrix() {
        return getItemMatrix(0, items.size() - 1, null, null);
    }

    @Override
    public String toString() {
        return "Timeline{" +
                "timelineName='" + timelineName + '\'' +
                ", timelineTitle=" + timelineTitle +
                ", timelineTitleStyle=" + timelineTitleStyle +
                ", items=" + items +
                ", textItems=" + textItems +
                ", itemizeStyle=" + itemizeStyle +
                ", itemMatrixStyle=" + itemMatrixStyle +
                ", columnStyles=" + columnStyles +
                '}';
    }
}
