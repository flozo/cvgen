package de.flozo.common;

public class TextStyle {

    private int id;
    private String name;
    private FontSize fontSize;
    private TextFormat textFormat;
    private BaseColor color;
    private PredefinedOpacity opacity;

    public TextStyle(int id, String name, FontSize fontSize, TextFormat textFormat, BaseColor color, PredefinedOpacity opacity) {
        this.id = id;
        this.name = name;
        this.fontSize = fontSize;
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

    public FontSize getFontSize() {
        return fontSize;
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
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

    public PredefinedOpacity getOpacity() {
        return opacity;
    }

    public void setOpacity(PredefinedOpacity opacity) {
        this.opacity = opacity;
    }

    @Override
    public String toString() {
        return "TextStyle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fontSize=" + fontSize +
                ", textFormat=" + textFormat +
                ", color=" + color +
                ", opacity=" + opacity +
                '}';
    }
}
