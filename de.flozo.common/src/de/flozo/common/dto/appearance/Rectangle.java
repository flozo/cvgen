package de.flozo.common.dto.appearance;

public class Rectangle {

    private int id;
    private String name;
    private Position origin;
    private Position target;
    private LineStyle lineStyle;
    private AreaStyle areaStyle;
    private boolean include;

    public Rectangle(int id, String name, Position origin, Position target, LineStyle lineStyle, AreaStyle areaStyle, boolean include) {
        this.id = id;
        this.name = name;
        this.origin = origin;
        this.target = target;
        this.lineStyle = lineStyle;
        this.areaStyle = areaStyle;
        this.include = include;
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

    public Position getOrigin() {
        return origin;
    }

    public void setOrigin(Position origin) {
        this.origin = origin;
    }

    public Position getTarget() {
        return target;
    }

    public void setTarget(Position target) {
        this.target = target;
    }

    public LineStyle getLineStyle() {
        return lineStyle;
    }

    public void setLineStyle(LineStyle lineStyle) {
        this.lineStyle = lineStyle;
    }

    public AreaStyle getAreaStyle() {
        return areaStyle;
    }

    public void setAreaStyle(AreaStyle areaStyle) {
        this.areaStyle = areaStyle;
    }

    public boolean isInclude() {
        return include;
    }

    public void setInclude(boolean include) {
        this.include = include;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", origin=" + origin +
                ", target=" + target +
                ", lineStyle=" + lineStyle +
                ", areaStyle=" + areaStyle +
                ", include=" + include +
                '}';
    }
}
