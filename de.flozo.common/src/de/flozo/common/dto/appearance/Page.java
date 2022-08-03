package de.flozo.common.dto.appearance;

public class Page {

    private int id;
    private String name;
    private Length width;
    private Length height;
    private AreaStyle areaStyle;

    public Page(int id, String name, Length width, Length height, AreaStyle areaStyle) {
        this.id = id;
        this.name = name;
        this.width = width;
        this.height = height;
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

    public AreaStyle getAreaStyle() {
        return areaStyle;
    }

    public void setAreaStyle(AreaStyle areaStyle) {
        this.areaStyle = areaStyle;
    }

    @Override
    public String toString() {
        return "Page{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", areaStyle=" + areaStyle +
                '}';
    }
}
