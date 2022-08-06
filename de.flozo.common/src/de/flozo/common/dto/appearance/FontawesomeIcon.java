package de.flozo.common.dto.appearance;

public class FontawesomeIcon {

    private int id;
    private IconCategory category;

    public FontawesomeIcon(int id, IconCategory category) {
        this.id = id;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IconCategory getCategory() {
        return category;
    }

    public void setCategory(IconCategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "FontawesomeIcon{" +
                "id=" + id +
                ", category=" + category +
                '}';
    }
}
