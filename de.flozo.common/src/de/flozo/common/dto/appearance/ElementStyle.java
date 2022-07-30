package de.flozo.common.dto.appearance;

public class ElementStyle {

    private int id;
    private String name;
    private TextStyle textStyle;
    private LineStyle lineStyle;
    private AreaStyle areaStyle;

    public ElementStyle(int id, String name, TextStyle textStyle, LineStyle lineStyle, AreaStyle areaStyle) {
        this.id = id;
        this.name = name;
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
                ", textStyle=" + textStyle +
                ", lineStyle=" + lineStyle +
                ", areaStyle=" + areaStyle +
                '}';
    }
}
