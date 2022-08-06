package de.flozo.common.dto.appearance;

public class TextStyle {

    private int id;
    private String name;
    private FontSize fontSize;
    private TextFormat textFormat;
    private Length textWidth;
    private Length textHeight;
    private Length textDepth;
    private Alignment alignment;
    private Color color;
    private PredefinedOpacity opacity;

    public TextStyle(int id, String name, FontSize fontSize, TextFormat textFormat, Length textWidth, Length textHeight, Length textDepth, Alignment alignment, Color color, PredefinedOpacity opacity) {
        this.id = id;
        this.name = name;
        this.fontSize = fontSize;
        this.textFormat = textFormat;
        this.textWidth = textWidth;
        this.textHeight = textHeight;
        this.textDepth = textDepth;
        this.alignment = alignment;
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

    public Length getTextWidth() {
        return textWidth;
    }

    public void setTextWidth(Length textWidth) {
        this.textWidth = textWidth;
    }

    public Length getTextHeight() {
        return textHeight;
    }

    public void setTextHeight(Length textHeight) {
        this.textHeight = textHeight;
    }

    public Length getTextDepth() {
        return textDepth;
    }

    public void setTextDepth(Length textDepth) {
        this.textDepth = textDepth;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
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
        return "TextStyle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fontSize=" + fontSize +
                ", textFormat=" + textFormat +
                ", textWidth=" + textWidth +
                ", textHeight=" + textHeight +
                ", textDepth=" + textDepth +
                ", alignment=" + alignment +
                ", color=" + color +
                ", opacity=" + opacity +
                '}';
    }
}
