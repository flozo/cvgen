package de.flozo.cvgen;

import de.flozo.common.dto.appearance.Element;
import de.flozo.common.dto.content.TextItem;
import de.flozo.common.dto.content.TimelineItem;
import de.flozo.latex.tikz.MatrixOfNodes;

import java.util.List;

public class Timeline {

    private final String timelineName;
    private final TextItem timelineTitle;
    private final Element timelineTitleStyle;
    private final List<TimelineItem> items;
    private final Element itemMatrixStyle;
    private final List<Element> columnStyles;
    private final int startIndex;
    private final int endIndex;

    public Timeline(String timelineName, TextItem timelineTitle, Element timelineTitleStyle, List<TimelineItem> items, Element itemMatrixStyle, List<Element> columnStyles, int startIndex, int endIndex) {
        this.timelineName = timelineName;
        this.timelineTitle = timelineTitle;
        this.timelineTitleStyle = timelineTitleStyle;
        this.items = items.subList(startIndex, endIndex + 1);
        this.itemMatrixStyle = itemMatrixStyle;
        this.columnStyles = columnStyles;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public Timeline(String timelineName, TextItem timelineTitle, Element timelineTitleStyle, List<TimelineItem> items, Element itemMatrixStyle, List<Element> columnStyles) {
        this(timelineName, timelineTitle, timelineTitleStyle, items, itemMatrixStyle, columnStyles, 0, items.size() - 1);
    }

    private ContentElement getTitle() {
        return new ContentElement.Builder()
                .addComponent(timelineTitle.getValue())
                .build();
    }

    public DocumentElement getTitleField() {
        return new DocumentElement(timelineTitle.getName(), getTitle(), timelineTitleStyle);
    }

    public MatrixOfNodes getItemMatrix() {
        MatrixOfNodes.Builder matrixBuilder = new MatrixOfNodes.Builder("career", itemMatrixStyle);
        for (TimelineItem item : items) {
            matrixBuilder.addRow(TimelinePeriod.withDefaultFormat(item).getPeriodTag(), item.getCompany(), item.getTask());
        }
        for (Element style : columnStyles) {
            matrixBuilder.addColumnStyle(new ColumnStyle(style).getStyle());
        }
        return matrixBuilder.build();
    }

    @Override
    public String toString() {
        return "Timeline{" +
                "timelineName='" + timelineName + '\'' +
                ", timelineTitle=" + timelineTitle +
                ", timelineTitleStyle=" + timelineTitleStyle +
                ", items=" + items +
                ", itemMatrixStyle=" + itemMatrixStyle +
                ", columnStyles=" + columnStyles +
                ", startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                '}';
    }
}
