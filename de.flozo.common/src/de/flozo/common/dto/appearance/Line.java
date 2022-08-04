package de.flozo.common.dto.appearance;

public class Line {

    private int id;
    private String name;
    private Position position;
    private Length length;
    private LineStyle lineStyle;
    private String orientation;

    public Line(int id, String name, Position position, Length length, LineStyle lineStyle, String orientation) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.length = length;
        this.lineStyle = lineStyle;
        this.orientation = orientation;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Length getLength() {
        return length;
    }

    public void setLength(Length length) {
        this.length = length;
    }

    public LineStyle getLineStyle() {
        return lineStyle;
    }

    public void setLineStyle(LineStyle lineStyle) {
        this.lineStyle = lineStyle;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    @Override
    public String toString() {
        return "Line{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position=" + position +
                ", length=" + length +
                ", lineStyle=" + lineStyle +
                ", orientation='" + orientation + '\'' +
                '}';
    }
}
