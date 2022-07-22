package de.flozo.dto.content;

public class Timeline {

    private int id;
    private String name;
    private String timelineTableName;

    public Timeline(int id, String name, String timelineTableName) {
        this.id = id;
        this.name = name;
        this.timelineTableName = timelineTableName;
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

    public String getTimelineTableName() {
        return timelineTableName;
    }

    public void setTimelineTableName(String timelineTableName) {
        this.timelineTableName = timelineTableName;
    }

    @Override
    public String toString() {
        return "Timeline{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", timelineTableName='" + timelineTableName + '\'' +
                '}';
    }
}
