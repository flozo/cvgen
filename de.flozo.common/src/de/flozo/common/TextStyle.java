package de.flozo.common;

public class TextStyle {

    private int id;
    private String name;
    private TextFormat textFormat;
    private BaseColor color;
    private NamedOpacity opacity;

    public TextStyle(int id, String name, TextFormat textFormat, BaseColor color, NamedOpacity opacity) {
        this.id = id;
        this.name = name;
        this.textFormat = textFormat;
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

    public TextFormat getTextFormat() {
        return textFormat;
    }

    public void setTextFormat(TextFormat textFormat) {
        this.textFormat = textFormat;
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
