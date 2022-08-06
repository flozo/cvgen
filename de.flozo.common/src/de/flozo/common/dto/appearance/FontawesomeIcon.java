package de.flozo.common.dto.appearance;

public class FontawesomeIcon {

    private int id;
    private String specifier;
    private IconCategory category;

    public FontawesomeIcon(int id, String specifier, IconCategory category) {
        this.id = id;
        this.specifier = specifier;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecifier() {
        return specifier;
    }

    public void setSpecifier(String specifier) {
        this.specifier = specifier;
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
                ", specifier='" + specifier + '\'' +
                ", category=" + category +
                '}';
    }
}
