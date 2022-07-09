package de.flozo.common;

public class Element {

    private int id;
    private String name;
    private NamedPosition position;
    private NamedLength width;
    private NamedLength height;
    private Anchor anchor;
    private TextStyle textStyle;
    private LineStyle lineStyle;
    private AreaStyle areaStyle;

    public Element(int id, String name, NamedPosition position, NamedLength width, NamedLength height, Anchor anchor, TextStyle textStyle, LineStyle lineStyle, AreaStyle areaStyle) {
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

    public NamedPosition getPosition() {
        return position;
    }

    public void setPosition(NamedPosition position) {
        this.position = position;
    }

    public NamedLength getWidth() {
        return width;
    }

    public void setWidth(NamedLength width) {
        this.width = width;
    }

    public NamedLength getHeight() {
        return height;
    }

    public void setHeight(NamedLength height) {
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
}
