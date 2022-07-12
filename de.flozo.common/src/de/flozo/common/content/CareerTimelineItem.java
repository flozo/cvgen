package de.flozo.common.content;

public class CareerTimelineItem {

    private int id;
    private String name;
    private int periodStartMonth;
    private int periodStartYear;
    private int periodEndMonth;
    private int periodEndYear;
    private String task;
    private String description;
    private String location;

    public CareerTimelineItem(int id, String name, int periodStartMonth, int periodStartYear, int periodEndMonth, int periodEndYear, String task, String description, String location) {
        this.id = id;
        this.name = name;
        this.periodStartMonth = periodStartMonth;
        this.periodStartYear = periodStartYear;
        this.periodEndMonth = periodEndMonth;
        this.periodEndYear = periodEndYear;
        this.task = task;
        this.description = description;
        this.location = location;
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

    public int getPeriodStartMonth() {
        return periodStartMonth;
    }

    public void setPeriodStartMonth(int periodStartMonth) {
        this.periodStartMonth = periodStartMonth;
    }

    public int getPeriodStartYear() {
        return periodStartYear;
    }

    public void setPeriodStartYear(int periodStartYear) {
        this.periodStartYear = periodStartYear;
    }

    public int getPeriodEndMonth() {
        return periodEndMonth;
    }

    public void setPeriodEndMonth(int periodEndMonth) {
        this.periodEndMonth = periodEndMonth;
    }

    public int getPeriodEndYear() {
        return periodEndYear;
    }

    public void setPeriodEndYear(int periodEndYear) {
        this.periodEndYear = periodEndYear;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "CareerTimelineItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", periodStartMonth=" + periodStartMonth +
                ", periodStartYear=" + periodStartYear +
                ", periodEndMonth=" + periodEndMonth +
                ", periodEndYear=" + periodEndYear +
                ", task='" + task + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
