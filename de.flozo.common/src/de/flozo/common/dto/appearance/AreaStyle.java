package de.flozo.common.dto.appearance;

public class AreaStyle {

    private int id;
    private String name;
    private Color color;
    private PredefinedOpacity opacity;

    public AreaStyle(int id, String name, Color color, PredefinedOpacity opacity) {
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public PredefinedOpacity getOpacity() {
        return opacity;
    }

    public void setOpacity(PredefinedOpacity opacity) {
        this.opacity = opacity;
    }

    @Override
    public String toString() {
        return "AreaStyle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color=" + color +
                ", opacity=" + opacity +
                '}';
    }
}
