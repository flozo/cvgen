package de.flozo.common;

public class AreaStyle {

    private int id;
    private String name;
    private BaseColor color;
    private NamedOpacity opacity;

    public AreaStyle(int id, String name, BaseColor color, NamedOpacity opacity) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.opacity = opacity;
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

    public BaseColor getColor() {
        return color;
    }

    public void setColor(BaseColor color) {
        this.color = color;
    }

    public NamedOpacity getOpacity() {
        return opacity;
    }

    public void setOpacity(NamedOpacity opacity) {
        this.opacity = opacity;
    }
}
