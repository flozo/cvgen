package de.flozo.cvgen;

import de.flozo.common.dto.appearance.Element;
import de.flozo.common.dto.content.TextItem;
import de.flozo.common.dto.content.TimelineItem;
import de.flozo.common.dto.content.TimelineTextItemLink;
import de.flozo.latex.tikz.MatrixOfNodes;

import java.util.List;

public class Timeline {

    private final String timelineName;
    private final TextItem timelineTitle;
    private final Element timelineTitleStyle;
    private final List<TimelineItem> items;
    private final List<TimelineTextItemLink> textItems;
    private final Element itemMatrixStyle;
    private final List<Element> columnStyles;

    public Timeline(String timelineName, TextItem timelineTitle, Element timelineTitleStyle, List<TimelineItem> items, List<TimelineTextItemLink> textItems, Element itemMatrixStyle, List<Element> columnStyles) {
        this.timelineName = timelineName;
        this.timelineTitle = timelineTitle;
        this.timelineTitleStyle = timelineTitleStyle;
        this.items = items;
        this.textItems = textItems;
        this.itemMatrixStyle = itemMatrixStyle;
        this.columnStyles = columnStyles;
    }

    private ContentElement getTitle() {
        return new ContentElement.Builder()
                .addComponent(timelineTitle.getValue())
                .build();
    }

    public DocumentElement getTitleField() {
        return new DocumentElement(timelineTitle.getName(), getTitle(), timelineTitleStyle);
    }

    public MatrixOfNodes getItemMatrix(int startIndex, int endIndex) {
        MatrixOfNodes.Builder matrixBuilder = new MatrixOfNodes.Builder("career", itemMatrixStyle);
        for (TimelineItem item : items.subList(startIndex, endIndex + 1)) {
            matrixBuilder.addRow(TimelinePeriod.withDefaultFormat(item).getPeriodTag(), item.getCompany(), item.getTask());
        }
        for (Element style : columnStyles) {
            matrixBuilder.addColumnStyle(new ColumnStyle(style).getStyle());
        }
        return matrixBuilder.build();
    }

    public MatrixOfNodes getItemMatrix() {
        return getItemMatrix(0, items.size() - 1);
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
                '}';
    }
}
