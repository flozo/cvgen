package de.flozo.common.dto.appearance;

public class Icon {

    private int id;
    private String name;
    private FontawesomeIcon fontawesomeIcon;

    public Icon(int id, String name, FontawesomeIcon fontawesomeIcon) {
        this.id = id;
        this.name = name;
        this.fontawesomeIcon = fontawesomeIcon;
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

    public FontawesomeIcon getFontawesomeIcon() {
        return fontawesomeIcon;
    }

    public void setFontawesomeIcon(FontawesomeIcon fontawesomeIcon) {
        this.fontawesomeIcon = fontawesomeIcon;
    }

    @Override
    public String toString() {
        return "Icon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fontawesomeIcon=" + fontawesomeIcon +
                '}';
    }
}
