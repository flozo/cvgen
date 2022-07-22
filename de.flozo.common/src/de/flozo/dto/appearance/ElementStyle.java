package de.flozo.dto.appearance;

public class ElementStyle {

    private int id;
    private String name;
    private Position position;
    private Length width;
    private Length height;
    private Anchor anchor;
    private TextStyle textStyle;
    private LineStyle lineStyle;
    private AreaStyle areaStyle;

    public ElementStyle(int id, String name, Position position, Length width, Length height, Anchor anchor, TextStyle textStyle, LineStyle lineStyle, AreaStyle areaStyle) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.width = width;
        this.height = height;
        this.anchor = anchor;
        this.textStyle = textStyle;
        this.lineStyle = lineStyle;
        this.areaStyle = areaStyle;
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

    public Length getWidth() {
        return width;
    }

    public void setWidth(Length width) {
        this.width = width;
    }

    public Length getHeight() {
        return height;
    }

    public void setHeight(Length height) {
        this.height = height;
    }

    public Anchor getAnchor() {
        return anchor;
    }

    public void setAnchor(Anchor anchor) {
        this.anchor = anchor;
    }

    public TextStyle getTextStyle() {
        return textStyle;
    }

    public void setTextStyle(TextStyle textStyle) {
        this.textStyle = textStyle;
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

    @Override
    public String toString() {
        return "ElementStyle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position=" + position +
                ", width=" + width +
                ", height=" + height +
                ", anchor=" + anchor +
                ", textStyle=" + textStyle +
                ", lineStyle=" + lineStyle +
                ", areaStyle=" + areaStyle +
                '}';
    }
}
