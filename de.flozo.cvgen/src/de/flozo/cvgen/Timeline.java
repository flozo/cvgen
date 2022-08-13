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

    public Timeline(String timelineName, TextItem timelineTitle, Element timelineTitleStyle, List<TimelineItem> items, Element itemMatrixStyle, List<Element> columnStyles) {
        this.timelineName = timelineName;
        this.timelineTitle = timelineTitle;
        this.timelineTitleStyle = timelineTitleStyle;
        this.items = items;
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

    public MatrixOfNodes getItemMatrix() {
        MatrixOfNodes.Builder matrixBuilder = new MatrixOfNodes.Builder("career", itemMatrixStyle);
        for (TimelineItem item : items) {
            TimelinePeriod timelinePeriod = TimelinePeriod.withDefaultFormat(item);
            matrixBuilder.addRow(timelinePeriod.getPeriodTag(), item.getCompany(), item.getTask());
        }
        for (Element style : columnStyles) {
            ColumnStyle columnStyle = new ColumnStyle(style);
            matrixBuilder.addColumnStyle(columnStyle.getStyle());
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
                '}';
    }
}
