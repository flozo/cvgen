package de.flozo.common.dto.content;

public class TimelineTextItemLink {

    private int id;
    private String name;
    private TimelineType timelineType;
    private TextItem textItem;

    public TimelineTextItemLink(int id, String name, TimelineType timelineType, TextItem textItem) {
        this.id = id;
        this.name = name;
        this.timelineType = timelineType;
        this.textItem = textItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TimelineType getTimelineType() {
        return timelineType;
    }

    public void setTimelineType(TimelineType timelineType) {
        this.timelineType = timelineType;
    }

    public TextItem getTextItem() {
        return textItem;
    }

    public void setTextItem(TextItem textItem) {
        this.textItem = textItem;
    }

    @Override
    public String toString() {
        return "TimelineTextItemLink{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", timelineType=" + timelineType +
                ", textItem=" + textItem +
                '}';
    }
}
